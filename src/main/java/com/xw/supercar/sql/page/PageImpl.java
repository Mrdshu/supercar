package com.xw.supercar.sql.page;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Basic {@code Page} implementation.
 * 
 * @param <T>
 *            the type of which the page consists.
 */
public class PageImpl<T> implements Page<T>, Serializable {

	private static final long serialVersionUID = 867755909294344406L;

	private final List<T> content = new ArrayList<T>();
	private final Pageable pageable;
	private final long total;
	
	public static <T> Page<T> logicalPage(Pageable pageable, List<T> allContent) {
		if (allContent == null)
			allContent = new ArrayList<T>();
		if (allContent.size() <= pageable.getSize() * (pageable.getNumber() + 1)) {
			return new PageImpl<T>(allContent, pageable, allContent.size());
		} else {
			List<T> content = new ArrayList<T>();
			int start = pageable.getSize() * pageable.getNumber();
			for (int i=start; i<allContent.size() && i<start + pageable.getSize(); i++) {
				T temp = allContent.get(i);
				content.add(temp);
			}
			return new PageImpl<T>(content, pageable, allContent.size());
		}
	}

	public PageImpl(List<T> content, Pageable pageable, long total) {

		if (null == content)
			content = new ArrayList<T>();

		this.content.addAll(content);
		this.total = total;
		this.pageable = pageable;
	}

	public PageImpl(List<T> content) {
		this(content, null, null == content ? 0 : content.size());
	}

	public int getNumber() {
		return this.pageable == null ? 0 : this.pageable.getNumber();
	}

	public int getSize() {
		return this.pageable == null ? 0 : this.pageable.getSize();
	}

	public int getTotalPages() {
		return getSize() == 0 ? 0 : (int) Math.ceil((double) this.total / (double) getSize());
	}

	public int getNumberOfElements() {
		return this.content.size();
	}

	public long getTotalElements() {
		return this.total;
	}

	public boolean hasPreviousPage() {
		return getNumber() > 0;
	}

	public boolean isFirstPage() {
		return !hasPreviousPage();
	}

	public boolean hasNextPage() {
		return (getNumber() + 1) * getSize() < this.total;
	}

	public boolean isLastPage() {
		return !hasNextPage();
	}

	public Iterator<T> iterator() {
		return this.content.iterator();
	}

	public List<T> getContent() {
		return Collections.unmodifiableList(this.content);
	}

	public boolean hasContent() {
		return !this.content.isEmpty();
	}

	public Map<String, Pageable.Direction> getSort() {
		return this.pageable == null ? null : this.pageable.getSort();
	}

	@Override
	public String toString() {

		String contentType = "UNKNOWN";

		if (this.content.size() > 0) {
			contentType = this.content.get(0).getClass().getName();
		}

		return String.format("Page %s of %d containing %s instances, content: %s", getNumber(), getTotalPages(), contentType, getContent().toString());
	}

	@Override
	public boolean equals(Object obj) {

		if (this == obj) {
			return true;
		}

		if (!(obj instanceof PageImpl<?>)) {
			return false;
		}

		PageImpl<?> that = (PageImpl<?>) obj;

		boolean totalEqual = this.total == that.total;
		boolean contentEqual = this.content.equals(that.content);
		boolean pageableEqual = this.pageable == null ? that.pageable == null : this.pageable.equals(that.pageable);

		return totalEqual && contentEqual && pageableEqual;
	}

	@Override
	public int hashCode() {

		int result = 17;

		result = 31 * result + (int) (this.total ^ this.total >>> 32);
		result = 31 * result + (this.pageable == null ? 0 : this.pageable.hashCode());
		result = 31 * result + this.content.hashCode();

		return result;
	}
}
