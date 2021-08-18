package com.locpage.utils;

import java.util.ArrayList;
import java.util.List;

public class PageVO {
	
	private Integer totalCount; //全部訂單數
	private Integer totalPage; //總共幾頁
	private Integer currentPage; //當前頁數
	private Integer rows; //每頁顯示幾筆訂單
	private List list; //裝當頁的訂單
	
	public Integer getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}
	public Integer getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}
	public Integer getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}
	public Integer getRows() {
		return rows;
	}
	public void setRows(Integer rows) {
		this.rows = rows;
	}
	public List getList() {
		return list;
	}
	public void setList(List list) {
		this.list = list;
	}
	@Override
	public String toString() {
		return "PageVO [totalCount=" + totalCount + ", totalPage=" + totalPage + ", currentPage=" + currentPage
				+ ", rows=" + rows + ", list=" + list + "]";
	}
	
	
}
