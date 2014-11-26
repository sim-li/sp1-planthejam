/*
 * Softwareprojekt SoSe/WiSe 2014, Team: Comanche
 * (C)opyright Sebastian Dassé, Mat.-Nr. 791537, s50602@beuth-hochschule.de
 *
 * Module: REST service
 */
'use strict';

angular.module('restModule', ['datePickerDate', 'constants', 'invite', 'group'])
    .factory('restService', ['$http', '$q', '$log', '$rootScope', 'User',
        function($http, $q, $log, $rootScope, User) {

            var LOG = true;

            // TODO the paths should best be retrieved from a config file
            var restPaths = {
                'basePath': '/rest',
                'user': {
                    'path': '/user',
                    'login': '/login',
                    'register': '/register',
                    'delete': '/deleteUser',
                    'update': '/updateUser',
                    'getMany': '/getAllUsers',
                    'logout': '/logout'
                },
                'invite': {
                    'path': '/invite',
                    'getMany': '/getInvites',
                    'get': '/get',
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
            //     $log.log('Failed loading the REST paths configuration file restPaths.json.');
            //     $log.log(data);
            // });

            /**
             * Returns the relative HTTP path for the given model and operation from the central REST path table.
             * @param  {object} model    needs to have a method called getModelId() that returns the model ID as a String
             * @param  {String} opString a String that denotes the desired operation
             * @return {String}          the relative HTTP path
             */
            var getPath = function(model, opString) {
                // var getModelId = model.getModelId || new model().getModelId;
                var getModelId = model.getModelId || model.prototype.getModelId;
                var theModel = restPaths[getModelId()];
                return restPaths.basePath + theModel.path + theModel[opString];
            };

            /**
             * Calls the HTTP service at the specified URL with the specified data using the specified method.
             * @param  {String} url    the URL
             * @param  {object} data   the data to be sent to the server
             * @param  {String} method the REST method (GET, POST, PUT, DELETE)
             * @return {promise}       [description]
             */
            var callHTTP = function(url, data, method) {
                // if (LOG)  {
                //     $log.log('REST: ' + url);
                // }
                var deferred = $q.defer();
                $http({
                    method: method || 'POST',
                    url: url,
                    data: data,
                    headers: (method === 'DELETE') ? {
                        'Content-Type': 'application/json'
                    } : ''
                }).success(function(data, status, header, config) {
                    if (LOG) {
                        $log.debug('REST: ' + url + ' ==>');
                        $log.debug(data);
                    }
                    deferred.resolve(data);
                }).error(function(data, status, header, config) {
                    // errors should always be logged
                    var errMsg = 'REST: ' + url + ' failed. ==> ';
                    $rootScope.warnings = errMsg + data.message;
                    $log.log(errMsg);
                    $log.log(data.stackTrace);
                    // deferred.reject(errMsg);
                });
                return deferred.promise;
            };

            var login = function(user) {
                return callHTTP(getPath(user, 'login'), {
                    'name': user.name,
                    'password': user.password
                });
            };

            var register = function(user) {
                return callHTTP(getPath(user, 'register'), {
                    'name': user.name,
                    'password': user.password,
                    'email': user.email,
                    'tel': user.tel
                });
            };

            var deleteUser = function(user) {
                return callHTTP(getPath(user, 'delete'), {
                    'oid': user.oid
                }, 'DELETE');
            };

            var updateUser = function(user) {
                return callHTTP(getPath(user, 'update'), {
                    'oid': user.oid,
                    'name': user.name,
                    'password': user.password,
                    'email': user.email,
                    'tel': user.tel
                });
            };

            var logout = function() {
                return callHTTP(getPath(User, 'logout'));
            }

            /**
             * Gets a collection of objects of the specified model.
             * @param  {object} modelClass needs to have a method called getModelId() that returns the model ID as a String
             * @return {promise}           a promise for a collection of objects of the specified model type
             */
            var doGetMany = function(modelClass) {
                return callHTTP(getPath(modelClass, 'getMany'));
            };

            /**
             * Gets the specified object specified by model class and oid.
             * @param  {object} modelClass needs to have a method called getModelId() that returns the model ID as a String
             * @param  {number} oid        the object id
             * @return {promise}           a promise for the object
             */
            var doGet = function(modelClass, oid) {
                return callHTTP(getPath(modelClass, 'get'), oid);
            };

            /**
             * Saves a model.
             *
             * @param  {object} model needs to have a method called getModelId() that returns the model ID as a String
             * @return {promise}      a promise for the saved object of the specified model
             */
            var doSave = function(model) {
                return callHTTP(getPath(model, 'save'), model.export());
            };

            // NOTE: delete is a javascript keyword, therefore the otherwise strange 'do'-prefix naming convention
            /**
             * Deletes a model.
             * @param  {object} model needs to have a method called getModelId() that returns the model ID as a String
             * @return {promise}      an empty (???) promise
             */
            var doDelete = function(model) {
                return callHTTP(getPath(model, 'delete'), model.oid, 'DELETE', {
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
                logout: logout,
                doGetMany: doGetMany,
                doGet: doGet,
                doSave: doSave,
                doDelete: doDelete,
                sayHi: sayHi
            };

        }
    ]);