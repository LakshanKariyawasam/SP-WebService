/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nibm.sp.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 *
 * @author Lakshan Kariyawasam
 * @version 1.0 15/10/2020
 */
public class DBCon {

    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
     */
    public Connection getCon() {
        return getJDBCConnection();
    }

    private Connection getJDBCConnection() {

        DataSource ds = null;
        Connection conn = null;

        try {
            ds = InitialContext.doLookup("java:jboss/datasources/SPMySqlDS");

            conn = ds.getConnection();
        } catch (NamingException | SQLException ex) {
            System.out.println(ex);
        }

        if (ds == null) {
            throw new NullPointerException();
        }

        if (conn == null) {
            throw new NullPointerException();
        }

        return conn;
    }

    public ResultSet search(Connection connection, String sql) throws Exception {

        return connection.createStatement().executeQuery(sql);
    }

    public PreparedStatement prepare(Connection connection, String sql) throws Exception {

        return connection.prepareStatement(sql);
    }

    public PreparedStatement prepareAutoId(Connection connection, String sql) throws Exception {

        return connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
    }

    public void executePreparedStatement(Connection connection, PreparedStatement statement) throws Exception {

        statement.executeUpdate();
    }

    public void save(Connection connection, String sql) throws Exception {
        connection.createStatement().executeUpdate(sql);

    }

    public void ConectionClose(Connection connection) {
        try {
            if (connection != null) {

                connection.close();
            }
        } catch (SQLException ex) {
            System.out.println("Error in DB Connection Close...." + ex.toString());
        }

    }

    public void batchsave(Connection connection, String sql) throws Exception {

        Statement smt = connection.createStatement();
        smt.addBatch(sql);

    }

    public void executeBatch(Connection connection) throws Exception {

        Statement smt = connection.createStatement();

        smt.executeBatch();
    }

    public void createStatement(Connection connection) throws Exception {

        Statement smt = connection.createStatement();
        smt = connection.createStatement();
    }

    public void offAutoCommit(Connection connection) {

        try {

            connection.setAutoCommit(false);
        } catch (SQLException ex) {
            System.out.println("DBCon error off auto commit Calling............");
            ex.printStackTrace();
        }
    }

    public void onAutoCommit(Connection connection) {

        try {

            connection.setAutoCommit(true);
        } catch (SQLException ex) {
            System.out.println("DBCon error off auto commit Calling............");
            ex.printStackTrace();
        }
    }

    public void doCommit(Connection connection) throws SQLException {

        try {

            connection.commit();

        } catch (SQLException ex) {
            connection.rollback();
            System.out.println("DBCon rolled back............");
            ex.printStackTrace();
        }
    }

    public void doRollback(Connection connection) {
        try {
            connection.rollback();

        } catch (SQLException ex) {

            ex.printStackTrace();

        }
    }
}
