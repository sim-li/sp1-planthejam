// TODO: Merge AddMember ETC with direct Groups
// Bug: Multiple Rename fails unless select happens
// Simplify, Patterns, Comment.

/**
 * @module myApp
 *
 * @author Simon Lischka
 * @author Sebastian Dass&eacute;
 */
angular.module('myApp')
    /**
     * The controller for the invite edit/creation view.
     *
     * @class inviteCtrl
     */
    .controller('inviteCtrl', ['$scope', '$log', '$location' /*, '$routeParams'*/ , 'restService', 'Invite', 'Survey', 'Group', 'Member', 'User', 'Type', 'TimeUnit', 'invitesPromise', 'groupsPromise', 'selectedInvitePromise', 'usersPromise',
        function($scope, $log, $location /*, $routeParams*/ , restService, Invite, Survey, Group, Member, User, Type, TimeUnit, invitesPromise, groupsPromise, selectedInvitePromise, usersPromise) {

            'use strict';

            // resolve the promises passed to this route
            $scope.selectedInvite = selectedInvitePromise ? new Invite(selectedInvitePromise) : new Invite({
                'survey': new Survey({
                    'name': 'Your survey',
                    'description': 'Say what it is all about',
                    'deadline': new Date()
                })
            });
            $scope.invites = Invite.importMany(invitesPromise);
            $scope.groups = Group.importMany(groupsPromise);
            // TODO - later on there sould be a REST-call like getTheFirstTenMatchingUsers for searching users from the database instead of getting all users
            $scope.users = User.importMany(usersPromise);


            // for now: some dummy users
            // $scope.users = [{
            //     name: 'Blackjack',
            //     email: 'bj@gmail.com'
            // }, {
            //     name: 'Bob',
            //     email: 'bob@gmail.com'
            // }, {
            //     name: 'Marie',
            //     email: 'marie@gmail.com'
            // }, {
            //     name: 'Sarah',
            //     email: 'sr@gmail.com'
            // }, {
            //     name: 'Simon',
            //     email: 'sm@gmail.com'
            // }, {
            //     name: 'Max',
            //     email: 'max@gmail.com'
            // }, {
            //     name: 'Sebastian',
            //     email: 'sb@gmail.com'
            // }];

            // $scope.selectedGroup = $scope.groups[0] || new Group();

            $scope.selectedUser = {
                name: ''
            };

            $scope.memberListIsCollapsed = true;
            $scope.showTrash = true;
            $scope.dt = new Date();
            $scope.showLiveButton = true;
            $scope.repeatedly = false;
            $scope.toOpened = false;
            $scope.fromOpened = false;

            // $scope.$watch('selectedGroup', function() {
            //     // if ($scope.selectedGroupName === $scope.editedGroupName) {
            //     if ($scope.selectedGroup.name === $scope.editedGroupName) {
            //         $scope.showTrash = true;
            //     } else {
            //         $scope.showTrash = false;
            //         changeGroupName($scope.selectedGroup.name, $scope.editedGroupName);
            //     }
            // });

            $scope.$watch('selectedUser', function() {
                // if ($scope.selectedUser === undefined || $scope.selectedUser.name === undefined) {
                if (!$scope.selectedUser.name) {
                    return;
                }
                $scope.memberListIsCollapsed = false;
                var members = $scope.selectedGroup.members;
                for (var i = 0, len = members.length; i < len; i++) {
                    if (members[i].user.oid === $scope.selectedUser.oid) {
                        return;
                    }
                }
                members.push(new Member({
                    user: $scope.selectedUser
                }));
            });

            // $scope.addGroup = function() {
            //     // if ($scope.groups.length > 0) {
            //     // if ($scope.selectedGroup) {
            //     changeGroupName($scope.editedGroupName, $scope.selectedGroup.name);
            //     // }
            //     $scope.groups.push(new Group({
            //         name: 'Copy of ' + $scope.selectedGroup.name, // FIXME
            //         members: $scope.selectedGroup.members
            //     }));
            //     $scope.selectedGroup = $scope.editedGroupName; // FIXME
            //     $scope.showTrash = true;
            // };

            $scope.addNewGroup = function() {
                restService.doSave(new Group({
                    name: 'Your new group'
                }));

                restService.doGetMany(Group)
                    .then(function(success) {
                        $scope.groups = Group.importMany(success);
                        selectFirstOrDefaultGroup();
                    } /*, function(error) { $log.log(error); }*/ );
            };

            $scope.deleteSelectedGroup = function() {
                if (!$scope.selectedGroup) {
                    return;
                }
                for (var i in $scope.groups) {
                    if ($scope.groups[i].oid === $scope.selectedGroup.oid) {
                        $scope.groups.splice(i, 1);
                    }
                }

                restService.doDelete($scope.selectedGroup)
                    .then(function(success) {
                        $scope.selectedGroup = $scope.groups[0] || new Group({
                            name: 'Your new group'
                        });
                    } /*, function(error) { $log.log(error); }*/ );
            };

            // $scope.deleteGroup = function() {
            //     var index = find($scope.groups, 'name', $scope.selectedGroup.name);
            //     if (index === -1) {
            //         return;
            //     }
            //     $scope.groups.splice(index, 1);
            //     selectFirstOrDefaultGroup();
            // };

            $scope.hideTrash = function() {
                $scope.showTrash = false;
            };

            $scope.switchDetailPanel = function() {
                if ($scope.memberListIsCollapsed) {
                    $scope.openDetailPanel();
                } else {
                    $scope.memberListIsCollapsed = true;
                }
            };

            $scope.openDetailPanel = function() {
                // if ($scope.addedUsers.length <= 0) {
                //     return;
                // }
                $scope.memberListIsCollapsed = false;
            };

            $scope.removeMember = function(index) {
                var members = $scope.selectedGroup.members;
                var member = members[index];
                // if (member.oid) {
                // restService.doDelete(member);
                // .then(function(success) {} /*, function(error) { $log.log(error); }*/ );
                // }
                members.splice(index, 1);
                if (members.length <= 0) {
                    $scope.memberListIsCollapsed = true;
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
                $scope.selectedGroup = group;
                $scope.editedGroupName = group.name;
                $scope.showTrash = true;
                // $scope.openDetailPanel();
            };

            $scope.clear = function() {
                $scope.dt = null;
            };

            // Disable weekend selection
            $scope.disabled = function(date, mode) {
                return (mode === 'day' && (date.getDay() === 0 || date.getDay() === 6));
            };

            $scope.toggleMin = function() {
                $scope.minDate = $scope.minDate ? null : new Date();
            };

            $scope.openTo = function($event) {
                $event.preventDefault();
                $event.stopPropagation();
                $scope.toOpened = !$scope.toOpened;
            };

            $scope.openFrom = function($event) {
                $event.preventDefault();
                $event.stopPropagation();
                $scope.fromOpened = !$scope.fromOpened;
            };

            $scope.saveInvite = function() {
                restService.doSave($scope.selectedInvite)
                    .then(function(success) {
                        $location.path('/cockpit');
                    } /*, function(error) { $log.log(error); }*/ );
            };

            // TODO rest service to save many groups
            $scope.saveGroups = function() {

                $log.log('Saving all groups');
                for (var i = 0; i < $scope.groups.length; i++) {
                    restService.doSave($scope.groups[i]);
                }
                $location.path('/invite');
            };

            var selectFirstOrDefaultGroup = function() {
                $scope.selectedGroup = $scope.groups[0] || new Group({
                    name: 'Your new group'
                });
                return $scope.selectedGroup;
            };

            var removeEmptyGroups = function() {
                var i = $scope.groups.length;
                while (i--) {
                    var group = $scope.groups[i];
                    if (group.members === undefined || group.members.length <= 0) {
                        $scope.groups.splice(i, 1);
                    }
                }
            };

            var changeGroupName = function(oldName, newName) {
                var index = find($scope.groups, 'name', oldName);
                if (index === -1) {
                    return index;
                }
                $scope.groups[index].name = newName;
                return newName;
            };

            var getGroup = function(name) {
                var index = find($scope.groups, 'name', name);
                if (index === -1) {
                    return index;
                }
                return $scope.groups[index];
            };

            // TODO simplify
            var find = function(array, key, value) {
                var i = array.length;
                while (i--) {
                    if (array[i][key] === undefined || array[i][key] !== value) {
                        continue;
                    }
                    return i;
                }
                return -1;
            };

            $scope.toggleMin();
            selectFirstOrDefaultGroup();
            // $scope.selectGroup($scope.editedGroupName);

        }
    ]);