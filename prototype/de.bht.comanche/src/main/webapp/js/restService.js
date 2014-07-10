/*
 * Ojektverwaltung-UI, SP1 SoSe 2014, Team: Comanche
 * (C)opyright Sebastian Dassé, Mat.-Nr. 791537, s50602@beuth-hochschule.de
 * 
 * Module: REST service
 */


"use strict";

angular.module("restModule", ["datePickerDate", "constants", "survey"])
    .factory("restService", ["$http", "$q", "$log", "$filter", "DatePickerDate", "TimeUnit", "Type", "Survey", 
        function($http, $q, $log, $filter, DatePickerDate, TimeUnit, Type, Survey) {


        // TODO refactor User, ...


        var USER_PATH = "rest/user/";

        var SUCCESS = "SUCCESS ----------------------------------------------------------", 
            ERROR   = "ERROR ------------------------------------------------------------", 
            DONE    = "DONE =============================================================";


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
        

        /*
         *
         */
        var getDummySurveyList = function() {
            return [
                {   
                    "name": "Bandprobe", 
                    "description": "Wir müssen vor dem Konzert Ende des Monats mindestens noch einmal proben. Wann könnt ihr?", 
                    "type": Type.UNIQUE, // or "RECURRING" <<enumeration>> = einmalig oder wiederholt
                    // "deadline": "10.07.2014, 23:55", // <<datatype>> date = Zeipunkt
                    "deadline": new Date(2014, 7, 10, 23, 55), // <<datatype>> date = Zeipunkt
                    "frequency": { "distance": 0, "timeUnit": TimeUnit.WEEK }, // <<datatype>> iteration = Wiederholung
                    "possibleTimeperiods": [
                            { "startTime": new Date(2014, 7, 11, 19, 0), "durationInMins": 120 }, // <<datatype>> <timeperiod> = List<Zeitraum>
                            { "startTime": new Date(2014, 7, 12, 20, 0), "durationInMins": 120 }, 
                            { "startTime": new Date(2014, 7, 18, 19, 30), "durationInMins": 120 } 
                        ], 
                    "determinedTimeperiod": { "startTime": new Date(2014, 7, 12, 20, 0), "durationInMins": 120 } // <<datatype>> timeperiod = Zeitraum
                }, 
                {   "name": "Chorprobe", 
                    "description": "Wir beginnen mit der Mozart-Messe in c-moll. In der Pause gibt es Kuchen im Garten.", 
                    "type": Type.RECURRING, 
                    "deadline": new Date(2014, 7, 21, 12, 0),
                    "frequency": { "distance": 0, "timeUnit": TimeUnit.DAY },
                    "possibleTimeperiods": [
                            { "startTime": new Date(2014, 8, 1, 18, 30), "durationInMins": 150 },
                            { "startTime": new Date(2014, 8, 2, 18, 30), "durationInMins": 150 } 
                        ], 
                    "determinedTimeperiod": { "startTime": undefined, "durationInMins": 0 }
                }, 
                {   "name": "Meeting", 
                    "description": "Unser monatliches Geschäftsessen. Dresscode: Bussiness casual.", 
                    "type": Type.RECURRING, 
                    "deadline": new Date(2014, 7, 31, 8, 0),
                    "frequency": { "distance": 0, "timeUnit": TimeUnit.MONTH },
                    "possibleTimeperiods": [], 
                    "determinedTimeperiod": { "startTime": undefined, "durationInMins": 0 }
                }
            ];
        };


        var login = function(name, password) {
            var deferred = $q.defer();
            $http({ 
                method: "POST", 
                url: USER_PATH + "login", 
                data: { "oid": "", 
                        "name": name, 
                        "password": password, 
                        "email": "", 
                        "tel": "", 
                        // "surveys": [] // FIXME missing on server in LgUser ?? <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< !!!!! FIXME
                    }
            })
            .success(function(data, status, header, config) {
                deferred.resolve(data.data[0]);
            })
            .error(function(data, status, header, config) {
                deferred.reject("Login auf dem Server fehlgeschlagen. (status: " + status + ")");
            });
            return deferred.promise;
        };

        var getUser = function(oid) {
            $log.warn("getUser() not implemented");
            // TODO retrieve data from rest service


            // var dummyReturn = { "success": true, 
            //                     "serverMessage": "HI FROM GET_USER",  
            //                     "user": getDummyUser() };
            // var _user = dummyReturn.user;


            // if (!_user) {
            //     return null;
            // }


            var deferred = $q.defer();
            $http({ 
                method: "POST", 
                url: USER_PATH + "login", 
                data: { "oid": oid, 
                        "name": "", 
                        "password": "", 
                        "email": "", 
                        "tel": "", 
                        // "surveys": [] // FIXME missing on server in LgUser ?? <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< !!!!! FIXME
                    }
            })
            .success(function(data, status, header, config) {
                var _user = data.data[0];

                // convert all dates to our date format  -->  TODO: factory for survey[] from [] from input
                for (var i = 0; i < _user.surveys.length; i++) {
                    _user.surveys[i] = new Survey(_user.surveys[i]);
                }
                Survey.forSurveysConvertDatesToDatePickerDate(_user.surveys);
                
                deferred.resolve(_user);
            })
            .error(function(data, status, header, config) {
                // $log.debug(data);
                // $log.debug(config);
                // deferred.reject("Benutzerdaten konnten nicht vom Server geholt werden. (status " + status + ")");

                //-- TEST --                                   FIXME
                $log.info("hack");
                deferred.resolve(getDummyUser());
                //-- TEST --
            });
            return deferred.promise;
        };

        var register = function(name, password, email, tel) {
            $log.warn("register() not implemented");
            // TODO retrieve data from rest service

            var dummyReturn = { "success": true, 
                                "serverMessage": "HI FROM REGISTER", 
                                "oid": new Date().getTime() };
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

            // convert all dates to the native date format
            // var _user = ...;
            // Survey.forSurveysConvertDatesToJsDate(_user.surveys);

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

            // convert all dates to the native date format
            // var _survey = ...;
            // _survey.convertDatesToJsDate();

            var dummyReturn = { "success": true, 
                                "serverMessage": "HI FROM SAVE_SURVEY", 
                                "survey": new Survey({name: "THE SURVEY" }) }; // for new survey: survey incl. oid of the newly generated Survey from the database
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

