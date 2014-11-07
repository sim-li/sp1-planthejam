/*
 * Softwareprojekt SoSe/WiSe 2014, Team: Comanche
 * (C)opyright Sebastian Dassé, Mat.-Nr. 791537, s50602@beuth-hochschule.de
 * 
 * Module: edit invite controller
 */

"use strict";

angular.module("myApp")
    .controller("editInviteCtrl", ["$scope", "$log", "DatePickerDate", "restService", "dialogMap", "util", 
        function($scope, $log, DatePickerDate, restService, dialogMap, util) {

        $scope.cancelEditInvite = function() {
            $scope.session.tempInvite = "";
            $scope.session.addingInvite = false;
            $scope.session.state.isVal = dialogMap.SURVEY_SELECTION;
            $log.log($scope.session.selectedInvite);
            $log.log($scope.session.user);
        };

        $scope.saveInvite = function() {
            var _invite = $scope.session.tempInvite;
            

            // TODO remove
            $log.debug("saveInvite: ");
            $log.debug(_invite);


            // *** test the change to async ***

            restService.saveInvite(_invite, $scope.session.user)
                .then(function(invite) {

                    if (!$scope.session.addingInvite) {
                        util.removeElementFrom(_invite, $scope.session.user.invites);
                    }
                    $scope.session.user.invites.push(invite);
                    // $scope.session.user.invites.sort(function(a, b){ return a.name.localeCompare(b.name) }); // FIXME
                    $scope.session.selectedInvite = invite;
                    $scope.session.tempInvite = "";
                    $scope.session.addingInvite = false;
                    $scope.session.state.isVal = dialogMap.SURVEY_SELECTION;

                }, function(error) {
                    $log.error(error);
                    $scope.warnings.central = error;
                    // $scope.initSession();
                }, function(notification) {
                    // $log.log(notification); // for future use
                });


            // var _fromSaveInvite = restService.saveInvite(_invite);
            //-- falls kein Fehlschlag:
            // _invite = _fromSaveIvite.survey;

            // if (!$scope.session.addingInvite) {
            //     util.removeElementFrom(_invite, $scope.session.user.invites);
            // }

            // $scope.session.user.invites.push(_invite);
            // $scope.session.user.invites.sort(function(a, b){ return a.name.localeCompare(b.name) });

            // $scope.session.selectedInvite = _invite;
            // $scope.session.tempInvite = "";
            // $scope.session.addingInvite = false;


            //---------------- ***
            console.log($scope.session.selectedInvite);
            console.log($scope.session.user);
            //---------------- ***
        };
        
        $scope.removeTimeperiodFromTempInvite = function(timeperiod) {
            util.removeElementFrom(timeperiod, $scope.session.tempInvite.survey.possibleTimeperiods);
        };

        $scope.addTimeperiodToTempInvite = function() {
            $scope.session.tempInvite.survey.possibleTimeperiods.push({ "startTime": new DatePickerDate(new Date()), "durationInMins": 60 });
        };


    }]);

