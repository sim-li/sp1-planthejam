<<<<<<< HEAD
/*
 * Softwareprojekt SoSe/WiSe 2014, Team: Comanche
 * (C)opyright Sebastian Dassé, Mat.-Nr. 791537, s50602@beuth-hochschule.de
 * (C)opyright Duc Tung Tong, Mat.-Nr. 798029, s51488@beuth-hochschule.de
 * Module: my app -- the main module
 */

=======
>>>>>>> 183b80ed96a2ddb6fd64cec602a207d6eb9184e3
'use strict';

/**
 * Provides the webapp. Also has:
 * - routes
 * - directives
 * ...
 *
 * @module myApp
 * @main
 *
 * @author Sebastian Dass&eacute;
 */
angular.module('myApp', ['ui.bootstrap', 'xeditable', 'ngRoute', 'datePickerDate', 'survey', 'constants', 'restModule', 'typeAugmentations', 'invite', 'group', 'user'])
    .factory('util', function() {

        // TODO is it still in use?

        /**
         * this method removes an element from array
         * @param  {object} element An element from array
         * @param  {array} Array
         * @return {array}          Array after removing element
         */
        var removeElementFrom = function(element, array) {
            var index = array.indexOf(element);
            if (index > -1) {
                array.splice(index, 1);
            }
        };
        return {
            removeElementFrom: removeElementFrom
        };
    })
    .config(function($routeProvider /*, $locationProvider*/ ) {
        $routeProvider
            .when('/', {
                templateUrl: 'pages/login.html',
                controller: 'loginCtrl'
            })
            .when('/register', {
                templateUrl: 'pages/register.html',
                controller: 'loginCtrl'
            })
            .when('/cockpit', {
                templateUrl: 'pages/cockpit.html',
                controller: 'cockpitCtrl',
                resolve: {
                    /**
                     *  get all invite promises
                     * @param  {rest} restService   rest service
                     * @param  {invite} Invite      invite model
                     * @return {promise}
                     */
                    invitesPromise: function(restService, Invite) {
                        return restService.doGetMany(Invite);
                    }
                }
            })
            .when('/invite/:inviteOid?', {
                templateUrl: 'pages/invite.html',
                controller: 'inviteCtrl',
                resolve: {
                    /**
                     * get the invite promise with oid
                     * @param  {} $route      [description]
                     * @param  {rest} restService   rest service
                     * @param  {invite} Invite      invite model
                     * @return {promise}            a promise for the invite object
                     */
                    selectedInvitePromise: function($route, restService, Invite) {
                        var inviteOid = $route.current.params.inviteOid;
                        if (typeof inviteOid === 'undefined') {
                            return '';
                        }
                        return restService.doGet(Invite, inviteOid);
                    },
                    // TODO maybe not necessary to get all invites for this route?
                    /**
                     *  get all invite promises
                     * @param  {rest} restService   rest service
                     * @param  {invite} Invite      invite model
                     * @return {promise}
                     */
                    invitesPromise: function(restService, Invite) {
                        return restService.doGetMany(Invite);
                    },
                    /**
                     * get all group promises
                     * @param  {rest} restService    rest service
                     * @param  {group} Group         group model
                     * @return {promise}
                     */
                    groupsPromise: function(restService, Group) {
                        return restService.doGetMany(Group);
                    },
                    /**
                     * get all user promises
                     * @param  {rest} restService   rest service
                     * @param  {user} User          user model
                     * @return {promise}
                     */
                    usersPromise: function(restService, User) {
                        return restService.doGetMany(User);
                    }
                }
            })
            .when('/account', {
                templateUrl: 'pages/editUser.html',
                controller: 'editUserCtrl',
                resolve: {
                    /**
                     * get one user promise
                     * @param  {rest} restService   rest service
                     * @param  {user} User          user model
                     * @return {promise}
                     */
                    userPromise: function(restService, User) {
                        return restService.doGet(User);
                    }
                }
            });
        // $locationProvider.html5Mode(true); // for prettier urls
    })
    .directive('ptjMenu', function() {
        return {
            restrict: 'E',
            templateUrl: 'partials/menu.html',
            controller: 'menuCtrl'
        };
    })
    .directive('ptjGroups', function() {
        return {
            restrict: 'E',
            templateUrl: 'partials/invite/groups.html'
        };
    })
    .directive('ptjSearch', function() {
        return {
            restrict: 'E',
            templateUrl: 'partials/search.html'
        };
    })
    .directive('ptjSurveyDetails', function() {
        return {
            restrict: 'E',
            templateUrl: 'partials/invite/surveydetails.html'
        };
    })
    .directive('ptjSurveySelect', function() {
        return {
            restrict: 'E',
            templateUrl: 'partials/surveyselect.html'
        };
    })
    .run(function(editableOptions, typeAugmentations) {
        editableOptions.theme = 'bs3'; // bootstrap3 theme. Can be also 'bs2', 'default'
        typeAugmentations();
    });