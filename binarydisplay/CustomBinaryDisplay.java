/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package binarydisplay;


/**
 * A binary clock-display
 * 
 *  8   0  0  X
 *  4  00 00 00
 *  2  00 0X X0
 *  1  0X X0 0X
 *  
 *     01 12 29
 * 
 * @author bjornar.risdalen
 * @version 19.01.19
 */
public class CustomBinaryDisplay
{
    // instance variables - replace the example below with your own
    public BitDisplay hours;
    public BitDisplay minutes;
    public BitDisplay seconds;

    /**
     * Constructor for objects of class BinaryDisplay
     */
    public CustomBinaryDisplay()
    {
        hours = new BitDisplay(24);
        minutes = new BitDisplay(60);
        seconds = new BitDisplay(60);
    }

    public void tick() {
        seconds.tick();
        if(seconds.getValue() == 0) {
            minutes.tick();
            if(minutes.getValue() == 0) {
                hours.tick();
            }
        }
        printStats();
    }
    
    public void printStats() {
        System.out.println("Hours: " + hours.getValue());
        System.out.println("Minutes: " + minutes.getValue());
        System.out.println("Seconds: " + seconds.getValue());
    }
}

