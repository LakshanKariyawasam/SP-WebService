/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nibm.sp.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author Lakshan Kariyawasam
 * @version 1.0 15/10/2020
 */
@Setter
@Getter
@ToString
public class Branch {

    public int id;
    public String branchCode;
    public String branchName;
    public String branchAddress;
    public String branchTel;
    public String branchEmail;

}
