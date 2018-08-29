$(document).ready(function(){


    var getCookie = function getCookie(cname) {
        var name = cname + "=";
        var decodedCookie = document.cookie ;
        var ca = decodedCookie.split(';');
        for(var i = 0; i <ca.length; i++) {
            var c = ca[i];
            while (c.charAt(0) == ' ') {
                c = c.substring(1);
            }
            if (c.indexOf(name) == 0) {
                return c.substring(name.length, c.length);
            }
        }
        return "";
    }

    var verifyChallengeValidation = function verifyChallengeValidation() {
        var i;
        for (i = 0; i <= 3; i++) {
            if (getCookie("Challenge" + i) != "1")
                $("#" + i).css("border" , "5px solid red") ;
            else
                $("#" + i).css("border" , "5px solid green") ;
        }
    }

    verifyChallengeValidation() ;
}) ;