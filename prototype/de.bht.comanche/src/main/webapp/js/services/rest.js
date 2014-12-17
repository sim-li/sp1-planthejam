/**
 * Provides a RESTful service.
 *
 * @module restModule
 * @requires baseModel
 * @requires user
 *
 * @author Sebastian Dass&eacute;
 */
angular.module('restModule', ['baseModel', 'user'])
    /**
     * The RESTful service.
     *
     * @class restService
     */
    .factory('restService', ['$http', '$q', '$log', '$rootScope', 'Invite', 'Model', 'User',
        function($http, $q, $log, $rootScope, Invite, Model, User) {

            'use strict';

            var restService = {};

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
                'survey': {
                    'path': '/survey',
                    'get': '/get',
                    'getMany': '/getMany',
                    'save': '/save',
                    'delete': '/delete',
                },
                'invite': {
                    'path': '/invite',
                    'get': '/get',
                    'getMany': '/getInvites',
                    'save': '/save',
                    'delete': '/delete' /*not necessary?*/ /*,*/
                        // 'getSurveyInvites': '/getSurveyInvites', // TODO -> REST service on  server side
                        // 'saveSurveyInvite': '/saveSurveyInvite' //
                        // 'saveSurveyInvites': '/saveSurveyInvites' //
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
             * @param  {Object} model    the model or model class with a modelId
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
             * @param  {Object}  [params] a map of strings containing the parameters with which the url will be called
             * @param  {Object}  [data] the data to be sent to the server
             * @param  {String}  [method] the REST method (GET, POST, PUT or DELETE)
             * @return {Promise}          a promise to the data received in the response
             */
            // var callHTTP = function(url, data, params, method) {
            var callHTTP = function(cfg) {
                if (LOG) {
                    $log.debug('REST: %s <== %o', cfg.url, cfg.data);
                }
                var deferred = $q.defer();
                $http({
                    // method: method || 'POST',
                    method: cfg.method,
                    url: cfg.url,
                    params: cfg.params,
                    data: cfg.data /*,*/
                        // TODO do we still send data with the body for DELETE?
                        // headers: (method === 'DELETE') ? {
                        //     'Content-Type': 'application/json'
                        // } : ''
                }).success(function(data, status, header, config) {
                    if (LOG) {
                        $log.debug('REST: %s ==> %o', cfg.url, cfg.data);
                    }
                    deferred.resolve(data);
                }).error(function(data, status, header, config) {
                    // errors should always be logged
                    var errMsg = 'REST: ' + cfg.url + ' failed. ==> ';
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
            restService.login = function(user) {
                return callHTTP({
                    method: 'POST',
                    url: getPath(user, 'login'),
                    data: {
                        'name': user.name,
                        'password': user.password
                    }
                });
            };

            /**
             * Calls the register REST service for the specified user.
             *
             * @method register
             * @param  {Object}  user the user to be registered
             * @return {Promise}      a promise to the registered user
             */
            restService.register = function(user) {
                return callHTTP({
                    method: 'POST',
                    url: getPath(user, 'register'),
                    data: {
                        'name': user.name,
                        'password': user.password,
                        'email': user.email,
                        'tel': user.tel
                    }
                });
            };

            /**
             * Calls the delete REST service for the specified user.
             *
             * @method deleteUser
             * @param  {Object}  user the user to be deleted
             */
            restService.deleteUser = function(user) { // TODO use generic doDelete if possible
                return callHTTP({
                    method: 'DELETE',
                    url: getPath(user, 'delete'),
                    data: {
                        'oid': user.oid
                    }
                });
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
            restService.updateUser = function(user) {
                return callHTTP({
                    method: 'POST',
                    path: getPath(user, 'update'),
                    data: {
                        'oid': user.oid,
                        'name': user.name,
                        'password': user.password,
                        'email': user.email,
                        'tel': user.tel
                    }
                });
            };

            /**
             * Calls the logout REST service.
             *
             * @method logout
             */
            restService.logout = function() {
                return callHTTP({
                    method: 'POST',
                    url: getPath(User, 'logout')
                });
            };

            // // TODO
            // restService.getSurveyInvites = function(inviteOid) {
            //     return callHTTP(getPath(Invite, 'getSurveyInvites'), inviteOid);
            // };

            // // TODO
            // restService.saveSurveyInvite = function(invite, inviteToSave) {
            //     return callHTTP(getPath(invite, 'saveSurveyInvite'), invite.oid, inviteToSave);
            // };

            // TODO
            // restService.saveSurveyInvites = function(invite, invitesToSave) {
            //     return callHTTP(getPath(invite, 'saveSurveyInvites'), invite.oid, invitesToSave);
            // };

            /**
             * Gets a collection of objects of the specified model class by calling the REST service.
             *
             * @method doGetMany
             * @param  {Object} ModelClass the model class with a modelId
             * @return {Promise}           a promise for a collection of objects of the specified model type
             */
            restService.doGetMany = function(ModelClass) {
                return callHTTP({
                    method: 'GET',
                    url: getPath(ModelClass, 'getMany')
                });
            };

            /**
             * Gets the object specified by model class and oid by calling the REST service.
             *
             * @method doGet
             * @param  {Object} ModelClass the model class with a modelId
             * @param  {Number} oid        the object ID
             * @return {Promise}           a promise for the object
             */
            restService.doGet = function(ModelClass, oid) {
                return callHTTP({
                    method: 'GET',
                    url: getPath(ModelClass, 'get'),
                    params: {
                        'oid': oid
                    }
                });
            };

            /**
             * Saves a model by calling the REST service.
             *
             * @method doSave
             * @param  {Object} model the model with a modelId and an export method
             * @return {Promise}      a promise for the saved object of the specified model
             */
            restService.doSave = function(model) {
                return callHTTP({
                    method: 'POST',
                    url: getPath(model, 'save'),
                    data: model.doExport()
                });
            };

            // NOTE: delete is a javascript keyword, therefore the otherwise strange 'do'-prefix naming convention
            /**
             * Deletes a model by calling the REST service.
             *
             * @method doDelete
             * @param  {Object} model the model with a modelId
             */
            restService.doDelete = function(model) {
                return callHTTP({
                    method: 'DELETE',
                    url: getPath(model, 'delete'),
                    params: {
                        oid: model.oid
                    }
                });
            };


            // return {
            //     login: login,
            //     register: register,
            //     deleteUser: deleteUser,
            //     updateUser: updateUser,
            //     // getUser: getUser,
            //     logout: logout,
            //     doGetMany: doGetMany,
            //     doGet: doGet,
            //     doSave: doSave,
            //     doDelete: doDelete
            // };

            return restService;
        }
    ]);