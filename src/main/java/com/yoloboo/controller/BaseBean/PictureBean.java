package com.yoloboo.controller.BaseBean;

/**
 * Created by huhaosumail on 16/5/9.
 */
public class PictureBean extends PagingBean {
    private Long countryTipId;
    private Long travelTipId;
    private Long countryId;
    private Long locationId;
    private Long userId;

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

    public Long getUserId()
    {
        return userId;
    }

    public void setUserId(Long userId)
    {
        this.userId = userId;
    }
}
