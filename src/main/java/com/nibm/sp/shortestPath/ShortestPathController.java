/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nibm.sp.shortestPath;

import com.nibm.sp.algorithms.GetLocationData;
import com.nibm.sp.config.DBCon;
import com.nibm.sp.entity.Branch;
import com.nibm.sp.entity.ResponseWrapper;
import com.nibm.sp.algorithms.shortestPath;
import com.nibm.sp.algorithms.HashMapDistances;
import com.nibm.sp.algorithms.MST;
import com.nibm.sp.entity.ShortestPath;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Lakshan Kariyawasam
 * @version 1.0 15/10/2020
 */
public class ShortestPathController {

    public ResponseWrapper getShortestPathDetails(String from, String to) {
        ResponseWrapper rw = new ResponseWrapper();
        DBCon db = new DBCon();
        Connection conn = db.getCon();

        int[][] arr = null;

        shortestPath path = null;

        HashMapDistances outs = null;

        String[] branches = null;

        try {

            GetLocationData obj = new GetLocationData();
            String[][] ss = null;

            try {
                branches = getArray();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ShortestPathController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(ShortestPathController.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                ss = obj.PrintArray();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ShortestPathController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(ShortestPathController.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                path = new shortestPath(ss.length, getArray());
                outs = new HashMapDistances(ss.length, getArray());
                // System.out.println(obj.PrintArray()[0][1]);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ShortestPathController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(ShortestPathController.class.getName()).log(Level.SEVERE, null, ex);
            }

            arr = new int[ss.length][ss.length];
            for (int i = 0; i < ss.length; i++) {
                for (int y = 0; y < ss.length; y++) {
                    int val = 0;
                    if (ss[i][y] != null) {

                        val = Integer.parseInt(ss[i][y]);
                    } else {
                        val = 0;
                    }
                    arr[i][y] = val;

                }

            }

            int index = findIndex(branches, from);

            StringBuilder out = path.dijkstra(arr, index);

            Map<String, Integer> data = outs.dijkstra(arr, index);

            String val = "The Minimum Distance from " + from + " to " + " " + to + " is " + data.get(to).toString() + " KM";

            System.out.println("value is:: " + val);
//            MST objOne = new MST(branches.length);
//            objOne.primMST(arr);

            ShortestPath sp = new ShortestPath();
            sp.setLableOne(val);
            sp.setLableTwo(out.toString());

            rw.setData(sp);
            rw.setFlag(100);
            rw.setSuccessMessage("Successfully retreived data...");

        } catch (Exception ex) {
            ex.printStackTrace();
            rw.setFlag(99);
            rw.setErrorMessage("Cannot retreive data");
            rw.setExceptionMsg(ex.toString());
        }

        return rw;
    }

    String[] getArray() throws ClassNotFoundException, SQLException {
        DBCon db = new DBCon();
        Connection con = db.getCon();
        String[] branches = null;
        ArrayList<String> list = new ArrayList<>();

        String sql = "SELECT SPB_BRANCH_NAME FROM SP_BRANCH WHERE SPB_ID IN (SELECT DISTINCT(SPD_FROM) FROM SP_DISTANCE WHERE SPD_ACTIVE_STATUS = 1 ) AND SPB_ACTIVE_STATUS = 1";
        Statement stmt = (Statement) con.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            list.add(rs.getString("SPB_BRANCH_NAME"));
        }

        branches = list.toArray(new String[list.size()]);

        return branches;
    }

    public static int findIndex(String arr[], String t) {

        // if array is Null
        if (arr == null) {
            return -1;
        }

        // find length of array
        int len = arr.length;
        int i = 0;

        // traverse in the array
        while (i < len) {

            // if the i-th element is t
            // then return the index
            if (arr[i].equals(t)) {
                return i;
            } else {
                i = i + 1;
            }
        }
        return -1;
    }
}
