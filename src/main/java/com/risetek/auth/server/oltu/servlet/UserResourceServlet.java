package com.risetek.auth.server.oltu.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.apache.oltu.oauth2.common.message.types.ParameterStyle;
import org.apache.oltu.oauth2.rs.request.OAuthAccessResourceRequest;
import org.apache.oltu.oauth2.rs.response.OAuthRSResponse;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.risetek.auth.server.UserManagement;

//PATH /oauth/user
@Singleton
@SuppressWarnings("serial")
public class UserResourceServlet extends HttpServlet {

	@Inject
	private UserManagement userManagement;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			OAuthAccessResourceRequest oauthRequest = new OAuthAccessResourceRequest(request, ParameterStyle.QUERY);
			String accessToken = oauthRequest.getAccessToken();
			String username = userManagement.getUsernameByAccessToken(accessToken);
			if(null == username)
				throw OAuthProblemException.error("invalid client token");
				
			response.setStatus(HttpServletResponse.SC_OK);
			PrintWriter pw = response.getWriter();
			pw.print(username);
			pw.flush();
			pw.close();
		} catch (OAuthSystemException | OAuthProblemException e) {
			e.printStackTrace();

			// build error response
			try {
				OAuthResponse oauthResponse = OAuthRSResponse.errorResponse(HttpServletResponse.SC_UNAUTHORIZED)
						.setRealm("account ristek").buildHeaderMessage();
				response.addHeader(OAuth.HeaderType.WWW_AUTHENTICATE, oauthResponse.getHeader(OAuth.HeaderType.WWW_AUTHENTICATE));
			} catch (OAuthSystemException e1) {
				e1.printStackTrace();
			}
		}
	}
}
