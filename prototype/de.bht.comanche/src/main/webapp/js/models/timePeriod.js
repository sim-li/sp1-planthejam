/**
 * @module models
 *
 * @author Duc Tung Tong
 */
angular.module('models')
	.factory('TimePeriod', ['arrayUtil', function(arrayUtil) {
		'use strict';

		/**
		 * A time period data type.
		 *
		 * @class TimePeriod
		 * @constructor
		 * @param {Object} [config={}] an optional configuration object
		 * @param {Date}   [config.startTime=new Date()] the start time of the time period
		 * @param {Date}   [config.endTime=new Date()] the end time of the time period
		 *
		 * @param {[type]} config [description]
		 */
		var TimePeriod = function(config) {
			if (!(this instanceof TimePeriod)) {
				return new TimePeriod(config);
			}
			config = config || timePeriodNull;
			this.startTime = config.startTime ? new Date(config.startTime) : new Date();
			this.endTime = config.endTime ? new Date(config.endTime) : new Date();
		};


		var timePeriodNull = {
			startTime: new Date(0),
			endTime: new Date(-60000),
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
			return (this.endTime - this.startTime) / 60000;
		};

		/**
		 * Returns true if this time period is considered to be null, which is the case when it equals TimePeriod.NULL.
		 * TimePeriod.NULL is defined as:
		 * 	- startTime: Jan 01 1970 01:00:00 GMT+0100
		 *  - endTime:   Jan 01 1970 00:59:00 GMT+0100
		 *
		 * @method isNull
		 * @return {Boolean} true if this time period is considered to be null, otherwise false.
		 */
		TimePeriod.prototype.isNull = function() {
			return this.getDurationMins() == -1;
		};

		/**
		 * This model's unique id.
		 *
		 * @property modelId
		 * @type {String}
		 */
		TimePeriod.prototype.modelId = 'timePeriod';

		/**
		 * Exports the time period by removing any client side attributes, that the server can not handle.
		 *
		 * @method doExport
		 * @return {Object} the exported invite
		 */
		TimePeriod.prototype.doExport = function() {
			return {
				// 'oid': this.oid,
				'startTime': this.startTime,
				'durationMins': this.durationMins
			};
		};

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

		/**
		 * Provides an array of three dummy time periods.
		 *
		 * @method dummyTimePeriods
		 * @static
		 * @return {Array} three dummy time periods
		 */
		TimePeriod.dummyTimePeriods = function() {
			return [{
				'startTime': new Date(),
				'durationMins': '3'
			}, {
				'startTime': new Date(),
				'durationMins': '4'
			}, {
				'startTime': new Date(),
				'durationMins': '5'
			}];
		};


		return (TimePeriod);
	}]);