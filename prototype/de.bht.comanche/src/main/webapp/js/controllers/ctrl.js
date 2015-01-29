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
    .controller('ctrl', ['$scope', '$rootScope',
        function($scope, $rootScope) {

            'use strict';

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