/**
 * @module myApp
 *
 * @author Sebastian Dass&eacute;
 */
angular.module('myApp')
    /**
     * The controller for the menu directive.
     *
     * @class menuCtrl
     */
    .controller('menuCtrl', ['$scope', '$location', '$log', 'restService', function($scope, $location, $log, restService) {

        'use strict';

        /**
         * Does a log out and switches to the login view.
         *
         * @method logout
         */
        $scope.logout = function() {
            restService.logout();
            $log.log('Logout erfolgreich.');
            $location.path('/');
        };

    }]);