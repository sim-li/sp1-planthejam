/**
 * Provides a model for groups.
 *
 * @module group
 * @requires member
 *
 * @author Sebastian Dass&eacute;
 */
angular.module('group', ['member'])
    .factory('Group', ['Member', function(Member) {

        'use strict';

        /**
         * Represents a group of users.
         *
         * @class Group
         * @constructor
         * @param {Object} [config={}] an optional configuration object
         * @param {Number} [config.oid=''] the object id of the group
         * @param {String} [config.name=''] the name of the group
         * @param {Array}  [config.members=[]] the members of the group
         */
        var Group = function(config) {
            if (!(this instanceof Group)) {
                return new Group(config);
            }
            config = config || {};
            this.oid = config.oid || '';
            this.name = config.name || '';
            // this.members = config.members || []; // e.g.: [{oid: 1, name: 'Alice'}, {oid: 2, name: 'Bob'}, {oid: 3, name: 'Carla'}]
            this.members = config.members ? Member.importMany(config.members) : [];
        };

        /**
         * Returns this model's unique id.
         *
         * @method getModelId
         * @return {String} the model's id
         */
        Group.prototype.getModelId = function() {
            return 'group';
        };

        /**
         * Imports an array of raw groups by converting them to the group model.
         *
         * @method importMany
         * @static
         * @param  {Array}  rawGroups the groups to be imported
         * @return {Array}            the imported groups
         */
        Group.importMany = function(rawGroups) {
            if (!rawGroups) {
                return rawGroups;
            }
            var groups = [];
            for (var i = 0; i < rawGroups.length; i++) {
                groups.push(new Group(rawGroups[i]));
            }
            // console.log("imported " + rawGroups.length)
            return groups;
        };

        /**
         * Exports the group by removing any client side attributes, that the server can not handle.
         *
         * @method export
         * @return {Object} the exported group
         */
        Group.prototype.export = function() {
            return {
                'oid': this.oid,
                'name': this.name,
                // 'members': this.members
                'members': Member.exportMany(this.members)
            };
        };

        return (Group);
    }]);