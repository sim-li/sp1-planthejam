/**
 * Provides a model for invites.
 *
 * @module invite
 * @requires user
 *
 * @author Sebastian Dass&eacute;
 */
angular.module('member', ['user'])
    .factory('Member', ['User', function(User) {

        'use strict';

        /**
         * Represents a member of a group of users.
         *
         * @class Member
         * @constructor
         * @param {Object} [config={}] an optional configuration object
         * @param {Number} config.oid the object id of the invite
         * @param {Object} [config.user=new Member()] the user that is a member
         */
        var Member = function(config) {
            if (!(this instanceof Member)) {
                return new Member(config);
            }
            config = config || {};
            this.oid = config.oid /* || ''*/ ;
            this.user = new User(config.user);
        };

        // Member.prototype = new Model();
        Member.prototype.modelId = 'member';

        /**
         * Returns this model's unique id.
         *
         * @method getModelId
         * @return {String} the model's id
         */
        // Member.prototype.getModelId = function() {
        //     return 'member';
        // };

        /**
         * Exports the member by removing any client side attributes, that the server can not handle.
         *
         * @return {Object} the exported member
         */
        Member.prototype.export = function() {
            return {
                'oid': this.oid,
                'user': this.user.export()
            };
        };

        /**
         * Imports an array of raw members by converting them to the member model.
         *
         * @method importMany
         * @static
         * @param  {Array}  rawMembers the members to be imported
         * @return {Array}             the imported members
         */
        // Member.importMany = function(rawMembers) {
        //     if (!rawMembers) {
        //         return rawMembers;
        //     }
        //     var members = [];
        //     for (var i = 0; i < rawMembers.length; i++) {
        //         members.push(new Member(rawMembers[i]));
        //     }
        //     return members;
        // };

        /**
         * Exports an array of members by removing any client side attributes, that the server can not handle.
         *
         * @param  {Array}  membersToExport the members to be exported
         * @return {Array}                  the exported members
         */
        Member.exportMany = function(membersToExport) {
            var members = [];
            for (var i = 0; i < membersToExport.length; i++) {
                members.push(membersToExport[i].export());
            }
            return members;
        };

        return (Member);
    }]);