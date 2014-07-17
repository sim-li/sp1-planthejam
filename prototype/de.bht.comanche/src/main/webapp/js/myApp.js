/*
 * Ojektverwaltung-UI, SP1 SoSe 2014, Team: Comanche
 * (C)opyright Sebastian Dassé, Mat.-Nr. 791537, s50602@beuth-hochschule.de
 * 
 * Module: my app -- the main module
 */


"use strict";

angular.module("myApp", ["datePickerDate", "survey", "constants", "restModule"])
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
        };
    });