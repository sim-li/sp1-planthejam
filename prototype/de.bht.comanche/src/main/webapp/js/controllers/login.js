/*
 * Softwareprojekt SoSe/WiSe 2014, Team: Comanche
 * (C)opyright Sebastian Dassé, Mat.-Nr. 791537, s50602@beuth-hochschule.de
 * 
 * Module: login controller
 */


"use strict";

angular.module("myApp")
    .controller("loginCtrl", ["$scope", "$rootScope", "$location", "$log", "patterns", "restService", "dialogMap", "Group", 
        function($scope, $rootScope, $location, $log, patterns, restService, dialogMap, Group) {
       
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
            var _user = $rootScope.session.user;
            if (!loginIsValidFor(_user)) {
                $log.log("Login ungueltig.");
                return;
            }
            restService.login(_user)
                .then(function(user) {

                    $rootScope.session.user = user;
                    $rootScope.session.state.isLoggedIn = true;
                    $rootScope.session.state.isVal = dialogMap.SURVEY_SELECTION;
                    $location.path('/cockpit');
                    $log.log("Login erfolgreich.");
                    $log.log($rootScope.session);
                    console.log(user);
                    // <<<<<<<<<<<<<<<<<<<< BAUSTELLE: Invites holen ------------------- TODO
                    restService.getInvites(user.oid)
                        .then(function(invites) {
                            $log.debug(invites);

                            $rootScope.session.user.invites = invites;
                            $rootScope.session.selectedInvite = $rootScope.session.user.invites[0] || "";
                            $log.debug($rootScope.session.user.invites);
                            $log.debug($rootScope.session.selectedInvite);
                            $log.debug($rootScope.session.selectedInvite.survey);
                            $log.debug("---------");

                        }, function(error) {
                            $log.error(error);
                            $scope.warnings.central = error;
                            
                        }, function(notification) {
                            // $log.log(notification); // for future use
                        });


                    // TODO still testing
                    restService.getGroups()
                        .then(function(groups) {
                            $log.info("================>");
                            $log.debug(groups);

                            
                            $rootScope.session.user.groups = Group.import(groups);
                            $log.debug($rootScope.session.user.groups);
                            // $log.info("================");
                            // $log.info(Group.import);
                            // var receivedGroups = groups;
                            // $log.log("receivedGroups: " + receivedGroups.length);
                            // for (var i = 0, len = receivedGroups.length; i < len; i++) {
                            //     $log.log(receivedGroups[i]);
                            // }
                            // var importedGroups = Group.import(receivedGroups);
                            // $log.log("importedGroups: " + importedGroups.length);
                            // for (var i = 0, len = importedGroups.length; i < len; i++) {
                            //     $log.log(importedGroups[i]);
                            // }
                            // $log.info("<================");


                            // $rootScope.session.user.groups = receivedGroups;
                            // $rootScope.session.user.groups = groups;

                        }, function(error) {
                            $log.error(error);
                            $scope.warnings.central = error;
                            
                        }, function(notification) {
                            // $log.log(notification); // for future use
                        });

                    // FIXME quick hack for debugging
                    // var dummyGroup = new Group({
                    //     oid: 123, 
                    //     name: "Kaffeeklatsch", 
                    //     members: [{oid: 1, name: "Alice"}, {oid: 2, name: "Bob"}, {oid: 3, name: "Carla"}]
                    // });
                    // restService.saveGroup(dummyGroup);


                }, function(error) {
                    $log.error(error);
                    $scope.warnings.central = error;
                    $scope.initSession();
                }, function(notification) {
                    // $log.log(notification); // for future use
                });
        };

        $scope.register = function() {
            var _user = $rootScope.session.user;
            if (!registerIsValidFor(_user)) {
                $log.log("Registrierung ungueltig.");
                return;
            }
            restService.register(_user)
                .then(function(user) {
                    $rootScope.session.user = user;
                    $rootScope.session.state.isLoggedIn = true;
                    $rootScope.session.state.isVal = dialogMap.SURVEY_SELECTION;
                    $log.log("Registrierung erfolgreich.");
                    $log.log("Login erfolgreich.");
                    $log.log($rootScope.session);
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

