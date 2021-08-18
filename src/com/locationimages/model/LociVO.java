package com.locationimages.model;


public class LociVO  implements java.io.Serializable{
	private Integer LOCI_ID;
	private Integer LOCI_LOCR_ID;
	private byte[] LOCI_IMAGES;
	
	
	
	public Integer getLOCI_ID() {
		return LOCI_ID;
	}
	public void setLOCI_ID(Integer lOCI_ID) {
		this.LOCI_ID = lOCI_ID;
	}
	public Integer getLOCI_LOCR_ID() {
		return LOCI_LOCR_ID;
	}
	public void setLOCI_LOCR_ID(Integer lOCI_LOCR_ID) {
		this.LOCI_LOCR_ID = lOCI_LOCR_ID;
	}
	public byte[] getLOCI_IMAGES() {
		return LOCI_IMAGES;
	}
	public void setLOCI_IMAGES(byte[] lOCI_IMAGES) {
		this.LOCI_IMAGES = lOCI_IMAGES;
	}
	
	
	
	
	
	
}
