package com.database.management.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javax.annotation.PreDestroy;

import com.database.management.pojo.DbTable;
import com.database.management.pojo.DynamicTableResults;
import com.database.management.pojo.TableDetails;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

public class DBConnection {

    private String dbName;
    private Connection connection;

    public DBConnection(String dbName) {
        super();
        this.dbName = dbName;
        initConnection();
    }

    private void initConnection() {
        try {
            Class.forName(Constants.JDBC_DRIVER_CLASS);
            this.connection = DriverManager.getConnection(Constants.DEFAULT_JDBC_CONNECION_URL + dbName,
                    Constants.ROOT_USERNAME, Constants.ROOT_PWD);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void createDatabase() throws SQLException {
        Statement stmt = this.connection.createStatement();
        int update = stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS " + this.dbName + ";");
        System.out.println("update:" + update);
    }

    public void createTable(DbTable dbTable, String userName) throws SQLException {
        String tableDeclaration = dbTable.constructTableDeclaration();
        System.out.println(tableDeclaration);
        Statement stmt = this.connection.createStatement();
        int tableCreated = stmt.executeUpdate(tableDeclaration);
        System.out.println("Table creation:" + tableCreated + " for stmt:" + tableDeclaration);

        // Log it in dbtable for visibility
        TableDetails tableDetails = new TableDetails();
        tableDetails.setDbName(dbName);
        tableDetails.setTableName(dbTable.getTableName());
        tableDetails.setCreateUserName(userName);
        InitDBConnector.INIT_DBCONNECTOR.insertNewTableRecord(tableDetails);

    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public boolean valid() {
        try {
            if (this.connection != null && !this.connection.isClosed()) {
                return true;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return false;
    }

    public DynamicTableResults getResults(String sqlQuery) {
        DynamicTableResults dtr = new DynamicTableResults();
        try {
            Statement statement = this.connection.createStatement();
            ResultSet rs = statement.executeQuery(sqlQuery);

            ResultSetMetaData metaData = rs.getMetaData();
            int count = metaData.getColumnCount(); //number of column
            String[] columnName = new String[count];
            for (int i = 1; i <= count; i++) {
                columnName[i - 1] = metaData.getColumnLabel(i);
            }

            dtr.setHeaders(columnName);
            List<String[]> rows = new ArrayList<>();
            while (rs.next()) {
                String[] row = new String[count];
                for (int i = 1; i <= count; i++) {
                    row[i - 1] = String.valueOf(rs.getObject(i));
                }
                rows.add(row);
            }
            dtr.setRows(rows);
        } catch (SQLException e) {
            System.out.println(e);
        }
        return dtr;
    }

    @PreDestroy
    public void closeConnection() throws SQLException {
        if (this.connection != null && !this.connection.isClosed()) {
            this.connection.close();
        }
    }
}
