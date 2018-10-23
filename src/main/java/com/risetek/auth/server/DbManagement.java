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
import com.risetek.auth.shared.AppEntity;
import com.risetek.auth.shared.UserResourceEntity;
import com.risetek.auth.shared.UserSecurityEntity;
/*
 * 基于hsqldb的用户及用户资源管理
 * 数据库初始化
 * security数据表 resources数据表 application数据表
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
			" application VARCHAR(30) NOT NULL," + 
			" key VARCHAR(30) NOT NULL," + 
			" value VARCHAR(600) NOT NULL," + 
			" UNIQUE(securityId, application, key)," +
			");";
	private final String createApplicationTable = "CREATE TABLE IF NOT EXISTS application (" +
			" id IDENTITY," +
			" name VARCHAR(30) NOT NULL UNIQUE," +
			" notes VARCHAR(600)," +
			");";
	private Connection connection;
	public DbManagement() {
		try {
			Class.forName("org.hsqldb.jdbc.JDBCDriver");
			connection = DriverManager.getConnection("jdbc:hsqldb:file:/risetekauth/db; shutdown=true", "SA", "");
			System.out.println("!!!!!!!!!! ---------------- connection successed ---------------- !!!!!!!!!!!!!!!!!!");
			connection.setAutoCommit(true);
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void closeConnection() {
		try {
	        connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			connection = null;
		}
	}
	
	public void development_init_db() throws SQLException {
		int result;
		Statement stmt = connection.createStatement();
		
		result = stmt.executeUpdate("DROP TABLE IF EXISTS resource;");
		System.out.println("!!!!!!!!!! ---------------- drop resource table: " + result);
		result = stmt.executeUpdate("DROP TABLE IF EXISTS security;");
		System.out.println("!!!!!!!!!! ---------------- drop security table: " + result);
		result = stmt.executeUpdate("DROP TABLE IF EXISTS application;");
		System.out.println("!!!!!!!!!! ---------------- drop application table: " + result);
		result = stmt.executeUpdate(createSecurityTable);
		System.out.println("!!!!!!!!!! ---------------- create security table: " + result);
		result = stmt.executeUpdate(createResourceTable);
		System.out.println("!!!!!!!!!! ---------------- create resource table: " + result);
		result = stmt.executeUpdate(createApplicationTable);
		System.out.println("!!!!!!!!!! ---------------- create application table: " + result);
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

	public List<UserSecurityEntity> getUserSecurity(String name) throws SQLException {
		List<UserSecurityEntity> securitys = new Vector<UserSecurityEntity>();
		String sql = "SELECT id, name, passwd, email, notes FROM security WHERE name = '" + name + "';";
		System.out.println("DEBUG:" + sql);
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
	
	public void addUserResourceIndirctor(UserResourceEntity resource) throws SQLException {
		String sql = "INSERT INTO resource (securityId, application, key, value) VALUES((SELECT id FROM security WHERE name = ?),?,?,?);";
		
		// create the java mysql update preparedstatement
		PreparedStatement preparedStmt = connection.prepareStatement(sql);
		preparedStmt.setString(1, resource.getUsername());
		preparedStmt.setString(2, resource.getApplication());
		preparedStmt.setString(3, resource.getKey());
		preparedStmt.setString(4, resource.getValue());
		// execute the java preparedstatement
		preparedStmt.executeUpdate();
		preparedStmt.close();
	}
	
	public void deleteUserResource(UserResourceEntity resource) throws SQLException {
		String sql = "DELETE FROM resource WHERE id=" + resource.getId() + ";";
		//System.out.println("DEBUG:" + sql);
		Statement stmt = connection.createStatement();
		stmt.execute(sql);
		stmt.close();
	}
	
	public void updateUserResource(UserResourceEntity resource) throws SQLException {
		String sql = "UPDATE resource SET key = ?, value = ? WHERE id=?;";
		// create the java mysql update preparedstatement
		PreparedStatement preparedStmt = connection.prepareStatement(sql);
		preparedStmt.setString(1, resource.getKey());
		preparedStmt.setString(2, resource.getValue());
		preparedStmt.setInt(3, resource.getId());
		// execute the java preparedstatement
		preparedStmt.executeUpdate();
		preparedStmt.close();
	}

	public List<UserResourceEntity> getUserFullResource() throws SQLException {
		List<UserResourceEntity> resources = new Vector<UserResourceEntity>();
		String sql = "SELECT R.id, S.name AS name, R.application, R.key, R.value FROM resource R, security S WHERE R.securityId=S.id;";
		// System.out.println("DEBUG:" + sql);
		Statement stmt = connection.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		while(rs.next()) {
			UserResourceEntity resource = new UserResourceEntity();
			resource.setId(rs.getInt("id"));
			resource.setUsername(rs.getString("name"));
			resource.setApplication(rs.getString("application"));
			resource.setKey(rs.getString("key"));
			resource.setValue(rs.getString("value"));
			resources.add(resource);
		}

		stmt.close();
		return resources;
	}

	public List<UserResourceEntity> getUserResourceByName(String username, String appname) throws SQLException {
		List<UserResourceEntity> resources = new Vector<UserResourceEntity>();
		String sql = "SELECT id, securityId, application, key, value FROM resource WHERE securityId IN (SELECT id FROM security WHERE name = '"+ username + "') AND application = '" + appname + "';";
		System.out.println("DEBUG:" + sql);
		Statement stmt = connection.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		while(rs.next()) {
			UserResourceEntity resource = new UserResourceEntity();
			resource.setId(rs.getInt("id"));
			resource.setUsername(username);
			resource.setApplication(rs.getString("application"));
			resource.setKey(rs.getString("key"));
			resource.setValue(rs.getString("value"));
			resources.add(resource);
		}

		stmt.close();
		return resources;
	}
	//-----------------
	public List<AppEntity> getAllApplications(int offset, int limit) throws SQLException {
		List<AppEntity> apps = new Vector<AppEntity>();
		String sql = "SELECT id, name, notes FROM application" + " OFFSET " + offset + " LIMIT " + limit + ";";
		//System.out.println("DEBUG:" + sql);
		Statement stmt = connection.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		while(rs.next()) {
			AppEntity app = new AppEntity();
			app.setId(rs.getInt("id"));
			app.setName(rs.getString("name"));
			app.setNotes(rs.getString("notes"));
			apps.add(app);
		}

		stmt.close();
		return apps;
	}

	public List<AppEntity> getApplication(String name) throws SQLException {
		List<AppEntity> apps = new Vector<AppEntity>();
		String sql = "SELECT id, name, notes FROM application WHERE name = '" + name + "';";
		System.out.println("DEBUG:" + sql);
		Statement stmt = connection.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		while(rs.next()) {
			AppEntity app = new AppEntity();
			app.setId(rs.getInt("id"));
			app.setName(rs.getString("name"));
			app.setNotes(rs.getString("notes"));
			apps.add(app);
		}

		stmt.close();
		return apps;
	}
	
	public void updateApplication(AppEntity app) throws SQLException {
		String sql = "UPDATE application SET name = ?, notes = ? WHERE id=?;";
		// create the java mysql update preparedstatement
		PreparedStatement preparedStmt = connection.prepareStatement(sql);
		preparedStmt.setString(1, app.getName());
		preparedStmt.setString(2, app.getNotes());
		preparedStmt.setInt(3, app.getId());
		// execute the java preparedstatement
		preparedStmt.executeUpdate();
		preparedStmt.close();
	}
	
	public void addApplication(AppEntity app) throws SQLException {
		String sql = "INSERT INTO application (name, notes) VALUES(?,?);";
		// create the java mysql update preparedstatement
		PreparedStatement preparedStmt = connection.prepareStatement(sql);
		preparedStmt.setString(1, app.getName());
		preparedStmt.setString(2, app.getNotes());
		// execute the java preparedstatement
		preparedStmt.executeUpdate();
		preparedStmt.close();
	}

	public void deleteApplication(AppEntity app) throws SQLException {
		String sql = "DELETE FROM application WHERE id=" + app.getId() + ";";
		//System.out.println("DEBUG:" + sql);
		Statement stmt = connection.createStatement();
		stmt.execute(sql);
		stmt.close();
	}	
	//-------------------
	private void test() throws SQLException {
		users_init();
		/*
		List<UserSecurityEntity> securitys = getAllUserSecurity(0, 100);
		for(UserSecurityEntity u:securitys)
			System.out.println("[" + u.getId() + "]-->> user:" + u.getUsername() + "  passwd:" + u.getPasswd() + " notes:" + u.getNotes());
		*/
	}

	private void add_one_user(String name, String passwd, String email, String notes) throws SQLException {
		UserSecurityEntity user = new UserSecurityEntity();
		user.setUsername(name);
		user.setPasswd(passwd);
		user.setEmail(email);
		user.setNotes(notes);
		addUserSecurity(user);
	}
	
	private void add_resource(String name, String app, String key, String value) throws SQLException {
		UserResourceEntity resource = new UserResourceEntity();
		resource.setUsername(name);
		resource.setApplication(app);
		resource.setKey(key);
		resource.setValue(value);
		addUserResourceIndirctor(resource);
	}

	private void add_one_app(String name, String notes) throws SQLException {
		AppEntity app = new AppEntity();
		app.setName(name);
		app.setNotes(notes);
		addApplication(app);
	}
	private void users_init() throws SQLException {
		add_one_user("wangyc@risetek.com", "gamelan", "wangyc@risetek.com", "wangyc@risetek.com");
		add_resource("wangyc@risetek.com", "risetek-yun74-id", "roles", "admin:developer:maintenance:operator:visitor");
		add_resource("wangyc@risetek.com", "risetek-yun74-id", "teams", "-1");

		add_one_user("wangyc", "gamelan", "wangyc@risetek.com", "wangyc");
		add_resource("wangyc", "risetek-yun74-id", "roles", "visitor");
		add_resource("wangyc", "risetek-yun74-id", "teams", "0");
		
		add_one_user("test@risetek.com", "test", "wangyc@risetek.com", "wangyc");
		add_resource("test@risetek.com", "risetek-yun74-id", "roles", "visitor");
		add_resource("test@risetek.com", "risetek-yun74-id", "teams", "0");

		add_one_user("szw@risetek.com", "szw", "wangyc@risetek.com", "wangyc");
		add_resource("szw@risetek.com", "risetek-yun74-id", "roles", "visitor");
		add_resource("szw@risetek.com", "risetek-yun74-id", "teams", "17");
		
		add_one_user("sdy@risetek.com", "sdy", "wangyc@risetek.com", "wangyc");
		add_resource("sdy@risetek.com", "risetek-yun74-id", "roles", "visitor");
		add_resource("sdy@risetek.com", "risetek-yun74-id", "teams", "13");

		add_one_user("zhangl@risetek.com", "zhangl", "zhangl@risetek.com", "zhangl@risetek.com");
		add_resource("zhangl@risetek.com", "risetek-yun74-id", "roles", "admin:developer:maintenance:operator:visitor");
		add_resource("zhangl@risetek.com", "risetek-yun74-id", "teams", "-1");
		
		add_one_user("wangxu@risetek.com", "wangxu", "wangxu@risetek.com", "wangxu@risetek.com");
		add_resource("wangxu@risetek.com", "risetek-yun74-id", "roles", "admin:developer:maintenance:operator:visitor");
		add_resource("wangxu@risetek.com", "risetek-yun74-id", "teams", "-1");
		
		//China ComService
		add_one_user("wangp@ccs", "wangp", "wangp@ccs", "wangp@ccs");
		add_resource("wangp@ccs", "risetek-yun74-id", "roles", "admin:developer:maintenance:operator:visitor");
		add_resource("wangp@ccs", "risetek-yun74-id", "teams", "-1");
		add_one_app("risetek-yun74-id", "risetek yun74 service");
		getUserResourceByName("wangyc@risetek.com", "wangyc@risetek.com");
	}

}
