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
import com.risetek.auth.shared.UserSecurityEntity;
/*
 * 基于hsqldb的用户及用户资源管理
 * 数据库初始化
 * security数据表 resources数据表
 */
@Singleton
public class DbManagement {
	private final String createTable = "CREATE TABLE IF NOT EXISTS security (" +
			" id IDENTITY," + 
			" name VARCHAR(40) NOT NULL UNIQUE," + 
			" passwd VARCHAR(60) NOT NULL," + 
			" notes VARCHAR(600)," + 
			");";
	
	private Connection c;
	public DbManagement() {
		
		try {
			c = DriverManager.getConnection("jdbc:hsqldb:file:/risetek/userdb", "SA", "");
			System.out.println("!!!!!!!!!! ---------------- connection successed ---------------- !!!!!!!!!!!!!!!!!!");
			c.setAutoCommit(true);
			
			test();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void init_db() throws SQLException {
		Statement stmt = c.createStatement();
		int result = stmt.executeUpdate(createTable);
		System.out.println("!!!!!!!!!! ---------------- create table: " + result);
		stmt.close();
	}

	public List<UserSecurityEntity> getAllUserSecurity() throws SQLException {
		List<UserSecurityEntity> securitys = new Vector<UserSecurityEntity>();
		String sql = "SELECT id, name, passwd, notes FROM security;";
		Statement stmt = c.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		while(rs.next()) {
			UserSecurityEntity security = new UserSecurityEntity();
			security.setId(rs.getInt("id"));
			security.setUsername(rs.getString("name"));
			security.setPasswd(rs.getString("passwd"));
			security.setNotes(rs.getString("notes"));
			securitys.add(security);
		}

		stmt.close();
		return securitys;
	}
	public void addUserSecurity(UserSecurityEntity security) throws SQLException {
		String sql = "INSERT INTO security (name,passwd,notes) VALUES(?,?,?);";
		// create the java mysql update preparedstatement
		PreparedStatement preparedStmt = c.prepareStatement(sql);
		preparedStmt.setString(1, security.getUsername());
		preparedStmt.setString(2, security.getPasswd());
		preparedStmt.setString(3, security.getNotes());
		// execute the java preparedstatement
		preparedStmt.executeUpdate();
		preparedStmt.close();
	}
	
	
	private void test() {
		try {
			init_db();
			
			UserSecurityEntity user = new UserSecurityEntity();
			user.setUsername("wangyc2@risetek.com");
			user.setPasswd("risetek");
			user.setNotes("wangyuchun1969");
			
			// addUserSecurity(user);
			
			List<UserSecurityEntity> securitys = getAllUserSecurity();
			for(UserSecurityEntity u:securitys)
				System.out.println("[" + u.getId() + "]-->> user:" + u.getUsername() + "  passwd:" + u.getPasswd() + " notes:" + u.getNotes());
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
