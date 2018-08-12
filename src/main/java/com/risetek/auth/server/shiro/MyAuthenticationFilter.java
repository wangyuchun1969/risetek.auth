package com.risetek.auth.server.shiro;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.web.filter.authc.UserFilter;
import org.apache.shiro.web.util.WebUtils;

public class MyAuthenticationFilter extends UserFilter {
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        WebUtils.toHttp(response).setStatus(HttpServletResponse.SC_SEE_OTHER);
        return false;
    }
}
