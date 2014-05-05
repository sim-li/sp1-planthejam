/*
 * Technik-Prototyp, SP1 SoSe 2014, Team: Comanche
 * (C)opyright Sebastian Dassé, Mat.-Nr. 791537, s50602@beuth-hochschule.de
 * 
 * Module: test
 */


/* requireJS module definition */
define(["jquery"], 
       (function($) {

    "use strict";
    

    var testString = "test.js loaded";
    var Test = function() {
        
    };
    
    Test.prototype.test = function() {
        return testString + " " + new Date().toUTCString();
    };
    
    return Test;

})); // define module
        

