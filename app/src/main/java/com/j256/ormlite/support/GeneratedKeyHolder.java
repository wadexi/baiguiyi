package com.j256.ormlite.support;

import java.sql.SQLException;

/**
 * The holder of a generated key so we can return the value of generated keys from update methods.
 * 生成密钥的持有者，以便我们可以从更新方法返回生成的密钥的值。
 * @author graywatson
 */
public interface GeneratedKeyHolder {

	/**
	 * Add the key number on the key holder. May be called multiple times.
	 * 在钥匙扣上添加钥匙编号。 可以多次调用。
	 */
	public void addKey(Number key) throws SQLException;
}
