package com.cabrales.console.chip8;

import static java.lang.Integer.toHexString;
import javax.swing.table.DefaultTableModel;

/**
 *
 */
public class Chip8Model extends DefaultTableModel {

    private static final long serialVersionUID = 1L;

    public Chip8Model() {
        super(20, 2);
        super.setColumnIdentifiers(new String[]{"", "V"});
        super.setValueAt("V0", 0, 0);
        super.setValueAt("V1", 1, 0);
        super.setValueAt("V2", 2, 0);
        super.setValueAt("V3", 3, 0);
        super.setValueAt("V4", 4, 0);
        super.setValueAt("V5", 5, 0);
        super.setValueAt("V6", 6, 0);
        super.setValueAt("V7", 7, 0);
        super.setValueAt("V8", 8, 0);
        super.setValueAt("V9", 9, 0);
        super.setValueAt("VA", 10, 0);
        super.setValueAt("VB", 11, 0);
        super.setValueAt("VC", 12, 0);
        super.setValueAt("VD", 13, 0);
        super.setValueAt("VE", 14, 0);
        super.setValueAt("VF", 15, 0);
        super.setValueAt("", 16, 0);
        super.setValueAt("I", 17, 0);
        super.setValueAt("PC", 18, 0);
        super.setValueAt("SP", 19, 0);
    }
    
    public void update(Chip8 chip8) {
        setValueAt(toHexString(chip8.getV0()), 0, 1);
        setValueAt(toHexString(chip8.getV1()), 1, 1);
        setValueAt(toHexString(chip8.getV2()), 2, 1);
        setValueAt(toHexString(chip8.getV3()), 3, 1);
        setValueAt(toHexString(chip8.getV4()), 4, 1);
        setValueAt(toHexString(chip8.getV5()), 5, 1);
        setValueAt(toHexString(chip8.getV6()), 6, 1);
        setValueAt(toHexString(chip8.getV7()), 7, 1);
        setValueAt(toHexString(chip8.getV8()), 8, 1);
        setValueAt(toHexString(chip8.getV9()), 9, 1);
        setValueAt(toHexString(chip8.getVA()), 10, 1);
        setValueAt(toHexString(chip8.getVB()), 11, 1);
        setValueAt(toHexString(chip8.getVC()), 12, 1);
        setValueAt(toHexString(chip8.getVD()), 13, 1);
        setValueAt(toHexString(chip8.getVE()), 14, 1);
        setValueAt(toHexString(chip8.getVF()), 15, 1);
        setValueAt("", 16, 1);
        setValueAt(toHexString(chip8.getiRegister()), 17, 1);
        setValueAt(toHexString(chip8.getPc()), 18, 1);
        setValueAt(toHexString(chip8.getSp()), 19, 1);
    }
    

}
