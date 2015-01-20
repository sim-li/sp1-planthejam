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
		 * @param {Number} [config.oid=''] the object id of the time period
		 * @param {Number} [config.startTime=new Date()] the start time of the time period
		 * @param {String} [config.durationMins=0] the duration of the time period in minutes
		 *
		 *
		 * @param {[type]} config [description]
		 */
		var TimePeriod = function(config) {
			if (!(this instanceof TimePeriod)) {
				return new TimePeriod(config);
			}
			this.oid = config.oid || '';
			this.startTime = config.startTime || new Date();
			this.durationMins = config.durationMins || 0;
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
				'oid': this.oid,
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