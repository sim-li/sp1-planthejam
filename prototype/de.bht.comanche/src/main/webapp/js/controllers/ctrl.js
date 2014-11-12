/*
 * Softwareprojekt SoSe/WiSe 2014, Team: Comanche
 * (C)opyright Sebastian Dassé, Mat.-Nr. 791537, s50602@beuth-hochschule.de
 * 
 * Module: main controller
 */

"use strict";

angular.module("myApp")
    .controller("ctrl", ["$scope", "$rootScope", "$log", "Survey", "TimeUnit", "Type", "patterns", "dialogMap", "typeAugmentations", "User", 
        function($scope, $rootScope, $log, Survey, TimeUnit, Type, patterns, dialogMap, typeAugmentations, User) {
        
        typeAugmentations();
        
        // make services available for the use in html
        $scope.$log = $log;
        $scope.TimeUnit = TimeUnit;
        $scope.Type = Type;
        $scope.patterns = patterns;
        
        $rootScope.session = {};
        $scope.warnings = {};
        $scope.dialogMap = dialogMap;

        // /*
        //  * returns an initialized user
        //  */
        // var getInitUser = function() {
        //     var user = {
        //         "oid": "", 
        //         "name": "", 
        //         "password": "", 
        //         "email": "", 
        //         "tel": "", 
        //         // "surveys": getDummySurveyList() // *** replace list of dummy surveys by real data from server ***
        //         // "surveys": [{}] // empty list for debugging
        //         "invites": [{}], // empty list for debugging
        //         "groups": []
        //     };
        //     $log.log("user initialized");
        //     return user;
        // };

        /*
         * initializes the session
         */
        $scope.initSession = function() {
            $rootScope.session = {
                // "user": getInitUser(), 
                "user": new User(), // maybe:  "user": ""  does the trick -> in this case delete user class
                "state": { 
                    "isLoggedIn": false, 
                    "isVal": dialogMap.USER_LOGIN, 
                    "is": function(otherVal) { return otherVal === this.isVal } 
                }
            }
            $log.log("session initialized");
            $log.log($scope.session);
        };
        $scope.initSession();
        

        $scope.discardWarnings = function() {
            $scope.warnings = {};
        };

        $scope.toggleLoginDialog = function() {
            $scope.session.state.isVal = ($scope.session.state.is(dialogMap.USER_LOGIN)) ? dialogMap.USER_REGISTER : dialogMap.USER_LOGIN;
            $log.debug($scope.session)
        };

    }]);
    
