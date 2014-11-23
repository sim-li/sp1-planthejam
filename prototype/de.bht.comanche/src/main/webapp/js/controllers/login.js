/*
 * Softwareprojekt SoSe/WiSe 2014, Team: Comanche
 * (C)opyright Sebastian Dassé, Mat.-Nr. 791537, s50602@beuth-hochschule.de
 *
 * Module: login controller
 */

'use strict';

angular.module('myApp')
    .controller('loginCtrl', ['$scope', '$rootScope', '$location', '$log', 'restService', 'patterns', 'User', 'Invite', 'Group',
        function($scope, $rootScope, $location, $log, restService, patterns, User, Invite, Group) {

            var DUMMY_LOGIN = false;

            $scope.login = function(user) {
                if (!loginIsValidFor(user)) {
                    $log.log('Login ungueltig.');
                    return;
                }

                if (DUMMY_LOGIN) {
                    user.name = 'Alice';
                    user.password = 'yousnoozeyoulose';
                }

                call(restService.login, user, '/cockpit', 'Login erfolgreich.');
            };

            $scope.register = function(user) {
                if (!registerIsValidFor(user)) {
                    $log.log('Registrierung ungueltig.');
                    return;
                }

                call(restService.register, user, '/cockpit', 'Registrierung erfolgreich.');
            };


            var loginIsValidFor = function(user) {
                if (!user.name) {
                    $log.log('Benutzername fehlt.');
                    return false;
                }
                if (!user.password) {
                    $log.log('Passwort fehlt.');
                    return false;
                }
                if (!patterns.password.test(user.password)) {
                    $rootScope.warnings = 'Das Passwort muss 8-20 Zeichen lang sein und darf keine Leerzeichen enthalten!';
                    $log.log($rootScope.warnings);
                    return false;
                }
                return true;
            };

            var registerIsValidFor = function(user) {
                if (!loginIsValidFor(user)) {
                    return false;
                }
                if (!patterns.email.test(user.email)) {
                    $rootScope.warnings = 'Bitte gib eine gueltige E-Mail-Adresse ein!';
                    $log.log($rootScope.warnings);
                    return false;
                }
                if (!patterns.tel.test(user.tel)) {
                    $rootScope.warnings = 'Bitte gib eine gueltige Telefonnummer ein!';
                    $log.log($rootScope.warnings);
                    return false;
                }
                return true;
            };

            var call = function(service, user, path, successMsg) {
                service(new User(user))
                    .then(function(_user) {
                        $log.log(successMsg);
                        $location.path(path);
                    }, function(error) {
                        $log.log(error);
                        $rootScope.warnings = error;
                    });
            }

        }
    ]);