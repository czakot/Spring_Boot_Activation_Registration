/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/*
 * Redirection Counter
 * It requires "count" and "redirect" variables
 */

var count;
var redirect;

function countDownInit(c,r) {
count = c; // Timer
redirect = r; // Target URL    
}

function countDown() {
    var timer = document.getElementById("timer");
    timer = 27;
}

//function countDown() {
//    if (count < 0) {
//        window.location.href = redirect;
//    } else {
//        document.getElementById("timer").innerHTML = count--;
//        setTimeout("countDown()", 1000);
//    }
//}



