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
    $scope.isCollapsed = false;
    $scope.$watch('userSelected', function() {
      for (var i = 0, len = $scope.addedUsers.length; i < len; i++) {
        if($scope.addedUsers[i] === $scope.userSelected) {
          return;
        }
      }
      if ($scope.userSelected !== undefined && $scope.userSelected.name !== undefined) {
        $scope.addedUsers.push($scope.userSelected);
      }
    });
 }]);