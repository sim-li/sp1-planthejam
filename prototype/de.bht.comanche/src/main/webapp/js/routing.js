
	
angular.module('myApp', ['ngRoute']).
	config(function($routeProvider) {
		$routeProvider
			.when('/login', {
				templateUrl : 'pages/login.html',
				controller  : 'loginCtrl'
			})
			.when('/register', {
				templateUrl : 'pages/register.html',
				controller  : 'loginCtrl'
			})
			.when('/cockpit', {
				templateUrl : 'pages/cockpit.html',
				controller  : 'cockpitCtrl'
			})

	});

	
