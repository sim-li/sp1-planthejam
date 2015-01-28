/**
 * @module myApp
 *
 * @author Sebastian Dass&eacute;
 */
angular.module('myApp')
    /**
     * The controller for the cockpit view.
     *
     * @class cockpitCtrl
     */
    .controller('cockpitCtrl', ['$location', '$log', '$scope', 'arrayUtil', 'Invite', 'invitesPromise',
        'messagesPromise', 'Model', 'restService', 'surveysPromise', 'Status', 'Survey', 'TimePeriod',
        function($location, $log, $scope, arrayUtil, Invite, invitesPromise,
            messagesPromise, Model, restService, surveysPromise, Status, Survey, TimePeriod) {

            'use strict';

            // resolve the promises passed to this route
            $scope.surveys = Model.importMany(Survey, surveysPromise);
            $scope.invites = Model.importMany(Invite, invitesPromise);
            $scope.messages = messagesPromise;

            $log.log('invites: ', $scope.invites)
            $log.log('surveys: ', $scope.surveys)
            $log.log('messages: ', $scope.messages)
                // $scope.surveys = Survey.getDummies(3);

            // preselects the first survey and invite in the list
            $scope.selectedInvite = $scope.invites[0];
            // $scope.selectedInvite.survey.possibleTimePeriods.push(new TimePeriod({
            //     startTime: new Date(),
            //     durationMins: 120
            // }))

            $scope.selectedSurvey = $scope.surveys[0];


//            // -------- HACK: Dummies ------------------------------------------------------>
//            if ($scope.selectedInvite && $scope.selectedInvite.survey) {
//                $scope.selectedInvite.survey.algoChecked = true;
//                $scope.selectedInvite.survey.success = Status.YES;
//                // $scope.selectedInvite.survey.success = Status.NO;
//                // $scope.selectedInvite.survey.success = Status.UNDECIDED;
//                $scope.selectedInvite.survey.determinedTimePeriod = new TimePeriod({
//                    startTime: new Date(),
//                    durationMins: 90
//                });
//                $log.debug('hacked invite.survey: ', $scope.selectedInvite.survey)
//            }
//            if ($scope.selectedSurvey) {
//                $scope.selectedSurvey.algoChecked = true;
//                $scope.selectedSurvey.success = Status.YES;
//                // $scope.selectedSurvey.success = Status.NO;
//                // $scope.selectedSurvey.success = Status.UNDECIDED;
//                $scope.selectedSurvey.determinedTimePeriod = new TimePeriod({
//                    startTime: new Date(),
//                    durationMins: 90
//                });
//                $log.debug('hacked survey: ', $scope.selectedSurvey)
//            }
            // <------- HACK --------------------------------------------------------------

            $scope.showSurveyDetails = true;


            /**
             * Switches to the survey creation view to create a new survey.
             *
             * @method addSurvey
             */
            $scope.addSurvey = function() {
                $location.path('/survey');
            };

            /**
             * Selects the specified survey.
             *
             * @method selectSurvey
             * @param  {Survey} survey the survey
             */
            $scope.selectSurvey = function(survey) {
                if (!survey) return;
                $scope.selectedSurvey = survey;
                restService.getSurveyInvites(survey.oid)
                    .then(function(invites) {
                        $scope.selectedSurvey.invites = Model.importMany(Invite, invites);
                    });
                $log.debug($scope.selectedSurvey);
            };

            /** Preselect the first survey on page load and get its invites. */
            $scope.selectSurvey($scope.surveys[0]);

            /**
             * Switches to the survey edit view to edit the selected survey.
             *
             * @method editSurvey
             */
            $scope.editSurvey = function() {
                if (!$scope.selectedSurvey) {
                    $log.log('Keine Terminumfrage ausgewaehlt.');
                    return;
                }
                $location.path('/survey/' + $scope.selectedSurvey.oid);
            };

            /**
             * Deletes the selected survey and refreshes the cockpit view.
             *
             * @method deleteSelectedSurvey
             */
            $scope.deleteSelectedSurvey = function() {
                // if (!$scope.selectedSurvey) {
                //     $log.log('Keine Terminumfrage ausgewaehlt.');
                //     return;
                // }
                restService.doDelete($scope.selectedSurvey)
                    .then(function(success) {
                        $location.path('/cockpit');
                    } /*, function(error) { $log.log(error); }*/ );
            };


            /**
             * Selects the specified invite.
             *
             * @method selectInvite
             * @param  {Invite} invite the invite
             */
            $scope.selectInvite = function(invite) {
                $scope.selectedInvite = invite;
                $log.debug('selectInvite(): ', $scope.selectedInvite); // for debugging
            };


            /**
             * Sets the ignored status of the selected invite according to the
             * specified tribool value. Finally saves the invite on the server.
             *
             * @method setSelectedInviteIgnoredStatus
             * @param {Status} status the status of the invite
             */
            $scope.setSelectedInviteIgnoredStatus = function(status) {
                $scope.selectedInvite.setIgnored(status);
                restService.doSave($scope.selectedInvite);
            };

            var sendMessagesToParticipant = function() {
                restService.notifyParticipants($scope.selectedSurvey.oid)
                    .then(function(success) {
                    	$location.path('/cockpit');
                    });
            };

            $scope.confirm = function() {
            	$scope.selectedSurvey.success = Status.YES;
                restService.doSave($scope.selectedSurvey)
                    .then(function() {
                         sendMessagesToParticipant(); 
                    });
            };

            $scope.reject = function() {
                $scope.selectedSurvey.success = Status.NO;
                $scope.selectedSurvey.determinedTimePeriod = TimePeriod.NÙLL();
                restService.doSave($scope.selectedSurvey)
                	.then(function() {
                		sendMessagesToParticipant();
                	});
            };

            $scope.toggleSurveyDetails = function() {
                $scope.showSurveyDetails = true;
            };

            $scope.toggleInviteDetails = function() {
                $scope.showSurveyDetails = false;
            };

            $scope.saveSelectedInvite = function() {
                return function(resultingTimePeriods) {
                    $scope.selectedInvite.concreteAvailability = resultingTimePeriods;
                    restService.doSave($scope.selectedInvite);
                };
            };

        }
    ]);