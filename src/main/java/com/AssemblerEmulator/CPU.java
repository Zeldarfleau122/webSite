package com.AssemblerEmulator;

import com.controllers.CPUStateAnswer;

import java.lang.Math;
import java.util.ArrayList;
import java.util.List;

/**
 * com.AssemblerEmulator.CPU class
 *
 * @// TODO: 29/05/2018 Rename fonction this.getResult / Verifier valeur et registre (  fonctions dediees )  / add error codes / take in count the fault register. / Ajout d'une macro pour le nombre de registre / Faire en 32 bits !!! / Erreur si "instruction" null
 */
public class CPU {
    private int[] registers ;               // All the registers
    private int sp, ip ;                    // sp : Stack Pointer, ip : Instruction Pointer
    private boolean zero, carry, fault ;    // zero : true or false either if result is 0 or not
                                            // carry : true if a carry is generated
                                            // fault : if error occur then set to true
    private Memory memory ;
    private int state ;                     // 0 : Disponible 1 : En attente -1 : Fini non success 3 : Fini success -2 : erreur
    private boolean doorOpen = false ;

    public CPU() {
        this.registers = new int[4] ;
        this.zero = false ;
        this.carry = false ;
        this.fault = false ;
        this.ip = 0 ;
        this.sp = 14*16 ;

        this.memory = null ;

        this.state = 0 ;
    }

    public void setMemory(Memory memory) {
        this.memory = memory ;
    }

    public Memory getMemory() {
        return this.memory ;
    }

    public void reset() {
        this.registers[0] = 0 ;
        this.registers[1] = 0 ;
        this.registers[2] = 0 ;
        this.registers[3] = 0 ;

        this.zero = false ;
        this.carry = false ;
        this.fault = false ;

        this.ip = 0 ;
        this.sp = 14*16 ;

        this.state = 0 ;

        this.memory.reset() ;

        this.doorOpen = false ;
    }

    private int checkReg(int reg) {
        //if (reg < 0 || 4<=reg)
            // THROW ERROR !!!
        return reg ;
    }

    public void step() {
        if (fault) {
            // Throw error
        }

        CodeOperation instruction = CodeOperation.getCode(memory.load(ip)) ;

        if (instruction == null) {
                            //  THROW ERROR !!!!!
            System.out.println("ERREUR INSTRUCTION NULL") ;
        }

        int regDest, regFrom ;
        int memDest, memFrom ;
        int number ;
        int res ;

        switch(instruction) {
            case NONE:
                if (this.doorOpen)
                    this.state = 3 ;
                else
                    this.state = -1 ;
                break;
            case MOV_REG_TO_REG :
                    regDest = this.checkReg(memory.load(++ip)) ;
                    regFrom = this.checkReg(memory.load(++ip)) ;

                    this.writeRegister(regDest, this.readRegister(regFrom)) ;

                    this.ip = ip + 2 ;
                break ;

            case MOV_ADDRESS_TO_REG:
                    regDest = this.checkReg(memory.load(++ip)) ;
                    memFrom = memory.load(++ip) ;

                    this.writeRegister(regDest, memory.load(memFrom)) ;

                    this.ip = ip + 2 ;
                break;

            case MOV_REGADDRESS_TO_REG:
                    regDest = this.checkReg(memory.load(++ip)) ;
                    regFrom = this.checkReg(memory.load(++ip)) ;

                    this.writeRegister(regDest, memory.load(this.readRegister(regFrom))) ;

                    this.ip = ip + 2 ;

                    break;

            case MOV_REG_TO_ADDRESS:
                memDest = memory.load(++ip) ;
                regFrom = this.checkReg(memory.load(++ip)) ;

                memory.store(memDest, this.readRegister(regFrom)) ;

                this.ip = ip + 2 ;

                break;

            case MOV_REG_TO_REGADDRESS:
                regDest = memory.load(++ip) ;
                regFrom = memory.load(++ip) ;

                memory.store(this.indirectRegAddr(regDest), this.readRegister(regFrom)) ;

                this.ip = ip + 2 ;

                break;

            case MOV_NUMBER_TO_REG:
                regDest = this.checkReg(memory.load(++ip)) ;
                number = memory.load(++ip) ;

                this.writeRegister(regDest, number);

                this.ip = ip + 2 ;

                break;

            case MOV_NUMBER_TO_ADDRESS:
                memDest = memory.load(++ip) ;
                number = memory.load(++ip) ;

                memory.store(memDest, number) ;

                this.ip = ip + 2 ;

                break;

            case MOV_NUMBER_TO_REGADDRESS:
                regDest = memory.load(++ip) ;
                number = memory.load(++ip) ;

                memory.store(this.indirectRegAddr(regDest), number) ;

                this.ip = ip + 2 ;

                break;

            case ADD_REG_TO_REG :
                regDest = memory.load(++ip) ;
                regFrom = memory.load(++ip) ;

                res = this.getResult(this.readRegister(regDest)+this.readRegister(regFrom)) ;

                this.writeRegister(regDest, res) ;

                ip = ip + 2 ;

                break ;

            case ADD_REGADDRESS_TO_REG:
                regDest = memory.load(++ip) ;
                regFrom = memory.load(++ip) ;

                this.writeRegister(regDest, this.getResult(this.readRegister(regDest) + memory.load(this.indirectRegAddr(regFrom)))) ;

                this.ip = ip + 2 ;

                break;

            case ADD_ADDRESS_TO_REG:
                regDest = this.checkReg(memory.load(++ip)) ;
                memFrom = memory.load(++ip) ;

                this.writeRegister(regDest, this.getResult(this.readRegister(regDest) + memory.load(memFrom))) ;

                this.ip = ip + 2 ;

                break;

            case ADD_NUMBER_TO_REG:
                regDest = this.checkReg(memory.load(++ip)) ;
                number = memory.load(memory.load(++ip)) ;

                this.writeRegister(regDest, this.getResult(this.readRegister(regDest) + number)) ;

                this.ip = ip + 2 ;

                break;

            case SUB_REG_FROM_REG:
                regDest = memory.load(++ip) ;
                regFrom = memory.load(++ip) ;

                res = this.getResult(this.readRegister(regDest)-this.readRegister(regFrom)) ;

                this.writeRegister(regDest, res) ;

                ip = ip + 2 ;

                break ;

            case SUB_REGADDRESS_FROM_REG:
                regDest = memory.load(++ip) ;
                regFrom = memory.load(++ip) ;

                this.writeRegister(regDest, this.getResult(this.readRegister(regDest) - memory.load(this.indirectRegAddr(regFrom)))) ;

                this.ip = ip + 2 ;

                break;

            case SUB_ADDRESS_FROM_REG:
                regDest = this.checkReg(memory.load(++ip)) ;
                memFrom = memory.load(++ip) ;

                this.writeRegister(regDest, this.getResult(this.readRegister(regDest) - memory.load(memFrom))) ;

                this.ip = ip + 2 ;

                break;

            case SUB_NUMBER_FROM_REG:
                regDest = this.checkReg(memory.load(++ip)) ;
                number = memory.load(++ip) ;

                this.writeRegister(regDest, this.getResult(this.readRegister(regDest) - number)) ;

                this.ip = ip + 2 ;

                break;

            case INC_REG:
                regDest = this.checkReg(memory.load(++ip)) ;

                this.writeRegister(regDest, this.checkReg(this.readRegister(regDest) + 1 )) ;

                this.ip = ip + 3 ;

                break;

            case DEC_REG:
                regDest = this.checkReg(memory.load(++ip)) ;

                this.writeRegister(regDest, this.checkReg(this.readRegister(regDest) - 1 )) ;

                this.ip = ip + 3 ;

                break;

            case CMP_REG_WITH_REG:
                regDest = this.checkReg(memory.load(++this.ip));
                regFrom = this.checkReg(memory.load(++this.ip));

                this.getResult(this.readRegister(regDest) - this.readRegister(regFrom));

                this.ip = ip + 2 ;

                break;

            case CMP_REGADDRESS_WITH_REG:
                regDest = this.checkReg(memory.load(++ip));
                regFrom = memory.load(++ip);

                this.getResult(this.readRegister(regDest) - memory.load(this.indirectRegAddr(regFrom)));

                this.ip = ip + 2 ;

                break;

            case CMP_ADDRESS_WITH_REG:
                regDest = this.checkReg(memory.load(++ip));
                regDest = this.checkReg(memory.load(++ip));
                memFrom = memory.load(++ip);

                this.checkReg(this.readRegister(regDest) - memory.load(memFrom));

                this.ip = ip + 2 ;

                break;

            case CMP_NUMBER_WITH_REG:
                regDest = this.checkReg(memory.load(++ip));
                number = memory.load(++ip);

                this.checkReg(this.readRegister(regDest) - number);

                this.ip = ip + 2 ;

                break;

            case JMP_REGADDRESS:
                regDest = this.checkReg(memory.load(++ip));

                jump(this.registers[regDest]);

                break;

            case JMP_ADDRESS:
                number = memory.load(++ip);

                jump(number);

                break;

            case JC_REGADDRESS:
                regDest = this.checkReg(memory.load(++ip));
                if (carry) {
                    jump(this.registers[regDest]);
                } else {
                    this.ip = ip + 3 ;
                }
                break;
            case JC_ADDRESS:
                number = memory.load(++ip);
                if (carry) {
                    jump(number);
                } else {
                    this.ip = ip + 3 ;
                }
                break;
            case JNC_REGADDRESS:
                regDest = this.checkReg(memory.load(++ip));
                if (!carry) {
                    jump(this.registers[regDest]);
                } else {
                    this.ip = ip + 3 ;
                }
                break;
            case JNC_ADDRESS:
                number = memory.load(++ip);
                if (!carry) {
                    jump(number);
                } else {
                    this.ip = ip + 3 ;
                }
                break;
            case JZ_REGADDRESS:
                regDest = this.checkReg(memory.load(++ip));
                if (zero) {
                    jump(this.registers[regDest]);
                } else {
                    this.ip = ip + 3 ;
                }
                break;
            case JZ_ADDRESS:
                number = memory.load(++ip);
                if (zero) {
                    jump(number);
                } else {
                    this.ip = ip + 3 ;
                }
                break;
            case JNZ_REGADDRESS:
                regDest = this.checkReg(memory.load(++ip));
                if (!zero) {
                    jump(this.registers[regDest]);
                } else {
                    this.ip = ip + 3 ;
                }
                break;
            case JNZ_ADDRESS:
                number = memory.load(++ip);
                if (!zero) {
                    jump(number);
                } else {
                    this.ip = ip + 3 ;
                }
                break;
            case JA_REGADDRESS:
                regDest = this.checkReg(memory.load(++ip));
                if (!zero && !carry) {
                    jump(this.registers[regDest]);
                } else {
                    this.ip = ip + 3 ;
                }
                break;
            case JA_ADDRESS:
                number = memory.load(++ip);
                if (!zero && !carry) {
                    jump(number);
                } else {
                    this.ip = ip + 3 ;
                }
                break;
            case JNA_REGADDRESS:
                regDest = this.checkReg(memory.load(++ip));
                if (zero || carry) {
                    jump(this.registers[regDest]);
                } else {
                    this.ip = ip + 3 ;
                }
                break;
            case JNA_ADDRESS:
                number = memory.load(++ip);
                if (zero || carry) {
                    jump(number);
                } else {
                    this.ip = ip + 3 ;
                }
                break;
            case PUSH_REG:
                regFrom = this.checkReg(memory.load(++ip));
                push(this.registers[regFrom]);
                this.ip = ip + 3 ;
                break;
            case PUSH_REGADDRESS:
                regFrom = memory.load(++ip);
                push(memory.load(this.indirectRegAddr(regFrom)));
                this.ip = ip + 3 ;
                break;
            case PUSH_ADDRESS:
                memFrom = memory.load(++ip);
                push(memory.load(memFrom));
                this.ip = ip + 3 ;
                break;
            case PUSH_NUMBER:
                number = memory.load(++ip);
                push(number);
                this.ip = ip + 3 ;
                break;
            case POP_REG:
                regDest = this.checkReg(memory.load(++ip));

                this.registers[regDest] = pop();

                this.ip = ip + 3 ;
                break;
            case CALL_REGADDRESS:
                regDest = this.checkReg(memory.load(++ip));
                push(ip+3);
                jump(this.registers[regDest]);
                break;
            case CALL_ADDRESS:
                number = memory.load(++ip);
                push(ip+3);
                jump(number);
                break;
            case RET:
                jump(pop());
                break;
            case MUL_REG:
                regFrom = this.checkReg(memory.load(++ip));
                this.registers[0] = this.checkReg(this.registers[0] * this.registers[regFrom]);
                this.ip = ip + 2 ;
                break;
            case MUL_REGADDRESS:
                regFrom = memory.load(++ip);
                this.registers[0] = this.checkReg(this.registers[0] * memory.load(this.indirectRegAddr(regFrom)));
                this.ip = ip + 2 ;
                break;
            case MUL_ADDRESS:
                memFrom = memory.load(++ip);
                this.registers[0] = this.checkReg(this.registers[0] * memory.load(memFrom));
                this.ip = ip + 2 ;
                break;
            case MUL_NUMBER:
                number = memory.load(++ip);
                this.registers[0] = this.checkReg(this.registers[0] * number);
                this.ip = ip + 2 ;
                break;
            case DIV_REG:
                regFrom = this.checkReg(memory.load(++ip));
                this.registers[0] = this.checkReg(division(this.registers[regFrom]));
                this.ip = ip + 2 ;
                break;
            case DIV_REGADDRESS:
                regFrom = memory.load(++ip);
                this.registers[0] = this.checkReg(division(memory.load(this.indirectRegAddr(regFrom))));
                this.ip = ip + 2 ;
                break;
            case DIV_ADDRESS:
                memFrom = memory.load(++ip);
                this.registers[0] = this.checkReg(division(memory.load(memFrom)));
                this.ip = ip + 2 ;
                break;
            case DIV_NUMBER:
                number = memory.load(++ip);
                this.registers[0] = this.checkReg(division(number));
                this.ip = ip + 2 ;
                break;
            case AND_REG_WITH_REG:
                regDest = this.checkReg(memory.load(++ip));
                regFrom = this.checkReg(memory.load(++ip));
                this.registers[regDest] = this.checkReg(this.registers[regDest] & this.registers[regFrom]);
                this.ip = ip + 2 ;
                break;
            case AND_REGADDRESS_WITH_REG:
                regDest = this.checkReg(memory.load(++ip));
                regFrom = memory.load(++ip);
                this.registers[regDest] = this.checkReg(this.registers[regDest] & memory.load(this.indirectRegAddr(regFrom)));
                this.ip = ip + 2 ;
                break;
            case AND_ADDRESS_WITH_REG:
                regDest = this.checkReg(memory.load(++ip));
                memFrom = memory.load(++ip);
                this.registers[regDest] = this.checkReg(this.registers[regDest] & memory.load(memFrom));
                this.ip = ip + 2 ;
                break;
            case AND_NUMBER_WITH_REG:
                regDest = this.checkReg(memory.load(++ip));
                number = memory.load(++ip);
                this.registers[regDest] = this.checkReg(this.registers[regDest] & number);
                this.ip = ip + 2 ;
                break;
            case OR_REG_WITH_REG:
                regDest = this.checkReg(memory.load(++ip));
                regFrom = this.checkReg(memory.load(++ip));
                this.registers[regDest] = this.checkReg(this.registers[regDest] | this.registers[regFrom]);
                this.ip = ip + 2 ;
                break;
            case OR_REGADDRESS_WITH_REG:
                regDest = this.checkReg(memory.load(++ip));
                regFrom = memory.load(++ip);
                this.registers[regDest] = this.checkReg(this.registers[regDest] | memory.load(this.indirectRegAddr(regFrom)));
                this.ip = ip + 2 ;
                break;
            case OR_ADDRESS_WITH_REG:
                regDest = this.checkReg(memory.load(++ip));
                memFrom = memory.load(++ip);
                this.registers[regDest] = this.checkReg(this.registers[regDest] | memory.load(memFrom));
                this.ip = ip + 2 ;
                break;
            case OR_NUMBER_WITH_REG:
                regDest = this.checkReg(memory.load(++ip));
                number = memory.load(++ip);
                this.registers[regDest] = this.checkReg(this.registers[regDest] | number);
                this.ip = ip + 2 ;
                break;
            case XOR_REG_WITH_REG:
                regDest = this.checkReg(memory.load(++ip));
                regFrom = this.checkReg(memory.load(++ip));
                this.registers[regDest] = this.checkReg(this.registers[regDest] ^ this.registers[regFrom]);
                this.ip = ip + 2 ;
                break;
            case XOR_REGADDRESS_WITH_REG:
                regDest = this.checkReg(memory.load(++ip));
                regFrom = memory.load(++ip);
                this.registers[regDest] = this.checkReg(this.registers[regDest] ^ memory.load(this.indirectRegAddr(regFrom)));
                this.ip = ip + 2 ;
                break;
            case XOR_ADDRESS_WITH_REG:
                regDest = this.checkReg(memory.load(++ip));
                memFrom = memory.load(++ip);
                this.registers[regDest] = this.checkReg(this.registers[regDest] ^ memory.load(memFrom));
                this.ip = ip + 2 ;
                break;
            case XOR_NUMBER_WITH_REG:
                regDest = this.checkReg(memory.load(++ip));
                number = memory.load(++ip);
                this.registers[regDest] = this.checkReg(this.registers[regDest] ^ number);
                this.ip = ip + 2 ;
                break;
            case NOT_REG:
                regDest = this.checkReg(memory.load(++ip));
                this.registers[regDest] = this.checkReg(~this.registers[regDest]);
                this.ip = ip + 2 ;
                break;
            case SHL_REG_WITH_REG:
                regDest = this.checkReg(memory.load(++ip));
                regFrom = this.checkReg(memory.load(++ip));
                this.registers[regDest] = this.checkReg(this.registers[regDest] << this.registers[regFrom]);
                this.ip = ip + 2 ;
                break;
            case SHL_REGADDRESS_WITH_REG:
                regDest = this.checkReg(memory.load(++ip));
                regFrom = memory.load(++ip);
                this.registers[regDest] = this.checkReg(this.registers[regDest] << memory.load(this.indirectRegAddr(regFrom)));
                this.ip = ip + 2 ;
                break;
            case SHL_ADDRESS_WITH_REG:
                regDest = this.checkReg(memory.load(++ip));
                memFrom = memory.load(++ip);
                this.registers[regDest] = this.checkReg(this.registers[regDest] << memory.load(memFrom));
                this.ip = ip + 2 ;
                break;
            case SHL_NUMBER_WITH_REG:
                regDest = this.checkReg(memory.load(++ip));
                number = memory.load(++ip);
                this.registers[regDest] = this.checkReg(this.registers[regDest] << number);
                this.ip = ip + 2 ;
                break;
            case SHR_REG_WITH_REG:
                regDest = this.checkReg(memory.load(++ip));
                regFrom = this.checkReg(memory.load(++ip));
                this.registers[regDest] = this.checkReg(this.registers[regDest] >>> this.registers[regFrom]);
                this.ip = ip + 2 ;
                break;
            case SHR_REGADDRESS_WITH_REG:
                regDest = this.checkReg(memory.load(++ip));
                regFrom = memory.load(++ip);
                this.registers[regDest] = this.checkReg(this.registers[regDest] >>> memory.load(this.indirectRegAddr(regFrom)));
                this.ip = ip + 2 ;
                break;
            case SHR_ADDRESS_WITH_REG:
                regDest = this.checkReg(memory.load(++ip));
                memFrom = memory.load(++ip);
                this.registers[regDest] = this.checkReg(this.registers[regDest] >>> memory.load(memFrom));
                this.ip = ip + 2 ;
                break;
            case SHR_NUMBER_WITH_REG:
                regDest = this.checkReg(memory.load(++ip));
                number = memory.load(++ip);
                this.registers[regDest] = this.checkReg(this.registers[regDest] >>> number);
                this.ip = ip + 2 ;
                break;
            case INT:
                ++ip ;
                regFrom = memory.load(++ip);
                this.memory.nextInputAddress(readRegister(regFrom));

                this.state = 1 ;

                this.ip = ip + 2 ;
                break ;
            case OPENDOOR:
                int memA = memory.load(++ip) ;
                int memB = memory.load(++ip) ;
                int memC = memory.load(++ip) ;

                this.doorOpen = true ;

                System.out.println("OPENDORR") ;

                // Verirication

                this.ip = ip + 1 ;
                break ;
            default : // THROW ERROR !!!
        }
    }

    private int division(int divisor) {
        if (divisor == 0) {
            //throw "Division by 0";
        }

        Double dTemp = Math.floor(this.registers[0] / divisor);
        return dTemp.intValue() ;
    }

    private int indirectRegAddr(int value) {
        int reg = value % 8;

        int base;
        if (reg < 4) {
            base = this.registers[reg];
        } else {
            base = this.sp;
        }

        Double dTemp =  Math.floor(value / 8.0) ;
        int offset = dTemp.intValue() ;

        if ( offset > 15 ) {
            offset = offset - 32;
        }

        return base+offset;
    }

    public   int readRegister(int regDest) {
        if (regDest < 4)
            return this.registers[regDest] ;
        else fault = true ;

        return 0 ;
    }

    private void writeRegister(int regDest, int res) {
        if (regDest < 4)
            this.registers[regDest] = res ;
        else fault = true ;
    }

    private int getResult(int result) {
        zero = false ;
        carry = false ;

        if (result >= 256) {
            carry = true ;
            result = result%256 ;
        } else if (result == 0) {
            zero = true;
        } else if (result < 0) {
            carry = true ;
            result = 255 - ((-1*result)%256) ;
        }

        return result ;
    }

    private void push(int value) {
        memory.store(this.sp, value) ;

        this.sp -- ;

        if (this.sp < 0) {
            // THROW ERROR !!!!
        }
    }

    private int pop() {
        int value = memory.load(++this.sp) ;

        if (256 <= this.sp ) {
            // THROW ERROR
        }

        return value ;
    }

    private void jump(int address) {
        if (address < 0 || 256 <= address ||address % 4 != 0) {
            // THROW ERROR !!!
        }

        this.ip = address ;
    }

    public void setSp(int sp) {
        this.sp = sp ;
    }

    public void setRegister(int regDest, int value) {
        if (regDest < 4)
            this.registers[regDest] = value ;
    }

    public CPUStateAnswer writeInput(String input) {
        this.memory.writeInput(input);                          // !!! ERROR HANDLER !!!!

        this.state = 0 ;

        return this.getCPUStateAnswer() ;
    }

    public CPUStateAnswer writeInput(int[] input) {
        this.memory.writeInput(input);                          // !!! ERROR HANDLER !!!!

        this.state = 0 ;

        return this.getCPUStateAnswer() ;
    }

    public int getIp() {
        return this.ip ;
    }

    public int getSp() {
        return this.sp ;
    }

    public boolean getZero() {
        return this.zero ;
    }

    public boolean getfault() {
        return this.fault ;
    }

    public boolean getCarry() {
        return this.carry ;
    }

    public CPUStateAnswer getCPUStateAnswer() {
        CPUStateAnswer cpuState = new CPUStateAnswer("1") ;

        cpuState.setIp(Integer.toString(ip));
        cpuState.setSp(Integer.toString(sp));

        cpuState.setCarry(carry == true ? "1" : "0");
        cpuState.setFault(fault == true ? "1" : "0");
        cpuState.setZero(zero == true ? "1" : "0");

        cpuState.setRegA(Integer.toString(registers[0]));
        cpuState.setRegB(Integer.toString(registers[1]));
        cpuState.setRegC(Integer.toString(registers[2]));
        cpuState.setRegD(Integer.toString(registers[3]));

        cpuState.setState(state) ;

        List<Integer> memoryLineChange = new ArrayList<>(memory.getMemoryLineChange()) ;

        cpuState.setMemoryLineChange(memoryLineChange);
        memory.clearMemoryLineChange();

        List<int[]> newMemoryLine = new ArrayList<>(memory.getNewmemoryLine()) ;

        cpuState.setNewmemoryLine(newMemoryLine);
        memory.clearNewmemoryLine();

        return cpuState ;
    }


    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
