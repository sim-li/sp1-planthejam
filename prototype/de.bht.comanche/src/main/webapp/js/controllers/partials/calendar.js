angular.module('myApp')
    .controller('calendarCtrl', ['$scope', '$modal', '$log',

        function($scope, $modal, $log) {

            'use strict';

            // dummy possible TimePeriods for test
            $scope.possibleTimePeriods = [{
                start: '2014-11-10T11:00:00',
                end: '2014-11-10T13:00:00'
            }, {
                start: '2014-11-11T05:00:00',
                end: '2014-11-11T09:00:00'
            }, {
                start: '2014-11-13T10:00:00',
                end: '2014-11-13T16:00:00'
            }];

            $scope.resultingTimePeriods = [];

            // placeholder all events for rendering ui-calendar
            $scope.backgroundTimePeriods = [];
            // copy all elements of possibleTimePeriods to backgroundTimePeriods
            for (var i = 0; i < $scope.possibleTimePeriods.length; i++) {
                $scope.backgroundTimePeriods.push({
                    id: 'possible',
                    rendering: 'background',
                    start: $scope.possibleTimePeriods[i].start,
                    end: $scope.possibleTimePeriods[i].end
                });
            }

            /* config object */
            $scope.uiConfig = {
                calendar: {
                    height: 550,
                    defaultView: 'agendaWeek',
                    defaultDate: '2014-11-12',
                    header: {
                        left: 'month,agendaWeek,agendaDay',
                        center: 'title',
                        right: 'today prev,next'
                    },
                    selectable: true,
                    select: function(startDate, endDate) {
                        /* correct timezoneoffset */
                        var timeZoneOffset = new Date().getTimezoneOffset();
                        var start = new Date(startDate + timeZoneOffset * 60000);
                        var end = new Date(endDate + timeZoneOffset * 60000);

                        $scope.resultingTimePeriods.push({
                            start: start,
                            end: end
                        });
                        $scope.backgroundTimePeriods.push({
                            start: start,
                            end: end,
                            editable: true,
                            durationEditable: true,
                            constraint: 'possible'
                        });
                    },
                    selectConstraint: 'possible',
                    events: $scope.backgroundTimePeriods
                }
            };

            $scope.eventSources = [];

        }
    ]);