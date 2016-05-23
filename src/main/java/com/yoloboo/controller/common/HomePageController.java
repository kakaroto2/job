package com.yoloboo.controller.common;

import com.common.UserVerifcationHelper;
import com.common.constans.SystemCodeContent;
import com.json.BaseBean;
import com.yoloboo.controller.BaseController;
import com.yoloboo.dao.HomeCmsDao;
import com.yoloboo.service.common.CmsContentService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;


/**
 * Created by ZHOU005 on 2016/1/16.
 */

@Controller
@RequestMapping("/homePage")
public class HomePageController extends BaseController
{
	@Resource
	private CmsContentService cmsContentService;

	@RequestMapping(value = "content")
	@ResponseBody
	public String getHomePage(@RequestParam(required = true) String uToken, @RequestParam(required = true) String userId)
	{
		BaseBean bean = new BaseBean();

		try
		{
			bean.setStatus(SystemCodeContent.SUCCESS_CODE);
			bean.setRows(cmsContentService.getHomePageContent());
		}
		catch (Exception e)
		{
			e.printStackTrace();
			bean.setStatus(SystemCodeContent.COMMON_ERROR_CODE);
		}

		return json(bean);
	}

}
