/*
 * Ojektverwaltung-UI, SP1 SoSe 2014, Team: Comanche
 * (C)opyright Sebastian Dassé, Mat.-Nr. 791537, s50602@beuth-hochschule.de
 * 
 * Module: logged in controller
 */


"use strict";

angular.module("myApp")
    .controller("loggedInCtrl", ["$scope", "$log", "Survey", "dialogMap", 
        function($scope, $log, Survey, dialogMap) {

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


    }]);

