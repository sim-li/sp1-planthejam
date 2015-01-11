/**
 * Provides a model for groups.
 *
 * @module group
 * @requires baseModel
 * @requires member
 *
 * @author Sebastian Dass&eacute;
 */
angular.module('group', ['baseModel', 'member'])
    .factory('Group', ['Model', 'Member', function(Model, Member) {

        'use strict';

        /**
         * Represents a group of users.
         *
         * @class Group
         * @constructor
         * @param {Object} [config={}] an optional configuration object
         * @param {Number} [config.oid=''] the object id of the group
         * @param {String} [config.name='Your new group'] the name of the group
         * @param {Array}  [config.members=[]] the members of the group
         */
        var Group = function(config) {
            if (!(this instanceof Group)) {
                return new Group(config);
            }
            config = config || {};
            this.oid = config.oid || '';
            this.name = config.name || 'Your new group';
            // this.members = config.members || []; // e.g.: [{oid: 1, name: 'Alice'}, {oid: 2, name: 'Bob'}, {oid: 3, name: 'Carla'}]
            this.members = config.members ? Model.importMany(Member, config.members) : [];
            this.iconurl = config.iconurl || '';
        };

        // Group.prototype = new Model();

        /**
         * This model's unique id.
         *
         * @property modelId
         * @type {String}
         */
        Group.prototype.modelId = 'group';

        /**
         * Exports the group by removing any client side attributes, that the server can not handle.
         *
         * @method doExport
         * @return {Object} the exported group
         */
        Group.prototype.doExport = function() {
            return {
                'oid': this.oid,
                'name': this.name,
                // 'members': this.members
                'members': Member.exportMany(this.members)
            };
        };

        Group.create

        return (Group);
    }]);