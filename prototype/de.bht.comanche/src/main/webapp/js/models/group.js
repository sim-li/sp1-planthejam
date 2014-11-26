/*
 * Softwareprojekt SoSe/WiSe 2014, Team: Comanche
 * (C)opyright Sebastian Dassé, Mat.-Nr. 791537, s50602@beuth-hochschule.de
 *
 * Module: group
 */

'use strict';

angular.module('group', [])
    .factory('Group', function(Survey) {

        var Group = function(config) {
            if (!(this instanceof Group)) {
                return new Group(config);
            }
            config = config || {};
            this.oid = config.oid || '';
            this.name = config.name || '';
            this.members = config.members || []; // e.g.: [{oid: 1, name: 'Alice'}, {oid: 2, name: 'Bob'}, {oid: 3, name: 'Carla'}]
        };

        Group.prototype.getModelId = function() {
            return 'group';
        };

        Group.importMany = function(rawGroups) {
            if (!rawGroups) {
                return rawGroups;
            }
            var groups = [];
            for (var i = 0; i < rawGroups.length; i++) {
                groups[i] = new Group(rawGroups[i]);
            }
            // console.log("imported " + rawGroups.length)
            return groups;
        };

        Group.prototype.export = function() {
            return {
                'oid': this.oid,
                'name': this.name,
                // 'host': this.members
            };
        };

        return (Group);
    });