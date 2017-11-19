package com.database.management.db;

import com.database.management.pojo.DBUser;
import com.database.management.pojo.DbTable;
import com.database.management.pojo.TableDetails;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class InitDBConnector {

    private static final String DEFAULT_JDBC_CONNECION_URL = "jdbc:mysql://localhost:3306/";
    private static final String DEFAULT_DATABASE_NAME = "dbmanagement";
    private static final String ROOT_USERNAME = "root";
    private static final String ROOT_PWD = "root";

    public static final InitDBConnector INIT_DBCONNECTOR = new InitDBConnector();

    private Connection connection = null;

    public InitDBConnector() {
        init();
    }

    private void init() {
        createDefaultDatabase();
        createDefaultUser();
        createDbTableCollection();
        createDatabaseCollection();
    }

    public void createDefaultDatabase() {
        createDatabase(DEFAULT_DATABASE_NAME, null);
    }

    public synchronized void createDatabase(String dbName, String userName) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(DEFAULT_JDBC_CONNECION_URL, ROOT_USERNAME, ROOT_PWD);
            Statement stmt = con.createStatement();
            int update = stmt.executeUpdate("CREATE DATABASE " + dbName + ";");
            System.out.println("update:" + update);

            if (userName != null && !userName.isEmpty()) {
                String query = " INSERT databasedetails (dbname, username)"
                        + " values (?, ?)";

                // create the mysql insert preparedstatement
                PreparedStatement preparedStmt = this.connection.prepareStatement(query);
                preparedStmt.setString(1, dbName);
                preparedStmt.setString(2, userName);

                boolean success = preparedStmt.execute();
                System.out.println("success:" + success);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void createDbTableCollection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Statement stmt = this.connection.createStatement();
            int tableCreated = stmt.executeUpdate("CREATE TABLE IF NOT EXISTS dbtable (" + "tablename VARCHAR(60) not null, "
                    + " dbname VARCHAR(60) not null," + "createusername VARCHAR(60), " + " fields TEXT);");
            System.out.println("tableCreated:" + tableCreated);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void createDatabaseCollection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Statement stmt = this.connection.createStatement();
            int tableCreated = stmt.executeUpdate("CREATE TABLE IF NOT EXISTS databasedetails (dbname VARCHAR(60) not null, "
                    + " username TEXT);");
            System.out.println("tableCreated:" + tableCreated);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void createDefaultUser() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            this.connection = DriverManager.getConnection(DEFAULT_JDBC_CONNECION_URL + DEFAULT_DATABASE_NAME,
                    ROOT_USERNAME, ROOT_PWD);
            Statement stmt = this.connection.createStatement();
            int tableCreated = stmt.executeUpdate("CREATE TABLE IF NOT EXISTS dbusers (" + "firstname VARCHAR(60) not null, "
                    + "lastname VARCHAR(60), " + "username VARCHAR(20) not null, " + "password VARCHAR(60) not null, "
                    + "admin boolean default false);");
            System.out.println("tableCreated:" + tableCreated);

            String sqlQuery = "SELECT * from dbusers where username='admin.dbmanagement'";
            ResultSet rs = stmt.executeQuery(sqlQuery);
            boolean userAlreadyExists = false;
            while (rs.next()) {
                userAlreadyExists = true;
            }
            if (!userAlreadyExists) {
                String query = " INSERT dbusers (firstname, lastname, username, password, admin)"
                        + " values (?, ?, ?, ?, ?)";

                // create the mysql insert preparedstatement
                PreparedStatement preparedStmt = this.connection.prepareStatement(query);
                preparedStmt.setString(1, "Admin");
                preparedStmt.setString(2, "User");
                preparedStmt.setString(3, "admin.dbmanagement");
                preparedStmt.setString(4, "admin123");
                preparedStmt.setBoolean(5, true);

                boolean success = preparedStmt.execute();

                System.out.println("success" + success);
            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public DBUser validateUserLogin(String username, String password) throws SQLException {
        DBUser dBUser = null;
        Statement stmt = this.connection.createStatement();
        String sqlQuery = "SELECT * from dbusers where username='" + username + "' AND password='" + password + "'";
        System.out.println(sqlQuery);
        ResultSet rs = stmt.executeQuery(sqlQuery);
        while (rs.next()) {
            dBUser = new DBUser();
            dBUser.setFirstname(rs.getString("firstname"));
            dBUser.setLastname(rs.getString("lastname"));
            dBUser.setUsername(rs.getString("username"));
            dBUser.setAdmin(rs.getBoolean("admin"));
            System.out.println("dbUser created:" + dBUser.toString());
        }
        return dBUser;
    }

    public Connection createNewConnection(String dbName) throws SQLException {
        return DriverManager.getConnection(DEFAULT_JDBC_CONNECION_URL + dbName, ROOT_USERNAME, ROOT_PWD);
    }

    public List<String> getAllDatabases() {
        List<String> dbNames = new ArrayList<>();
        try {
            Statement stmt = this.connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * from databasedetails;");
            while (rs.next()) {
                dbNames.add(rs.getString("dbname"));
            }
        } catch (Exception ex) {
            System.out.println("Error fetching databases:" + ex);
        }
        return dbNames;
    }

    public List<String> getTables(String dbName) {
        List<String> tables = new ArrayList<>();
        try {
            Statement stmt = this.connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * from dbtable where dbname='" + dbName + "';");
            while (rs.next()) {
                tables.add(rs.getString("tablename"));
            }
        } catch (Exception ex) {
            System.out.println("Error fetching databases:" + ex);
        }
        return tables;
    }

    public void insertNewTableRecord(TableDetails tableDetails) {
        String query = " INSERT dbtable (tablename, dbname, createusername)"
                + " values (?, ?, ?)";

        try {
            // create the mysql insert preparedstatement
            PreparedStatement preparedStmt = this.connection.prepareStatement(query);
            preparedStmt.setString(1, tableDetails.getTableName());
            preparedStmt.setString(2, tableDetails.getDbName());
            preparedStmt.setString(3, tableDetails.getCreateUserName());

            boolean success = preparedStmt.execute();

            System.out.println("success" + success);
        } catch (Exception ex) {
            System.out.println("Error recording table creation : " + ex);
        }

    }
}
