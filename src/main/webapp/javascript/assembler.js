$(document).ready(function(){
    var previousCommand = "" ;
    var previousIp = 0 ;
    var previousSp = 255 ;
    var previousIpLineID = "00" ;

    var updateInstruction = function(ip) {
        hexaId = "00" + Number(ip).toString(16) ;
        hexaId = hexaId[hexaId.length-2]+hexaId[hexaId.length-1] ;

        $(previousIpLineID).css("background-color", '#FFFFFF')
        $("#"+hexaId.toUpperCase()).css("background-color", "#00FF00")

        previousIpLineID = "#" + hexaId.toUpperCase()
    }

    var update = function(data) {
        $("#regSp").text(data.sp) ;
        $("#regIp").text(data.ip) ;

        $("#regZ").text(data.zero) ;
        $("#regC").text(data.carry) ;
        $("#regF").text(data.fault) ;

        $("#reg0").text(data.regA) ;
        $("#reg1").text(data.regB) ;
        $("#reg2").text(data.regC) ;
        $("#reg3").text(data.regD) ;

        if (parseInt(data.state) == 1) {
            $("#userInputWindow").modal('show');
            setTimeout(function() {$('#userInput').focus()}, 250) ;
            $("#your-div-id").css("display", "none");
        } else {
            if (parseInt(data.state) == -10) {
                $("#userInputWindow").modal('show');
                setTimeout(function() {$('#userInput').focus()}, 250) ;
                $("#errorHexa").css("display", "block");
            } else {
                $("#userInputWindow").modal('hide');
            }
        }

        var lineChanged = data.memoryLineChange ;

        if (lineChanged != null) {
            var i ;

            for (i = 0; i < lineChanged.length; i++) {
                var line = "#l" + lineChanged[i] ;

                var memoryLine = data.newmemoryLine[i] ;

                console.log(memoryLine) ;

                for (var j = 0; j < memoryLine.length; j++) {
                    var cell = "#c" + j ;

                    var hexa = "00" + memoryLine[j].toString(16) ;

                    $(line).find(cell).html(hexa[hexa.length-2].toUpperCase() + hexa[hexa.length-1].toUpperCase()) ;
                }
            }
        }

        var line = "#l" + Math.floor(previousIp/16)
        var cell = "#c" + previousIp%16

        $(line).find(cell).css('background', '') ;

        line = "#l" + Math.floor(data.ip/16)
        cell = "#c" + data.ip%16

        $(line).find(cell).css('background', '#ff4d4d') ;

        previousIp = data.ip ;

        var line = "#l" + Math.floor(previousSp/16)
        var cell = "#c" + previousSp%16

        $(line).find(cell).css('background', '') ;

        line = "#l" + Math.floor(data.sp/16)
        cell = "#c" + data.sp%16

        $(line).find(cell).css('background', '#0080ff') ;

        previousSp = data.sp ;

        updateInstruction(data.ip) ;
    } ;

    $("#userCommand").keypress(function(e) {
        if(e.which == 13) {
            command = $("#userCommand").val()

            if (command == "") {
                command = previousCommand
            } else {
                previousCommand = command
            }

            $("#console").append("> "+command+"\n");
            $("#userCommand").val("");

            $.ajax({
                url: "/assemblerDoorChallenge/sendCommand",
                data: {"userCommand" : command},
                contentType: "application/json; charset=utf-8",
                dataType: 'json',
                success: function(data) {
                    console.log(data.success == "1") ;

                    if (data.success == "1") {
                        if (data.ouput != "")
                            $("#console").append(data.output+"\n");
                        $("#console").append("\n")

                        if (command == "reset")
                            location.reload() ;
                        else
                            update(data) ;
                    } else {
                        $("#console").append("> Not a valid command\n")
                        $("#console").append("\n")
                    }
                }
            });
        }
    });

    $("#userInput").keypress(function(e) {
        if(e.which == 13) {
            input = $("#userInput").val() ;

            $.ajax({
                url: "/assemblerDoorChallenge/sendInput",
                data: {"userInput" : input},
                contentType: "application/json; charset=utf-8",
                dataType: 'json',
                success: function(data) {
                    update(data) ;
                }
            }) ;

            $("#userInput").val("") ;
        }
    }) ;

    $("#inputValidation").click(function(){
        input = $("#userInput").val() ;

        $.ajax({
            url: "/assemblerDoorChallenge/sendInput",
            data: {"userInput" : input},
            contentType: "application/json; charset=utf-8",
            dataType: 'json',
            success: function(data) {
                data.stateupdate(data) ;
            }
        }) ;

        $("#userInput").val("") ;
    }) ;

     $.ajax({
        url: "/assemblerDoorChallenge/cpuState",
        data: {},
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        success: function(data) {
            if (data.success == "1") {
                update(data) ;
            } else {
                        // ERROR !!!!
            }
        }
     }) ;

     $("#intro").modal("show") ;
});