package com.cabrales.console.chip8;

import com.cabrales.console.chip8.util.Audio;
import com.cabrales.console.chip8.util.Input;

import java.util.Arrays;
import java.util.Random;

/**
 * Created by Casey on 9/5/16.
 *
 * In reference to TDD tutorial from https://github.com/secondsun/chip8
 */
public class Chip8 {

    private static final int DISPLAY_HEIGHT = 32;
    private static final int DISPLAY_WIDTH = 64;
    private static final int MEM_SIZE = 4096;
    private final Random random = new Random();

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
        int y = (instruction & 0x00F0) >> 4;
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
                setVX(nn, x);
                break;

            // ADD
            case 0x7000:
                setVX(getVX(x) + nn, x);
                break;

            case 0x8000:
                int low = instruction & 0xF;
                switch (low) {
                    // LD
                    case 0x0:
                        setVX(getVX(y), x);
                        break;

                    // OR
                    case 0x1:
                        setVX(getVX(x) | getVX(y), x);
                        break;

                    // AND
                    case 0x2:
                        setVX(getVX(x) & getVX(y), x);
                        break;

                    // XOR
                    case 0x3:
                        setVX(getVX(x) ^ getVX(y), x);
                        break;

                    // ADD
                    case 0x4:
                        int sum = getVX(x) + getVX(y);
                        setVX(sum > 255 ? 1 : 0, 0xF);
                        setVX(sum, x);

                        if (getVX(x) > 255) {
                            setVX(getVX(x) - 255, x);
                        }
                        break;

                    // SUB
                    case 0x5:
                        int sub = getVX(x) - getVX(y);
                        setVX(getVX(x) > getVX(y) ? 1 : 0, 0xF);
                        setVX(sub, x);

                        if (getVX(x) < 0) {
                            setVX(getVX(x) + 256, x);
                        }
                        break;

                    // SHR
                    case 0x6:
                        setVX(getVX(x) & 0x1, 0xF);
                        setVX(getVX(x) >> 1, x);
                        break;

                    // SUBN
                    case 0x7:
                        setVX(getVX(y) - getVX(x), x);
                        if (getVX(y) > getVX(x)) {
                            setVX(getVX(x) + 256, x);
                            setVX(1, 0xF);
                        } else {
                            setVX(0, 0xF);
                        }
                        break;

                    // SHL
                    case 0xE:
                        setVX((getVX(x) >> 7) & 0x01, 0xF);
                        setVX(getVX(x) << 1, x);
                        break;
                    default:
                        throw new UnsupportedOperationException("Unsupported opcode:" + Integer.toHexString(instruction));
                }
                break;

            // SNE
            case 0x9000:
                if (getVX(x) != getVX(y)) {
                    pc += 2;
                }
                break;

            // LD
            case 0xA000:
                iRegister = nnn;
                break;

            // JP
            case 0xB000:
                pc = getV0() + nnn;
                break;

            // RND
            case 0xC000:
                setVX(random.nextInt(0xFF) & nn, x);
                break;

            // DRW
            case 0xD000:
                int xCoord = getVX(x);
                int yCoord = getVX(y);
                int height = instruction & 0x00F;

                setVX(0, 0xF);

                for (int i = 0; i < height; i++) {
                    byte pixelRow = getPixelRow(xCoord, yCoord + i);
                    writeNewDisplay(xCoord, yCoord + i, memory[iRegister + i]);
                    if ((memory[iRegister + i] & pixelRow) != 0) {
                        setVX(1, 0xF);
                    }
                }
                break;

            case 0xE000:
                switch(nn) {
                    // SKP
                    case 0x9E:
                        if (Input.read() == getVX(x)) {
                            pc += 2;
                        }
                        break;

                    // SKNP
                    case 0xA1:
                        if (Input.read() != getVX(x)) {
                            pc += 2;
                        }
                        break;
                    default:
                        throw new UnsupportedOperationException("Unsupported opcode:" + Integer.toHexString(instruction));
                }
                break;
            case 0xF000:
                switch(nn) {
                    // LD
                    case 0x07:
                        setVX(delayTimer, x);
                        break;

                    // LD
                    case 0x0A:
                        if (Input.read() == -1) {
                            pc -= 2;
                        } else {
                            setVX(Input.read(), x);
                        }
                        break;

                    // LD DT
                    case 0x15:
                        delayTimer = getVX(x);
                        break;

                    // LD ST
                    case 0x18:
                        soundTimer = getVX(x);
                        break;

                    // ADD I
                    case 0x1E:
                        iRegister += getVX(x);
                        break;

                    // LD F
                    case 0x29:
                        iRegister = getVX(x) * 5;
                        break;

                    // LD B
                    case 0x33:
                        int val = getVX(x);
                        for (int i = iRegister + 2; i >= iRegister - 2; i--) {
                            memory[i] = (byte) (val % 10);
                            val /= 10;
                        }
                        break;

                    // LD [I]
                    case 0x55:
                        for (int i = 0; i <= x; i++) {
                            memory[iRegister++] = (byte) getVX(i);
                        }
                        break;

                    // LD Vx
                    case 0x65:
                        for (int i = 0; i <= x; i++) {
                            setVX(memory[iRegister++], i);
                        }
                        break;
                    default:
                        throw new UnsupportedOperationException("Unsupported opcode:" + Integer.toHexString(instruction));
                }
                break;
            default:
                throw new UnsupportedOperationException("Unsupported opcode:" + Integer.toHexString(instruction));
        }
    }

    public void cycle() {
        int currInstruction = ((memory[pc++] << 8) & 0xFF00 | (memory[pc++] & 0xFF));

        long currTime = System.currentTimeMillis();
        if (currTime > step) {
            if (delayTimer > 0) {
                delayTimer--;
            }
            if (soundTimer > 0) {
                soundTimer--;
                Audio.play();
            } else {
                Audio.stop();
            }

            step = currTime + (1000 / 60);
        }

        execute(currInstruction);
    }

    private byte getPixelRow(int x, int y) {
        x %= 64;
        y %= 32;
        byte res = display[x + y * 64];

        for (int i = 1; i < 8; i++) {
            res |= (display[(x + i) % 64 + y * 64] << (8 - i));
        }

        return res;
    }

    private void writeNewDisplay(int x, int y, byte loc) {
        x %= 64;
        y %= 32;

        display[x + y * 64] ^= (byte) ((loc & 0b10000000) >> 7);
        display[(1 + (x)) % 64 + (y) * 64] ^= (byte) ((loc & 0b01000000) >> 6);
        display[(2 + (x)) % 64 + (y) * 64] ^= (byte) ((loc & 0b00100000) >> 5);
        display[(3 + (x)) % 64  + (y) * 64] ^= (byte) ((loc & 0b00010000) >> 4);
        display[(4 + (x)) % 64  + (y) * 64] ^= (byte) ((loc & 0b00001000) >> 3);
        display[(5 + (x)) % 64  + (y) * 64] ^= (byte) ((loc & 0b00000100) >> 2);
        display[(6 + (x)) % 64  + (y) * 64] ^= (byte) ((loc & 0b00000010) >> 1);
        display[(7 + (x)) % 64 + (y) * 64] ^= (byte) ((loc & 0b00000001));
    }

}
