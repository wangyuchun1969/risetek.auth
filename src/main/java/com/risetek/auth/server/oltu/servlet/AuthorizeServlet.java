package com.risetek.auth.server.oltu.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.oltu.oauth2.as.issuer.MD5Generator;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuerImpl;
import org.apache.oltu.oauth2.as.request.OAuthAuthzRequest;
import org.apache.oltu.oauth2.as.response.OAuthASResponse;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.risetek.auth.server.UserManagement;


// PATH /oauth/authorize
@Singleton
public class AuthorizeServlet extends HttpServlet {

	private static final long serialVersionUID = -1969643036046255438L;

	@Inject
	private UserManagement userManagement;
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String username = request.getParameter("username");
		String passwd = request.getParameter("passwd");
		System.out.println("username: " + username + " passwd:" + passwd);

		if(null==username || null == passwd || !userManagement.isValid(username, passwd.toCharArray())) {
			OAuthProblemException ex = OAuthProblemException.error("missing/wrong username or passwd");
			
			// TODO: 这个地方有问题，还没时间调试。
			try {
				OAuthResponse resp = OAuthASResponse.errorResponse(HttpServletResponse.SC_FOUND).error(ex)
				.location(ex.getRedirectUri()).buildQueryMessage();
				response.sendRedirect(resp.getLocationUri());
			} catch (OAuthSystemException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return;
		}

		try {
			try {
				// dynamically recognize an OAuth profile based on request
				// characteristic (params, method, content type etc.), perform
				// validation
				OAuthAuthzRequest oauthRequest = new OAuthAuthzRequest(request);
				OAuthIssuerImpl oauthIssuerImpl = new OAuthIssuerImpl(new MD5Generator());
				// validateRedirectionURI(oauthRequest)
				String token = oauthIssuerImpl.authorizationCode();
				userManagement.setToken(username, token);
				
				// build OAuth response
				OAuthResponse resp = OAuthASResponse.authorizationResponse(request, HttpServletResponse.SC_FOUND)
						.setCode(token).location(oauthRequest.getRedirectURI())
						.buildQueryMessage();

				response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1
				response.setHeader("Pragma", "no-cache"); // HTTP 1.0
				response.setDateHeader("Expires", 0); // Proxies.
				
				response.sendRedirect(resp.getLocationUri());
				// if something goes wrong
			} catch (OAuthProblemException ex) {
				OAuthResponse resp = OAuthASResponse.errorResponse(HttpServletResponse.SC_FOUND).error(ex)
						.location(ex.getRedirectUri()).buildQueryMessage();
				response.sendRedirect(resp.getLocationUri());
			}
		} catch (OAuthSystemException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		System.out.println("call authorize post");
		doGet(request, response);
	}
}
