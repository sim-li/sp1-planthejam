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
angular.module('myApp', ['constants', 'models', 'ngRoute', 'rest', 'ui.bootstrap', 'ui.bootstrap.datetimepicker',
        'ui.calendar', 'uiUtilsModule', 'util', 'xeditable'
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
                    },
                    messagesPromise: function(restService) {
                        return restService.getMessages();
                    }
                }
            })
            .when('/survey/:surveyOid?', {
                templateUrl: 'pages/survey.html',
                controller: 'surveyCtrl',
                resolve: {
                    selectedSurveyPromise: function($route, restService, Survey) {
                        var surveyOid = $route.current.params.surveyOid;
                        // console.log("surveyOid = " + surveyOid)
                        return (surveyOid === undefined) ? '' : restService.doGet(Survey, surveyOid);
                    },
                    selectedSurveyInvitesPromise: function($route, restService) { // <<<<<<<<<<<<<
                        var surveyOid = $route.current.params.surveyOid;
                        return (surveyOid === undefined) ? [] : restService.getSurveyInvites(surveyOid);
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
            templateUrl: 'partials/invite/groups.html',
            controller: 'groupsCtrl'
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
    .directive('ptjCalendar', function() {
        return {
            restrict: 'E',
            scope: {
                allowedTimePeriods: '=allowed',
                resultingTimePeriods: '=resulting'
            },
            // template: '<div class="calendar" ng-model="eventSources" ui-calendar="uiConfig.calendar"></div>',
            templateUrl: 'partials/calendar.html',
            controller: 'calendarCtrl'
        };
    })
    .directive('ptjCalendarModal', function() {
        return {
            restrict: 'E',
            scope: {
                allowedTimePeriods: '=allowed',
                resultingTimePeriods: '=resulting',
                save: '&'
                    /*,
                                    selected: '='*/
            },
            templateUrl: 'partials/calendarModal.html',
            controller: 'calendarModalCtrl'
        };
    })
    .run(function(editableOptions) {
        editableOptions.theme = 'bs3'; // bootstrap3 theme. Can be also 'bs2', 'default'
    });