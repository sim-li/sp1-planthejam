/**
 * @module models
 *
 * @author Sebastian Dass&eacute;
 */
angular.module('models')
    .factory('Survey', ['DatePickerDate', 'Repetition', 'Status', 'SurveyType', 'TimeUnit',
        function(DatePickerDate, Repetition, Status, SurveyType, TimeUnit) {

            'use strict';

            /**
             * Represents a survey.
             *
             * @class Survey
             * @constructor
             * @param {Object}  [config={}] an optional configuration object
             * @param {Number}  [config.oid=''] the object id of the survey
             * @param {String}  [config.name=''] the name of the survey
             * @param {String}  [config.descrition=''] the description of the survey
             * @param {String}  [config.type='ONE_TIME'] the type of the survey
             * @param {Number}  [config.durationMins=0] the duration of the survey in minutes
             * @param {Date}    [config.deadline=new Date()] the deadline of the survey
             *
             * @param {Number}  [config.frequencyDist=0] the frequency distance of the survey
             * @param {String}  [config.frequencyTimeUnit='WEEK'] the frequency time unit of the survey
             *
             * @param {Array}   [config.possibleTimeperiods=[]] the possible time periods of the survey
             * @param {Object}  [config.determinedTimeperiod] the determined time period of the survey
             * @param {Status}  [config.success='UNDECIDED'] the status of the service, which can be one of the options [UNDECIDED, YES, NO]
             * @param {Boolean} [config.algoCheck=false] indicates whether or not the survey was already checked by the determination algorithm
             * @param {Array}   [config.invites=[]] the invites of the survey
             */
            var Survey = function(config) {
                config = config || {};
                this.oid = config.oid || '';
                this.name = config.name || '';
                this.description = config.description || '';
                this.type = config.type || SurveyType.ONE_TIME;
                this.durationMins = config.durationMins || 0;
                this.deadline = new Date(config.deadline) || new Date();
                // this.deadline = new DatePickerDate(config.deadline) || new DatePickerDate(new Date());
                this.frequency = new Repetition(config.frequency);
                // this.frequency = config.frequencyDist || 0;
                // this.frequencyTimeUnit = TimeUnit[config.frequencyTimeUnit] || TimeUnit.WEEK;
                this.possibleTimeperiods = config.possibleTimeperiods || [];
                this.determinedTimeperiod = config.determinedTimeperiod;
                this.success = config.success || Status.UNDECIDED;
                this.algoCheck = config.algoCheck || false;
                this.invites = config.invites || []; // FIXME the invites have to be imported seperately (right?)
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
             * Exports the member by removing any client side attributes, that the server can not handle.
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
                    'deadline': this.deadline,
                    // 'frequency': this.frequency.doExport(), // FIXME temporarily commented out

                    // 'frequencyDist': this.frequencyDist,
                    // 'frequencyTimeUnit': this.frequencyTimeUnit,

                    // 'possibleTimeperiods': this.possibleTimeperiods, // FIXME temporarily commented out
                    // 'determinedTimeperiod': this.determinedTimeperiod, // FIXME temporarily commented out

                    // 'success': this.success, // FIXME temporarily commented out
                    // 'algoCheck': this.algoCheck, // FIXME temporarily commented out
                    // 'invites': Invite.exportMany(this.invites) // FIXME <<=======================================================
                };
            };

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


            //-- Note: keep this date conversion for now, throw away when surveys have time periods -->
            //
            // Survey.prototype.convertDatesToDatePickerDate = function() {
            //     this.deadline = new DatePickerDate(this.deadline);
            //     var _possibleTimeperiods = [];
            //     for (var i = 0; i < this.possibleTimeperiods.length; i++) {
            //         var p = this.possibleTimeperiods[i];
            //         p.startTime = new DatePickerDate(p.startTime);
            //         _possibleTimeperiods.push(p);
            //     }
            //     this.possibleTimeperiods = _possibleTimeperiods;
            //     this.determinedTimeperiod.startTime = new DatePickerDate(this.determinedTimeperiod.startTime);
            // };
            //
            // Survey.prototype.convertDatesToJsDate = function() {
            //     this.deadline = this.deadline.toDate;
            //     var _possibleTimeperiods = [];
            //     for (var i = 0; i < this.possibleTimeperiods.length; i++) {
            //         var _p = this.possibleTimeperiods[i];
            //         _p.startTime = _p.startTime.toDate();
            //         _possibleTimeperiods.push(_p);
            //     }
            //     this.possibleTimeperiods = _possibleTimeperiods;
            //     this.determinedTimeperiod.startTime = this.determinedTimeperiod.toDate();
            // };
            //
            // Survey.forSurveysConvertDatesToDatePickerDate = function(surveys) {
            //     if (!surveys) {
            //         return surveys;
            //     }
            //     for (var i = 0; i < surveys.length; i++) {
            //         surveys[i].convertDatesToDatePickerDate();
            //     }
            //     return surveys;
            // };
            //
            // Survey.forSurveysConvertDatesToJsDate = function(surveys) {
            //     if (!surveys) {
            //         return surveys;
            //     }
            //     for (var i = 0; i < surveys.length; i++) {
            //         surveys[i].convertDatesToJsDate();
            //     }
            //     return surveys;
            // };
            //
            //  <--

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

            Survey.getDummy = function() {
                return new Survey({
                    oid: Math.round(Math.random() * 100),
                    name: randomString(5 + Math.round(Math.random() * 15)),
                    description: randomString(10 + Math.round(Math.random() * 30)),
                    type: SurveyType._options[Math.round(Math.random() * (SurveyType._options.length - 1))],
                    deadline: new DatePickerDate(new Date()),
                    // frequency: new Repetition(...),
                    frequencyDist: 1 + Math.round(Math.random() * 30),
                    frequencyTimeUnit: TimeUnit._options[Math.round(Math.random() * (TimeUnit._options.length - 1))],
                    possibleTimeperiods: [],
                    determinedTimeperiod: null
                });
            };

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