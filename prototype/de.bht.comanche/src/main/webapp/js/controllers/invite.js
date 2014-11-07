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