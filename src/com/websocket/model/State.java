package com.websocket.model;

import java.util.Map;
import java.util.Set;

public class State {
	private String type;
	// the user changing the state
	private String user;
	// total users
	private Set<String> users;
	
	private Map<String,Long> unread;

	public State(String type, String user, Set<String> users, Map<String, Long> unread) {
		super();
		this.type = type;
		this.user = user;
		this.users = users;
		this.unread = unread;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public Set<String> getUsers() {
		return users;
	}

	public void setUsers(Set<String> users) {
		this.users = users;
	}

	public Map<String, Long> getUnread() {
		return unread;
	}

	public void setUnread(Map<String, Long> unread) {
		this.unread = unread;
	}
}
