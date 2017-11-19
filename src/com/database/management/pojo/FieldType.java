package com.database.management.pojo;

public enum FieldType {
	
	SMALL_STRING("VARCHAR(20)"), LARGE_STRING("VARCHAR(256)"), BOOLEAN("boolean");

	String DbFieldDeclaration;

	FieldType(String name) {
		this.DbFieldDeclaration = name;
	}

	public String getDBFieldDeclaration() {
		return DbFieldDeclaration;
	}

	public String getDbFieldDeclaration() {
		return DbFieldDeclaration;
	}

	public void setDbFieldDeclaration(String dbFieldDeclaration) {
		DbFieldDeclaration = dbFieldDeclaration;
	}
}
