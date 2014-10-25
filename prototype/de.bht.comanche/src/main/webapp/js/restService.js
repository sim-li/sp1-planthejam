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
    		var DUMMY_INVITE_LIST = false;
	    	var DUMMY_LOGIN = false;
	        var LOG = true;
	        var USER_PATH = "rest/user/";
	        var INVITE_PATH = "rest/invite/";
	
	        var callHTTP = function(url, data, method, headers) {
	        	if (LOG) {
	        		$log.log("REST: " + url);
	        	}
	            var deferred = $q.defer();
	            $http({
	                method: method || "POST",
	                url: url,
	                data: data, 
	                headers: headers
		            /*
		             * TODO@CodeCleanup: If not necessary, remove from code. Removed equivalent code 
		             * duplicate from deleteUser & deleteInvite.
		             * >
		             *    $http( ... { headers: { "Content-Type": "application/json" } } ) 
		             * <
		             */
	            }).success(function(data, status, header, config) {
	            	if (LOG) {
	            		$log.debug(data);
	            	}
	                deferred.resolve(data);
	            }).error(function(data, status, header, config) {
	            	if (LOG) {
	                    $log.debug(data);
	                    $log.debug(data.stackTrace);
	            	}
	                deferred.reject("REST: " + url + " failed. \n" + data.message);
	            });
	            return deferred.promise;
	        };
	        
	        var login = function(user) {
	        	if (DUMMY_LOGIN) {
	        		return callHTTP(USER_PATH + "login", { "name": "Alice", "password": "yousnoozeyoulose" } );
	        	}
	            return callHTTP(USER_PATH + "login", { "name": user.name, "password": user.password } );
	        };
	        
	        var register = function(user) {
	            return callHTTP(USER_PATH + "register", 
	               {
	                    "name": user.name,
	                    "password": user.password,
	                    "email": user.email,
	                    "tel": user.tel
	                });
	        };
	        
	        var deleteUser = function(user) {
	            return callHTTP(USER_PATH + "delete", { "oid": user.oid }, "DELETE", { "Content-Type": "application/json" } );
	            
	        };
	
	        var updateUser = function(user) {
	            return callHTTP(USER_PATH + "update", 
	                {
	                    "oid": user.oid,
	                    "name": user.name,
	                    "password": user.password,
	                    "email": user.email,
	                    "tel": user.tel
	                });
	        };
	        
	        var getInvites = function(oid) {
	        	//TODO@CodeCleanup: If not necessary, remove from code.
	            if (false) {
	          	    var _rawInvites = data;
	                var _invites = Invite.forInvitesConvertFromRawInvites(_rawInvites);
	                deferred.resolve(_invites);
	            }
	            return DUMMY_INVITE_LIST === TRUE ? Invite.getDummyInviteList : callHTTP(INVITE_PATH + "getInvites", oid);
	       };
	
	        /*
	         * Update or insert a survey.
	         * - @param invite optional. If not specified, a new invite will be created on the server and inserted into the database.
	         */
	        var saveInvite = function(invite, user) {
	        	if (LOG) {
	        		$log.debug("saveInvite: ");
	        		$log.debug(invite);
	        		$log.debug(invite.export(user));
	        	}
	            return callHTTP(INVITE_PATH + "save", invite.export(user));
	        };
	        
	        var deleteInvite = function(oid) {
	            return callHTTP(INVITE_PATH + "delete", oid, "DELETE", { "Content-Type": "application/json" } );
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

