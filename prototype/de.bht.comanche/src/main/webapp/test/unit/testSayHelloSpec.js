'use strict';

/* write jasmine specs for controllers, services etc. like this */
describe('Hello Test', function() {

    var sayHello = function() {
        return 'hello';
    };

    it('should say hello', function() {
        expect(sayHello()).toBe('hello');
    });
});