angular.module('myApp')
    .controller('calendarModalCtrl', ['$scope', '$modal', '$log', 'arrayUtil', 'TimePeriod',
        function($scope, $modal, $log, arrayUtil, TimePeriod) {

            'use strict';

            $scope.items = ['item1', 'item2', 'item3'];

            $scope.openCalendarModal = function() {
                $log.debug('openCalendarModal')
                $log.debug($scope.test)
                $scope.test.tel = 10101019

                var modalInstance = $modal.open({
                    templateUrl: 'calendarModalContent.html',
                    controller: 'calendarModalInstanceCtrl',
                    size: 'lg',
                    resolve: {
                        itemsRes: function() {
                            return $scope.items;
                        }
                    }
                });

                modalInstance.result.then(function(selectedItem) {
                    $scope.selected = selectedItem;
                }, function() {
                    $log.info('Modal dismissed at: ' + new Date());
                });
            };

        }
    ]);

angular.module('myApp')
    .controller('calendarModalInstanceCtrl', ['$scope', '$modalInstance', '$log', 'itemsRes',
        function($scope, $modalInstance, $log, itemsRes) {

            'use strict';

            $scope.items_ = itemsRes;
            $scope.selected = {
                item: $scope.items_[0]
            };

            $scope.ok = function() {
                $log.debug($scope.items_)
                    // $modalInstance.close($scope.selected.item);
                $modalInstance.close("hi from modal");
            };

            $scope.cancel = function() {
                $log.debug($scope.items_)
                $modalInstance.dismiss('cancel');
            };

        }
    ]);