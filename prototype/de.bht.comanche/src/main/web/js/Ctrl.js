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
    myApp.controller("Ctrl", function($scope, $log) {

        $scope.$log = $log;

        $scope.Type = {
            UNIQUE: "einmalig", 
            RECURRING: "wiederholt"
        };
        $scope.typeOptions = [
            $scope.Type.UNIQUE, 
            $scope.Type.RECURRING
        ];
        
        $scope.TimeUnit = {
            DAY: "Tag", 
            WEEK: "Woche", 
            MONTH: "Monat"
        };
        $scope.timeUnitOptions = [
            $scope.TimeUnit.DAY, 
            $scope.TimeUnit.WEEK, 
            $scope.TimeUnit.MONTH
        ];


        // $scope.allowedPassword = /^\d{5}$/;
        $scope.allowedPassword = /^[^\s]{8,20}$/; // no whitespace allowed -- TODO: at this point whitespace is still allowed at the beginning and end
        // $scope.allowedPassword = /^[^\s]+$/; // no whitespace allowed -- TODO: at this point whitespace is still allowed at the beginning and end


        var initUser = function() {

            // *** get user data from server ***

            var dummyList = [
                { "name": "Bandprobe", 
                  "description": "Wir müssen vor dem Konzert Ende des Monats mindestens noch einmal proben. Wann könnt ihr?", 
                  "type": $scope.Type.UNIQUE, // or "RECURRING" <<enumeration>> = einmalig oder wiederholt
                  // "deadline": "10.07.2014, 23:55", // <<datatype>> date = Zeipunkt
                  "deadline": new Date(2014, 4, 10, 23, 55), // <<datatype>> date = Zeipunkt
                  "frequency": { "distance": 0, "timeUnit": $scope.TimeUnit.WEEK }, // <<datatype>> iteration = Wiederholung
                  "possibleTimeslots": [ // <<datatype>> List<timeslot> = List<Zeitraum>
                        { "startTime": new Date(2014, 7, 11, 19, 0), "durationInMins": 120 }, 
                        { "startTime": new Date(2014, 7, 12, 20, 0), "durationInMins": 120 }, 
                        { "startTime": new Date(2014, 7, 18, 19, 30), "durationInMins": 120 } ], 
                  "determinedTimeslot": { "startTime": new Date(2014, 7, 12, 20, 0), "durationInMins": 120 } }, // <<datatype>> timeslot = Zeitraum
                { "name": "Chorprobe", 
                  "description": "foo"}, 
                { "name": "Meeting", 
                  "description": "bar"}];

            return {
                "name": "", 
                "password": "", 
                "loggedIn": false, 
                "surveys": dummyList // *** replace list of dummy surveys by real data from server ***
                // "surveys": [{}] // empty list for debugging
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

                // TODO: checken, ob so sinnvoll:
                $scope.filteredSurveys = $scope.user.surveys;
                $scope.user.selectedSurvey = $scope.filteredSurveys[0] || "";
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
        };

        

        // $scope.searchString = "";

        // $scope.filter = function() {
        //     console.log("searchString: '" + $scope.searchString + "'");
        //     $scope.filteredSurveys = [];
        //     angular.forEach($scope.user.surveys, function(survey, index) {
        //         var re = new RegExp($scope.searchString, "i");
        //         if (survey.name.match(re) || survey.description.match(re)) {
        //             $scope.filteredSurveys.push(survey);
        //         }
        //     });
        // };

    });

}());
