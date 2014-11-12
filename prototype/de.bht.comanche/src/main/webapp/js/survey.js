/*
 * Softwareprojekt SoSe/WiSe 2014, Team: Comanche
 * (C)opyright Sebastian Dassé, Mat.-Nr. 791537, s50602@beuth-hochschule.de
 * 
 * Module: survey
 */


"use strict";

angular.module("survey", ["datePickerDate", "constants"])
    .factory("Survey", ["DatePickerDate", "TimeUnit", "Type", function(DatePickerDate, TimeUnit, Type) {
        
        var Survey = function(config) {
            config = config || {};
            this.oid = config.oid || "";
            this.name = config.name || "";
            this.description = config.description || "";
            this.type = config.type || Type.ONE_TIME;
            this.deadline = new DatePickerDate(config.deadline) || new DatePickerDate(new Date());
            this.frequencyDist = config.frequencyDist || 0;
            this.frequencyTimeUnit = TimeUnit[config.frequencyTimeUnit] || TimeUnit.WEEK;
            this.possibleTimeperiods = config.possibleTimeperiods || [];
            this.determinedTimeperiod = config.determinedTimeperiod || { "startTime": new DatePickerDate(), "durationInMins": 0 };
        };

        Survey.prototype.convertDatesToDatePickerDate = function() {
            this.deadline = new DatePickerDate(this.deadline);
            var _possibleTimeperiods = [];
            for (var i = 0; i < this.possibleTimeperiods.length; i++) {
                var p = this.possibleTimeperiods[i];
                p.startTime = new DatePickerDate(p.startTime);
                _possibleTimeperiods.push(p);
            }
            this.possibleTimeperiods = _possibleTimeperiods;
            this.determinedTimeperiod.startTime = new DatePickerDate(this.determinedTimeperiod.startTime);
        }

        Survey.prototype.convertDatesToJsDate = function() {
            this.deadline = this.deadline.toDate;
            var _possibleTimeperiods = [];
            for (var i = 0; i < this.possibleTimeperiods.length; i++) {
                var p = this.possibleTimeperiods[i];
                p.startTime = p.startTime.toDate();
                _possibleTimeperiods.push(_p);
            }
            this.possibleTimeperiods = _possibleTimeperiods;
            this.determinedTimeperiod.startTime = this.determinedTimeperiod.toDate();
        }

        Survey.forSurveysConvertDatesToDatePickerDate = function(surveys) {
            if (!surveys) {
                return surveys;
            }
            for (var i = 0; i < surveys.length; i++) {
                surveys[i].convertDatesToDatePickerDate();
            }
            return surveys;
        }
        
        Survey.forSurveysConvertDatesToJsDate = function(surveys) {
            if (!surveys) {
                return surveys;
            }
            for (var i = 0; i < surveys.length; i++) {
                surveys[i].convertDatesToJsDate();
            }
            return surveys;
        }

        return (Survey);
    }]);
