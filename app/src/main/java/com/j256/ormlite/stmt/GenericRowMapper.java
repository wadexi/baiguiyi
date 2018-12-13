package com.j256.ormlite.stmt;

import java.sql.SQLException;

import com.j256.ormlite.support.DatabaseResults;

/**
 * Parameterized version similar to Spring's RowMapper which converts a result row into an object.
 * 
 * @param <T>
 *            Type that the mapRow returns.
 *
 * 参数化版本类似于Spring的RowMapper，它将结果行转换为对象。
 * @param <T>
 *      输入mapRow返回的类型。
 * @author graywatson
 */
public interface GenericRowMapper<T> {

	/**
	 * Used to convert a results row to an object.
	 * 
	 * @return The created object with all of the fields set from the results;
	 * @param results
	 *            Results object we are mapping.
	 * @throws SQLException
	 *             If we could not get the SQL results or instantiate the object.
	 * 用于将结果行转换为对象。
	 * @return创建的对象，其中包含从结果中设置的所有字段;
	 * @param结果
	 *             结果对象我们正在映射。
	 * @throws SQLException
	 *              如果我们无法获取SQL结果或实例化对象。
	 */
	public T mapRow(DatabaseResults results) throws SQLException;
}
