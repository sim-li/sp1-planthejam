'use strict';

/* jasmine specs for the util module go here */
describe('util', function() {

    var arrayUtil,
        modelUtil;

    beforeEach(module('util'));

    beforeEach(inject(function(_arrayUtil_, _modelUtil_) {
        arrayUtil = _arrayUtil_;
        modelUtil = _modelUtil_;
    }));

    describe('arrayUtil', function() {

        it('should should exist', function() {
            expect(arrayUtil).toBeDefined();
            expect(arrayUtil.forEach).toBeDefined();
            expect(arrayUtil.findByKey).toBeDefined();
            expect(arrayUtil.removeByKey).toBeDefined();
            expect(arrayUtil.removeElement).toBeDefined();
            expect(arrayUtil.removeDuplicatesByKey).toBeDefined();
        });

        describe('forEach', function() {

            it('should execute the callback on every element of the array', function() {
                var arr = ['foo', 'bar', 'baz'],
                    actual = '';
                var callback = function(ele, idx, arr) {
                    actual += idx + ': ' + ele + ' == ' + arr[idx] + '. ';
                };
                arrayUtil.forEach(arr, callback);
                expect(actual).toEqual('0: foo == foo. 1: bar == bar. 2: baz == baz. ');
            });

        });

        describe('findByKey', function() {

            it('should return the first element with a key matching the specified value', function() {
                var arr = [{
                        id: '#1',
                        name: 'foo'
                    }, {
                        id: '#2',
                        name: 'bar'
                    }, {
                        id: '#3',
                        name: 'bar'
                    }],
                    key = 'name',
                    value = 'bar';
                var actual = arrayUtil.findByKey(arr, key, value);
                expect(actual).toEqual({
                    id: '#2',
                    name: 'bar'
                });
            });

        });

        describe('removeByKey', function() {

            it('should remove the first element with a key matching the specified value from the array', function() {
                var arr = [{
                        id: '#1',
                        name: 'foo'
                    }, {
                        id: '#2',
                        name: 'bar'
                    }, {
                        id: '#3',
                        name: 'bar'
                    }],
                    key = 'name',
                    value = 'bar',
                    // removed = {
                    //     id: '#2',
                    //     name: 'bar'
                    // },
                    expected = [{
                        id: '#1',
                        name: 'foo'
                    }, {
                        id: '#3',
                        name: 'bar'
                    }];
                var returned = arrayUtil.removeByKey(arr, key, value);
                // expect(arr).not.toContain(removed);
                // expect(returned).not.toContain(removed);
                expect(arr).toEqual(expected);
                expect(returned).toEqual(expected);
            });

        });

        describe('removeElement', function() {

            it('should remove the first element equal to the specified object from the array', function() {
                var arr = [{
                        id: '#1',
                        name: 'foo'
                    }, {
                        id: '#2',
                        name: 'bar'
                    }, {
                        id: '#3',
                        name: 'baz'
                    }, {
                        id: '#1',
                        name: 'foo'
                    }],
                    ele = {
                        id: '#1',
                        name: 'foo'
                    },
                    expected = [{
                        id: '#2',
                        name: 'bar'
                    }, {
                        id: '#3',
                        name: 'baz'
                    }, {
                        id: '#1',
                        name: 'foo'
                    }];
                var returned = arrayUtil.removeElement(arr, ele);
                expect(arr).toEqual(expected);
                expect(returned).toEqual(expected);
            });

        });

        describe('contains', function() {

            it('should return true if the array contains an object equal to the specified object', function() {
                var arr = [{
                        id: '#1',
                        name: 'foo'
                    }, {
                        id: '#2',
                        name: 'bar'
                    }, {
                        id: '#3',
                        name: 'baz'
                    }, {
                        id: '#1',
                        name: 'foo'
                    }],
                    ele = {
                        id: '#1',
                        name: 'foo'
                    },
                    expected = [{
                        id: '#2',
                        name: 'bar'
                    }, {
                        id: '#3',
                        name: 'baz'
                    }, {
                        id: '#1',
                        name: 'foo'
                    }];
                var returned = arrayUtil.removeElement(arr, ele);
                expect(arr).toEqual(expected);
                expect(returned).toEqual(expected);
            });

        });

    });

    describe('modelUtil', function() {

        it('should should exist', function() {
            expect(modelUtil).toBeDefined();
            expect(modelUtil.areEqual).toBeDefined();
        });

        describe('areEqual', function() {

            it('should return true for two objects with equal values for all attributes', function() {
                var obj1 = {
                        id: '#1',
                        name: 'foo'
                    },
                    obj2 = {
                        id: '#1',
                        name: 'foo'
                    };
                expect(modelUtil.areEqual(obj1, obj2)).toBe(true);
            });

            it('should return false for two objects with at least one unequal attribute', function() {
                var obj1 = {
                        id: '#1',
                        name: 'foo'
                    },
                    obj2 = {
                        id: '#2',
                        name: 'foo'
                    };
                expect(modelUtil.areEqual(obj1, obj2)).toBe(false);
            });

        });


    });

});