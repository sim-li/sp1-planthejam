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
            this.membersOids = [];  // e.g.: [1, 2, 3]
            this.membersNames = []; // e.g.: ["Alice", "Bob", "Carla"]
        };
        
        Group.prototype.export = function(user) {
            var that = this;
            that.user = {
                "oid": user.oid, 
                "name": user.name
            };
            return that;
            
            // return {
                // "oid": this.oid, 
                // "name": this.name, 
                // "host": this.membersOids, 
                // "host": this.membersNames, 
                // "user": {
                    // "oid": user.oid, 
                    // "name": user.name
                // }
            // }
        }
        
        return (Group);
    });
