package com.database.management.pojo;

import java.util.List;
import java.util.stream.Collectors;

public class DbTable {
	private String tableName;
	private List<DbField> fields;

	public DbTable() {
		super();
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public List<DbField> getFields() {
		return fields;
	}

	public void setFields(List<DbField> fields) {
		this.fields = fields;
	}

	public String constructTableDeclaration() {
		// CREATE TABLE IF NOT EXISTS dbusers (" + "firstname VARCHAR(60) not null,
		// lastname VARCHAR(60), " + "username VARCHAR(20) not null, " + "password
		// VARCHAR(60) not null, admin boolean default false);
		String returnVal = "CREATE TABLE IF NOT EXISTS " + tableName;
		if (this.fields != null && !this.fields.isEmpty()) {
			returnVal += " ("
					+ String.join(",",
							this.fields.stream().map(a -> a.constructFieldDeclaration()).collect(Collectors.toList()))
					+ ")";
		}
		return returnVal;
	}

	@Override
	public String toString() {
		return "DbTable [tableName=" + tableName + ", fields=" + fields + ", toString()=" + super.toString() + "]";
	}
}
