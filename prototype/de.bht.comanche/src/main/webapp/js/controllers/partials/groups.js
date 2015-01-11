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
                                console.log(groups);
                                return groups;
                            },
                            users: function() {
                                return users;
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
                 * Retrieves users and groups form promise of
                 * parent controller (using current $scope)
                 */
                var users, groups;
                (function resolvePromises() {
                    users = $scope.users;
                    groups = $scope.groups;
                })(users, groups);

                /**
                 * Create simple variables needed to store UI states for components
                 * that aren't grouped in directives.
                 */
                (function createSimpleUIStateVariables() {
                    $scope.panelOpened = false;
                })();

                (function createSimpleModelVariables() {
                    $scope.lastElementSelected = '';
                    $scope.allElementsSelected = [];
                })();

                $scope.$watch('allElementsSelected', function() {
                    panelOpener.toggle();
                });

                var panelOpener = new StateSwitcher({
                    switchOnAction: function() {
                        addCurrentElementToSelection();
                        $scope.panelOpened = true;
                        console.log('Got called');

                    },
                    switchOffAction: function() {
                        $scope.panelOpened = false;
                    },
                    condition: function() {
                        return isValidSelection() && isNoDuplicate();
                    }
                });

                var isValidSelection = function() {
                    if (!($scope.lastElementSelected && $scope.lastElementSelected.name)) {
                        return false;
                    }
                    return true;
                }

                var isNoDuplicate = function() {
                    for (var i = 0, len = $scope.addedUsers.length; i < len; i++) {
                        if ($scope.addedUsers[i] === $scope.userSelected) {
                            return false;
                        }
                    }
                    return true;
                }

                var addCurrentElementToSelection = function() {
                    $scope.allElementsSelected.push(lastElementSelected);
                }

                $scope.removeElementFromSelection = function(index) {
                    $scope.allElementsSelected.splice(index, 1);
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