'use strict';

/**
 * Provides some constants.
 *
 * @module constants
 *
 * @author Sebastian Dass&eacute;
 */
angular.module('constants', [])
    /**
     * A __pseudo-enum__ for time units. Usage: TimeUnit.DAY addresses the String "DAY".
     *
     * @class TimeUnit
     */
    .constant('TimeUnit', function() {
        var DAY = 'DAY',
            WEEK = 'WEEK',
            MONTH = 'MONTH';
        return {
            /**
             * @property {PseudoEnum} DAY
             * @static
             */
            DAY: DAY,
            /**
             * @property {PseudoEnum} WEEK
             * @static
             */
            WEEK: WEEK,
            /**
             * @property {PseudoEnum} MONTH
             * @static
             */
            MONTH: MONTH,
            /**
             * The options of this pseudo-enum. Usage: TimeUnit._options[0] addresses the String "DAY".
             *
             * @property {Array} _options
             * @static
             */
            _options: [DAY, WEEK, MONTH]
        };
    }())
    /**
     * A __pseudo-enum__ for survey types.
     *
     * Usage: SurveyType.ONE_TIME addresses the String "ONE_TIME".
     *
     * @class SurveyType
     */
    .constant('SurveyType', function() {
        var ONE_TIME = 'ONE_TIME',
            RECURRING = 'RECURRING';
        return {
            /**
             * @property {PseudoEnum} ONE_TIME
             * @static
             */
            ONE_TIME: ONE_TIME,
            /**
             * @property {PseudoEnum} RECURRING
             * @static
             */
            RECURRING: RECURRING,
            /**
             * The options of this pseudo-enum. Usage: SurveyType._options[0] addresses the String "ONE_TIME".
             *
             * @property {Array} _options
             * @static
             */
            _options: [ONE_TIME, RECURRING]
        };
    }())
    /**
     * A __pseudo-enum__ for a status.
     *
     * Usage: Status.YES addresses the String "YES".
     *
     * @class Status
     */
    .constant('Status', function() {
        var UNDECIDED = 'UNDECIDED',
            YES = 'YES',
            NO = 'NO';
        return {
            /**
             * @property {PseudoEnum} UNDECIDED
             * @static
             */
            UNDECIDED: UNDECIDED,
            /**
             * @property {PseudoEnum} YES
             * @static
             */
            YES: YES,
            /**
             * @property {PseudoEnum} NO
             * @static
             */
            NO: NO,
            /**
             * The options of this pseudo-enum. Usage: Status._options[0] addresses the String "UNDECIDED".
             *
             * @property {Array} _options
             * @static
             */
            _options: [UNDECIDED, YES, NO]
        };
    }())
    /**
     * Some __regular expressions__ for input validation.
     *
     * @class patterns
     */
    .constant('patterns', {
        /**
         * Allows any sequence of non-whitespace characters with a length of including 8-20.
         *
         * @property {RegExp} password
         * @static
         */
        password: /^[\S]{8,20}$/, // no whitespace allowed -- TODO: at this point whitespace is still allowed at the beginning and end
        /**
         * Allows any character sequence that resembles an e-mail address.
         *
         * @property {RegExp} email
         * @static
         */
        email: /^[a-zA-Z][\w]*@[a-zA-Z]+\.[a-zA-Z]{2,3}$/, // TODO
        /**
         * Allows any strictly numeric character sequence with a length of including 4-12.
         *
         * @property {RegExp} tel
         * @static
         */
        tel: /^[0-9]{4,12}$/ // TODO
    });