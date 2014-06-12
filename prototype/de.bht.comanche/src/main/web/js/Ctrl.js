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
                {   
                    "name": "Bandprobe", 
                    "description": "Wir müssen vor dem Konzert Ende des Monats mindestens noch einmal proben. Wann könnt ihr?", 
                    "type": $scope.Type.UNIQUE, // or "RECURRING" <<enumeration>> = einmalig oder wiederholt
                    // "deadline": "10.07.2014, 23:55", // <<datatype>> date = Zeipunkt
                    "deadline": new Date(2014, 7, 10, 23, 55), // <<datatype>> date = Zeipunkt
                    "frequency": { "distance": 0, "timeUnit": $scope.TimeUnit.WEEK }, // <<datatype>> iteration = Wiederholung
                    "possibleTimeslots": [
                            { "startTime": new Date(2014, 7, 11, 19, 0), "durationInMins": 120 }, // <<datatype>> <timeslot> = List<Zeitraum>
                            { "startTime": new Date(2014, 7, 12, 20, 0), "durationInMins": 120 }, 
                            { "startTime": new Date(2014, 7, 18, 19, 30), "durationInMins": 120 } 
                        ], 
                    "determinedTimeslot": { "startTime": new Date(2014, 7, 12, 20, 0), "durationInMins": 120 } // <<datatype>> timeslot = Zeitraum
                }, 
                {   "name": "Chorprobe", 
                    "description": "Wir beginnen mit der Mozart-Messe in c-moll. In der Pause gibt es Kuchen im Garten.", 
                    "type": $scope.Type.RECURRING, 
                    "deadline": new Date(2014, 7, 21, 12, 0),
                    "frequency": { "distance": 0, "timeUnit": $scope.TimeUnit.DAY },
                    "possibleTimeslots": [
                            { "startTime": new Date(2014, 8, 1, 18, 30), "durationInMins": 150 },
                            { "startTime": new Date(2014, 8, 2, 18, 30), "durationInMins": 150 } 
                        ], 
                    "determinedTimeslot": {}
                }, 
                {   "name": "Meeting", 
                    "description": "Unser monatliches Geschäftsessen. Dresscode: Bussiness casual.", 
                    "type": $scope.Type.RECURRING, 
                    "deadline": new Date(2014, 7, 31, 8, 0),
                    "frequency": { "distance": 0, "timeUnit": $scope.TimeUnit.MONTH },
                    "possibleTimeslots": [], 
                    "determinedTimeslot": {}
                }
            ];

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

                // TODO: Baustelle -- checken, ob so sinnvoll:
                // $scope.filteredSurveys = $scope.user.surveys;
                // $scope.user.selectedSurvey = $scope.filteredSurveys[0] || "";
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


        var Survey = function(config) {
            config = config || {};
            this.name = config.name || "";
            this.description = config.description || "";
            this.type = config.type || $scope.Type.UNIQUE;
            this.deadline = config.deadline || new Date();
            this.frequency = config.frequency || { "distance": 0, "timeUnit": $scope.TimeUnit.WEEK };
            this.possibleTimeslots = config.possibleTimeslots || [ { "startTime": new Date(), "durationInMins": 60 } ], 
            this.determinedTimeslot = config.determinedTimeslot || {}
        };

        $scope.editSurvey = function() {
            $scope.user.tempSurvey = new Survey($scope.user.selectedSurvey);
            $scope.user.inEditMode = true;
        };

        $scope.addSurvey = function() {
            $scope.user.tempSurvey = new Survey();
            $scope.user.adding = true;
            $scope.user.inEditMode = true;
        };

        $scope.cancelEditSurvey = function() {
            $scope.user.tempSurvey = "";
            $scope.user.adding = false;
            $scope.user.inEditMode = false;
        };

        $scope.saveSurvey = function() {
            
            // *** try to synchronize with server ***
            
            if (!$scope.user.adding) {
                removeSelectedSurvey();
            }
            $scope.user.surveys.push($scope.user.tempSurvey);
            $scope.user.surveys.sort(function(a, b){ return a.name.localeCompare(b.name) });


            $scope.user.selectedSurvey = $scope.user.tempSurvey;
            $scope.user.tempSurvey = "";
            $scope.user.adding = false;
            $scope.user.inEditMode = false;
        };



        // TODO: Baustelle
        // $scope.updateSelection = function() {
        //     $scope.user.selectedSurvey = $scope.filteredSurveys[0] || "";
        // };


        var removeElementFrom = function(element, array) {
            var index = array.indexOf(element);
            if (index > -1) {
                array.splice(index, 1);
            }
        };

        var removeSelectedSurvey = function() {
            removeElementFrom($scope.user.selectedSurvey, $scope.user.surveys);
            $scope.user.selectedSurvey = "";
        };

        $scope.deleteSelectedSurvey = function() {

            // *** ask: are you sure you want to delete? ***
            // *** try to delete from server ***
            // *** refresh local model ***

            removeSelectedSurvey();
            // $scope.user.selectedSurvey = $scope.filteredSurveys[0] || "";
            console.log($scope.user);
        };

        $scope.removeTimeSlotFromTempSurvey = function(timeslot) {
            removeElementFrom(timeslot, $scope.user.tempSurvey.possibleTimeslots);
        };

        $scope.addTimeSlotToTempSurvey = function() {
            $scope.user.tempSurvey.possibleTimeslots.push({ "startTime": new Date(), "durationInMins": 60 });
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

    // Ctrl.prototype.remove = function(first_argument) {
    //     // body...
    //     console.log($scope.user.name);
    // };

    // return Ctrl;

}());
