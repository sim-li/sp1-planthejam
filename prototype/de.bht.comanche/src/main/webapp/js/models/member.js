/**
 * @module models
 *
 * @author Sebastian Dass&eacute;
 */
angular.module('models')
    .factory('Member', ['arrayUtil', 'User', function(arrayUtil, User) {

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

        /**
         * This model's unique id.
         *
         * @property modelId
         * @type {String}
         */
        Member.prototype.modelId = 'member';

        /**
         * Exports the member by removing any client side attributes, that the server can not handle.
         *
         * @method doExport
         * @return {Object} the exported member
         */
        Member.prototype.doExport = function() {
            return {
                'oid': this.oid,
                'user': this.user.doExport()
            };
        };

        /**
         * Exports an array of members by removing any client side attributes, that the server can not handle.
         *
         * @method exportMany
         * @static
         * @param  {Array}  membersToExport the members to be exported
         * @return {Array}                  the exported members
         */
        Member.exportMany = function(membersToExport) {
            if (!membersToExport) {
                return [];
            }
            var members = [];
            // for (var i = 0; i < membersToExport.length; i++) {
            //     members.push(membersToExport[i].doExport());
            // }
            arrayUtil.forEach(membersToExport, function(ele) {
                members.push(ele.doExport());
            });
            return members;
        };

        return (Member);
    }]);