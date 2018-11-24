package spring.proxy;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.lang.Math;


/**
 * Acts as a proxy to store and return partitioned data and associated metadata. Pagesize 
 * represents the size of the partitioned subsets and page is the stored subset with 1-based 
 * numbering.
 * 
 * @author Shane Lockwood
 *
 */
public class Proxy<T> {
	
	private List<T> pagedData;
	private int pageSize = 2;
	private int numPages = 0;
	private int page = 1;

	
	/**
	 * Partitions and stores a subset of passed data.
	 * 
	 * @param data The Set<T> represents the data to be proxied.
	 * @param page The Integer represents which subset of the data to store.
	 */
	public void setPagedData(Set<T> data, Integer page) {
		setNumPages(data);
		setPage(page);
		int lastIndex;
		if (getPage()*pageSize >= data.size()) {
			lastIndex = data.size();
		} else {
			lastIndex = getPage()*pageSize;
		}
		pagedData = (new ArrayList<T>(data)).subList((getPage()-1)*pageSize, lastIndex);
	}
	
	/**
	 * Returns the stored subset of data.
	 * 
	 * @return Set<T>
	 */
	public List<T> getPagedData() {
		return pagedData;
	}
	
	/**
	 * Sets/modifies the page size.
	 * 
	 * @param pageSize The int representing the page size.
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	/**
	 * Returns the page size.
	 * 
	 * @return int
	 */
	public int getPageSize() {
		return pageSize;
	}
	
	private void setNumPages(Set<T> data) {
		this.numPages = (int)Math.ceil((double)data.size() / getPageSize());
	}
	
	/**
	 * Returns the number of pages the data could partition into given the page size.
	 * 
	 * @return int
	 */
	public int getNumPages() {
		return numPages;
	}
	
	private void setPage(int page) {
		this.page = Math.max(1, Math.min(page, numPages));
	}
	
	/**
	 * Returns the page selected for partitioning.
	 * 
	 * @return int
	 */
	public int getPage() {
		return this.page;
	}
}
