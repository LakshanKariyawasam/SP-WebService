/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nibm.sp.distance;

import com.nibm.sp.config.DBCon;
import com.nibm.sp.entity.Distance;
import com.nibm.sp.entity.ResponseWrapper;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Lakshan Kariyawasam
 * @version 1.0 15/10/2020
 */
public class DistanceController {

    public ResponseWrapper getDistanceDetails() {
        ResponseWrapper rw = new ResponseWrapper();
        DBCon db = new DBCon();
        Connection conn = db.getCon();
        ArrayList<Distance> dml = new ArrayList<>();

        try {

            String sql = " SELECT SPD_ID, SPD_FROM, (SELECT SPB_BRANCH_NAME FROM SP_BRANCH SPB WHERE SPB_ID = SPD_FROM AND SPB_ACTIVE_STATUS = 1) AS SPD_FROM_DESC, "
                    + " SPD_TO, (SELECT SPB_BRANCH_NAME FROM SP_BRANCH WHERE SPB_ID = SPD_TO AND SPB_ACTIVE_STATUS = 1) AS SPD_TO_DESC,"
                    + " SPD_DISTANCE, SPD_DATE_INSERTED "
                    + " FROM SP_DISTANCE "
                    + " WHERE SPD_ACTIVE_STATUS = 1";

            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Distance dm = new Distance();
                dm.setId(rs.getInt("SPD_ID"));
                dm.setFromDesc(rs.getString("SPD_FROM_DESC"));
                dm.setFrom(rs.getInt("SPD_FROM"));
                dm.setToDesc(rs.getString("SPD_TO_DESC"));
                dm.setTo(rs.getInt("SPD_TO"));
                dm.setDistance(rs.getInt("SPD_DISTANCE"));
                dml.add(dm);
            }
            rs.close();
            stmt.close();

            rw.setData(dml);
            rw.setFlag(100);
            rw.setSuccessMessage("Successfully retreived data...");
            rw.setTotalRecords(dml.size());

        } catch (Exception ex) {
            ex.printStackTrace();
            rw.setFlag(99);
            rw.setErrorMessage("Cannot retreive data");
            rw.setExceptionMsg(ex.toString());
        }

        return rw;
    }

    public ResponseWrapper addDistance(Distance distance) {
        ResponseWrapper rw = new ResponseWrapper();
        DBCon db = new DBCon();
        Connection conn = db.getCon();
        int result = -1;
        int cnt = 0;
        int dis = -1;
        try {
            String query = "SELECT COUNT(*) AS CNT FROM SP_DISTANCE WHERE SPD_FROM = " + distance.getFrom() + "  AND SPD_TO = " + distance.getTo() + ""
                    + " AND SPD_ACTIVE_STATUS = 1 ";

            ResultSet rs = db.search(conn, query);

            while (rs.next()) {
                cnt = rs.getInt("CNT");
            }
            rs.close();

            if (cnt > 0) {
                result = 3;
            } else {
                String queryOne = "SELECT SPD_DISTANCE FROM SP_DISTANCE WHERE SPD_FROM = " + distance.getTo() + "  AND SPD_TO = " + distance.getFrom() + ""
                        + " AND SPD_ACTIVE_STATUS = 1 ";

                ResultSet rsOne = db.search(conn, queryOne);

                while (rsOne.next()) {
                    dis = rs.getInt("SPD_DISTANCE");
                }
                rsOne.close();

                if (dis != -1 && dis != distance.getDistance()) {
                    result = 4;
                } else {
                    db.offAutoCommit(conn);
                    String sqlForAddDistance = "INSERT INTO SP_DISTANCE "
                            + " (SPD_FROM,SPD_TO,SPD_DISTANCE) "
                            + " VALUES (?,?,?) ";

                    PreparedStatement psForAddDistance = db.prepareAutoId(conn, sqlForAddDistance);
                    psForAddDistance.setInt(1, distance.getFrom());
                    psForAddDistance.setInt(2, distance.getTo());
                    psForAddDistance.setInt(3, distance.getDistance());
                    psForAddDistance.executeUpdate();

                    db.doCommit(conn);
                    result = 1;

                    rw.setSuccessMessage("Distance added successfully...");
                    rw.setFlag(Integer.valueOf(result));
                }
            }
        } catch (Exception ex) {
            db.doRollback(conn);
            ex.printStackTrace();
            rw.setFlag(99);
            rw.setErrorMessage("Error in Distance adding process");
            rw.setExceptionMsg(ex.toString());
        } finally {
            try {
                db.onAutoCommit(conn);
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (result == 3) {
            rw.setFlag(Integer.valueOf(result));
            rw.setErrorMessage("Distance is alrady added.");
        } else if (result == 4) {
            rw.setFlag(Integer.valueOf(result));
            rw.setErrorMessage("Distance is not tally with an opesit distance.");
        }
        return rw;
    }

    public ResponseWrapper editDistance(Distance distance) {
        ResponseWrapper rw = new ResponseWrapper();
        DBCon db = new DBCon();
        Connection conn = db.getCon();
        int result = -1;
        int dis = -1;

        try {

            String query = "SELECT SPD_DISTANCE FROM SP_DISTANCE WHERE SPD_FROM = " + distance.getTo() + "  AND SPD_TO = " + distance.getFrom() + ""
                    + " AND SPD_ACTIVE_STATUS = 1 ";

            ResultSet rs = db.search(conn, query);

            while (rs.next()) {
                dis = rs.getInt("SPD_DISTANCE");
            }
            rs.close();

            if (dis != -1 && dis != distance.getDistance()) {
                result = 3;
            } else {
                db.offAutoCommit(conn);
                String sqlForAddDistance = "UPDATE SP_DISTANCE "
                        + " SET SPD_FROM = ?, SPD_TO = ?, SPD_DISTANCE = ? "
                        + " WHERE SPD_ID = ? ";

                PreparedStatement psForAddDistance = db.prepareAutoId(conn, sqlForAddDistance);
                psForAddDistance.setInt(1, distance.getFrom());
                psForAddDistance.setInt(2, distance.getTo());
                psForAddDistance.setInt(3, distance.getDistance());
                psForAddDistance.setInt(4, distance.getId());
                psForAddDistance.executeUpdate();

                db.doCommit(conn);
                result = 1;

                rw.setSuccessMessage("Distance edited successfully...");
                rw.setFlag(Integer.valueOf(result));
            }

        } catch (Exception ex) {
            db.doRollback(conn);
            ex.printStackTrace();
            rw.setFlag(99);
            rw.setErrorMessage("Error in Distance editing process");
            rw.setExceptionMsg(ex.toString());
        } finally {
            try {
                db.onAutoCommit(conn);
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (result == 3) {
            rw.setFlag(Integer.valueOf(result));
            rw.setErrorMessage("Distance is not tally with an opesit distance.");
        }

        return rw;
    }

    public ResponseWrapper deleteDistance(Distance distance) {
        ResponseWrapper rw = new ResponseWrapper();
        DBCon db = new DBCon();
        Connection conn = db.getCon();
        int result = -1;

        try {

            db.offAutoCommit(conn);
            String sqlForAddDistance = "DELETE FROM SP_DISTANCE "
                    + " WHERE SPD_ID = ? ";

            PreparedStatement psForAddDistance = db.prepareAutoId(conn, sqlForAddDistance);
            psForAddDistance.setInt(1, distance.getId());
            psForAddDistance.executeUpdate();

            db.doCommit(conn);
            result = 1;

            rw.setSuccessMessage("Distance delete successfully...");
            rw.setFlag(Integer.valueOf(result));

        } catch (Exception ex) {
            db.doRollback(conn);
            ex.printStackTrace();
            rw.setFlag(99);
            rw.setErrorMessage("Error in Distance deleting process");
            rw.setExceptionMsg(ex.toString());
        } finally {
            try {
                db.onAutoCommit(conn);
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return rw;
    }

}
