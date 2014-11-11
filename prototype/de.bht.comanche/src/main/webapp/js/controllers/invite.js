

"use strict";

angular.module('myApp')
    .controller('inviteCtrl', ['$scope', 'restService', "$log", "Group", function($scope, restService) {

        // make restService available for scope  -->  remove if not needed
        $scope.restService = restService;

        $scope.today = function() {
            $scope.dt = new Date();
        };
        $scope.today();

        $scope.clear = function () {
            $scope.dt = null;
        };
        // Disable weekend selection
        $scope.disabled = function(date, mode) {
            return ( mode === 'day' && ( date.getDay() === 0 || date.getDay() === 6 ) );
        };

        $scope.toggleMin = function() {
            $scope.minDate = $scope.minDate ? null : new Date();
        };
        $scope.toggleMin();

        $scope.open = function($event) {
            $event.preventDefault();
            $event.stopPropagation();

            $scope.opened = true;
        };

        $scope.users = [
        {name:'Blackjack', email:'bj@gmail.com'},
        {name:'Bob',      email:'bob@gmail.com'},
        {name:'Marie',     email:'marie@gmail.com'},
        {name:'Sarah',     email:'sr@gmail.com'},
        {name:'Simon',     email:'sm@gmail.com'},
        {name:'Max',       email:'max@gmail.com'},
        {name:'Sebastian', email:'sb@gmail.com'}
        ]

        $scope.groups = [
        {name: 'Rockettes'},
        {name: 'Ralf Laurens'},
        {name: 'Sam Fillers'},
        {name: 'Dam Killers'},
        {name: 'Masaki Haki Kaki'}
        ]

        $scope.addedUsers = []
        $scope.isCollapsed = true;

        $scope.switchDetailPanel = function() {
            if ($scope.addedUsers.length <= 0) {
                $scope.isCollapsed = true;
                console.log($scope.isCollapsed);
                return;
            }
            $scope.isCollapsed = !$scope.isCollapsed;
            console.log($scope.isCollapsed);
        }

        $scope.removeMember = function(index) {
            $scope.addedUsers.splice(index, 1);
            if ($scope.addedUsers.length <= 0) {
                $scope.isCollapsed = true;
            }
        }

        $scope.$watch('userSelected', function() {
            for (var i = 0, len = $scope.addedUsers.length; i < len; i++) {
                if($scope.addedUsers[i] === $scope.userSelected) {
                    $scope.isCollapsed = false;
                    return;
                }
            }
            if ($scope.userSelected !== undefined && $scope.userSelected.name !== undefined) {
                $scope.addedUsers.push($scope.userSelected);
                $scope.isCollapsed = false;
            }
        });
    }]);
