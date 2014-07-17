/*
 * Ojektverwaltung-UI, SP1 SoSe 2014, Team: Comanche
 * (C)opyright Sebastian Dassé, Mat.-Nr. 791537, s50602@beuth-hochschule.de
 * 
 * Module: edit survey controller
 */


"use strict";

angular.module("myApp")
    .controller("editSurveyCtrl", ["$scope", "$log", "DatePickerDate", "Survey", "restService", "dialogMap", "util", 
        function($scope, $log, DatePickerDate, Survey, restService, dialogMap, util) {

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
                        util.removeElementFrom(_survey, $scope.session.user.surveys);
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
            //     util.removeElementFrom(_survey, $scope.session.user.surveys);
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
        
        $scope.removeTimeperiodFromTempSurvey = function(timeperiod) {
            util.removeElementFrom(timeperiod, $scope.session.tempSurvey.possibleTimeperiods);
        };

        $scope.addTimeperiodToTempSurvey = function() {
            $scope.session.tempSurvey.possibleTimeperiods.push({ "startTime": new DatePickerDate(new Date()), "durationInMins": 60 });
        };


    }]);

