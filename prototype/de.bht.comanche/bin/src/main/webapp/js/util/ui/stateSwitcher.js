'use strict';

angular.module('uiUtilsModule', [])
    .factory('StateSwitcher', ['$log', function($log) {
        var StateSwitcher = function(config) {
            this.onValueChange = config.onValueChange || function() {};
            this.isOn = config.defaultValue || false;
            var self = this;

            this.actions = {
                'off':  [
                    function() {
                        self.isOn = false;
                    },
                    function() {
                        self.onValueChange(false);
                    }
                ],
                'on':  [
                    function() {
                        self.isOn = true;
                    },
                    function() {
                        self.onValueChange(true);
                    }
                ]
            }
            this.actions.off.push(config.switchOffAction ? config.switchOffAction : function() {});
            this.actions.on.push(config.switchOnAction ? config.switchOnAction : function() {});
            this.condition = config.condition || function() {};
        }

        StateSwitcher.prototype.toggle = function()  {
            if (!this.isOn && this.condition()) {
                this.on();
                return;
            }
            this.off();
        }

        StateSwitcher.prototype.off = function()  {
            this.executeAllActions(this.actions.off);
        }

        StateSwitcher.prototype.on = function()  {
            if (!this.condition()) {
                return;
            }
            this.executeAllActions(this.actions.on);
        }

    StateSwitcher.prototype.executeAllActions = function(actions) {
            var i = actions.length;
            while (i--) {
                if (typeof actions[i] === 'function') {
                    actions[i]();
                }
            }
        }
        return (StateSwitcher);
    }]);