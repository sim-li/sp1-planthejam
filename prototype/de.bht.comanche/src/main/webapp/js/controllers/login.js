/**
 * @module myApp
 *
 * @author Sebastian Dass&eacute;
 */
angular.module('myApp')
    /**
     * The controller for the login view.
     *
     * @class loginCtrl
     */
    .controller('loginCtrl', ['$location', '$log', '$rootScope', '$scope', 'patterns', 'restService', 'User',
        function($location, $log, $rootScope, $scope, patterns, restService, User) {

            'use strict';

            // turns the dummy login on/off for debugging
            var DUMMY_LOGIN = false;

            /**
             * Does a login for the specified user if his credentials are valid.
             *
             * @method login
             * @param  {Object}  user the user to be logged in
             * @return {Promise}      a promise to the logged-in user
             */
            $scope.login = function(user) {
                if (!loginIsValidFor(user)) {
                    $log.log('Login ungueltig.');
                    return;
                }
                if (DUMMY_LOGIN) {
                    user.name = 'Alice';
                    user.password = 'testtest';
                }
                callService(restService.login, user, '/cockpit', 'Login erfolgreich.');
            };

            /**
             * Registers the specified user and does a login if his credentials are valid.
             *
             * @method register
             * @param  {Object}  user the user to be registered
             * @return {Promise}      a promise toe the registered and logged-in user
             */
            $scope.register = function(user) {
                if (!registerIsValidFor(user)) {
                    $log.log('Registrierung ungueltig.');
                    return;
                }
                callService(restService.register, user, '/cockpit', 'Registrierung erfolgreich.');
            };


            /**
             * Checks if the credentials of the specified user are valid.
             *
             * @method loginIsValidFor
             * @private
             * @param  {Object}  user the user
             * @return {Boolean}      true if the credentials are valid, otherwise false
             */
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

            /**
             * Checks if the credentials of the specified user are valid.
             *
             * @method registerIsValidFor
             * @private
             * @param  {Object}  user the user
             * @return {Boolean}      true if the credentials are valid, otherwise false
             */
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

            /**
             * Calls an asynchronous service at the specified relative path and passes the user as a parameter.
             *
             * @method callService
             * @private
             * @param  {Function} service    a restService
             * @param  {Object}   user       the user
             * @param  {String}   path       the relative path at which the RESTful service shall be called
             * @param  {String}   [successMsg] the message to be logged for success
             * @return {Promise}             a promise to the requested object
             */
            var callService = function(service, user, path, successMsg) {
                service(new User(user))
                    .then(function(_user) {
                        if (successMsg) {
                            $log.log(successMsg);
                        }
                        $scope.user = new User(_user);
                        $location.path(path);
                    }, function(error) {
                        $log.log(error);
                        $rootScope.warnings = error;
                    });
            };

        }
    ]);