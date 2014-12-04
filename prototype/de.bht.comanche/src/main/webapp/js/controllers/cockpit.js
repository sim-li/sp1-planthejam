/**
 * @module myApp
 *
 * @author Sebastian Dass&eacute;
 */
angular.module('myApp')
    /**
     * The controller for the cockpit view.
     *
     * @class cockpitCtrl
     */
    .controller('cockpitCtrl', ['$scope', '$location', '$log', 'restService', 'Invite', 'util', 'invitesPromise', 'Model',
        function($scope, $location, $log, restService, Invite, util, invitesPromise, Model) {

            'use strict';

            // resolve the promises passed to this route
            $scope.invites = Model.importMany(Invite, invitesPromise);

            // preselects the first invite in the list
            $scope.selectedInvite = $scope.invites[0];


            /**
             * Selects the specified invite.
             *
             * @method selectInvite
             * @param  {Invite} invite the invite
             */
            $scope.selectInvite = function(invite) {
                $scope.selectedInvite = invite;
                // $log.debug($scope.selectedInvite);
            };

            /**
             * Switches to the invite edit view to edit the selected invite.
             *
             * @method editInvite
             */
            $scope.editInvite = function() {
                if (!$scope.selectedInvite) {
                    $log.log('Keine Terminumfrage ausgewaehlt.');
                    return;
                }
                $location.path('/invite/' + $scope.selectedInvite.oid);
            };

            /**
             * Switches to the invite creation view to create a new invite.
             *
             * @method addInvite
             */
            $scope.addInvite = function() {
                $location.path('/invite');
            };

            /**
             * Deletes the selected invite and refreshes the cockpit view.
             *
             * @method deleteSelectedInvite
             */
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