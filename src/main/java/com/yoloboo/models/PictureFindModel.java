package com.yoloboo.models;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;


/**
 * Created by ZHOU005 on 2016/2/26.
 */
public class PictureFindModel
{

	private String nameEn;
	private String nameCn;
	private String nameTw;
	private Long countryId;
	private Long locationId;
	private Long travelTipId;
	private Long countryTipId;
	private List<PictureModel> pictureModels;
	private List<TravelTipsModel> travelTipsModels;

	public String getNameTw() {
		return nameTw;
	}

	public void setNameTw(String nameTw) {
		this.nameTw = nameTw;
	}

	public String getNameEn() {
		return nameEn;
	}

	public void setNameEn(String nameEn) {
		this.nameEn = nameEn;
	}

	public String getNameCn() {
		return nameCn;
	}

	public void setNameCn(String nameCn) {
		this.nameCn = nameCn;
	}

	public Long getCountryId()
	{
		return countryId;
	}

	public void setCountryId(Long countryId)
	{
		this.countryId = countryId;
	}

	public Long getLocationId()
	{
		return locationId;
	}

	public void setLocationId(Long locationId)
	{
		this.locationId = locationId;
	}

	public Long getCountryTipId()
	{
		return countryTipId;
	}

	public void setCountryTipId(Long countryTipId)
	{
		this.countryTipId = countryTipId;
	}

	public Long getTravelTipId()
	{
		return travelTipId;
	}

	public void setTravelTipId(Long travelTipId)
	{
		this.travelTipId = travelTipId;
	}

	public List<TravelTipsModel> getTravelTipsModels()
	{
		return travelTipsModels;
	}

	public void setTravelTipsModels(List<TravelTipsModel> travelTipsModels)
	{
		this.travelTipsModels = travelTipsModels;
	}

	public List<PictureModel> getPictureModels()
	{
		return pictureModels;
	}

	public void setPictureModels(List<PictureModel> pictureModels)
	{
		this.pictureModels = pictureModels;
	}
}
