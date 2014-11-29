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
            restService.logout();
            $log.log('Logout erfolgreich.');
            $location.path('/');
        };

        // TODO -> Account
        $scope.editUser = function() {
            $log.warn('editUser not accessible yet --> adapt implementation from editUser.js');
            // $scope.tempUser = angular.copy($scope.user);
            // $location.path('/editUser');
        };

    }]);