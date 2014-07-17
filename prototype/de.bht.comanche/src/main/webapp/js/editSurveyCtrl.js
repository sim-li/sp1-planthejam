/*
 * Ojektverwaltung-UI, SP1 SoSe 2014, Team: Comanche
 * (C)opyright Sebastian Dassé, Mat.-Nr. 791537, s50602@beuth-hochschule.de
 * 
 * Module: edit survey controller
 */


"use strict";

angular.module("myApp")
    .controller("editSurveyCtrl", ["$scope", "$log", "DatePickerDate", "Survey", "restService", "dialogMap", 
        function($scope, $log, DatePickerDate, Survey, restService, dialogMap) {

        $scope.cancelEditSurvey = function() {
            $scope.session.tempSurvey = "";
            $scope.session.addingSurvey = false;
            $scope.session.state.isVal = dialogMap.SURVEY_SELECTION;
            $log.log($scope.session.selectedSurvey);
            $log.log($scope.session.user);
        };

        $scope.saveSurvey = function() {
            var _survey = $scope.session.tempSurvey;


            // *** test the change to async ***

            restService.saveSurvey(_survey)
                .then(function(survey) {

                    if (!$scope.session.addingSurvey) {
                        removeElementFrom(_survey, $scope.session.user.surveys);
                    }

                    $scope.session.user.surveys.push(survey);
                    $scope.session.user.surveys.sort(function(a, b){ return a.name.localeCompare(b.name) });
                    $scope.session.selectedSurvey = survey;
                    $scope.session.tempSurvey = "";
                    $scope.session.addingSurvey = false;
                    $scope.session.state.isVal = dialogMap.SURVEY_SELECTION;

                }, function(error) {
                    $log.error(error);
                    $scope.warnings.central = error;
                    // $scope.initSession();
                }, function(notification) {
                    // $log.log(notification); // for future use
                });


            // var _fromSaveSurvey = restService.saveSurvey(_survey);
            //-- falls kein Fehlschlag:
            // _survey = _fromSaveSurvey.survey;

            // if (!$scope.session.addingSurvey) {
            //     removeElementFrom(_survey, $scope.session.user.surveys);
            // }

            // $scope.session.user.surveys.push(_survey);
            // $scope.session.user.surveys.sort(function(a, b){ return a.name.localeCompare(b.name) });

            // $scope.session.selectedSurvey = _survey;
            // $scope.session.tempSurvey = "";
            // $scope.session.addingSurvey = false;


            //---------------- ***
            console.log($scope.session.selectedSurvey);
            console.log($scope.session.user);
            //---------------- ***
        };


        var removeElementFrom = function(element, array) {
            var index = array.indexOf(element);
            if (index > -1) {
                array.splice(index, 1);
            }
        };

        // var removeSelectedSurvey = function() {
        //     removeElementFrom($scope.session.selectedSurvey, $scope.session.user.surveys);
        //     $scope.session.selectedSurvey = "";
        // };

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
                    removeElementFrom(_survey, $scope.session.user.surveys);
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

        $scope.removeTimeperiodFromTempSurvey = function(timeperiod) {
            removeElementFrom(timeperiod, $scope.session.tempSurvey.possibleTimeperiods);
        };

        $scope.addTimeperiodToTempSurvey = function() {
            $scope.session.tempSurvey.possibleTimeperiods.push({ "startTime": new DatePickerDate(new Date()), "durationInMins": 60 });
        };


    }]);

