package com.j256.ormlite.field;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.db.DatabaseType;
import com.j256.ormlite.field.types.VoidType;

/**
 * <p>
 * Annotation that identifies a field in a class that corresponds to a column in the database and will be persisted.
 * Fields that are not to be persisted such as transient or other temporary fields probably should be ignored. For
 * example:
 * </p>
 * 
 * <pre>
 * &#064;DatabaseField(id = true)
 * private String name;
 * 
 * &#064;DatabaseField(columnName = &quot;passwd&quot;, canBeNull = false)
 * private String password;
 * </pre>
 * 
 * <p>
 * <b> WARNING:</b> If you add any extra fields here, you will need to add them to {@link DatabaseFieldConfig},
 * {@link DatabaseFieldConfigLoader}, DatabaseFieldConfigLoaderTest, and DatabaseTableConfigUtil as well.
 * </p>
 * 
 * @author graywatson
 */
@Target(FIELD)
@Retention(RUNTIME)
public @interface DatabaseField {

	/** this special string is used as a .equals check to see if no default was specified */
	public static final String DEFAULT_STRING = "__ormlite__ no default value string was specified";

	/**
	 * Default for the maxForeignAutoRefreshLevel.
	 * 
	 * @see #maxForeignAutoRefreshLevel()
	 */
	public static final int DEFAULT_MAX_FOREIGN_AUTO_REFRESH_LEVEL = 2;

	/**
	 * The name of the column in the database. If not set then the name is taken from the field name.
	 * 数据库中列的名称。 如果未设置，则从字段名称中获取名称。
	 */
	String columnName() default "";

	/**
	 * The DataType associated with the field. If not set then the Java class of the field is used to match with the
	 * appropriate DataType. This should only be set if you are overriding the default database type or if the field
	 * cannot be automatically determined (ex: byte[]).
	 *
	 * 与该字段关联的DataType。 如果未设置，则使用该字段的Java类来匹配
	 * 适当的DataType。 只有在覆盖默认数据库类型或字段无法自动确定时才应设置此项
	 * （例如：byte []）。
	 */
	DataType dataType() default DataType.UNKNOWN;

	/**
	 * The default value of the field for creating the table. Default is none.
	 * 
	 * <p>
	 * <b>NOTE:</b> If the field has a null value then this value will be inserted in its place when you call you call
	 * {@link Dao#create(Object)}. This does not apply to primitive fields so you should just assign them in the class
	 * instead.
	 * </p>
	 *
	 * 用于创建表的字段的默认值。 默认为none。
	 * <P>
	 * <b>注意：</ b>如果该字段的值为空，那么当您致电时，此值将插入其位置
	 * {@link Dao #create（Object）}。 这不适用于原始字段，因此您只需在类中分配它们即可
	 * 代替。
	 * </ p>
	 */
	String defaultValue() default DEFAULT_STRING;

	/**
	 * Width of array fields (often for strings). Default is 0 which means to take the data-type and database-specific
	 * default. For strings that means 255 characters although some databases do not support this.
	 * 数组字段的宽度（通常用于字符串）。 默认值为0表示采用数据类型和数据库特定的
	 * 默认。 对于表示255个字符的字符串，尽管某些数据库不支持此字符。
	 */
	int width() default 0;

	/**
	 * Whether the field can be assigned to null or have no value. Default is true.
	 */
	boolean canBeNull() default true;

	/**
	 * Whether the field is the id field or not. Default is false. Only one field can have this set in a class. If you
	 * don't have it set then you won't be able to use the query, update, and delete by ID methods. Only one of this,
	 * {@link #generatedId}, and {@link #generatedIdSequence} can be specified.
	 *
	 * 字段是否为id字段。 默认值为false。 只有一个字段可以在一个类中设置此设置。 如果你
	 * 如果没有设置，那么您将无法使用ID方法进行查询，更新和删除。 只有一个，
	 * generatedId generatedIdSequence id 只可指定一个
	 */
	boolean id() default false;

	/**
	 * Whether the field is an auto-generated id field. Default is false. With databases for which
	 * {@link DatabaseType#isIdSequenceNeeded} is true then this will cause the name of the sequence to be
	 * auto-generated. To specify the name of the sequence use {@link #generatedIdSequence}. Only one of this,
	 * {@link #id}, and {@link #generatedIdSequence} can be specified.
	 *
	 * 该字段是否是自动生成的id字段。 默认值为false。 有数据库
	 * {@link DatabaseType＃isIdSequenceNeeded}为true，那么这将导致序列的名称
	 * 自动生成的。 要使用{@link #generatedIdSequence}指定序列的名称。 只有一个，
	 * 可以指定{@link #id}和{@link #generatedIdSequence}。
	 */
	boolean generatedId() default false;

	/**
	 * The name of the sequence number to be used to generate this value. Default is none. This is only necessary for
	 * database for which {@link DatabaseType#isIdSequenceNeeded} is true and you already have a defined sequence that
	 * you want to use. If you use {@link #generatedId} instead then the code will auto-generate a sequence name. Only
	 * one of this, {@link #id}, and {@link #generatedId} can be specified.
	 *
	 * 用于生成此值的序列号的名称。 默认为none。 这只是必要的
	 * {@link DatabaseType＃isIdSequenceNeeded}为真的数据库，您已经有一个已定义的序列
	 * 你想用。 如果您使用{@link #generatedId}，则代码将自动生成序列名称。 只要
	 * 其中之一，{@ link #id}和{@link #generatedId}可以指定。
	 *
	 * 可理解为有些数据库可是将自动生成的id列改名，不叫id，随便什么myid。。。
	 */
	String generatedIdSequence() default "";

	/**
	 * Field is a non-primitive object that corresponds to another class that is also stored in the database. It must
	 * have an id field (either {@link #id}, {@link #generatedId}, or {@link #generatedIdSequence} which will be stored
	 * in this table. When an object is returned from a query call, any foreign objects will just have the id field set
	 * in it. To get all of the other fields you will have to do a refresh on the object using its own Dao.
	 *
	 * Field是一个非基本对象，对应于同样存储在数据库中的另一个类。 它必须
	 * 有一个id字段（{@link #id}，{@ link #generatedId}或{@link #generatedIdSequence}将被存储
	 * 在这张表中。 从查询调用返回对象时，任何外部对象都将设置id字段
	 * 在里面。 要获得所有其他字段，您必须使用自己的Dao对对象进行刷新。
	 */
	boolean foreign() default false;

	/**
	 * <p>
	 * Package should use get...() and set...() to access the field value instead of the default direct field access via
	 * reflection. This may be necessary if the object you are storing has protections around it.
	 * </p>
	 * 
	 * <p>
	 * <b>NOTE:</b> The name of the get method <i>must</i> match getXxx() where Xxx is the name of the field with the
	 * first letter capitalized. The get <i>must</i> return a class which matches the field's. The set method
	 * <i>must</i> match setXxx(), have a single argument whose class matches the field's, and return void. For example:
	 * </p>
	 * 
	 * <pre>
	 * &#064;DatabaseField
	 * private Integer orderCount;
	 * 
	 * public Integer getOrderCount() {
	 * 	return orderCount;
	 * }
	 * 
	 * public void setOrderCount(Integer orderCount) {
	 * 	this.orderCount = orderCount;
	 * }
	 * </pre>
	 */
	boolean useGetSet() default false;

	/**
	 * If the field is an Enum and the database has a value that is not one of the names in the enum then this name will
	 * be used instead. It must match one of the enum names. This is mainly useful when you are worried about backwards
	 * compatibility with older database rows or future compatibility if you have to roll back to older data definition.
	 *
	 * 如果该字段是枚举，并且数据库的值不是枚举中的某个名称，则此名称将为
	 * 改为使用。 它必须匹配其中一个枚举名称。 当你担心倒退时，这主要是有用的
	 * 如果必须回滚到较旧的数据定义，则与旧数据库行的兼容性或将来的兼容性。
	 */
	String unknownEnumName() default "";

	/**
	 * If this is set to true (default false) then it will throw a SQLException if a null value is attempted to be
	 * de-persisted into a primitive. This must only be used on a primitive field. If this is false then if the database
	 * field is null, the value of the primitive will be set to 0.
	 * 如果将其设置为true（默认为false），则在尝试使用null值时将抛出SQLException
	 * 去坚持原始。 这只能用于原始字段。 如果这是假的，那么如果是数据库
	 * field为null，基元的值将设置为0。
	 */
	boolean throwIfNull() default false;

	/**
	 * Set this to be false (default true) to not store this field in the database. This is useful if you want to have
	 * the annotation on all of your fields but turn off the writing of some of them to the database.
	 * 将此值设置为false（默认值为true），以便不将此字段存储在数据库中。 如果你想拥有它，这很有用
	 * 所有字段上的注释，但关闭将其中一些字段写入数据库。
	 */
	boolean persisted() default true;

	/**
	 * Optional format information that can be used by various field types. For example, if the Date is to be persisted
	 * as a string, this can set what format string to use for the date.
	 * 各种字段类型可以使用的可选格式信息。 例如，如果要保留日期
	 * 作为字符串，这可以设置用于日期的格式字符串。
	 */
	String format() default "";

	/**
	 * Set this to be true (default false) to have the database insure that the column is unique to all rows in the
	 * table. Use this when you wan a field to be unique even if it is not the identify field. For example, if you have
	 * the firstName and lastName fields, both with unique=true and you have "Bob", "Smith" in the database, you cannot
	 * insert either "Bob", "Jones" or "Kevin", "Smith".
	 *
	 * 将此设置为true（默认为false）以使数据库确保该列对于该行中的所有行是唯一的
	 * 表。 当您将一个字段变为唯一时，即使它不是标识字段，也可以使用此选项。 例如，如果你有
	 * firstName和lastName字段，都是unique = true，你在数据库中有“Bob”，“Smith”，你不能
	 * 插入“Bob”，“Jones”或“Kevin”，“Smith”。
	 */
	boolean unique() default false;

	/**
	 * Set this to be true (default false) to have the database insure that _all_ of the columns marked with this as
	 * true will together be unique. For example, if you have the firstName and lastName fields, both with unique=true
	 * and you have "Bob", "Smith" in the database, you cannot insert another "Bob", "Smith" but you can insert "Bob",
	 * "Jones" and "Kevin", "Smith".
	 *
	 * 将此设置为true（默认为false），以使数据库确保_all_标记为此列的列
	 * 真实将是独一无二的。 例如，如果您有firstName和lastName字段，则都使用unique = true
	 * 并且你在数据库中有“Bob”，“Smith”，你不能插入另一个“Bob”，“Smith”但你可以插入“Bob”，
	 * “琼斯”和“凯文”，“史密斯”。
	 */
	boolean uniqueCombo() default false;

	/**
	 * Set this to be true (default false) to have the database add an index for this field. This will create an index
	 * with the name columnName + "_idx". To specify a specific name of the index or to index multiple fields, use
	 * {@link #indexName()}.
	 *
	 * 将此设置为true（默认为false）以使数据库为此字段添加索引。 这将创建一个索引
	 * 名称为columnName +“_ idx”。 要指定索引的特定名称或索引多个字段，请使用
	 * {@link #indexName（）}。
	 */
	boolean index() default false;

	/**
	 * Set this to be true (default false) to have the database add a unique index for this field. This is the same as
	 * the {@link #index()} field but this ensures that all of the values in the index are unique..
	 *
	 * 将此设置为true（默认为false）以使数据库为此字段添加唯一索引。 这是一样的
	 * {@link #index（）}字段，但这可以确保索引中的所有值都是唯一的。
	 */
	boolean uniqueIndex() default false;

	/**
	 * Set this to be a string (default none) to have the database add an index for this field with this name. You do
	 * not need to specify the {@link #index()} boolean as well. To index multiple fields together in one index, each of
	 * the fields should have the same indexName value.
	 *
	 * 将此值设置为字符串（默认为none），以使数据库使用此名称为此字段添加索引。 你做
	 * 也不需要指定{@link #index（）}布尔值。 在一个索引中将多个字段索引在一起
	 * 字段应具有相同的indexName值。
	 */
	String indexName() default "";

	/**
	 * Set this to be a string (default none) to have the database add a unique index for this field with this name.
	 * This is the same as the {@link #indexName()} field but this ensures that all of the values in the index are
	 * unique.
	 *
	 * 将此值设置为字符串（默认为none），以使数据库使用此名称为此字段添加唯一索引。
	 * 这与{@link #indexName（）}字段相同，但这可以确保索引中的所有值都是
	 * 独特。
	 */
	String uniqueIndexName() default "";

	/**
	 * Set this to be true (default false) to have a foreign field automagically refreshed when an object is queried.
	 * This will _not_ automagically create the foreign object but when the object is queried, a separate database call
	 * will be made to load of the fields of the foreign object via an internal DAO. The default is to just have the ID
	 * field in the object retrieved and for the caller to call refresh on the correct DAO.
	 *
	 * 将此属性设置为true（默认为false），以便在查询对象时自动刷新外部字段。
	 * 这将_not_自动创建外部对象但是在查询对象时，单独的数据库调用
	 * 将通过内部DAO加载外来对象的字段。 默认是只拥有ID
	 * 检索到的对象中的字段以及调用者在正确的DAO上调用刷新。
	 */
	boolean foreignAutoRefresh() default false;

	/**
	 * Set this to be the number of times to refresh a foreign object's foreign object. If you query for A and it has an
	 * foreign field B which has an foreign field C ..., then querying for A could get expensive. Setting this value to
	 * 1 will mean that when you query for A, B will be auto-refreshed, but C will just have its id field set. This also
	 * works if A has an auto-refresh field B which has an auto-refresh field A.
	 * 
	 * <p>
	 * <b>NOTE:</b> Increasing this value will result in more database transactions whenever you query for A, so use
	 * carefully.
	 * </p>
	 *
	 *
	 * 将此值设置为刷新外部对象的外部对象的次数。 如果您查询A并且它有一个
	 * 外国字段B具有外国字段C ...，然后查询A可能会变得昂贵。 将此值设置为
	 * 1表示当您查询A时，B将自动刷新，但C将只设置其id字段。 这也是
	 * 如果A具有自动刷新字段B，其具有自动刷新字段A.
	 * <P>
	 * <b>注意：</ b>每当您查询A时，增加此值将导致更多数据库事务，因此请使用
	 * 小心。
	 * </ p>
	 */
	int maxForeignAutoRefreshLevel() default DEFAULT_MAX_FOREIGN_AUTO_REFRESH_LEVEL;

	/**
	 * Allows you to set a custom persister class to handle this field. This class must have a getSingleton() static
	 * method defined which will return the singleton persister.
	 *
	 * 允许您设置自定义持久性类来处理此字段。 该类必须具有getSingleton（）静态
	 * 定义的方法将返回单例持久性。
	 *
	 * @see DataPersister
	 */
	Class<? extends DataPersister> persisterClass() default VoidType.class;

	/**
	 * If this is set to true then inserting an object with the ID field already set (i.e. not null, 0) will not
	 * override it with a generated-id. If the field is null or 0 then the id will be generated. This is useful when you
	 * have a table where items sometimes have IDs and sometimes need them generated. This only works if the database
	 * supports this behavior and if {@link #generatedId()} is also true for the field.
	 *

		 如果将其设置为true，则插入已设置ID字段的对象（即非空，0）将不会
		 用generate-id覆盖它。 如果该字段为null或0，则将生成id。 这对你很有用
		 有一个表，项目有时有ID，有时需要生成。 这仅适用于数据库
		 支持此行为，并且{@link #generatedId（）}对于该字段也是如此。
	 *
	 */
	boolean allowGeneratedIdInsert() default false;

	/**
	 * Specify the SQL necessary to create this field in the database. This can be used if you need to tune the schema
	 * to enable some per-database feature or to override the default SQL generated. The column name is provided by
	 * ORMLite. If you need to specify the full schema definition including the name, see
	 * {@link #fullColumnDefinition()}.
	 *
	 * 指定在数据库中创建此字段所需的SQL。 如果需要调整架构，可以使用此方法
	 * 启用某些每个数据库功能或覆盖生成的默认SQL。 列名由。提供
	 * ORMLite。 如果需要指定包含名称的完整模式定义，请参阅
	 * {@link #fullColumnDefinition（）}。
	 */
	String columnDefinition() default "";

	/**
	 * <p>
	 * Set this to be true (default false) to have the foreign field will be automagically created using its internal
	 * DAO if the ID field is not set (null or 0). So when you call dao.create() on the parent object, any field with
	 * this set to true will possibly be created via an internal DAO. By default you have to create the object using its
	 * DAO directly. This only works if {@link #generatedId()} is also set to true.
	 * </p>
	 * 
	 * <pre>
	 * Order order1 = new Order();
	 * // account1 has not been created in the db yet and it's id == null
	 * order1.account = account1;
	 * // this will create order1 _and_ pass order1.account to the internal account dao.create().
	 * orderDao.create(order1);
	 * </pre>
	 *
	 * <P>
	 * 将此设置为true（默认为false）以使用其内部自动创建外部字段
	 * DAO如果未设置ID字段（null或0）。 所以当你在父对象上调用dao.create（）时，任何字段都有
	 * 可以通过内部DAO创建此设置为true。 默认情况下，您必须使用它创建对象
	 * DAO直接。 这仅在{@link #generatedId（）}也设置为true时有效。
	 * </ p>
	 * <PRE>
	 * 订单order1 =新订单（）;
	 * //尚未在db中创建account1，它的id == null
	 * order1.account = account1;
	 * //这将创建order1 _and_将order1.account传递给内部帐户dao.create（）。
	 * orderDao.create（order1）;
	 * </ PRE>
	 * lean foreignAutoCreate（）默认为false;
	 */
	boolean foreignAutoCreate() default false;

	/**
	 * Set this to be true (default false) to have this field to be a version field similar to
	 * javax.persistence.Version. When an update is done on a row the following happens:
	 * 
	 * <ul>
	 * <li>The update statement is augmented with a "WHERE version = current-value"</li>
	 * <li>The new value being updated is the current-value + 1 or the current Date</li>
	 * <li>If the row has been updated by another entity then the update will not change the row and 0 rows changed will
	 * be returned.</li>
	 * <li>If a row was changed then the object is changed so the version field gets the new value</li>
	 * </ul>
	 * 
	 * The field should be a short, integer, long, Date, Date-string, or Date-long type.
	 *
	 * 将此设置为true（默认为false）以使此字段成为类似的版本字段
	 * javax.persistence.Version。 在行上进行更新时，会发生以下情况：
	 * <UL>
	 * <li>更新语句使用“WHERE version = current-value”</ li>进行扩充
	 * <li>要更新的新值是当前值+ 1或当前日期</ li>
	 * <li>如果该行已被另一个实体更新，则更新将不会更改该行，并且将更改0行
	 * 被退回。</ li>
	 * <li>如果更改了某行，则会更改该对象，以便版本字段获取新值</ li>
	 * </ UL>
	 * 该字段应为short，integer，long，Date，Date-string或Date-long类型。
	 */
	boolean version() default false;

	/**
	 * Name of the foreign object's field that is tied to this table. This does not need to be specified if you are
	 * using the ID of the foreign object which is recommended. For example, if you have an Order object with a foreign
	 * Account then you may want to key off of the Account name instead of the Account ID.
	 * 
	 * <p>
	 * <b>NOTE:</b> Setting this implies {@link #foreignAutoRefresh()} is also set to true because there is no way to
	 * refresh the object since the id field is not stored in the database. So when this is set, the field will be
	 * automatically refreshed in another database query.
	 * </p>
	 *
	 * 与此表关联的外部对象的字段的名称。 如果您这样做，则无需指定
	 * 使用推荐的外来对象的ID。 例如，如果您有一个带有外部的Order对象
	 * 帐户然后您可能想要关闭帐户名称而不是帐户ID。
	 * <P>
	 * <b>注意：</ b>设置这意味着{@link #foreignAutoRefresh（）}也设置为true，因为没有办法
	 * 刷新对象，因为id字段未存储在数据库中。 因此，当设置此项时，该字段将为
	 * 在另一个数据库查询中自动刷新。
	 * </ p>
	 */
	String foreignColumnName() default "";

	/**
	 * Set this to be true (default false) if this field is a read-only field. This field will be returned by queries
	 * however it will be ignored during insert/create statements. This can be used to represent create or modification
	 * dates with the values being generated by the database.
	 *
	 * 如果此字段是只读字段，则将其设置为true（默认为false）。 查询将返回此字段
	 * 但是在插入/创建语句期间它将被忽略。 这可以用于表示创建或修改
	 * 日期，数据库生成的值。
	 */
	boolean readOnly() default false;

	/**
	 * Specify the SQL necessary to create this field in the database including the column name, which should be
	 * properly escaped and in proper case depending on your database type. This can be used if you need to fully
	 * describe the schema to enable some per-database feature or to override the default SQL generated. If you just
	 * need to specify the schema for a column then the {@link #columnDefinition()} should be used instead.
	 *
	 * 指定在数据库中创建此字段所需的SQL，包括列名，应该是
	 * 根据您的数据库类型正确转义并在适当的情况下。 如果您需要完全使用，可以使用此功能
	 * 描述模式以启用某些每个数据库的功能或覆盖生成的默认SQL。 如果你只是
	 * 需要指定列的模式，然后应该使用{@link #columnDefinition（）}。
	 */
	String fullColumnDefinition() default "";

	/*
	 * NOTE to developers: if you add fields here you have to add them to the DatabaseFieldConfig,
	 * DatabaseFieldConfigLoader, DatabaseFieldConfigLoaderTest, DatabaseTableConfigUtil, and ormlite.texi.
	 */
}
