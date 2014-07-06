"use strict";

angular.module("anotherInjection", [])
    .factory("anotherService", function() {

        var someValues = [9, 8, 7, 6, 5, 4, 3, 2, 1], 
            mulTwoVals = function(a, b) {
                return a * b;
            };

        return {
            someValues: someValues,
            mulTwoVals: mulTwoVals
        };
    });

