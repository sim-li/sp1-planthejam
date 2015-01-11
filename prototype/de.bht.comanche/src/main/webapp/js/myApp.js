'use strict';

/**
 * Provides the webapp. Also has:
 * - routes
 * - directives
 * ...
 *
 * @module myApp
 * @main
 * @requires constants
 * @requires models
 * @requires rest
 * @requires util
 *
 * @author Sebastian Dass&eacute;
 * @author Duc Tung Tong
 */
angular.module('myApp', ['constants', 'models', 'ngRoute', 'rest', 'ui.bootstrap', 'ui.bootstrap.datetimepicker', 'ui.calendar', 'util', 'xeditable'])
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
    .run(function(editableOptions /*, typeAugmentations*/ ) {
        editableOptions.theme = 'bs3'; // bootstrap3 theme. Can be also 'bs2', 'default'
        // typeAugmentations();
    });