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
 * @author Duc Tung Tong
 */
angular.module('myApp', ['ngRoute', 'ui.bootstrap', 'ui.bootstrap.datetimepicker', 'xeditable', 'baseModel',
        'constants', 'datePickerDate', 'group', 'invite', 'restModule', 'survey', 'timePeriod', 'user', 'util', 'ui.calendar'
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
                    surveysPromise: function(restService, Survey) {
                        return restService.doGetMany(Survey);
                    },
                    invitesPromise: function(restService, Invite) {
                        return restService.doGetMany(Invite);
                    }
                }
            })
            .when('/survey/:inviteOid?', {
                templateUrl: 'pages/survey.html',
                controller: 'surveyCtrl',
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
                    usersPromise: function(restService) {
                            return restService.getAllUsers();
                        }
                        //, timePeriodPromise: function(restService, timePeriod) {
                        //     return restService.doGetMany(timePeriod);
                        // }
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
    .directive('ptjTimeperiodSelector', function() {
        return {
            restrict: 'E',
            templateUrl: 'partials/timeperiodSelector.html',
            controller: 'timeperiodSelectorCtrl'
        };
    })
    .run(function(editableOptions /*, typeAugmentations*/ ) {
        editableOptions.theme = 'bs3'; // bootstrap3 theme. Can be also 'bs2', 'default'
        // typeAugmentations();
    });