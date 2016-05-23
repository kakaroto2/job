package com.yoloboo.dao.impl;

import com.yoloboo.controller.BaseBean.PictureBean;
import com.yoloboo.dao.PictureDao;
import com.yoloboo.models.ActivityPictureCommentsModel;
import com.yoloboo.models.PictureModel;
import com.yoloboo.models.UpdatePictureSortModel;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;


/**
 * Created by ZHOU005 on 2016/2/26.
 */

@Repository
public class PictureDaoImpl extends BaseDao implements PictureDao
{

	@Override
	public List<PictureModel> getPicturesByLocationAndTravelTip(PictureBean bean)
	{
		return (List<PictureModel>) sqlSession.selectList("PictureDao.getPicturesByLocationAndTravelTip", bean);
	}


	@Override
	public List<PictureModel> getPicturesByLocationAndTravelTipSize(HashMap param)
	{
		return (List<PictureModel>) sqlSession.selectList("PictureDao.getPicturesByLocationAndTravelTipSize", param);
	}

	@Override
	public List<Long> getRelationPictureIds(PictureBean bean)
	{
		return (List<Long>) sqlSession.selectList("PictureDao.getRelationPictureIds", bean);
	}

	@Override
	public List<UpdatePictureSortModel> getPicturesByNote()
	{
		return (List<UpdatePictureSortModel>) sqlSession.selectList("PictureDao.getPicturesByNote");
	}

	@Override
	public List<UpdatePictureSortModel> getPicturesByNoteId(UpdatePictureSortModel model)
	{
		return (List<UpdatePictureSortModel>) sqlSession.selectList("PictureDao.getPicturesByNoteId",model);
	}

	@Override
	public void updatePicture(UpdatePictureSortModel model)
	{
		sqlSession.update("PictureDao.updatePicture",model);
	}


}

