/*
 * Technik-Prototyp, SP1 SoSe 2014, Team: Comanche
 * (C)opyright Sebastian Dassé, Mat.-Nr. 791537, s50602@beuth-hochschule.de
 * 
 * Module: myApp1
 */

(function() {

    "use strict";

    angular.module("myApp1", [])
        .controller("controller1", ["$scope", function($scope) {

            // console.log($scope);
            // $scope.helloTo = {};
            // $scope.helloTo.title = "World, AngularJS"; 

            $scope.update = function() {
                $scope.test = {
                    suit: random(suit), 
                    card: random(card), 
                    date: new Date().toLocaleTimeString()
                };
            };

            $scope.update();
        }])
        .controller("controller2", ["$scope", function($scope) {
            $scope.update = function() {
                $scope.test = {
                    suit: random(suit) + "#", 
                    card: random(card) + "#", 
                    date: new Date().toLocaleTimeString()
                };
            };

            $scope.update();
        }]);


    var suit = ["Karo", "Herz", "Pik", "Kreuz"];
    var card = ["Sieben", "Acht", "Neun", "Zehn", "Bube", "Dame", "Koenig", "As"];
    
    var random = function(array_) {
        var i = parseInt(Math.random() * array_.length);
        return array_[i];
    };

}());
