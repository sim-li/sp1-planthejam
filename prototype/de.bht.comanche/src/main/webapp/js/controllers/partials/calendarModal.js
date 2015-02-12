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

            /**
             * Opens the calendar modal. When the modal is closed with success, it saves the resulting time periods,
             * otherwise the data changes will be dismissed.
             *
             * @method openCalendarModal
             */
            $scope.openCalendarModal = function() {
                $log.log('open calendar modal');
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

            /**
             * Closes the calendar instance while returning the resulting time periods.
             * @method ok
             */
            $scope.ok = function() {
                $modalInstance.close($scope.resultingTimePeriods);
            };

            /**
             * Closes the calendar instance while dismissing all data changes.
             */
            $scope.cancel = function() {
                $modalInstance.dismiss('cancel');
            };

        }
    ]);