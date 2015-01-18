<<<<<<< HEAD
/*
 * Softwareprojekt SoSe/WiSe 2014, Team: Comanche
 * (C)opyright Sebastian Dassé, Mat.-Nr. 791537, s50602@beuth-hochschule.de
 *
 * Module: edit user controller
 */


"use strict";

angular.module("myApp")
    .controller("editUserCtrl", ["$scope", "$log", "Survey", "restService", "dialogMap",
        function($scope, $log, Survey, restService, dialogMap) {

        $scope.saveEditedUser = function() {
            var _user = $scope.session.tempUser;
            restService.updateUser(_user)
                .then(function(success) {
                    $log.log(success);
                    $scope.session.user = _user;
                    $scope.session.state.isVal = dialogMap.SURVEY_SELECTION;
                }, function(error) {
                    $log.error(error);
                    $scope.warnings.central = error;
                    $scope.cancelEditUser();
                }, function(notification) {
                    // $log.log(notification); // for future use
                });
        };

        $scope.cancelEditUser = function() {
            $log.log("Bearbeitung der Kontodaten abgebrochen.");
            $scope.session.tempUser = undefined;
            $scope.session.state.isVal = dialogMap.SURVEY_SELECTION;
        };

        $scope.deleteUser = function() {

            // *** ask: are you sure you want to delete? ***

            var _user = $scope.session.user;
            restService.deleteUser(_user)
                .then(function(success) {
                    $log.log(success);
                    $scope.initSession();
                    $scope.showRegisterDialog = false;
                }, function(error) {
                    $log.error(error);
                    $scope.warnings.central = error;
                }, function(notification) {
                    // $log.log(notification); // for future use
                });
        };


    }]);
=======
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

        }
    ]);
>>>>>>> 28717c5aa782520e8b4a53c98880b3d839cc4135
