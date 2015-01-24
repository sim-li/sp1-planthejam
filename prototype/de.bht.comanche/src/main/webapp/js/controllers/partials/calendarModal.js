angular.module('myApp')
    .controller('calendarModalCtrl', ['$scope', '$modal', '$log', 'arrayUtil', 'TimePeriod',
        function($scope, $modal, $log, arrayUtil, TimePeriod) {

            'use strict';

            $scope.openCalendarModal = function() {
                // $log.debug('openCalendarModal')

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
                    $log.info('Modal dismissed at: ' + new Date());
                });
            };

        }
    ]);

angular.module('myApp')
    .controller('calendarModalInstanceCtrl', ['$scope', '$modalInstance', '$log', 'possibleTimePeriods', 'resultingTimePeriods',
        function($scope, $modalInstance, $log, possibleTimePeriods, resultingTimePeriods) {

            'use strict';

            $scope.possibleTimePeriods = possibleTimePeriods;
            $scope.resultingTimePeriods = resultingTimePeriods;

            $scope.ok = function() {
                // $log.debug($scope.possibleTimePeriods);
                // $log.debug($scope.resultingTimePeriods);

                $modalInstance.close($scope.resultingTimePeriods);
            };

            $scope.cancel = function() {
                // $log.debug($scope.items_)
                $modalInstance.dismiss('cancel');
            };

        }
    ]);