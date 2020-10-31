/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nibm.sp.config;

import java.io.FileInputStream;
import java.util.Properties;
import org.apache.log4j.Logger;

/**
 *
 * @author Lakshan Kariyawasam
 * @version 1.0 15/10/2020
 */

public class AppParams {

    static Properties props = new Properties();
    static String wspath = System.getenv("WS_HOME");

    static String pathset = wspath.replace("\\", "/");
    static String ippath = pathset + "/DTS_Conn.properties";

    public static final String EXCEL_FILE_PATH;
    public static final String KSA_VAT_CHANGE_EFFECTIVE_DATE;
    public static final String DROOLS_FILE_PATH;
    public static Logger LOGGER;

    static {
        System.out.println("DTS property path " + ippath);

        try {

            props.load(new FileInputStream(ippath));

        } catch (Exception e) {
            System.out.println(e);

        }

        EXCEL_FILE_PATH = props.getProperty("EXCEL_FILE_PATH");
        KSA_VAT_CHANGE_EFFECTIVE_DATE = props.getProperty("KSA_VAT_CHANGE_EFFECTIVE_DATE");
        DROOLS_FILE_PATH = props.getProperty("DROOLS_FILE_PATH");
        


    }

}
