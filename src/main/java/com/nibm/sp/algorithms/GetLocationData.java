/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nibm.sp.algorithms;

import com.nibm.sp.config.DBCon;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Lakshan Kariyawasam
 */
public class GetLocationData {

    public String[][] PrintArray() throws ClassNotFoundException, SQLException {
        ArrayList<String> f_cities = new ArrayList<String>();

        DBCon db = new DBCon();
        Connection con = db.getCon();

        String sql = "SELECT SPB_BRANCH_NAME FROM SP_BRANCH WHERE SPB_ID IN (SELECT DISTINCT(SPD_FROM) FROM SP_DISTANCE WHERE SPD_ACTIVE_STATUS = 1 ) AND SPB_ACTIVE_STATUS = 1";
        Statement stmt = (Statement) con.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            f_cities.add(rs.getString("SPB_BRANCH_NAME"));
        }

        String[] nameArr = new String[f_cities.size()];
        nameArr = f_cities.toArray(nameArr);

        String[][] shades = new String[nameArr.length][nameArr.length];
        for (int i = 0; i < nameArr.length; i++) {
            for (int y = 0; y < nameArr.length; y++) {
                shades[i][y] = find_location_between_cities(nameArr[i], nameArr[y]);
                //System.out.println(find_location_between_cities(nameArr[i], nameArr[y]));
            }
        }

//     return graph;
        return shades;
    }

    /**
     *
     * return single Array object for multi dimentional array
     *
     *
     *
     */
    String find_location_between_cities(String f_city, String to_city) throws ClassNotFoundException, SQLException {

        DBCon db = new DBCon();
        Connection con = db.getCon();
        String disString = null;

        String sql = "SELECT SPD_DISTANCE FROM SP_DISTANCE WHERE SPD_FROM=(SELECT SPB_ID FROM SP_BRANCH WHERE  SPB_BRANCH_NAME = '" + f_city + "' AND SPB_ACTIVE_STATUS = 1) AND SPD_TO = (SELECT SPB_ID FROM SP_BRANCH WHERE  SPB_BRANCH_NAME = '" + to_city + "' AND SPB_ACTIVE_STATUS = 1) AND SPD_ACTIVE_STATUS = 1 ";
        Statement stmt = (Statement) con.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            disString = rs.getString("SPD_DISTANCE");
        }

        return disString;

    }
}
