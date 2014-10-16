/*
 * Ojektverwaltung-UI, SP1 SoSe 2014, Team: Comanche
 * (C)opyright Sebastian Dass�, Mat.-Nr. 791537, s50602@beuth-hochschule.de
 *
 * Module: REST service
 */


"use strict";

angular.module("restModule", ["datePickerDate", "constants", "invite"])
    .factory("restService", ["$http", "$q", "$log", "$filter", "Invite",
        function($http, $q, $log, $filter, Invite) {

        var USER_PATH = "rest/user/";
        var INVITE_PATH = "rest/invite/";

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


        /*
         *
         */
        var getDummyUser = function() {
            return {
                "oid": new Date().getTime(),
                "name": "THE USER",
                "password": "supersafe123",
                "email": "dummy@test.net",
                "tel": "+49-30-1234567",
                // "invites": [] // empty list for debugging
                "invites": Invite.getDummyInviteList() // dummy list for debugging
            };
        };


        var login = function(user) {
            $log.log("REST login");
            var deferred = $q.defer();
            $http({
                method: "POST",
                url: USER_PATH + "login",
                // data: { "name": "Alice", "password": "yousnoozeyoulose" }        // for debugging <----------- ***** --
                data: { "name": user.name, "password": user.password }              // the real thing <---------- ***** --
            }).success(function(data, status, header, config) {
                $log.debug("DATA RESPONSE FROM USER LOGIN");
                $log.debug(data);
                deferred.resolve(data.data);
            }).error(function(data, status, header, config) {
<<<<<<< HEAD
                deferred.reject("Login auf dem Server fehlgeschlagen. " + getErrorMesage(status) + " " + data.errorMessage  + " " + data.stackTrace);
=======
                $log.debug(data);
                $log.debug(data.stackTrace);
                deferred.reject("Login auf dem Server fehlgeschlagen. " + getErrorMesage(status) + "\n" + data.message);
>>>>>>> 0e844bee13c576cd29118e259eb91900edd225c5
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
                $log.debug(data.data);
                deferred.resolve(data.data);
            }).error(function(data, status, header, config) {
                $log.debug(data);
                $log.debug(data.stackTrace);
                deferred.reject("Registrierung auf dem Server fehlgeschlagen. " + getErrorMesage(status) + "\n" + data.message);
            });
            return deferred.promise;
        };

        var deleteUser = function(user) {
            $log.log("REST deleteUser");
            var deferred = $q.defer();
            $http({
                method: "DELETE",
                url: USER_PATH + "delete",
                data: user.oid,
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
            var deferred = $q.defer();
            $http({
                method: "POST",
                url: USER_PATH + "update",
                data: {
                    oid: user.oid,
                    name: user.name,
                    password: user.password,
                    email: user.email,
                    tel: user.tel
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
                data: oid
            }).success(function(data, status, header, config) {
                deferred.resolve(data.data);                                    // the real thing <----------- ***** --
                // deferred.resolve(Invite.getDummyInviteList());               // for debugging <------------ ***** --
                //---- conversion, if necessary:
                // var _rawInvites = data.data;
                // var _invites = Invite.forInvitesConvertFromRawInvites(_rawInvites);
                // deferred.resolve(_invites);
            }).error(function(data, status, header, config) {
                deferred.reject("Benutzerdaten konnten nicht vom Server geholt werden. " + getErrorMesage(status));
            });
            return deferred.promise;
        };


        /*
         * Update or insert a survey.
         * - @param survey optional. If not specified, a new survey will be created on the server and inserted into the database.
         */
        var saveInvite = function(invite, user) {
            $log.debug("saveInvite: ");
            $log.debug(invite);
            $log.debug(invite.export(user));
            $log.log("REST save invite");
            var deferred = $q.defer();
            $http({
                method: "POST",
                url: INVITE_PATH + "save",
                data: invite.export(user)
            }).success(function(data, status, header, config) {
                $log.debug(data.data);
                deferred.resolve(data.data);
            }).error(function(data, status, header, config) {
                deferred.reject("Speichern der Terminumfrage auf dem Server fehlgeschlagen." + getErrorMesage(status));
            });
            return deferred.promise;
        };

        var deleteInvite = function(oid) {
            $log.log("REST deleteInvite");
            var deferred = $q.defer();
            $http({
                method: "DELETE",
                url: INVITE_PATH + "delete",
                data: oid,
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
            register: register,
            deleteUser: deleteUser,
            updateUser: updateUser,
            getInvites: getInvites,
            saveInvite: saveInvite,
            deleteInvite: deleteInvite
        };
    }]);

