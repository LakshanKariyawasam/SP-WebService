/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nibm.sp.config;

import java.util.Set;

/**
 *
 * @author Lakshan Kariyawasam
 * @version 1.0 15/10/2020
 */

@javax.ws.rs.ApplicationPath("service")
public class AppConfig extends javax.ws.rs.core.Application{
    
       @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method.
     * It is automatically populated with
     * all resources defined in the project.
     * If required, comment out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(com.nibm.sp.branch.BranchService.class);
        resources.add(com.nibm.sp.distance.DistanceService.class);
        resources.add(com.nibm.sp.shortestPath.ShortestPathService.class);
    }
    
    
}
