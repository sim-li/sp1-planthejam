/*
 * Softwareprojekt SoSe/WiSe 2014, Team: Comanche
 * (C)opyright Sebastian Dassé, Mat.-Nr. 791537, s50602@beuth-hochschule.de
 *
 * Module: REST service
 */


"use strict";

angular.module("restModule", ["datePickerDate", "constants", "invite"])
    .factory("restService", ["$http", "$q", "$log", "$filter", "Invite",
        function($http, $q, $log, $filter, Invite) {

        var USER_PATH = "rest/user/";
        var INVITE_PATH = "rest/invite/";
 
 
        var callHTTP = function(url, data, method) {
            $log.log("REST: " + url);
            var deferred = $q.defer();
            $http({
                method: method || "POST",
                url: url,
                data: data
                // , headers: { "Content-Type": "application/json" }                  // TODO check if necessary
            }).success(function(data, status, header, config) {
                $log.debug(data.data);
                deferred.resolve(data.data);
            }).error(function(data, status, header, config) {
                $log.debug(data.data);
                $log.debug(data.stackTrace);
                deferred.reject("REST: " + url + " failed. \n" + data.message);
            });
            return deferred.promise;
        };
        
        
        var login = function(user) {
            // return callHTTP(USER_PATH + "login", { "name": "Alice", "password": "yousnoozeyoulose" } );    // for debugging <----------- ***** --
            return callHTTP(USER_PATH + "login", { "name": user.name, "password": user.password } );          // the real thing <---------- ***** --
        };
        
        var register = function(user) {
            return callHTTP(USER_PATH + "login", { "name": user.name, "password": user.password } );
        };
        
        var deleteUser = function(user) {
            return callHTTP(USER_PATH + "login", { "oid": user.oid }, "DELETE");
            // --> $http( ... { headers: { "Content-Type": "application/json" } } )     // TODO check if necessary
        };

        var updateUser = function(user) {
            return callHTTP(USER_PATH + "update", 
                {
                    oid: user.oid,
                    name: user.name,
                    password: user.password,
                    email: user.email,
                    tel: user.tel
                });
        };
        
        
        var getInvites = function(oid) { //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
            return callHTTP(INVITE_PATH + "getInvites", oid);     // the real thing <----------- ***** --
            // return Invite.getDummyInviteList();              // for debugging <------------ ***** --
            //---- conversion, if necessary:
            // var _rawInvites = data.data;
            // var _invites = Invite.forInvitesConvertFromRawInvites(_rawInvites);
            // deferred.resolve(_invites);
        };

        /*
         * Update or insert a survey.
         * - @param invite optional. If not specified, a new invite will be created on the server and inserted into the database.
         */
        var saveInvite = function(invite, user) {
            // $log.debug("saveInvite: ");
            // $log.debug(invite);
            // $log.debug(invite.export(user));
            return callHTTP(INVITE_PATH + "save", invite.export(user));
        };
        
        var deleteInvite = function(oid) {
            return callHTTP(INVITE_PATH + "save", oid, "DELETE");
            // --> $http( ... { headers: { "Content-Type": "application/json" } } )     // TODO check if necessary
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

