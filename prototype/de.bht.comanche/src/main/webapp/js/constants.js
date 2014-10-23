/*
 * Softwareprojekt SoSe/WiSe 2014, Team: Comanche
 * (C)opyright Sebastian Dassé, Mat.-Nr. 791537, s50602@beuth-hochschule.de
 * 
 * Module: constants
 */


"use strict";

angular.module("constants", [])
    .constant("TimeUnit", function() {
        // var DAY = "Tag", 
        //     WEEK = "Woche", 
        //     MONTH = "Monat";
        var DAY = "DAY", 
            WEEK = "WEEK", 
            MONTH = "MONTH";
        return {
            DAY: DAY, 
            WEEK: WEEK, 
            MONTH: MONTH, 
            options_: [ DAY, WEEK, MONTH ]
        };
    }())
    .constant("Type", function() {
        // var ONE_TIME = "einmalig", 
        //     RECURRING = "wiederholt";
        var ONE_TIME = "ONE_TIME", 
            RECURRING = "RECURRING";
        return {
            ONE_TIME: ONE_TIME, 
            RECURRING: RECURRING, 
            options_: [ ONE_TIME, RECURRING ]
        };
    }())
    .constant("patterns", {
            password: /^[\S]{8,20}$/, // no whitespace allowed -- TODO: at this point whitespace is still allowed at the beginning and end
            email: /^[a-zA-Z][\w]*@[a-zA-Z]+\.[a-zA-Z]{2,3}$/, // TODO
            tel: /^[0-9]{4,12}$/ // TODO
    });
