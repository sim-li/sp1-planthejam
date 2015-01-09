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
    .controller('cockpitCtrl', ['$location', '$log', '$scope', 'Invite', 'invitesPromise', 'Model', 'restService',
        'surveysPromise', 'Survey',
        function($location, $log, $scope, Invite, invitesPromise, Model, restService,
            surveysPromise, Survey) {

            'use strict';

            // resolve the promises passed to this route
            $scope.invites = Model.importMany(Invite, invitesPromise);
            // $scope.surveys = Model.importMany(Survey, surveysPromise);
            $scope.surveys = Survey.getDummies(3);

            // preselects the first survey and invite in the list
            $scope.selectedInvite = $scope.invites[0];
            $scope.selectedSurvey = $scope.surveys[0];


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
                // $log.debug($scope.selectedSurvey);
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
             * @method setSelectedInviteIgnored
             * @param {Boolean} ignored the status of the invite
             */
            // $scope.radioModel = $scope.selectedInvite.ignored ? 'ignore' : 'accept';
            $scope.setSelectedInviteIgnored = function(ignored) {
                // TODO rename to ==>  $scope.setSelectedInviteStatus = function(status) {
                $scope.selectedInvite.setIgnored(ignored);
                restService.doSave($scope.selectedInvite);
            };
        }
    ]);