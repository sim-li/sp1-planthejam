/**
 * @module models
 *
 * @author Sebastian Dass&eacute;
 */
angular.module('models')
    .factory('Invite', ['arrayUtil', 'Model', 'Status', 'Survey', 'TimePeriod', 'User',
        function(arrayUtil, Model, Status, Survey, TimePeriod, User) {

            'use strict';

            /**
             * Represents an invite. An invite belongs to a survey.
             *
             * @class Invite
             * @constructor
             * @param {Object}  [config={}] an optional configuration object
             * @param {Number}  [config.oid=''] the object id of the invite
             * @param {Boolean} [config.isHost='false'] a flag that indicates if the owning user of the invite is host of the survey
             * @param {Status}  [config.isIgnored='UNDECIDED'] a flag that indicates if the invite is ignored
             * @param {Object}  [config.user=new User()] the user that owns the invite
             * @param {Object}  [config.survey=new Survey()] the survey to which the invite belongs
             * @param {Array}   [config.concreteAvailability=[]] the available time periods of the participant for this survey
             */
            var Invite = function(config) {
                if (!(this instanceof Invite)) {
                    return new Invite(config);
                }
                config = config || {};
                this.oid = config.oid || '';
                this.isHost = config.isHost || false;
                this.isIgnored = config.isIgnored || Status.UNDECIDED;
                this.user = new User(config.user);
                this.survey = config.survey ? new Survey(config.survey) : '';
                this.concreteAvailability = Model.importMany(TimePeriod, config.concreteAvailability);
            };

            // Invite.prototype = new Model();

            /**
             * This model's unique id.
             *
             * @property modelId
             * @type {String}
             */
            Invite.prototype.modelId = 'invite';

            /**
             * Exports the invite by removing any client side attributes, that the server can not handle.
             *
             * @method doExport
             * @return {Object} the exported invite
             */
            Invite.prototype.doExport = function() {
                return {
                    'oid': this.oid,
                    'isHost': this.isHost,
                    'isIgnored': this.isIgnored,
                    'user': this.user.doExport(),
                    'survey': this.survey ? this.survey.doExport() : null,
                    'concreteAvailability': TimePeriod.exportMany(this.concreteAvailability)
                };
            };

            /**
             * Sets the isIgnored flag with the following significance:
             *   - Status.YES indicates that the invite is ignored by the user
             *   - Status.NO  indicates that the invite is accepted by the user
             *   - Status.UNDECIDED indicates that the user has not decided yet, whether or not to ignore the invite
             *
             * @method setIgnored
             * @param {Boolean} isIgnored a flag that indicates, whether or not the invite is ignored by the user
             */
            Invite.prototype.setIgnored = function(isIgnored) {
                this.isIgnored = isIgnored;
            };

            /**
             * Exports many invites.
             *
             * @method exportMany
             * @static
             * @param  {Array} invitesToExport the invites to export
             * @return {Array} the exported invites
             */
            Invite.exportMany = function(invitesToExport) {
                if (!invitesToExport) {
                    return [];
                }
                var invites = [];
                arrayUtil.forEach(invitesToExport, function(ele) {
                    invites.push(ele.doExport());
                });
                return invites;
            };

            return (Invite);
        }
    ]);