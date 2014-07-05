function HelloJson($scope, $http) {
	
	$scope.loadJson = function(){
	
		var getDataJson = $http.get('/plan-the-jam/rest/service/single-user');
		
		getDataJson.success(function(data) {
			$scope.data = data;
		});
		
		
		getDataJson.error(function(data) {
			//TODO die richtige Behandlung von Fehler, ggf. Benutzer informieren 
			throw new Error('No data found');
		});
	};
}











//		return {"email":"test@hascode.com","firstName":"Tim","lastName":"Testerman","id":1};

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

