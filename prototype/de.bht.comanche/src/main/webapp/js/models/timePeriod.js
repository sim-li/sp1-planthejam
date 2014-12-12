angular.module('timePeriod', [])
	.factory('TimePeriod', function() {
		'use strict';

		var TimePeriod = function(config) {
			if (!(this instanceof TimePeriod)) {
				return new TimePeriod(config);
			}
			this.startTime = config.startTime || new Date();
			this.duration = config.duration || 0;
		};

		TimePeriod.prototype.modelId = 'timePeriod';

		TimePeriod.doExport = function() {
			return {
				'startTime': this.startTime,
				'duration': this.duration
			};
		};

		TimePeriod.dummyTimePeriods = function() {
			return [{
				'startTime': new Date(),
				'duration': '3'
			}, {
				'startTime': new Date(),
				'duration': '4'
			}, {
				'startTime': new Date(),
				'duration': '5'
			}];
		};


		return (TimePeriod);
	});