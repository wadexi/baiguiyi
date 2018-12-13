package com.j256.ormlite.db;

import java.sql.Driver;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import com.j256.ormlite.field.DataPersister;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.FieldConverter;
import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;

/**
 * Definition of the per-database functionality needed to isolate the differences between the various databases.
 * 定义隔离各种数据库之间差异所需的每数据库功能。
 * 
 * @author graywatson
 */
public interface DatabaseType {

	/**
	 * Return true if the database URL corresponds to this database type. Usually the URI is in the form jdbc:ddd:...
	 * where ddd is the driver url part.
	 * 如果数据库URL对应于此数据库类型，则返回true。 通常URI的格式为jdbc：ddd：...
	 * 其中ddd是驱动程序URL部分。
	 */
	public boolean isDatabaseUrlThisType(String url, String dbTypePart);

	/**
	 * Load the driver class associated with this database so it can wire itself into JDBC.
	 * 
	 * @throws SQLException
	 *             If the driver class is not available in the classpath.
	 */
	public void loadDriver() throws SQLException;

	/**
	 * Set the driver instance on the database type.
	 */
	public void setDriver(Driver driver);

	/**
	 * Takes a {@link FieldType} and appends the SQL necessary to create the field to the string builder. The field may
	 * also generate additional arguments which go at the end of the insert statement or additional statements to be
	 * executed before or afterwards depending on the configurations. The database can also add to the list of queries
	 * that will be performed afterward to test portions of the config.
	 * 获取{@link FieldType}并将创建字段所需的SQL附加到字符串构建器。 该领域可能
	 * 还生成其他参数，这些参数位于insert语句的末尾或其他语句中
	 * 根据配置执行之前或之后执行。 数据库还可以添加到查询列表中
	 * 之后将执行以测试部分配置。
	 */
	public void appendColumnArg(String tableName, StringBuilder sb, FieldType fieldType, List<String> additionalArgs,
                                List<String> statementsBefore, List<String> statementsAfter, List<String> queriesAfter) throws SQLException;

	/**
	 * Appends information about primary key field(s) to the additional-args or other lists.
	 * 将有关主键字段的信息附加到additional-args或其他列表。  additional  额外
	 */
	public void addPrimaryKeySql(FieldType[] fieldTypes, List<String> additionalArgs, List<String> statementsBefore,
                                 List<String> statementsAfter, List<String> queriesAfter) throws SQLException;

	/**
	 * Appends information about unique field(s) to the additional-args or other lists.
	 * 将有关唯一字段的信息附加到附加参数或其他列表。
	 */
	public void addUniqueComboSql(FieldType[] fieldTypes, List<String> additionalArgs, List<String> statementsBefore,
                                  List<String> statementsAfter, List<String> queriesAfter) throws SQLException;

	/**
	 * Takes a {@link FieldType} and adds the necessary statements to the before and after lists necessary so that the
	 * dropping of the table will succeed and will clear other associated sequences or other database artifacts
	 */
	public void dropColumnArg(FieldType fieldType, List<String> statementsBefore, List<String> statementsAfter);

	/**
	 * Add a entity-name word to the string builder wrapped in the proper characters to escape it. This avoids problems
	 * with table, column, and sequence-names being reserved words.
	 */
	public void appendEscapedEntityName(StringBuilder sb, String word);

	/**
	 * Add the word to the string builder wrapped in the proper characters to escape it. This avoids problems with data
	 * values being reserved words.
	 */
	public void appendEscapedWord(StringBuilder sb, String word);

	/**
	 * Return the name of an ID sequence based on the tabelName and the fieldType of the id.
	 */
	public String generateIdSequenceName(String tableName, FieldType idFieldType);

	/**
	 * Return the prefix to put at the front of a SQL line to mark it as a comment.
	 */
	public String getCommentLinePrefix();

	/**
	 * Return true if the database needs a sequence when you use generated IDs. Some databases (H2, MySQL) create them
	 * auto-magically. This also means that the database needs to query for a sequence value <i>before</i> the object is
	 * inserted. For old[er] versions of Postgres, for example, the JDBC call-back stuff to get the just-inserted id
	 * value does not work so we have to get the next sequence value by hand, assign it into the object, and then insert
	 * the object -- yes two SQL statements.
	 *
	 *
	 *
	 487/5000
	 如果数据库在使用生成的ID时需要序列，则返回true。 一些数据库（H2，MySQL）创建它们
	 自动神奇。 这也意味着数据库需要在</ i>对象之前查询序列值<i>
	 插入。 例如，对于Postgres的旧[er]版本，JDBC回调函数可以获得刚刚插入的id
	 值不起作用所以我们必须手动获取下一个序列值，将其分配给对象，然后插入
	 对象 - 是两个SQL语句。
	 */
	public boolean isIdSequenceNeeded();

	/**
	 * Return the DataPersister to associate with the DataType. This allows the database instance to convert a field as
	 * necessary before it goes to the database.
	 *
	 * 返回DataPersister（数据持久性）以与DataType关联。 这允许数据库实例将字段转换为
	 * 在进入数据库之前是必要的。
	 */
	public DataPersister getDataPersister(DataPersister defaultPersister, FieldType fieldType);

	/**
	 * Return the FieldConverter to associate with the DataType. This allows the database instance to convert a field as
	 * necessary before it goes to the database.
	 *
	 * 返回FieldConverter以与DataType关联。 这允许数据库实例将字段转换为
	 * 在进入数据库之前是必要的。
	 */
	public FieldConverter getFieldConverter(DataPersister dataType, FieldType fieldType);

	/**
	 * Return true if the database supports the width parameter on VARCHAR fields.
	 */
	public boolean isVarcharFieldWidthSupported();

	/**
	 * Return true if the database supports the LIMIT SQL command. Otherwise we have to use the
	 * {@link PreparedStatement#setMaxRows} instead. See prepareSqlStatement in MappedPreparedQuery.
	 */
	public boolean isLimitSqlSupported();

	/**
	 * Return true if the LIMIT should be called after SELECT otherwise at the end of the WHERE (the default).
	 */
	public boolean isLimitAfterSelect();

	/**
	 * Append to the string builder the necessary SQL to limit the results to a certain number. With some database
	 * types, the offset is an argument to the LIMIT so the offset value (which could be null or not) is passed in. The
	 * database type can choose to ignore it.
	 */
	public void appendLimitValue(StringBuilder sb, long limit, Long offset);

	/**
	 * Return true if the database supports the OFFSET SQL command in some form.
	 */
	public boolean isOffsetSqlSupported();

	/**
	 * Return true if the database supports the offset as a comma argument from the limit. This also means that the
	 * limit _must_ be specified if the offset is specified
	 */
	public boolean isOffsetLimitArgument();

	/**
	 * Append to the string builder the necessary SQL to start the results at a certain row number.
	 */
	public void appendOffsetValue(StringBuilder sb, long offset);

	/**
	 * Append the SQL necessary to get the next-value from a sequence. This is only necessary if
	 * {@link #isIdSequenceNeeded} is true.
	 */
	public void appendSelectNextValFromSequence(StringBuilder sb, String sequenceName);

	/**
	 * Append the SQL necessary to properly finish a CREATE TABLE line.
	 */
	public void appendCreateTableSuffix(StringBuilder sb);

	/**
	 * Returns true if a 'CREATE TABLE' statement should return 0. False if &gt; 0.
	 */
	public boolean isCreateTableReturnsZero();

	/**
	 * Returns true if CREATE and DROP TABLE statements can return &lt; 0 and still have worked. Gross!
	 */
	public boolean isCreateTableReturnsNegative();

	/**
	 * Returns true if table and field names should be made uppercase.
	 * 
	 * <p>
	 * Turns out that Derby and Hsqldb are doing something wrong (IMO) with entity names. If you create a table with the
	 * name "footable" (with the quotes) then it will be created as lowercase footable, case sensitive. However, if you
	 * then issue the query 'select * from footable' (without quotes) it won't find the table because it gets promoted
	 * to be FOOTABLE and is searched in a case sensitive manner. So for these databases, entity names have to be forced
	 * to be uppercase so external queries will also work.
	 * </p>
	 */
	public boolean isEntityNamesMustBeUpCase();

	/**
	 * Returns the uppercase version of an entity name. This is here in case someone wants to override the behavior of
	 * the default method because of localization issues.
	 */
	public String upCaseEntityName(String entityName);

	/**
	 * Returns the uppercase version of a string for generating and matching fields and methods. This is here in case
	 * someone wants to override the behavior of the default locale because of localization issues around
	 * capitalization.
	 * 
	 * @param string
	 *            String to up case.
	 * @param forceEnglish
	 *            Set to true to use the English locale otherwise false to use the default local one.
	 */
	public String upCaseString(String string, boolean forceEnglish);

	/**
	 * Returns the lowercase version of a string for generating and fields and methods. This is here in case someone
	 * wants to override the behavior of the default locale because of localization issues around capitalization.
	 * 
	 * @param string
	 *            String to down case.
	 * @param forceEnglish
	 *            Set to true to use the English locale otherwise false to use the default local one.
	 */
	public String downCaseString(String string, boolean forceEnglish);

	/**
	 * Returns true if nested savePoints are supported, otherwise false.
	 */
	public boolean isNestedSavePointsSupported();

	/**
	 * Return an statement that doesn't do anything but which can be used to ping the database by sending it over a
	 * database connection.
	 */
	public String getPingStatement();

	/**
	 * Returns true if batch operations should be done inside of a transaction. Default is false in which case
	 * auto-commit disabling will be done.
	 */
	public boolean isBatchUseTransaction();

	/**
	 * Returns true if the table truncate operation is supported.
	 */
	public boolean isTruncateSupported();

	/**
	 * Returns true if the table creation IF NOT EXISTS syntax is supported.
	 */
	public boolean isCreateIfNotExistsSupported();

	/**
	 * Does the database support the "CREATE INDEX IF NOT EXISTS" SQL construct. By default this just calls
	 * {@link #isCreateIfNotExistsSupported()}.
	 */
	public boolean isCreateIndexIfNotExistsSupported();

	/**
	 * Returns true if we have to select the value of the sequence before we insert a new data row.
	 */
	public boolean isSelectSequenceBeforeInsert();

	/**
	 * Does the database support the {@link DatabaseField#allowGeneratedIdInsert()} setting which allows people to
	 * insert values into generated-id columns.
	 */
	public boolean isAllowGeneratedIdInsertSupported();

	/**
	 * Return the name of the database for logging purposes.
	 */
	public String getDatabaseName();

	/**
	 * Extract and return a custom database configuration for this class.
	 * 
	 * @return null if no custom configuration extractor for this database type.
	 */
	public <T> DatabaseTableConfig<T> extractDatabaseTableConfig(ConnectionSource connectionSource, Class<T> clazz)
			throws SQLException;

	/**
	 * Append the SQL necessary to properly finish a "INSERT INTO xxx" line when there are no arguments.
	 */
	public void appendInsertNoColumns(StringBuilder sb);
}
