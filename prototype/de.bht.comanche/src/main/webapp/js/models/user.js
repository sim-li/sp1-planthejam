/*
 * Softwareprojekt SoSe/WiSe 2014, Team: Comanche
 * (C)opyright Sebastian Dassé, Mat.-Nr. 791537, s50602@beuth-hochschule.de
 *
 * Module: user
 */

'use strict';

angular.module('user', [])
    .factory('User', function(Survey) {

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
            this.invites = [];
            this.groups = [];
        };

        User.prototype.getModelId = function() {
            return 'user';
        };

        return (User);
    });