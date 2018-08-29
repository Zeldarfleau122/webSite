package com.AssemblerEmulator;

import java.util.ArrayList;
import java.util.List;

/**
 * @// TODO: 29/05/2018 MACRO memory.length / ERREUR THROWS !!!
 */
public class Memory {
    private int[][] memory ;              // Size : 256 ( 8 bit )

    private int inputAddress ;

    private List<Integer> memoryLineChange ;
    private List<int[]> newmemoryLine ;

    public Memory() {
        this.memory = new int[16][16] ;
        this.inputAddress = -1 ;

        memoryLineChange = new ArrayList<Integer>() ;
        newmemoryLine = new ArrayList<int[]>() ;
    }

    public Memory(int[][] memory) {
        this.memory = memory ;

        memoryLineChange = new ArrayList<Integer>() ;
        newmemoryLine = new ArrayList<int[]>() ;
    }

    public int load(int ip) {
        if (ip < 0 || 256<=ip) {
            // THROWS !!
            return 0 ;
        } else return this.memory[ip/16][ip%16] ;
    }

    public void store(int address, int value) {
        if (address < 0 || 256 <= address) {
            // THROW ERROR !!!!
        }

        memory[address/16][address%16] = value ;

        updateMemoryChanges(address) ;
    }

    private void updateMemoryChanges(int address) {
        if (!memoryLineChange.contains(address/16)) {
            memoryLineChange.add(address/16) ;
            newmemoryLine.add(memory[address/16]) ;
        }
    }

    public int[][] getMemory() {
        return this.memory ;
    }

    public void reset() {
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                this.memory[i][j] = 0 ;
            }
        }
    }

    public void nextInputAddress(int inputAddress) {
        this.inputAddress = inputAddress ;
    }

    public void writeInput(String input) {
        if (this.inputAddress != -1) {
            for (int i = 0; i<input.length() && (inputAddress + i < 256); i++)
                this.store(this.inputAddress+i, input.charAt(i));               // !!!!!!! ERRRROOORRR !!!!

            this.inputAddress = -1 ;
        }
    }

    public void writeInput(int[] input) {
        if (this.inputAddress != -1) {
            for (int i = 0; i<input.length && (inputAddress + i < 256); i++)
                this.store(this.inputAddress+i, input[i]);               // !!!!!!! ERRRROOORRR !!!!

            this.inputAddress = -1 ;
        }
    }

    public List<Integer> getMemoryLineChange() {
        return this.memoryLineChange ;
    }

    public void clearMemoryLineChange() {
        this.memoryLineChange.clear();
    }

    public List<int[]> getNewmemoryLine() {
        return this.newmemoryLine ;
    }

    public void clearNewmemoryLine() {
        this.newmemoryLine.clear();
    }
}
