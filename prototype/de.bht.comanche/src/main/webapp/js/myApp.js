/*
 * Softwareprojekt SoSe/WiSe 2014, Team: Comanche
 * (C)opyright Sebastian Dassé, Mat.-Nr. 791537, s50602@beuth-hochschule.de
 * 
 * Module: my app -- the main module
 */


"use strict";

var myApp = angular.module("myApp", ["ui.bootstrap", "ngRoute","datePickerDate", "survey", "constants", "restModule"])
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
    .config(function($routeProvider) {
        $routeProvider
            .when('/', {
                templateUrl : 'pages/login.html',
                controller  : 'loginCtrl'
            })
            .when('/register', {
                templateUrl : 'pages/register.html',
                controller  : 'loginCtrl'
            })
            // Temporaly redirecting to invite
            .when('/cockpit', {
                templateUrl : 'pages/invite.html',
                controller  : 'inviteCtrl'
            })
            .when('/invite', {
                templateUrl : 'pages/invite.html',
                controller  : 'inviteCtrl'
            })
    });

    myApp.directive('ptjMenu', function() {
        return {
            restrict: 'E', 
            templateUrl: 'partials/menu.html'
        }
    })
    myApp.directive('ptjGroups', function() {
        return {
            restrict: 'E', 
            templateUrl: 'partials/invite/groups.html'
        }
    });
    myApp.directive('ptjSearch', function() {
        return {
            restrict: 'E', 
            templateUrl: 'partials/search.html'
        }
    });
    myApp.directive('ptjSurveyDetails', function() {
        return {
            restrict: 'E', 
            templateUrl: 'partials/surveydetails.html'
        }
    });
    myApp.directive('ptjMembers', function() {
        return {
            restrict: 'E', 
            templateUrl: 'partials/invite/members.html'
        }
    });
   
