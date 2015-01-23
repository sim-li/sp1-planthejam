angular.module('myApp')
    .controller('calendarCtrl', ['$scope', '$modal', '$log', 'arrayUtil', 'TimePeriod',

        function($scope, $modal, $log, arrayUtil, TimePeriod) {

            'use strict';

            $scope.eventSources = [];

            $scope.possibleTimePeriods = $scope.possibleTimePeriods || [];
            // $scope.resultingTimePeriods = $scope.resultingTimePeriods || [];

            //-- dummies -->
            // $scope.possibleTimePeriods.push({
            //     startTime: new Date('Jan 23 2015 10:00'),
            //     durationMins: 120
            // });
            // $scope.possibleTimePeriods.push({
            //     startTime: new Date('Jan 24 2015 6:00'),
            //     durationMins: 120
            // });
            // <-----------

            // placeholder all events for rendering ui-calendar
            $scope.uiTimePeriods = [];
            // copy all elements of possibleTimePeriods to uiTimePeriods and convert durationMins to end
            arrayUtil.forEach($scope.possibleTimePeriods, function(timePeriod) {
                $scope.uiTimePeriods.push({
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

                        $scope.uiTimePeriods.push({
                            // start: start,
                            // end: end,
                            // start: startDate.zone(timeZoneOffset),
                            // end: endDate.zone(timeZoneOffset),
                    start: startDate,
                    end: endDate,
                            editable: true,
                            durationEditable: true,
                            constraint: 'possible'
                        });

                        $scope.resultingTimePeriods.push(new TimePeriod({
                            startTime: startDate.toDate(),
                            durationMins: endDate.diff(startDate, 'minutes')
                        }));

                        $log.debug('----------')
                        $log.debug('- debug calendar -')
                            // $log.debug($scope.test.survey.name)
                            // $log.debug($scope.eventSources)
                        $log.debug($scope.possibleTimePeriods)
                        $log.debug($scope.uiTimePeriods)
                        $log.debug($scope.resultingTimePeriods)
                        $log.debug('----------')

                    },
                    eventResize: function(event, delta, reverFunc, jsEvent, ui, view) {
                        console.log(delta)
                        var ele = arrayUtil.findByAttribute($scope.uiTimePeriods, 'id', event.id);
                        console.log(ele.start.toDate())
                        console.log(ele.end.toDate())
                        ele.end = ele.end.add(delta);
                        console.log(ele.start.toDate())
                        console.log(ele.end.toDate())
                        console.log(event)
                    },
                    eventDrop: function(event, delta, revertFunc, jsEvent, ui, view) {
                        // console.log(delta)
                        // console.log(delta.asMinutes())
                        // console.log(event)
                        var ele = arrayUtil.findByAttribute($scope.uiTimePeriods, 'id', event.id);
                        // ele.start = event.start,
                        // ele.end = event.end;
                        /*console.log(event.start.toDate())
                        console.log(ele.start)*/
                        console.log(ele)
                        console.log(event)
                        console.log(ele.start.toDate())
                        console.log(ele.end.toDate())
                        ele.start = ele.start.add(delta);
                        ele.end = ele.end.add(delta);
                        console.log(ele.start.toDate())
                        console.log(ele.end.toDate())

                        // console.log(event.start.add(delta.asMinutes(), 'minutes').toDate())
                        // ele = {
                        //     start: event.start
                        // }
                        // console.log(ele)
                        // console.log($scope.uiTimePeriods)

                        // $scope.uiTimePeriods.push({
                        //     start: start,
                        //     end: end,
                        //     editable: true,
                        //     durationEditable: true,
                        //     constraint: 'possible'
                        // });
                    },
                    selectConstraint: $scope.uiTimePeriods.length > 0 ? 'possible' : null,
                    // selectConstraint: 'possible',
                    events: $scope.uiTimePeriods
                }

            };
        }
    ]);