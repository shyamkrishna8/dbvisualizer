/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.database.management.db;

import com.database.management.pojo.DBUser;
import com.database.management.pojo.DbTable;
import com.database.management.pojo.DynamicTableResults;

/**
 *
 * @author syam
 */
public class UserSession {

    private DBUser userInfo;
    private DBConnection connection;

    public UserSession(DBUser userInfo) {
        this.userInfo = userInfo;
    }

    public DBUser getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(DBUser userInfo) {
        this.userInfo = userInfo;
    }

    public DBConnection getConnection() {
        return connection;
    }

    public void setConnection(DBConnection connection) {
        this.connection = connection;
    }

    public void updateConnection(String dbName) {
        try {
            this.connection = new DBConnection(dbName);
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public boolean createNewTable(DbTable dbTable) {
        try {
            this.connection.createTable(dbTable, this.getUserInfo().getUsername());
            return true;
        } catch (Exception ex) {
            System.out.println("Error creating table:" + ex.toString());
        }
        return false;
    }

    public DynamicTableResults getResults(String sqlString) {
        DynamicTableResults dtr = null;
        try {
            dtr = this.connection.getResults(sqlString);
        } catch (Exception ex) {
            System.out.println("Error fetching results" + ex.toString());
        }
        return dtr;
    }

    @Override
    public String toString() {
        return "UserSession{" + "userInfo=" + userInfo + ", connection=" + connection + '}';
    }
}
