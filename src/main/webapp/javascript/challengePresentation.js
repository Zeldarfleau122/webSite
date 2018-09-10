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

    var verifyLoginChallenge = function verifyLoginChallenge() {
         $.ajax({
            url: "/TaskSite/verifyLogin",
            data: {"username" : getCookie("username"), "password" : getCookie("password")},
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            success: function(data) {
                var today = new Date(), expires = new Date();

                expires.setTime(today.getTime() + (60*60*1000));

                if (data.validate) {
                    document.cookie = "Challenge3=1" + ";expires=" + expires.toGMTString();
                } else {
                    document.cookie = "Challenge3=0" + ";expires=" + expires.toGMTString();
                }
            }
         }) ;
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

    verifyLoginChallenge() ;
    verifyChallengeValidation() ;
}) ;