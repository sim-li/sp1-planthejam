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
    .controller('surveyCtrl', ['$location', '$log', '$scope', 'arrayUtil', 'Group', 'groupsPromise', 'Invite', 'Member',
        'Model', 'restService', 'selectedSurveyPromise', 'selectedSurveyInvitesPromise', 'Survey', 'TimePeriod',
        'TimeUnit', 'SurveyType', 'User', 'usersPromise', 'userPromise',

        function($location, $log, $scope, arrayUtil, Group, groupsPromise, Invite, Member,
            Model, restService, selectedSurveyPromise, selectedSurveyInvitesPromise, Survey, TimePeriod,
            TimeUnit, SurveyType, User, usersPromise, userPromise) {

            'use strict';
            $scope.host = new User(userPromise);
            console.log("host :", $scope.host);
            /*
             * Retrieve all data loaded by the REST-service before page load.
             * All $scope variables for directives and their controllers should be declared here.
             */
            $scope.selectedSurvey = new Survey(selectedSurveyPromise);
            $scope.selectedSurvey.invites = Model.importMany(Invite, selectedSurveyInvitesPromise);
            console.log('selected survey: ', $scope.selectedSurvey);

            // For group widget
            $scope.groups = Model.importMany(Group, groupsPromise);
            $scope.users = Model.importMany(User, usersPromise);
            $scope.TimeUnit = TimeUnit;

            /**
             * ???
             *
             * Create simple variables needed to store UI states for components that aren't grouped in directives.
             */
            $scope.showLiveButton = true;

            /**
             * The new group will immediately be persisted on the server.
             *
             * @method addNewGroup
             * @protected
             */
            // $scope.addNewGroup = function() {
            //     restService.doSave(new Group())
            //         .then(refreshGroupsAndShowLast());
            // };

            /**
             * Deletes the selected group from the user's groups.
             * The group will immediately be deleted on the server.
             *
             * @method deleteSelectedGroup
             * @protected
             */
            // $scope.deleteSelectedGroup = function() {
            //     if (!$scope.selectedGroup) {
            //         return;
            //     }
            //     // delete selected on client
            //     for (var i = 0, len = $scope.groups.length; i < len; i++) {
            //         if ($scope.groups[i].oid === $scope.selectedGroup.oid) {
            //             $scope.groups.splice(i, 1);
            //         }
            //     }

            //     // delete selected on server
            //     restService.doDelete($scope.selectedGroup)
            //         .then(function(success) {
            //             $scope.selectedGroup = $scope.groups[0] || new Group();
            //         } /*, function(error) { $log.log(error); }*/ );

            //     // QUESTION maybe better to just delete on server and then refresh? - but then we have to wait for the server
            // };

            // TODO rest service to save many groups
            // $scope.saveGroups = function() {
            //     $log.log('Saving all groups');
            //     arrayUtil.forEach($scope.groups, function(group) {
            //         restService.doSave(group);
            //     });
            //     $location.path('/invite');
            // };

            // $scope.attachSelectedGroupToInvite = function() {
            //     // $log.log($scope.selectedInviteSurveyInvites)
            //     $scope.selectedInvite.addParticipantsFromGroup($scope.selectedGroup);
            //     $log.log($scope.selectedInvite.survey)
            //         // inv.addParticipantsFromGroup($scope.selectedGroup)
            // };

            // var selectFirstOrDefaultGroup = function() {
            //     $scope.selectedGroup = $scope.groups[0] || new Group({
            //         name: 'Your new group'
            //     });
            //     return $scope.selectedGroup;
            // };

            $scope.isRecurring = function() {
                return $scope.selectedSurvey.type == SurveyType.RECURRING;
            };

            $scope.setRecurring = function() {
                $scope.selectedSurvey.type = SurveyType.RECURRING;
            };

            $scope.setOneTime = function() {
                $scope.selectedSurvey.type = SurveyType.ONE_TIME;
            };

            $scope.saveSurvey = function() {
                $log.log('selected survey: ', $scope.selectedSurvey);
                /* Export of invites would be better to be placed inside the survey class, but not possible because of circular dependency. */
                $scope.selectedSurvey.invites = Invite.exportMany($scope.selectedSurvey.invites);
                restService.doSave($scope.selectedSurvey)
                    .then(function(success) {
                        $location.path('/cockpit');
                    });
            };

            $scope.cancel = function() {
                $location.path('/cockpit');
            };

        }
    ]);