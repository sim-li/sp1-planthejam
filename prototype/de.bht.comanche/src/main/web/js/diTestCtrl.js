"use strict";

angular.module("diTestApp", ["myInjection", "anotherInjection"])
    .controller("diTestCtrl", ["$scope", "myService", "anotherService", function($scope, myService, anotherService) {
        
        $scope.c1 = {
            testOutput1: " ", 
            testOutput2: 1000, 
            testSelection: myService.someValues
        };
        var _s = $scope.c1;

        _s.testModel = _s.testSelection[0], 
        
        _s.test1 = function() {
            _s.testOutput1 += " TEST";
        }

        _s.test2 = function() {
            _s.testOutput2 = myService.addTwoVals(_s.testOutput2, _s.testModel);
            myService.someValues.push(_s.testOutput2);
        };
        
    }])
    
    .controller("anotherCtrl", ["$scope", "anotherService", function($scope, anotherService) {
        
        $scope.c2 = {
            testOutput1: "SOME OTHER OUTPUT", 
            testOutput2: 1, 
            testSelection: anotherService.someValues
        };
        var _s = $scope.c2;
        
        _s.testModel = _s.testSelection[0];

        _s.test1 = function() {
            _s.testOutput1 += " +";
        };

        _s.test2 = function() {
            _s.testOutput2 = anotherService.mulTwoVals(_s.testOutput2, _s.testModel);
            anotherService.someValues.push(_s.testOutput2);
        };
    }]);


angular.module("myInjection", [])
    .factory("myService", function() {

        var someValues = [1, 2, 3, 4, 5, 6, 7, 8, 9], 
            addTwoVals = function(a, b) {
                return a + b;
            };

        return {
            someValues: someValues,
            addTwoVals: addTwoVals
        };
    });

