/*
 * Softwareprojekt SoSe/WiSe 2014, Team: Comanche
 * (C)opyright Sebastian Dassé, Mat.-Nr. 791537, s50602@beuth-hochschule.de
 * (C)opyright Duc Tung Tong, Mat.-Nr. 798029, s51488@beuth-hochschule.de
 * Module: my app -- the main module
 */

'use strict';

/**
 * Provides the webapp. Also has:
 * - routes
 * - directives
 * ...
 *
 * @module myApp
 * @main
 * @requires ui.bootstrap
 * @requires xeditable
 * @requires ngRoute
 * @requires typeAugmentations
 * @requires constants
 * @requires restModule
 * @requires datePickerDate
 * @requires baseModel
 * @requires user
 * @requires invite
 * @requires survey
 * @requires group
 *
 * @author Sebastian Dass&eacute;
 */
angular.module('myApp', ['ui.bootstrap', 'xeditable', 'ngRoute', 'typeAugmentations', 'constants', 'restModule', 'datePickerDate', 'user', 'invite', 'survey', 'group', 'baseModel'])
    .factory('util', function() {

        // TODO is it still in use?

        /**
         * this method removes an element from array
         * @param  {object} element An element from array
         * @param  {array} Array
         * @return {array}          Array after removing element
         */
        var removeElementFrom = function(element, array) {
            var index = array.indexOf(element);
            if (index > -1) {
                array.splice(index, 1);
            }
        };
        return {
            removeElementFrom: removeElementFrom
        };
    })
    .config(function($routeProvider /*, $locationProvider*/ ) {
        $routeProvider
            .when('/', {
                templateUrl: 'pages/login.html',
                controller: 'loginCtrl'
            })
            .when('/register', {
                templateUrl: 'pages/register.html',
                controller: 'loginCtrl'
            })
            .when('/cockpit', {
                templateUrl: 'pages/cockpit.html',
                controller: 'cockpitCtrl',
                resolve: {
                    invitesPromise: function(restService, Invite) {
                        return restService.doGetMany(Invite);
                    }
                }
            })
            .when('/invite/:inviteOid?', {
                templateUrl: 'pages/invite.html',
                controller: 'inviteCtrl',
                resolve: {
                    selectedInvitePromise: function($route, restService, Invite) {
                        var inviteOid = $route.current.params.inviteOid;
                        console.log("inviteOid = " + inviteOid)
                        return (inviteOid === undefined) ? '' : restService.doGet(Invite, inviteOid);
                    },
                    currentUserPromise: function($route, restService, User) {
                        return ($route.current.params.inviteOid !== undefined) ? '' : restService.doGet(User);
                    },
                    // TODO maybe not necessary to get all invites for this route?
                    invitesPromise: function(restService, Invite) {
                        return restService.doGetMany(Invite);
                    },
                    groupsPromise: function(restService, Group) {
                        return restService.doGetMany(Group);
                    },
                    usersPromise: function(restService, User) {
                        return restService.doGetMany(User);
                    }
                }
            })
            .when('/account', {
                templateUrl: 'pages/editUser.html',
                controller: 'editUserCtrl',
                resolve: {
                    userPromise: function(restService, User) {
                        return restService.doGet(User);
                    }
                }
            });
        // $locationProvider.html5Mode(true); // for prettier urls
    })
    .directive('ptjMenu', function() {
        return {
            restrict: 'E',
            templateUrl: 'partials/menu.html',
            controller: 'menuCtrl'
        };
    })
    .directive('ptjGroups', function() {
        return {
            restrict: 'E',
            templateUrl: 'partials/invite/groups.html'
        };
    })
    .directive('ptjSearch', function() {
        return {
            restrict: 'E',
            templateUrl: 'partials/search.html'
        };
    })
    .directive('ptjSurveyDetails', function() {
        return {
            restrict: 'E',
            templateUrl: 'partials/invite/surveydetails.html'
        };
    })
    .directive('ptjSurveySelect', function() {
        return {
            restrict: 'E',
            templateUrl: 'partials/surveyselect.html'
        };
    })
    .run(function(editableOptions /*, typeAugmentations*/ ) {
        editableOptions.theme = 'bs3'; // bootstrap3 theme. Can be also 'bs2', 'default'
        // typeAugmentations();
    });