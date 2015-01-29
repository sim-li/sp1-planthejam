/**
 * @module myApp
 *
 * @author Sebastian Dass&eacute;
 */
angular.module('myApp')
    /**
     * The controller for the calendar directive view.
     *
     * @class calendarCtrl
     */
    .controller('calendarCtrl', ['$scope', '$log', 'arrayUtil', 'TimePeriod',
        function($scope, $log, arrayUtil, TimePeriod) {

            'use strict';

            /** The calendar uses Moment.js, which provides some operations for date manipulation --> http://momentjs.com */

            // an optional list for constraining the timePeriods that can be generated
            $scope.allowedTimePeriods = $scope.allowedTimePeriods || [];
            $scope.resultingTimePeriods = $scope.resultingTimePeriods || []; //
            $scope.eventSources = []; // placeholder for all events for rendering ui-calendar

            /** must be called to keep the resulting time periods in sync with the edits done in the calendar */
            var refreshResultingTimePeriods = function() {
                $scope.resultingTimePeriods = [];
                arrayUtil.forEach($scope.eventSources, function(event) {
                    if (event.id != 'allowed') {
                        $scope.resultingTimePeriods.push(new TimePeriod({
                            startTime: event.start.toDate(),
                            endTime: event.end.toDate()
                        }));
                    }
                });
                $log.debug('refreshResultingTimePeriods: ', $scope.resultingTimePeriods);
            };

            /** required for the calendar */
            var generateUniqueId = function() {
                var time = new Date().getTime();
                while (time == new Date().getTime());
                return '#' + new Date().getTime();
            };

            /** constrains the input of time periods if the list of allowed time periods is not empty */
            var autoConstrain = $scope.allowedTimePeriods.length > 0 ? 'allowed' : null;
            $log.debug('autoConstrain: ', $scope.allowedTimePeriods);

            /** copy all elements of allowedTimePeriods and existing resultingTimePeriods to eventSources and convert tothe calendar date format */
            arrayUtil.forEach($scope.allowedTimePeriods, function(timePeriod) {
                $scope.eventSources.push({
                    id: 'allowed',
                    start: moment(timePeriod.startTime),
                    end: moment(timePeriod.endTime),
                    rendering: 'background'
                });
            });
            arrayUtil.forEach($scope.resultingTimePeriods, function(timePeriod) {
                $scope.eventSources.push({
                    id: generateUniqueId(),
                    start: moment(timePeriod.startTime),
                    end: moment(timePeriod.endTime),
                    editable: true,
                    durationEditable: true,
                    constraint: autoConstrain
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
                    timezone: 'local',
                    selectable: true,
                    select: function(startDate, endDate, jsEvent, view) {

                        $scope.eventSources.push({
                            id: generateUniqueId(),
                            start: startDate,
                            end: endDate,
                            editable: true,
                            durationEditable: true,
                            constraint: autoConstrain
                        });
                        refreshResultingTimePeriods();
                    },
                    eventResize: function(event, delta, reverFunc, jsEvent, ui, view) {
                        $log.debug('resize: ', event);
                        var ele = arrayUtil.findByAttribute($scope.eventSources, 'id', event.id);
                        ele.end = event.end;
                        refreshResultingTimePeriods();
                    },
                    eventDrop: function(event, delta, revertFunc, jsEvent, ui, view) {
                        $log.debug('drop  : ', event);
                        var ele = arrayUtil.findByAttribute($scope.eventSources, 'id', event.id);
                        ele.start = event.start;
                        ele.end = event.end;
                        refreshResultingTimePeriods();
                    },
                    eventClick: function(event, jsEvent, view) {
                        $log.debug('click : ', event);
                        arrayUtil.removeByAttribute($scope.eventSources, 'id', event.id);
                        refreshResultingTimePeriods();
                    },
                    selectConstraint: autoConstrain,
                    events: $scope.eventSources
                }

            };
        }
    ]);