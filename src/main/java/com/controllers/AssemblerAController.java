package com.controllers;

import com.AssemblerEmulator.CPU;
import com.AssemblerEmulator.Memory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

@Controller
public class AssemblerAController {
    private CPU cpu ;
    private boolean running ;


    public AssemblerAController() {
        this.running = false ;
    }

    public void loadLevelMemory() {
        Memory memory = this.cpu.getMemory() ;

            // 00   MOV D, 200
        memory.store(0, 6);
        memory.store(1, 3);
        memory.store(2, 200);

            // 04   CALL GetPassword
        memory.store(4, 56);
        memory.store(5, 48);

            // 08   CALL CheckPassword
        memory.store(8, 56);
        memory.store(9, 116);

            // 0C   CMP D, 0
        memory.store(12, 23);
        memory.store(13, 3);
        memory.store(14, 0);

            // 10   JZ UnlockDoor
        memory.store(16, 37);
        memory.store(17, 40);

            // 14   HLT
        memory.store(20, 00);

            // 28   OPENDOOR
        memory.store(40, 160);
        memory.store(41, 32);
        memory.store(42, 1);

            // 2C   HLT
        memory.store(44, 00);

            // 30   INT 1, D
        memory.store(48, 100);
        memory.store(49, 1);
        memory.store(50, 3);


            // 34   MOV B, 1
        memory.store(52, 6);
        memory.store(53, 1);
        memory.store(54, 1);

            // 38   MOV C, 0
        memory.store(56, 6);
        memory.store(57, 2);
        memory.store(58, 0);

            // 3C   MOV A, [D]
        memory.store(60, 3);
        memory.store(61, 0);
        memory.store(62, 3);

            // 40   ADD A, B
        memory.store(64, 10);
        memory.store(65, 0);
        memory.store(66, 1);

            // 44   MOV [D], A
        memory.store(68, 5);
        memory.store(69, 3);
        memory.store(70, 0);

            // 48   INC D
        memory.store(72, 18);
        memory.store(73, 3);

            // 4C   INC B
        memory.store(76, 18);
        memory.store(77, 1);

            // 50   CMP C, [D]
        memory.store(80, 21);
        memory.store(81, 2);
        memory.store(82, 3);

            // 54   JNZ .loopG
        memory.store(84, 39);
        memory.store(85, 56);

            // 58   MOV C, B
        memory.store(88, 1);
        memory.store(89, 2);
        memory.store(90, 1);

            // 5C   SUB C, 1
        memory.store(92, 17);
        memory.store(93, 2);
        memory.store(94, 1);

            // 60   MOV D, 200
        memory.store(96, 6);
        memory.store(97, 3);
        memory.store(98, 200);

            // 64   CMP B, 4
        memory.store(100, 6);
        memory.store(101, 1);
        memory.store(102, 1);

            // 68   JA 70
        memory.store(104, 41);
        memory.store(105, 7*16);

            // 6C   HLT
        memory.store(108, 0);

            // 70   RET
        memory.store(112, 57);

            // 74   MOV B, 1
        memory.store(116, 6);
        memory.store(117, 1);
        memory.store(118, 1);

            // 78   MOV A, [D]
        memory.store(120, 3);
        memory.store(121, 0);
        memory.store(122, 3);

            // 7C   MOV C, [D]
        memory.store(124, 3);
        memory.store(125, 2);
        memory.store(126, 3);

            // 80   SUB C, B
        memory.store(128, 14);
        memory.store(129, 2);
        memory.store(130, 1);

            // 84   CMP A, C
        memory.store(132, 20);
        memory.store(133, 0);
        memory.store(134, 2);

            // 88   JNE 90
        memory.store(136, 39);
        memory.store(137, 164);

            // 8C   INC D
        memory.store(140, 18);
        memory.store(141, 3);

            // 90   INC B
        memory.store(144, 18);
        memory.store(145, 1);

            // 94 MOV A, [D]
        memory.store(148, 3);
        memory.store(149, 1);
        memory.store(150, 3);

            // 98   CMP A, 0
        memory.store(152, 23);
        memory.store(153, 0);
        memory.store(154, 0);

            // 9C   JNZ .loopC
        memory.store(156, 39);
        memory.store(157, 120);

            // A4 MOV D, [D]
        memory.store(160, 3);
        memory.store(161, 3);
        memory.store(162, 3);

            // A8   RET
        memory.store(164, 57);

        memory.clearNewmemoryLine();
        memory.clearMemoryLineChange();
    }

    @RequestMapping(value = "/hearthRequestPassword", method = RequestMethod.GET)
    public String hearthPassword() {
        return "redirect:/sound/hearthPassword.wav" ;
    }

    @RequestMapping(value = "/assemblerDoorChallenge", method = RequestMethod.GET)
    public String setUp(Model model) {
        if (this.cpu == null) {
            this.cpu = new CPU() ;
            Memory memory = new Memory();

            this.cpu.setMemory(memory);

            loadLevelMemory() ;
        }

        model.addAttribute("regIp", this.cpu.getIp()) ;
        model.addAttribute("regSp", this.cpu.getSp()) ;

        model.addAttribute("regZ", this.cpu.getZero()) ;
        model.addAttribute("regC", this.cpu.getCarry()) ;
        model.addAttribute("regF", this.cpu.getfault()) ;

        model.addAttribute("reg0", this.cpu.readRegister(0)) ;
        model.addAttribute("reg1", this.cpu.readRegister(1)) ;
        model.addAttribute("reg2", this.cpu.readRegister(2)) ;
        model.addAttribute("reg3", this.cpu.readRegister(3)) ;

        model.addAttribute("memory", cpu.getMemory().getMemory()) ;

        return "assemblerDoorChallenge" ;
    }

    private CPUStateAnswer executeCommand(String userCommand) {
        String answer = "" ;
        CPUStateAnswer cpuState = this.cpu.getCPUStateAnswer() ;

        this.running = true ;

        switch(userCommand) {
            case "help":
                answer = "Commands :\nhelp - Show this message\n    reset - reset the cpu state.\n    step - step 1 instruction.\n    continue - run until input request or end.\nEmpty input will send previous command.\nSend an empty input will send the last command entered." ;
                break;
            case "continue":
                List<int[]> newMemoryLine = cpuState.getNewmemoryLine() ;
                List<Integer> memoryLineChange = cpuState.getMemoryLineChange() ;
                if (cpuState.getState() == 0) {
                    do {
                        cpu.step() ;

                        cpuState = this.cpu.getCPUStateAnswer() ;

                        newMemoryLine.addAll(cpuState.getNewmemoryLine()) ;
                        memoryLineChange.addAll(cpuState.getMemoryLineChange()) ;
                    } while (cpuState.getState() == 0) ;
                    cpuState.setNewmemoryLine(newMemoryLine);
                    cpuState.setMemoryLineChange(memoryLineChange);
                }

                break;
            case "step":
                // If state 0 ok.
                if (cpuState.getState() == 0)
                    cpu.step() ;
                cpuState = this.cpu.getCPUStateAnswer() ;
               // else // THROW ERROR !!!!!!!!!

                break;
            case "reset":
                // Always
                cpu.reset() ;
                loadLevelMemory();
                break ;
            default:
                answer = "unknown command" ;
        }

        cpuState.setOutput(answer);

        this.running = false ;

        return cpuState ;
    }

    @RequestMapping(value="/assemblerDoorChallenge/sendCommand", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public CPUStateAnswer sendCommand(String userCommand) {

        if (!this.running)
            return executeCommand(userCommand) ;
        else
            return null ;
    }

    private boolean checkHexa(String userInput) {
        userInput = userInput.toUpperCase() ;

        if (userInput.length()%2 == 0) {
            for (int i = 0; i < userInput.length(); i++) {
                if (!((('0' <= userInput.charAt(i))
                        && ( userInput.charAt(i) <= '9'))
                        || (('A' <= userInput.charAt(i))
                        && (userInput.charAt(i) <= 'F'))))
                    return false ;
            }
        } else
            return false ;

        return true ;
    }

    @RequestMapping(value="/assemblerDoorChallenge/sendInput", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public CPUStateAnswer getInput(String userInput) {
        CPUStateAnswer cpuState = this.cpu.getCPUStateAnswer() ;

        System.out.println("1") ;

        if (checkHexa(userInput) && (cpuState.getState() == 1)) {
            System.out.println("2") ;
            int[] input = new int[userInput.length()/2 + 1] ;
            String temp ;
            int lastIndex = 0 ;
            boolean endInput = false ;
            for (int i = 0; (i < userInput.length()/2) && !(endInput); i++) {
                temp = "0x" + userInput.charAt(2*i) + userInput.charAt(2*i+1) ;

                input[i] = Integer.decode(temp).intValue() ;

                if (input[i] == 0) endInput = true ;

                lastIndex = i+1 ;
            }
            input[lastIndex] = 0 ;
            System.out.println("3") ;
            cpuState = this.cpu.writeInput(input) ;
        } else {
            System.out.println("21") ;
            if (cpuState.getState() == 1)
                cpuState.setState(-10);                 // Error not hexa input.
        }

        return cpuState ;
    }

    @RequestMapping(value="/assemblerDoorChallenge/cpuState", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public CPUStateAnswer cpuState() {
        CPUStateAnswer cpuState = this.cpu.getCPUStateAnswer() ;

        return cpuState ;
    }

    @RequestMapping(value = "/assemblerDoorChallenge/step", method = RequestMethod.GET)
    public String step() {
        cpu.step() ;

        return "redirect:/assemblerDoorChallenge" ;
    }
}
