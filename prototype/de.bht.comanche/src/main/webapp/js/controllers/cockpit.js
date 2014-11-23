/*
 * Softwareprojekt SoSe/WiSe 2014, Team: Comanche
 * (C)opyright Sebastian Dassé, Mat.-Nr. 791537, s50602@beuth-hochschule.de
 *
 * Module: logged in controller
 */


'use strict';

angular.module('myApp')
    .controller('cockpitCtrl', ['$scope', '$rootScope', '$location', '$log', 'restService', 'Invite', 'Group', 'util', 'invites', 'groups',
        function($scope, $rootScope, $location, $log, restService, Invite, Group, util, invites, groups) {

            // resolving the promises passed to this route
            $scope.invites = invites;
            $scope.groups = groups;
            $scope.selectedInvite = $scope.invites[0];

            $scope.selectInvite = function(invite) {
                $scope.selectedInvite = invite;
                // $log.debug($scope.selectedInvite);
            };

            $scope.editUser = function() {
                $scope.tempUser = angular.copy($scope.user);
                // $location.path('/editUser'); // TODO
            };

            $scope.editInvite = function() {
                if (!$scope.selectedInvite) {
                    $log.log('Keine Terminumfrage ausgewaehlt.');
                    return;
                }
                $scope.tempInvite = new Invite($scope.selectedInvite);
                $log.log($scope.user);
            };

            $scope.addInvite = function() {
                $scope.tempInvite = new Invite();
                $scope.addingInvite = true;
            };

            $scope.deleteSelectedInvite = function() {
                var _invite = $scope.selectedInvite;
                if (!_invite) {
                    $log.log('Keine Terminumfrage ausgewaehlt.');
                    return;
                }
                $log.log('deleteSelectedInvite: ');
                $log.log(_invite);
                restService.deleteInvite(_invite.oid)
                    // restService.deleteInvite(_invite)
                    .then(function(success) {
                        $log.log(success);
                        util.removeElementFrom(_invite, $scope.invites);
                        $scope.selectedInvite = $scope.invites[0] || '';
                        $scope.tempInvite = '';
                    }, function(error) {
                        $log.error(error);
                        $rootScope.warnings = error;
                    }, function(notification) {
                        // $log.log(notification); // for future use
                    });

            };
        }
    ]);