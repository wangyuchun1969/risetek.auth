package com.risetek.auth.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class UserSecurity implements IsSerializable {
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPasswd() {
		return passwd;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	private int id;
	private String username;
	private String passwd;
	private String notes;
}
