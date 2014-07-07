/*
 * Ojektverwaltung-UI, SP1 SoSe 2014, Team: Comanche
 * (C)opyright Sebastian Dassé, Mat.-Nr. 791537, s50602@beuth-hochschule.de
 * 
 * Module: survey
 */


"use strict";

angular.module("survey", ["datePickerDate", "constants"])
    .factory("Survey", ["DatePickerDate", "TimeUnit", "Type", function(DatePickerDate, TimeUnit, Type) {
        
        var Survey = function(config) {
            config = config || {};
            this.name = config.name || "";
            this.description = config.description || "";
            this.type = config.type || Type.UNIQUE;
            this.deadline = config.deadline || new DatePickerDate(new Date());
            this.frequency = config.frequency || { "distance": 0, "timeUnit": TimeUnit.WEEK };
            this.possibleTimeperiods = config.possibleTimeperiods || [], 
            this.determinedTimeperiod = config.determinedTimeperiod || { "startTime": new DatePickerDate(), "durationInMins": 0 }
        };

        return (Survey);
    }]);
