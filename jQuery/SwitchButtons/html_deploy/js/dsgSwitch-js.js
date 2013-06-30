// JavaScript Document
var $ = jQuery.noConflict();

$(document).ready(function(){
	
	//set the animation speed for the switches					   
	var dsgAnimationSpeed = 200 ;
		
	// large switches
	$(".dsgSwitchL").click(function() {
			if( $(this).is('.dsgLSwitchOff') ) {
            $(this).animate({
                backgroundPosition: '-41px 0px'
            }, dsgAnimationSpeed, "swing",function(){
				$(this).removeClass("dsgLSwitchOff");
				$(this).addClass("dsgLSwitchOn");
				});
				}
				
			if( $(this).is('.dsgLSwitchOn') ) {
            $(this).animate({
                 backgroundPosition: '0px 0px'
            }, dsgAnimationSpeed, "swing",function(){
			    $(this).removeClass("dsgLSwitchOn");
				$(this).addClass("dsgLSwitchOff");
				});
				}	   
			return false; 
	});
	// small switches
	$(".dsgSwitchS").click(function() {
			if( $(this).is('.dsgSSwitchOff') ) {
            $(this).animate({
                backgroundPosition: '-24px 0px'
            }, dsgAnimationSpeed, "swing",function(){
				$(this).removeClass("dsgSSwitchOff");
				$(this).addClass("dsgSSwitchOn");
				});
				}
				
			if( $(this).is('.dsgSSwitchOn') ) {
            $(this).animate({
                 backgroundPosition: '0px 0px'
            }, dsgAnimationSpeed, "swing",function(){
			    $(this).removeClass("dsgSSwitchOn");
				$(this).addClass("dsgSSwitchOff");
				});
				}	   
			return false; 
	});
	
//document ready ends
});