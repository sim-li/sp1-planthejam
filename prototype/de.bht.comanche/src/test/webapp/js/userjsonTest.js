describe('A suite',function(){
    
    it('says Hello Json!',function(){
        expect(HelloJson.sayHelloJson()).toBe("Hello World!");
        // expect(Hello.sayHelloWorld()).toEqual("Hello World!");
    });

});




//
//describe("A suite is just a function", function() {
////  var a = 'http://localhost:8080/service/single-user';
//	var getDataJson = $http.get({method: 'GET', url: 'http://localhost/plan-the-jam/index.html'});
////	http://localhost:8080/plan-the-jam/index.html
////  50195
////  50212
//  
//  var jsonself = null;
//	  
//  getDataJson.success(function(data, status, headers, config) {
//		console.log(data, status, headers, config);
//		
//		jsonself = data;
//  });		
//  
//  it("and so is a spec", function() {
//    a = true;
//    expect(jsonself.email).toEqual("test@hascode.com");
//    expect(jsonself.firstName).toEqual("Tim");
//    expect(jsonself.lastName).toEqual("Testerman");
//    expect(jsonself.id).toEqual(1);
//    
//    
////    private String email;
////	private String firstName;
////	private String lastName;
////	private int id;
////    "test@hascode.com", "Tim","Testerman", 1
//    
//  });
//  
//});

