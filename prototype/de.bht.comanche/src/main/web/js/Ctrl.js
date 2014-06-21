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

    // var myApp = angular.module("myApp", ["testModule"]); // <<==== TRYING TO UNDERSTAND MODULES AND CONTROLLERS ====

    var myApp = angular.module("myApp", []);
    myApp.controller("Ctrl", function($scope, $log, $filter) {

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

        
        /*
         *
         */
        var initDate = function(config) {
            var date = $filter('date')(config, "yyyy-MM-dd");
            var time = $filter('date')(config, "HH:mm");
            return {
                "date": date, 
                "time": time, 
                "toDate": function() {
                    // var self = this;
                    // return new Date(self.date + " " + self.time);
                    return new Date(this.date + " " + this.time);
                }
            };
        };

        /*
         *
         */
        var getDummySurveyList = function() {
            return [
                {   
                    "name": "Bandprobe", 
                    "description": "Wir müssen vor dem Konzert Ende des Monats mindestens noch einmal proben. Wann könnt ihr?", 
                    "type": $scope.Type.UNIQUE, // or "RECURRING" <<enumeration>> = einmalig oder wiederholt
                    // "deadline": "10.07.2014, 23:55", // <<datatype>> date = Zeipunkt
                    "deadline": initDate(new Date(2014, 7, 10, 23, 55)), // <<datatype>> date = Zeipunkt
                    "frequency": { "distance": 0, "timeUnit": $scope.TimeUnit.WEEK }, // <<datatype>> iteration = Wiederholung
                    "possibleTimeslots": [
                            { "startTime": initDate(new Date(2014, 7, 11, 19, 0)), "durationInMins": 120 }, // <<datatype>> <timeslot> = List<Zeitraum>
                            { "startTime": initDate(new Date(2014, 7, 12, 20, 0)), "durationInMins": 120 }, 
                            { "startTime": initDate(new Date(2014, 7, 18, 19, 30)), "durationInMins": 120 } 
                        ], 
                    "determinedTimeslot": { "startTime": initDate(new Date(2014, 7, 12, 20, 0)), "durationInMins": 120 } // <<datatype>> timeslot = Zeitraum
                }, 
                {   "name": "Chorprobe", 
                    "description": "Wir beginnen mit der Mozart-Messe in c-moll. In der Pause gibt es Kuchen im Garten.", 
                    "type": $scope.Type.RECURRING, 
                    "deadline": initDate(new Date(2014, 7, 21, 12, 0)),
                    "frequency": { "distance": 0, "timeUnit": $scope.TimeUnit.DAY },
                    "possibleTimeslots": [
                            { "startTime": initDate(new Date(2014, 8, 1, 18, 30)), "durationInMins": 150 },
                            { "startTime": initDate(new Date(2014, 8, 2, 18, 30)), "durationInMins": 150 } 
                        ], 
                    "determinedTimeslot": { "startTime": initDate(), "durationInMins": 0 }
                }, 
                {   "name": "Meeting", 
                    "description": "Unser monatliches Geschäftsessen. Dresscode: Bussiness casual.", 
                    "type": $scope.Type.RECURRING, 
                    "deadline": initDate(new Date(2014, 7, 31, 8, 0)),
                    "frequency": { "distance": 0, "timeUnit": $scope.TimeUnit.MONTH },
                    "possibleTimeslots": [], 
                    "determinedTimeslot": { "startTime": initDate(), "durationInMins": 0 }
                }
            ];
        };

        /*
         *
         */
        var initUser = function() {

            // *** get user data from server ***
            
            return {
                "name": "", 
                "password": "", 
                "loggedIn": false
                // , 
                // "surveys": getDummySurveyList() // *** replace list of dummy surveys by real data from server ***
                // "surveys": [{}] // empty list for debugging
            };
        };
        
        $scope.user = initUser();

        /**
         *
         */
        var fetchUserData = function() {
            $scope.user.surveys = getDummySurveyList() // *** replace list of dummy surveys by real data from server ***
            // $scope.user.surveys = [{}] // empty list for debugging
        }
        


        

        // $scope.test = new Date();
        // var myDate = function(date) {
        //     this.date = $filter('date')(date, "yyyy-mm-dd");
        //     this.time = $filter('date')(date, "hh:mm");
            
        // };
        
        // var initDate = function(otherDate) {
        //     otherDate = otherDate || new Date();
        //     // var xxx = function() {
        //     //         var self = this;
        //     //         return new Date(self.date + " " + self.time);
        //     return {
        //         "date": $filter('date')(otherDate, "yyyy-MM-dd"), 
        //         "time": $filter('date')(otherDate, "HH:mm"), 
        //         "toDate": function() {
        //             var self = this;
        //             // console.log("d " + self.date);
        //             // console.log("t " + self.time);
        //             return new Date(self.date + " " + self.time);
        //         }
        //     };
        // };
        // initDate();

        // var initDate = function(date_) {
        //     date_ = date_ || new Date();
        //     return {
        //         "date": $filter('date')(date_, "yyyy-MM-dd"), 
        //         "time": $filter('date')(date_, "HH:mm"), 
        //         "toDate": function() {
        //             var self = this;
        //             return new Date(self.date + " " + self.time);
        //         }
        //     };
        // };

        
        // var getDate = function() {
        //     return $scope.user.tempDate.toDate();
        // };
        
        // $scope.getDate = function(from, to) {
        //     // console.log(from); // TODO
        //     console.log(from.date + "#" + from.time);
        //     to = new Date(from.date + "#" + from.time);

        //     // $scope.convertToDate(from.date, from.time);
        // }

        // $scope.convertToDate = function(datetime) {
        //     return new Date(datetime.date + " " + datetime.time);
        // }






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

                // $scope.user = initUser();
                fetchUserData();
                
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
            this.deadline = config.deadline || initDate(new Date());
            this.frequency = config.frequency || { "distance": 0, "timeUnit": $scope.TimeUnit.WEEK };
            this.possibleTimeslots = config.possibleTimeslots || [], 
            this.determinedTimeslot = config.determinedTimeslot || { "startTime": initDate(), "durationInMins": 0 }
        };

        $scope.editSurvey = function() {
            $scope.user.tempSurvey = new Survey($scope.user.selectedSurvey);
            $scope.user.inEditMode = true;
            console.log($scope.user);
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
            console.log($scope.user.selectedSurvey);
            console.log($scope.user);
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
            console.log($scope.user.selectedSurvey);
            console.log($scope.user);
        };


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
            console.log($scope.user.selectedSurvey);
            console.log($scope.user);
        };

        $scope.removeTimeSlotFromTempSurvey = function(timeslot) {
            removeElementFrom(timeslot, $scope.user.tempSurvey.possibleTimeslots);
        };

        $scope.addTimeSlotToTempSurvey = function() {
            $scope.user.tempSurvey.possibleTimeslots.push({ "startTime": initDate(new Date()), "durationInMins": 60 });
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

    
    //==== TRYING TO UNDERSTAND MODULES AND CONTROLLERS ====
    //
    // myApp.controller("Test", function($scope) {
    //     $scope.testing = "Testing";
    // });
    // myApp.controller("testCtrl", function($scope, $log, $filter) {

    // });

    // var testModule = angular.module("testModule", []);
    // testModule.controller("Test2", function($scope) {
    //     $scope.testing2 = "Testing again";
    // });
    //======================================================


    // Ctrl.prototype.remove = function(first_argument) {
    //     // body...
    //     console.log($scope.user.name);
    // };

    // return Ctrl;

}());
