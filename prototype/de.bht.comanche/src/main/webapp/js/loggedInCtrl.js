/*
 * Softwareprojekt SoSe/WiSe 2014, Team: Comanche
 * (C)opyright Sebastian Dassé, Mat.-Nr. 791537, s50602@beuth-hochschule.de
 * 
 * Module: logged in controller
 */


"use strict";

angular.module("myApp")
    .controller("loggedInCtrl", ["$scope", "$location", "$log", "Invite", "restService", "dialogMap", "util", 
        function($scope, $location, $log, Invite, restService, dialogMap, util) {

        $scope.logout = function() {

            // *** if anything needs to be done on the server for logout, do it now ***

            $log.log("Logout erfolgreich.");
            $scope.initSession();
            $scope.showRegisterDialog = false;
            $location.path('/');
        };

        
        $scope.editUser = function() {
            $scope.session.tempUser = angular.copy($scope.session.user);
            $scope.session.state.isVal = dialogMap.USER_EDIT;
        };


        $scope.editContacts = function() {
            $log.warn("editContacts() not implemented");
        };


        $scope.editInvite = function() {
            if (!$scope.session.selectedInvite) {
                $log.log("Keine Terminumfrage ausgewaehlt.");
                return;
            }
            $scope.session.tempInvite = new Invite($scope.session.selectedInvite);
            $scope.session.state.isVal = dialogMap.SURVEY_EDIT;
            $log.log($scope.session.user);
        };

        $scope.addInvite = function() {
            $scope.session.tempInvite = new Invite();
            $scope.session.addingInvite = true;
            $scope.session.state.isVal = dialogMap.SURVEY_EDIT;
        };
        
        $scope.deleteSelectedInvite = function() {

            // *** ask: are you sure you want to delete? ***

            var _invite = $scope.session.selectedInvite;
            if (!_invite) {
                $log.log("Keine Terminumfrage ausgewaehlt.");
                return;
            }

            $log.log("deleteSelectedInvite: ");
            $log.log(_invite);
            
            restService.deleteInvite(_invite.oid)
            // restService.deleteInvite(_invite)
                .then(function(success) {
                    $log.log(success);
                    util.removeElementFrom(_invite, $scope.session.user.invites);
                    $scope.session.selectedInvite = $scope.session.user.invites[0] || "";
                    $scope.session.tempInvite = "";
                }, function(error) {
                    $log.error(error);
                    $scope.warnings.central = error;
                }, function(notification) {
                    // $log.log(notification); // for future use
                });

            
            //-------------------- ***
            console.log($scope.session.selectedInvite);
            console.log($scope.session.user);
            //-------------------- ***
        };


    }]);

