angular.module('myApp')
.controller('inviteCtrl', ['$scope', function($scope) {

    $scope.surveyTitle = 'Lets have a beer, guys';

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
        {
            name: 'Rockettes',
            members: [
                {name:'Blackjack', email:'bj@gmail.com'},
                {name:'Bob',      email:'bob@gmail.com'},
                {name:'Sebastian', email:'sb@gmail.com'}
            ]
        }, {
            name: 'Ralf Laurens',
            members: [
                {name:'Marie',     email:'marie@gmail.com'},
                {name:'Sarah',     email:'sr@gmail.com'},
                {name:'Simon',     email:'sm@gmail.com'},
            ]
        },
        {name: 'Sam Fillers'},
        {name: 'Dam Killers'},
        {name: 'Masaki Haki Kaki'}
    ]

    $scope.addedUsers = []
    $scope.isCollapsed = true;
    $scope.userSelected = undefined;
    
    $scope.switchDetailPanel = function() {
        if ($scope.addedUsers.length <= 0) {
            $scope.isCollapsed = true;
            return;
        }
        $scope.isCollapsed = !$scope.isCollapsed;
    }

    $scope.removeMember = function(index) {
        $scope.addedUsers.splice(index, 1);
        if ($scope.addedUsers.length <= 0) {
            $scope.isCollapsed = true;
        }
    }

    $scope.selectGroup = function(groupName) {
        if (groupName === undefined || $scope.groups === undefined) {
            return;
        }
        var group = findGroup(groupName);
        if (group === -1) { 
            return;
        }
        $scope.addedUsers = [];
        $scope.addedUsers = group.members;
    }

    var findGroup = function(name) {
        for (var i = 0, len = $scope.groups.length; i < len; i++) {
            if ($scope.groups[i].name === name) {
                return $scope.groups[i];
            }
        }
        return -1;
    };

    $scope.$watch('userSelected', function() {
        if ($scope.userSelected === undefined || $scope.userSelected.name === undefined) {
            return;
        }
        for (var i = 0, len = $scope.addedUsers.length; i < len; i++) {
            if($scope.addedUsers[i] === $scope.userSelected) {
                $scope.isCollapsed = false;
                return;
            }
        }
        $scope.addedUsers.push($scope.userSelected);
        $scope.isCollapsed = false;
     });
}]);