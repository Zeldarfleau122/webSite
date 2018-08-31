$(document).ready(function(){
    var deleteCookie = function deleteCookie( name ) {
      document.cookie = name + '=; expires=Thu, 01 Jan 1970 00:00:01 GMT;';
    }

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

    var logout = function() {
        deleteCookie("username") ;
        deleteCookie("password") ;
    }

    var isLogged = function() {
        if ((getCookie("username") != "") && (getCookie("password") != ""))
            return true ;
        else
            return false ;
    }

    if (isLogged()) {
        $("#identification").attr("href", "/logout") ;
        $("#identification").text("Logout") ;
        $("#identification").on('click', function() {logout()}) ;
    } else {
        $("#identification").attr("href", "/TaskSite/login") ;
        $("#identification").text("Login") ;
    }
}) ;