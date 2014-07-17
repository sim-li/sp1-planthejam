/*
 * Ojektverwaltung-UI, SP1 SoSe 2014, Team: Comanche
 * (C)opyright Sebastian Dassé, Mat.-Nr. 791537, s50602@beuth-hochschule.de
 * 
 * Module: logged in controller
 */


"use strict";

angular.module("myApp")
    .controller("loggedInCtrl", ["$scope", "$log", "Survey", "restService", "dialogMap", "util", 
        function($scope, $log, Survey, restService, dialogMap, util) {

        $scope.logout = function() {

            // *** if anything needs to be done on the server for logout, do it now ***

            $log.log("Logout erfolgreich.");
            $scope.initSession();
            $scope.showRegisterDialog = false;
        };

        
        $scope.editUser = function() {
            $scope.session.tempUser = angular.copy($scope.session.user);
            $scope.session.state.isVal = dialogMap.USER_EDIT;
        };


        $scope.editContacts = function() {
            $log.warn("editContacts() not implemented");
        };


        $scope.editSurvey = function() {
            if (!$scope.session.selectedSurvey) {
                $log.log("Keine Terminumfrage ausgewaehlt.");
                return;
            }
            $scope.session.tempSurvey = new Survey($scope.session.selectedSurvey);
            $scope.session.state.isVal = dialogMap.SURVEY_EDIT;
            $log.log($scope.session.user);
        };

        $scope.addSurvey = function() {
            $scope.session.tempSurvey = new Survey();
            $scope.session.addingSurvey = true;
            $scope.session.state.isVal = dialogMap.SURVEY_EDIT;
        };
        
        $scope.deleteSelectedSurvey = function() {

            // *** ask: are you sure you want to delete? ***

            var _survey = $scope.session.selectedSurvey;
            if (!_survey) {
                $log.log("Keine Terminumfrage ausgewaehlt.");
                return;
            }

            var _survey = $scope.session.user;
            restService.deleteSurvey(_survey.oid)
                .then(function(success) {
                    $log.log(success);
                    util.removeElementFrom(_survey, $scope.session.user.surveys);
                    $scope.session.selectedSurvey = $scope.session.user.surveys[0] || "";
                    $scope.session.tempSurvey = "";
                }, function(error) {
                    $log.error(error);
                    $scope.warnings.central = error;
                }, function(notification) {
                    // $log.log(notification); // for future use
                });

            
            //-------------------- ***
            console.log($scope.session.selectedSurvey);
            console.log($scope.session.user);
            //-------------------- ***
        };


    }]);

