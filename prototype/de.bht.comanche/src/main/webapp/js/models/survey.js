/**
 * @module models
 *
 * @author Sebastian Dass&eacute;
 */
angular.module('models')
    .factory('Survey', ['arrayUtil', 'Model', 'Status', 'SurveyType', 'TimePeriod', 'TimeUnit',

        function(arrayUtil, Model, Status, SurveyType, TimePeriod, TimeUnit) {

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
             * @param {Number}  [config.surveyDurationMins=0] the duration of the survey in minutes
             * @param {Date}    [config.deadline=new Date()] the deadline of the survey
             * @param {Number}  [config.frequencyDist=0] the frequency distance of the survey
             * @param {String}  [config.frequencyUnit='WEEK'] the frequency time unit of the survey
             * @param {Array}   [config.possibleTimePeriods=[]] the possible time periods of the survey
             * @param {Object}  [config.determinedTimePeriod] the determined time period of the survey
             * @param {Status}  [config.success='UNDECIDED'] the status of the service, which can be one of the options [UNDECIDED, YES, NO]
             * @param {Boolean} [config.algoChecked=false] indicates whether or not the survey was already checked by the determination algorithm
             * @param {Array}   [config.invites=[]] the invites of the survey
             */
            var Survey = function(config) {
                if (!(this instanceof Survey)) {
                    return new Survey(config);
                }
                var nowInOneWeek = new Date(new Date().getTime() + (7 * 24 * 60 * 60 * 1000));

                config = config || {};
                this.oid = config.oid || '';
                this.name = config.name || 'Your survey';
                this.description = config.description || 'Say what it is all about';
                this.type = config.type || SurveyType.ONE_TIME;
                this.surveyDurationMins = config.surveyDurationMins || 0;
                this.deadline = config.deadline ? new Date(config.deadline) : nowInOneWeek;
                this.frequencyDist = config.frequencyDist || 0;
                this.frequencyUnit = TimeUnit[config.frequencyUnit] || TimeUnit.WEEK;
                this.possibleTimePeriods = Model.importMany(TimePeriod, config.possibleTimePeriods);
                this.determinedTimePeriod = new TimePeriod(config.determinedTimePeriod);
                this.success = config.success || Status.UNDECIDED;
                this.algoChecked = config.algoChecked || false;
                this.invites = config.invites || [];
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
                    'surveyDurationMins': this.surveyDurationMins,
                    'deadline': this.deadline,
                    'invites': this.invites,
                    'frequencyDist': this.frequencyDist,
                    'frequencyUnit': this.frequencyUnit,
                    'possibleTimePeriods': TimePeriod.exportMany(this.possibleTimePeriods),
                    'determinedTimePeriod': this.determinedTimePeriod.doExport(),
                    'success': this.success,
                    'algoChecked': this.algoChecked
                };
            };

            /**
             * Returns true if this survey has participants - which is the case when it has any invites that are not the host's invite.
             *
             * @method hasParticipants
             * @return {Boolean} true if this survey has participants, otherwise false
             */
            Survey.prototype.hasParticipants = function() {
                return arrayUtil.findByAttribute(this.invites, 'isHost', false) ? true : false;
            };

            /**
             * Returns true if this survey was not evaluated yet.
             *
             * @method isNotReady
             * @return {Boolean} true if this survey is not ready, otherwise false
             */
            Survey.prototype.isNotReady = function() {
                return !this.algoChecked;
            };

            /**
             * Returns true if this survey was evaluated but the host has still to confirm or reject the result.
             *
             * @method isReady
             * @return {Boolean} true if this survey is ready, otherwise false
             */
            Survey.prototype.isReady = function() {
                return this.algoChecked && this.success == Status.UNDECIDED;
            };

            /**
             * Returns true if this survey was evaluated and the host confirmed the result.
             *
             * @method isSuccessful
             * @return {Boolean} true if this survey is successful, otherwise false
             */
            Survey.prototype.isSuccessful = function() {
                return this.algoChecked && this.success == Status.YES;
            };

            /**
             * Returns true if this survey was evaluated and the host rejected the result.
             *
             * @method isUnsuccessful
             * @return {Boolean} true if this survey is unsuccessful, otherwise false
             */
            Survey.prototype.isUnsuccessful = function() {
                return this.algoChecked && this.success == Status.NO;
            };


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
                    surveyDurationMins: Math.round(Math.random() * 240),
                    deadline: new Date(),
                    frequencyDist: 1 + Math.round(Math.random() * 30),
                    frequencyUnit: TimeUnit._options[Math.round(Math.random() * (TimeUnit._options.length - 1))],
                    possibleTimePeriods: [],
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
                    dummies.push(Survey.getDummy());
                }
                return dummies;
            };

            return (Survey);
        }
    ]);