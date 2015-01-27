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
		 * @param {Number} [config.startTime=new Date()] the start time of the time period
		 * @param {String} [config.durationMins=0] the duration of the time period in minutes
		 *
		 * @param {[type]} config [description]
		 */
		var TimePeriod = function(config) {
			if (!(this instanceof TimePeriod)) {
				return new TimePeriod(config);
			}
			config = config || timePeriodNull;
			this.startTime = config.startTime ? new Date(config.startTime) : new Date();
			this.durationMins = config.durationMins || 0;
		};


		var timePeriodNull = {
			startTime: new Date(0),
			durationMins: -1
		};

		/**
		 * A pseudo-null value, defined as:
		 * 	- startTime: Jan 01 1970 01:00:00 GMT+0100
		 *  - duration: 0
		 *
		 * @method NULL
		 * @static
		 * @constant
		 */
		TimePeriod.NULL = function() {
			return new TimePeriod(timePeriodNull);
		};

		TimePeriod.prototype.isNull = function() {
			return this.startTime.getTime() == timePeriodNull.startTime.getTime() &&
				this.durationMins == timePeriodNull.durationMins;
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