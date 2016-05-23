package com.yoloboo.controller.BaseBean;

/**
 * Created by huhaosumail on 16/5/9.
 */
public class TopicBean  extends UserBean{
    private Long id;
    private Long themeId;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getThemeId()
    {
        return themeId;
    }

    public void setThemeId(Long themeId)
    {
        this.themeId = themeId;
    }
}
