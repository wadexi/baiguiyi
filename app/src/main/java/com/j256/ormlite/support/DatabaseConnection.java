package com.j256.ormlite.support;

import java.io.Closeable;
import java.sql.SQLException;
import java.sql.Savepoint;

import com.j256.ormlite.dao.ObjectCache;
import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.stmt.GenericRowMapper;
import com.j256.ormlite.stmt.StatementBuilder.StatementType;

/**
 * A reduction of the SQL Connection so we can implement its functionality outside of JDBC.
 * 减少SQL连接，以便我们可以在JDBC之外实现其功能。
 * @author graywatson
 */
public interface DatabaseConnection extends Closeable {

	/** returned by {@link #queryForOne} if more than one result was found by the query 如果查询找到多个结果，则由{@link #queryForOne}返回*/
	public final static Object MORE_THAN_ONE = new Object();
	public final static int DEFAULT_RESULT_FLAGS = -1;

	/**
	 * Return if auto-commit is supported.
	 */
	public boolean isAutoCommitSupported() throws SQLException;

	/**
	 * Return if auto-commit is currently enabled.
	 * 如果当前启用了自动提交，则返回。
	 */
	public boolean isAutoCommit() throws SQLException;

	/**
	 * Set the auto-commit to be on (true) or off (false). Setting auto-commit to true may or may-not cause a commit
	 * depending on the underlying database code.
	 * 将自动提交设置为打开（true）或关闭（false）。 将auto-commit设置为true可能会也可能不会导致提交
	 * 取决于底层数据库代码。
	 */
	public void setAutoCommit(boolean autoCommit) throws SQLException;

	/**
	 * Start a save point with a certain name. It can be a noop if savepoints are not supported.
	 * 
	 * @param savePointName
	 *            to use for the Savepoint although it can be ignored.
	 * 
	 * @return A SavePoint object with which we can release or commit in the future or null if none.
	 *
	 * 使用特定名称启动保存点。 如果不支持保存点，它可以是noop。
	 * @param savePointName
	 *             用于保存点虽然可以忽略。
	 * @return我们可以在将来释放或提交的SavePoint对象，如果没有，则返回null。
	 */
	public Savepoint setSavePoint(String savePointName) throws SQLException;

	/**
	 * Commit all changes since the savepoint was created. If savePoint is null then commit all outstanding changes.
	 * 
	 * @param savePoint
	 *            That was returned by setSavePoint or null if none.
	 *
	 * 自创建保存点以来提交所有更改。 如果savePoint为null，则提交所有未完成的更改。
	 * @param savePoint
	 *             这是由setSavePoint返回的，如果没有则返回null。
	 */
	public void commit(Savepoint savePoint) throws SQLException;

	/**
	 * Roll back all changes since the savepoint was created. If savePoint is null then roll back all outstanding
	 * changes.
	 * 
	 * @param savePoint
	 *            That was returned by setSavePoint previously or null if none.
	 * 自创建保存点以来回滚所有更改。 如果savePoint为null，则回滚所有未完成的
	 * 变化。
	 * @param savePoint
	 *     这是之前由setSavePoint返回的，如果没有则返回null。
	 */
	public void rollback(Savepoint savePoint) throws SQLException;

	/**
	 * Release a savepoint when we reach the end of a transaction but not the most outer one.
	 * 
	 * @param savePoint
	 *            That was returned by setSavePoint previously or null if none.
	 * Release a savepoint when we reach the end of a transaction but not the most outer one.
	 * @param savePoint
	 *            That was returned by setSavePoint previously or null if none.
	 *
	 * 当我们到达交易结束时释放保存点但不是最外部的保存点。
	 * @param savePoint
	 *             这是之前由setSavePoint返回的，如果没有则返回null。
	 * 当我们到达交易结束时释放保存点但不是最外部的保存点。
	 * @param savePoint
	 *             这是之前由setSavePoint返回的，如果没有则返回null。
	 */
	public void releaseSavePoint(Savepoint savePoint) throws SQLException;

	/**
	 * Execute a statement directly on the connection.
	 * 
	 * @param resultFlags
	 *            Allows specification of some result flags. This is dependent on the backend and database type. Set to
	 *            {@link #DEFAULT_RESULT_FLAGS} for the internal default.
	 * 直接在连接上执行语句。
	 * @param resultFlags
	 *             允许指定一些结果标志。 这取决于后端和数据库类型。 调成
	 *             {@link #DEFAULT_RESULT_FLAGS}表示内部默认值。
	 */
	public int executeStatement(String statementStr, int resultFlags) throws SQLException;

	/**
	 * Like compileStatement(String, StatementType, FieldType[]) except the caller can specify the result flags.
	 * 
	 * @param resultFlags
	 *            Allows specification of some result flags. This is dependent on the backend and database type. Set to
	 *            {@link #DEFAULT_RESULT_FLAGS} for the internal default.
	 * @param cacheStore
	 *            Cache can store results from this statement.
	 *
	 *        与compileStatement（String，StatementType，FieldType []）类似，但调用者可以指定结果标志。
	 * @param resultFlags
	 *             允许指定一些结果标志。 这取决于后端和数据库类型。 调成
	 *             {@link #DEFAULT_RESULT_FLAGS}表示内部默认值。
	 * @param cacheStore
	 *             缓存可以存储此语句的结果。
	 */
	public CompiledStatement compileStatement(String statement, StatementType type, FieldType[] argFieldTypes,
                                              int resultFlags, boolean cacheStore) throws SQLException;

	/**
	 * Perform a SQL update while with the associated SQL statement, arguments, and types. This will possibly return
	 * generated keys if kyeHolder is not null.
	 * 
	 * @param statement
	 *            SQL statement to use for inserting.
	 * @param args
	 *            Object arguments for the SQL '?'s.
	 * @param argfieldTypes
	 *            Field types of the arguments.
	 * @param keyHolder
	 *            The holder that gets set with the generated key value which may be null.
	 * @return The number of rows affected by the update. With some database types, this value may be invalid.
	 */
	public int insert(String statement, Object[] args, FieldType[] argfieldTypes, GeneratedKeyHolder keyHolder)
			throws SQLException;

	/**
	 * Perform a SQL update with the associated SQL statement, arguments, and types.
	 * 
	 * @param statement
	 *            SQL statement to use for updating.
	 * @param args
	 *            Object arguments for the SQL '?'s.
	 * @param argfieldTypes
	 *            Field types of the arguments.
	 * @return The number of rows affected by the update. With some database types, this value may be invalid.
	 *
	 * 使用关联的SQL语句，参数和类型执行SQL更新。
	 * @param声明
	 *             用于更新的SQL语句。
	 * @param args
	 *             SQL'的对象参数。
	 * @param argfieldTypes
	 *             参数的字段类型。
	 * @return受更新影响的行数。 对于某些数据库类型，此值可能无效。
	 */
	public int update(String statement, Object[] args, FieldType[] argfieldTypes) throws SQLException;

	/**
	 * Perform a SQL delete with the associated SQL statement, arguments, and types.
	 * 
	 * @param statement
	 *            SQL statement to use for deleting.
	 * @param args
	 *            Object arguments for the SQL '?'s.
	 * @param argfieldTypes
	 *            Field types of the arguments.
	 * @return The number of rows affected by the update. With some database types, this value may be invalid.
	 *
	 * 使用关联的SQL语句，参数和类型执行SQL删除。
	 * @param声明
	 *             用于删除的SQL语句。
	 * @param args
	 *             SQL'的对象参数。
	 * @param argfieldTypes
	 *             参数的字段类型。
	 * @return受更新影响的行数。 对于某些数据库类型，此值可能无效。
	 */
	public int delete(String statement, Object[] args, FieldType[] argfieldTypes) throws SQLException;

	/**
	 * Perform a SQL query with the associated SQL statement, arguments, and types and returns a single result.
	 * 
	 * @param statement
	 *            SQL statement to use for deleting.
	 * @param args
	 *            Object arguments for the SQL '?'s.
	 * @param argfieldTypes
	 *            Field types of the arguments.
	 * @param rowMapper
	 *            The mapper to use to convert the row into the returned object.
	 * @param objectCache
	 *            Any object cache associated with the query or null if none.
	 * @return The first data item returned by the query which can be cast to T, null if none, the object
	 *         {@link #MORE_THAN_ONE} if more than one result was found.
	 *
	 * 使用关联的SQL语句，参数和类型执行SQL查询，并返回单个结果。
	 * @param声明
	 *             用于删除的SQL语句。
	 * @param args
	 *             SQL'的对象参数。
	 * @param argfieldTypes
	 *             参数的字段类型。
	 * @param rowMapper
	 *             用于将行转换为返回对象的映射器。
	 * @param objectCache
	 *             与查询关联的任何对象缓存，如果没有，则为null。
	 * @return查询返回的第一个数据项，可以强制转换为T，如果没有则为null，即对象
	 *          {@link #MORE_THAN_ONE}如果找到多个结果。
	 */
	public <T> Object queryForOne(String statement, Object[] args, FieldType[] argfieldTypes,
                                  GenericRowMapper<T> rowMapper, ObjectCache objectCache) throws SQLException;

	/**
	 * Perform a query whose result should be a single long-integer value.
	 * 
	 * @param statement
	 *            SQL statement to use for the query.
	 * 执行查询，其结果应为单个长整数值。
	 * @param声明
	 *      用于查询的SQL语句。
	 */
	public long queryForLong(String statement) throws SQLException;

	/**
	 * Perform a query whose result should be a single long-integer value.
	 * 
	 * @param statement
	 *            SQL statement to use for the query.
	 * @param args
	 *            Arguments to pass into the query.
	 * @param argFieldTypes
	 *            Field types that correspond to the args.
	 * 执行查询，其结果应为单个长整数值。
	 * @param声明
	 *             用于查询的SQL语句。
	 * @param args
	 *             传递给查询的参数。
	 * @param argFieldTypes
	 *             与args对应的字段类型。
	 */
	public long queryForLong(String statement, Object[] args, FieldType[] argFieldTypes) throws SQLException;

	/**
	 * Close the connection to the database but swallow any exceptions.
	 * 关闭与数据库的连接，但吞下任何异常。
	 */
	public void closeQuietly();

	/**
	 * Return if the connection has been closed either through a call to {@link #close()} or because of a fatal error.
	 * 如果通过调用{@link #close（）}或由于致命错误关闭了连接，则返回。
	 */
	public boolean isClosed() throws SQLException;

	/**
	 * Return true if the table exists in the database.
	 * 如果表存在于数据库中，则返回true。
	 */
	public boolean isTableExists(String tableName) throws SQLException;
}
