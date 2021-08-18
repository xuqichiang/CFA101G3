package com.post.model;
import java.util.List;


public class PostPageVO implements java.io.Serializable{
	private Integer totalCount; //全部文章數
	private Integer totalPage; //總共幾頁
	private Integer currentPage; //當前頁數
	private Integer rows; //每頁顯示幾篇文章
	private List list; //裝當頁的文章
	
	
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
