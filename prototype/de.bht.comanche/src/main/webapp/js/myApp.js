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
 * @requires ngRoute
 * @requires ui.bootstrap
 * @requires ui.bootstrap.datetimepicker
 * @requires xeditable
 * @requires baseModel
 * @requires constants
 * @requires datePickerDate
 * @requires group
 * @requires invite
 * @requires restModule
 * @requires survey
 * @requires timePeriod
 * @requires user
 * @requires util
 *
 * @author Sebastian Dass&eacute;
 */
angular.module('myApp', ['ngRoute', 'ui.bootstrap', 'ui.bootstrap.datetimepicker', 'xeditable', 'baseModel',
        'constants', 'datePickerDate', 'group', 'invite', 'restModule', 'survey', 'timePeriod', 'user', 'util'
    ])
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
            // selectedInviteSurveyInvitesPromise: function($route, restService, Invite) { // <<<<<<<<<<<<<
            //     var inviteOid = $route.current.params.inviteOid;
            //     return (inviteOid === undefined) ? [] : restService.getSurveyInvites(Invite, inviteOid);
            // },
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