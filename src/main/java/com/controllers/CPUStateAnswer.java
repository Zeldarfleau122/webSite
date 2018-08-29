package com.controllers;

import java.util.List;

public class CPUStateAnswer {
    private String success ;
    private String sp ;
    private String ip ;
    private String carry ;
    private String fault ;
    private String zero ;
    private String regA ;
    private String regB ;
    private String regC ;
    private String regD ;
    private String output ;

    private int state ;

    private List<Integer> memoryLineChange ;
    private List<int[]> newmemoryLine ;


    public CPUStateAnswer(String success) {
        this.success = success;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getSp() {
        return sp;
    }

    public void setSp(String sp) {
        this.sp = sp;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getCarry() {
        return carry;
    }

    public void setCarry(String carry) {
        this.carry = carry;
    }

    public String getFault() {
        return fault;
    }

    public void setFault(String fault) {
        this.fault = fault;
    }

    public String getZero() {
        return zero;
    }

    public void setZero(String zero) {
        this.zero = zero;
    }

    public String getRegA() {
        return regA;
    }

    public void setRegA(String regA) {
        this.regA = regA;
    }

    public String getRegB() {
        return regB;
    }

    public void setRegB(String regB) {
        this.regB = regB;
    }

    public String getRegC() {
        return regC;
    }

    public void setRegC(String regC) {
        this.regC = regC;
    }

    public String getRegD() {
        return regD;
    }

    public void setRegD(String regD) {
        this.regD = regD;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public List<Integer> getMemoryLineChange() {
        return memoryLineChange;
    }

    public void setMemoryLineChange(List<Integer> memoryLineChange) {
        this.memoryLineChange = memoryLineChange;
    }

    public List<int[]> getNewmemoryLine() {
        return newmemoryLine;
    }

    public void setNewmemoryLine(List<int[]> newmemoryLine) {
        this.newmemoryLine = newmemoryLine;
    }
}
