/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.database.management.pojo;

/**
 *
 * @author syam
 */
public class TableDetails {
    private String id;
    private String tableName;
    private String dbName;
    private String createUserName;
    private String fields; // List of DbFields

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    
    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public String getFields() {
        return fields;
    }

    public void setFields(String fields) {
        this.fields = fields;
    }

    @Override
    public String toString() {
        return "DBTable{" + "id=" + id + ", tableName=" + tableName + ", dbName=" + dbName + ", createUserName=" + createUserName + ", fields=" + fields + '}';
    }
}
