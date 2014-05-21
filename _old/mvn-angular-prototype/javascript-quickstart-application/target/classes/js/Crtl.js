/*
 * Technik-Prototyp, SP1 SoSe 2014, Team: Comanche
 * (C)opyright Sebastian Dass�, Mat.-Nr. 791537, s50602@beuth-hochschule.de
 * 
 * Module: controller
 */

(function() {

    "use strict";

    var myApp = angular.module("myApp", []);
    myApp.controller("Ctrl", function($scope) {

        $scope.allowedPIN = /^\d{5}$/;


        $scope.reverseName = function() {
            return $scope.secrets && $scope.secrets.split('').reverse().join('');
        };


        $scope.show = {
            cardGame: true, 
            calculator: false, 
            somethingElse: false, 
            suitSelection: false, 
            cardSelection: false
        };

        $scope.togglePlayground = function(x) {
            $scope.show.cardGame = false;
            $scope.show.calculator = false;
            $scope.show.somethingElse = false;

            switch(x) {
            case "CARD_GAME":
                $scope.show.cardGame = true;
                break;
            case "CALCULATOR":
                $scope.show.calculator = true;
                break;
            case "SOMETHING_ELSE":
                $scope.show.somethingElse = true;
                break;
            default:
                throw new Error("This case should not happen.");
            }
        };

        $scope.toggleSelection = function(x) {
            switch(x) {
                case "SUIT_SELECTION":
                    $scope.show.suitSelection = !$scope.show.suitSelection;
                    $scope.show.cardSelection = false;
                    break;
                case "CARD_SELECTION":
                    $scope.show.cardSelection = !$scope.show.cardSelection;
                    $scope.show.suitSelection = false;
                    break;
                default:
                    throw new Error("This case should not happen.");
            }
        };


        $scope.cards = {
            suit: ["Karo", "Herz", "Pik", "Kreuz"], 
            card: ["Sieben", "Acht", "Neun", "Zehn", "Bube", "Dame", "K\u00f6nig", "As"]
        }

        $scope.test = { 
            update: function() {
                this.suit = randomElementFrom($scope.cards.suit); 
                this.card = randomElementFrom($scope.cards.card); 
                this.date = new Date().toLocaleTimeString();
            }
        };
        $scope.test.update();
        

        $scope.add = function() {
            $scope.sum = parseInt($scope.a) + parseInt($scope.b);
        };

    });
    
    var randomElementFrom = function(array_) {
        var i = parseInt(Math.random() * array_.length);
        return array_[i];
    };

}());
