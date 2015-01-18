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
    .controller('cockpitCtrl', ['$location', '$log', '$scope', 'arrayUtil', 'Invite', 'invitesPromise', 'Model',
        'restService', 'surveysPromise', 'Status', 'Survey',
        function($location, $log, $scope, arrayUtil, Invite, invitesPromise, Model,
            restService, surveysPromise, Status, Survey) {

            'use strict';

            // resolve the promises passed to this route
            $scope.invites = Model.importMany(Invite, invitesPromise);
            $scope.surveys = Model.importMany(Survey, surveysPromise);
            $log.log($scope.surveys)
                // $scope.surveys = Survey.getDummies(3);

            // preselects the first survey and invite in the list
            $scope.selectedInvite = $scope.invites[0];
            $scope.selectedSurvey = $scope.surveys[0];

            $scope.Status = Status;


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

            /**********************************************************************************************/
            // dummy possible TimePeriods for test
            $scope.possibleTimePeriods = [{
                start: '2014-11-10T11:00:00',
                end: '2014-11-10T13:00:00'
            }, {
                start: '2014-11-11T05:00:00',
                end: '2014-11-11T09:00:00'
            }, {
                start: '2014-11-13T10:00:00',
                end: '2014-11-13T16:00:00'
            }]

            $scope.concreteAvailabilities = [];

            // placeholder all events for rendering ui-calendar
            $scope.timePeriodArray = [];
            // copy all elements of possibleTimePeriods to timePeriodArray
            for (var i = 0; i < $scope.possibleTimePeriods.length; i++) {
                $scope.timePeriodArray.push({
                    id: 'possible',
                    rendering: 'background',
                    start: $scope.possibleTimePeriods[i].start,
                    end: $scope.possibleTimePeriods[i].end
                });
            }

            /* config object */
            $scope.uiConfig = {
                calendar: {
                    height: 550,
                    defaultView: 'agendaWeek',
                    defaultDate: '2014-11-12',
                    header: {
                        left: 'month,agendaWeek,agendaDay',
                        center: 'title',
                        right: 'today prev,next'
                    },
                    selectable: true,
                    select: function(startDate, endDate) {
                        /* correct timezoneoffset */
                        var timeZoneOffset = new Date().getTimezoneOffset();
                        var start = new Date(startDate + timeZoneOffset * 60000);
                        var end = new Date(endDate + timeZoneOffset * 60000);

                        $scope.concreteAvailabilities.push({
                            start: start,
                            end: end
                        })
                        $scope.timePeriodArray.push({
                            start: start,
                            end: end,
                            editable: true,
                            durationEditable: true,
                            constraint: 'possible'
                        });
                    },
                    selectConstraint: 'possible',
                    events: $scope.timePeriodArray
                }
            };

            $scope.eventSources = [];
        }
    ]);