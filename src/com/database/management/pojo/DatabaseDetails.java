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
public class DatabaseDetails {
     private String dbName;
     private String userName;

    public DatabaseDetails() {
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "Database{" + "dbName=" + dbName + ", userName=" + userName + '}';
    }    
}
