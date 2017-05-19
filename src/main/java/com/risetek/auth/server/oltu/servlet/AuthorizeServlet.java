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

import com.google.inject.Singleton;

@Singleton
public class AuthorizeServlet extends HttpServlet {

	private static final long serialVersionUID = -1969643036046255438L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			try {
				// dynamically recognize an OAuth profile based on request
				// characteristic (params, method, content type etc.), perform
				// validation
				OAuthAuthzRequest oauthRequest = new OAuthAuthzRequest(request);
				OAuthIssuerImpl oauthIssuerImpl = new OAuthIssuerImpl(new MD5Generator());
				// validateRedirectionURI(oauthRequest)

				// build OAuth response
				OAuthResponse resp = OAuthASResponse.authorizationResponse(request, HttpServletResponse.SC_FOUND)
						.setCode(oauthIssuerImpl.authorizationCode()).location(oauthRequest.getRedirectURI())
						.buildQueryMessage();

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
}
