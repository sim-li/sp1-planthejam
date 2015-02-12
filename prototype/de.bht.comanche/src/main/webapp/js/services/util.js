/**
 * Provides some utility methods for basic operations.
 *
 * @module util
 *
 * @author Sebastian Dass&eacute;
 */
angular.module('util', [])
    /**
     * A utility class providing basic array operations.
     *
     * __Note:__ be sure to use the following native JavaScript array operations:
     *
     * - to add an element to an array simply use Array.prototype.push(), for example:
     *         var arr = [1, 2, 3].push(4);             // arr == [1, 2, 3, 4]
     * - to merge two arrays simply use Array.prototype.concat(), for example:
     *         var arr = [1, 2, 3].concat([4, 5, 6]);   // arr == [1, 2, 3, 4, 5, 6]
     * - to filter an array use Array.prototype.filter(callback), for example:
     *         var arr = [1, 2, 3];
     *         var callback = function(val, idx, arr) {
     *             return val <= 2;
     *             };
     *         var res = arr.filter(callback);          // arr == [1, 2]
     *
     * @class  arrayUtil
     */
    .factory('arrayUtil', ['modelUtil', function(modelUtil) {

        'use strict';

        var arrayUtil = {};

        /**
         * Executes the specified callback function for each element of the array.
         *
         * @method forEach
         * @static
         * @param  {Array}    arr      the array
         * @param  {Function} callback the function to be executed for each
         * element, taking three arguments:
         * - element - the current element being processed
         * - index   - the index of the current element
         * - arrays  - the array itself
         * @return {Array}             a reference to the array
         */
        arrayUtil.forEach = function(arr, callback) {
            for (var i = 0, len = arr.length; i < len; i++) {
                callback(arr[i], i, arr);
            }
            return arr;
        };

        //---- forEach with context -- if we have use for it?
        // arrayUtil.forEach = function(arr, callback, context) {
        //     context = context || {};
        //     context.callback = callback;
        //     for (var i = 0, len = arr.length; i < len; i++) {
        //         context.callback(arr[i], i, arr);
        //     }
        //     delete context.callback;
        // };

        /**
         * Returns the first array element with the specified attribute
         * matching the specified value, or undefined if no matching element
         * was found.
         *
         * @method findByAttribute
         * @static
         * @param  {Array}  arr   the array
         * @param  {String} attr  the attribute name
         * @param  {Object} value the attribute value to be searched for
         * @return {Object}       the found object or undefined
         */
        arrayUtil.findByAttribute = function(arr, attr, value) {
            for (var i = 0, len = arr.length; i < len; i++) {
                var ele = arr[i];
                if (ele[attr] == value) {
                    return ele;
                }
            }
        };

        /**
         * Removes the first array element with the specified attribute
         * matching the specified value.
         *
         * @method removeByAttribute
         * @static
         * @param  {Array}  arr   the array
         * @param  {String} attr  the attribute name
         * @param  {Object} value the attribute value to be searched for
         * @return {Array}        a reference to the array
         */
        arrayUtil.removeByAttribute = function(arr, attr, value) {
            for (var i = 0, len = arr.length; i < len; i++) {
                var ele = arr[i];
                if (ele[attr] == value) {
                    arr.splice(i, 1);
                    return arr;
                }
            }
            return arr;
        };

        /**
         * Removes the first occurrence of the specified element from the array.
         *
         * @method remove
         * @static
         * @param  {Array}    arr      the array
         * @param  {Object}   obj      the element to be removed
         * @param  {Function} [areEqual=modelUtil.areEqual] an optional
         * function that returns true, if two objects are considered equal; the
         * default considers two objects to be equal, if all their attributes
         * are equal
         * @return {Array}             a reference to the array
         */
        arrayUtil.remove = function(arr, obj, areEqual) {
            areEqual = areEqual || modelUtil.areEqual;
            for (var i = 0, len = arr.length; i < len; i++) {
                if (areEqual(arr[i], obj)) {
                    arr.splice(i, 1);
                    return arr;
                }
            }
            return arr;
        };

        /**
         * Returns true if the array contains the specified element.
         *
         * @method contains
         * @static
         * @param  {Array}    arr      the array
         * @param  {Object}   obj      the object to be searched for
         * @param  {Function} [areEqual=modelUtil.areEqual] an optional
         * function that returns true, if two objects are considered equal; the
         * default considers two objects to be equal, if all their attributes
         * are equal
         * @return {Boolean}          true if the array contains the element
         */
        arrayUtil.contains = function(arr, obj, areEqual) {
            areEqual = areEqual || modelUtil.areEqual;
            var i = arr.length;
            while (i--) {
                if (modelUtil.areEqual(arr[i], obj)) {
                    return true;
                }
            }
            return false;
        };

        /**
         * Removes all duplicate elements based on the specified attribute.
         * More particularly, it forbids duplicate values for the specified
         * attribute and thus removes all but the first occurrence.
         *
         * @method removeDuplicatesByAttribute
         * @static
         * @param  {Array}  arr  the array
         * @param  {String} attr the attribute name
         * @return {Array}       a reference to the array
         */
        arrayUtil.removeDuplicatesByAttribute = function(arr, attr) {
            var map = {};
            arrayUtil.forEach(arr, function(ele) {
                var key = ele[attr];
                if (!map[key]) {
                    map[key] = ele;
                }
            });
            arr.splice(0);
            for (var key in map) {
                arr.push(map[key]);
            }
            return arr;
        };

        return arrayUtil;
    }])
    /**
     * A utility class providing basic operations for models/objects.
     *
     * @class  modelUtil
     */
    .factory('modelUtil', function() {

        'use strict';

        var modelUtil = {};

        /**
         * Compares two specified objects for equality by their attributes.
         *
         * @method areEqual
         * @static
         * @param  {Object}  obj1 an object
         * @param  {Object}  obj2 another object
         * @return {Boolean}      true if the all the attributes are equal
         */
        modelUtil.areEqual = function(obj1, obj2) {
            for (var attr in obj1) {
                if (obj1[attr] != obj2[attr]) {
                    return false;
                }
            }
            return true;
        };


        return modelUtil;
    });