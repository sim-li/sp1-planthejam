/*
 * Technik-Prototyp, SP1 SoSe 2014, Team: Comanche
 * (C)opyright Sebastian Dassé, Mat.-Nr. 791537, s50602@beuth-hochschule.de
 * 
 * Module: controller
 */

(function() {

    "use strict";

    var myApp = angular.module("myApp", []);
    myApp.controller("Ctrl", function($scope) {

        $scope.update = function() {
            $scope.test = {
                suit: random(suit), 
                card: random(card), 
                date: new Date().toLocaleTimeString()
            };
        };
        
        $scope.update();

        $scope.add = function() {
            $scope.sum = parseInt($scope.a) + parseInt($scope.b);
        };

        $scope.allowedPIN = /^\d{5}$/;

        $scope.XXX = $scope.inputPIN;
        // $scope.update = function() {
        //     $scope.XXX = new Date();
        // }

        $scope.reverseName = function() {
            return $scope.secrets && $scope.secrets.split('').reverse().join('');
        }

    });


    var suit = ["Karo", "Herz", "Pik", "Kreuz"];
    var card = ["Sieben", "Acht", "Neun", "Zehn", "Bube", "Dame", "K\u00f6nig", "As"];
    
    var random = function(array_) {
        var i = parseInt(Math.random() * array_.length);
        return array_[i];
    };

}());
