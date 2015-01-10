/**
 * @module myApp
 *
 * @author Simon Lischka
 * @author Sebastian Dass&eacute;
 */
angular.module('myApp')
    /**
     * The controller for the survey edit/creation view.
     *
     * @class surveyCtrl
     */
    .controller('surveyCtrl', ['$location', '$log', '$scope', 'arrayUtil', 'currentUserPromise', 'Group',
        'groupsPromise', 'Invite', 'invitesPromise', 'Member', 'Model', 'restService', 'selectedInvitePromise',
        'Survey', 'TimePeriod', 'TimeUnit', 'Type', 'User', 'usersPromise',
        function($location, $log, $scope, arrayUtil, currentUserPromise, Group,
            groupsPromise, Invite, invitesPromise, Member, Model, restService, selectedInvitePromise,
            Survey, TimePeriod, TimeUnit, Type, User, usersPromise) {

            /**
             * Retrieves all data loaded by the REST-service on page load.
             * $scope vars for directives and their controllers should be declared here
             * to be included in the REST operations for this page.
             */
            'use strict';
            (function resolvePromises() {
                $scope.selectedInvite = selectedInvitePromise ?
                    new Invite(selectedInvitePromise) : Invite.createFor(currentUserPromise);
                $scope.invites = Model.importMany(Invite, invitesPromise);
                $scope.groups = Model.importMany(Group, groupsPromise);
                $scope.users = Model.importMany(User, usersPromise);
                // No connection to REST jet (widget will probably be discarded)
                $scope.timePeriods = TimePeriod.dummyTimePeriods();
            })();

            /**
             * Create simple variables needed to store UI states for components
             * that aren't refacotered to directives.
             *
             */
            (function createSimpleUIStateVariables() {
                $scope.repeatedly = false;
                $scope.showLiveButton = true;
            })();


            $scope.selectedUser = {
                name: ''
            };

            $scope.memberListIsCollapsed = true;
            $scope.showTrash = true;
            $scope.dt = new Date();

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

            var refreshGroupsAndShowLast = function() {
                restService.doGetMany(Group)
                    .then(function(success) {
                        $scope.groups = Model.importMany(Group, success);
                        $scope.selectedGroup = $scope.groups[$scope.groups.length - 1];
                    } /*, function(error) { $log.log(error); }*/ );
            };

            /**
             * Adds a new empty group to the user's groups.
             * The new group will immediately be persisted on the server.
             *
             * @method addNewGroup
             * @protected
             */
            $scope.addNewGroup = function() {
                restService.doSave(new Group())
                    .then(refreshGroupsAndShowLast());
            };

            /**
             * Deletes the selected group from the user's groups.
             * The group will immediately be deleted on the server.
             *
             * @method deleteSelectedGroup
             * @protected
             */
            $scope.deleteSelectedGroup = function() {
                if (!$scope.selectedGroup) {
                    return;
                }
                // delete selected on client
                for (var i = 0, len = $scope.groups.length; i < len; i++) {
                    if ($scope.groups[i].oid === $scope.selectedGroup.oid) {
                        $scope.groups.splice(i, 1);
                    }
                }

                // delete selected on server
                restService.doDelete($scope.selectedGroup)
                    .then(function(success) {
                        $scope.selectedGroup = $scope.groups[0] || new Group();
                    } /*, function(error) { $log.log(error); }*/ );

                // QUESTION maybe better to just delete on server and then refresh? - but then we have to wait for the server
            };

            /**
             * Hides the trash bin.
             *
             * @method description]
             * @protected
             */
            $scope.hideTrash = function() {
                $scope.showTrash = false;
            };

            // TODO -> QUESTION do we need both: switchDetailPanel and openDetailPanel?

            /**
             * Toggles the detail panel. ???
             *
             * @method switchDetailPanel
             * @protected
             */
            $scope.switchDetailPanel = function() {
                if ($scope.memberListIsCollapsed) {
                    $scope.openDetailPanel();
                } else {
                    $scope.memberListIsCollapsed = true;
                }
            };

            /**
             * Opens the detail panel.
             *
             * @method openDetailPanel
             * @protected
             */
            $scope.openDetailPanel = function() {
                // if ($scope.addedUsers.length <= 0) {
                //     return;
                // }
                $scope.memberListIsCollapsed = false;
            };

            /**
             * Removes the member with the specified index from the selected group.
             *
             * @method removeMember
             * @protected
             * @param  {Number} index the index of the selected member
             */
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

            /**
             * Selects one of the user's groups by name.
             *
             * @method selectGroup
             * @param  {String} groupName the name of the group to be selected
             * @protected
             */
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

            // TODO -> remove if unused
            /**
             * Clears the date. ???
             *
             * @method clear
             * @protected
             */
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

                // arrayUtil.forEach($scope.selectedInviteSurveyInvites, function(invite) { // <<<<<<<<<<<<< TODO
                //     restService.saveSurveyInvite($scope.selectedInvite, invite);
                // });
            };

            // TODO rest service to save many groups
            $scope.saveGroups = function() {

                $log.log('Saving all groups');
                arrayUtil.forEach($scope.groups, function(group) {
                    restService.doSave(group);
                });
                $location.path('/invite');
            };

            //____________________________________________________________________ aktuelle Baustelle _______________________
            $scope.attachSelectedGroupToInvite = function() {
                // $log.log($scope.selectedInviteSurveyInvites)
                $scope.selectedInvite.addParticipantsFromGroup($scope.selectedGroup);
                $log.log($scope.selectedInvite.survey)
                    // inv.addParticipantsFromGroup($scope.selectedGroup)
            };

            var selectFirstOrDefaultGroup = function() {
                $scope.selectedGroup = $scope.groups[0] || new Group({
                    name: 'Your new group'
                });
                return $scope.selectedGroup;
            };

            // TODO check: unused?
            var removeEmptyGroups = function() {
                var i = $scope.groups.length;
                while (i--) {
                    var group = $scope.groups[i];
                    if (group.members === undefined || group.members.length <= 0) {
                        $scope.groups.splice(i, 1);
                    }
                }
            };

            // TODO check: unused?
            var changeGroupName = function(oldName, newName) {
                var index = find($scope.groups, 'name', oldName);
                if (index === -1) {
                    return index;
                }
                $scope.groups[index].name = newName;
                return newName;
            };

            // TODO check: unused?
            var getGroup = function(name) {
                var index = find($scope.groups, 'name', name);
                if (index === -1) {
                    return index;
                }
                return $scope.groups[index];
            };

            // TODO check: unused?
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