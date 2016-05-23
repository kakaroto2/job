package com.yoloboo.service.impl;

import com.common.*;

import com.yoloboo.controller.BaseBean.UserBean;
import com.yoloboo.dao.*;
import com.yoloboo.excptions.ActionPermissionDelayException;
import com.json.BaseBean;
import com.yoloboo.controller.BaseBean.TravelNoteBean;
import com.yoloboo.models.PictureModel;
import com.yoloboo.models.ThemeModel;
import com.yoloboo.models.TravelNoteModel;
import com.yoloboo.service.TravelNotesService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


/**
 * Created by ZHOU005 on 2015/12/28.
 */

@Service
@Transactional
public class TravelNotesServiceImpl implements TravelNotesService {
    @Resource
    private TravelManager travelManager;

    @Resource
    private CountryManager countryManager;

    @Resource
    private TravelNoteDao travelNoteDao;

    @Resource
    private ThemeDao themeDao;

    @Resource
    private TopicManager topicManager;

    private static Logger logger = LoggerFactory.getLogger(TravelNotesServiceImpl.class);

    @Override
    public BaseBean addTravelNotes(TravelNoteBean bean) {
        BaseBean baseBean = new BaseBean();
        HashMap param = new HashMap();
        HashMap param1 = new HashMap();

        if (StringUtils.isNotEmpty(bean.getQiniukey())) {// 添加图片时候必须

            param.put("countryId", bean.getCountryId());
            param.put("countryName", bean.getCountryName());
            param.put("date", bean.getDate());
            param.put("userId", bean.getUserId());
            param.put("travelThemeId", bean.getTravelThemeId());
            param.put("newtime", Commonparam.Date2Str());
            param.put("stringId", (new Date()).getTime() + "-" + Commonparam.createRandomNumber());

            String travelNotesId = null;

            if (StringUtils.isBlank(bean.getTravelNotesId())) {// 表示需要新建游记，下面的贴士也是需要新增的.保存新的游记,获得游记的ID
                travelNotesId = travelManager.addTravelNotes(param).toString();
            } else// 表示已经有新的游记了，下面的贴士需要新增，归属在改游记下面就可以了newPictureTips
            {
            }
            // 下面是图片的属性
            param.put("p_width", bean.getP_width());
            param.put("p_height", bean.getP_height());
            param.put("travelNotesId", travelNotesId);// 游记ID
            param.put("pictureDate", bean.getPictureDate());// 图片日期
            param.put("address", bean.getDate());// 地址
            param.put("description", bean.getDescription());// 描述
            param.put("picture", QiNiuUtils.getSaveDBUrl(bean.getQiniukey()));// 图片地址
            param.put("isnocover", bean.getIsnocover());// 是否是封面

            String pictureTipsId = travelManager.newPictureTips(param);// 增加游记

            param.put("pictureTipsId", pictureTipsId);

            param.put("moodTipsId", bean.getMoodTipsId());// 情感标签
            param.put("notesTipsId", bean.getNotesTipsId());// 贴士标签

            String[] ids = bean.getMoodTipsId().split(",");

            for (int i = 0; i < ids.length; i++) {
                if (ids[i].trim().length() == 0) {
                    continue;
                }
                param1.clear();
                if (ids[i].substring(0, 1).equals("_")) {
                    param1.put("moodId", 10000);// 自己写的没有id只有明曾
                    param1.put("moodName", ids[i].replaceAll("_", ""));// 说明是自己填写的
                    param1.put("pictureTipsId", pictureTipsId);// 获取的图片的iD
                } else {
                    param1.put("moodId", ids[i]);// 说明是官方的
                    param1.put("moodName", "");// 说明是官方的
                    param1.put("pictureTipsId", pictureTipsId);// 获取的图片的iD
                }

                travelManager.moodTipsPicture(param1);// 情感和图片的标签关系增加数据
            }
            // 贴士标签
            String[] ids1 = bean.getNotesTipsId().split(",");

            for (int i = 0; i < ids1.length; i++) {
                if (ids1[i].trim().length() == 0) {
                    continue;
                }
                param1.clear();
                if (ids1[i].substring(0, 1).equals("_")) {
                    param1.put("travelTipsId", 10000);// 自己写的没有id只有明曾
                    param1.put("travelTipsName", ids1[i].replaceAll("_", ""));// 说明是自己填写的
                    param1.put("pictureTipsId", pictureTipsId);// 获取的图片的iD
                } else {
                    param1.put("travelTipsId", ids1[i]);// 说明是官方的
                    // 验证一下贴士是否属于国家贴士
                    // 如果是的，还要把再tips_column加数据
                    HashMap pictureMap = travelManager.obtainQuestionType(ids1[i]);// 获得是否是属于国家贴士
                    @SuppressWarnings("rawtypes")
                    HashMap param2 = new HashMap();
                    if (pictureMap.get("ttType").toString().equals("1")) {
                        param2.put("countryTipsId", pictureMap.get("countryTipsId"));// 保存国家贴士ID
                        param2.put("description", bean.getDescription());// 保存描述
                        param2.put("userId", bean.getUserId());// 用户ID
                        param2.put("picture", param.get("picture"));// 保存图片
                        param2.put("tcDate", Commonparam.Date2Str());
                        param2.put("countryId", bean.getCountryId());
                        param2.put("pictureTipsId", pictureTipsId);
                        param2.put("travelNotesId", travelNotesId);// 游记ID
                        param2.put("address", bean.getAddress());// 地址
                        travelManager.addPitureToTipsDataContent(param2);// 属于贴士的话要保存到贴士数据的那个表面//0否(默认)

                    }

                    param1.put("travelTipsName", "");// 说明是官方的
                    param1.put("pictureTipsId", pictureTipsId);// 获取的图片的iD
                }

                travelManager.notesTipsPicture(param1);// 情感和图片的标签关系增加数据
            }

            // travelNotesRead表没有用，不进行次操作
            // travelManager.travelNotesRead(param);//把游记阅读关系表改一下，每个好友的都改一下

            // 添加通知中数据，他的每个好友都要在里面,该处另起线程
            //new PushThread(travelManager, param).start();

            baseBean.setStatus(200);
            baseBean.setRows(travelNotesId);
            baseBean.setMsg("上传成功");

            travelManager.addCommoneAddress(bean.getAddress());
        }

        return baseBean;
    }

    @Override
    public BaseBean modifyPictureTips(TravelNoteBean bean) {
        BaseBean baseBean = new BaseBean();
        HashMap param = new HashMap();
        HashMap param1 = new HashMap();

        if (StringUtils.isNotEmpty(bean.getQiniukey())) {//更换了照片
            param.put("picture", QiNiuUtils.getSaveDBUrl2(bean.getQiniukey()));// 图片地址
            param.put("commentsNum", "0");//
            param.put("praiseNum", "0");
            param.put("travelNotesId", bean.getTravelNotesId());
            String stringId = (new Date()).getTime() + "-" + Commonparam.createRandomNumber();
            param.put("stringId", stringId);
            param.put("pictureId", bean.getPictureTipsId());
            param.put("pictureDate", bean.getPictureDate());// 图片日期
            param.put("address", bean.getAddress());// 地址
            param.put("description", bean.getDescription());// 描述
            param.put("isnocover", bean.getIsnocover());// 是否是封面
            param.put("pictureTipsId", bean.getPictureTipsId()); //更换之前的图片ID
            param.put("p_width", bean.getP_width());
            param.put("p_height", bean.getP_height());
            //param.put("travelNotesId",travelManager.obtainNotesId(pictureTipsId).get("travelNotesId"));
            //param.put("pictureId", pictureTipsId);
            //插数据 并获得pictureId
            String newPicId = travelManager.newPictureTips(param);//插入新图片
            //软删除照片 以及修改游记点赞数评论数
            travelManager.deletePictureTips(param);

            // 修改游记总的点赞数、评论数
            //travelManager.deleteTravelPraiseAndCommentsByPictureId(PrameterBuilder.instance().add("pictureTipsId", pictureTipsId).builder());
            //删除照片的三表贴士
            //param.put("pictureTipsId",pictureTipsId);
            travelManager.deleteTips(param);
            //删除照片的评论点赞tb_praise tb_comments
            travelManager.deleteCommentPrasieById(param);

            param.put("pictureTipsId", newPicId); //更换之后的图片ID

        } else { //未更换图片 qiniukey为空
            param.put("pictureTipsId", bean.getPictureTipsId());
            param.put("pictureDate", bean.getPictureDate());// 图片日期
            param.put("address", bean.getAddress());// 地址
            param.put("description", bean.getDescription());// 描述
            param.put("isnocover", bean.getIsnocover());// 是否是封面
            travelManager.modifyPictureTips(param);// 编辑照片 更新字段

            //删除三表数据
            travelManager.deleteNotesMoodTips(param);
            travelManager.deleteNotesTips(param);
            HashMap param3 = new HashMap();
            param3.put("pictureTipsId", bean.getPictureTipsId());
            travelManager.deletePitureToTipsDataContent(param3);// 删除国家贴士数据
        }

        param.put("moodTipsId", bean.getMoodTipsId());// 情感标签
        param.put("notesTipsId", bean.getNotesTipsId());// 贴士标签

        String[] ids = bean.getMoodTipsId().split(",");

        for (int i = 0; i < ids.length; i++) {
            if (ids[i].trim().length() == 0) {
                continue;
            }
            param1.clear();
            if (ids[i].substring(0, 1).equals("_")) {
                param1.put("moodId", 10000);// 自己写的没有id只有明曾
                param1.put("moodName", ids[i].replaceAll("_", ""));// 说明是自己填写的
                param1.put("pictureTipsId", param.get("pictureTipsId"));// 获取的图片的iD
            } else {
                param1.put("moodId", ids[i]);//
                param1.put("moodName", "");//
                param1.put("pictureTipsId", param.get("pictureTipsId"));// 获取的图片的iD
            }
            travelManager.moodTipsPicture(param1);// 情感和图片的标签关系增加数据 tb_picture_mood
        }
        // 贴士标签
        String[] ids1 = bean.getNotesTipsId().split(",");
        // 删除贴士标签


        for (int i = 0; i < ids1.length; i++) {
            if (ids1[i].trim().length() == 0) {
                continue;
            }
            param1.clear();
            if (ids1[i].substring(0, 1).equals("_")) {
                param1.put("travelTipsId", 10000);// 自己写的没有id只有明曾
                param1.put("travelTipsName", ids1[i].replaceAll("_", ""));// 说明是自己填写的
                param1.put("pictureTipsId", param.get("pictureTipsId"));// 获取的图片的iD
            } else {
                param1.put("travelTipsId", ids1[i]);// 说明是官方的
                // 验证一下贴士是否属于国家贴士,
                HashMap pictureMap = travelManager.obtainQuestionType(ids1[i]);// 获得是否是属于国家贴士
                HashMap param2 = new HashMap();
                if (pictureMap != null && pictureMap.get("ttType").toString().equals("1")) {
                    param2.put("countryTipsId", pictureMap.get("countryTipsId"));// 保存国家贴士ID
                    param2.put("description", bean.getDescription());// 保存描述
                    param2.put("userId", bean.getUserId());// 用户ID
                    if (StringUtils.isBlank(bean.getQiniukey())) {
                        //logger.debug("pictureId=====================================>"+param.get("pictureTipsId").toString());
                        HashMap maps = travelManager.findPicturePath(param.get("pictureTipsId").toString());
                        //logger.debug("pictureUrl==============================>"+maps.get("picture"));
                        param2.put("picture", maps.get("p_picture"));// 保存图片
                        //logger.debug("picture=================================>"+param2.get("picture"));
                        param2.put("p_width", maps.get("p_width"));
                        param2.put("p_height", maps.get("p_height"));
                        // 有修改的时候传
                    } else {
                        param2.put("picture", param.get("picture"));// 保存图片
                        param2.put("p_width", param.get("p_width"));// 有修改的时候传
                        param2.put("p_height", param.get("p_height"));
                    }
                    param2.put("tcDate", Commonparam.Date2Str());
                    param2.put("pictureTipsId", param.get("pictureTipsId"));// 获取的图片的iD
                    // pictureTipsId
                    param2.put("countryId", travelManager.findNoteCountryIdByPictureId(param.get("pictureTipsId").toString()));
                    param2.put("travelNotesId",
                            travelManager.findTravelNotesIdByPictureId(param.get("pictureTipsId").toString()));// 游记ID
                    param2.put("address", bean.getAddress());// 地址
                    travelManager.addPitureToTipsDataContent(param2);// 属于贴士的话要保存到贴士数据的那个表面//0否(默认)
                    // //1是
                }
                param1.put("travelTipsName", "");// 说明是官方的
                param1.put("pictureTipsId", param.get("pictureTipsId"));// 获取的图片的iD
            }
            travelManager.notesTipsPicture(param1);// 情感和图片的标签关系增加数据tb_travel_tips_picture
        }

        if (StringUtils.isNotEmpty(bean.getQiniukey())) {
            //new PushThreadTips(travelManager, param).start();
        }
        baseBean.setStatus(200);
        baseBean.setMsg("上传成功");
        travelManager.addCommoneAddress(bean.getAddress());

        return baseBean;
    }

    @Override
    public List inputLocationQuery(String replace) {
        return travelManager.inputLocationQuery(replace);
    }

    @Override
    public List inputLocationOrCountryQuery(String replace) {
        return travelManager.inputLocationOrCountryQuery(replace);
    }

    @Override
    public List obtainCategory() {
        return travelManager.obtainCategory();
    }

    @Override
    public HashMap newTravelNote(String userId, String noteName, String locationId, String themeId, String noteDescription,
                                 String qiniukey, String squareSide, String picDate, String picDescription, String isnoCover, String picAddress,
                                 String travelTipsId, String moodTipsId) {
        HashMap param = new HashMap();
        param.put("userId", userId);

        param.put("title", noteName);
        param.put("noteDescription", noteDescription);
        param.put("themeId", themeId);
        param.put("stringId", (new Date()).getTime() + "-" + Commonparam.createRandomNumber());
        String noteTime = Commonparam.Date2Str();
        param.put("addTime", noteTime);
        param.put("locationId", locationId);
        String countryId = null;
        if(Long.valueOf(locationId) > 10000){//地区
            countryId = travelManager.obtainCountryByLocation(locationId);
            param.put("countryId", countryId);
            param.put("locationId", locationId);
        }
        else{//国家
            param.put("countryId", locationId);
            countryId = locationId;
            param.put("locationId", null);
        }
        //插入文章

        String travelNoteId = travelManager.insertNewNote(param);
        param.clear();
        param.put("squareSide", squareSide);
        param.put("picDate", picDate);
        param.put("picAddress", picAddress);
        param.put("picture", QiNiuUtils.getSaveDBUrl2(qiniukey));
        param.put("picDescription", picDescription);
        param.put("isnoCover", isnoCover);
        param.put("travelNoteId", travelNoteId);
        param.put("stringId", (new Date()).getTime() + "-" + Commonparam.createRandomNumber());
        //插入图片
        //先去获取最大的排序值 默认为1
        // Integer sort=travelManager.getLastSort(travelNoteId);
        param.put("sort",1);
        String pictureId = travelManager.insertNewPicture(param);
        //插入贴士(自定义贴士只存放在travel_tips_picture中)
        HashMap param1 = new HashMap();
        //心情贴士
        String[] ids = moodTipsId.split(",");
        for (int i = 0; i < ids.length; i++) {
            if (ids[i].trim().length() == 0) {
                continue;
            }
            param1.clear();
            param1.put("moodId", ids[i]);// 说明是官方的
            param1.put("moodName", "");// 说明是官方的
            param1.put("pictureTipsId", pictureId);// 获取的图片的iD
            travelManager.moodTipsPicture(param1);// 情感和图片的标签关系增加数据
        }

        //旅游贴士
        String[] ids1 = travelTipsId.split(",");
        for (int i = 0; i < ids1.length; i++) {
            if (ids1[i].trim().length() == 0) {
                continue;
            }
            param1.clear();
            if (ids1[i].substring(0, 1).equals("_")) {
                param1.put("travelTipsId", 10000);// 自己写的没有id只有明曾
                param1.put("travelTipsName", ids1[i].replaceAll("_", ""));// 说明是自己填写的
                param1.put("pictureTipsId", pictureId);// 获取的图片的iD
            } else {
                param1.put("travelTipsId", ids1[i]);// 说明是官方的
                // 验证一下贴士是否属于国家贴士
                // 如果是的，还要把再tips_column加数据
                HashMap pictureMap = travelManager.obtainQuestionType(ids1[i]);// 获得是否是属于国家贴士
                @SuppressWarnings("rawtypes")
                HashMap param2 = new HashMap();
                //1为国家贴士country_tip
                if (pictureMap.get("ttType").toString().equals("1")) {
                    param2.put("countryTipsId", pictureMap.get("countryTipsId"));// 保存国家贴士ID
                    param2.put("description", picDescription);// 保存描述
                    param2.put("userId", userId);// 用户ID
                    param2.put("picture", QiNiuUtils.getSaveDBUrl2(qiniukey));// 保存图片
                    param2.put("tcDate", Commonparam.Date2Str());
                    param2.put("countryId", countryId);
                    param2.put("pictureTipsId", pictureId);
                    param2.put("travelNotesId", travelNoteId);// 游记ID
                    param2.put("address", picAddress);// 地址
                    travelManager.addPitureToTipsDataContent(param2);// 属于贴士的话要保存到贴士数据的那个表面//0否(默认)
                }

                param1.put("travelTipsName", "");// 说明是官方的
                param1.put("pictureTipsId", pictureId);// 获取的图片的iD
            }

            travelManager.notesTipsPicture(param1);// 情感和图片的标签关系增加数据
        }
        // 添加通知中数据，他的每个好友都要在里面,该处另起线程
        param.put("userId", userId);
        param.put("title", noteName);
        param.put("countryId", countryId);
        param.put("picture", QiNiuUtils.getSaveDBUrl2(qiniukey));
        param.put("pictureId", pictureId);
        travelManager.addCommoneAddress(picAddress);
        // new PushThreadNewNote(travelManager, param).start();
        return obtainNoteInfoById(param);
    }

    private HashMap obtainNoteInfoById(HashMap param) {
        String travelNoteId = param.get("travelNoteId").toString();
        HashMap obtainNotesInfoToTips2 = travelManager.obtainNotesInfoToTips(travelNoteId);
        if (obtainNotesInfoToTips2 != null) {

            List<HashMap> obtainOneNotesTipsList = travelManager.obtainOneNotesTipsList(param); //获取note下pic信息
            for (HashMap tip : obtainOneNotesTipsList) {
                tip.put("moodList", travelManager.findNoteMoodList(tip.get("tipsId").toString()));
                tip.put("styleList", travelManager.findNoteStyleList(tip.get("tipsId").toString()));
                tip.put("praiseList", travelManager.findNotePraiseList(tip.get("tipsId").toString()));
            }
            HashMap map1 = travelManager.obtainCoverPicture(Long.valueOf(travelNoteId));
            if (map1 != null) {
                String coverPicture = map1.get("p_picture_square").toString();// 获取封面图片
                obtainNotesInfoToTips2.put("coverPicture", coverPicture == null ? "" : coverPicture);
                obtainNotesInfoToTips2.put("square_side", map1.get("square_side"));
            }
            HashMap userInfo = countryManager
                    .obtainUserInfo(Long.valueOf(obtainNotesInfoToTips2.get("userId").toString()));// 获取头像和昵称

            obtainNotesInfoToTips2.put("travelNoteId", travelNoteId);
            obtainNotesInfoToTips2.put("headPicture", userInfo.get("headPicture"));
            obtainNotesInfoToTips2.put("nickname", userInfo.get("nickname"));
            obtainNotesInfoToTips2.put("tipsList", obtainOneNotesTipsList);// 贴士列表

        }
        return obtainNotesInfoToTips2;
    }

    @Override

    public HashMap newNotePicture(String userId, String qiniukey, String travelNoteId, String picAddress, String picDate,
                                  String picDescription, String isnoCover, String squareSide, String travelTipsId, String moodTipsId) {
        if (isnoCover.contains("1")) {
            travelManager.clearCoverPicture(travelNoteId);
        }
        HashMap param = new HashMap();
        String countryId = travelManager.obtainCountryByNote(travelNoteId);
        param.put("picture", QiNiuUtils.getSaveDBUrl2(qiniukey));
        param.put("travelNoteId", travelNoteId);
        param.put("picAddress", picAddress);
        param.put("picDate", picDate);
        param.put("picDescription", picDescription);
        param.put("isnoCover", isnoCover);
        param.put("squareSide", squareSide);
        param.put("stringId", (new Date()).getTime() + "-" + Commonparam.createRandomNumber());
        //先去获取最大的排序值
        Integer sort=travelManager.getLastSort(travelNoteId);
        param.put("sort",sort+1);
        String pictureId = travelManager.insertNewPicture(param);
        HashMap param1 = new HashMap();
        //心情贴士
        String[] ids = moodTipsId.split(",");
        for (int i = 0; i < ids.length; i++) {
            if (ids[i].trim().length() == 0) {
                continue;
            }
            param1.clear();
            param1.put("moodId", ids[i]);// 说明是官方的
            param1.put("moodName", "");// 说明是官方的
            param1.put("pictureTipsId", pictureId);// 获取的图片的iD
            travelManager.moodTipsPicture(param1);// 情感和图片的标签关系增加数据
        }

        //旅游贴士
        String[] ids1 = travelTipsId.split(",");
        for (int i = 0; i < ids1.length; i++) {
            if (ids1[i].trim().length() == 0) {
                continue;
            }
            param1.clear();
            if (ids1[i].substring(0, 1).equals("_")) {
                param1.put("travelTipsId", 10000);// 自己写的没有id只有明曾
                param1.put("travelTipsName", ids1[i].replaceAll("_", ""));// 说明是自己填写的
                param1.put("pictureTipsId", pictureId);// 获取的图片的iD
            } else {
                param1.put("travelTipsId", ids1[i]);// 说明是官方的
                // 验证一下贴士是否属于国家贴士
                // 如果是的，还要把再tips_column加数据
                HashMap pictureMap = travelManager.obtainQuestionType(ids1[i]);// 获得是否是属于国家贴士
                @SuppressWarnings("rawtypes")
                HashMap param2 = new HashMap();
                //1为国家贴士country_tip
                if (pictureMap.get("ttType").toString().equals("1")) {
                    param2.put("countryTipsId", pictureMap.get("countryTipsId"));// 保存国家贴士ID
                    param2.put("description", picDescription);// 保存描述
                    param2.put("userId", userId);// 用户ID
                    param2.put("picture", QiNiuUtils.getSaveDBUrl2(qiniukey));// 保存图片
                    param2.put("tcDate", Commonparam.Date2Str());
                    param2.put("countryId", countryId);
                    param2.put("pictureTipsId", pictureId);
                    param2.put("travelNotesId", travelNoteId);// 游记ID
                    param2.put("address", picAddress);// 地址
                    travelManager.addPitureToTipsDataContent(param2);// 属于贴士的话要保存到贴士数据的那个表面//0否(默认)
                }

                param1.put("travelTipsName", "");// 说明是官方的
                param1.put("pictureTipsId", pictureId);// 获取的图片的iD
            }
            travelManager.notesTipsPicture(param1);// 情感和图片的标签关系增加数据
        }
        param.clear();
        param.put("userId", userId);
        param.put("travelNoteId", travelNoteId);
        travelManager.addCommoneAddress(picAddress);
        return obtainNoteInfoById(param);
    }

    @Override
    public void modifyTravelNote(String travelNoteId, String locationId, String themeId, String noteName, String noteDescription) {
        HashMap param = new HashMap();

        param.put("travelNoteId", travelNoteId);
        param.put("themeId", themeId);
        param.put("noteName", noteName);
        param.put("noteDescription", noteDescription);

        String countryId = null;
        if(Long.valueOf(locationId) > 10000){//地区
            countryId = travelManager.obtainCountryByLocation(locationId);
            param.put("countryId", countryId);
            param.put("locationId", locationId);
        }
        else{//国家
            param.put("countryId", locationId);
            countryId = locationId;
            param.put("locationId", null);
        }

        travelManager.modifyTravelNote(param);

        // new PushThreadNewNote(travelManager, param).start();

    }

    @Override
    public HashMap modifyNotePicture(String userId, String qiniukey, String travelNoteId, String pictureId, String picDate,
                                     String picDescription, String picAddress, String isnoCover, String squareSide, String travelTipsId, String moodTipsId) {
        HashMap param = new HashMap();
        if (isnoCover.contains("1")) {
            travelManager.clearCoverPicture(travelNoteId);
        }
        if (/*StringUtils.isNotEmpty(qiniukey)*/false) {//更换图片url
            param.put("picture", QiNiuUtils.getSaveDBUrl2(qiniukey));
            param.put("travelNoteId", travelNoteId);
            String stringId = (new Date()).getTime() + "-" + Commonparam.createRandomNumber();
            param.put("stringId", stringId);
            param.put("pictureId", pictureId);
            param.put("picDate", picDate);// 图片日期
            param.put("picAddress", picAddress);// 地址
            param.put("picDescription", picDescription);// 描述
            param.put("isnoCover", isnoCover);// 是否是封面
            param.put("pictureTipsId", pictureId); //更换之前的图片ID
            param.put("squareSide", squareSide);

            //先去获取最大的排序值
            Integer sort=travelManager.getLastSort(travelNoteId);
            param.put("sort",sort+1);
            String newPicId = travelManager.insertNewPicture(param);

            //软删除照片 以及修改游记点赞数评论数
            travelManager.deletePictureTips(param);

            // 修改游记总的点赞数、评论数
            //travelManager.deleteTravelPraiseAndCommentsByPictureId(PrameterBuilder.instance().add("pictureTipsId", pictureTipsId).builder());
            //删除照片的三表贴士
            //param.put("pictureTipsId",pictureTipsId);
            travelManager.deleteTips(param);
            //删除照片的评论点赞tb_praise tb_comments
            travelManager.deleteCommentPrasieById(param);
            param.put("pictureTipsId", newPicId); //更换之后的图片ID
        } else {//未更换图片
            if (StringUtils.isNotEmpty(qiniukey)) {
                param.put("picture", QiNiuUtils.getSaveDBUrl2(qiniukey));
                param.put("squareSide", squareSide);
            }
            param.put("pictureTipsId", pictureId);
            param.put("pictureDate", picDate);// 图片日期
            param.put("address", picAddress);// 地址
            param.put("description", picDescription);// 描述
            param.put("isnocover", isnoCover);// 是否是封面
            travelManager.modifyPictureTips(param);// 编辑照片 更新字段
            Integer flag = travelManager.autoCheckCover(travelNoteId);
            //删除三表数据
            travelManager.deleteNotesMoodTips(param);
            travelManager.deleteNotesTips(param);
            HashMap param3 = new HashMap();
            param3.put("pictureTipsId", pictureId);
            travelManager.deletePitureToTipsDataContent(param3);// 删除国家贴士数据
        }
        String countryId = travelManager.obtainCountryByNote(travelNoteId);
        HashMap param1 = new HashMap();
        //心情贴士
        String[] ids = moodTipsId.split(",");
        for (int i = 0; i < ids.length; i++) {
            if (ids[i].trim().length() == 0) {
                continue;
            }
            param1.clear();
            param1.put("moodId", ids[i]);// 说明是官方的
            param1.put("moodName", "");// 说明是官方的
            param1.put("pictureTipsId", pictureId);// 获取的图片的iD
            travelManager.moodTipsPicture(param1);// 情感和图片的标签关系增加数据
        }

        //旅游贴士
        String[] ids1 = travelTipsId.split(",");
        for (int i = 0; i < ids1.length; i++) {
            if (ids1[i].trim().length() == 0) {
                continue;
            }
            param1.clear();
            if (ids1[i].substring(0, 1).equals("_")) {
                param1.put("travelTipsId", 10000);// 自己写的没有id只有明曾
                param1.put("travelTipsName", ids1[i].replaceAll("_", ""));// 说明是自己填写的
                param1.put("pictureTipsId", pictureId);// 获取的图片的iD
            } else {
                param1.put("travelTipsId", ids1[i]);// 说明是官方的
                // 验证一下贴士是否属于国家贴士
                // 如果是的，还要把再tips_column加数据
                HashMap pictureMap = travelManager.obtainQuestionType(ids1[i]);// 获得是否是属于国家贴士
                @SuppressWarnings("rawtypes")
                HashMap param2 = new HashMap();
                //1为国家贴士country_tip
                if (pictureMap != null && pictureMap.get("ttType").toString().equals("1")) {
                    param2.put("countryTipsId", pictureMap.get("countryTipsId"));// 保存国家贴士ID
                    param2.put("description", picDescription);// 保存描述
                    param2.put("userId", userId);// 用户ID
                    if (StringUtils.isEmpty(qiniukey)) {//未更换图片
                        String squarePicture = travelManager.obtainPictureById(pictureId);
                        param2.put("picture", squarePicture);
                    } else {//更换图片
                        param2.put("picture", QiNiuUtils.getSaveDBUrl2(qiniukey));// 保存图片
                    }
                    param2.put("tcDate", Commonparam.Date2Str());
                    param2.put("countryId", countryId);
                    param2.put("pictureTipsId", pictureId);
                    param2.put("travelNotesId", travelNoteId);// 游记ID
                    param2.put("address", picAddress);// 地址
                    travelManager.addPitureToTipsDataContent(param2);// 属于贴士的话要保存到贴士数据的那个表面//0否(默认)
                }

                param1.put("travelTipsName", "");// 说明是官方的
                param1.put("pictureTipsId", pictureId);// 获取的图片的iD
            }
            travelManager.notesTipsPicture(param1);// 情感和图片的标签关系增加数据
        }
        param.clear();
        param.put("userId", userId);
        param.put("travelNoteId", travelNoteId);
        travelManager.addCommoneAddress(picAddress);
        return obtainNoteInfoById(param);
    }

    @Override
    public void deleteTravelNote(String travelNoteId) {
        //软删除文章
        travelManager.deleteTravelNoteById(travelNoteId);
        //软删除文章下的照片
        List<HashMap> pictureList = travelManager.deletePicturesById(travelNoteId);
        for (HashMap map1 : pictureList) {
            //删除照片下的评论点赞
            travelManager.deleteCommentPrasieById(map1);
            //删除照片下的贴士
            travelManager.deleteTips(map1);
        }
        //删除收藏表
        travelManager.deleteNoteCollectById(travelNoteId);
        //更新用户的阅读数
        String userId = travelManager.obtainUserByNote(travelNoteId);
        HashMap map = new HashMap();
        map.put("userId", userId);
        map.put("travelNoteId", travelNoteId);
        travelManager.updateUserRead(map);
    }

    @Override
    public void deleteNotePicture(String pictureId) throws ActionPermissionDelayException {
        String travelNoteId = travelManager.obtainNoteByPicture(pictureId);
        //软删除照片
        long pictureNum = travelManager.obtainPictureNumByNote(travelNoteId);
        if (pictureNum > 1) {
            travelManager.softDeletePictureById(pictureId);
        } else {
            throw new ActionPermissionDelayException();
        }
        //删除贴士
        HashMap param = new HashMap();
        param.put("pictureId", pictureId);
        travelManager.deleteTips(param);
        //删除评论点赞
        travelManager.deleteCommentPrasieById(param);
        param.put("travelNoteId", travelNoteId);
        //更新文章评论点赞数
        travelManager.updateNoteCommentPraise(param);

        Integer flag = travelManager.autoCheckCover(travelNoteId);
    }

    @Override
    public List<HashMap> obtainPicturesByNote(String travelNoteId, String userId) {
        //文章阅读数+1
        travelManager.addNoteRead(travelNoteId);
        //用户阅读数+1
        travelManager.addUserRead(userId);
        //获取文章下的照片信息
        List<HashMap> pictureInfoList = travelManager.obtainPicInfoList(travelNoteId);
        HashMap param = new HashMap();
        param.put("userId", userId);
        param.put("travelNoteId", travelNoteId);
        //是否收藏
        long isCollect = travelManager.judgeIsCollect(param);
        HashMap travelNoteInfo = travelManager.obtainTravelNoteInfo(travelNoteId);
        travelNoteInfo.put("pictureNum", pictureInfoList.size());
        travelNoteInfo.put("isCollect", isCollect);
        pictureInfoList.add(travelNoteInfo);
        return pictureInfoList;
    }

    @Override
    public void collectNote(String userId, String travelNoteId) {
        HashMap param = new HashMap();
        param.put("userId", userId);
        param.put("travelNoteId", travelNoteId);
        param.put("collectTime", Commonparam.Date2Str());
        travelManager.addCollectNote(param);
    }

    @Override
    public List<TravelNoteModel> getNotesForHomePage(UserBean userBean) {
        return travelNoteDao.getModelForHomePageByUser(userBean);
    }

    public PictureModel obtainPictureInfoByPictureId(String pictureId) {
        return travelManager.getPictureModelsByPictureId(pictureId);
    }

    @Override
    public ThemeModel getCategoryNotes(UserBean userBean) {
        ThemeModel themeModel = themeDao.getModelByPK(userBean.getThemeId());

        themeModel.setNoteModelList(getNotesForHomePage(userBean));
        themeModel.setTopicNum(topicManager.getTopicCountByTheme(userBean.getThemeId()));

        return themeModel;
    }

    @Override
    public TravelNoteModel obtainNoteInfoByNoteId(String travelNoteId) {
        return travelNoteDao.getModelByPK(Long.valueOf(travelNoteId));
    }

    @Override
    public void uploadTravelNote(String noteName,String travelNoteId,String userId,String locationId, Date now) {
        HashMap param = new HashMap();
        param.put("travelNoteId", travelNoteId);
        param.put("now", now);
        travelManager.uploadTravelNote(param);
        String countryId = null;
        if(Long.valueOf(locationId) > 10000){//地区
            countryId = travelManager.obtainCountryByLocation(locationId);
            param.put("countryId", countryId);
            param.put("locationId", locationId);
        }
        else{//国家
            param.put("countryId", locationId);
            countryId = locationId;
            param.put("locationId", null);
        }
        //获取该文章的封面照片信息
        PictureModel model=travelManager.getPictureModelsByNoteId(travelNoteId);
        param.put("pictureId",model.getId());
        param.put("picture",model.getPicture());
        param.put("userId",userId);
        param.put("title",noteName);
        //发布文章的时候推送消息
        new PushThreadNewNote(travelManager, param).start();
    }

}
