'use strict';

angular.module('myApp')
    .controller('groupsCtrl', ['$scope', '$log', '$location', 'uiHelpers', 'restService',
        'Group', 'User', 'arrays',

        function($scope, $log, $location, restService, Invite, Survey, Group,
            Type, TimeUnit, invites, groups, selectedInvite, users, uiHelpers, arrays) {

            $scope.isCollapsed = true;


            arrayOperations.test('Hello');

            // State vars  $scope.isCollapsed = true;
            $scope.elementSelected = '';
            $scope.addedUsers = [];

            var checkIfValidSelection = function() {
                if (!($scope.userSelected && $scope.userSelected.name)) {
                    return;
                }
            }

            var checkForDuplicates = function() {
                    for (var i = 0, len = $scope.addedUsers.length; i < len; i++) {
                        if ($scope.addedUsers[i] === $scope.userSelected) {
                            $scope.isCollapsed = false;
                            return;
                        }
                    }
                }
                // These two have dependencies
                //
            var addSelectionToModel = function(modelReference) {
                $scope.addedUsers.push($scope.userSelected);
            }

            $scope.$watch('userSelected', function() {
                checkIfValidSelection();
                checkForDuplicates();
                addSelectionToModel(modelReference);
                collapseSwitcher.on();
            });


            $scope.removeMember = function(index) {
                $scope.addedUsers.splice(index, 1);
                if ($scope.addedUsers.length <= 0) {
                    $scope.isCollapsed = true;
                }
            };

            $scope.selectGroup = function(groupName) {
                if (groupName === undefined || $scope.groups === undefined) {
                    return;
                }
                var group = getGroup(groupName);
                if (group === -1) {
                    return;
                }
                $scope.selectedGroupName = group.name;
                $scope.editedGroupName = group.name;
                $scope.addedUsers = [];
                $scope.addedUsers = group.members;
                $scope.showTrash = true;
                // $scope.openDetailPanel();
            };
        }
    ]);