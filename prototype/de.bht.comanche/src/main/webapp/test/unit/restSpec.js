describe('restModule', function() {

    'use strict';

    // beforeEach(module('rest'));

    beforeEach(function() {
        module('rest');
        module('models');
    });

    describe('restService', function() {

        it('should should exist', inject(function(restService) {
            expect(restService).toBeDefined();
            expect(restService.login).toBeDefined();
            expect(restService.register).toBeDefined();
            expect(restService.deleteUser).toBeDefined();
            expect(restService.updateUser).toBeDefined();
            expect(restService.logout).toBeDefined();
            expect(restService.getAllUsers).toBeDefined();
            expect(restService.getSurveyInvites).toBeDefined();
            expect(restService.getMessages).toBeDefined();
            expect(restService.notifyParticipants).toBeDefined();
            expect(restService.doGetMany).toBeDefined();
            expect(restService.doGet).toBeDefined();
            expect(restService.doSave).toBeDefined();
            expect(restService.doDelete).toBeDefined();
        }));

    });

    describe('login', function() {});

    describe('register', function() {});

    describe('deleteUser', function() {});

    describe('updateUser', function() {});

    describe('logout', function() {});

    describe('getAllUsers', function() {});

    describe('getSurveyInvites', function() {});

    describe('getMessages', function() {});

    describe('notifyParticipants', function() {});

    describe('doGetMany', function() {});

    describe('doGet', function() {});

    describe('doSave', function() {});

    describe('doDelete', function() {});

});