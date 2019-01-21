/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package binarydisplay;

import java.awt.Graphics;
import java.awt.Color;
/**
 * BitDisplay is a graphical class containing an array of Bits, with methods to
 * paint them on a screen
 *
 * @author bjornar.risdalen
 * @version 19.01.19
 */
public class BitDisplay
{
    // 
    private int rollOverLimit;
    private int value;
    private Bit[] bits;
    public boolean[] onArray;

    /**
     * Constructor for objects of class BitDisplay
     */
    public BitDisplay(int rollOverLimit) {
        this.rollOverLimit = rollOverLimit;
        this.value = 0;
        this.bits = new Bit[7];
        this.onArray = new boolean[7];
        init();
    }
    
    private void init() {
        for(int i = 0; i < bits.length; i++) {
            bits[i] = new Bit("Bit_" + i);
        }
        
        for(int i = 0; i < bits.length; i++) {
            
            switch(i) {
                case 0: 
                    bits[0].setParentNode(bits[1]);
                    break;
                case 6:
                    bits[6].setChildNode(bits[5]);
                    break;
                default:
                    bits[i].setChildNode(bits[i-1]);
                    bits[i].setParentNode(bits[i+1]);
                    break;
            }
        }
    }
    
    public void tick() {
        //Common for all displays
        if(bits[3].isOn() && bits[0].isOn() && value < 59) {
            bits[3].toggle();
            bits[0].setOn(false);
        } else {
            //If its an hour-display
            if(rollOverLimit == 24) {
                //Check only the highest bit to optimize
                if(bits[5].isOn()) {
                    if(bits[0].isOn() && bits[1].isOn()) {
                        resetBits();
                    } else {
                        bits[0].toggle();
                    }
                } else {
                    bits[0].toggle();
                }
            }   
            //If its a minute- or second-display
            if(rollOverLimit == 60) {
                /* Check only the highest bit first, then bit 4 before the remainding
                 * bits to optimize */
                if(bits[6].isOn()) {
                    if(bits[4].isOn()) {
                        if(bits[3].isOn() && bits[0].isOn()) {
                            resetBits();
                        } else {
                            bits[0].toggle();
                        }
                    } else {
                        bits[0].toggle();
                    }
                } else {
                    bits[0].toggle();
                }
            }
        }
        update();
    }
    
    public int getValue() {
        return this.value;
    }
    
    public int getSize() {
        return this.bits.length;
    }
    
    private void update() {
        for(int i = 0; i < bits.length; i++) {
            if(bits[i].isOn()) {
                onArray[i] = true;
            } else {
                onArray[i] = false;
            }
        }
        
        updateValue();
    }
    
    private void updateValue() {
        int temp = 0;
        
        for(int i = 0; i < bits.length; i++) {
            switch(i) {
                case 0:
                    if(bits[0].isOn()) {
                        temp += 1;
                    }
                    break;
                case 1:
                    if(bits[1].isOn()) {
                        temp += 2;
                    }
                    break;
                case 2:
                    if(bits[2].isOn()) {
                        temp += 4;
                    }
                    break;
                case 3:
                    if(bits[3].isOn()) {
                        temp += 8;
                    }
                    break;
                case 4:
                    if(bits[4].isOn()) {
                        temp += 10;
                    }
                    break;
                case 5:
                    if(bits[5].isOn()) {
                        temp += 20;
                    }
                    break;
                case 6:
                    if(bits[6].isOn()) {
                        temp += 40;
                    }
                    break;       
            }
        }
        
        value = temp;
    }
    
    public void reset() {
        resetBits();
    }
    
    private void resetBits() {
        for(Bit b : bits) {
            b.setOn(false);
        }
        updateValue();
    }
    
    public void printStats() {
        for(Bit b : bits) {
            b.printStats();
        }
        System.out.println("Value: " + value);
    }
}
