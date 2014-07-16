/*
 * Ojektverwaltung-UI, SP1 SoSe 2014, Team: Comanche
 * (C)opyright Sebastian Dassé, Mat.-Nr. 791537, s50602@beuth-hochschule.de
 * 
 * Module: invite
 */


"use strict";

angular.module("invite", ["survey"])
    .factory("Invite", ["Survey", function(Survey) {
        
        var Invite = function(config) {
            config = config || {};
            this.survey = config.survey || new Survey();
        };

        Invite.prototype.convertDatesToDatePickerDate = function() {
            this.survey.convertDatesToDatePickerDate();
        }

        Invite.forInvitesConvertDatesToDatePickerDate = function(invites) {
            if (!invites) {
                return invites;
            }
            for (var i = 0; i < invites.length; i++) {
                invites[i].convertDatesToDatePickerDate();
            }
            return invites;
        }
        
        return (Invite);
    }]);
