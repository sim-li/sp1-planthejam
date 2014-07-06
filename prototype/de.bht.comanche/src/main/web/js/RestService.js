/*
 * Ojektverwaltung-UI, SP1 SoSe 2014, Team: Comanche
 * (C)opyright Sebastian Dassé, Mat.-Nr. 791537, s50602@beuth-hochschule.de
 * 
 * Module: rest service
 */


"use strict";

angular.module("restModule", [])
    .factory("restService", ["$http", "$log", "$filter", function($http, $log, $filter) {


        // TODO refactor User, Survey, DatePickerDate ...

        var getDummyUser = function() {
            return {
                "oid": new Date().getTime(), 
                "name": "THE USER", 
                "password": "supersafe123", 
                "email": "dummy@test.net", 
                "tel": "+49-30-1234567", 
                // "surveys": [] // empty list for debugging
                "surveys": getDummySurveyList() // dummy list for debugging
                
            };
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

        /*
         * Converts a JavaScript Date to a date format the angular datepicker understands. 
         * - toDate() converts the date back to JavaScript Date
         */
        var DatePickerDate = function(jsDate) {
            this.date = $filter('date')(jsDate, "yyyy-MM-dd");
            this.time = $filter('date')(jsDate, "HH:mm");
        };

        DatePickerDate.prototype.toDate = function() {
            return new Date(this.date + " " + this.time);
        };


        //--- FIXME dangerous temporary hack - refactor!!!!
        var $scope = {};
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
        //----
        

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
            updateUser: updateUser, 
            saveSurvey: saveSurvey, 
            deleteSurvey: deleteSurvey
        };
    }]);

