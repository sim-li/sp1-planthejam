/*
 * Softwareprojekt SoSe/WiSe 2014, Team: Comanche
 * (C)opyright Sebastian Dassé, Mat.-Nr. 791537, s50602@beuth-hochschule.de
 *
 * Module: edit user controller
 */

'use strict';

angular.module('myApp')
    .controller('editUserCtrl', ['$scope', '$location', '$log', 'restService', 'User', 'userPromise',
        function($scope, $location, $log, restService, User, userPromise) {

            // resolve the promises passed to this route
            $scope.user = new User(userPromise);

            $scope.saveEditedUser = function() {
                restService.updateUser($scope.user)
                    .then(function(success) {
                        $scope.user = new User(success);
                        $location.path('/cockpit');
                    } /*, function(error) { $log.error(error);}*/ );
            };

            $scope.cancelEditUser = function() {
                $location.path('/cockpit');
            };

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

        }
    ]);