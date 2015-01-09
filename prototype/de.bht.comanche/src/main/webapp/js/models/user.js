/**
 * Provides a model for users.
 *
 * @module user
 *
 * @author Sebastian Dass&eacute;
 */
angular.module('user', [])
    .factory('User', function() {

        'use strict';

        /**
         * Represents a user.
         *
         * @class User
         * @constructor
         * @param {Object} [config={}] an optional configuration object
         * @param {Number} [config.oid=''] the object id of the user
         * @param {String} [config.name=''] the name of the user
         * @param {String} [config.password=''] the password of the user
         * @param {String} [config.email=''] the email address of the user
         * @param {String} [config.tel=''] the telephone number of the user
         */
        var User = function(config) {
            if (!(this instanceof User)) {
                return new User(config);
            }
            config = config || {};
            this.oid = config.oid || '';
            this.name = config.name || '';
            this.password = config.password || '';
            this.email = config.email || '';
            this.tel = config.tel || '';
            this.iconurl = config.iconurl || '';
            // this.invites = [];
            // this.groups = [];
        };

        // User.prototype = new Model();

        /**
         * This model's unique id.
         *
         * @property modelId
         * @type {String}
         */
        User.prototype.modelId = 'user';

        /**
         * Exports the user by removing any client side attributes, that the server can not handle.
         *
         * @method doExport
         * @return {Object} the exported user
         */
        User.prototype.doExport = function() {
            return {
                'oid': this.oid,
                'name': this.name,
                'password': this.password,
                'email': this.email,
                'tel': this.tel
                    /*,'invites': this.invites,
                    'groups': this.groups*/
            };
        };

        return (User);
    });