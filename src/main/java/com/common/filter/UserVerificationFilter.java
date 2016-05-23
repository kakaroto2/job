package com.common.filter;

import com.common.Json;
import com.common.UserVerifcationHelper;
import com.common.constans.SystemCodeContent;
import com.json.BaseBean;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;


/**
 * Created by ZHOU005 on 2016/1/12.
 */
public class UserVerificationFilter implements Filter
{
	private String[] igonreUrl;

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException
	{
		HttpServletRequest request = ((HttpServletRequest) servletRequest);

		if(!isIgonreUrl(request.getRequestURL().toString()))
		{
			String uToken = servletRequest.getParameter("uToken");
			String userId = servletRequest.getParameter("userId");

			if (null != userId && null != uToken)
			{
				if (!UserVerifcationHelper.isAccredited(userId, uToken, request))
				{
					servletResponse.getWriter().print(getForbiddenDis());
					return;
				}
			}
		}

		filterChain.doFilter(servletRequest, servletResponse);
	}

	private boolean isIgonreUrl(String url)
	{
		String urlMapping = getUrlMappingPart(url);

		for (int i = 0; i < igonreUrl.length; i++)
		{
			if (urlMapping.equals(igonreUrl[i]))
			{
				return true;
			}
		}

		return false;
	}

	private String getUrlMappingPart(String url)
	{
		String urlPart1 = url.substring(0, url.lastIndexOf("/"));

		return urlPart1.substring(urlPart1.lastIndexOf("/")+1, urlPart1.length());
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException
	{
		igonreUrl = filterConfig.getInitParameter("ignoreUrl").split(",");
	}

	@Override
	public void destroy()
	{
	}

	private String getForbiddenDis()
	{
		BaseBean bean = new BaseBean();

		bean.setMsg("User Token Verify Failed !");
		bean.setStatus(SystemCodeContent.FORBIDDEN_CODE);

		return Json.toString(bean);
	}

}
