function HelloJson($scope, $http) {
	
	$scope.loadJson = function(){
	
		var getDataJson = $http.get('/plan-the-jam/rest/service/single-user');
		
		getDataJson.success(function(data) {
			$scope.data = data;
			console.log(data);
		});
		
		getDataJson.error(function(data) {
			//TODO die richtige Behandlung von Fehler, ggf. Benutzer informieren 
			throw new Error('No data found');
		});
	};
	
	var name = 'qweqwe';
	var telephone = 112312;
	var email = "test@test.com";
	var password = "sdsadasd";
	
	$scope.PutJson = function(){
		
		$http({
		    url: '/plan-the-jam/rest/user/login',
		    method: 'POST',
		    headers: { 'Content-Type': 'application/json' },
		    data: {name: name, password: password}
		}).success(function (data, status, headers, config) {
            $scope.persons = data; // assign  $scope.persons here as promise is resolved here 
            console.log("SUCCESS");
            console.log(data);
        }).error(function (data, status, headers, config) {
            $scope.status = status;
            console.log("ERROR");
            console.log(data);
        });
		
	};
}


