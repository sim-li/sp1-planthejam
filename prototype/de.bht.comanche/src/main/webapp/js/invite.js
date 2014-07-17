/*
 * Ojektverwaltung-UI, SP1 SoSe 2014, Team: Comanche
 * (C)opyright Sebastian Dassé, Mat.-Nr. 791537, s50602@beuth-hochschule.de
 * 
 * Module: invite
 */


"use strict";

angular.module("invite", ["survey"])
    .factory("Invite", ["Survey", "DatePickerDate", function(Survey, DatePickerDate) {
        
        var Invite = function(config) {
            config = config || {};
            this.oid = config.oid || "";
            this.ignored = config.ignored;
            this.host = config.host;
            this.survey = new Survey(config.survey) || new Survey();
        };

        Invite.prototype.convertDatesToDatePickerDate = function() {
            this.survey.convertDatesToDatePickerDate();
        }

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
        }
        
        return (Invite);
    }]);
