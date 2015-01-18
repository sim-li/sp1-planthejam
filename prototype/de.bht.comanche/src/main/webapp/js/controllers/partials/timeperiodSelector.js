'use strict';

angular.module('myApp')
    .controller('timeperiodSelectorCtrl', ['$scope', '$log', '$location', 'TimePeriod',
        function($scope, $log, $location, TimePeriod) {
            $scope.selectedTimePeriod = $scope.possibleTimePeriods[0] || {
                'start': new Date(),
                'end': new Date()
            };

            $scope.selectTimePeriod = function(timePeriod) {
                $scope.selectedTimePeriod = timePeriod;
            };

            // $scope.addNewTimePeriod = function() {
            //     $scope.timePeriods.push({
            //         'start': $scope.selectedTimePeriod.startTime,
            //         'end': $scope.selectedTimePeriod.duration
            //     });
            // };

            $scope.removeTimePeriod = function(index) {
                $scope.possibleTimePeriods.splice($scope.possibleTimePeriods.indexOf($scope.selectedTimePeriod), 1);
            };
        }
    ]);