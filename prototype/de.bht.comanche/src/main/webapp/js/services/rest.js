/**
 * Provides a RESTful service.
 *
 * @module rest
 * @requires models
 *
 * @author Sebastian Dass&eacute;
 */
angular.module('rest', ['models'])
    /**
     * The RESTful service.
     *
     * @class restService
     */
    .factory('restService', ['$http', '$q', '$log', '$rootScope', 'Invite', 'Model', 'Survey', /*'TimePeriod', */ 'User',
        function($http, $q, $log, $rootScope, Invite, Model, Survey, /*TimePeriod, */ User) {

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
                'basePath': '/rest/',
                'user': {
                    'path': 'user/',
                    'login': 'login',
                    'register': 'register',
                    'delete': 'delete',
                    'get': 'get',
                    'update': 'update',
                    'getAll': 'getAllUsers',
                    'messages': 'messages',
                    'logout': 'logout'
                },
                'survey': {
                    'path': 'surveys/',
                    'invites': 'invites',
                    'notifyParticipants': 'notifyParticipants'
                },
                'invite': {
                    'path': 'invites/'
                },
                'group': {
                    'path': 'groups/'
                },
                'member': {
                    'path': 'members/'
                }
                // ,
                // 'timePeriod': {
                //     'path': '/timeperiods',
                // 'getMany': '/gettimeperiods'
                // ,
                // 'save': '/save',
                // 'delete': '/delete'
                // }
            };

            //--> TODO the paths config should best be retrieved from a config file
            // $http.get('restPaths.json').success(function(data, status, header, config) {
            //     restPaths = data;
            // }).error(function(data, status, header, config) {
            //     $log.log('Failed loading the REST paths configuration file restPaths.json.');
            //     $log.log(data);
            // });

            /**
             * Returns the relative URL for the given model from the central REST path table.
             * An oid or an operation or both may be specified. For example: `getUrlFor(Survey, 123, invites)` will
             * result in the url: `/rest/surveys/123/invites`.
             *
             * @method getUrlFor
             * @private
             * @param  {Object} model    the model or model class with a modelId
             * @param  {Number} [oid]    the oid of the model
             * @param  {String} [opString] a String that denotes the desired operation
             * @return {String}          the relative HTTP path
             */
            var getUrlFor = function(model, oid, opString) {
                var modelId = model.modelId || model.prototype.modelId;
                var theModel = restPaths[modelId];
                return restPaths.basePath + theModel.path + (oid || '') + ((oid && opString) ? '/' : '') + (theModel[opString] || '');
            };

            /**
             * Calls the HTTP service at the specified URL with the specified data using the specified method.
             *
             * @method callHttp
             * @private
             * @param  {String}  method the REST method (GET, POST, PUT or DELETE)
             * @param  {String}  url the URL (relative to the root path)
             * @param  {Object}  [data] the data to be sent to the server
             * @return {Promise}          a promise to the data received in the response
             */
            var callHttp = function(method) {
                return function(url, data) {
                    if (LOG) {
                        $log.debug('%s %s <== %o', method, url, data);
                    }
                    var deferred = $q.defer();
                    $http({
                        method: method,
                        url: url,
                        data: data /*,*/
                            // headers: {
                            //     'Content-Type': 'application/json'
                            // } : ''
                    }).success(function(data, status, header, config) {
                        if (LOG) {
                            if (url == '/rest/surveys/1/invites') {
                                console.log('calling ', url)
                                console.log('retrieving ', data)
                                console.log('retrieving ', status)
                            }
                            $log.debug('%s %s ==> %o', method, url, data);
                        }
                        deferred.resolve(data);
                    }).error(function(data, status, header, config) {
                        // errors should always be logged
                        var errMsg = method + ' ' + url + ' failed. ==> ';
                        $rootScope.warnings = errMsg + data.message;
                        $log.log(errMsg);
                        $log.log(data.stackTrace);
                        deferred.reject(errMsg);
                    });
                    return deferred.promise;
                };
            };

            /**
             * Does a login for the specified user.
             *
             * @method login
             * @param  {Object}  user the user to be logged in
             * @return {Promise}      a promise to the logged-in user
             */
            restService.login = function(user) {
                var url = getUrlFor(user, null, 'login'),
                    data = {
                        'name': user.name,
                        'password': user.password
                    };
                return callHttp('POST')(url, data);
            };

            /**
             * Registers the specified user.
             *
             * @method register
             * @param  {Object}  user the user to be registered
             * @return {Promise}      a promise to the registered user
             */
            restService.register = function(user) {
                var url = getUrlFor(user, null, 'register'),
                    data = {
                        'name': user.name,
                        'password': user.password,
                        'email': user.email,
                        'tel': user.tel
                    };
                return callHttp('POST')(url, data);
            };

            /**
             * Deletes the specified user.
             *
             * @method deleteUser
             * @param  {Object}  user the user to be deleted
             */
            restService.deleteUser = function(user) { // TODO use generic doDelete if possible
                var url = getUrlFor(user, null, 'delete');
                return callHttp('DELETE')(url);
            };

            // var getUser = function(user) {
            //     return callHttp('GET')(getUrlFor(user, null, 'get'));
            // };

            /**
             * Updates the specified user.
             *
             * @method updateUser
             * @param  {Object}  user the user to be updated
             * @return {Promise}      a promise to the updated user
             */
            restService.updateUser = function(user) { // TODO use generic doUpdate if possible
                var url = getUrlFor(user, null, 'update'),
                    // data = {
                    //     'oid': user.oid,
                    //     'name': user.name,
                    //     'password': user.password,
                    //     'email': user.email,
                    //     'tel': user.tel
                    // };
                    data = user.doExport();
                return callHttp('POST')(url, data);
            };

            /**
             * Does a logout for the user that is currently logged-in.
             *
             * @method logout
             */
            restService.logout = function() {
                var url = getUrlFor(User, null, 'logout');
                return callHttp('POST')(url);
            };

            /**
             * Gets all users.
             *
             * @method getAllUsers
             * @return {Promise} a promise to an array of all users
             */
            restService.getAllUsers = function() {
                var url = getUrlFor(User, null, 'getAll');
                return callHttp('GET')(url);
            };

            restService.getSurveyInvites = function(oid) {
                console.log('getting many for ', oid)
                var url = getUrlFor(Survey, oid, 'invites');
                return callHttp('GET')(url);
            };

            restService.getMessages = function() {
                var url = getUrlFor(User, null, 'messages');
                $log.debug(url)
                return callHttp('GET')(url);
            };

            restService.notifyParticipants = function(oid) {
                var url = getUrlFor(Survey, oid, 'notifyParticipants');
                $log.debug(url)
                return callHttp('POST')(url);
            };

            /**
             * Gets a collection of objects of the specified model class by calling the REST service.
             *
             * @method doGetMany
             * @param  {Object} ModelClass the model class with a modelId
             * @return {Promise}           a promise for a collection of objects of the specified model type
             */
            restService.doGetMany = function(ModelClass) {
                var url = getUrlFor(ModelClass);
                return callHttp('GET')(url);
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
                var url = getUrlFor(ModelClass, oid);
                return callHttp('GET')(url);
            };

            /**
             * Saves a model by calling the REST service.
             *
             * @method doSave
             * @param  {Object} model the model with a modelId and an export method
             * @return {Promise}      a promise for the saved object of the specified model
             */
            restService.doSave = function(model) {
                var method = (model.oid) ? 'PUT' : 'POST',
                    url = getUrlFor(model, model.oid),
                    data = model.doExport();
                return callHttp(method)(url, data);
            };

            /**
             * Deletes a model by calling the REST service.
             *
             * @method doDelete
             * @param  {Object} model the model with a modelId
             */
            restService.doDelete = function(model) {
                var url = getUrlFor(model, model.oid);
                return callHttp('DELETE')(url);
            };

            return restService;
        }
    ]);