/**
 * Provides a RESTful service.
 *
 * @module restModule
 * @requires baseModel
 * @requires user
 *
 * TODO change docs concerning model and model ID !!!
 *
 * @author Sebastian Dass&eacute;
 */
angular.module('restModule', ['baseModel', 'user'])
    /**
     * The RESTful service.
     *
     * @class restService
     */
    .factory('restService', ['$http', '$q', '$log', '$rootScope', 'Model', 'User',
        function($http, $q, $log, $rootScope, Model, User) {

            'use strict';

            // turns the logging on/off
            var LOG = true;

            /**
             * A configuration map for the server routes.
             *
             * @property restPaths
             * @type {Object}
             */
            var restPaths = {
                'basePath': '/rest',
                'user': {
                    'path': '/user',
                    'login': '/login',
                    'register': '/register',
                    'delete': '/delete',
                    'get': '/get',
                    'update': '/update',
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
                },
                'member': {
                    'path': '/member',
                    // 'get': '/get',
                    // 'save': '/save',
                    'delete': '/delete'
                }
            };

            //--> TODO the paths config should best be retrieved from a config file
            // $http.get('restPaths.json').success(function(data, status, header, config) {
            //     restPaths = data;
            // }).error(function(data, status, header, config) {
            //     $log.log('Failed loading the REST paths configuration file restPaths.json.');
            //     $log.log(data);
            // });

            /**
             * Returns the relative HTTP path for the given model and operation from the central REST path table.
             *
             * @method getPath
             * @private
             * @param  {Object} model    the model, which needs to have a method called getModelId(), that returns the
             *                               model ID as a String
             * @param  {String} opString a String that denotes the desired operation
             * @return {String}          the relative HTTP path
             */
            var getPath = function(model, opString) {
                var modelId = model.modelId || model.prototype.modelId;
                var theModel = restPaths[modelId];
                return restPaths.basePath + theModel.path + theModel[opString];
            };

            /**
             * Calls the HTTP service at the specified URL with the specified data using the specified method.
             *
             * @method callHTTP
             * @private
             * @param  {String}  url the URL (relative to the root path)
             * @param  {Object}  [data] the data to be sent to the server
             * @param  {String}  [method] the REST method (GET, POST, PUT or DELETE)
             * @return {Promise}          a promise to the data received in the response
             */
            var callHTTP = function(url, data, method) {
                if (LOG) {
                    $log.debug('REST: %s <== %o', url, data);
                }
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
                        $log.debug('REST: %s ==> %o', url, data);
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

            /**
             * Calls the login REST service for the specified user.
             *
             * @method login
             * @param  {Object}  user the user to be logged in
             * @return {Promise}      a promise to the logged-in user
             */
            var login = function(user) {
                return callHTTP(getPath(user, 'login'), {
                    'name': user.name,
                    'password': user.password
                });
            };

            /**
             * Calls the register REST service for the specified user.
             *
             * @method register
             * @param  {Object}  user the user to be registered
             * @return {Promise}      a promise to the registered user
             */
            var register = function(user) {
                return callHTTP(getPath(user, 'register'), {
                    'name': user.name,
                    'password': user.password,
                    'email': user.email,
                    'tel': user.tel
                });
            };

            /**
             * Calls the delete REST service for the specified user.
             *
             * @method deleteUser
             * @param  {Object}  user the user to be deleted
             */
            var deleteUser = function(user) {
                return callHTTP(getPath(user, 'delete'), { // TODO use generic doDelete if possible
                    'oid': user.oid
                }, 'DELETE');
            };

            // var getUser = function(user) {
            //     return callHTTP(getPath(user, 'get'));
            // };

            /**
             * Calls the update REST service for the specified user.
             *
             * @method updateUser
             * @param  {Object}  user the user to be updated
             * @return {Promise}      a promise to the updated user
             */
            var updateUser = function(user) {
                return callHTTP(getPath(user, 'update'), {
                    'oid': user.oid,
                    'name': user.name,
                    'password': user.password,
                    'email': user.email,
                    'tel': user.tel
                });
            };

            /**
             * Calls the logout REST service.
             *
             * @method logout
             */
            var logout = function() {
                return callHTTP(getPath(User, 'logout'));
            };

            /**
             * Gets a collection of objects of the specified model class by calling the REST service.
             *
             * @method doGetMany
             * @param  {Object} ModelClass the model class, which needs to have a method called getModelId(), that
             *                                 returns the model ID as a String
             * @return {Promise}           a promise for a collection of objects of the specified model type
             */
            var doGetMany = function(ModelClass) {
                return callHTTP(getPath(ModelClass, 'getMany'));
            };

            /**
             * Gets the object specified by model class and oid by calling the REST service.
             *
             * @method doGet
             * @param  {Object} ModelClass the model class, which needs to have a method called getModelId(), that
             *                                 returns the model ID as a String
             * @param  {Number} oid        the object ID
             * @return {Promise}           a promise for the object
             */
            var doGet = function(ModelClass, oid) {
                return callHTTP(getPath(ModelClass, 'get'), oid);
            };

            /**
             * Saves a model by calling the REST service.
             *
             * @method doSave
             * @param  {Object} model the model, which needs to have a method called getModelId(), that returns the
             *                            model ID as a String
             * @return {Promise}      a promise for the saved object of the specified model
             */
            var doSave = function(model) {
                return callHTTP(getPath(model, 'save'), model.export());
            };

            // NOTE: delete is a javascript keyword, therefore the otherwise strange 'do'-prefix naming convention
            /**
             * Deletes a model by calling the REST service.
             *
             * @method doDelete
             * @param  {Object} model the model, which needs to have a method called getModelId(), that returns the
             *                            model ID as a String
             */
            var doDelete = function(model) {
                return callHTTP(getPath(model, 'delete'), model.oid, 'DELETE');
            };

            return {
                login: login,
                register: register,
                deleteUser: deleteUser,
                updateUser: updateUser,
                // getUser: getUser,
                logout: logout,
                doGetMany: doGetMany,
                doGet: doGet,
                doSave: doSave,
                doDelete: doDelete
            };

        }
    ]);