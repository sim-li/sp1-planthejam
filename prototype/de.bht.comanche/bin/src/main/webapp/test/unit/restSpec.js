describe('rest module', function() {

    'use strict';

    // beforeEach(module('rest'));

    // var $httpBackend, $rootScope, createController, authRequestHandler;
    var $httpBackend, $scope, authRequestHandler, alice;

    beforeEach(function() {
        module('rest');
        module('models');
    });

    // beforeEach(inject(function($injector) {
    beforeEach(inject(function($injector, $controller, $rootScope, User) {
        $httpBackend = $injector.get('$httpBackend');

        alice = new User({
            'oid': 1,
            'name': 'Alice',
            'tel': '321321321',
            'email': 'alice@wonderlan.de',
            'password': 'testtest',
            'iconurl': 'http://www.gravatar.com/avatar/f561d577cbf0ef4d7ba4af240d924d8f?d=retro',
            'generalAvailability': [],
            'messages': []
        });

        $scope = $rootScope.$new();

        // ctrl = $controller('MyCtrl', {
        //     $scope: $scope
        // });
        // $rootScope = $injector.get('$rootScope');
        // var $controller = $injector.get('$controller');
        // $controller = $controller("MyCtrl", {
        //     $scope: $rootScope
        // });

        // createController = function() {
        //     return $controller('MyController', {
        //         '$scope': $rootScope
        //     });
        // };


    }));

    afterEach(function() {
        $httpBackend.verifyNoOutstandingExpectation();
        $httpBackend.verifyNoOutstandingRequest();
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

    it('should login', inject(function($http, restService, User) {
        $httpBackend.when('POST', '/rest/user/login').respond(alice);
        $httpBackend.expectPOST('/rest/user/login');
        restService.login(alice).then(function(success) {
            expect(success.oid).toBeDefined();
            expect(success.name).toEqual(alice.name);
            expect(success.email).toEqual(alice.email);
        });
        $httpBackend.flush();
    }));

    it('should register', inject(function(restService, User) {
        $httpBackend.when('POST', '/rest/user/register').respond(alice);
        $httpBackend.expectPOST('/rest/user/register');
        restService.register(new User(alice)).then(function(success) {
            expect(success.oid).toBeDefined();
            expect(success.name).toEqual(alice.name);
            expect(success.email).toEqual(alice.email);
        });
        $httpBackend.flush();
    }));

    it('should logout', inject(function(restService, User) {
        $httpBackend.when('POST', '/rest/user/logout')
            .respond(204, null);
        $httpBackend.expectPOST('/rest/user/logout');
        restService.logout().then(function(success) {
            expect(success).toBe(null);
        });
        $httpBackend.flush();
    }));

    it('should deleteUser', inject(function(restService, User) {
        $httpBackend.when('DELETE', '/rest/user/delete')
            .respond(200, null);
        $httpBackend.expectDELETE('/rest/user/delete');
        restService.deleteUser(alice).then(function(success) {
            expect(success).toBe(null);
        });
        $httpBackend.flush();
    }));

    it('should updateUser', inject(function(restService, User) {
        var update = new User(alice);
        update.email = "alice@cooper.com";
        update.tel = 666;
        $httpBackend.when('POST', '/rest/user/update')
            .respond(200, update);
        $httpBackend.expectPOST('/rest/user/update');
        restService.updateUser(alice).then(function(success) {
            // expect(success.oid).toBe(update.oid);
            expect(success.name).toBe(update.name);
            expect(success.email).toBe(update.email);
            expect(success.tel).toBe(update.tel);
        });
        $httpBackend.flush();
    }));


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