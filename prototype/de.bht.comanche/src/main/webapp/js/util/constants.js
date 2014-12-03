'use strict';

/**
 * Provides some constants.
 *
 * @module constants
 * @class constants
 *
 * @author Sebastian Dass&eacute;
 */
angular.module('constants', [])
    /**
     * A pseudo-enum for time units with the following options:
     * - DAY
     * - WEEK
     * - MONTH
     *
     * Usage: TimeUnit.DAY addresses the String "DAY"
     *
     * @property TimeUnit
     * @type {PseudoEnum}
     */
    .constant('TimeUnit', function() {
        var DAY = 'DAY',
            WEEK = 'WEEK',
            MONTH = 'MONTH';
        return {
            DAY: DAY,
            WEEK: WEEK,
            MONTH: MONTH,
            options_: [DAY, WEEK, MONTH]
        };
    }())
    /**
     * A pseudo-enum for types with the following options:
     * - ONE_TIME
     * - RECURRING
     *
     * Usage: Type.ONE_TIME addresses the String "ONE_TIME"
     *
     * @property Type
     * @type {PseudoEnum}
     */
    .constant('Type', function() {
        var ONE_TIME = 'ONE_TIME',
            RECURRING = 'RECURRING';
        return {
            ONE_TIME: ONE_TIME,
            RECURRING: RECURRING,
            options_: [ONE_TIME, RECURRING]
        };
    }())
    /**
     * Some regular expressions for input validation:
     * - patterns.name - allows any sequence of non-whitespace characters with a length of including 8-20
     * - patterns.email - allows any character sequence that resembles an e-mail address
     * - patterns.tel - allows any strictly numeric character sequence with a length of including 4-12
     *
     * @property patterns
     * @type {RegExp}
     */
    .constant('patterns', {
        password: /^[\S]{8,20}$/, // no whitespace allowed -- TODO: at this point whitespace is still allowed at the beginning and end
        email: /^[a-zA-Z][\w]*@[a-zA-Z]+\.[a-zA-Z]{2,3}$/, // TODO
        tel: /^[0-9]{4,12}$/ // TODO
    });