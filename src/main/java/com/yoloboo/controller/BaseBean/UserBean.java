package com.yoloboo.controller.BaseBean;

/**
 * Created by huhaosumail on 16/5/9.
 */
public class UserBean extends PagingBean{
    private Long userId;
    private Long themeId;
    private int registerType;
    private String phone;
    private String threeId;
    private String password;
    private String qiniukey;
    private String headImgUrl;
    private String nickname;
    private String code;
    private String countryCode;
    private String travelStyle;
    private String language;
    //新加的用户描述
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Long getUserId()
    {
        return userId;
    }

    public void setUserId(Long userId)
    {
        this.userId = userId;
    }

    public Long getThemeId()
    {
        return themeId;
    }

    public void setThemeId(Long themeId)
    {
        this.themeId = themeId;
    }

    public int getRegisterType()
    {
        return registerType;
    }

    public void setRegisterType(int registerType)
    {
        this.registerType = registerType;
    }

    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public String getThreeId()
    {
        return threeId;
    }

    public void setThreeId(String threeId)
    {
        this.threeId = threeId;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getQiniukey()
    {
        return qiniukey;
    }

    public void setQiniukey(String qiniukey)
    {
        this.qiniukey = qiniukey;
    }

    public String getHeadImgUrl()
    {
        return headImgUrl;
    }

    public void setHeadImgUrl(String headImgUrl)
    {
        this.headImgUrl = headImgUrl;
    }

    public String getNickname()
    {
        return nickname;
    }

    public void setNickname(String nickname)
    {
        this.nickname = nickname;
    }

    public String getCode()
    {
        return code;
    }

    public void setCode(String code)
    {
        this.code = code;
    }

    public String getCountryCode()
    {
        return countryCode;
    }

    public void setCountryCode(String countryCode)
    {
        this.countryCode = countryCode;
    }

    public String getTravelStyle()
    {
        return travelStyle;
    }

    public void setTravelStyle(String travelStyle)
    {
        this.travelStyle = travelStyle;
    }
}
