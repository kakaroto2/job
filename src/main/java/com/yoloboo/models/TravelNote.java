package com.yoloboo.models;

/**
 * Created by maojiancai on 16/10/8.
 */
public class TravelNote {
    private Long travel_notes_id;

    private String tn_title;

    private String countryName;

    private String tn_date;

    private String tn_newtime;

    private String userName;

    private String  locationName;

    private String  topicName;

    private Integer can_publish;

    private String pushTime;

    //精选状态
    private Integer is_pushed;

    //精选状态String 类型
    private String str_pushed;

    private String str_ispushed;

    //设为精选
    private String of_set;

    private Integer tn_praise_num;

    private Integer tn_comments_num;

    //被收藏数
    private Integer collect_num;

    //类别
    private String themeName;

    private Integer tn_views_num;

    private Integer tn_isno_delete;

    private Long recommendNoteId1;

    private Long recommendNoteId2;

    private Long recommendNoteId3;

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    private Long user_id;

    private String tn_description;

    private Long country_id;

    private Long location_id;

    private Long themeId;

    private Long topic_id;

    public Long getThemeId() {
        return themeId;
    }

    public void setThemeId(Long themeId) {
        this.themeId = themeId;
    }

    public Long getTopic_id() {
        return topic_id;
    }

    public void setTopic_id(Long topic_id) {
        this.topic_id = topic_id;
    }

    public Long getCountry_id() {
        return country_id;
    }

    public void setCountry_id(Long country_id) {
        this.country_id = country_id;
    }

    public Long getLocation_id() {
        return location_id;
    }

    public void setLocation_id(Long location_id) {
        this.location_id = location_id;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public Long getTravel_notes_id() {
        return travel_notes_id;
    }

    public void setTravel_notes_id(Long travel_notes_id) {
        this.travel_notes_id = travel_notes_id;
    }

    public String getTn_title() {
        return tn_title;
    }

    public void setTn_title(String tn_title) {
        this.tn_title = tn_title;
    }

    public String getTn_date() {
        return tn_date;
    }

    public void setTn_date(String tn_date) {
        this.tn_date = tn_date;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public Integer getCan_publish() {
        return can_publish;
    }

    public void setCan_publish(Integer can_publish) {
        this.can_publish = can_publish;
    }

    public Integer getIs_pushed() {
        return is_pushed;
    }

    public void setIs_pushed(Integer is_pushed) {
        this.is_pushed = is_pushed;
    }

    public Integer getTn_praise_num() {
        return tn_praise_num;
    }

    public void setTn_praise_num(Integer tn_praise_num) {
        this.tn_praise_num = tn_praise_num;
    }

    public Integer getTn_comments_num() {
        return tn_comments_num;
    }

    public void setTn_comments_num(Integer tn_comments_num) {
        this.tn_comments_num = tn_comments_num;
    }

    public Integer getCollect_num() {
        return collect_num;
    }

    public void setCollect_num(Integer collect_num) {
        this.collect_num = collect_num;
    }

    public String getThemeName() {
        return themeName;
    }

    public void setThemeName(String themeName) {
        this.themeName = themeName;
    }

    public Integer getTn_views_num() {
        return tn_views_num;
    }

    public void setTn_views_num(Integer tn_views_num) {
        this.tn_views_num = tn_views_num;
    }

    public String getStr_pushed() {
        return str_pushed;
    }

    public void setStr_pushed(String str_pushed) {
        this.str_pushed = str_pushed;
    }

    public String getOf_set() {
        return of_set;
    }

    public void setOf_set(String of_set) {
        this.of_set = of_set;
    }

    public String getStr_ispushed() {
        return str_ispushed;
    }

    public void setStr_ispushed(String str_ispushed) {
        this.str_ispushed = str_ispushed;
    }

    public Integer getTn_isno_delete() {
        return tn_isno_delete;
    }

    public void setTn_isno_delete(Integer tn_isno_delete) {
        this.tn_isno_delete = tn_isno_delete;
    }

    public String getTn_newtime() {
        return tn_newtime;
    }

    public void setTn_newtime(String tn_newtime) {
        this.tn_newtime = tn_newtime;
    }

    public Long getRecommendNoteId1() {
        return recommendNoteId1;
    }

    public void setRecommendNoteId1(Long recommendNoteId1) {
        this.recommendNoteId1 = recommendNoteId1;
    }

    public Long getRecommendNoteId2() {
        return recommendNoteId2;
    }

    public void setRecommendNoteId2(Long recommendNoteId2) {
        this.recommendNoteId2 = recommendNoteId2;
    }

    public Long getRecommendNoteId3() {
        return recommendNoteId3;
    }

    public void setRecommendNoteId3(Long recommendNoteId3) {
        this.recommendNoteId3 = recommendNoteId3;
    }

    public String getTn_description() {
        return tn_description;
    }

    public void setTn_description(String tn_description) {
        this.tn_description = tn_description;
    }

    public String getPushTime() {
        return pushTime;
    }

    public void setPushTime(String pushTime) {
        this.pushTime = pushTime;
    }
}
