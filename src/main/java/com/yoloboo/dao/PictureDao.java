package com.yoloboo.dao;

import com.yoloboo.controller.BaseBean.PictureBean;
import com.yoloboo.models.ActivityPictureCommentsModel;
import com.yoloboo.models.PictureModel;
import com.yoloboo.models.UpdatePictureSortModel;

import java.util.HashMap;
import java.util.List;


/**
 * Created by ZHOU005 on 2016/2/26.
 */
public interface PictureDao
{
	List<PictureModel> getPicturesByLocationAndTravelTip(PictureBean bean);

	List<PictureModel> getPicturesByLocationAndTravelTipSize(HashMap param);

	List<Long> getRelationPictureIds(PictureBean bean);

	List<UpdatePictureSortModel> getPicturesByNote();

	List<UpdatePictureSortModel> getPicturesByNoteId(UpdatePictureSortModel model);

	void updatePicture(UpdatePictureSortModel model);



}
