/*
 * Ojektverwaltung-UI, SP1 SoSe 2014, Team: Comanche
 * (C)opyright Sebastian Dass�, Mat.-Nr. 791537, s50602@beuth-hochschule.de
 * 
 * Module: REST service
 */


"use strict";

angular.module("restModule", ["datePickerDate", "constants", "survey", "invite"])
    .factory("restService", ["$http", "$q", "$log", "$filter", "DatePickerDate", "TimeUnit", "Type", "Survey", "Invite", 
        function($http, $q, $log, $filter, DatePickerDate, TimeUnit, Type, Survey, Invite) {


        // TODO refactor User, ...


        var USER_PATH = "rest/user/";
        var INVITE_PATH = "rest/invite/";
        var SURVEY_PATH = "rest/survey/"; // ???????????????? not used??

        var SUCCESS = "SUCCESS ----------------------------------------------------------", 
            ERROR   = "ERROR ------------------------------------------------------------", 
            DONE    = "DONE =============================================================";

        
        var getErrorMesage = function(status) {
            var error = {
                  2: "Die Objekt-ID wurde in der Datenbank nicht gefunden.", 
                  3: "Das Objekt wurde in der Datenbank nicht gefunden.", 
                  4: "No query class.", 
                  5: "Diese Klasse kann nicht gespeichert werden.", 
                  6: "Falscher Argument-Typ.", 
                  7: "Falsche Anzahl an Argumenten.", 
                  8: "Falsches Passwort.", 
                  9: "Kein Benutzer mit diesem Namen gefunden.", 
                 10: "Falsche ID.", 
                 11: "Dieser Benutzername ist schon vergeben.", 
                404: "REST-Service nicht gefunden.", 
                123: "FOO-ERROR", 
                321: "BAR-ERROR"
            };
            var msg = error[status] || "";
            return msg + " (status " + status + ")";
        }


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
                    "description": "Wir m�ssen vor dem Konzert Ende des Monats mindestens noch einmal proben. Wann k�nnt ihr?", 
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
                    "description": "Unser monatliches Gesch�ftsessen. Dresscode: Bussiness casual.", 
                    "type": Type.RECURRING, 
                    "deadline": new Date(2014, 7, 31, 8, 0),
                    "frequency": { "distance": 0, "timeUnit": TimeUnit.MONTH },
                    "possibleTimeperiods": [], 
                    "determinedTimeperiod": { "startTime": undefined, "durationInMins": 0 }
                }
            ];
        };


        var login = function(user) {
            $log.log("REST login");
            var deferred = $q.defer();
            $http({ 
                method: "POST", 
                url: USER_PATH + "login", 
                data: { "name": user.name, "password": user.password }
            }).success(function(data, status, header, config) {
                $log.debug(data.data[0]);

                getUser(data.data[0].oid)
                    .then(function(success) {    
                        deferred.resolve(success);
                    }, function(error) {
                        deferred.reject(error);
                    }, function(notification) {
                        // $log.log(notification); // for future use
                    });
            }).error(function(data, status, header, config) {
                deferred.reject("Login auf dem Server fehlgeschlagen. " + getErrorMesage(status));
            });
            return deferred.promise;
        };

        var getUser = function(oid) {
            $log.log("REST getUser");
            var deferred = $q.defer();
            $http({ 
                method: "POST", 
                url: USER_PATH + "getUser", 
                data: { "oid": oid }
            })
            .success(function(data, status, header, config) {
                var _user = data.data[0];
                $log.debug(_user);


                // TODO extract invites/surveys from user ********************************** 
                // convert all dates to our date format  -->  TODO: factory for survey[] from [] from input
                var _surveys =  _user.surveys || [];
                for (var i = 0; i < _surveys.length; i++) {
                    _surveys.push(new Survey(_user.surveys[i]));
                }
                _user.surveys = Survey.forSurveysConvertDatesToDatePickerDate(_surveys);
                
                deferred.resolve(_user);
            })
            .error(function(data, status, header, config) {
                deferred.reject("Benutzerdaten konnten nicht vom Server geholt werden. " + getErrorMesage(status));
            });
            return deferred.promise;
        };

        var register = function(user) {
            $log.log("REST register");
            var deferred = $q.defer();
            $http({ 
                method: "POST", 
                url: USER_PATH + "register", 
                data: { "name": user.name, "password": user.password, "email": user.email, "tel": user.tel }
            }).success(function(data, status, header, config) {
                $log.debug(data.data[0]);

                getUser(data.data[0].oid)
                    .then(function(success) {    
                        deferred.resolve(success);
                    }, function(error) {
                        deferred.reject(error);
                    }, function(notification) {
                        // $log.log(notification); // for future use
                    });
            }).error(function(data, status, header, config) {
                deferred.reject("Registrierung auf dem Server fehlgeschlagen. " + getErrorMesage(status));
            });
            return deferred.promise;
        };

        var deleteUser = function(user) {
            $log.log("REST deleteUser");
            var deferred = $q.defer();
            $http({ 
                method: "DELETE", 
                url: USER_PATH + "delete", 
                data: { "oid": user.oid }, 
                headers: { "Content-Type": "application/json" }
            }).success(function(data, status, header, config) {
                $log.debug(data.data);

                deferred.resolve("Das Konto wurde erfolgreich geloescht.");
            }).error(function(data, status, header, config) {
                deferred.reject("Loeschen des Kontos auf dem Server fehlgeschlagen. " + getErrorMesage(status));
            });
            return deferred.promise;
        };

        var updateUser = function(user) {
            $log.log("REST updateUser");
            
            
            // TODO assure, that user has all surveys wrapped up in invites ************************************************
            // convert all dates to the native date format
            // var _user = angular.copy(user);
            // Survey.forSurveysConvertDatesToJsDate(_user.surveys);


            var deferred = $q.defer();
            $http({ 
                method: "POST", 
                url: USER_PATH + "update", 
                data: { 
                    oid: user.oid, 
                    name: user.name, 
                    password: user.password, 
                    email: user.email, 
                    tel: user.tel//, 
                    // invites: invites
                }
            }).success(function(data, status, header, config) {
                $log.debug(data);

                deferred.resolve("Die Kontodaten wurden erfolgreich auf dem Server gespeichert.");
            }).error(function(data, status, header, config) {
                deferred.reject("Update der Kontodaten auf dem Server fehlgeschlagen." + getErrorMesage(status));
            });
            return deferred.promise;
        };


        var getInvites = function(oid) { //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
            $log.log("REST getInvites");
            var deferred = $q.defer();
            $http({ 
                method: "POST", 
                url: INVITE_PATH + "getInvites", 
                data: { "oid": oid }
            }).success(function(data, status, header, config) {
                $log.debug(data.data);
                
                var _invites = data.data;
                _invites = Invite.forInvitesConvertFromRawInvites(_invites);
                deferred.resolve(_invites);
                
                

                // KONVERTIERUNG
                // var _invites = ...;
                // _invites = Invite.forInvitesConvertDatesToDatePickerDate(_invites);


                // var _user = data.data[0];
                


                // TODO extract invites/surveys from user ********************************** 
                // convert all dates to our date format  -->  TODO: factory for survey[] from [] from input
                // var _surveys =  _user.surveys || [];
                // for (var i = 0; i < _surveys.length; i++) {
                //     _surveys.push(new Survey(_user.surveys[i]));
                // }
                // _user.surveys = Survey.forSurveysConvertDatesToDatePickerDate(_surveys);
                
                // deferred.resolve(_user);
            }).error(function(data, status, header, config) {
                
                // ----DEBUGGING -------- ******************
                $log.debug(data);
                $log.debug(status);
                $log.debug(header);
                $log.debug(config);
                // ----DEBUGGING -------- ******************
                
                deferred.reject("Benutzerdaten konnten nicht vom Server geholt werden. " + getErrorMesage(status));
            });
            return deferred.promise;
        };


        /*
         * Update or insert a survey.
         * - @param survey optional. If not specified, a new survey will be created on the server and inserted into the database.
         */
        var saveSurvey = function(survey) {
            // $log.warn("saveSurvey() not implemented");
            $log.warn("saveSurvey() not tested");
            // TODO retrieve data from rest service

            // convert all dates to the native date format
            // var _survey = ...;
            // _survey.convertDatesToJsDate();

            // var dummyReturn = { "success": true, 
            //                     "serverMessage": "HI FROM SAVE_SURVEY", 
            //                     "survey": new Survey({name: "THE SURVEY" }) }; // for new survey: survey incl. oid of the newly generated Survey from the database
            // return dummyReturn;

            $log.log("REST save survey");
            var deferred = $q.defer();
            $http({ 
                method: "POST", 
                url: SURVEY_PATH + "save", 
                data: {   
                    "name": survey.name, 
                    "description": survey.description, 
                    "type": survey.type, 
                    "deadline": survey.deadline, 
                    "frequency": survey.frequency, 
                    "possibleTimeperiods": survey.possibleTimeperiods, 
                    "determinedTimeperiod": survey.determinedTimeperiod 
                }
            }).success(function(data, status, header, config) {
                $log.debug(data.data[0]);

                deferred.resolve(data.data[0]);
            }).error(function(data, status, header, config) {
                deferred.reject("Speichern der Terminumfrage auf dem Server fehlgeschlagen." + getErrorMesage(status));
            });
            return deferred.promise;
        };

        var deleteSurvey = function(oid) {
            // $log.warn("deleteSurvey() not implemented");
            $log.warn("deleteSurvey() not tested");
            // TODO retrieve data from rest service

            // var dummyReturn = { "success": true, 
            //                     "serverMessage": "HI FROM DELETE_SURVEY" };
            // return dummyReturn;

            $log.log("REST deleteUser");
            var deferred = $q.defer();
            $http({ 
                method: "DELETE", 
                url: USER_PATH + "delete", 
                data: { "oid": oid }, 
                headers: { "Content-Type": "application/json" }
            }).success(function(data, status, header, config) {
                $log.debug(data.data);

                deferred.resolve("Die Terminumfrage wurde erfolgreich geloescht.");
            }).error(function(data, status, header, config) {
                deferred.reject("Loeschen der Terminumfrage auf dem Server fehlgeschlagen. " + getErrorMesage(status));
            });
            return deferred.promise;
        };

        return {
            login: login, 
            getUser: getUser, 
            register: register, 
            deleteUser: deleteUser, 
            updateUser: updateUser, 
            getInvites: getInvites, 
            saveSurvey: saveSurvey, 
            deleteSurvey: deleteSurvey
        };
    }]);

