/**
 * Provides a model for invites.
 *
 * @module invite
 * @requires survey
 * @requires user
 * @requires util
 *
 * @author Sebastian Dass&eacute;
 */
angular.module('invite', ['survey', 'user', 'util'])
    .factory('Invite', ['Survey', 'User', 'arrayUtil' /*, 'baseDatePickerDate'', 'TimeUnit', 'Type'*/ , function(Survey, User, arrayUtil /*, DatePickerDate, TimeUnit, Type*/ ) {

        'use strict';

        /**
         * Represents an invite. An invite belongs to a survey.
         *
         * @class Invite
         * @constructor
         * @param {Object}  [config={}] an optional configuration object
         * @param {Number}  [config.oid=''] the object id of the invite
         * @param {Boolean} [config.ignored] a flag that indicates if the invite is ignored
         * @param {Boolean} [config.host] a flag that indicates if the owning user of the invite is host of the survey
         * @param {Object}  [config.survey=new Survey()] the survey
         */
        var Invite = function(config) {
            if (!(this instanceof Invite)) {
                return new Invite(config);
            }
            config = config || {};
            this.oid = config.oid || '';
            this.ignored = config.ignored;
            this.host = config.host;
            this.user = new User(config.user);
            this.survey = new Survey(config.survey);
        };

        // Invite.prototype = new Model();

        /**
         * This model's unique id.
         *
         * @property modelId
         * @type {String}
         */
        Invite.prototype.modelId = 'invite';

        /**
         * Exports the invite by removing any client side attributes, that the server can not handle.
         *
         * @method doExport
         * @return {Object} the exported invite
         */
        Invite.prototype.doExport = function() {
            return {
                'oid': this.oid,
                'ignored': this.ignored,
                'host': this.host,
                'user': this.user.doExport(),
                'survey': this.survey.doExport()
            };
        };

        /**
         * Sets the ignored flag, which, if set to true, indicates that the invite is ignored by the user.
         *
         * @method setIgnored
         * @param {Boolean} ignored a flag that indicates, whether or not the invite is ignored by the user
         */
        Invite.prototype.setIgnored = function(ignored) {
            this.ignored = ignored;
        };

        /**
         * A factory method that creates a default invite for the specified user.
         *
         * @method createFor
         * @static
         * @param  {User}   user the host of the invite
         * @return {Invite}      a default invite
         */
        Invite.createFor = function(user) {
            return new Invite({
                'ignored': false,
                'host': true,
                'user': user,
                'survey': new Survey({
                    'name': 'Your survey',
                    'description': 'Say what it is all about',
                    'deadline': new Date()
                })
            });
        };

        /**
         * ...
         *
         * @method addParticipant
         * @param {User} user the user to be added as participant
         */
        Invite.prototype.addParticipant = function(user) {
            this.survey.invites.push(new Invite({
                user: user /*, host: false, ignored: false  <<--  default values in constructor, no need to set explicitly (?) */
            }));
        };

        /**
         * ...
         *
         * @method addParticipants
         * @param {Group} group the group, which members shall be added as participants
         */
        Invite.prototype.addParticipantsFromGroup = function(group) {
            if (group.modelId !== 'group') {
                return;
            }
            var self = this;
            arrayUtil.forEach(group.members, function(member) {
                self.addParticipant(member.user);
            });
        };

        /**
         * Replaces all participants from a list that can contain
         * users or groups.
         *
         * @param  {List} mixedList Mixed list of groups and users
         * @return {[type]}           [description]
         */
        Invite.prototype.updateParticipantsFromMixedList = function(mixedList) {
            this.survey.invites = [];
            arrayUtil.foreach(mixedList, function(element) {
                switch (element.modelId) {
                    case 'user':
                        addParticipant(element);
                        break;
                    case 'group':
                        addParticipantsFromGroup(element);
                        break;
                    default:
                        console.log('Faulty element in your collection');
                }
            });
        }


        // Invite.prototype.addParticipantsFromGroup = function(group) {
        //     if (group.modelId !== 'group') {
        //         return;
        //     }
        //     group.members.forEach(function(member) {
        //         this.addParticipant(member.user);
        //     }, this);
        // };

        Invite.exportMany = function(invitesToExport) {
            var invites = [];
            arrayUtil.forEach(invitesToExport, function(ele) {
                invites.push(ele.doExport());
            });
            return invites;
        };

        // Invite.exportMany = function(invitesToExport) {
        //     var invites = [];
        //     for (var i = 0; i < invitesToExport.length; i++) {
        //         invites.push(invitesToExport[i].doExport());
        //     }
        //     return invites;
        // };


        // Invite.prototype.convertDatesToDatePickerDate = function() {
        //     this.survey.convertDatesToDatePickerDate();
        // };

        // Invite.getDummyInviteList = function() {
        //     return [
        //         {   'oid': 1,
        //             'isHost': false,
        //             'isIgnored': false,
        //             'survey': {
        //                 'name': 'Bandprobe',
        //                 'description': 'Wir müssen vor dem Konzert Ende des Monats mindestens noch einmal proben. Wann könnt ihr?',
        //                 'type': Type.ONE_TIME, // or 'RECURRING' <<enumeration>> = einmalig oder wiederholt
        //                 // 'deadline': '10.07.2014, 23:55', // <<datatype>> date = Zeipunkt
        //                 'deadline': new Date(2014, 7, 10, 23, 55), // <<datatype>> date = Zeipunkt
        //                 'frequencyDist': 0, // <<datatype>> iteration = Wiederholung
        //                 'frequencyTimeUnit': TimeUnit.WEEK, // <<datatype>> iteration = Wiederholung
        //                 'possibleTimeperiods': [
        //                         { 'startTime': new Date(2014, 7, 11, 19, 0), 'durationInMins': 120 }, // <<datatype>> <timeperiod> = List<Zeitraum>
        //                         { 'startTime': new Date(2014, 7, 12, 20, 0), 'durationInMins': 120 },
        //                         { 'startTime': new Date(2014, 7, 18, 19, 30), 'durationInMins': 120 }
        //                     ],
        //                 'determinedTimeperiod': { 'startTime': new Date(2014, 7, 12, 20, 0), 'durationInMins': 120 } // <<datatype>> timeperiod = Zeitraum
        //             }
        //         },
        //         {   'oid': 2,
        //             'isHost': false,
        //             'isIgnored': false,
        //             'survey': {
        //                 'name': 'Chorprobe',
        //                 'description': 'Wir beginnen mit der Mozart-Messe in c-moll. In der Pause gibt es Kuchen im Garten.',
        //                 'type': Type.RECURRING,
        //                 'deadline': new Date(2014, 7, 21, 12, 0),
        //                 'frequencyDist': 0,
        //                 'frequencyTimeUnit': TimeUnit.DAY,
        //                 'possibleTimeperiods': [
        //                         { 'startTime': new Date(2014, 8, 1, 18, 30), 'durationInMins': 150 },
        //                         { 'startTime': new Date(2014, 8, 2, 18, 30), 'durationInMins': 150 }
        //                     ],
        //                 'determinedTimeperiod': { 'startTime': undefined, 'durationInMins': 0 }
        //             }
        //         },
        //         {   'oid': 3,
        //             'isHost': false,
        //             'isIgnored': false,
        //             'survey': {
        //                 'name': 'Meeting',
        //                 'description': 'Unser monatliches Geschäftsessen. Dresscode: Bussiness casual.',
        //                 'type': Type.RECURRING,
        //                 'deadline': new Date(2014, 7, 31, 8, 0),
        //                 'frequencyDist': 0,
        //                 'frequencyTimeUnit': TimeUnit.MONTH,
        //                 'possibleTimeperiods': [],
        //                 'determinedTimeperiod': { 'startTime': undefined, 'durationInMins': 0 }
        //             }
        //         }
        //     ];
        // };

        return (Invite);
    }]);