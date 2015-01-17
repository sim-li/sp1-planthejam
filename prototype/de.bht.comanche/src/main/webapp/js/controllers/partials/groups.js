'use strict';

angular.module('myApp')
    .controller('groupsCtrl', ['$scope', '$modal', '$log', 'restService', 'StateSwitcher', 'arrayUtil', 'Invite',

        function($scope, $modal, $log, restService, StateSwitcher, arrayUtil, Invite) {

            /**
             * Creates a new modal instance and opens it.
             * TODO: Improve doc
             * @param  {[type]} size [description]
             * @return {[type]}      [description]
             */
            $scope.openGroupModal = function() {
                var modalInstance = $modal.open({
                    templateUrl: 'groupsModalContent.html',
                    controller: 'groupsModalCtrl',
                    size: 'lg',
                    resolve: {
                        groups: function() {
                            return $scope.groups;
                        },
                        users: function() {
                            return $scope.users;
                        }
                    }
                });
                modalInstance.result.then(function(selectedItem) {
                    $scope.selected = selectedItem;
                }, function() {
                    $log.info('Modal dismissed at: ' + new Date());
                });
            };

            /**
             * Create simple variables needed to store UI states for components
             * that aren't grouped in directives.
             */
            (function createSimpleUIStateVariables() {
                $scope.panelOpened = false;
            })();

            (function createDataModels() {
                $scope.lastElementSelected = '';
                //IMPL THIS
                //$scope.allElementsSelected = $scope.selectedSurvey.getAllParticipants() ||  []; /////////////////////////////////////////
                $scope.allElementsSelected = [];
                $scope.elements = $scope.users.concat($scope.groups);
                console.log('Got datamodel $scope')
            })();

            $scope.$watch('lastElementSelected', function() {
                addElementToSelection();
            });

            // CURRENTLY not connected to UI ( collapse flag set to FALSE )
            var panelOpener = new StateSwitcher({
                switchOnAction: function() {
                    $scope.panelOpened = true;
                },
                switchOffAction: function() {
                    $scope.panelOpened = false;
                },
                condition: function() {
                    return $scope.allElementsSelected > 0;
                }
            });

            var addElementToSelection = function() {
                if (isNoValidSelection() || isDuplicate()) {
                    return;
                }
                $scope.allElementsSelected.push($scope.lastElementSelected);
                refreshUserListOfSelectedSurvey();
                console.log('Current state of survey (after add-update)', $scope.selectedSurvey);
                // TODO: Implement "switch according to condition in stateswitcher" + add here.
            };

            var refreshUserListOfSelectedSurvey = function() {
                updateParticipantsFromMixedList($scope.allElementsSelected);
            }

            var isNoValidSelection = function() {
                if (!($scope.lastElementSelected && $scope.lastElementSelected.name)) {
                    return true;
                }
                return false;
            };

            var isDuplicate = function() {
                for (var i = 0, len = $scope.allElementsSelected.length; i < len; i++) {
                    if ($scope.allElementsSelected[i] === $scope.lastElementSelected) {
                        return true;
                    }
                }
                return false;
            };

            $scope.removeElementFromSelection = function(index) {
                $scope.allElementsSelected.splice(index, 1);
                console.log('Calling update');
                updateParticipantsFromMixedList($scope.allElementsSelected);
                console.log('Current state of survey (after remove-update)', $scope.selectedSurvey);
            };
            /**
             * ...
             *
             * @method addParticipant
             * @param {User} user the user to be added as participant
             */
            var addParticipant = function(user) {
                $scope.selectedSurvey.invites.push(new Invite({
                    user: user
                }));
            };

            var getAllParticipants = function() {
                var allParticipants = [];
                if (!this.invites) {
                    return;
                }
                arrayUtil.forEach($scope.selectedSurvey.invites, function(invite) {
                    allParticipants.push(invite.user);
                });
                return allParticipants;
            };
            /**
             * ...
             *
             * @method addParticipants
             * @param {Group} group the group, which members shall be added as participants
             */
            var addParticipantsFromGroup = function(group) {
                if (group.modelId !== 'group') {
                    return;
                }

                arrayUtil.forEach(group.members, function(member) {
                    addParticipant(member.user);
                });
            };

            /**
             * Replaces all participants from a list that can contain
             * users or groups.
             *
             * @param  {List} mixedList Mixed list of groups and users
             * @return {[type]}           [description]
             */
            var updateParticipantsFromMixedList = function(mixedList) {
                $scope.selectedSurvey.invites = [];
                arrayUtil.forEach(mixedList, function(element) {
                    switch (element.modelId) {
                        case 'user':
                            addParticipant(element);
                            break;
                        case 'group':
                            addParticipantsFromGroup(element);
                            break;
                        default:
                            console.log('Faulty element in your collection');
                    }
                });
            }

            // TODO Should match users + groups against participants and return mixed list
            var getMixedListFromParticipants = function() {}
        }
    ]);
// Please note that $modalInstance represents a modal window (instance) dependency.
// It is not the same as the $modal service used above.

angular.module('myApp')
    .controller('groupsModalCtrl', function($scope, $modalInstance, groups, users) {
        $scope.test = 'My test!';
        $scope.groups = groups;
        $scope.users = users;
        console.log('GRAV URL', groups);
        $scope.selectedGroup = '';
        $scope.userSelected = '';

        /**
         * This must be transferred into a generic solution, simply not convincing.
         * Must dock to actual data model.
         * @return {[type]} [description]
         */
        $scope.$watch('userSelected', function() {
            if ($scope.userSelected === undefined || $scope.userSelected.name === undefined) {
                return;
            }
            for (var i = 0, len = $scope.addedUsers.length; i < len; i++) {
                if ($scope.addedUsers[i] === $scope.userSelected) {
                    return;
                }
            }
            $scope.addedUsers.push($scope.userSelected);
        });

        $scope.selectGroup = function(group) {
            $scope.selectedGroup = group;
        };

        $scope.ok = function() {
            // $modalInstance.close($scope.selected.item);
        };
        $scope.cancel = function() {
            $modalInstance.dismiss('cancel');
        };
    });