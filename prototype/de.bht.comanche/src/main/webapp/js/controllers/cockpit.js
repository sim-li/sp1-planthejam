/*
 * Softwareprojekt SoSe/WiSe 2014, Team: Comanche
 * (C)opyright Sebastian Dassé, Mat.-Nr. 791537, s50602@beuth-hochschule.de
 *
 * Module: logged in controller
 */


'use strict';

angular.module('myApp')
    .controller('cockpitCtrl', ['$scope', '$location', '$log', 'restService', 'Invite', 'util', 'invitesPromise',
        function($scope, $location, $log, restService, Invite, util, invitesPromise) {

            // resolve the promises passed to this route
            $scope.invites = Invite.importMany(invitesPromise);

            $scope.selectedInvite = $scope.invites[0];

            $scope.selectInvite = function(invite) {
                $scope.selectedInvite = invite;
                // $log.debug($scope.selectedInvite);
            };

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