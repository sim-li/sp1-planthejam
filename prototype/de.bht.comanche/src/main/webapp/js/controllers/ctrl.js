/*
 * Softwareprojekt SoSe/WiSe 2014, Team: Comanche
 * (C)opyright Sebastian Dassé, Mat.-Nr. 791537, s50602@beuth-hochschule.de
 *
 * Module: main controller
 */

'use strict';

angular.module('myApp')
    .controller('ctrl', ['$scope', '$rootScope', '$log', 'Survey', 'TimeUnit', 'Type', 'User',
        function($scope, $rootScope, $log, Survey, TimeUnit, Type, User) {

            // make services available for the use in html
            $scope.$log = $log;
            $scope.TimeUnit = TimeUnit;
            $scope.Type = Type;

            $rootScope.warnings = '';

            $rootScope.discardWarnings = function() {
                $rootScope.warnings = '';
            };

        }
    ]);