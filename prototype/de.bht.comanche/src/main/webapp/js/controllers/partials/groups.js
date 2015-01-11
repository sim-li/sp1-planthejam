'use strict';

angular.module('myApp')
    .controller('groupsCtrl', ['$scope', '$modal', '$log', 'restService', 'StateSwitcher',
        function($scope, $modal, $log, restService, StateSwitcher) {

            /**
             * Creates a new modal instance and opens it.
             * TODO: Improve doc
             * @param  {[type]} size [description]
             * @return {[type]}      [description]
             */
            $scope.openGroupModal = function() {
                var modalInstance = $modal.open({
                    templateUrl: 'groupsModalContent.html',
                    controller: 'groupsModalCtrl',
                    size: 'lg',
                    resolve: {
                        groups: function() {
                            return $scope.groups;
                        },
                        users: function() {
                            return $scope.users;
                        }
                    }
                });
                modalInstance.result.then(function(selectedItem) {
                    $scope.selected = selectedItem;
                }, function() {
                    $log.info('Modal dismissed at: ' + new Date());
                });
            };

            /**
             * Create simple variables needed to store UI states for components
             * that aren't grouped in directives.
             */
            (function createSimpleUIStateVariables() {
                $scope.panelOpened = false;
            })();

            (function createDataModels() {
                $scope.lastElementSelected = '';
                //IMPL THIS
                $scope.allElementsSelected = convertToMixedList($scope.usersOfSurvey);
                $scope.elements = $scope.users.concat($scope.groups);
            })();

            $scope.$watch('allElementsSelected', function() {
                // TRACK CHANGES HERE and OPEN Accordingly
                // TODO: Implement "switch according to condition in stateswitcher"
                // IMPL THIS
                $scope.usersOfSurvey = convertToListOfUsers($scope.allElementsSelected);
            });

            $scope.$watch('lastElementSelected', function() {
                addElementToSelection();
            });

            // CURRENTLY not connected to UI ( collapse flag set to FALSE )
            var panelOpener = new StateSwitcher({
                switchOnAction: function() {
                    $scope.panelOpened = true;
                },
                switchOffAction: function() {
                    $scope.panelOpened = false;
                },
                condition: function() {
                    return $scope.allElementsSelected > 0;
                }
            });

            var addElementToSelection = function() {
                if (isNoValidSelection() || isDuplicate()) {
                    return;
                }
                $scope.allElementsSelected.push($scope.lastElementSelected);
            }

            var isNoValidSelection = function() {
                if (!($scope.lastElementSelected && $scope.lastElementSelected.name)) {
                    return true;
                }
                return false;
            }

            var isDuplicate = function() {
                for (var i = 0, len = $scope.allElementsSelected.length; i < len; i++) {
                    if ($scope.allElementsSelected[i] === $scope.lastElementSelected) {
                        return true;
                    }
                }
                return false;
            }

            $scope.removeElementFromSelection = function(index) {
                $scope.allElementsSelected.splice(index, 1);
            }
        }
    ]);
// Please note that $modalInstance represents a modal window (instance) dependency.
// It is not the same as the $modal service used above.

angular.module('myApp')
    .controller('groupsModalCtrl', function($scope, $modalInstance, groups, users) {
        $scope.test = 'My test!';
        $scope.groups = groups;
        $scope.users = users;
        console.log('GRAV URL', groups);
        $scope.selectedGroup = '';
        $scope.userSelected = '';

        /**
         * This must be transferred into a generic solution, simply not convincing.
         * Must dock to actual data model.
         * @return {[type]} [description]
         */
        $scope.$watch('userSelected', function() {
            if ($scope.userSelected === undefined || $scope.userSelected.name === undefined) {
                return;
            }
            for (var i = 0, len = $scope.addedUsers.length; i < len; i++) {
                if ($scope.addedUsers[i] === $scope.userSelected) {
                    return;
                }
            }
            $scope.addedUsers.push($scope.userSelected);
        });

        $scope.selectGroup = function(group) {
            $scope.selectedGroup = group;
        };

        $scope.ok = function() {
            // $modalInstance.close($scope.selected.item);
        };
        $scope.cancel = function() {
            $modalInstance.dismiss('cancel');
        };
    });