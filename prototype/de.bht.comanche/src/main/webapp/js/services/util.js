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
    .factory('arrayUtil', ['$log', 'modelUtil', function($log, modelUtil) {

        'use strict';

        var arrayUtil = {};

        arrayUtil.forEach = function(arr, callback) {
            for (var i = 0, len = arr.length; i < len; i++) {
                callback(arr[i], i, arr);
            }
        };

        // arrayUtil.forEach = function(arr, callback, context) {
        //     context = context || {};
        //     context.callback = callback;
        //     for (var i = 0, len = arr.length; i < len; i++) {
        //         context.callback(arr[i], i, arr);
        //     }
        //     delete context.callback;
        // };

        /*
         * Note: use Array.prototype.concat()
         */
        // arrayUtil.merge = function(arr1, arr2) {
        //     return arr1.concat(arr2);
        // };

        /*
         * Note: use Array.prototype.push()
         */
        // arrayUtil.addElement = function(arr, ele) {
        //     arr.push(ele);
        // };

        arrayUtil.findByKey = function(arr, key, value) {
            for (var i = 0, len = arr.length; i < len; i++) {
                var ele = arr[i];
                if (ele[key] == value) {
                    return ele;
                }
            }
        };

        arrayUtil.removeByKey = function(arr, key, value) {
            for (var i = 0, len = arr.length; i < len; i++) {
                var ele = arr[i];
                if (ele[key] == value) {
                    arr.splice(i, 1);
                    return arr;
                }
            }
        };

        arrayUtil.removeElement = function(arr, ele) {
            for (var i = 0, len = arr.length; i < len; i++) {
                if (modelUtil.areEqual(arr[i], ele)) {
                    arr.splice(i, 1);
                    return arr;
                }
            }
            return "not found + " + ele.id;
        };

        arrayUtil.contains = function(arr, ele) {
            var i = arr.length;
            while (i--) {
                if (arr[i] == ele) {
                    return true;
                }
            }
        };

        arrayUtil.removeDuplicatesByKey = function(arr, key) {
            var callback = function(ele, idx, arr) {
                // if (ele[key])
            };
            arrayUtil.forEach(arr, callback);
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

        return arrayUtil;
    }])
    /**
     * TODO comment it!
     *
     * @class  otherUtil
     */
    .factory('modelUtil', ['$log', function($log) {

        'use strict';

        var modelUtil = {};

        modelUtil.areEqual = function(obj1, obj2) {
            for (var attr in obj1) {
                if (obj1[attr] != obj2[attr]) {
                    return false;
                }
            }
            return true;
        };


        return modelUtil;
    }]);