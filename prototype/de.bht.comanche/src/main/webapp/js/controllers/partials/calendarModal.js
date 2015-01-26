/**
 * @module myApp
 *
 * @author Sebastian Dass&eacute;
 */
angular.module('myApp')
    /**
     * The controller for the calendar modal.
     *
     * @class calendarModalCtrl
     */
    .controller('calendarModalCtrl', ['$scope', '$modal', '$log', 'arrayUtil', 'TimePeriod',
        function($scope, $modal, $log, arrayUtil, TimePeriod) {

            'use strict';

            $scope.openCalendarModal = function() {
                var modalInstance = $modal.open({
                    templateUrl: 'calendarModalContent.html',
                    controller: 'calendarModalInstanceCtrl',
                    size: 'lg',
                    resolve: {
                        possibleTimePeriods: function() {
                            return $scope.possibleTimePeriods;
                        },
                        resultingTimePeriods: function() {
                            return $scope.resultingTimePeriods;
                        }
                    }
                });

                modalInstance.result.then(function(resultingTimePeriods) {
                    $scope.resultingTimePeriods = resultingTimePeriods;
                }, function() {
                    $log.debug('Modal dismissed at: ' + new Date());
                });
            };

        }
    ])
    /**
     * The controller for the calendar modal instance.
     *
     * @class calendarModalInstanceCtrl
     */
    .controller('calendarModalInstanceCtrl', ['$scope', '$modalInstance', '$log', 'possibleTimePeriods', 'resultingTimePeriods',
        function($scope, $modalInstance, $log, possibleTimePeriods, resultingTimePeriods) {

            'use strict';

            $scope.possibleTimePeriods = possibleTimePeriods;
            $scope.resultingTimePeriods = resultingTimePeriods;

            $scope.ok = function() {
                $modalInstance.close($scope.resultingTimePeriods);
            };

            $scope.cancel = function() {
                $modalInstance.dismiss('cancel');
            };

        }
    ]);