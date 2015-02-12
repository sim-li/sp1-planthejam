/**
 * Provides the model classes.
 *
 * @module models
 * @main
 * @requires constants
 * @requires util
 *
 * @author Sebastian Dass&eacute;
 */
angular.module('models', ['constants', 'util'])
    .factory('Model', ['arrayUtil', function(arrayUtil) {

        'use strict';

        /**
         * Represents a base model for all model classes.
         *
         * For now it only provides the method `importMany` to import many models of the same class at once conveniently.
         *
         * For the future the idea would be to let all model classes inherit from the prototype of `baseModel` like this:
         *
         * <pre><code>
         * var ConcreteModelClass = function(config) { ... }; // the constructor
         * ConcreteModelClass.prototype = new Model();
         * ConcreteModelClass.prototype.modelId = 'concreteModelId';
         * </pre></code>
         *
         * It is important to have the code in this order!
         *
         * @class Model
         * @constructor
         */
        var Model = function() {};

        /**
         * This model's unique id.
         *
         * @property modelId
         * @type {String}
         */
        Model.prototype.modelId = 'abstract';

        /**
         * Imports an array of raw models by converting them to the specified model class.
         *
         * @method importMany
         * @static
         * @param  {Function} ModelClass the model class
         * @param  {Array}    rawModels  the raw models to be imported
         * @return {Array}               the imported models
         */
        Model.importMany = function(ModelClass, rawModels) {
            if (!rawModels) {
                return [];
            }
            // console.log('importing many ' + ModelClass.prototype.modelId + 's');
            var models = [];
            arrayUtil.forEach(rawModels, function(rawModel) {
                models.push(new ModelClass(rawModel));
            });
            return models;
        };

        /* ---
         * it is very likely that exporting cannot be done for our models, at least when they themselves have their
         * own collections of models
         * ---
         */
        //-- Note: ignore is an optional array of attributes that shall not be exported
        // Model.prototype.doExport = function(ignore) {
        //     ignore = ignore || [];
        //     var obj = this;
        //     var exp = {};
        //     for (var attr in obj) {
        //         var objAttr = obj[attr];
        //         if (typeof objAttr !== 'function' && ignore.indexOf(attr) < 0) {
        //             exp[attr] = objAttr;
        //         }
        //     }
        //     return exp;
        // };

        //-- Note: ignore is an optional array of attributes that shall not be exported
        // Model.exportMany = function(modelsToExport, ignore) {
        //     if (!modelsToExport) {
        //         return modelsToExport;
        //     }
        //     var models = [];
        //     for (var i = 0, len = modelsToExport.length; i < len; i++) {
        //         models.push(modelsToExport[i].doExport());
        //     }
        //     return models;
        // };

        return (Model);
    }]);