// TODO: Merge AddMember ETC with direct Groups
// Bug: Multiple Rename fails unless select happens
// Simplify, Patterns, Comment.

'use strict';

angular.module('myApp')
    .controller('inviteCtrl', ['$scope', '$log', '$location', 'restService', 'Invite', 'Survey',
        'Group', 'Type', 'TimeUnit', 'invites', 'groups', 'selectedInvite', 'users', 'stateSwitcher', 'arrays',

        function($scope, $log, $location, restService, Invite, Survey, Group,
            Type, TimeUnit, invites, groups, selectedInvite, users, uiHelpers, arrays) {

            /**
             * =============================GROUP WIDGET================================================
    //      * @type {Boolean}
    //      */

            new GroupCtrl().bindToModel(groups);

            // $scope.saveGroups = function() {
            //     for (var i = 0; i < $scope.groups.length; i++) {
            //         restService.doSave($scope.groups[i]);
            //     }
            //     $location.path('/invite');
            // };



            // var changeGroupName = function(oldName, newName) {
            //     var index = find($scope.groups, 'name', oldName);
            //     if (index === -1) {
            //         return index;
            //     }
            //     $scope.groups[index].name = newName;
            //     return newName;
            // };

            // var getGroup = function(name) {
            //     var index = find($scope.groups, 'name', name);
            //     if (index === -1) {
            //         return index;
            //     }
            //     return $scope.groups[index];
            // };


            /**
             * ========================DATA MAINTANENCE/REST=========================================================
             */
            // $scope.invites = Invite.importMany(invites);
            // $scope.groups = Group.importMany(groups);
            // $scope.users = users; // importMany?
            // $scope.selectedInvite = selectedInvite ? new Invite(selectedInvite) : new Invite({
            //     'survey': new Survey({
            //         'name': 'Your survey',
            //         'description': 'Say what it is all about',
            //         'deadline': new Date()
            //     })
            // });
            //
            //
            //
            //
            // $scope.saveInvite = function() {
            //     restService.doSave($scope.selectedInvite)
            //         .then(function(success) {
            //             $location.path('/cockpit');
            //         } /*, function(error) { $log.log(error); }*/ );
            // };
            // // TODO rest service to save many groups
            // $scope.saveGroups = function() {
            //     for (var i = 0; i < $scope.groups.length; i++) {
            //         restService.doSave($scope.groups[i]);
            //     }
            //     $location.path('/invite');
            // };



            // /**
            //  * Other widgets
            //  * @type {Number}
            //  */
            // $scope.duration = 1;
            // $scope.dt = new Date();

            // $scope.showLiveButton = true;
            // $scope.repeatedly = false;

            // $scope.toOpened = false;
            // $scope.fromOpened = false;

            // $scope.clear = function() {
            //     $scope.dt = null;
            // };
            // // Disable weekend selection
            // $scope.disabled = function(date, mode) {
            //     return (mode === 'day' && (date.getDay() === 0 || date.getDay() === 6));
            // };

            // $scope.toggleMin = function() {
            //     $scope.minDate = $scope.minDate ? null : new Date();
            // };


            // $scope.dateTimeNow = function() {
            //     $scope.date = new Date();
            // };


            // $scope.toggleMin();

        }
    ]);