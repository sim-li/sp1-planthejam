/**
 * @module models
 *
 * @author Sebastian Dass&eacute;
 */
angular.module('models')
    .factory('Repetition', ['TimeUnit', function(TimeUnit) {

        'use strict';

        /**
         * A repetition data type.
         *
         * @class Repetition
         * @constructor
         * @param {Object} [config={}] an optional configuration object
         * @param {Number} [config.oid=''] the object id of the repetition
         * @param {Number} [config.distance=1] the distance of the repetition
         * @param {String} [config.unit='WEEK'] the time unit of the repetition
         */
        var Repetition = function(config) {
            if (!(this instanceof Repetition)) {
                return new Repetition(config);
            }
            config = config || {};
            this.oid = config.oid || '';
            this.distance = config.distance || 1;
            this.unit = config.unit || TimeUnit.WEEK;
        };

        /**
         * This model's unique id.
         *
         * @property modelId
         * @type {String}
         */
        Repetition.prototype.modelId = 'repetition';

        /**
         * Exports the repetition by removing any client side attributes, that the server can not handle.
         *
         * @method doExport
         * @return {Object} the exported invite
         */
        Repetition.doExport = function() {
            return {
                'oid': this.oid,
                'distance': this.distance,
                'unit': this.unit
            };
        };

        return (Repetition);
    }]);