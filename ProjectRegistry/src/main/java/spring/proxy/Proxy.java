package spring.proxy;

import java.util.List;
import java.lang.Math;


public class Proxy<T> {
	
	private List<T> data;
	private int pageSize = 2;
	private int numPages;
	private int page = 1;

	public void setData(List<T> data) {
		this.data = data;
		setNumPages((int)Math.ceil((double)data.size() / getPageSize()));
	}
	
	public List<T> getData() {
		return data;
	}
	
	public List<T> getDataByPage(Integer page) {
		page = Math.max(1, Math.min(page, numPages));
		setPage(page);
		int lastIndex;
		if (page*pageSize >= data.size()) {
			lastIndex = data.size();
		} else {
			lastIndex = page*pageSize;
		}
		return data.subList((page-1)*pageSize, lastIndex);
	}
	
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	public int getPageSize() {
		return pageSize;
	}
	
	public void setNumPages(int numPages) {
		this.numPages = numPages;
	}
	
	public int getNumPages() {
		return numPages;
	}
	
	public void setPage(int page) {
		this.page = page;
	}
	
	public int getPage() {
		return page;
	}
}
