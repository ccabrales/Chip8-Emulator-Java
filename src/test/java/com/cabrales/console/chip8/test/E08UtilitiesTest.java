package com.cabrales.console.chip8.test;

import com.cabrales.console.chip8.Chip8;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * Chip 8 has a few opcodes which are convenient utility methods.
 */
public class E08UtilitiesTest {

    private Chip8 chip8;

    @Before
    public void setup() {
        this.chip8 = new Chip8();
        this.chip8.execute(0x6064);
        this.chip8.execute(0x6127);
        this.chip8.execute(0x6212);
        this.chip8.execute(0x63AE);
        this.chip8.execute(0x64FF);
        this.chip8.execute(0x65B4);
        this.chip8.execute(0x6642);
        this.chip8.execute(0x6F25);
    }


    /**
     * FX33 : Convert the value in VX to decimal and store each digit in I, I+1,
     * and I+2 along with any leading 0s.
     */
    @Test
    public void testBCD() {
        chip8.execute(0xA200);

        chip8.execute(0xF033);
        byte[] memory = chip8.getMemory();
        assertEquals(1, memory[0x200]);
        assertEquals(0, memory[0x201]);
        assertEquals(0, memory[0x202]);


        chip8.execute(0xF133);
        memory = chip8.getMemory();
        assertEquals(0, memory[0x200]);
        assertEquals(3, memory[0x201]);
        assertEquals(9, memory[0x202]);

        chip8.execute(0xF433);
        memory = chip8.getMemory();
        assertEquals(2, memory[0x200]);
        assertEquals(5, memory[0x201]);
        assertEquals(5, memory[0x202]);

    }

    /**
     * Chip8 has the ability to copy a range of registers to memory.
     * FX55 : Store the values of registers V0 -> VX to memory addresses I -> I + X.
     * Set I to I + X + 1 afterward.
     */
    @Test
    public void copyToMemory() {
        chip8.execute(0xA200);
        chip8.execute(0xF355);

        byte[] memory = chip8.getMemory();

        assertEquals((byte)0x64, memory[0x200]);
        assertEquals((byte)0x27, memory[0x201]);
        assertEquals((byte)0x12, memory[0x202]);
        assertEquals((byte)0xAE, memory[0x203]);
        assertEquals(0x204, chip8.getiRegister());

    }

    /**
     * Chip8 has the ability to copy a range of memory to registers.
     * FX65 : Store the values of memory addresses I -> I + X to registers V0 -> VX.
     * Set I to I + X + 1 afterward.
     */
    @Test
    public void copyFromMemory() {
        chip8.execute(0xA200);
        chip8.execute(0xF355);

        byte[] memory = chip8.getMemory();

        assertEquals((byte)0x64, memory[0x200]);
        assertEquals((byte)0x27, memory[0x201]);
        assertEquals((byte)0x12, memory[0x202]);
        assertEquals((byte)0xAE, memory[0x203]);
        assertEquals(0x204, chip8.getiRegister());

        chip8.execute(0xA200);
        this.chip8.execute(0x6000);
        this.chip8.execute(0x6100);
        this.chip8.execute(0x6200);
        this.chip8.execute(0x6300);
        chip8.execute(0xF365);

        assertEquals((byte)0x64, chip8.getV0());
        assertEquals((byte)0x27, chip8.getV1());
        assertEquals((byte)0x12, chip8.getV2());
        assertEquals((byte)0xAE, (byte)chip8.getV3());
        assertEquals(0x204, chip8.getiRegister());


    }

}
