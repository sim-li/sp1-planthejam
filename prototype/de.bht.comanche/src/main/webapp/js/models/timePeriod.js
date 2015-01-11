angular.module('models')
	.factory('TimePeriod', function() {
		'use strict';

		var TimePeriod = function(config) {
			if (!(this instanceof TimePeriod)) {
				return new TimePeriod(config);
			}
			this.startTime = config.startTime || new Date();
			this.durationMins = config.durationMins || 0;
		};

		TimePeriod.prototype.modelId = 'timePeriod';

		TimePeriod.prototype.doExport = function() {
			return {
				'startTime': this.startTime,
				'durationMins': this.durationMins
			};
		};

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
	});