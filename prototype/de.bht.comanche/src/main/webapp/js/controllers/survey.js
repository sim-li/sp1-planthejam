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
    .controller('surveyCtrl', ['$location', '$log', '$scope', 'arrayUtil', /*'currentUserPromise', */ 'Group',
        'groupsPromise', 'Invite', /*'invitesPromise', */ 'Member', 'Model', 'restService', /*'selectedInvitePromise',*/
        'selectedSurveyPromise', 'selectedSurveyInvitesPromise', 'Survey', 'TimePeriod', 'TimeUnit', 'SurveyType',
        'User', 'usersPromise',
        function($location, $log, $scope, arrayUtil, /*currentUserPromise, */ Group,
            groupsPromise, Invite, /*invitesPromise, */ Member, Model, restService, /*selectedInvitePromise,*/
            selectedSurveyPromise, selectedSurveyInvitesPromise, Survey, TimePeriod, TimeUnit, SurveyType,
            User, usersPromise) {

            'use strict';

            /*
             * Retrieve all data loaded by the REST-service before page load.
             * All $scope variables for directives and their controllers should be declared here.
             */
            $scope.selectedSurvey = new Survey(selectedSurveyPromise);
            $log.log($scope.selectedSurvey)
            $scope.selectedSurveyInvites = Model.importMany(Invite, selectedSurveyInvitesPromise);
            // For group widget
            $scope.groups = Model.importMany(Group, groupsPromise);
            $scope.users = Model.importMany(User, usersPromise);
            $scope.TimeUnit = TimeUnit;
            // No connection to REST yet (widget will probably be discarded)
            // $scope.timePeriods = TimePeriod.dummyTimePeriods();

            /**
             * Create simple variables needed to store UI states for components that aren't grouped in directives.
             */
            $scope.showLiveButton = true;

            // NO!!! the group widget shall take care of all this
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

            $scope.saveSurvey = function() {
                $log.log($scope.selectedSurvey);
                // HACK -> Move to survey class when possible.
                $scope.selectedSurvey.invites = Invite.exportMany($scope.selectedSurvey.invites);
                restService.doSave($scope.selectedSurvey)
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

            $scope.isRecurring = function() {
                return $scope.selectedSurvey.type == SurveyType.RECURRING;
            };

            $scope.setRecurring = function() {
                $scope.selectedSurvey.type = SurveyType.RECURRING;
            };

            $scope.setOneTime = function() {
                $scope.selectedSurvey.type = SurveyType.ONE_TIME;
            };

            /**********************************************************************************************/

            /* config object */
            // $scope.uiConfig = {
            //     calendar: {
            //         height: 450,
            //         defaultView: 'agendaWeek',
            //         header: {
            //             left: 'month,agendaWeek,agendaDay',
            //             center: 'title',
            //             right: 'today prev,next'
            //         },
            //         editable: true,
            //         selectable: true,
            //         select: function(startDate, endDate) {
            //             /* correct timezoneoffset */
            //             var timeZoneOffset = new Date().getTimezoneOffset();
            //             var start = new Date(startDate + timeZoneOffset * 60000);
            //             var end = new Date(endDate + timeZoneOffset * 60000);

            //             $scope.possibleTimePeriods.push({
            //                 start: start,
            //                 end: end
            //             });
            //         },
            //         events: $scope.possibleTimePeriods
            //     }
            // };

            $scope.renderCalendar = function() {
                $scope.surveyCalendar.fullCalendar('render');
            };

            $scope.saveAvailabilities = function() {
                // $log.log($scope.resultingTimePeriods);
                // $scope.selectedInvite.possibleTimePeriods = ...
            };
        }
    ]);