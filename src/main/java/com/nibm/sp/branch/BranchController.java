/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nibm.sp.branch;

import com.nibm.sp.config.DBCon;
import com.nibm.sp.entity.Branch;
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
public class BranchController {

    public ResponseWrapper getBranchDetails() {
        ResponseWrapper rw = new ResponseWrapper();
        DBCon db = new DBCon();
        Connection conn = db.getCon();
        ArrayList<Branch> bml = new ArrayList<>();

        try {

            String sql = " SELECT SPB_ID, SPB_BRANCH_ID, SPB_BRANCH_NAME, SPB_BRANCH_ADDRESS, SPB_BRANCH_TEL, SPB_BRANCH_EMAIL "
                    + " FROM SP_BRANCH "
                    + " WHERE SPB_ACTIVE_STATUS = 1";

            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Branch bm = new Branch();
                bm.setId(rs.getInt("SPB_ID"));
                bm.setBranchCode(rs.getString("SPB_BRANCH_ID"));
                bm.setBranchName(rs.getString("SPB_BRANCH_NAME"));
                bm.setBranchAddress(rs.getString("SPB_BRANCH_ADDRESS"));
                bm.setBranchEmail(rs.getString("SPB_BRANCH_EMAIL"));
                bm.setBranchTel(rs.getString("SPB_BRANCH_TEL"));
                bml.add(bm);
            }
            rs.close();
            stmt.close();

            rw.setData(bml);
            rw.setFlag(100);
            rw.setSuccessMessage("Successfully retreived data...");
            rw.setTotalRecords(bml.size());

        } catch (Exception ex) {
            ex.printStackTrace();
            rw.setFlag(99);
            rw.setErrorMessage("Cannot retreive data");
            rw.setExceptionMsg(ex.toString());
        }

        return rw;
    }

    public ResponseWrapper addBranch(Branch branch) {
        ResponseWrapper rw = new ResponseWrapper();
        DBCon db = new DBCon();
        Connection conn = db.getCon();
        int result = -1;
        int cnt = 0;

        try {
            String query = "SELECT COUNT(*) AS CNT FROM SP_BRANCH WHERE SPB_BRANCH_ID = '" + branch.getBranchCode() + "' "
                    + " AND SPB_ACTIVE_STATUS = 1 ";

            ResultSet rs = db.search(conn, query);

            while (rs.next()) {
                cnt = rs.getInt("CNT");
            }
            rs.close();

            if (cnt > 0) {
                result = 3;
            } else {

                db.offAutoCommit(conn);
                String sqlForAddBranch = "INSERT INTO SP_BRANCH "
                        + " (SPB_BRANCH_ID,SPB_BRANCH_NAME,SPB_BRANCH_ADDRESS,SPB_BRANCH_EMAIL,SPB_BRANCH_TEL) "
                        + " VALUES (?,?,?,?,?) ";

                PreparedStatement psForAddBranch = db.prepareAutoId(conn, sqlForAddBranch);
                psForAddBranch.setString(1, branch.getBranchCode());
                psForAddBranch.setString(2, branch.getBranchName());
                psForAddBranch.setString(3, branch.getBranchAddress());
                psForAddBranch.setString(4, branch.getBranchEmail());
                psForAddBranch.setString(5, branch.getBranchTel());
                psForAddBranch.executeUpdate();

                db.doCommit(conn);
                result = 1;

                rw.setSuccessMessage("Branch added successfully...");
                rw.setFlag(Integer.valueOf(result));
            }
        } catch (Exception ex) {
            db.doRollback(conn);
            ex.printStackTrace();
            rw.setFlag(99);
            rw.setErrorMessage("Error in Branch adding process");
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
            rw.setErrorMessage("Branch is alrady added.");
        }

        return rw;
    }

    public ResponseWrapper editBranch(Branch branch) {
        ResponseWrapper rw = new ResponseWrapper();
        DBCon db = new DBCon();
        Connection conn = db.getCon();
        int result = -1;

        try {

            db.offAutoCommit(conn);
            String sqlForAddBranch = "UPDATE SP_BRANCH "
                    + " SET SPB_BRANCH_ID = ?, SPB_BRANCH_NAME = ?, SPB_BRANCH_ADDRESS = ?, SPB_BRANCH_EMAIL = ?,SPB_BRANCH_TEL = ? "
                    + " WHERE SPB_ID = ? ";

            PreparedStatement psForAddBranch = db.prepareAutoId(conn, sqlForAddBranch);
            psForAddBranch.setString(1, branch.getBranchCode());
            psForAddBranch.setString(2, branch.getBranchName());
            psForAddBranch.setString(3, branch.getBranchAddress());
            psForAddBranch.setString(4, branch.getBranchEmail());
            psForAddBranch.setString(5, branch.getBranchTel());
            psForAddBranch.setInt(6, branch.getId());
            psForAddBranch.executeUpdate();

            db.doCommit(conn);
            result = 1;

            rw.setSuccessMessage("Branch edited successfully...");
            rw.setFlag(Integer.valueOf(result));

        } catch (Exception ex) {
            db.doRollback(conn);
            ex.printStackTrace();
            rw.setFlag(99);
            rw.setErrorMessage("Error in Branch editing process");
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

    public ResponseWrapper deleteBranch(Branch branch) {
        ResponseWrapper rw = new ResponseWrapper();
        DBCon db = new DBCon();
        Connection conn = db.getCon();
        int result = -1;

        try {

            db.offAutoCommit(conn);
            String sqlForAddBranch = "DELETE FROM SP_BRANCH "
                    + " WHERE SPB_ID = ? ";

            PreparedStatement psForAddBranch = db.prepareAutoId(conn, sqlForAddBranch);
            psForAddBranch.setInt(1, branch.getId());
            psForAddBranch.executeUpdate();

            db.doCommit(conn);
            result = 1;

            rw.setSuccessMessage("Branch delete successfully...");
            rw.setFlag(Integer.valueOf(result));

        } catch (Exception ex) {
            db.doRollback(conn);
            ex.printStackTrace();
            rw.setFlag(99);
            rw.setErrorMessage("Error in Branch deleting process");
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
