package spring.proxy;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.lang.Math;


public class Proxy<T> {
	
	private Set<T> pagedData;
	private int pageSize = 1;
	private int numPages = 0;
	private int page = 1;

	
	public void setPagedData(Set<T> data, Integer page) {
		setNumPages(data);
		setPage(page);
		int lastIndex;
		if (getPage()*pageSize >= data.size()) {
			lastIndex = data.size();
		} else {
			lastIndex = getPage()*pageSize;
		}
		List<T> list = (new ArrayList<T>(data)).subList((getPage()-1)*pageSize, lastIndex);
		pagedData = new HashSet<T>(list);
	}
	
	public Set<T> getPagedData() {
		return pagedData;
	}
	
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	public int getPageSize() {
		return pageSize;
	}
	
	public void setNumPages(Set<T> data) {
		this.numPages = (int)Math.ceil((double)data.size() / getPageSize());
	}
	
	public int getNumPages() {
		return numPages;
	}
	
	public void setPage(int page) {
		this.page = Math.max(1, Math.min(page, numPages));
	}
	
	public int getPage() {
		return this.page;
	}
}
