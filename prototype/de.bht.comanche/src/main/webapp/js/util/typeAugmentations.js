/**
 * Provides some type augmentations.
 *
 * @module typeAugmentations
 *
 * @author Sebastian Dass&eacute;
 */
angular.module('typeAugmentations', [])
    /**
     * Augments built-in JavaScript types with additional functions.
     *
     * The augmentations are applied to the types as soon as the factory method is called once.
     *
     * @class  typeAugmentations
     */
    .factory('typeAugmentations', ['$log', function($log) {

        'use strict';

        return function() {

            /**
             * Pushes the element to the array or updates it, if is already contained.
             *
             * @method updateElementWithOid
             *
             * @param {Object} element an element with an attribute "oid"
             */
            Array.prototype.updateElementWithOid = function(element) {
                var oid = element && element.oid;
                if (!oid) {
                    return;
                }
                var foundIndex = -1;
                for (var i = 0, len = this.length; i < len; i++) {
                    var sel = this[i];
                    if (sel.oid && sel.oid === oid) {
                        foundIndex = i;
                    }
                }
                if (foundIndex > 0) {
                    this[foundIndex] = element;
                } else {
                    this.push(element);
                }
            };

            $log.log('type augmentations done');
        };
    }]);