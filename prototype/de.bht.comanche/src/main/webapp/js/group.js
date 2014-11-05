/*
 * Softwareprojekt SoSe/WiSe 2014, Team: Comanche
 * (C)opyright Sebastian Dassé, Mat.-Nr. 791537, s50602@beuth-hochschule.de
 * 
 * Module: group
 */


"use strict";

angular.module("group", [])
    .factory("Group", function(Survey) {
        
        var Group = function(config) {
            config = config || {};
            this.oid = config.oid || "";
            this.name = config.name || "";
            this.friendsOids = [];  // e.g.: [1, 2, 3]
            this.friendsNames = []; // e.g.: ["Alice", "Bob", "Carla"]
        };
        
        return (Group);
    });
