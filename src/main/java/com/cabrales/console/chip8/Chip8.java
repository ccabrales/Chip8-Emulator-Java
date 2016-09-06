package com.cabrales.console.chip8;

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * Created by Casey on 9/5/16.
 *
 * In reference to TDD tutorial from https://github.com/secondsun/chip8
 */
public class Chip8 {

    private static final int DISPLAY_HEIGHT = 32;
    private static final int DISPLAY_WIDTH = 64;
    private static final int MEM_SIZE = 4096;

    private int pc = 0x200;
    private final byte[] memory;
    private final int[] stack = new int[16];
    private int iRegister = 0;
    private byte[] display = new byte[DISPLAY_HEIGHT * DISPLAY_WIDTH];
    private int sp = 0;
    private final int[] vRegisters = new int[0x10];
    private int delayTimer = 0;
    private int soundTimer = 0;
    private long step = 0;


    public Chip8() {
        this.memory = new byte[MEM_SIZE];
    }

    public Chip8(byte[] memory) {
        if (memory.length > MEM_SIZE) {
            throw new IllegalArgumentException("Memory must not be greater than 4096 bytes");
        }
        this.memory = memory;
        loadFontset();
    }

    private void loadFontset() {
        int[] fontSet = new int[]{
            0xF0, 0x90, 0x90, 0x90, 0xF0, // 0
            0x20, 0x60, 0x20, 0x20, 0x70, // 1
            0xF0, 0x10, 0xF0, 0x80, 0xF0, // 2
            0xF0, 0x10, 0xF0, 0x10, 0xF0, // 3
            0x90, 0x90, 0xF0, 0x10, 0x10, // 4
            0xF0, 0x80, 0xF0, 0x10, 0xF0, // 5
            0xF0, 0x80, 0xF0, 0x90, 0xF0, // 6
            0xF0, 0x10, 0x20, 0x40, 0x40, // 7
            0xF0, 0x90, 0xF0, 0x90, 0xF0, // 8
            0xF0, 0x90, 0xF0, 0x10, 0xF0, // 9
            0xF0, 0x90, 0xF0, 0x90, 0x90, // A
            0xE0, 0x90, 0xE0, 0x90, 0xE0, // B
            0xF0, 0x80, 0x80, 0x80, 0xF0, // C
            0xE0, 0x90, 0x90, 0x90, 0xE0, // D
            0xF0, 0x80, 0xF0, 0x80, 0xF0, // E
            0xF0, 0x80, 0xF0, 0x80, 0x80 // F
        };

        for (int i = 0; i < fontSet.length; i++) {
            memory[i] = (byte) fontSet[i];
        }

    }

    public int getPc() {
        return pc;
    }

    public byte[] getMemory() {
        return Arrays.copyOf(memory, memory.length);
    }

    public int getiRegister() {
        return iRegister;
    }

    public byte[] getDisplay() {
        return Arrays.copyOf(display, display.length);
    }

    public int getSp() {
        return sp;
    }

    public int getVX(int regNum) {
        return vRegisters[regNum] & 0x000000FF;
    }

    public void setVX(int val, int regNum) {
        vRegisters[regNum] = (val & 0x000000FF);
    }

    public int getV0() {
        return vRegisters[0] & 0xFF;
    }

    public int getV1() {
        return vRegisters[1] & 0xFF;
    }

    public int getV2() {
        return vRegisters[0x2] & 0xFF;
    }

    public int getV3() {
        return vRegisters[0x3] & 0xFF;
    }

    public int getV4() {
        return vRegisters[0x4] & 0xFF;
    }

    public int getV5() {
        return vRegisters[0x5] & 0xFF;
    }

    public int getV6() {
        return vRegisters[0x6] & 0xFF;
    }

    public int getV7() {
        return vRegisters[0x7] & 0xFF;
    }

    public int getV8() {
        return vRegisters[0x8] & 0xFF;
    }

    public int getV9() {
        return vRegisters[0x9] & 0xFF;
    }

    public int getVA() {
        return vRegisters[0xA] & 0xFF;
    }

    public int getVB() {
        return vRegisters[0xB] & 0xFF;
    }

    public int getVC() {
        return vRegisters[0xC] & 0xFF;
    }

    public int getVD() {
        return vRegisters[0xD] & 0xFF;
    }

    public int getVE() {
        return vRegisters[0xE] & 0xFF;
    }

    public int getVF() {
        return vRegisters[0xF] & 0xFF;
    }

    // Where the instruction given is executed according to the Chip8 specs
    public void execute(int instruction) {
        throw new UnsupportedOperationException("Unsupported opcode:" + Integer.toHexString(instruction));
    }

    public void cycle() {

    }

}
