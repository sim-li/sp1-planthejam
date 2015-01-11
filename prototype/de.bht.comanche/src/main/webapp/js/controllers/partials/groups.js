'use strict';

angular.module('myApp')
    .controller('groupsCtrl', ['$scope', '$modal', '$log', 'restService', function($scope, $modal, $log, restService) {

        $scope.open = function(size) {
            console.log('Open got called');
            var modalInstance = $modal.open({
                templateUrl: 'groupsModalContent.html',
                controller: 'groupsModalCtrl',
                size: size,
                resolve: {
                    groups: function(restService, Group) {
                        return restService.doGetMany(Group);
                    },
                    users: function(restService, User) {
                        return restService.doGetMany(User);
                    }
                }
            });
            modalInstance.result.then(function(selectedItem) {
                $scope.selected = selectedItem;
            }, function() {
                $log.info('Modal dismissed at: ' + new Date());
            });
        };

        // -> UI BEHAVIOUR

        // // REFACTOR THIS
        // $scope.isCollapsed = true;

        // // State vars  $scope.isCollapsed = true;
        // $scope.elementSelected = '';
        // $scope.addedUsers = [];

        // var checkIfValidSelection = function() {
        //     if (!($scope.userSelected && $scope.userSelected.name)) {
        //         return;
        //     }
        // }

        // var checkForDuplicates = function() {
        //         for (var i = 0, len = $scope.addedUsers.length; i < len; i++) {
        //             if ($scope.addedUsers[i] === $scope.userSelected) {
        //                 $scope.isCollapsed = false;
        //                 return;
        //             }
        //         }
        //     }
        //     // These two have dependencies
        //     //
        // var addSelectionToModel = function(modelReference) {
        //     $scope.addedUsers.push($scope.userSelected);
        // }

        // $scope.$watch('userSelected', function() {
        //     checkIfValidSelection();
        //     checkForDuplicates();
        //     // addSelectionToModel(modelReference);
        //     collapseSwitcher.on();
        // });


        // $scope.removeMember = function(index) {
        //     $scope.addedUsers.splice(index, 1);
        //     if ($scope.addedUsers.length <= 0) {
        //         $scope.isCollapsed = true;
        //     }
        // };

        // $scope.selectGroup = function(groupName) {
        //     if (groupName === undefined || $scope.groups === undefined) {
        //         return;
        //     }
        //     var group = getGroup(groupName);
        //     if (group === -1) {
        //         return;
        //     }
        //     $scope.selectedGroupName = group.name;
        //     $scope.editedGroupName = group.name;
        //     $scope.addedUsers = [];
        //     $scope.addedUsers = group.members;
        //     $scope.showTrash = true;
        //     // $scope.openDetailPanel();
        // };
    }]);

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