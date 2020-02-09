/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/*
 * Redirection Counter
 * It requires "count" and "redirect" variables
 */

function countDown() {
    if (count < 0) {
        window.location.href = redirect;
    } else {
        document.getElementById("timer").innerHTML = count--;
        setTimeout("countDown()", 1000);
    }
}



