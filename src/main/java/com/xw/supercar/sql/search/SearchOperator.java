package com.xw.supercar.sql.search;

import java.util.Arrays;

/**
 * <p>
 * 查询操作符
 * </p>
 */
public enum SearchOperator {
	eq("so.equal.to", "="), ne("so.not.equal.to", "!="), gt("so.greater.than", ">"), 
	gte("so.greater.than.or.equal.to", ">="), lt("so.less.than", "<"), lte("so.less.than.or.equal.to", "<="), 
	like("so.fuzzy.matching", "like"), notLike("so.fuzzy.not.matching", "not like"), 
	isNull("so.null", "is null"), isNotNull("so.not.null", "is not null"), 
	in("so.in", "in"), notIn("so.not.in", "not in"), custom("so.custom", null);

	private final String info;
	private final String symbol;

	SearchOperator(final String info, String symbol) {
		this.info = info;
		this.symbol = symbol;
	}

	public String getInfo() {
		return info;
	}

	public String getSymbol() {
		return symbol;
	}

	public static String toStringAllOperator() {
		return Arrays.toString(SearchOperator.values());
	}

	/**
	 * 操作符是否允许为空
	 * 
	 * @param operator
	 * @return
	 */
	public static boolean isAllowBlankValue(final SearchOperator operator) {
		return operator == SearchOperator.isNotNull || operator == SearchOperator.isNull;
	}

	public static SearchOperator valueBySymbol(String symbol) {
		symbol = formatSymbol(symbol);
		for (SearchOperator operator : values()) {
			if (operator.getSymbol().equals(symbol)) {
				return operator;
			}
		}

		throw new IllegalArgumentException("SearchOperator not method search operator symbol : " + symbol);
	}

	private static String formatSymbol(String symbol) {
		if (symbol == null || "".equals(symbol)) {
			return symbol;
		}
		return symbol.trim().toLowerCase().replace("  ", " ");
	}
}

