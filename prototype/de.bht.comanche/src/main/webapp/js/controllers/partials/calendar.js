angular.module('myApp')
    .controller('calendarCtrl', ['$scope', '$modal', '$log', 'arrayUtil', 'TimePeriod',

        function($scope, $modal, $log, arrayUtil, TimePeriod) {

            'use strict';

            $scope.eventSources = [];

            $scope.possibleTimePeriods = $scope.possibleTimePeriods || [];
            // $scope.resultingTimePeriods = $scope.resultingTimePeriods || [];

            // placeholder all events for rendering ui-calendar
            $scope.uiTimePeriods = [];
            // copy all elements of possibleTimePeriods to uiTimePeriods and convert durationMins to end
            arrayUtil.forEach($scope.possibleTimePeriods, function(timePeriod) {
                $scope.uiTimePeriods.push({
                    id: 'possible',
                    rendering: 'background',
                    start: timePeriod.startTime,
                    end: moment(timePeriod.startTime).add(timePeriod.durationMins, 'minutes').toDate()
                });
            });

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

                        $scope.resultingTimePeriods.push(new TimePeriod({
                            startTime: start,
                            durationMins: moment(end).diff(moment(start), 'minutes')
                        }));
                        $scope.uiTimePeriods.push({
                            start: start,
                            end: end,
                            editable: true,
                            durationEditable: true,
                            constraint: 'possible'
                        });

                        $log.debug('----------')
                        $log.debug('- debug calendar -')
                        $log.debug($scope.test.survey.name)
                            // $log.debug($scope.eventSources)
                            // $log.debug($scope.uiTimePeriods)
                            // $log.debug($scope.possibleTimePeriods)
                            // $log.debug($scope.resultingTimePeriods)
                        $log.debug('----------')

                    },
                    selectConstraint: $scope.possibleTimePeriods.length ? 'possible' : null,
                    events: $scope.uiTimePeriods
                }

            };
        }
    ]);