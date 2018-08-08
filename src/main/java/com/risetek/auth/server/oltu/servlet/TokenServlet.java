package com.risetek.auth.server.oltu.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.oltu.oauth2.as.issuer.MD5Generator;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuer;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuerImpl;
import org.apache.oltu.oauth2.as.request.OAuthTokenRequest;
import org.apache.oltu.oauth2.as.response.OAuthASResponse;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.risetek.auth.server.UserManagement;
import com.risetek.auth.shared.OpenAuthInfo;


// PATH /oauth/token
@Singleton
public class TokenServlet extends HttpServlet {

	private static final long serialVersionUID = -6683598824382339693L;
	
	@Inject
	private UserManagement userManagement;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		OAuthIssuer oauthIssuerImpl = new OAuthIssuerImpl(new MD5Generator());

		try {
			OAuthTokenRequest oauthRequest = new OAuthTokenRequest(request);

			String authzCode = oauthRequest.getCode();
			// some code
			String accessToken = oauthIssuerImpl.accessToken();
			String refreshToken = oauthIssuerImpl.refreshToken();

			System.out.println("code is:" + authzCode);
			
			OpenAuthInfo info = userManagement.getInfoByToken(authzCode);
			if(null == info)
				throw OAuthProblemException.error("invalid client token");

			System.out.println("accessToken is:" + accessToken + " username:" + info.getUsername());

			userManagement.setInfoByAccessToken(accessToken, info);

			// some code
			OAuthResponse oauthResponse = OAuthASResponse.tokenResponse(HttpServletResponse.SC_OK).setAccessToken(accessToken)
					.setExpiresIn("3600").setRefreshToken(refreshToken).buildJSONMessage();

			response.setContentType("application/json");
			response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1
			response.setHeader("Pragma", "no-cache"); // HTTP 1.0
			response.setDateHeader("Expires", 0); // Proxies.
			
			response.setStatus(oauthResponse.getResponseStatus());
			PrintWriter pw = response.getWriter();
			pw.print(oauthResponse.getBody());
			pw.flush();
			pw.close();
			// if something goes wrong
		} catch (OAuthProblemException | OAuthSystemException ex) {
			OAuthProblemException pro = OAuthProblemException.error("somethign");
			OAuthResponse r;
			try {
				r = OAuthResponse.errorResponse(401).error(pro).buildJSONMessage();
				response.setStatus(r.getResponseStatus());

				PrintWriter pw = response.getWriter();
				pw.print(r.getBody());
				pw.flush();
				pw.close();

				response.sendError(401);
			} catch (OAuthSystemException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}
}
