/**
 * 
 */
package com.atguigu.crm.orm;

import java.util.List;

/**
 * @author wang fu liang 
 *
 */
public class Page<T> {

	private int pageNo;
	private int pageSize=3;
	private int totalElements;
	private List<T> content;
	
	
	public int getPageNo() {
		return pageNo;
	}


	public void setPageNo(int pageNo) {
		if(pageNo<1){
			pageNo=1;
		}
		this.pageNo = pageNo;
	}


	public int getPageSize() {
		return pageSize;
	}


	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}


	public int getTotalElements() {
		return totalElements;
	}


	public void setTotalElements(int totalElements) {
		this.totalElements = totalElements;
		if(pageNo>getTotalPages()){
			pageNo=getTotalPages();
		}
	}


	public List<T> getContent() {
		return content;
	}


	public void setContent(List<T> content) {
		this.content = content;
	}


	public int getTotalPages(){
		int totalPages=0;
		totalPages=getTotalElements()/getPageSize();
		if(getTotalElements()%getPageSize()!=0){
			totalPages++;
		}
		return totalPages;
	}
	
	public boolean isHasPrevPage(){
		return pageNo>1;
	}
	
	public int getPrevPage(){
		if(isHasPrevPage()){
			return this.pageNo-1;
		}
		return this.pageNo;
	}
	
	public boolean isHasNextPage(){
		return pageNo<getTotalPages();
	}
	
	public int getNextPage(){
		if(isHasNextPage()){
			return this.pageNo+1;
		}
		return this.pageNo;
	}
}
