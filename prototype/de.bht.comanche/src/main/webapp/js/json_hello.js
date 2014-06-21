function Hello($scope, $http) {
	var url = $http.get('http://');
//	var url = $q.defer();
	$http.get('http://localhost:8080/plan-the-jam/rest/service/single-user').
	success(function(data) {
		$scope.greeting = data;
		$scope.testurl = url;
//		return {"email":"test@hascode.com","firstName":"Tim","lastName":"Testerman","id":1};
	});
}

//var HelloJson = {
//		
//sayHelloJson: function($scope, $http) {
////    $http.get('http://localhost:8080/plan-the-jam/rest/service/single-user').
//    $http.get('http://localhost/plan-the-jam/rest/service/single-user').
//        success(function(data) {
//            $scope.greeting = data;
//            return data;
//        });
//		}
//
//};

