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
            $scope.invites = Model.importMany(Invite, invitesPromise);
            $scope.surveys = Model.importMany(Survey, surveysPromise);
            $scope.messages = messagesPromise;
            $log.log('messages: ', $scope.messages)
            $log.log('surveys: ', $scope.surveys)
                // $scope.surveys = Survey.getDummies(3);

            // preselects the first survey and invite in the list
            $scope.selectedInvite = $scope.invites[0];
            $scope.selectedSurvey = $scope.surveys[0];

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
                $scope.selectedSurvey = survey;
                $log.debug($scope.selectedSurvey);
            };

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
                // $log.debug($scope.selectedInvite);
            };


            /**
             * TODO to be changed to enums { UNDECIDED, ACCEPTED, INGORED }
             *
             * Sets the ignored status of the selected invite according to the
             * specified boolean value. Finally saves the invite on the server.
             *
             * @method setSelectedInviteStatus
             * @param {Status} status the status of the invite
             */
            $scope.setSelectedInviteStatus = function(status) {
                // TODO change color of button when it was pressed
                // TODO rename to ==>  $scope.setSelectedInviteStatus = function(status) {
                $scope.selectedInvite.setIgnored(status);
                restService.doSave($scope.selectedInvite);
            };
            // $scope.radioModel = $scope.selectedInvite.ignored ? 'ignore' : 'accept';

            var sendMessagesToParticipant = function() {

                /* FRAGE oder lieber Server-Methode ???
                --> restService.sendMessagesToParticipant($scope.selectedSurvey);
                */

                var selectedSurvey = $scope.selectedSurvey,
                    invites = selectedSurvey.invites,
                    message = 'For ' + selectedSurvey.name;
                if (selectedSurvey.success == Status.YES) {
                    message += 'the following date was determined: ' + selectedSurvey.determinedTimePeriod; // some extra formatting here?
                } else {
                    message += 'no date could be determined.';
                }
                arrayUtil.forEach(invites, function(invite) {
                    invite.user.messages.push(message);
                });
            };

            $scope.confirm = function() {
                $scope.selectedSurvey.success = Status.YES;
                sendMessagesToParticipant();
                restService.save($scope.selectedSurvey);
            };

            $scope.reject = function() {
                $scope.selectedSurvey.success = Status.NO;
                $scope.selectedSurvey.determinedTimePeriod = null;
                sendMessagesToParticipant();
                restService.save($scope.selectedSurvey);
            };

            //-- some dummies
            $scope.possibleTimePeriods = [
                new TimePeriod({
                    startTime: new Date('2014-11-10T11:00:00'),
                    durationMins: 120
                }), new TimePeriod({
                    startTime: new Date('2014-11-11T05:00:00'),
                    durationMins: 240
                }), new TimePeriod({
                    startTime: new Date('2014-11-13T10:00:00'),
                    durationMins: 360
                })
            ];
            $scope.resultingTimePeriods = [];

            $scope.saveAvailabilities = function() {
                $log.log($scope.resultingTimePeriods);
                // $scope.selectedInvite.possibleTimePeriods = ...
            };



            $scope.toggleSurveyDetails = function() {
                $scope.showSurveyDetails = true;
                return false;
            };

    $scope.toggleInviteDetails = function() {
        $scope.showSurveyDetails = false;
        return false;
    };
        }
    ]);