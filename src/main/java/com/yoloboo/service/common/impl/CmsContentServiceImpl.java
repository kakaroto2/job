package com.yoloboo.service.common.impl;

import com.common.enums.SystemEnums.HomePageNavType;
import com.yoloboo.dao.ActivityDao;
import com.yoloboo.dao.HomeCmsDao;
import com.yoloboo.dao.ThemeDao;
import com.yoloboo.dao.TravelNoteDao;
import com.yoloboo.models.ActivityModel;
import com.yoloboo.models.HomeCmsModel;
import com.yoloboo.models.ThemeModel;
import com.yoloboo.models.TravelNoteModel;
import com.yoloboo.service.common.CmsContentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by ZHOU005 on 2016/1/16.
 */

@Service
public class CmsContentServiceImpl implements CmsContentService
{
	@Resource
	private HomeCmsDao homeCmsDao;

	@Resource
	private ThemeDao themeDao;

	@Resource
	private ActivityDao activityDao;

	@Resource
	private TravelNoteDao travelNoteDao;

	private static Logger logger= LoggerFactory.getLogger(CmsContentServiceImpl.class);

	public List getHomePageContent()
	{
		logger.info("*********************** getHomePageContent start");

		List homePageContent = new ArrayList();
		List<HomeCmsModel> cmsModelList = homeCmsDao.getNavContent();

		for (HomeCmsModel model : cmsModelList)
		{
			if (model.getNavType().equals(HomePageNavType.Cate.name()))
			{
				ThemeModel themeModel = themeDao.getModelByPK(model.getNavId());

				if (null != themeModel)
				{
					homePageContent.add(themeModel);
				}
			}
			else if (model.getNavType().equals(HomePageNavType.Note.name()))
			{
				TravelNoteModel noteModel = travelNoteDao.getModelForHomePage(model.getNavId());

				if (null != noteModel)
				{
					homePageContent.add(noteModel);
				}
			}
			else if (model.getNavType().equals(HomePageNavType.Activity.name()))
			{
				ActivityModel activityModel = activityDao.getModelByPK(model.getNavId());

				if (null != activityModel)
				{
					homePageContent.add(activityModel);
				}
			}
		}

		logger.info("**********************getHomePageContent end");

		return homePageContent;
	}

	public void refreshHomePageContent()
	{
		logger.info("*********************** refreshHomePageContent");
	}


}
