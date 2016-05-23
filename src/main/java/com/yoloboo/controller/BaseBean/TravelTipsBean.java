package com.yoloboo.controller.BaseBean;

/**
 * Created by huhaosumail on 16/5/9.
 */
public class TravelTipsBean  extends PagingBean{
    private Long travelTipsId;
    private Long userId;
    private Long countryOrLocationId;
    private Integer type;

    public Long getTravelTipsId() {
        return travelTipsId;
    }

    public void setTravelTipsId(Long travelTipsId) {
        this.travelTipsId = travelTipsId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getCountryOrLocationId() {
        return countryOrLocationId;
    }

    public void setCountryOrLocationId(Long countryOrLocationId) {
        this.countryOrLocationId = countryOrLocationId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
