/*
 * Softwareprojekt SoSe/WiSe 2014, Team: Comanche
 * (C)opyright Sebastian Dassé, Mat.-Nr. 791537, s50602@beuth-hochschule.de
 * 
 * Module: my app -- the main module
 */


"use strict";

angular.module("myApp", ["ngRoute","datePickerDate", "survey", "constants", "restModule"])
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
            .when('/logedin', {
                templateUrl : 'pages/logedin.html',
                controller  : 'loggedInCtrl'
            })
    })
    .directive('ptjMenu', function() {
        return {
            restrict: 'E', 
            templateUrl: 'partials/menu.html'
        }
    });
