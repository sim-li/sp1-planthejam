/**
 * @module myApp
 *
 * @author Sebastian Dass&eacute;
 */
angular.module('myApp')
    /**
     * The main controller for myApp.
     *
     * @class menuCtrl
     */
    .controller('ctrl', ['$scope', '$rootScope', /*'$log', */ 'Survey', /*'TimeUnit', 'SurveyType', */ 'User',
        function($scope, $rootScope, /*$log, */ Survey, /*TimeUnit, SurveyType, */ User) {

            'use strict';

            // make services available for the use in html
            // $scope.$log = $log;
            // $scope.TimeUnit = TimeUnit;
            // $scope.Type = Type;

            $rootScope.warnings = '';

            /**
             * Discards all warnings from the app's main view.
             *
             * @method discardWarnings
             */
            $rootScope.discardWarnings = function() {
                $rootScope.warnings = '';
            };

        }
    ]);