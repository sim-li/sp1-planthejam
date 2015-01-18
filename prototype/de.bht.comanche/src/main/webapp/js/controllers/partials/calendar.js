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

            //--

            // <-- Datumskonvertierung hier für Input ***

            $scope.possibleTimePeriods = $scope.possibleTimePeriods || [];
            $scope.resultingTimePeriods = [];

            // placeholder all events for rendering ui-calendar
            $scope.uiTimePeriods = [];
            // copy all elements of possibleTimePeriods to uiTimePeriods
            for (var i = 0; i < $scope.possibleTimePeriods.length; i++) {
                $scope.uiTimePeriods.push({
                    id: 'possible',
                    rendering: 'background',
                    start: $scope.possibleTimePeriods[i].start,
                    end: $scope.possibleTimePeriods[i].end
                });
            }
            // for (var j = 0, len = $scope.uiTimePeriods.length; j < len; j++) {
            //     $log.log($scope.uiTimePeriods[j])
            // };

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

                        // <-- Datumskonvertierung hier für Output ***
                        $scope.resultingTimePeriods.push({
                            start: start,
                            end: end
                        });
                        $scope.uiTimePeriods.push({
                            start: start,
                            end: end,
                            editable: true,
                            durationEditable: true,
                            constraint: 'possible'
                        });

                        // var start = $scope.resultingTimePeriods[$scope.resultingTimePeriods.length - 1].start;
                        // var end = $scope.resultingTimePeriods[$scope.resultingTimePeriods.length - 1].end;
                        // $log.log(moment(end).diff(moment(start), 'minutes'))


                    },
                    selectConstraint: $scope.possibleTimePeriods.length ? 'possible' : null,
                    events: $scope.uiTimePeriods
                }
            };

            $scope.eventSources = [];

        }
    ]);