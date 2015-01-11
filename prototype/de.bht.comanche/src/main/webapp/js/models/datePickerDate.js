/**
 * @module models
 *
 * @author Sebastian Dass&eacute;
 */
angular.module('models')
    .factory('DatePickerDate', function($filter) {

        'use strict';

        /**
         * A date/time class that is convenient to use in combination with date pickers.
         *
         * @class DatePickerDate
         * @constructor
         * @param {Date} jsDate a JavaScript Date
         */
        var DatePickerDate = function(jsDate) {
            if (jsDate instanceof DatePickerDate) { // safe copying of another DatePickerDate
                return jsDate;
            }
            this.date = $filter('date')(jsDate, 'yyyy-MM-dd');
            this.time = $filter('date')(jsDate, 'HH:mm');
        };

        /**
         * Converts and returns the DatePickerDate back to a JavaScript Date.
         *
         * @method toDate
         * @return {Date} this date as JavaScript Date
         */
        DatePickerDate.prototype.toDate = function() {
            return new Date(this.date + ' ' + this.time);
        };

        /**
         * Converts and returns the given array of JavaScript Dates to an array of DatePickerDates.
         *
         * @method convertDates
         * @static
         * @param  {Array}  jsDates an array of JavaScript Dates
         * @return {Array}          an array of DatePickerDates
         */
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