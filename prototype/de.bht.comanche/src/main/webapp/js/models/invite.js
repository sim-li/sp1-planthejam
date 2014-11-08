/*
 * Softwareprojekt SoSe/WiSe 2014, Team: Comanche
 * (C)opyright Sebastian Dassé, Mat.-Nr. 791537, s50602@beuth-hochschule.de
 * 
 * Module: invite
 */


"use strict";

angular.module("invite", ["survey"])
    .factory("Invite", ["Survey", "DatePickerDate", "TimeUnit", "Type", function(Survey, DatePickerDate, TimeUnit, Type) {
        
        var Invite = function(config) {
            config = config || {};
            this.oid = config.oid || "";
            this.ignored = config.ignored;
            this.host = config.host;
            this.survey = new Survey(config.survey) || new Survey();
        };

        // TODO if possible, simplify like this:  var that = this; that.user = { ... }; return that;
        Invite.prototype.export = function(user) {
            console.log("OIDS? ", this.oid, this.survey.oid);
            return {   
                    // "isHost": true,         // muss per GUI gesetzt werden <<------------------------ FIXME
                    // // "isIgnored": false,     //                             <<------------------------
                    // // "host": true,         // muss per GUI gesetzt werden <<------------------------ FIXME
                    // // "ignored": false,     //                             <<------------------------
                "oid": this.oid, 
                "ignored": this.ignored, 
                "host": this.host, 
                "user": {
                    "oid": user.oid, 
                    "name": user.name
                }, 
                "survey": {
                    "oid": this.survey.oid, 
                    "name": this.survey.name, 
                    "description": this.survey.description, 
                    "type": this.survey.type, 
                    "deadline": this.survey.deadline.toDate(), 
                    "frequencyDist": this.survey.frequencyDist, 
                    "frequencyTimeUnit": this.survey.frequencyTimeUnit, 
                    // "possibleTimeperiods": _survey.possibleTimeperiods, 
                    // "determinedTimeperiod": _survey.determinedTimeperiod 
                }
            };
        };

        Invite.prototype.convertDatesToDatePickerDate = function() {
            this.survey.convertDatesToDatePickerDate();
        };

        Invite.forInvitesConvertFromRawInvites = function(rawInvites) {
            if (!rawInvites) {
                return rawInvites;
            }
            var _invites = [];
            for (var i = 0; i < rawInvites.length; i++) {
                var _invite = new Invite(rawInvites[i]);
                _invites.push(_invite);
                
                // console.log(new DatePickerDate(_invite.survey.deadline));
                // console.log(_invite.survey);
                // // rawInvites[i].convertDatesToDatePickerDate();
            }
            return _invites;
        };
/*
        Invite.getDummyInviteList = function() {
            return [
                {   "oid": 1, 
                    "isHost": false, 
                    "isIgnored": false, 
                    "survey": {
                        "name": "Bandprobe", 
                        "description": "Wir m�ssen vor dem Konzert Ende des Monats mindestens noch einmal proben. Wann k�nnt ihr?", 
                        "type": Type.ONE_TIME, // or "RECURRING" <<enumeration>> = einmalig oder wiederholt
                        // "deadline": "10.07.2014, 23:55", // <<datatype>> date = Zeipunkt
                        "deadline": new Date(2014, 7, 10, 23, 55), // <<datatype>> date = Zeipunkt
                        "frequencyDist": 0, // <<datatype>> iteration = Wiederholung
                        "frequencyTimeUnit": TimeUnit.WEEK, // <<datatype>> iteration = Wiederholung
                        "possibleTimeperiods": [
                                { "startTime": new Date(2014, 7, 11, 19, 0), "durationInMins": 120 }, // <<datatype>> <timeperiod> = List<Zeitraum>
                                { "startTime": new Date(2014, 7, 12, 20, 0), "durationInMins": 120 }, 
                                { "startTime": new Date(2014, 7, 18, 19, 30), "durationInMins": 120 } 
                            ], 
                        "determinedTimeperiod": { "startTime": new Date(2014, 7, 12, 20, 0), "durationInMins": 120 } // <<datatype>> timeperiod = Zeitraum
                    }
                }, 
                {   "oid": 2, 
                    "isHost": false, 
                    "isIgnored": false, 
                    "survey": {
                        "name": "Chorprobe", 
                        "description": "Wir beginnen mit der Mozart-Messe in c-moll. In der Pause gibt es Kuchen im Garten.", 
                        "type": Type.RECURRING, 
                        "deadline": new Date(2014, 7, 21, 12, 0),
                        "frequencyDist": 0, 
                        "frequencyTimeUnit": TimeUnit.DAY,
                        "possibleTimeperiods": [
                                { "startTime": new Date(2014, 8, 1, 18, 30), "durationInMins": 150 },
                                { "startTime": new Date(2014, 8, 2, 18, 30), "durationInMins": 150 } 
                            ], 
                        "determinedTimeperiod": { "startTime": undefined, "durationInMins": 0 }
                    }
                }, 
                {   "oid": 3, 
                    "isHost": false, 
                    "isIgnored": false, 
                    "survey": {
                        "name": "Meeting", 
                        "description": "Unser monatliches Gesch�ftsessen. Dresscode: Bussiness casual.", 
                        "type": Type.RECURRING, 
                        "deadline": new Date(2014, 7, 31, 8, 0),
                        "frequencyDist": 0, 
                        "frequencyTimeUnit": TimeUnit.MONTH,
                        "possibleTimeperiods": [], 
                        "determinedTimeperiod": { "startTime": undefined, "durationInMins": 0 }
                    }
                }
            ];
        };
        */
        return (Invite);
    }]);
