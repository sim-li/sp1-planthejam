'use strict';
describe('Ui Utils Spec', function() {
    var StateSwitcher;
    var testOffSpy;
    var testOnSpy;
    var condition;

    beforeEach(module('uiUtilsModule'));

    beforeEach(inject(function(_StateSwitcher_)  {
        StateSwitcher = _StateSwitcher_;
        testOffSpy = jasmine.createSpy('testOffSpy');
        testOnSpy = jasmine.createSpy('testOnSpy');
        condition = function() {
            return true;
        }
    }));

    it('should have stateSwitcher module defined', function() {
        expect(StateSwitcher).toBeDefined();
    });


    describe('executeAllActions', function() {
        it('should execute all queued actions', function()  {
            var stateSwitcher = new StateSwitcher({});
            var action1 = jasmine.createSpy('actionSpy1');
            var action2 = jasmine.createSpy('actionSpy2');
            var action3 = jasmine.createSpy('actionSpy3');
            var actions = [
                action1,
                action2,
                action3
            ];
            stateSwitcher.executeAllActions(actions);
            expect(action1).toHaveBeenCalled();
            expect(action2).toHaveBeenCalled();
            expect(action3).toHaveBeenCalled();
        });
    });

    describe('A stateSwitcher with actions and conditions', function() {
        var stateSwitcher;
        beforeEach(function() {
            stateSwitcher = new StateSwitcher({
                'switchOffAction': testOffSpy,
                'switchOnAction': testOnSpy,
                'condition': condition
            });
        });

        it('should have all test vars defined', function() {
            expect(testOffSpy).toBeDefined();
            expect(testOnSpy).toBeDefined();
            expect(condition).toBeDefined();
            expect(stateSwitcher).toBeDefined();
        });

        describe('toggle', function() {
            it('should change on and off state correctly on multiple executions', function() {
                stateSwitcher.on();
                expect(stateSwitcher.isOn).toEqual(true);
                stateSwitcher.toggle();
                expect(stateSwitcher.isOn).toEqual(false);
                stateSwitcher.toggle();
                expect(stateSwitcher.isOn).toEqual(true);
                stateSwitcher.toggle();
                expect(stateSwitcher.isOn).toEqual(false);
            });

        });

        describe('off', function() {
            it('should change stateSwitcher to off state', function() {
                stateSwitcher.off();
                expect(testOffSpy).toHaveBeenCalled();
            });

            it('should change stateSwitcher to on state', function() {
                stateSwitcher.off();
                expect(stateSwitcher.isOn).toEqual(false);
            });
        });

        describe('on', function() {
            it('should change stateSwitcher to on state when condition is complied', function() {
                stateSwitcher.on();
                expect(testOnSpy).toHaveBeenCalled();
            });

            it('should not change stateSwitcher to on state when condition is not complied', function() {
                spyOn(stateSwitcher, 'condition').and.callFake(function()  {
                    return false;
                });
                stateSwitcher.on();
                expect(testOnSpy.calls.any()).toEqual(false);
            });
        });
    });

    describe('An bound stateSwitcher, with all parameters initialized', function() {
        var testBindVariable;
        var stateSwitcher;

        beforeEach(function() {
            testBindVariable = true;
            stateSwitcher = new StateSwitcher({
                'switchOffAction': testOffSpy,
                'switchOnAction': testOnSpy,
                'condition': condition,
                'defaultValue': testBindVariable,
                'onValueChange': function(newValue) {
                    testBindVariable = newValue;
                }
            });
        });

        it('should have all test vars defined', function() {
            expect(testOffSpy).toBeDefined();
            expect(testOnSpy).toBeDefined();
            expect(condition).toBeDefined();
            expect(testBindVariable).toBeDefined();
            expect(stateSwitcher).toBeDefined();
            expect(testBindVariable).toBeDefined();
        });

        describe('off', function() {
            it('should set testBindVariable when switching off', function() {
                stateSwitcher.off();
                expect(testBindVariable).toEqual(false);
            });
        });

        describe('on', function() {
            it('should set state by testBindVariable', function() {
                expect(stateSwitcher.isOn).toEqual(true);
            });
        });

        it('should write back isOn value to testBindVariable when switching on', function() {
            stateSwitcher.on();
            expect(testBindVariable).toEqual(true);
        });
    });

});