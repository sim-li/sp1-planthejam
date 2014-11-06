angular.module('myApp')
  .controller('inviteCtrl', ['$scope', function($scope) {
    $scope.users = [
       {name:'Blackjack', email:'bj@gmail.com'},
       {name:'Bob', 	  email:'bob@gmail.com'},
       {name:'Marie',     email:'marie@gmail.com'},
       {name:'Sarah',     email:'sr@gmail.com'},
       {name:'Simon',     email:'sm@gmail.com'},
       {name:'Max',       email:'max@gmail.com'},
       {name:'Sebastian', email:'sb@gmail.com'}
    ]

    $scope.addedUsers = []
   
    $scope.showInviteButton = function(){
    	console.log("number of users in group : " + $scope.addedUsers.length)
    	return $scope.addedUsers.length == 0 ? false: true;
    }
 }]);