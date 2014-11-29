// TODO: Merge AddMember ETC with direct Groups
// Bug: Multiple Rename fails unless select happens
// Simplify, Patterns, Comment.

'use strict';

angular.module('myApp')
    .controller('inviteCtrl', ['$scope', '$log', '$location' /*, '$routeParams'*/ , 'restService', 'Invite', 'Survey', 'Group', 'Member', 'User', 'Type', 'TimeUnit', 'invites', 'groups', 'selectedInvite', 'users',
        function($scope, $log, $location /*, $routeParams*/ , restService, Invite, Survey, Group, Member, User, Type, TimeUnit, invites, groups, selectedInvite, users) {

            // resolve the promises passed to this route
            $scope.selectedInvite = selectedInvite ? new Invite(selectedInvite) : new Invite({
                'survey': new Survey({
                    'name': 'Your survey',
                    'description': 'Say what it is all about',
                    'deadline': new Date()
                })
            });
            $scope.invites = Invite.importMany(invites);
            $scope.groups = Group.importMany(groups);
            $scope.users = User.importMany(users);

            // $log.log('selectedInvite: ');
            // $log.log($scope.selectedInvite);

            // TODO - the users should be passed when the route is called
            //      - later there sould be a REST-call like getTheFirstTenMatchingUsers for searching users from the database
            // $scope.users = users;
            // TODO - the selected invite should be passed when the route is called
            // $scope.invite = invite;

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

            // $scope.surveyTitle = 'Lets have a beer, guys';
            // $scope.surveyDescription = 'This will a a really casual get together with the uppermose style etiquette. Eventhough there' + 'will be beer involved, we will not get to the limits of our physical capacities.';
            $scope.editedGroupName = '';
            $scope.selectedGroupName = '';
            $scope.showTrash = true;
            $scope.addedUsers = [];
            $scope.isCollapsed = true;
            $scope.userSelected = undefined;
            $scope.dt = new Date();
            $scope.showLiveButton = true;
            $scope.repeatedly = false;
            $scope.toOpened = false;
            $scope.fromOpened = false;

            $scope.$watch('editedGroupName', function() {
                if ($scope.selectedGroupName === $scope.editedGroupName) {
                    $scope.showTrash = true;
                } else {
                    $scope.showTrash = false;
                    changeGroupName($scope.selectedGroupName, $scope.editedGroupName);
                }
            });

            $scope.$watch('userSelected', function() {
                if ($scope.userSelected === undefined || $scope.userSelected.name === undefined) {
                    return;
                }
                for (var i = 0, len = $scope.addedUsers.length; i < len; i++) {
                    if ($scope.addedUsers[i] === $scope.userSelected) {
                        $scope.isCollapsed = false;
                        return;
                    }
                }
                $scope.addedUsers.push($scope.userSelected);
                $scope.isCollapsed = false;
            });

            $scope.addGroup = function() {
                if ($scope.groups.length <= 0) {
                    $scope.groups.push({
                        name: $scope.editedGroupName,
                        members: $scope.addedUsers
                    });
                    $scope.selectedGroupName = $scope.editedGroupName;
                    $scope.showTrash = true;
                    return;
                }
                changeGroupName($scope.editedGroupName, $scope.selectedGroupName);
                $scope.groups.push(new Group({
                    name: $scope.editedGroupName,
                    members: $scope.addedUsers
                }));
                $scope.showTrash = true;
            };

            $scope.deleteGroup = function() {
                var index = find($scope.groups, 'name', $scope.selectedGroupName);
                if (index === -1) {
                    return;
                }
                $scope.groups.splice(index, 1);
                setDefaultGroup();
            };

            $scope.hideTrash = function() {
                $scope.showTrash = false;
            };

            $scope.switchDetailPanel = function() {
                if ($scope.isCollapsed) {
                    $scope.openDetailPanel();
                } else {
                    $scope.isCollapsed = true;
                }
            };

            $scope.openDetailPanel = function() {
                if ($scope.addedUsers.length <= 0) {
                    return;
                }
                $scope.isCollapsed = false;
            };

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

                // ** HACK ** hard coded adding of a user to the first group
                $scope.groups[0].members.push(new Member({
                    user: new User($scope.users[3])
                }))

                $log.log('Clicked save groups')
                for (var i = 0; i < $scope.groups.length; i++) {
                    restService.doSave($scope.groups[i]);
                }
                $location.path('/invite');
            };

            var setDefaultGroup = function() {
                var hasEntries = $scope.groups.length > 0 && $scope.groups[0].name !== undefined;
                var groupName;
                if (hasEntries) {
                    groupName = $scope.groups[0].name;
                    $scope.addedUsers = $scope.groups[0].members;
                } else {
                    groupName = '[New group]';
                    $scope.addedUsers = [];
                }
                $scope.editedGroupName = groupName;
                return groupName;
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
            setDefaultGroup();
            $scope.selectGroup($scope.editedGroupName);

        }
    ]);