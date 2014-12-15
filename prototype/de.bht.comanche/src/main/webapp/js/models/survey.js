/**
 * Provides a model for surveys.
 *
 * @module survey
 * @requires datePickerDate
 * @requires constants
 * @requires invite
 *
 * @author Sebastian Dass&eacute;
 */
angular.module('survey', ['constants', 'datePickerDate'])
    .factory('Survey', ['DatePickerDate', 'TimeUnit', 'Type', function(DatePickerDate, TimeUnit, Type) {

        'use strict';

        // TODO still lacks the list of invites !!!

        /**
         * Represents a survey.
         *
         * @class Survey
         * @constructor
         * @param {Object} [config={}] an optional configuration object
         * @param {Number} [config.oid=''] the object id of the survey
         * @param {String} [config.name=''] the name of the survey
         * @param {String} [config.descrition=''] the description of the survey
         * @param {String} [config.type='ONE_TIME'] the type of the survey
         * @param {Date}   [config.deadline=new Date()] the deadline of the survey
         * @param {Number} [config.frequencyDist=0] the frequency distance of the survey
         * @param {String} [config.frequencyTimeUnit='WEEK'] the frequency time unit of the survey
         * @param {Array}  [config.possibleTimeperiods=[]] the possible time periods of the survey
         * @param {Object} [config.determinedTimeperiod] the determined time period of the survey
         */
        var Survey = function(config) {
            config = config || {};
            this.oid = config.oid || '';
            this.name = config.name || '';
            this.description = config.description || '';
            this.type = config.type || Type.ONE_TIME;
            this.invites = []; // the invites have to be imported seperately
            this.deadline = new DatePickerDate(config.deadline) || new DatePickerDate(new Date());
            this.frequencyDist = config.frequencyDist || 0;
            this.frequencyTimeUnit = TimeUnit[config.frequencyTimeUnit] || TimeUnit.WEEK;
            this.possibleTimeperiods = config.possibleTimeperiods || [];
            this.determinedTimeperiod = config.determinedTimeperiod || {
                'startTime': new DatePickerDate(),
                'durationInMins': 0
            };
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
                'deadline': this.deadline.toDate(),
                'frequencyDist': this.frequencyDist,
                'frequencyTimeUnit': this.frequencyTimeUnit
                    //, 'possibleTimeperiods': this.possibleTimeperiods,
                    // 'determinedTimeperiod': this.determinedTimeperiod
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

        return (Survey);
    }]);