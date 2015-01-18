/*
 * Softwareprojekt SoSe/WiSe 2014, Team: Comanche
 * (C)opyright Sebastian Dassé, Mat.-Nr. 791537, s50602@beuth-hochschule.de
 * (C)opyright Duc Tung Tong, Mat.-Nr. 798029, s51488@beuth-hochschule.de
 * Module: my app -- the main module
 */

'use strict';

<<<<<<< HEAD
"use strict";

angular.module("myApp", ["ui.bootstrap", "ngRoute","datePickerDate", "survey", "constants", "restModule", "typeAugmentations"])
    .constant("dialogMap", {
        USER_LOGIN: 0, 
        USER_REGISTER: 1, 
        USER_EDIT: 2, 
        SURVEY_SELECTION: 3,
        SURVEY_EDIT: 4
    })
    .factory("util", function() {
        var removeElementFrom = function(element, array) {
            var index = array.indexOf(element);
            if (index > -1) {
                array.splice(index, 1);
            }
        };
        return {
            removeElementFrom: removeElementFrom
        }
    })
    .config(function($routeProvider, $locationProvider) {
=======
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
>>>>>>> 28717c5aa782520e8b4a53c98880b3d839cc4135
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
<<<<<<< HEAD
            .when('/invite', {
                templateUrl : 'pages/invite.html',
                controller  : 'inviteCtrl'
            });
        $locationProvider.html5Mode(true);
=======
            .when('/invite/:inviteOid?', {
                templateUrl: 'pages/invite.html',
                controller: 'inviteCtrl',
                resolve: {
                    selectedInvitePromise: function($route, restService, Invite) {
                        var inviteOid = $route.current.params.inviteOid;
                        // console.log("inviteOid = " + inviteOid)
                        return (inviteOid === undefined) ? '' : restService.doGet(Invite, inviteOid);
                    },
                    // selectedInviteSurveyInvitesPromise: function($route, restService) { // <<<<<<<<<<<<<
                    //     var inviteOid = $route.current.params.inviteOid;
                    //     return (inviteOid === undefined) ? [] : restService.getSurveyInvites(inviteOid);
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
>>>>>>> 28717c5aa782520e8b4a53c98880b3d839cc4135
    })
    .directive('ptjMenu', function() {
        return {
            restrict: 'E',
<<<<<<< HEAD
            templateUrl: 'partials/menu.html'
        }
=======
            templateUrl: 'partials/menu.html',
            controller: 'menuCtrl'
        };
>>>>>>> 28717c5aa782520e8b4a53c98880b3d839cc4135
    })
    .directive('ptjGroups', function() {
        return {
            restrict: 'E',
            templateUrl: 'partials/invite/groups.html'
<<<<<<< HEAD
        }
=======
        };
>>>>>>> 28717c5aa782520e8b4a53c98880b3d839cc4135
    })
    .directive('ptjSearch', function() {
        return {
            restrict: 'E',
            templateUrl: 'partials/search.html'
<<<<<<< HEAD
        }
=======
        };
>>>>>>> 28717c5aa782520e8b4a53c98880b3d839cc4135
    })
    .directive('ptjSurveyDetails', function() {
        return {
            restrict: 'E',
            templateUrl: 'partials/invite/surveydetails.html'
<<<<<<< HEAD
        }
    })
    .directive('ptjMembers', function() {
        return {
            restrict: 'E',
            templateUrl: 'partials/invite/members.html'
        }
    });
=======
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
>>>>>>> 28717c5aa782520e8b4a53c98880b3d839cc4135
