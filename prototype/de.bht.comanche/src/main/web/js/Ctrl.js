/*
 * Ojektverwaltung-UI, SP1 SoSe 2014, Team: Comanche
 * (C)opyright Sebastian Dassé, Mat.-Nr. 791537, s50602@beuth-hochschule.de
 * 
 * Module: controller
 */


"use strict";

//-- for testing -- TODO remove later <<==== TRYING TO UNDERSTAND MODULES AND CONTROLLERS ====
angular.module("myInjection", [])
    .factory("myTestService", function() {

        var someValues = [1, 2, 3, 4, 5, 6, 7, 8, 9], 
            addTwoVals = function(a, b) {
                return a + b;
            };

        return {
            someValues: someValues,
            addTwoVals: addTwoVals
        };
    });



angular.module("restModule", [])
    .factory("restService", ["$http", "$log", function($http, $log) {

        var getDummyUser = function() {
            return {
                "oid": "", 
                "name": "THE USER", 
                "email": "dummy@test.net", 
                "password": "supersafe123", 
                "surveys": [] // empty list for debugging
            };
        };


        var login = function(name, password) {
            $log.warn("login() not implemented");
            // TODO retrieve data from rest service

            var dummyReturn = { "oid": new Date().getTime(), 
                                "success": true, 
                                "serverMessage": "HI FROM LOGIN" };
            return dummyReturn;
        };

        var getUser = function(oid) {
            $log.warn("getUser() not implemented");
            // TODO retrieve data from rest service


            var dummyReturn = { "user": getDummyUser(), 
                                "success": true, 
                                "serverMessage": "HI FROM GET_USER" };
            return dummyReturn;
        };

        var register = function(name, password, email, tel) {
            $log.warn("register() not implemented");
            // TODO retrieve data from rest service

            var dummyReturn = { "oid": new Date().getTime(), 
                                "success": true, 
                                "serverMessage": "HI FROM REGISTER" };
            return dummyReturn;
        };

        var deleteUser = function(oid) {
            $log.warn("deleteUser() not implemented");
            // TODO retrieve data from rest service

            var dummyReturn = { "success": true, 
                                "serverMessage": "HI FROM DELETE_USER" };
            return dummyReturn;
        };

        var updateUser = function(user) {
            $log.warn("updateUser() not implemented");
            // TODO retrieve data from rest service

            var dummyReturn = { "success": true, 
                                "serverMessage": "HI FROM UPDATE_USER" };
            return dummyReturn;
        };

        /*
         * Update or insert a survey.
         * - @param survey optional. If not specified, a new survey will be created on the server and inserted into the database.
         */
        var saveSurvey = function(survey) {
            $log.warn("saveSurvey() not implemented");
            // TODO retrieve data from rest service

            var dummyReturn = { "survey": { "name": "THE SURVEY" }, // for new survey: survey incl. oid of the newly generated Survey from the database
                                "success": true, 
                                "serverMessage": "HI FROM SAVE_SURVEY" };
            return dummyReturn;
        };

        var deleteSurvey = function(oid) {
            $log.warn("deleteSurvey() not implemented");
            // TODO retrieve data from rest service

            var dummyReturn = { "success": true, 
                                "serverMessage": "HI FROM DELETE_SURVEY" };
            return dummyReturn;
        };

        return {
            login: login, 
            getUser: getUser, 
            register: register, 
            deleteUser: deleteUser, 
            editUser: editUser, 
            saveSurvey: saveSurvey, 
            deleteSurvey: deleteSurvey
        };
    }]);

angular.module("myApp", ["myInjection", "restModule"])
    
    //-- TEST START --
    .controller("Ctrl2", ["$scope", "$log", "$filter", "myTestService", function($scope, $log, $filter, myTestService) {
        $scope.testFct = function() {
            $scope.test123 += 1;
        };
    }]) //-- TEST END --

    .controller("Ctrl", ["$scope", "$log", "$filter", "myTestService", "restService", function($scope, $log, $filter, myTestService, restService) {

        // make $log service available for the use in html
        $scope.$log = $log;



        //-- TEST START --
        // $scope.test123 = myTestService.someValues;
        // $scope.test123 = myTestService.addTwoVals(1000, 11);

        // $scope.testDateOutput;
        // $scope.testDate = function() {
        //     var d1 = new Date();
        //     var d2 = new DatePickerDate(d1);
        //     $log.log(d1);
        //     $log.log(d2);
        //     $scope.testDateOutput = d1 + " " + d2;
        // };

        $scope.doLogin = function() {
            var result = restService.login();
            $log.log("logged in with oid = " + result.oid + ", success = " + result.success + ", serverMessage = '" + result.serverMessage + "'");
        };
        $scope.doRegister = function() {
            var result = restService.register();
            $log.log("registered with oid = " + result.oid + ", success = " + result.success + ", serverMessage = '" + result.serverMessage + "'");
        };
        
        $log.log("DONE TESTING");
        //-- TEST END--



        $scope.Type = {
            UNIQUE: "einmalig", 
            RECURRING: "wiederholt"
        };
        $scope.Type.options_ = [
            $scope.Type.UNIQUE, 
            $scope.Type.RECURRING
        ];
        
        $scope.TimeUnit = {
            DAY: "Tag", 
            WEEK: "Woche", 
            MONTH: "Monat"
        };
        $scope.TimeUnit.options_ = [
            $scope.TimeUnit.DAY, 
            $scope.TimeUnit.WEEK, 
            $scope.TimeUnit.MONTH
        ];

        $scope.patterns = {
            password: /^[\S]{8,20}$/, // no whitespace allowed -- TODO: at this point whitespace is still allowed at the beginning and end
            email: /^[a-zA-Z][\w]*@[a-zA-Z]+\.[a-zA-Z]{2,3}$/, // TODO
            tel: /^[0-9]{4,12}$/ // TODO
        }

        
        $scope.session = {};

        /*
         * returns an initialized user
         */
        var getInitUser = function() {
            var user = {
                "oid": "", 
                "name": "", 
                "password": ""
                , 
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
        };
        initSession();





        
        /*
         * Converts a JavaScript Date to a date format the angular datepicker understands. 
         * - toDate() converts the date back to JavaScript Date
         */
        var DatePickerDate = function(jsDate) {
            this.date = $filter('date')(jsDate, "yyyy-MM-dd");
            this.time = $filter('date')(jsDate, "HH:mm");
        };

        DatePickerDate.prototype.toDate = function() {
            // console.log(new Date(this.date + " " + this.time));
            return new Date(this.date + " " + this.time);
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
                    "deadline": new DatePickerDate(new Date(2014, 7, 10, 23, 55)), // <<datatype>> date = Zeipunkt
                    "frequency": { "distance": 0, "timeUnit": $scope.TimeUnit.WEEK }, // <<datatype>> iteration = Wiederholung
                    "possibleTimeslots": [
                            { "startTime": new DatePickerDate(new Date(2014, 7, 11, 19, 0)), "durationInMins": 120 }, // <<datatype>> <timeslot> = List<Zeitraum>
                            { "startTime": new DatePickerDate(new Date(2014, 7, 12, 20, 0)), "durationInMins": 120 }, 
                            { "startTime": new DatePickerDate(new Date(2014, 7, 18, 19, 30)), "durationInMins": 120 } 
                        ], 
                    "determinedTimeslot": { "startTime": new DatePickerDate(new Date(2014, 7, 12, 20, 0)), "durationInMins": 120 } // <<datatype>> timeslot = Zeitraum
                }, 
                {   "name": "Chorprobe", 
                    "description": "Wir beginnen mit der Mozart-Messe in c-moll. In der Pause gibt es Kuchen im Garten.", 
                    "type": $scope.Type.RECURRING, 
                    "deadline": new DatePickerDate(new Date(2014, 7, 21, 12, 0)),
                    "frequency": { "distance": 0, "timeUnit": $scope.TimeUnit.DAY },
                    "possibleTimeslots": [
                            { "startTime": new DatePickerDate(new Date(2014, 8, 1, 18, 30)), "durationInMins": 150 },
                            { "startTime": new DatePickerDate(new Date(2014, 8, 2, 18, 30)), "durationInMins": 150 } 
                        ], 
                    "determinedTimeslot": { "startTime": new DatePickerDate(), "durationInMins": 0 }
                }, 
                {   "name": "Meeting", 
                    "description": "Unser monatliches Geschäftsessen. Dresscode: Bussiness casual.", 
                    "type": $scope.Type.RECURRING, 
                    "deadline": new DatePickerDate(new Date(2014, 7, 31, 8, 0)),
                    "frequency": { "distance": 0, "timeUnit": $scope.TimeUnit.MONTH },
                    "possibleTimeslots": [], 
                    "determinedTimeslot": { "startTime": new DatePickerDate(), "durationInMins": 0 }
                }
            ];
        };

        /**
         * 
         */
        var fetchUserData = function(oid) {
            var _fromGetUser = restService.getUser(oid);
            if (!_fromGetUser.success) {
                $log.log("Benutzerdaten konnten nicht vom Server geholt werden.");
                $log.warn(_fromGetUser.serverMessage);
                return;
            }
            // *** get all user data from server ***

            return _fromGetUser.user;

            // $scope.session.user.surveys = getDummySurveyList() // *** replace list of dummy surveys by real data from server ***
            // $scope.session.user.surveys = [{}] // empty list for debugging
        }
        


        

        // $scope.test = new Date();
        // var myDate = function(date) {
        //     this.date = $filter('date')(date, "yyyy-mm-dd");
        //     this.time = $filter('date')(date, "hh:mm");
            
        // };
        
        // var new DatePickerDate = function(otherDate) {
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
        // new DatePickerDate();

        // var new DatePickerDate = function(date_) {
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
        //     return $scope.session.user.tempDate.toDate();
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




        var loginIsValid = function() {
            if (!$scope.session.user.name) {
                $log.log("Benutzername fehlt.");
                return false;
            }
            if (!$scope.session.user.password) {
                $log.log("Passwort fehlt.");
                return false;
            }
            if (!$scope.patterns.password.test($scope.session.user.password)) {
                $scope.warnings.password = "Das Passwort muss 8-20 Zeichen lang sein und darf keine Leerzeichen enthalten!";
                $log.log($scope.warnings.password);
                return false;
            }
            return true;
        };

        var registerIsValid = function() {
            if (!loginIsValid()) {
                return false;
            }
            if (!$scope.session.user.email) {
                $log.log("E-Mail-Adresse fehlt.");
                return false;
            }
            if (!$scope.patterns.email.test($scope.session.user.email)) {
                $scope.warnings.email = "Bitte gib eine gueltige E-Mail-Adresse ein!";
                $log.log($scope.warnings.email);
                return false;
            }
            if (!$scope.session.user.tel) {
                $log.log("Telefonnummer fehlt.");
                return false;
            }
            if (!$scope.patterns.tel.test($scope.session.user.tel)) {
                $scope.warnings.tel = "Bitte gib eine gueltige Telefonnummer ein!";
                $log.log($scope.warnings.tel);
                return false;
            }
            return true;
        };

        $scope.login = function() {
            // $scope.warnings = {};
            if (!loginIsValid()) {
                $log.log("Login fehlgeschlagen.");
                $log.log($scope.session.user);
                return;
            }
            
            // *** try to login at server ***

            // initSession();
            $scope.session.user = fetchUserData();
            
            $scope.session.isLoggedIn = true;
            $log.log("Login erfolgreich.");
            $log.log($scope.session);

            // TODO: Baustelle -- checken, ob so sinnvoll:
            // $scope.filteredSurveys = $scope.session.user.surveys;
            // $scope.session.user.selectedSurvey = $scope.filteredSurveys[0] || "";
        };

        // $scope.showRegisterDialog = false;

        $scope.register = function() {
            if (!registerIsValid()) {
                $log.log("Registrierung fehlgeschlagen.");
                $log.log($scope.session.user);
                return;
            }
            
            // *** try to register at server ***

            var _user = $scope.session.user;
            var _fromRegister = restService.register(_user.name, _user.password, _user.email, _user.tel);
            if (!_fromRegister.success) {
                $log.log("Registrierung auf dem Server fehlgeschlagen.");
                $log.warn(_fromRegister.serverMessage);
                return;
            }
            
            // initSession();

            $scope.session.user = fetchUserData(_fromRegister.oid);
            // var _oid = _fromRegister.oid;
            // var _fromGetUser = restService.getUser(_oid);
            // if (!_fromGetUser.success) {
            //     $log.log("Benutzerdaten konnten nicht vom Server geholt werden.");
            //     $log.warn(_fromGetUser.serverMessage);
            //     return;
            // }

            
            $scope.session.isLoggedIn = true;
            $log.log("Registrierung erfolgreich.");
            $log.log($scope.session);
        };

        $scope.logout = function() {

            // *** try to logout at server ***

            initSession();
            console.log("Logout erfolgreich.");
            console.log($scope.session);
        };

        $scope.discardWarnings = function() {
            $scope.warnings = {};
        };


        var Survey = function(config) {
            config = config || {};
            this.name = config.name || "";
            this.description = config.description || "";
            this.type = config.type || $scope.Type.UNIQUE;
            this.deadline = config.deadline || new DatePickerDate(new Date());
            this.frequency = config.frequency || { "distance": 0, "timeUnit": $scope.TimeUnit.WEEK };
            this.possibleTimeslots = config.possibleTimeslots || [], 
            this.determinedTimeslot = config.determinedTimeslot || { "startTime": new DatePickerDate(), "durationInMins": 0 }
        };

        $scope.editSurvey = function() {
            $scope.session.user.tempSurvey = new Survey($scope.session.user.selectedSurvey);
            $scope.session.user.inEditMode = true;
            console.log($scope.session.user);
        };

        $scope.addSurvey = function() {
            $scope.session.user.tempSurvey = new Survey();
            $scope.session.user.adding = true;
            $scope.session.user.inEditMode = true;
        };

        $scope.cancelEditSurvey = function() {
            $scope.session.user.tempSurvey = "";
            $scope.session.user.adding = false;
            $scope.session.user.inEditMode = false;
            console.log($scope.session.user.selectedSurvey);
            console.log($scope.session.user);
        };

        $scope.saveSurvey = function() {
            
            // *** try to synchronize with server ***
            
            if (!$scope.session.user.adding) {
                removeSelectedSurvey();
            }
            $scope.session.user.surveys.push($scope.session.user.tempSurvey);
            $scope.session.user.surveys.sort(function(a, b){ return a.name.localeCompare(b.name) });

            $scope.session.user.selectedSurvey = $scope.session.user.tempSurvey;
            $scope.session.user.tempSurvey = "";
            $scope.session.user.adding = false;
            $scope.session.user.inEditMode = false;
            console.log($scope.session.user.selectedSurvey);
            console.log($scope.session.user);
        };


        var removeElementFrom = function(element, array) {
            var index = array.indexOf(element);
            if (index > -1) {
                array.splice(index, 1);
            }
        };

        var removeSelectedSurvey = function() {
            removeElementFrom($scope.session.user.selectedSurvey, $scope.session.user.surveys);
            $scope.session.user.selectedSurvey = "";
        };

        $scope.deleteSelectedSurvey = function() {

            // *** ask: are you sure you want to delete? ***
            // *** try to delete from server ***
            // *** refresh local model ***

            removeSelectedSurvey();
            // $scope.session.user.selectedSurvey = $scope.filteredSurveys[0] || "";
            console.log($scope.session.user.selectedSurvey);
            console.log($scope.session.user);
        };

        $scope.removeTimeSlotFromTempSurvey = function(timeslot) {
            removeElementFrom(timeslot, $scope.session.user.tempSurvey.possibleTimeslots);
        };

        $scope.addTimeSlotToTempSurvey = function() {
            $scope.session.user.tempSurvey.possibleTimeslots.push({ "startTime": new DatePickerDate(new Date()), "durationInMins": 60 });
        };

        

        // $scope.searchString = "";

        // $scope.filter = function() {
        //     console.log("searchString: '" + $scope.searchString + "'");
        //     $scope.filteredSurveys = [];
        //     angular.forEach($scope.session.user.surveys, function(survey, index) {
        //         var re = new RegExp($scope.searchString, "i");
        //         if (survey.name.match(re) || survey.description.match(re)) {
        //             $scope.filteredSurveys.push(survey);
        //         }
        //     });
        // };

    }]);

