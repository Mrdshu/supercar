package com.xw.supercar.sql.page;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

public class Pageable implements Serializable {

	private static final long serialVersionUID = 2175427582384123357L;

	private int number = 0;
	private int size = 0;
	private Map<String, Direction> sort = new LinkedHashMap<String, Direction>();
	
	public static enum Direction {
		ASC, DESC;
		public static Direction fromString(String value) {
			try {
				return Direction.valueOf(value.toUpperCase(Locale.US));
			} catch (Exception e) {
				throw new IllegalArgumentException(String.format("Invalid value '%s' for orders given! Has to be either 'desc' or 'asc' (case insensitive).", value), e);
			}
		}
	}
	
	public Pageable() {
	}
	
	public Pageable(int number, int size) {
		if (0 > number) {
			throw new IllegalArgumentException("Page index must not be less than zero!");
		}
		if (0 > size) {
			throw new IllegalArgumentException("Page size must not be less than zero!");
		}
		
		this.setNumber(number);
		this.setSize(size);
	}

	public Pageable(int number, int size, Pageable.Direction direction, String... properties) {
		this(number, size);
		
		Map<String, Pageable.Direction> orders = new LinkedHashMap<String, Pageable.Direction>();
		for (String property : properties) {
			orders.put(property, direction);
		}
		
		this.setSort(orders);
	}

	public Pageable(int number, int size, Map<String, Pageable.Direction> sort) {
		this(number, size);
		
		if (sort != null && sort.size() > 0) this.setSort(sort);
	}

	public int getNumber() {
		return this.number;
	}
	
	public void setNumber(int number) {
		this.number = number;
	}
	
	public int getSize() {
		return this.size;
	}
	
	public void setSize(int size) {
		this.size = size;
	}
	
	public Map<String, Direction> getSort() {
		return sort;
	}

	public void setSort(final Map<String, Direction> sort) {
		this.sort = sort == null ? new LinkedHashMap<String, Direction>() : sort;
	}

	
	public void addSort(final Map<String, Direction> sort) {
		if (sort != null && sort.size() > 0) this.sort.putAll(sort);
	}
	
	public void addSort(final Pageable.Direction direction, final String... properties) {
		if (direction != null || properties != null) {
			for (String property : properties) {
				this.sort.put(property, direction);
			}
		}
	}
	
	public int toOffset() {
		return this.getNumber() * this.getSize();
	}
	
	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof Pageable)) {
			return false;
		}

		Pageable that = (Pageable) obj;

		boolean pageEqual = this.getNumber() == that.getNumber();
		boolean sizeEqual = this.getSize() == that.getSize();

		boolean orderEqual = this.getSort() == null ? that.getSort() == null : this.getSort().equals(that.getSort());

		return pageEqual && sizeEqual && orderEqual;
	}
	
	@Override
	public String toString() {
		return String.format("number:%s, size:%s, sort:%s",
				number, size, sort);
	}
	
	@Override
	public int hashCode() {
		return this.toString().hashCode();
	}
}
