/*
 * Softwareprojekt SoSe/WiSe 2014, Team: Comanche
 * (C)opyright Sebastian Dassé, Mat.-Nr. 791537, s50602@beuth-hochschule.de
 *
 * Module: menu controller
 */

'use strict';

angular.module('myApp')
    .controller('menuCtrl', ['$scope', '$location', '$log', 'restService', function($scope, $location, $log, restService) {

        $scope.logout = function() {

            // *** call rest -> logout to disable cookies ***
            // *** if anything else needs to be done on the server for logout, do it now ***

            restService.logout();
            $log.log('Logout erfolgreich.');
            $location.path('/');
        };

    }]);