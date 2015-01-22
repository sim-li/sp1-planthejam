describe('util', function() {

    'use strict';

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
            expect(arrayUtil.findByAttribute).toBeDefined();
            expect(arrayUtil.removeByAttribute).toBeDefined();
            expect(arrayUtil.remove).toBeDefined();
            expect(arrayUtil.removeDuplicatesByAttribute).toBeDefined();
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

        describe('findByAttribute', function() {

            var arr = [{
                id: '#1',
                name: 'foo'
            }, {
                id: '#2',
                name: 'bar'
            }, {
                id: '#3',
                name: 'bar'
            }];

            it('should return the first array element with the specified attribute matching the specified value', function() {
                var actual = arrayUtil.findByAttribute(arr, 'name', 'bar');
                expect(actual).toEqual({
                    id: '#2',
                    name: 'bar'
                });
            });

            it('should return undefined if no element with the specified attribute matches the specified value', function() {
                var actual = arrayUtil.findByAttribute(arr, 'name', 'baz');
                expect(actual).toBeUndefined();
            });

            it('should return undefined for an inexistent attribute', function() {
                var actual = arrayUtil.findByAttribute(arr, 'inexistent', 'foo');
                expect(actual).toBeUndefined();
            });

        });

        describe('removeByAttribute', function() {

            it('should remove the first element with an attribute matching the specified value from the array', function() {
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
                    expected = [{
                        id: '#1',
                        name: 'foo'
                    }, {
                        id: '#3',
                        name: 'bar'
                    }];
                var returned = arrayUtil.removeByAttribute(arr, 'name', 'bar');
                expect(arr).toEqual(expected);
                expect(returned).toEqual(expected);
            });

            it('should not change an array with no attribute matching the specified value', function() {
                var arr = [{
                        id: '#1',
                        name: 'foo'
                    }],
                    expected = [{
                        id: '#1',
                        name: 'foo'
                    }];
                var returned = arrayUtil.removeByAttribute(arr, 'name', 'bar');
                expect(arr).toEqual(expected);
                expect(returned).toEqual(expected);
            });

            it('should not change an array for an inexistent attribute', function() {
                var arr = [{
                        id: '#1',
                        name: 'foo'
                    }],
                    expected = [{
                        id: '#1',
                        name: 'foo'
                    }];
                var returned = arrayUtil.removeByAttribute(arr, 'inexistent', 'foo');
                expect(arr).toEqual(expected);
                expect(returned).toEqual(expected);
            });

        });

        describe('remove', function() {

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
                var returned = arrayUtil.remove(arr, ele);
                expect(arr).toEqual(expected);
                expect(returned).toEqual(expected);
            });

            it('should not change an array containing no element equal to the specified object', function() {
                var arr = [{
                        id: '#1',
                        name: 'foo'
                    }],
                    ele = {
                        id: '#2',
                        name: 'bar'
                    },
                    expected = [{
                        id: '#1',
                        name: 'foo'
                    }];
                var returned = arrayUtil.remove(arr, ele);
                expect(arr).toEqual(expected);
                expect(returned).toEqual(expected);
            });

        });

        describe('contains', function() {

            var arr = [{
                id: '#1',
                name: 'foo'
            }, {
                id: '#2',
                name: 'bar'
            }];

            it('should return true if the array contains an object equal to the specified object', function() {
                var ele = {
                    id: '#2',
                    name: 'bar'
                };
                expect(arrayUtil.contains(arr, ele)).toBe(true);
            });

            it('should return false if the array does not contain an object equal to the specified object', function() {
                var ele = {
                    id: '#3',
                    name: 'baz'
                };
                expect(arrayUtil.contains(arr, ele)).toBe(false);
            });

        });

        describe('removeDuplicatesByAttribute', function() {

            it('should remove all duplicate elements for the specified attribute', function() {
                var arr = [{
                        id: '#1',
                        name: 'foo'
                    }, {
                        id: '#2',
                        name: 'bar'
                    }, {
                        id: '#3',
                        name: 'foo'
                    }, {
                        id: '#4',
                        name: 'baz'
                    }, {
                        id: '#5',
                        name: 'baz'
                    }, {
                        id: '#6',
                        name: 'fum'
                    }],
                    expected = [{
                        id: '#1',
                        name: 'foo'
                    }, {
                        id: '#2',
                        name: 'bar'
                    }, {
                        id: '#4',
                        name: 'baz'
                    }, {
                        id: '#6',
                        name: 'fum'
                    }];
                var returned = arrayUtil.removeDuplicatesByAttribute(arr, 'name');
                expect(arr).toEqual(expected);
                expect(returned).toEqual(expected);
            });


            it('should not change an array with no duplicates', function() {
                var arr = [{
                        id: '#1',
                        name: 'foo'
                    }, {
                        id: '#2',
                        name: 'bar'
                    }, {
                        id: '#3',
                        name: 'baz'
                    }],
                    expected = [{
                        id: '#1',
                        name: 'foo'
                    }, {
                        id: '#2',
                        name: 'bar'
                    }, {
                        id: '#3',
                        name: 'baz'
                    }];
                var returned = arrayUtil.removeDuplicatesByAttribute(arr, 'name');
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