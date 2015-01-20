/**
 * @module models
 *
 * @author Sebastian Dass&eacute;
 */
angular.module('models')
    .factory('Survey', ['arrayUtil', 'Status', 'SurveyType', 'TimePeriod', 'TimeUnit',

        function(arrayUtil, Status, SurveyType, TimePeriod, TimeUnit) {

            'use strict';

            /**
             * Represents a survey.
             *
             * @class Survey
             * @constructor
             * @param {Object}  [config={}] an optional configuration object
             * @param {Number}  [config.oid=''] the object id of the survey
             * @param {String}  [config.name='Your survey'] the name of the survey
             * @param {String}  [config.descrition='Say what it is all about'] the description of the survey
             * @param {String}  [config.type='ONE_TIME'] the type of the survey
             * @param {Number}  [config.durationMins=0] the duration of the survey in minutes
             * @param {Date}    [config.deadline=new Date()] the deadline of the survey
             * @param {Number}  [config.frequencyDist=0] the frequency distance of the survey
             * @param {String}  [config.frequencyUnit='WEEK'] the frequency time unit of the survey
             * @param {Array}   [config.possibleTimePeriods=[]] the possible time periods of the survey
             * @param {Object}  [config.determinedTimePeriod] the determined time period of the survey
             * @param {Status}  [config.success='UNDECIDED'] the status of the service, which can be one of the options [UNDECIDED, YES, NO]
             * @param {Boolean} [config.algoChecked=false] indicates whether or not the survey was already checked by the determination algorithm
             *
             *          @param {Array}   [config.invites=[]] the invites of the survey           ==> TODO ==> delete this line if attribute was removed
             */
            var Survey = function(config) {
                config = config || {};
                this.oid = config.oid || '';
                this.name = config.name || 'Your survey';
                this.description = config.description || 'Say what it is all about';
                this.type = config.type || SurveyType.ONE_TIME;
                this.durationMins = config.durationMins || 0;
                this.deadline = config.deadline ? new Date(config.deadline) : new Date();
                this.frequencyDist = config.frequencyDist || 0;
                this.frequencyUnit = TimeUnit[config.frequencyUnit] || TimeUnit.WEEK;
                this.possibleTimePeriods = config.possibleTimePeriods || [];
                this.determinedTimePeriod = config.determinedTimePeriod;
                this.success = config.success || Status.UNDECIDED;
                this.algoChecked = config.algoChecked || false;
                this.invites = config.invites || []; // ??? -- removed ON PURPOSE --> the invites shall be imported seperately
            };

            // Survey.prototype = new Model();

            /**
             * This model's unique id.
             *
             * @property modelId
             * @type {String}
             */
            Survey.prototype.modelId = 'survey';

            /**
             * Exports the survey by removing any client side attributes, that the server can not handle.
             *
             * @method doExport
             * @return {Object} the exported survey
             */
            Survey.prototype.doExport = function() {
                return {
                    'oid': this.oid,
                    'name': this.name,
                    'description': this.description,
                    'type': this.type,
                    'durationMins': this.durationMins,
                    'deadline': this.deadline,
                    'invites': this.invites,
                    'frequencyDist': this.frequencyDist, // FIXME temporarily commented out
                    'frequencyUnit': this.frequencyUnit, // FIXME temporarily commented out
                    'possibleTimePeriods': TimePeriod.exportMany(this.possibleTimePeriods), // FIXME temporarily commented out
                    'determinedTimePeriod': this.determinedTimePeriod ? this.determinedTimePeriod.doExport() : null, // FIXME temporarily commented out
                    'success': this.success, // FIXME temporarily commented out
                    'algoChecked': this.algoChecked // FIXME temporarily commented out
                };
            };

            Survey.prototype.hasParticipants = function() {
                return arrayUtil.findByAttribute(this.invites, 'host', false) ? true : false;
            };

            Survey.prototype.isReady = function() {
                return this.algoChecked && this.success == 'UNDECIDED';
            };

            // Invite.prototype.addParticipantsFromGroup = function(group) {
            //     if (group.modelId !== 'group') {
            //         return;
            //     }
            //     group.members.forEach(function(member) {
            //         this.addParticipant(member.user);
            //     }, this);
            // };

            //--------------------------------------------------------------------
            // IMPORTANT NOTE!
            //
            // Surveys do not not know their invites on client side.
            // In the survey-controller there is:
            // - $scope.selectedSurvey        -> the survey
            // - $scope.selectedSurveyInvites -> the invites of the survey -- add participants here
            // Therefore in the survey-controller we need some methods to handle add/remove of participants.
            // Some of the methods might as well belong to the invite-model.
            //--------------------------------------------------------------------


            // Invite.prototype.addParticipantsFromGroup = function(group) {
            //     if (group.modelId !== 'group') {
            //         return;
            //     }
            //     group.members.forEach(function(member) {
            //         this.addParticipant(member.user);
            //     }, this);
            // };

            // TODO maybe not used -> to be implemented inviteCtrl?
            // /**
            //  * ...
            //  *
            //  * @method addParticipant
            //  * @param {User} user the user to be added as participant
            //  */
            // Survey.prototype.addParticipant = function(user) {
            //     this.invites.push(new Invite({
            //         user: user /*, host: false, ignored: false  <<--  default values in constructor, no need to set explicitly (?) */
            //     }));
            // };

            // TODO maybe not used -> to be implemented inviteCtrl?
            // /**
            //  * ...
            //  *
            //  * @method addParticipants
            //  * @param {Group} group the group, which members shall be added as participants
            //  */
            // Survey.prototype.addParticipantsFromGroup = function(group) {
            //     if (group.modelId !== 'group') {
            //         return;
            //     }
            //     group.members.forEach(function(member) {
            //         this.addParticipant(member.user);
            //     }, this);
            // };

            //--------------------------------------------------------------------


            var randomChar = function() {
                return String.fromCharCode('a'.charCodeAt(0) + Math.round(Math.random() * 25));
            };

            var randomString = function(len) {
                var str = '';
                while (len--) {
                    str += randomChar();
                }
                return str;
            };

            /**
             * Provides a dummy survey.
             *
             * @method getDummy
             * @return {Object} a dummy survey
             */
            Survey.getDummy = function() {
                return new Survey({
                    oid: Math.round(Math.random() * 100),
                    name: randomString(5 + Math.round(Math.random() * 15)),
                    description: randomString(10 + Math.round(Math.random() * 30)),
                    type: SurveyType._options[Math.round(Math.random() * (SurveyType._options.length - 1))],
                    durationMins: Math.round(Math.random() * 240),
                    deadline: new Date(),
                    frequencyDist: 1 + Math.round(Math.random() * 30),
                    frequencyUnit: TimeUnit._options[Math.round(Math.random() * (TimeUnit._options.length - 1))],
                    possibleTimePeriods: [],
                    determinedTimePeriods: [],
                    determinedTimePeriod: null
                });
            };

            /**
             * Provides an array of dummy surveys.
             *
             * @method getDummies
             * @param  {Number} num the number of dummies
             * @return {Array}     an array of dummie surveys
             */
            Survey.getDummies = function(num) {
                var dummies = [];
                for (var i = 0; i < num; i++) {
                    var dummy = Survey.getDummy();
                    dummies.push(dummy);
                }
                return dummies;
            };

            return (Survey);
        }
    ]);