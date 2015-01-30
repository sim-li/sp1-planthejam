/**
 * @module myApp
 *
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

            // resolve the promises passed to this route
            $scope.host = new User(userPromise);
            $scope.selectedSurvey = new Survey(selectedSurveyPromise);
            $scope.selectedSurvey.invites = Model.importMany(Invite, selectedSurveyInvitesPromise);
            $log.debug('selected survey: ', $scope.selectedSurvey);

            // For group widget
            $scope.groups = Model.importMany(Group, groupsPromise);
            $scope.users = Model.importMany(User, usersPromise);
            $scope.TimeUnit = TimeUnit;

            /**
             * @method isRecurring
             * @return {Boolean} true if the survey is recurring
             */
            $scope.isRecurring = function() {
                return $scope.selectedSurvey.type == SurveyType.RECURRING;
            };

            /**
             * Sets the survey to RECURRING.
             *
             * @method setRecurring
             */
            $scope.setRecurring = function() {
                $scope.selectedSurvey.type = SurveyType.RECURRING;
            };

            /**
             * Sets the survey to ONE_TIME.
             *
             * @method setOneTime
             */
            $scope.setOneTime = function() {
                $scope.selectedSurvey.type = SurveyType.ONE_TIME;
            };

            /**
             * Saves the survey and switches to the cockpit.
             *
             * @method saveSurvey
             */
            $scope.saveSurvey = function() {
                $log.log('selected survey: ', $scope.selectedSurvey);
                /* Export of invites would be better to be placed inside the survey class, but not possible because of circular dependency. */
                $scope.selectedSurvey.invites = Invite.exportMany($scope.selectedSurvey.invites);
                restService.doSave($scope.selectedSurvey)
                    .then(function(success) {
                        $location.path('/cockpit');
                    });
            };

            /**
             * Cancels the current survey edit and switches to the cockpit.
             *
             * @method cancel
             */
            $scope.cancel = function() {
                $location.path('/cockpit');
            };

        }
    ]);