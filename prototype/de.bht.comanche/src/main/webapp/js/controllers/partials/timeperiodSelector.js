'use strict';

angular.module('myApp')
    .controller('timeperiodSelectorCtrl', ['$scope', '$log', '$location', 'TimePeriod',
        function($scope, $log, $location, TimePeriod) {
            $scope.selectedTimePeriod = $scope.timePeriods[0] || {
                'startTime': new Date(),
                'duration': '0'
            };

            $scope.selectTimePeriod = function(timePeriod) {
                $scope.selectedTimePeriod = timePeriod;
            };

            $scope.addNewTimePeriod = function() {
                $scope.timePeriods.push({
                    'startTime': $scope.selectedTimePeriod.startTime,
                    'duration': $scope.selectedTimePeriod.duration
                });
            };

            $scope.removeTimePeriod = function(index) {
                $scope.timePeriods.splice($scope.timePeriods.indexOf($scope.selectedTimePeriod), 1);
            };
        }
    ]);