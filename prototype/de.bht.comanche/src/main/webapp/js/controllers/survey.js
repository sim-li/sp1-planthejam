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

            'use strict';

            /**
             * Retrieves all data loaded by the REST-service on page load.
             * $scope vars for directives and their controllers should be declared here
             * to be included in the REST operations for this page.
             */
            (function resolvePromises() {
                $scope.selectedInvite = selectedInvitePromise ?
                    new Invite(selectedInvitePromise) : Invite.createFor(currentUserPromise);
                // For group widget
                $scope.groups = Model.importMany(Group, groupsPromise);
                $scope.users = Model.importMany(User, usersPromise);
                // No connection to REST jet (widget will probably be discarded)
                $scope.timePeriods = TimePeriod.dummyTimePeriods();
            })();

            /**
             * Create simple variables needed to store UI states for components
             * that aren't grouped in directives.
             */
            (function createSimpleUIStateVariables() {
                $scope.repeatedly = false;
                $scope.showLiveButton = true;
            })();

            // NO!!!
            var refreshGroupsAndShowLast = function() {
                restService.doGetMany(Group)
                    .then(function(success) {
                        $scope.groups = Model.importMany(Group, success);
                        $scope.selectedGroup = $scope.groups[$scope.groups.length - 1];
                    } /*, function(error) { $log.log(error); }*/ );
            };

            /**
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
        }
    ]);