/*
 * Softwareprojekt SoSe/WiSe 2014, Team: Comanche
 * (C)opyright Sebastian Dassé, Mat.-Nr. 791537, s50602@beuth-hochschule.de
 *
 * Module: member
 */

'use strict';

angular.module('member', ['user'])
    .factory('Member', ['User', function(User) {

        var Member = function(config) {
            if (!(this instanceof Member)) {
                return new Member(config);
            }
            config = config || {};
        this.oid = config.oid /* || ''*/ ;
            this.user = new User(config.user);
        };

        Member.prototype.getModelId = function() {
            return 'Member';
        };

        Member.prototype.export = function() {
            return {
                'oid': this.oid,
                'user': this.user.export()
            };
        };

        Member.importMany = function(rawMembers) {
            if (!rawMembers) {
                return rawMembers;
            }
            var members = [];
            for (var i = 0; i < rawMembers.length; i++) {
                members.push(new Member(rawMembers[i]));
            }
            return members;
        };

        Member.exportMany = function(membersToExport) {
            var members = [];
            for (var i = 0; i < membersToExport.length; i++) {
                members.push(membersToExport[i].export());
            }
            return members;
        };

        return (Member);
    }]);