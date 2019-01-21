/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package binarydisplay;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.*;

/**
 * Write a description of class BinaryClock here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class BinaryClock
{
    // instance variables - replace the example below with your own
    private JFrame frame;
    private JPanel containerPanel;
    private DrawPanel binaryPanel;
    private JPanel labelPanel;
    private JLabel label;
    private CustomBinaryDisplay binaryClock;
    private boolean clockRunning = false;
    private Thread timerThread;
    String hours = "";
    String minutes = "";
    String seconds = "";
    private Point[] hourLocations;
    private Point[] minutesLocations;
    private Point[] secondsLocations;

    /**
     * Constructor for objects of class BinaryClock
     */
    public BinaryClock() {
        // initialise instance variables
        binaryClock = new CustomBinaryDisplay();
        timerThread = new TimerThread();
        frame = new JFrame("Binary Clock");
        containerPanel = new JPanel();
        containerPanel.setLayout(new BorderLayout());
        binaryPanel = new DrawPanel();
        labelPanel = new JPanel();
        labelPanel.setLayout(new FlowLayout());
        
        hours = "0" + binaryClock.hours.getValue();
        minutes = "0" + binaryClock.minutes.getValue();
        seconds = "0" + binaryClock.seconds.getValue();
        
        hourLocations = new Point[7];
        minutesLocations = new Point[7];
        secondsLocations = new Point[7];
        
        label = new JLabel();
        setLabel(label, hours, minutes, seconds);
        labelPanel.add(label);
        
        containerPanel.add(binaryPanel, BorderLayout.CENTER);
        containerPanel.add(labelPanel, BorderLayout.SOUTH);
        frame.add(containerPanel);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        binaryPanel.repaint();
        frame.setVisible(true);
    }

    public void start() {
        clockRunning = true;
        timerThread.start();
    }
    
    public void update() {
        int hoursVal = binaryClock.hours.getValue();
        int minutesVal = binaryClock.minutes.getValue();
        int secondsVal = binaryClock.seconds.getValue();
        
        hours = (hoursVal < 10) ? "0" + binaryClock.hours.getValue() : "" + binaryClock.hours.getValue();
        minutes = (minutesVal < 10) ? "0" + binaryClock.minutes.getValue() : "" + binaryClock.minutes.getValue();
        seconds = (secondsVal < 10) ? "0" + binaryClock.seconds.getValue() : "" + binaryClock.seconds.getValue();
        
        setLabel(label, hours, minutes, seconds);
        
        binaryPanel.repaint();
    }
    
    public void setLabel(JLabel label, String hours, String minutes, String seconds) {
        label.setText(hours + " : " + minutes + " : " + seconds);
    }
    
    class TimerThread extends Thread {       
        
        public void run() {
            while(clockRunning) {
                binaryClock.tick();
                update();
                pause();
            }
        }
        
        public void pause() {
            try {
                Thread.sleep(10);
            } catch(InterruptedException ie) {
                System.out.println(ie.getStackTrace());
            }
        }
    }
    
    class DrawPanel extends JPanel {
        int prefferedWidth = 600;
        int prefferedHeight = 400;
        
        int offset = 100;
        
        int bitWidth = 75;
        int bitHeight = 75;
        
        int distFromR1 = offset*1;
        int distFromB1 = offset;
        int distFromR2 = distFromR1 + bitWidth;
        int distFromB2 = distFromB1;
        
        int distFromR3 = offset*3;
        int distFromB3 = offset;
        int distFromR4 = distFromR3 + bitWidth;
        int distFromB4 = distFromB3;
        
        int distFromR5 = offset*5;
        int distFromB5 = offset;
        int distFromR6 = distFromR5 + bitWidth;
        int distFromB6 = distFromB5;
        
        boolean first = true;
        
        public DrawPanel() {
            this.setPreferredSize(new Dimension(prefferedWidth, prefferedHeight));
            repaint();
        }
        
        
        public void paintBits(Graphics g) {
            for(int i = 0; i < 7; i++) {
                
                if(binaryClock.seconds.onArray[i]) {
                    g.setColor(Color.green);
                    g.fillRect(secondsLocations[i].x, secondsLocations[i].y, bitWidth, bitHeight);
                    binaryPanel.repaint(secondsLocations[i].x, secondsLocations[i].y, bitWidth, bitHeight);
                }
                if(!binaryClock.seconds.onArray[i]) {
                    g.setColor(Color.lightGray);
                    g.fillRect(secondsLocations[i].x, secondsLocations[i].y, bitWidth, bitHeight);
                    binaryPanel.repaint(secondsLocations[i].x, secondsLocations[i].y, bitWidth, bitHeight);
                }
                if(binaryClock.minutes.onArray[i]) {
                    g.setColor(Color.green);
                    g.fillRect(minutesLocations[i].x, minutesLocations[i].y, bitWidth, bitHeight);
                    binaryPanel.repaint(minutesLocations[i].x, minutesLocations[i].y, bitWidth, bitHeight);
                }
                if(!binaryClock.minutes.onArray[i]) {
                    g.setColor(Color.lightGray);
                    g.fillRect(minutesLocations[i].x, minutesLocations[i].y, bitWidth, bitHeight);
                    binaryPanel.repaint(minutesLocations[i].x, minutesLocations[i].y, bitWidth, bitHeight);
                }
                if(binaryClock.hours.onArray[i]) {
                    g.setColor(Color.green);
                    g.fillRect(hourLocations[i].x, hourLocations[i].y, bitWidth, bitHeight);
                    binaryPanel.repaint(hourLocations[i].x, hourLocations[i].y, bitWidth, bitHeight);
                }
                if(!binaryClock.hours.onArray[i]) {
                    g.setColor(Color.lightGray);
                    g.fillRect(hourLocations[i].x, hourLocations[i].y, bitWidth, bitHeight);
                    binaryPanel.repaint(hourLocations[i].x, hourLocations[i].y, bitWidth, bitHeight);
                }
            }
        }
        
        public void initialPaint(Graphics g) {
            g.setColor(Color.lightGray);
            
            secondsLocations[0] = new Point(prefferedWidth-distFromR1, prefferedHeight-distFromB1);
            secondsLocations[1] = new Point(prefferedWidth-distFromR1, prefferedHeight-distFromB1-(1*bitHeight));
            secondsLocations[2] = new Point(prefferedWidth-distFromR1, prefferedHeight-distFromB1-(2*bitHeight));
            secondsLocations[3] = new Point(prefferedWidth-distFromR1, prefferedHeight-distFromB1-(3*bitHeight));
            secondsLocations[4] = new Point(prefferedWidth-distFromR2, prefferedHeight-distFromB2);
            secondsLocations[5] = new Point(prefferedWidth-distFromR2, prefferedHeight-distFromB2-(1*bitHeight));
            secondsLocations[6] = new Point(prefferedWidth-distFromR2, prefferedHeight-distFromB2-(2*bitHeight));
            
            g.fillRect(secondsLocations[0].x, secondsLocations[0].y, bitWidth, bitHeight);
            g.fillRect(secondsLocations[1].x, secondsLocations[1].y, bitWidth, bitHeight);
            g.fillRect(secondsLocations[2].x, secondsLocations[2].y, bitWidth, bitHeight);
            g.fillRect(secondsLocations[3].x, secondsLocations[3].y, bitWidth, bitHeight);

            g.fillRect(secondsLocations[4].x, secondsLocations[4].y, bitWidth, bitHeight);
            g.fillRect(secondsLocations[5].x, secondsLocations[5].y, bitWidth, bitHeight);
            g.fillRect(secondsLocations[6].x, secondsLocations[6].y, bitWidth, bitHeight);
            
            minutesLocations[0] = new Point(prefferedWidth-distFromR3, prefferedHeight-distFromB3);
            minutesLocations[1] = new Point(prefferedWidth-distFromR3, prefferedHeight-distFromB3-(1*bitHeight));
            minutesLocations[2] = new Point(prefferedWidth-distFromR3, prefferedHeight-distFromB3-(2*bitHeight));
            minutesLocations[3] = new Point(prefferedWidth-distFromR3, prefferedHeight-distFromB3-(3*bitHeight));
            minutesLocations[4] = new Point(prefferedWidth-distFromR4, prefferedHeight-distFromB4);
            minutesLocations[5] = new Point(prefferedWidth-distFromR4, prefferedHeight-distFromB4-(1*bitHeight));
            minutesLocations[6] = new Point(prefferedWidth-distFromR4, prefferedHeight-distFromB4-(2*bitHeight));
            
            g.fillRect(minutesLocations[0].x, minutesLocations[0].y, bitWidth, bitHeight);
            g.fillRect(minutesLocations[1].x, minutesLocations[1].y, bitWidth, bitHeight);
            g.fillRect(minutesLocations[2].x, minutesLocations[2].y, bitWidth, bitHeight);
            g.fillRect(minutesLocations[3].x, minutesLocations[3].y, bitWidth, bitHeight);

            g.fillRect(minutesLocations[4].x, minutesLocations[4].y, bitWidth, bitHeight);
            g.fillRect(minutesLocations[5].x, minutesLocations[5].y, bitWidth, bitHeight);
            g.fillRect(minutesLocations[6].x, minutesLocations[6].y, bitWidth, bitHeight);
            
            hourLocations[0] = new Point(prefferedWidth-distFromR5, prefferedHeight-distFromB5);
            hourLocations[1] = new Point(prefferedWidth-distFromR5, prefferedHeight-distFromB5-(1*bitHeight));
            hourLocations[2] = new Point(prefferedWidth-distFromR5, prefferedHeight-distFromB5-(2*bitHeight));
            hourLocations[3] = new Point(prefferedWidth-distFromR5, prefferedHeight-distFromB5-(3*bitHeight));
            hourLocations[4] = new Point(prefferedWidth-distFromR6, prefferedHeight-distFromB6);
            hourLocations[5] = new Point(prefferedWidth-distFromR6, prefferedHeight-distFromB6-(1*bitHeight));
            hourLocations[6] = new Point(prefferedWidth-distFromR6, prefferedHeight-distFromB6-(2*bitHeight));
            
            g.fillRect(hourLocations[0].x, hourLocations[0].y, bitWidth, bitHeight);
            g.fillRect(hourLocations[1].x, hourLocations[1].y, bitWidth, bitHeight);
            g.fillRect(hourLocations[2].x, hourLocations[2].y, bitWidth, bitHeight);
            g.fillRect(hourLocations[3].x, hourLocations[3].y, bitWidth, bitHeight);

            g.fillRect(hourLocations[4].x, hourLocations[4].y, bitWidth, bitHeight);
            g.fillRect(hourLocations[5].x, hourLocations[5].y, bitWidth, bitHeight);
            g.fillRect(hourLocations[6].x, hourLocations[6].y, bitWidth, bitHeight);
        }
        
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            if(first) {
                initialPaint(g);
                first = false;
            }
            
            paintBits(g);
        }
    }
    
   public static void main(String[] args) {
       BinaryClock clock = new BinaryClock();
       clock.start();
   } 
}
