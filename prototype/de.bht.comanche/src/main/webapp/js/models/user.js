/**
 * @module models
 *
 * @author Sebastian Dass&eacute;
 */
angular.module('models')
    .factory('User', ['Model', 'TimePeriod', function(Model, TimePeriod) {

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
         * @param {String} [config.tel=''] the telephone number of the user
         * @param {String} [config.email=''] the email address of the user
         * @param {String} [config.iconurl=''] the icon URL of the user
         *                                     @param {Array}  [config.messages=[]] the messages of the user
         * @param {Array}  [config.generalAvailability=[]] the time periods when the user is generally available
         */
        var User = function(config) {
            if (!(this instanceof User)) {
                return new User(config);
            }
            config = config || {};
            this.oid = config.oid || '';
            this.name = config.name || '';
            this.password = config.password || '';
            this.tel = config.tel || '';
            this.email = config.email || '';
            this.iconurl = config.iconurl || '';
            // this.messages = config.messages || [];
            this.generalAvailability = Model.importMany(TimePeriod, config.generalAvailability);
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
                'tel': this.tel,
                'email': this.email,
                'iconurl': this.iconurl,
                // 'messages': this.messages,
                'generalAvailability': TimePeriod.exportMany(this.generalAvailability)
            };
        };

        return (User);
    }]);