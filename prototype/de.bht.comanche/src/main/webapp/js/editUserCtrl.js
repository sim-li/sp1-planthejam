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