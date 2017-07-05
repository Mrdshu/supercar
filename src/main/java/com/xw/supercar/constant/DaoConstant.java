package com.xw.supercar.constant;

import com.xw.supercar.sql.search.SearchOperator;
import com.xw.supercar.sql.search.Searchable;

/**
 * Dao层相关常量
 * @author wangsz 2017-05-11
 */
public class DaoConstant {
	/**默认分页size*/
	public final static int DEFAULT_PAGE_SIZE = 10;
	/**默认分页时显示第一页*/
	public final static int DEFAULT_PAGE_NUMBER = 0;
	/**自定义查询条件的key*/
	public final static String whereSqlCustomKey = Searchable.WHERE_SQL + "_" + SearchOperator.custom.name();
	
	/**数据库表中id对应的字段名*/
	public final static String NAME_ID = "id";
	/**数据库表中软删除对应的字段名*/
	public final static String NAME_IS_DELETED = "isDeleted";
	
	/**mapper文件中 '新增' 操作对应的statement*/
	public final static String STMT_INSERT = ".insert";
	/**mapper文件中 '批量新增' 操作对应的statement*/
	public final static String STMT_INSERT_LIST = ".insertList";
	/**mapper文件中'修改'操作对应的statement*/
	public final static String STMT_UPDATE = ".update";
	/**mapper文件中'删除'操作对应的statement*/
	public final static String STMT_DELETE = ".delete";
	/**mapper文件中'条件删除'操作对应的statement*/
	public final static String STMT_DELETE_BY = ".deleteBy";
	/**mapper文件中 '条件查询' 操作对应的statement*/
	public final static String STMT_SELECT_BY = ".selectBy";
	/**mapper文件中 '关联条件查询' 操作对应的statement*/
	public final static String STMT_EXTEND_SELECT_BY = ".extendSelectBy";
	/**mapper文件中 '计数' 操作对应的statement*/
	public final static String STMT_COUNT_BY = ".countBy";
	/**mapper文件中 '条件修改' 操作对应的statement*/
	public final static String STMT_UPDATE_BY = ".updateBy";

}
