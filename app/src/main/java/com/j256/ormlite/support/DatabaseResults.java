package com.j256.ormlite.support;

import java.io.Closeable;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;

import com.j256.ormlite.dao.ObjectCache;

/**
 * A reduction of the SQL ResultSet so we can implement it outside of JDBC.
 * 减少SQL ResultSet，以便我们可以在JDBC之外实现它。
 * <p>
 * <b>NOTE:</b> In all cases, the columnIndex parameters are 0 based -- <i>not</i> 1 based like JDBC.
 * </p>
 * 
 * @author graywatson
 */
public interface DatabaseResults extends Closeable {

	/**
	 * Returns the number of columns in these results.
	 */
	public int getColumnCount() throws SQLException;

	/**
	 * Returns an array of column names.
	 */
	public String[] getColumnNames() throws SQLException;

	/**
	 * Moves to the first result. This may not work with the default iterator depending on your database.
	 * 移至第一个结果。 这可能不适用于默认迭代器，具体取决于您的数据库。
	 * @return true if there are more results to be processed.
	 */
	public boolean first() throws SQLException;

	/**
	 * Moves to the previous result. This may not work with the default iterator depending on your database.
	 * 
	 * @return true if there are more results to be processed.
	 */
	public boolean previous() throws SQLException;

	/**
	 * Moves to the next result.
	 * 
	 * @return true if there are more results to be processed.
	 */
	public boolean next() throws SQLException;

	/**
	 * Moves to the last result. This may not work with the default iterator depending on your database.
	 * 
	 * @return true if there are more results to be processed.
	 */
	public boolean last() throws SQLException;

	/**
	 * Moves forward (positive value) or backwards (negative value) the list of results. moveRelative(1) should be the
	 * same as {@link #next()}. moveRelative(-1) is the same as {@link #previous} result. This may not work with the
	 * default iterator depending on your database.
	 * 
	 * @param offset
	 *            Number of rows to move. Positive moves forward in the results. Negative moves backwards.
	 * @return true if there are more results to be processed.
	 *
	 * 向前移动（正值）或向后移动（负值）结果列表。 moveRelative（1）应该是
	 * 与{@link #next（）}相同。 moveRelative（-1）与{@link #previous}结果相同。 这可能不适用于
	 * 默认迭代器，具体取决于您的数据库。
	 * @param offset
	 *             要移动的行数。 积极向前推进结果。 负面向后移动。
	 * @return如果有更多结果要处理，则为true。
	 *
	 */
	public boolean moveRelative(int offset) throws SQLException;

	/**
	 * Moves to an absolute position in the list of results. This may not work with the default iterator depending on
	 * your database.
	 * 
	 * @param position
	 *            Row number in the result list to move to.
	 * @return true if there are more results to be processed.
	 *
	 * 移动到结果列表中的绝对位置。 这可能不适用于默认迭代器，具体取决于
	 * 你的数据库。
	 * @param职位
	 *             要移动到的结果列表中的行号。
	 * @return如果有更多结果要处理，则为true。
	 */
	public boolean moveAbsolute(int position) throws SQLException;

	/**
	 * Returns the column index associated with the column name.
	 * 
	 * @throws SQLException
	 *             if the column was not found in the results.
	 *
	 * 返回与列名关联的列索引。
	 * @throws SQLException
	 *              如果在结果中找不到该列。
	 */
	public int findColumn(String columnName) throws SQLException;

	/**
	 * Returns the string from the results at the column index.
	 * 从列索引处的结果返回字符串。
	 */
	public String getString(int columnIndex) throws SQLException;

	/**
	 * Returns the boolean value from the results at the column index.
	 */
	public boolean getBoolean(int columnIndex) throws SQLException;

	/**
	 * Returns the char value from the results at the column index.
	 */
	public char getChar(int columnIndex) throws SQLException;

	/**
	 * Returns the byte value from the results at the column index.
	 */
	public byte getByte(int columnIndex) throws SQLException;

	/**
	 * Returns the byte array value from the results at the column index.
	 */
	public byte[] getBytes(int columnIndex) throws SQLException;

	/**
	 * Returns the short value from the results at the column index.
	 */
	public short getShort(int columnIndex) throws SQLException;

	/**
	 * Returns the integer value from the results at the column index.
	 */
	public int getInt(int columnIndex) throws SQLException;

	/**
	 * Returns the long value from the results at the column index.
	 */
	public long getLong(int columnIndex) throws SQLException;

	/**
	 * Returns the float value from the results at the column index.
	 */
	public float getFloat(int columnIndex) throws SQLException;

	/**
	 * Returns the double value from the results at the column index.
	 */
	public double getDouble(int columnIndex) throws SQLException;

	/**
	 * Returns the SQL timestamp value from the results at the column index.
	 * 从列索引处的结果返回SQL时间戳值。
	 */
	public Timestamp getTimestamp(int columnIndex) throws SQLException;

	/**
	 * Returns an input stream for a blob value from the results at the column index.
	 * 从列索引处的结果返回blob值的输入流。
	 */
	public InputStream getBlobStream(int columnIndex) throws SQLException;

	/**
	 * Returns the SQL big decimal value from the results at the column index.
	 * 从列索引处的结果返回SQL大十进制值。
	 */
	public BigDecimal getBigDecimal(int columnIndex) throws SQLException;

	/**
	 * Returns the SQL object value from the results at the column index.
	 * 从列索引处的结果返回SQL对象值。
	 */
	public Object getObject(int columnIndex) throws SQLException;

	/**
	 * Returns true if the last object returned with the column index is null.
	 * 如果使用列索引返回的最后一个对象为null，则返回true。
	 */
	public boolean wasNull(int columnIndex) throws SQLException;

	/**
	 * Returns the object cache for looking up objects associated with these results or null if none.
	 * 返回用于查找与这些结果关联的对象的对象缓存，如果没有则返回null。
	 */
	public ObjectCache getObjectCacheForRetrieve();

	/**
	 * Returns the object cache for storing objects generated by these results or null if none.
	 * 返回用于存储由这些结果生成的对象的对象缓存，如果没有则返回null。
	 */
	public ObjectCache getObjectCacheForStore();

	/**
	 * Closes any underlying database connections but swallows any SQLExceptions.
	 * 关闭任何基础数据库连接但吞下任何SQLExceptions。
	 */
	public void closeQuietly();
}
