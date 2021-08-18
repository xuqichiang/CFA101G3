package com.admin.model;

public class AdmService {
	AdmDAOImpl dao = new AdmDAOImpl();
	public AdmVO findByUsernameAndPassword(String username,String password) {
		return dao.findByUsernameAndPassword(username, password);
	};
}
