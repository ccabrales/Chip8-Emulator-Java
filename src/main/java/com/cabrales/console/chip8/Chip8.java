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
        loadFontset();
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
        int opCode = instruction & 0xF000;
        int x = (instruction & 0x0F00) >> 8;
        int y = (instruction & 0x00F0) >> 8;
        int nn = instruction & 0xFF;
        int nnn = instruction & 0xFFF;

        switch(opCode) {
            case 0x0000:
                switch (instruction) {
                    //CLS - Clear screen
                    case 0x00E0:
                        display = new byte[memory.length];
                        break;

                    //RET - Returns from subroutine
                    case 0x00EE:
                        pc = stack[--sp];
                        break;
                    default:
                        throw new UnsupportedOperationException("Unsupported opcode:" + Integer.toHexString(instruction));
                }
                break;

            // JP - addr
            case 0x1000:
                pc = nnn;
                break;

            // CALL - addr
            case 0x2000:
                stack[sp++] = pc;
                pc = nnn;
                break;

            // SE
            case 0x3000:
                if (getVX(x) == nn) {
                    pc += 2;
                }
                break;

            // SNE
            case 0x4000:
                if (getVX(x) != nn) {
                    pc += 2;
                }
                break;

            // SE
            case 0x5000:
                if (getVX(x) == getVX(y)) {
                    pc += 2;
                }
                break;

            // LD
            case 0x6000:
                setVX(x, nn);
                break;

            // ADD
            case 0x7000:
                setVX(x, getVX(x) + nn);
                break;

            case 0x8000:
                int low = instruction & 0xF;
                switch (low) {
                    // LD
                    case 0x0:
                        setVX(x, getVX(y));
                        break;

                    // OR
                    case 0x1:
                        setVX(x, getVX(x) | getVX(y));
                        break;

                    // AND
                    case 0x2:
                        setVX(x, getVX(x) & getVX(y));
                        break;

                    // XOR
                    case 0x3:
                        setVX(x, getVX(x) ^ getVX(y));
                        break;

                    // ADD
                    case 0x4:
                        int sum = getVX(x) + getVX(y);
                        setVX(0xF, sum > 255 ? 1 : 0);
                        setVX(x, sum);

                        if (getVX(x) > 255) {
                            setVX(x, getVX(x) - 255);
                        }
                        break;

                    // SUB
                    case 0x5:
                        break;
                    case 0x6:
                        break;
                    case 0x7:
                        break;
                    case 0xE:
                        break;
                }
                break;

            case 0x9000:
                break;
            case 0xA000:
                break;
            case 0xB000:
                break;
            case 0xC000:
                break;
            case 0xD000:
                break;
            case 0xE000:
                break;
            case 0xF000:
                break;
            default:
                throw new UnsupportedOperationException("Unsupported opcode:" + Integer.toHexString(instruction));
        }
    }

    public void cycle() {

    }

}
