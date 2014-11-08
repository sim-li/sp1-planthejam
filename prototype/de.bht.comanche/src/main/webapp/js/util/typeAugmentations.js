/*
 * Softwareprojekt SoSe/WiSe 2014, Team: Comanche
 * (C)opyright Sebastian Dassé, Mat.-Nr. 791537, s50602@beuth-hochschule.de
 * 
 * Module: type augmentations
 */


"use strict";

angular.module("typeAugmentations", [])
    .factory("typeAugmentations", ["$log", function($log) {
        return function() {
            
            /**
             * TODO doc comment
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

            $log.log("type augmentations done");
        };
    }]);
