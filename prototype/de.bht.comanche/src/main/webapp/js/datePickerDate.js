/*
 * Softwareprojekt SoSe/WiSe 2014, Team: Comanche
 * (C)opyright Sebastian Dassé, Mat.-Nr. 791537, s50602@beuth-hochschule.de
 * 
 * Module: date picker date
 */


"use strict";

angular.module("datePickerDate", [])
    .factory("DatePickerDate", function($filter) {
        /*
         * Converts a JavaScript Date to a date format the angular datepicker understands. 
         * - toDate() converts and returns the date back to JavaScript Date
         * - convertDates() converts and returns the given JavaScript Date array to a DatePickerDate array
         */
        var DatePickerDate = function(jsDate) {
            if (jsDate instanceof DatePickerDate) { // safe copying of another DatePickerDate
                return {
                    date: jsDate.date || "", 
                    time: jsDate.time || ""
                };
            }
            this.date = $filter('date')(jsDate, "yyyy-MM-dd");
            this.time = $filter('date')(jsDate, "HH:mm");
        };

        DatePickerDate.prototype.toDate = function() {
            return new Date(this.date + " " + this.time);
        };

        DatePickerDate.convertDates = function(jsDates) {
            var _dates = [];
            if (!jsDates) {
                return _dates;
            }
            for (var i = 0; i < jsDates.length; i++) {
                _dates.push(new DatePickerDate(jsDates[i]));
            }
            return _dates;
        };

        return (DatePickerDate);
    });
