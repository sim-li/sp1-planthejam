/**
 * @module models
 *
 * @author Sebastian Dass&eacute;
 * @author Duc Tung Tong
 */
angular.module('models')
	.factory('TimePeriod', ['arrayUtil', function(arrayUtil) {

		'use strict';

		/**
		 * A time period data type.
		 *
		 * If the configuration object is null or undefined the constructed time period will be TimePeriod.NULL.
		 *
		 * @class TimePeriod
		 * @constructor
		 * @param {Object} [config={}] an optional configuration object
		 * @param {Date}   [config.startTime=new Date()] the start time of the time period
		 * @param {Date}   [config.endTime=new Date()] the end time of the time period
		 */
		var TimePeriod = function(config) {
			if (!(this instanceof TimePeriod)) {
				return new TimePeriod(config);
			};
			config = config || timePeriodNull;
			this.startTime = getValidDateFrom(config.startTime);
			this.endTime = getValidDateFrom(config.endTime);
		};

		var getValidDateFrom = function(date) {
			return date == undefined ? new Date() : new Date(date);
		};

		/**
		 * This model's unique id.
		 *
		 * @property modelId
		 * @type {String}
		 */
		TimePeriod.prototype.modelId = 'timePeriod';


		var timePeriodNull = {
			startTime: new Date(0),
			endTime: new Date(-60000)
		};

		/**
		 * A pseudo-null value, defined as:
		 * 	- startTime: Jan 01 1970 01:00:00 GMT+0100
		 *  - endTime:   Jan 01 1970 00:59:00 GMT+0100
		 *
		 * @method NULL
		 * @static
		 */
		TimePeriod.NULL = function() {
			return new TimePeriod(timePeriodNull);
		};

		/**
		 * Returns the duration of this time period in minutes.
		 *
		 * @method getDurationMins
		 * @return {Number} the duration of the time period in minutes
		 */
		TimePeriod.prototype.getDurationMins = function() {
			return parseFloat(((this.endTime - this.startTime) / 60000).toFixed(0));
		};

		/**
		 * Returns true if this time period is considered to be null, which is the case when the duration in minutes is -1.
		 *
		 * @method isNull
		 * @return {Boolean} true if this time period is considered to be null, otherwise false.
		 */
		TimePeriod.prototype.isNull = function() {
			return this.getDurationMins() == -1;
		};

		/**
		 * Exports the time period by removing any client side attributes, that the server can not handle.
		 *
		 * @method doExport
		 * @return {Object} the exported invite
		 */
		TimePeriod.prototype.doExport = function() {
			return {
				'startTime': this.startTime,
				'endTime': this.endTime
			};
		};

		/**
		 * Exports many time periods.
		 *
		 * @method exportMany
		 * @static
		 * @param  {Array} timePeriodsToExport the time periods to export
		 * @return {Array} the exported time periods
		 */
		TimePeriod.exportMany = function(timePeriodsToExport) {
			if (!timePeriodsToExport) {
				return [];
			}
			var timePeriods = [];
			arrayUtil.forEach(timePeriodsToExport, function(ele) {
				timePeriods.push(ele.doExport());
			});
			return timePeriods;
		};

		return (TimePeriod);
	}]);