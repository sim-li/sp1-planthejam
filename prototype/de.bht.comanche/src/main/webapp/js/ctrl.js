/*
 * Ojektverwaltung-UI, SP1 SoSe 2014, Team: Comanche
 * (C)opyright Sebastian Dassé, Mat.-Nr. 791537, s50602@beuth-hochschule.de
 * 
 * Module: main controller
 */


"use strict";

angular.module("myApp", ["datePickerDate", "survey", "constants", "restModule"])
    .controller("Ctrl", ["$scope", "$log", "$filter", "DatePickerDate", "Survey", "TimeUnit", "Type", "patterns", "restService", 
        function($scope, $log, $filter, DatePickerDate, Survey, TimeUnit, Type, patterns, restService) {

        // make services available for the use in html
        $scope.$log = $log;
        $scope.TimeUnit = TimeUnit;
        $scope.Type = Type;
        $scope.patterns = patterns;
        
        $scope.session = {};
        $scope.warnings = {};

        /*
         * returns an initialized user
         */
        var getInitUser = function() {
            var user = {
                "oid": "", 
                "name": "", 
                "password": "", 
                "email": "", 
                "tel": "", 
                // "surveys": getDummySurveyList() // *** replace list of dummy surveys by real data from server ***
                "surveys": [{}] // empty list for debugging
            };
            $log.log("user initialized");
            return user;
        };

        /*
         * initializes the session
         */
        var initSession = function() {
            $scope.session = {
                "user": getInitUser(), 
                "isLoggedIn": false
            }
            $log.log("session initialized");
            $log.log($scope.session);
        };
        initSession();


        $scope.discardWarnings = function() {
            $scope.warnings = {};
        };

        $scope.toggleLoginDialog = function() {
            $scope.showRegisterDialog = !$scope.showRegisterDialog;
            initSession();
        };


        var loginIsValidFor = function(user) {
            if (!user.name) {
                $log.log("Benutzername fehlt.");
                return false;
            }
            if (!user.password) {
                $log.log("Passwort fehlt.");
                return false;
            }
            if (!$scope.patterns.password.test(user.password)) {
                $scope.warnings.password = "Das Passwort muss 8-20 Zeichen lang sein und darf keine Leerzeichen enthalten!";
                $log.log($scope.warnings.password);
                return false;
            }
            return true;
        };

        var registerIsValidFor = function(user) {
            if (!loginIsValidFor(user)) {
                return false;
            }
            // if (!user.email || !$scope.patterns.email.test(user.email)) {
            if (!$scope.patterns.email.test(user.email)) {
                $scope.warnings.email = "Bitte gib eine gueltige E-Mail-Adresse ein!";
                $log.log($scope.warnings.email);
                return false;
            }
            // if (!user.tel || !$scope.patterns.tel.test(user.tel)) {
            if (!$scope.patterns.tel.test(user.tel)) {
                $scope.warnings.tel = "Bitte gib eine gueltige Telefonnummer ein!";
                $log.log($scope.warnings.tel);
                return false;
            }
            return true;
        };
        

        $scope.login = function() {
            var _user = $scope.session.user;
            if (!loginIsValidFor(_user)) {
                $log.log("Login ungueltig.");
                return;
            }
            restService.login(_user)
                .then(function(user) {
                    $scope.session.user = user;
                    $scope.session.isLoggedIn = true;
                    $log.log("Login erfolgreich.");
                    $log.log($scope.session);
                }, function(error) {
                    $log.error(error);
                    $scope.warnings.central = error;
                    initSession();
                }, function(notification) {
                    // $log.log(notification); // for future use
                });
        };

        $scope.register = function() {
            var _user = $scope.session.user;
            if (!registerIsValidFor(_user)) {
                $log.log("Registrierung ungueltig.");
                return;
            }
            restService.register(_user)
                .then(function(user) {
                    $scope.session.user = user;
                    $scope.session.isLoggedIn = true;
                    $log.log("Registrierung erfolgreich.");
                    $log.log("Login erfolgreich.");
                    $log.log($scope.session);
                }, function(error) {
                    $log.error(error);
                    $scope.warnings.central = error;
                    initSession();
                }, function(notification) {
                    // $log.log(notification); // for future use
                });
        };

        $scope.logout = function() {

            // *** if anything needs to be done on the server for logout, do it now ***

            $log.log("Logout erfolgreich.");
            initSession();
            $scope.showRegisterDialog = false;
        };

        $scope.editUser = function() {
            $scope.session.tempUser = angular.copy($scope.session.user);
            $scope.session.inEditMode = true;
            $scope.session.showEditUserDialog = true;
        };

        $scope.saveEditedUser = function() {
            var _user = $scope.session.tempUser;
            restService.updateUser(_user)
                .then(function(success) {
                    $log.log(success);
                    $scope.session.user = _user;
                    $scope.session.inEditMode = false;
                    $scope.session.showEditUserDialog = false;
                }, function(error) {
                    $log.error(error);
                    $scope.warnings.central = error;
                    $scope.cancelEditUser();
                }, function(notification) {
                    // $log.log(notification); // for future use
                });
        };

        $scope.cancelEditUser = function() {
            $scope.session.tempUser = undefined;
            $scope.session.inEditMode = false;
            $scope.session.showEditUserDialog = false;
        };

        $scope.deleteUser = function() {
            
            // *** ask: are you sure you want to delete? ***

            var _user = $scope.session.user;
            restService.deleteUser(_user)
                .then(function(success) {
                    $log.log(success);
                    initSession();
                    $scope.showRegisterDialog = false;
                }, function(error) {
                    $log.error(error);
                    $scope.warnings.central = error;
                }, function(notification) {
                    // $log.log(notification); // for future use
                });
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
            $scope.session.inEditMode = true;
            $scope.session.showEditSurveysDialog = true;
            $log.log($scope.session.user);
        };

        $scope.addSurvey = function() {
            var _fromSaveSurvey = restService.saveSurvey();
            if (!_fromSaveSurvey.success) {
                $log.error("Erstellen einer leeren Terminumfrage auf dem Server fehlgeschlagen.");
                $log.error(_fromSaveSurvey.serverMessage);
                return;
            }
            $scope.session.tempSurvey = _fromSaveSurvey.survey;
            // $scope.session.tempSurvey = new Survey();

            $scope.session.addingSurvey = true;
            $scope.session.inEditMode = true;
            $scope.session.showEditSurveysDialog = true;
        };

        $scope.cancelEditSurvey = function() {
            $scope.session.tempSurvey = "";
            $scope.session.addingSurvey = false;
            $scope.session.inEditMode = false;
            $scope.session.showEditSurveysDialog = false;
            $log.log($scope.session.selectedSurvey);
            $log.log($scope.session.user);
        };

        $scope.saveSurvey = function() {
            var _survey = $scope.session.tempSurvey;


            // *** change to async ***

            restService.restService.saveSurvey(_survey)
                .then(function(survey) {
                    // $scope.session.user = user;
                    // $scope.session.isLoggedIn = true;
                    // $log.log("Registrierung erfolgreich.");
                    // $log.log("Login erfolgreich.");
                    // $log.log($scope.session);

                    if (!$scope.session.addingSurvey) {
                        removeElementFrom(_survey, $scope.session.user.surveys);
                    }

                    $scope.session.user.surveys.push(survey);
                    $scope.session.user.surveys.sort(function(a, b){ return a.name.localeCompare(b.name) });
                    $scope.session.selectedSurvey = survey;
                    $scope.session.tempSurvey = "";
                    $scope.session.addingSurvey = false;
                    $scope.session.inEditMode = false;
                    $scope.session.showEditSurveysDialog = false;

                }, function(error) {
                    $log.error(error);
                    $scope.warnings.central = error;
                    // initSession();
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
            // $scope.session.inEditMode = false;
            // $scope.session.showEditSurveysDialog = false;


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

