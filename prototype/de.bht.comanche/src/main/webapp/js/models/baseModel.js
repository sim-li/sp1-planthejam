/**
 * Provides a base model.
 *
 * @module baseModel
 *
 * @author Sebastian Dass&eacute;
 */
angular.module('baseModel', [])
    .factory('Model', function() {

        'use strict';

        var Model = function() {};

        Model.prototype.modelId = 'abstract';

        Model.importMany = function(ModelClass, rawModels) {
            if (!rawModels) {
                return rawModels;
            }
            var models = [];
            for (var i = 0, len = rawModels.length; i < len; i++) {
                models.push(new ModelClass(rawModels[i]));
            }
            console.log('imported many ' + ModelClass.prototype.modelId + 's');
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
    });