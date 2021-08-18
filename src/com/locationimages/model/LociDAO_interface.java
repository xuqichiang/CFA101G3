package com.locationimages.model;

import java.util.*;

public interface LociDAO_interface {

	public void insert(LociVO LociVO);
	public void update(LociVO LociVO);
	public void delete(Integer LOCI_LOCR_ID);
	public void deleteById(Integer LOCI_ID);
	
	public LociVO findByPrimaryKey(Integer LOCI_ID);
	public List<LociVO> getAll() ;
	public List<LociVO> findByForeignKey(Integer LOCI_LOCR_ID) ;

	
}
