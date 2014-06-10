/*
 * Ojektverwaltung-UI, SP1 SoSe 2014, Team: Comanche
 * (C)opyright Sebastian Dassé, Mat.-Nr. 791537, s50602@beuth-hochschule.de
 * 
 * Module: controller
 */

/**
 * The controller for the angular app
 * @namespace
 */
var Ctrl = (function() {

    "use strict";

    var myApp = angular.module("myApp", []);
    myApp.controller("Ctrl", function($scope) {

        // $scope.allowedPassword = /^\d{5}$/;
        $scope.allowedPassword = /^[^\s]{8,20}$/; // no whitespace allowed -- TODO: at this point whitespace is still allowed at the beginning and end
        // $scope.allowedPassword = /^[^\s]+$/; // no whitespace allowed -- TODO: at this point whitespace is still allowed at the beginning and end


        var initUser = function() {

            // *** get user data from server ***

            var dummyList = [
                { "name": "Bandprobe", 
                  "description": "Wir müssen vor dem Konzert Ende des Monats mindestens noch einmal proben. Wann könnt ihr?", 
                  "type": "UNIQUE", // or "RECURRING" <<enumeration>> = einmalig oder wiederkehrend
                  "deadline": "04.07.2014, 23:55", // <<datatype>> date = Zeipunkt
                  "frequency": { "distance": 0, "timeUnit": "WEEK" }, // <<datatype>> iteration = Wiederholung
                  "possibleTimeslots": [ // <<datatype>> List<timeslot> = List<Zeitraum>
                        { "startTime": "11.07.2014, 19:00", "durationInMins": 120 }, 
                        { "startTime": "12.07.2014, 20:00", "durationInMins": 120 }, 
                        { "startTime": "18.07.2014, 19:30", "durationInMins": 120 } ], 
                  "determinedTimeslot": { "startTime": "12.07.2014, 20:00", "durationInMins": 120 } }, // <<datatype>> timeslot = Zeitraum
                { "name": "Chorprobe" }, 
                { "name": "Meeting" }];

            return {
                "name": "", 
                "password": "", 
                "loggedIn": false, 
                "surveys": dummyList // *** replace list of dummy surveys by real data from server ***
            };
        };
        
        $scope.user = initUser();


        $scope.login = function() {
            $scope.warning = "";

            if (!$scope.user.name || !$scope.user.password) {
                console.log("Login fehlgeschlagen.");
                console.log($scope.user);

                if (!$scope.user.name) {
                    console.log("Benutzername fehlt.");
                }
                if (!$scope.user.password) {
                    console.log("Passwort fehlt.");
                }
            } else if (!$scope.allowedPassword.test($scope.user.password)) {
                $scope.warning = "Das Passwort muss 8-20 Zeichen lang sein und darf keine Leerzeichen enthalten!";
                console.log($scope.warning);
            } else {
                
                // *** try to login at server ***
                
                $scope.user.loggedIn = true;
                console.log("Login erfolgreich. Benutzer:");
                console.log($scope.user);
            }
        };

        $scope.logout = function() {

            // *** try to logout at server ***

            $scope.user = initUser();
            console.log("Logout erfolgreich.");
            console.log($scope.user);
        };

        $scope.turnOffWarning = function() {
            $scope.warning = "";
        }

        $scope.filteredSurveys = [];
        $scope.filter = function() {
            // return true if survey name or description contain searchString
            // for (var survey in $scope.user.surveys) {
            //     console.log(survey.name);
            // }
            // console.log($scope.user.surveys);
            console.log(">>>" + $scope.searchString);
            angular.forEach($scope.user.surveys, function(survey, index) {
                // if (($scope.searchString !== undefined) && (survey.name.indexOf($scope.searchString) != -1)) {
                    $scope.filteredSurveys.push(survey);
                // }
            })
            console.log($scope.filteredSurveys);
        }

    });

}());
