/**
 * Provides some utility methods for basic operations.
 *
 * @module util
 *
 * @author Sebastian Dass&eacute;
 */
angular.module('util', [])
    /**
     * TODO comment it!
     *
     * @class  arrayUtil
     */
    .factory('arrayUtil', ['$log', function($log) {

        'use strict';

        var arrayUtil = {};

        arrayUtil.forEach = function() {

        };

        arrayUtil.merge = function() {

        };

        arrayUtil.addElement = function() {

        };

        arrayUtil.findByKey = function() {

        };

        arrayUtil.removeByKey = function() {

        };

        arrayUtil.removeElement = function() {

        };

        arrayUtil.removeDuplicatesByKey = function() {

        };

        /**
         * Pushes the element to the array or updates it, if is already contained.
         *
         * @method updateElementWithOid
         *
         * @param {Object} element an element with an attribute "oid"
         */
        // Array.prototype.updateElementWithOid = function(element) {
        //     var oid = element && element.oid;
        //     if (!oid) {
        //         return;
        //     }
        //     var foundIndex = -1;
        //     for (var i = 0, len = this.length; i < len; i++) {
        //         var sel = this[i];
        //         if (sel.oid && sel.oid === oid) {
        //             foundIndex = i;
        //         }
        //     }
        //     if (foundIndex > 0) {
        //         this[foundIndex] = element;
        //     } else {
        //         this.push(element);
        //     }
        // };

        return {
            arrayUtil: arrayUtil
        };
    }])
    /**
     * TODO comment it!
     *
     * @class  otherUtil
     */
    .factory('otherUtil', ['$log', function($log) {

        'use strict';

        var otherUtil = {};


        return {
            otherUtil: otherUtil
        };
    }]);