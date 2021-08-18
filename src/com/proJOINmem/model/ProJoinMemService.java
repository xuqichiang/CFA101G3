package com.proJOINmem.model;

public class ProJoinMemService {

	ProJoinMemDAOimpl dao = new ProJoinMemDAOimpl();
	
	public ProJoinMemVO findByPid(Integer pro_id) {
		return dao.findByPid(pro_id);
		
	}
}
