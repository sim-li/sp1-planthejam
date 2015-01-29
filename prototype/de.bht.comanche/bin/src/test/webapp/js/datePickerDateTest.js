// describe("Date picker date", function() {

//     beforeEach(function() {
//         // module("datePickerDate");

//         angular.module("test", ["datePickerDate"]);

//         // inject(["DatePickerDate.DatePickerDate", function(DatePickerDate) {

//         // }])

//         it("should have a working DatePickerDate", inject(function(DatePickerDate) {
            
//         }));
//     });

//     // it("should have a working DatePickerDate", inject(function(DatePickerDate) {
        
//     // }));
    
//     it("should say hello world", function(DatePickerDate){
//         expect(1).toEqual(1);
//         var d = new Date();

//         expect(test).toEqual(test);
//         expect(test.DatePickerDate).toBeDefined();

//         // expect(new DatePickerDate(d).toDate()).toEqual(d);
//         // expect($scope).toBeDefined();
//     });
// });



//------------------ good test, but not getting the injection right: ------------

// describe("Date picker date", function() {

//     // beforeEach(angular.module("datePickerDate"));
//     beforeEach(inject(["DatePickerDate", function(DatePickerDate) {
//         expect(111).not.to.equal(0);
//     });

//     it("should have a working DatePickerDate", angular.inject(["DatePickerDate", function(DatePickerDate) {
          
//         expect(DatePickerDate.prototype.toDate).not.to.equal(null);
//         expect(DatePickerDate.convertDates).not.to.equal(null);

//         var jsDates = [
//             new Date("1969-12-31 23:55"), 
//             new Date("2014-07-16 01:23"), 
//             new Date("2112-11-22 21:45") 
//         ];

//         var dpDates = [
//             new DatePickerDate(jsDates[0]), 
//             new DatePickerDate(jsDates[1]), 
//             new DatePickerDate(jsDates[2]) 
//         ];

//         it("should ensure conversion from DatePickerDate to JavaScript Date", function() {
//             for (var i = 0; i < dpDates.length; i++) {
//                 expect(dpDates[i].toDate()).toEqual(jsDates[i]);
//             }
//         });

//         it("should ensure conversion from JavaScript Date array to DatePickerDate array", function() {
//             var convertedDates = DatePickerDate.convertDates(jsDates);
//             for (var i = 0; i < convertDates.length; i++) {
//                 expect(convertDates[i].toEqual(dpDates[i]));
//             }
//         });

//         it("bullsh", function() {
//             expect(12).toEqual(13);
//         });
//         // it("", function() {});
//         // it("", function() {});
//         // it("", function() {});

//     }]));
// });



