/*
 * Softwareprojekt SoSe/WiSe 2014, Team: Comanche
 * (C)opyright Sebastian Dassé, Mat.-Nr. 791537, s50602@beuth-hochschule.de
 *
 * Module: logged in controller
 */


'use strict';

angular.module('myApp')
    .controller('cockpitCtrl', ['$scope', '$location', '$log', 'restService', 'Invite', 'Group', 'util', 'invites', 'groups',
        function($scope, $location, $log, restService, Invite, Group, util, invites, groups) {

            // resolve the promises passed to this route
            $scope.invites = Invite.importMany(invites);
            $scope.groups = groups;

            $scope.selectedInvite = $scope.invites[0];

            $scope.selectInvite = function(invite) {
                $scope.selectedInvite = invite;
                // $log.debug($scope.selectedInvite);
            };

            // TODO -> Account
            // $scope.editUser = function() {
            //     $scope.tempUser = angular.copy($scope.user);
            //     // $location.path('/editUser');
            // };

            $scope.editInvite = function() {
                if (!$scope.selectedInvite) {
                    $log.log('Keine Terminumfrage ausgewaehlt.');
                    return;
                }
                $location.path('/invite/' + $scope.selectedInvite.oid);
            };

            $scope.addInvite = function() {
                $location.path('/invite');
            };

            $scope.deleteSelectedInvite = function() {
                // if (!$scope.selectedInvite) {
                //     $log.log('Keine Terminumfrage ausgewaehlt.');
                //     return;
                // }
                restService.doDelete($scope.selectedInvite)
                    .then(function(success) {
                        $location.path('/cockpit');
                    } /*, function(error) { $log.log(error); }*/ );
            };
        }
    ]);