/*
 * Technik-Prototyp, SP1 SoSe 2014, Team: Comanche
 * (C)opyright Sebastian Dassé, Mat.-Nr. 791537, s50602@beuth-hochschule.de
 * 
 * Module: main
 */

/* 
 * RequireJS alias/path configuration (http://requirejs.org/)
 */

requirejs.config({
    paths: {
    
        // jquery library
        "jquery": [
            // try content delivery network location first
            'http://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min',
            //If the load via CDN fails, load locally
            './lib/jquery-1.7.2.min']

    }
});


/*
 * The function defined below is the "main" function,
 * it will be called once all prerequisites listed in the
 * define() statement are loaded.
 * 
 */

/* requireJS module definition */
define(["jquery", "test"], 
       (function($, Test) {

    "use strict";
    

    /*
     * main program, to be called once the document has loaded 
     * and the DOM has been constructed
     * 
     */

    $(document).ready( (function() {

        console.log("document ready - starting!");

        // try to get the test-div
        var test_div=$("#test_div").get(0);
        if(!test_div) { 
            throw new util.RuntimeError("this test failed", this); 
        }
        
        // try to run a function from another module
        console.log(new Test().test());
        
        $("#test_button").click( function() {
            var output = "Test " + Date.now();
            console.log(output);
            $("#output").text(output);
        });

        
    })); // $(document).ready()

})); // define module
        

