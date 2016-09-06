package com.cabrales.console.chip8.util;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;
import com.cabrales.console.chip8.Chip8;

/**
 *
 */
public class Chip8DisplayPanel extends JPanel {
    
    private static final long serialVersionUID = 0x203920L;

    private Chip8 chip8 = new Chip8();
    
    @Override
    public void paint(Graphics g) {
        int width = super.getWidth();
        int height = super.getHeight();
        int pixelWidth = width / 64;
        int pixelHeight = height / 32;
        byte[] video = chip8.getDisplay();
        for (int x = 0; x < 64; x++) {
            for (int y = 0; y < 32; y++) {
                if (video[y * 64 + x]  == 0) {
                    g.setColor(Color.BLACK);
                } else {
                    g.setColor(Color.WHITE);
                }
                g.fillRect(x * pixelWidth, y*pixelHeight , pixelWidth, pixelHeight);
            }
        }
    }

    public void setChip8(Chip8 chip8) {
        this.chip8 = chip8;
    }
    
    
    
    
}
