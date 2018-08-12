package com.risetek.auth.server.oltu.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.apache.oltu.oauth2.common.message.types.ParameterStyle;
import org.apache.oltu.oauth2.jwt.JWT;
import org.apache.oltu.oauth2.jwt.io.JWTWriter;
import org.apache.oltu.oauth2.rs.request.OAuthAccessResourceRequest;
import org.apache.oltu.oauth2.rs.response.OAuthRSResponse;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.risetek.auth.server.DbManagement;
import com.risetek.auth.server.UserManagement;
import com.risetek.auth.shared.OpenAuthInfo;
import com.risetek.auth.shared.UserResourceEntity;

//PATH /oauth/user
@Singleton
@SuppressWarnings("serial")
public class UserResourceServlet extends HttpServlet {

	@Inject
	private UserManagement userManagement;

	@Inject
	private DbManagement dbManagement;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			OAuthAccessResourceRequest oauthRequest = new OAuthAccessResourceRequest(request, ParameterStyle.QUERY);
			String accessToken = oauthRequest.getAccessToken();
			OpenAuthInfo info = userManagement.getInfoByAccessToken(accessToken);
			if(null == info)
				throw OAuthProblemException.error("invalid client token");
				
			String username = info.getUsername();
				
			JWT.Builder builder = new JWT.Builder();
			builder.setHeaderAlgorithm("RS256");
			builder.setHeaderType("JWT");
			builder.setHeaderContentType("JWT");
			builder.setClaimsSetIssuer("accounts.yun74.com");
			builder.setClaimsSetJwdId("UUID");

			builder.setClaimsSetIssuedAt(new Date().getTime());
			builder.setClaimsSetExpirationTime(new Date().getTime() + 1000 * 60 * 60 * 24 * 5);

			builder.setClaimsSetSubject(username);

			try {
				List<UserResourceEntity> list = dbManagement.getUserResourceByName(username, info.getClient_id());
				for(UserResourceEntity entity:list)
					builder.setClaimsSetCustomField(entity.getKey(), entity.getValue());
				
			} catch (SQLException e) {
				throw new OAuthSystemException("database failed: " + e.getMessage());
			}
			
			builder.setSignature(info.getClient_id());
			
			JWTWriter writer = new JWTWriter();
			String jwts = writer.write(builder.build());
			response.setStatus(HttpServletResponse.SC_OK);

			PrintWriter pw = response.getWriter();
			pw.print(jwts);
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
