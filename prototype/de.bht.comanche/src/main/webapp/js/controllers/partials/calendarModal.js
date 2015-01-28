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
    .controller('calendarModalCtrl', ['$scope', '$modal', '$log', 'arrayUtil', 'restService', 'TimePeriod',
        function($scope, $modal, $log, arrayUtil, restService, TimePeriod) {

            'use strict';

            $scope.openCalendarModal = function() {
                console.log("open calendar modal");
                var modalInstance = $modal.open({
                    templateUrl: 'calendarModalContent.html',
                    controller: 'calendarModalInstanceCtrl',
                    size: 'lg',
                    resolve: {
                        allowedTimePeriods: function() {
                            return $scope.allowedTimePeriods;
                        },
                        resultingTimePeriods: function() {
                            return $scope.resultingTimePeriods;
                        },
                    }
                });

                modalInstance.result.then(function(resultingTimePeriods) {
                    // $scope.resultingTimePeriods = resultingTimePeriods;
                    $scope.save()(resultingTimePeriods);
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
    .controller('calendarModalInstanceCtrl', ['$scope', '$modalInstance', '$log', 'allowedTimePeriods', 'resultingTimePeriods',
        function($scope, $modalInstance, $log, allowedTimePeriods, resultingTimePeriods) {

            'use strict';

            $scope.allowedTimePeriods = allowedTimePeriods;
            $scope.resultingTimePeriods = resultingTimePeriods;

            $scope.ok = function() {
                $modalInstance.close($scope.resultingTimePeriods);
            };

            $scope.cancel = function() {
                $log.debug($scope.allowedTimePeriods)
                $modalInstance.dismiss('cancel');
            };

        }
    ]);