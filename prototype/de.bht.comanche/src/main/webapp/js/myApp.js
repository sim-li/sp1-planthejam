/*
 * Softwareprojekt SoSe/WiSe 2014, Team: Comanche
 * (C)opyright Sebastian Dassé, Mat.-Nr. 791537, s50602@beuth-hochschule.de
 * 
 * Module: my app -- the main module
 */

"use strict";

angular.module("myApp", ["ui.bootstrap", "xeditable", "ngRoute","datePickerDate", "survey", "constants", "restModule", "typeAugmentations", "group", "user"])
    .constant("dialogMap", { // TODO remove after routing works without it
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
        $routeProvider
            .when('/', {
                templateUrl : 'pages/login.html',
                controller  : 'loginCtrl'
            })
            .when('/register', {
                templateUrl : 'pages/register.html',
                controller  : 'loginCtrl'
            })
            .when('/cockpit', {
                templateUrl : 'pages/cockpit.html',
                controller  : 'inviteCtrl'
            })
            .when('/invite', {
                templateUrl : 'pages/invite.html',
                controller  : 'inviteCtrl',
            });
        // $locationProvider.html5Mode(true); // for prettier urls
    })
    .directive('ptjMenu', function() {
        return {
            restrict: 'E',
            templateUrl: 'partials/menu.html'
        }
    })
    .directive('ptjGroups', function() {
        return {
            restrict: 'E',
            templateUrl: 'partials/invite/groups.html'
        }
    })
    .directive('ptjSearch', function() {
        return {
            restrict: 'E',
            templateUrl: 'partials/search.html'
        }
    })
    .directive('ptjSurveyDetails', function() {
        return {
            restrict: 'E',
            templateUrl: 'partials/invite/surveydetails.html'
        }
    })
    .directive('ptjMembers', function() {
        return {
            restrict: 'E',
            templateUrl: 'partials/invite/members.html'
        }
    })
    .directive('ptjSurveySelect', function() {
        return {
            restrict: 'E',
            templateUrl: 'partials/surveyselect.html'
        }
    })
    .run(function(editableOptions) {
        editableOptions.theme = 'bs3'; // bootstrap3 theme. Can be also 'bs2', 'default'
    });

