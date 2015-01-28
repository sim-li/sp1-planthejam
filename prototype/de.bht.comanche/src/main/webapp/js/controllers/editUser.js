/**
 * @module myApp
 *
 * @author Sebastian Dass&eacute;
 */
angular.module('myApp')
    /**
     * The controller for account/edit user view.
     *
     * @class editUserCtrl
     */
    .controller('editUserCtrl', ['$scope', '$location', '$log', 'restService', 'User', 'userPromise',
        function($scope, $location, $log, restService, User, userPromise) {

            'use strict';

            // resolve the promises passed to this route
            $scope.user = new User(userPromise);

            /**
             * Saves the edited user and switches to the cockpit view.
             *
             * @method saveEditedUser
             */
            $scope.saveEditedUser = function() {
                restService.updateUser($scope.user)
                    .then(function(success) {
                        $scope.user = new User(success);
                        $location.path('/cockpit');
                    } /*, function(error) { $log.error(error);}*/ );
            };

            /**
             * Cancels the user edit and switches to the cockpit view.
             *
             * @method cancelEditUser
             */
            $scope.cancelEditUser = function() {
                $location.path('/cockpit');
            };

            /**
             * Deletes the user account and logs out, i.e. switches to the login view.
             *
             * @method deleteUser
             */
            $scope.deleteUser = function() {

                // *** ask: are you sure you want to delete? ***

                var userName = $scope.user.name;
                restService.deleteUser($scope.user)
                    .then(function(success) {
                        $log.log(success);
                        restService.logout();
                        $log.log('Account for ' + userName + ' was deleted.');
                        $location.path('/');
                    } /*, function(error) {$log.error(error);}*/ );
            };

            $scope.saveGeneralAvailability = function() {
                return function(resultingTimePeriods) {
                    $scope.user.generalAvailability = resultingTimePeriods;
                    restService.updateUser($scope.user);
                };
            };

            //-- dummies -->
            // $scope.dummyPoss = [];
            // $scope.dummyPoss.push({
            //     startTime: new Date('Jan 23 2015 10:00'),
            //     durationMins: 120
            // });
            // $scope.dummyPoss.push({
            //     startTime: new Date('Jan 24 2015 6:00'),
            //     durationMins: 120
            // });
            // $scope.user.generalAvailability = $scope.dummyPoss;
            //<-- dummies --
        }
    ]);