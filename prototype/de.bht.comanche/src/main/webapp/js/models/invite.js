/**
 * @module models
 *
 * @author Sebastian Dass&eacute;
 */
angular.module('models')
    .factory('Invite', ['arrayUtil', 'Model', 'Status', 'Survey' /*, 'SurveyType', 'TimeUnit'*/ , 'TimePeriod', 'User',
        function(arrayUtil, Model, Status, Survey /*, SurveyType, TimeUnit*/ , TimePeriod, User) {

            'use strict';

            /**
             * Represents an invite. An invite belongs to a survey.
             *
             * @class Invite
             * @constructor
             * @param {Object}  [config={}] an optional configuration object
             * @param {Number}  [config.oid=''] the object id of the invite
             * @param {Boolean} [config.host='false'] a flag that indicates if the owning user of the invite is host of the survey
             * @param {Status}  [config.ignored='UNDECIDED'] a flag that indicates if the invite is ignored
             * @param {Object}  [config.user=new User()] the user that owns the invite
             * @param {Object}  [config.survey=new Survey()] the survey to which the invite belongs
             *
             * @param {Array}   [config.concreteAvailability=[]] the available time periods of the participant for this survey
             */
            var Invite = function(config) {
                if (!(this instanceof Invite)) {
                    return new Invite(config);
                }
                config = config || {};
                this.oid = config.oid || '';
                this.host = config.host || false;
                this.ignored = config.ignored || Status.UNDECIDED;
                this.user = new User(config.user);
                this.survey = config.survey ? new Survey(config.survey) : ''; // ???
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
                    'host': this.host,
                    'ignored': this.ignored,
                    'user': this.user.doExport(),
                    'survey': this.survey ? this.survey.doExport() : null /*,*/
                        // 'timePeriods': TimePeriod.exportMany(this.concreteAvailability) // FIXME temporarily commented out
                };
            };

            /**
             * Sets the ignored flag, which, if set to true, indicates that the invite is ignored by the user.
             *
             * @method setIgnored
             * @param {Boolean} ignored a flag that indicates, whether or not the invite is ignored by the user
             */
            Invite.prototype.setIgnored = function(ignored) {
                this.ignored = ignored;
            };

            /**
             * NOT USED ANYMORE???
             *
             * A factory method that creates a default invite for the specified user.
             *
             * @method createFor
             * @static
             * @param  {User}   user the host of the invite
             * @return {Invite}      a default invite
             */
            // Invite.createFor = function(user) {
            //     return new Invite({
            //         'ignored': false,
            //         'host': true,
            //         'user': user,
            //         'survey': new Survey({
            //             'name': 'Your survey',
            //             'description': 'Say what it is all about',
            //             'deadline': new Date()
            //         })
            //     });
            // };

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


            //--------------------------------------------------------------------
            // IMPORTANT NOTE!
            //
            // Surveys do not not know their invites on client side.
            // In the survey-controller there is:
            // - $scope.selectedSurvey        -> the survey
            // - $scope.selectedSurveyInvites -> the invites of the survey -- add participants here
            // Therefore in the survey-controller we need some methods to handle add/remove of participants.
            // Some of the methods might as well belong to the invite-model.
            //--------------------------------------------------------------------


            //--------------------------------------------------------------------


            // Invite.getDummyInviteList = function() {
            //     return [
            //         {   'oid': 1,
            //             'isHost': false,
            //             'isIgnored': false,
            //             'survey': {
            //                 'name': 'Bandprobe',
            //                 'description': 'Wir müssen vor dem Konzert Ende des Monats mindestens noch einmal proben. Wann könnt ihr?',
            //                 'type': Type.ONE_TIME, // or 'RECURRING' <<enumeration>> = einmalig oder wiederholt
            //                 // 'deadline': '10.07.2014, 23:55', // <<datatype>> date = Zeipunkt
            //                 'deadline': new Date(2014, 7, 10, 23, 55), // <<datatype>> date = Zeipunkt
            //                 'frequencyDist': 0, // <<datatype>> iteration = Wiederholung
            //                 'frequencyTimeUnit': TimeUnit.WEEK, // <<datatype>> iteration = Wiederholung
            //                 'possibleTimeperiods': [
            //                         { 'startTime': new Date(2014, 7, 11, 19, 0), 'durationInMins': 120 }, // <<datatype>> <timeperiod> = List<Zeitraum>
            //                         { 'startTime': new Date(2014, 7, 12, 20, 0), 'durationInMins': 120 },
            //                         { 'startTime': new Date(2014, 7, 18, 19, 30), 'durationInMins': 120 }
            //                     ],
            //                 'determinedTimeperiod': { 'startTime': new Date(2014, 7, 12, 20, 0), 'durationInMins': 120 } // <<datatype>> timeperiod = Zeitraum
            //             }
            //         },
            //         {   'oid': 2,
            //             'isHost': false,
            //             'isIgnored': false,
            //             'survey': {
            //                 'name': 'Chorprobe',
            //                 'description': 'Wir beginnen mit der Mozart-Messe in c-moll. In der Pause gibt es Kuchen im Garten.',
            //                 'type': Type.RECURRING,
            //                 'deadline': new Date(2014, 7, 21, 12, 0),
            //                 'frequencyDist': 0,
            //                 'frequencyTimeUnit': TimeUnit.DAY,
            //                 'possibleTimeperiods': [
            //                         { 'startTime': new Date(2014, 8, 1, 18, 30), 'durationInMins': 150 },
            //                         { 'startTime': new Date(2014, 8, 2, 18, 30), 'durationInMins': 150 }
            //                     ],
            //                 'determinedTimeperiod': { 'startTime': undefined, 'durationInMins': 0 }
            //             }
            //         },
            //         {   'oid': 3,
            //             'isHost': false,
            //             'isIgnored': false,
            //             'survey': {
            //                 'name': 'Meeting',
            //                 'description': 'Unser monatliches Geschäftsessen. Dresscode: Bussiness casual.',
            //                 'type': Type.RECURRING,
            //                 'deadline': new Date(2014, 7, 31, 8, 0),
            //                 'frequencyDist': 0,
            //                 'frequencyTimeUnit': TimeUnit.MONTH,
            //                 'possibleTimeperiods': [],
            //                 'determinedTimeperiod': { 'startTime': undefined, 'durationInMins': 0 }
            //             }
            //         }
            //     ];
            // };

            return (Invite);
        }
    ]);