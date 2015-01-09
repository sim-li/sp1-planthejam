'use strict';

angular.module('myApp')
    .controller('groupsCtrl', ['$scope', '$modal', '$log', 'restService', function($scope, $modal, $log, restService) {
        $scope.test = 'My test!';
        $scope.items = ['item1', 'item2', 'item3'];
        $scope.open = function(size) {
            console.log('OPEN called. ');
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
    }]);

// Please note that $modalInstance represents a modal window (instance) dependency.
// It is not the same as the $modal service used above.

angular.module('myApp')
    .controller('groupsModalCtrl', function($scope, $modalInstance, groups, users) {
        $scope.test = 'My test!';
        $scope.groups = groups;
        $scope.users = users;
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