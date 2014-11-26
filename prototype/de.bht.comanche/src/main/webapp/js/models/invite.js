/*
 * Softwareprojekt SoSe/WiSe 2014, Team: Comanche
 * (C)opyright Sebastian Dassé, Mat.-Nr. 791537, s50602@beuth-hochschule.de
 *
 * Module: invite
 */

'use strict';

angular.module('invite', ['survey'])
    .factory('Invite', ['Survey', 'DatePickerDate', 'TimeUnit', 'Type', function(Survey, DatePickerDate, TimeUnit, Type) {

        var Invite = function(config) {
            if (!(this instanceof Invite)) {
                return new Invite(config);
            }
            config = config || {};
            // this.user = config.user || {
            //     oid: 1
            // }
            this.oid = config.oid || '';
            this.ignored = config.ignored;
            this.host = config.host;
            this.survey = new Survey(config.survey);
        };

        Invite.prototype.getModelId = function() {
            return 'invite';
        };

        Invite.prototype.export = function() {
            return {
                'oid': this.oid,
                'ignored': this.ignored,
                'host': this.host,
                // 'user': this.user,
                'survey': this.survey.export()
            };
        };

        Invite.importMany = function(rawInvites) {
            if (!rawInvites) {
                return rawInvites;
            }
            var invites = [];
            for (var i = 0; i < rawInvites.length; i++) {
                invites.push(new Invite(rawInvites[i]));
            }
            return invites;
        };


        Invite.prototype.convertDatesToDatePickerDate = function() {
            this.survey.convertDatesToDatePickerDate();
        };

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
    }]);