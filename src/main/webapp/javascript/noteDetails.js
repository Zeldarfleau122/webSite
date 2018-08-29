$(document).ready(function(){
    var commentaries = "" ;

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

    $("#submitCommentary").on('click', function () {
        commentaries = commentaries + "<div class=\"panel panel-default\"><div class=\"panel-body\">Author : " + getCookie("username") + "</div><div class=\"panel-footer\">" + $("#commentaryContent").val() + "</div></div>";

        $("#commentaries")[0].innerHTML = "<div>" +  commentaries + "</div>"

        commentary = $("#commentaryContent").val()
        commentary.replace(new RegExp('"', 'g'), '\"');
        $.ajax({
            url:'http://localhost:8080/TaskSite/commentary/share?id_Note=' + encodeURIComponent(3),
            type:'GET',
            dataType: 'json',
            data:'content='+encodeURIComponent(commentary),
                success: function(data) {
                    if (data.content != "") {
                        commentaries = commentaries + "<div class=\"panel panel-default\"><div class=\"panel-body\">Author : " + data.author + "</div><div class=\"panel-footer\">" + data.content + "</div></div>";

                        $("#commentaries")[0].innerHTML = "<div>" +  commentaries + "</div>"

                        verifyChallengeValidation() ;
                    }
                }
        }) ;

        $("#commentaryContent").val("") ;
    }) ;
}) ;