/**
 * @module myApp
 *
 * @author Sebastian Dass&eacute;
 */
angular.module('myApp')
    /**
     * The main controller for myApp.
     *
     * @class menuCtrl
     */
    .controller('ctrl', ['$scope', '$rootScope', '$log', 'Survey', 'TimeUnit', 'Type', 'User',
        function($scope, $rootScope, $log, Survey, TimeUnit, Type, User) {

            'use strict';

<<<<<<< HEAD
angular.module("myApp")
    .controller("ctrl", ["$scope", "$log", "Survey", "TimeUnit", "Type", "patterns", "dialogMap", "typeAugmentations", 
        function($scope, $log, Survey, TimeUnit, Type, patterns, dialogMap, typeAugmentations) {

            typeAugmentations();
            
            // make services available for the use in html
            $scope.$log = $log;
            $scope.TimeUnit = TimeUnit;
            $scope.Type = Type;
            $scope.patterns = patterns;
            
            $scope.session = {};
            $scope.warnings = {};
            $scope.dialogMap = dialogMap;

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
                    // "surveys": [{}] // empty list for debugging
                    "invites": [{}], // empty list for debugging
                    "groups": []
                };
                $log.log("user initialized");
                return user;
            };

            /*
             * initializes the session
             */
            $scope.initSession = function() {
                $scope.session = {
                    "user": getInitUser(), 
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
=======
            // make services available for the use in html
            $scope.$log = $log;
            $scope.TimeUnit = TimeUnit;
            $scope.Type = Type;

            $rootScope.warnings = '';

            /**
             * Discards all warnings from the app's main view.
             *
             * @method discardWarnings
             */
            $rootScope.discardWarnings = function() {
                $rootScope.warnings = '';
>>>>>>> 28717c5aa782520e8b4a53c98880b3d839cc4135
            };

        }
    ]);
