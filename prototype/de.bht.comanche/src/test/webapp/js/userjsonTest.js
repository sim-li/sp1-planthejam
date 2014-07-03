//
//describe('Controller Json Test', function() {
// var $httpBackend = null;
// var myController = null; 
// 
// beforeEach(inject(function($rootScope, $controller) {
//	 myController = $rootScope.$new();
//	 $controller('HelloJson', { $scope: myController});
//   }));
// 
// beforeEach(inject(function(_$httpBackend_) {
//	 $httpBackend = _$httpBackend_;
//   }));
//	 
// it("Test JSON", function() {
//	 $httpBackend.expectGet('/plan-the-jam/rest/service/single-user').respond({
//		 "email" : "test@hascode.com",
//	      "firstName" : "Tim",
//	      "lastName" : "Testerman",
//	      "id" : 1
//	 });
//	 myController.loadJson();
//	 $httpBackend.flush();
//	 expect(myController.data.firstName).toEqual("Tim");
// });
// 
//// beforeEach(inject(
//// function(
//// _$httpBackend_, _$rootScope_, _myService_) {
//// $httpBackend = _$httpBackend_;
//// $rootScope = _$rootScope_;
//// // myService is a service that makes HTTP
//// // calls for us
//// myService = _myService_;
//// }));
////
//// it('should make a request to the backend', function() {
//// // Set an expectation that myService will
//// // send a GET request to the route
//// // /v1/api/current_user
//// $httpBackend.expect('GET', '/index.html').respond(200, {userId: 123});
//// myService.getCurrentUser();
//// // Important to flush requests
//// $httpBackend.flush();
//// });
// });
//
////----------------------------------------------------------------------------
//
////describe('A suite',function(){
////	var $http;
////
////	it('says Hello Json!',function(){
////		$http({
////				method: "GET",
////				url: "/service/single-user",
////				params: {
////				"username": "auser"
////				}
////		});
////	});
////
////	
////});
//
////---------------------------------------------------------------------------
//////describe('A suite',function(){
//////    
//////    it('says Hello Json!',function(){
//////        expect(Hello()).toBe("Hello World!");
//////        // expect(Hello.sayHelloWorld()).toEqual("Hello World!");
//////    });
//////
//////});
////describe("A suite is just a function", function() {
////
////var $scope, $http;
////
//////you need to inject dependencies first
////beforeEach(inject(function($rootScope, $rootHttp) {
////    $scope = $rootScope.$new();        
////    $http = $rootHttp.$new();  
////}));
////
////
////
////
////	
//////  var a = 'http://localhost:8080/service/single-user';
//////	var getDataJson = $http.get({method: 'GET', url: 'http://localhost/plan-the-jam/index.html'});
////	var getDataJson = $http.get('/plan-the-jam/rest/service/single-user');
//////	http://localhost:8080/plan-the-jam/index.html
//////  50195
//////  50212
////  
////  var jsonself = null;
////	  
////  getDataJson.success(function(data, status, headers, config) {
////		console.log(data, status, headers, config);
////		
////		jsonself = data;
////  });		
////  
////  it("and so is a spec", function() {
////    a = true;
////    expect(jsonself.email).toEqual("test@hascode.com");
////    expect(jsonself.firstName).toEqual("Tim");
////    expect(jsonself.lastName).toEqual("Testerman");
////    expect(jsonself.id).toEqual(1);
////    
////    
//////    private String email;
//////	private String firstName;
//////	private String lastName;
//////	private int id;
//////    "test@hascode.com", "Tim","Testerman", 1
////    
////  });
////  
////});
////
