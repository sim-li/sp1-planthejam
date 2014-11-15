/*
 * Softwareprojekt SoSe/WiSe 2014, Team: Comanche
 * (C)opyright Sebastian Dassé, Mat.-Nr. 791537, s50602@beuth-hochschule.de
 *
 * Module: REST service
 */
'use strict';

angular.module('restModule', ['datePickerDate', 'constants', 'invite', 'group'])
    .factory('restService', ['$http', '$q', '$log', '$filter', 'Invite', 'Group',
        function($http, $q, $log, $filter, Invite, Group) {

            var DUMMY_INVITE_LIST = false;
            var DUMMY_LOGIN = false;
            var LOG = true;

            // TODO remove as soon as generic CRUD methods work -->
            var USER_PATH = 'rest/user/';
            var INVITE_PATH = 'rest/invite/';
            var GROUP_PATH = 'rest/group/';
            // <--

            // TODO the paths should best be retrieved from a config file
            var restPaths = {
                'basePath': '/rest',
                'user': {
                    'path': '/user'
                },
                'invite': {
                    'path': '/invite',
                    'getMany': '/getInvites',
                    'save': '/save',
                    'delete': '/delete'
                },
                'group': {
                    'path': '/group',
                    'getMany': '/getGroups',
                    'save': '/save',
                    'delete': '/delete'
                }
            };

            // $http.get('restPaths.json').success(function(data, status, header, config) {
            //     restPaths = data;
            // }).error(function(data, status, header, config) {
            //     $log.error('Failed loading the REST paths configuration file restPaths.json.');
            //     $log.error(data);
            // });


            var callHTTP = function(url, data, method) {
                if (LOG)  {
                    $log.log('REST: ' + url);
                }
                var deferred = $q.defer();
                $http({
                    method: method || 'POST',
                    url: url,
                    data: data,
                    headers: (method !== 'DELETE') ? {
                        'Content-Type': 'application/json'
                    } : ''
                }).success(function(data, status, header, config) {
                    if (LOG) {
                        $log.debug(data);
                    }
                    deferred.resolve(data);
                }).error(function(data, status, header, config) {
                    // errors should always be logged
                    $log.error(data);
                    $log.error(data.stackTrace);
                    deferred.reject('REST: ' + url + ' failed. \n' + data.message);
                });
                return deferred.promise;
            };

            var login = function(user) {
                if (DUMMY_LOGIN) {
                    return callHTTP(USER_PATH + 'login', {
                        'name': 'Alice',
                        'password': 'yousnoozeyoulose'
                    });
                }
                return callHTTP(USER_PATH + 'login', {
                    'name': user.name,
                    'password': user.password
                });
            };

            var register = function(user) {
                return callHTTP(USER_PATH + 'register', {
                    'name': user.name,
                    'password': user.password,
                    'email': user.email,
                    'tel': user.tel
                });
            };

            var deleteUser = function(user) {
                return callHTTP(USER_PATH + 'delete', {
                    'oid': user.oid
                }, 'DELETE');

            };

            var updateUser = function(user) {
                return callHTTP(USER_PATH + 'update', {
                    'oid': user.oid,
                    'name': user.name,
                    'password': user.password,
                    'email': user.email,
                    'tel': user.tel
                });
            };


            /**
             * Returns the relative HTTP path for the given model and operation from the central REST path table.
             */
            var _getPath = function(model, opString) {
                var theModel = restPaths[model.getModelId()];
                return restPaths.basePath + theModel.path + theModel[opString];
            };


            /**
             * Gets a collection of objects model.
             * Note: Models need a function 'getModelId' returning the model id as a String.
             */
            var doGetMany = function(modelClass) {
                console.log('GETTING MANY ' + modelClass.getModelId); // >>>>>>>>>>>>>>>>>>>>>
                return callHTTP(_getPath(modelClass, 'getMany'));
            };

            /**
             * Saves a model.
             *
             * @param  {[type]} model the model to be saved. Note: Models need a function 'getModelId' returning the model id as a String.
             * @return {promise}      a promise, promise.then(function(model) { [do something] });
             */
            var doSave = function(model) {
                return callHTTP(_getPath(model, 'save'), model.export());
            };

            // NOTE: delete is a javascript keyword, therefore the otherwise strange 'do'-prefix naming convention
            /**
             * Deletes a model.
             * Note: Models need a function 'getModelId' returning the model id as a String.
             */
            var doDelete = function(model) {
                return callHTTP(_getPath(model, 'delete'), model.oid, 'DELETE', {
                    'Content-Type': 'application/json'
                });
            };


            var getInvites = function() {
                return DUMMY_INVITE_LIST === true ? Invite.getDummyInviteList : callHTTP(INVITE_PATH + 'getInvites');
            };

            /*
             * Update or insert an invite.
             * - @param invite ...
             */
            var saveInvite = function(invite) {
                if (LOG) {
                    $log.debug('saveInvite: ');
                    $log.debug(invite);
                    $log.debug(invite.export());
                }
                return callHTTP(INVITE_PATH + 'save', invite.export());
            };

            var deleteInvite = function(oid) {
                return callHTTP(INVITE_PATH + 'delete', oid, 'DELETE');
            };

            var getGroups = function() {

                $log.info('getGroups is untested!');

                return callHTTP(GROUP_PATH + 'getGroups');
            };

            var saveGroup = function(group) {

                $log.info('saveGroups is untested!');

                return callHTTP(GROUP_PATH + 'save', group.export());
            };

            var deleteGroup = function() {

                $log.info('deleteGroup is untested!');

                return callHTTP(GROUP_PATH + 'delete', oid, 'DELETE', {
                    'Content-Type': 'application/json'
                });
            };

            var sayHi = function() {
                $log.log('HI from rest');
            };

            return {
                login: login,
                register: register,
                deleteUser: deleteUser,
                updateUser: updateUser,
                getInvites: getInvites,
                saveInvite: saveInvite,
                deleteInvite: deleteInvite,
                getGroups: getGroups,
                saveGroup: saveGroup,
                deleteGroup: deleteGroup,
                doGetMany: doGetMany,
                doSave: doSave,
                doDelete: doDelete,
                sayHi: sayHi
            };
        }
    ]);