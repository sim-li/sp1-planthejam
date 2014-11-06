/*
 * Softwareprojekt SoSe/WiSe 2014, Team: Comanche
 * (C)opyright Sebastian Dassé, Mat.-Nr. 791537, s50602@beuth-hochschule.de
 * 
 * Module: login controller
 */


"use strict";

angular.module("myApp")
    .controller("loginCtrl", ["$scope", "$location", "$log", "patterns", "restService", "dialogMap", 
        function($scope, $location, $log, patterns, restService, dialogMap) {
       
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
                    $scope.session.state.isLoggedIn = true;
                    $scope.session.state.isVal = dialogMap.SURVEY_SELECTION;
                    $location.path('/cockpit');
                    $log.log("Login erfolgreich.");
                    $log.log($scope.session);
                    console.log(user);
                    // <<<<<<<<<<<<<<<<<<<< BAUSTELLE: Invites holen ------------------- TODO
                    restService.getInvites(user.oid)
                        .then(function(invites) {
                            $log.debug(invites);

                            var _invites = [];
                            for (var i in invites) {
                                _invites.push(invites[i]);
                            }
                            $scope.session.user.invites  = _invites;
                            $scope.session.selectedInvite = $scope.session.user.invites[0] || "";
                            $log.debug($scope.session.user.invites);
                            $log.debug($scope.session.selectedInvite.survey);
                            $log.debug("---------");

                        }, function(error) {
                            $log.error(error);
                            $scope.warnings.central = error;
                            
                        }, function(notification) {
                            // $log.log(notification); // for future use
                        });


                }, function(error) {
                    $log.error(error);
                    $scope.warnings.central = error;
                    $scope.initSession();
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
                    $scope.session.state.isLoggedIn = true;
                    $scope.session.state.isVal = dialogMap.SURVEY_SELECTION;
                    $log.log("Registrierung erfolgreich.");
                    $log.log("Login erfolgreich.");
                    $log.log($scope.session);
                    $location.path('/'); // come back to login seite when register succesful
                }, function(error) {
                    $log.error(error);
                    $scope.warnings.central = error;
                    $scope.initSession();
                }, function(notification) {
                    // $log.log(notification); // for future use
                });
        };

    }]);

