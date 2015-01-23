angular.module('myApp')
    .controller('calendarCtrl', ['$scope', '$modal', '$log', 'arrayUtil', 'TimePeriod',

        function($scope, $modal, $log, arrayUtil, TimePeriod) {

            'use strict';

            $scope.possibleTimePeriods = $scope.possibleTimePeriods || [];
            // $scope.resultingTimePeriods = $scope.resultingTimePeriods || [];

            // placeholder for all events for rendering ui-calendar
            $scope.eventSources = [];
            // copy all elements of possibleTimePeriods to eventSources and convert durationMins to end
            arrayUtil.forEach($scope.possibleTimePeriods, function(timePeriod) {
                $scope.eventSources.push({
                    id: 'possible',
                    rendering: 'background',
                    // start: timePeriod.startTime,
                    // end: moment(timePeriod.startTime).add(timePeriod.durationMins, 'minutes').toDate()
                    start: moment(timePeriod.startTime),
                    end: moment(timePeriod.startTime).add(timePeriod.durationMins, 'minutes')
                });
            });

            /* config object */
            $scope.uiConfig = {
                calendar: {
                    height: 550,
                    defaultView: 'agendaWeek',
                    defaultDate: new Date(),
                    header: {
                        left: 'month,agendaWeek,agendaDay',
                        center: 'title',
                        right: 'today prev,next'
                    },
                    selectable: true,
                    select: function(startDate, endDate, jsEvent, view) {
                        /* correct timezoneoffset */
                        var timeZoneOffset = new Date().getTimezoneOffset();
                        // var start = new Date(startDate + timeZoneOffset * 60000);
                        // var end = new Date(endDate + timeZoneOffset * 60000);

                        $scope.eventSources.push({
                            // start: startDate.zone(timeZoneOffset),
                            // end: endDate.zone(timeZoneOffset),
                            start: startDate,
                            end: endDate,
                            editable: true,
                            durationEditable: true,
                            constraint: 'possible'
                        });

                        // $scope.resultingTimePeriods.push(new TimePeriod({
                        //     startTime: startDate.toDate(),
                        //     durationMins: endDate.diff(startDate, 'minutes')
                        // }));

                        $log.debug('----------')
                        $log.debug('- debug calendar -')
                            // $log.debug($scope.test.survey.name)
                        $log.debug('possible : ', $scope.possibleTimePeriods)
                        $log.debug('ui       : ', $scope.eventSources)
                        $log.debug('resulting: ', $scope.resultingTimePeriods)
                        $log.debug('----------')

                    },
                    eventResize: function(event, delta, reverFunc, jsEvent, ui, view) {
                        var ele = arrayUtil.findByAttribute($scope.eventSources, 'id', event.id);
                        ele.end = event.end;
                    },
                    eventDrop: function(event, delta, revertFunc, jsEvent, ui, view) {
                        var ele = arrayUtil.findByAttribute($scope.eventSources, 'id', event.id);
                        ele.start = event.start;
                        ele.end = event.end;
                    },
                    selectConstraint: $scope.eventSources.length > 0 ? 'possible' : null,
                    // selectConstraint: 'possible',
                    events: $scope.eventSources
                }

            };
        }
    ]);