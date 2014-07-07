/*
 * Ojektverwaltung-UI, SP1 SoSe 2014, Team: Comanche
 * (C)opyright Sebastian Dassé, Mat.-Nr. 791537, s50602@beuth-hochschule.de
 * 
 * Module: controller
 */


"use strict";

//-- TEST START --
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
    });//-- TEST END --


angular.module("myApp", ["myInjection", "restModule"])
    
    //-- TEST START --
    .controller("Ctrl2", ["$scope", "$log", "myTestService", function($scope, $log, myTestService) {
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
        //
        // $scope.doLogin = function() {
        //     var result = restService.login();
        //     $log.log("logged in with oid = " + result.oid + ", success = " + result.success + ", serverMessage = '" + result.serverMessage + "'");
        // };
        // $scope.doRegister = function() {
        //     var result = restService.register();
        //     $log.log("registered with oid = " + result.oid + ", success = " + result.success + ", serverMessage = '" + result.serverMessage + "'");
        // };
        
        // $log.log("DONE TESTING");
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
            return new Date(this.date + " " + this.time);
        };



        /**
         * tries to get the specified user from the server
         */
        var fetchUserData = function(oid) {
            var _fromGetUser = restService.getUser(oid);
            if (!_fromGetUser.success) {
                $log.error("Benutzerdaten konnten nicht vom Server geholt werden.");
                $log.error(_fromGetUser.serverMessage);
                return;
            }
            // *** get all user data from server ***

            return _fromGetUser.user;

            // $scope.session.user.surveys = getDummySurveyList() // *** replace list of dummy surveys by real data from server ***
            // $scope.session.user.surveys = [{}] // empty list for debugging
        }
        

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
            if (!user.email || !$scope.patterns.email.test(user.email)) {
                $scope.warnings.email = "Bitte gib eine gueltige E-Mail-Adresse ein!";
                $log.log($scope.warnings.email);
                return false;
            }
            if (!user.tel || !$scope.patterns.tel.test(user.tel)) {
                $scope.warnings.tel = "Bitte gib eine gueltige Telefonnummer ein!";
                $log.log($scope.warnings.tel);
                return false;
            }
            return true;
        };

        $scope.login = function() {
            // $scope.warnings = {};
            var _user = $scope.session.user;

            if (!loginIsValidFor(_user)) {
                $log.log("Login ungueltig.");
                $log.log(_user);
                return;
            }

            var _fromLogin = restService.login(_user.name, _user.password);
            if (!_fromLogin.success) {
                $log.error("Login auf dem Server fehlgeschlagen.");
                $log.error(_fromLogin.serverMessage);
                initSession();
                return;
            }

            _user = fetchUserData(_fromLogin.oid);
            if (!_user) {
                $log.error("Login fehlgeschlagen.");
                initSession();
                return;
            }
            $scope.session.user = _user;
            
            $scope.session.isLoggedIn = true;
            $log.log("Login erfolgreich.");
            $log.log($scope.session);

            // TODO: Baustelle -- checken, ob so sinnvoll:
            // $scope.filteredSurveys = $scope.session.user.surveys;
            // $scope.session.selectedSurvey = $scope.filteredSurveys[0] || "";
        };

        // $scope.showRegisterDialog = false;

        $scope.register = function() {
            var _user = $scope.session.user;

            if (!registerIsValidFor(_user)) {
                $log.log("Registrierung ungueltig.");
                $log.log(_user);
                return;
            }

            var _fromRegister = restService.register(_user.name, _user.password, _user.email, _user.tel);
            if (!_fromRegister.success) {
                $log.error("Registrierung auf dem Server fehlgeschlagen.");
                $log.error(_fromRegister.serverMessage);
                initSession();
                return;
            }

            _user = fetchUserData(_fromRegister.oid);
            if (!_user) {
                $log.error("Login fehlgeschlagen.");
                initSession();
                return;
            }
            $scope.session.user = _user;
            
            $scope.session.isLoggedIn = true;
            $log.log("Registrierung erfolgreich.");
            $log.log($scope.session);
        };

        $scope.logout = function() {

            // *** try to logout at server ***  -- TODO: not necessary?

            initSession();
            $scope.showRegisterDialog = false;
            $log.log("Logout erfolgreich.");
            $log.log($scope.session);
        };

        $scope.discardWarnings = function() {
            $scope.warnings = {};
        };



        $scope.editUser = function() {
            $scope.session.tempUser = $scope.session.user;
            $scope.session.inEditMode = true;
            $scope.session.showEditUserDialog = true;
        };

        $scope.saveEditedUser = function() {
            var _user = $scope.session.tempUser;
            var _fromUpdateUser = restService.updateUser(_user);

            if (!_fromUpdateUser.success) {
                $log.error("Update der Kontodaten auf dem Server fehlgeschlagen.");
                $log.error(_fromUpdateUser.serverMessage);

                cancelEditUser();
                return;
            }
            $scope.session.user = _user;

            $scope.session.inEditMode = false;
            $scope.session.showEditUserDialog = false;
        };

        $scope.cancelEditUser = function() {
            $scope.session.inEditMode = false;
            $scope.session.showEditUserDialog = false;
        };

        $scope.deleteUser = function() {
            
            // *** ask: are you sure you want to delete? ***

            var _user = $scope.session.user;
            var _fromDeleteUser = restService.deleteUser(_user.oid);

            if (!_fromDeleteUser.success) {
                $log.error("Loeschen des Kontos auf dem Server fehlgeschlagen.");
                $log.error(_fromDeleteUser.serverMessage);
                return;
            }

            initSession();
            $scope.showRegisterDialog = false;
            $log.log("Das Konto wurde erfolgreich geloescht.");
            $log.log($scope.session);
        };



        $scope.editContacts = function() {
            $log.warn("editContacts() not implemented");
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
            var _fromSaveSurvey = restService.saveSurvey(_survey);

            if (!_fromSaveSurvey.success) {
                $log.error("Speichern der Terminumfrage auf dem Server fehlgeschlagen.");
                $log.error(_fromSaveSurvey.serverMessage);
                return;
            }
            _survey = _fromSaveSurvey.survey;

            if (!$scope.session.addingSurvey) {
                removeElementFrom(_survey, $scope.session.user.surveys);
            }
            $scope.session.user.surveys.push(_survey);
            $scope.session.user.surveys.sort(function(a, b){ return a.name.localeCompare(b.name) });

            $scope.session.selectedSurvey = _survey;
            $scope.session.tempSurvey = "";
            $scope.session.addingSurvey = false;
            $scope.session.inEditMode = false;
            $scope.session.showEditSurveysDialog = false;
            console.log($scope.session.selectedSurvey);
            console.log($scope.session.user);
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
            var _fromDeleteSurvey = restService.deleteSurvey(_survey.oid);

            if (!_fromDeleteSurvey.success) {
                $log.error("Loeschen der Terminumfrage auf dem Server fehlgeschlagen.");
                $log.error(_fromDeleteSurvey.serverMessage);
                return;
            }

            removeElementFrom($scope.session.selectedSurvey, $scope.session.user.surveys);
            // $scope.session.selectedSurvey = "";
            $scope.session.selectedSurvey = $scope.session.user.surveys[0] || "";

            // $scope.session.selectedSurvey = $scope.filteredSurveys[0] || "";
            console.log($scope.session.selectedSurvey);
            console.log($scope.session.user);
        };

        $scope.removeTimeSlotFromTempSurvey = function(timeslot) {
            removeElementFrom(timeslot, $scope.session.tempSurvey.possibleTimeslots);
        };

        $scope.addTimeSlotToTempSurvey = function() {
            $scope.session.tempSurvey.possibleTimeslots.push({ "startTime": new DatePickerDate(new Date()), "durationInMins": 60 });
        };

    }]);

