package com.risetek.auth.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Vector;

import com.google.inject.Singleton;
import com.risetek.auth.shared.UserResourceEntity;
import com.risetek.auth.shared.UserSecurityEntity;
/*
 * 基于hsqldb的用户及用户资源管理
 * 数据库初始化
 * security数据表 resources数据表
 */
@Singleton
public class DbManagement {
	private final String createSecurityTable = "CREATE TABLE IF NOT EXISTS security (" +
			" id IDENTITY," + 
			" name VARCHAR(40) NOT NULL UNIQUE," + 
			" passwd VARCHAR(60) NOT NULL," + 
			" email VARCHAR(100) NOT NULL," + 
			" notes VARCHAR(600)," + 
			");";
	
	private final String createResourceTable = "CREATE TABLE IF NOT EXISTS resource (" +
			" id IDENTITY," + 
			" securityId INT NOT NULL FOREIGN KEY REFERENCES security(id)," + 
			" appId INT NOT NULL," + 
			" key VARCHAR(30) NOT NULL," + 
			" value VARCHAR(600) NOT NULL," + 
			" UNIQUE(securityId, appId, key)," +
			");";

	private Connection connection;
	public DbManagement() {
		try {
			connection = DriverManager.getConnection("jdbc:hsqldb:file:/risetekauth/db", "SA", "");
			System.out.println("!!!!!!!!!! ---------------- connection successed ---------------- !!!!!!!!!!!!!!!!!!");
			connection.setAutoCommit(true);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void development_init_db() throws SQLException {
		int result;
		Statement stmt = connection.createStatement();
		
		result = stmt.executeUpdate("DROP TABLE IF EXISTS resource;");
		System.out.println("!!!!!!!!!! ---------------- drop resource table: " + result);
		result = stmt.executeUpdate("DROP TABLE IF EXISTS security;");
		System.out.println("!!!!!!!!!! ---------------- drop security table: " + result);

		result = stmt.executeUpdate(createSecurityTable);
		System.out.println("!!!!!!!!!! ---------------- create security table: " + result);
		result = stmt.executeUpdate(createResourceTable);
		System.out.println("!!!!!!!!!! ---------------- create resource table: " + result);

		stmt.close();
		
		test();
	}
	
	public void init_db() throws SQLException {
		Statement stmt = connection.createStatement();
		int result = stmt.executeUpdate(createSecurityTable);
		System.out.println("!!!!!!!!!! ---------------- create table: " + result);
		stmt.close();
	}

	public List<UserSecurityEntity> getAllUserSecurity(int offset, int limit) throws SQLException {
		List<UserSecurityEntity> securitys = new Vector<UserSecurityEntity>();
		String sql = "SELECT id, name, passwd, email, notes FROM security" + " OFFSET " + offset + " LIMIT " + limit + ";";
		//System.out.println("DEBUG:" + sql);
		Statement stmt = connection.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		while(rs.next()) {
			UserSecurityEntity security = new UserSecurityEntity();
			security.setId(rs.getInt("id"));
			security.setUsername(rs.getString("name"));
			security.setPasswd(rs.getString("passwd"));
			security.setEmail(rs.getString("email"));
			security.setNotes(rs.getString("notes"));
			securitys.add(security);
		}

		stmt.close();
		return securitys;
	}

	public void updateUserSecurity(UserSecurityEntity security) throws SQLException {
		String sql = "UPDATE security SET name = ?, passwd = ?, email = ?, notes = ? WHERE id=?;";
		// create the java mysql update preparedstatement
		PreparedStatement preparedStmt = connection.prepareStatement(sql);
		preparedStmt.setString(1, security.getUsername());
		preparedStmt.setString(2, security.getPasswd());
		preparedStmt.setString(3, security.getEmail());
		preparedStmt.setString(4, security.getNotes());
		preparedStmt.setInt(5, security.getId());
		// execute the java preparedstatement
		preparedStmt.executeUpdate();
		preparedStmt.close();
	}
	
	public void addUserSecurity(UserSecurityEntity security) throws SQLException {
		String sql = "INSERT INTO security (name, passwd, email, notes) VALUES(?,?,?,?);";
		// create the java mysql update preparedstatement
		PreparedStatement preparedStmt = connection.prepareStatement(sql);
		preparedStmt.setString(1, security.getUsername());
		preparedStmt.setString(2, security.getPasswd());
		preparedStmt.setString(3, security.getEmail());
		preparedStmt.setString(4, security.getNotes());
		// execute the java preparedstatement
		preparedStmt.executeUpdate();
		preparedStmt.close();
	}

	public void deleteUserSecurity(UserSecurityEntity security) throws SQLException {
		String sql = "DELETE FROM security WHERE id=" + security.getId() + ";";
		//System.out.println("DEBUG:" + sql);
		Statement stmt = connection.createStatement();
		stmt.execute(sql);
		stmt.close();
	}	
	
	public void addUserResource(UserResourceEntity resource) throws SQLException {
		String sql = "INSERT INTO resource (securityId, appId, key, value) VALUES(?,?,?,?);";
		// create the java mysql update preparedstatement
		PreparedStatement preparedStmt = connection.prepareStatement(sql);
		preparedStmt.setInt(1, resource.getSecurityId());
		preparedStmt.setInt(2, resource.getAppId());
		preparedStmt.setString(3, resource.getKey());
		preparedStmt.setString(4, resource.getValue());
		// execute the java preparedstatement
		preparedStmt.executeUpdate();
		preparedStmt.close();
	}
	
	public List<UserResourceEntity> getUserResouce(int keyid, int appid) throws SQLException {
		List<UserResourceEntity> resources = new Vector<UserResourceEntity>();
		String sql = "SELECT id, securityId, appId, key, value FROM resource WHERE securityId=" + keyid + " AND appId ="+ appid + ";";
		//System.out.println("DEBUG:" + sql);
		Statement stmt = connection.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		while(rs.next()) {
			UserResourceEntity resource = new UserResourceEntity();
			resource.setId(rs.getInt("id"));
			resource.setSecurityId(rs.getInt("securityId"));
			resource.setAppId(rs.getInt("appId"));
			resource.setKey(rs.getString("key"));
			resource.setValue(rs.getString("value"));
			resources.add(resource);
		}

		stmt.close();
		return resources;
	}
	
	private void test() throws SQLException {
		UserSecurityEntity user = new UserSecurityEntity();
		user.setUsername("wangyc2@risetek.com");
		user.setPasswd("risetek");
		user.setEmail("wangyc@risetek.com");
		user.setNotes("wangyuchun1969");
		
		addUserSecurity(user);
		
		List<UserSecurityEntity> securitys = getAllUserSecurity(0, 100);
		for(UserSecurityEntity u:securitys)
			System.out.println("[" + u.getId() + "]-->> user:" + u.getUsername() + "  passwd:" + u.getPasswd() + " notes:" + u.getNotes());
		
		
		UserResourceEntity resource = new UserResourceEntity();
		resource.setSecurityId(0);
		resource.setAppId(0);
		resource.setKey("roles");
		resource.setValue("admin:visitor");
		addUserResource(resource);
		
		resource = new UserResourceEntity();
		resource.setSecurityId(0);
		resource.setAppId(0);
		resource.setKey("teams");
		resource.setValue("17:22");
		addUserResource(resource);
	}
}
