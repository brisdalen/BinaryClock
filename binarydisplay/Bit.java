/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package binarydisplay;


/**
 * Bit has a boolean value, and a parent and child node
 * 
 * @author bjornar.risdalen
 * @version 18.01.19
 */
public class Bit
{
    private String id;
    private boolean on = false;
    private Bit parentNode;
    private Bit childNode;

    /**
     * Default constructor for objects of class Bit
     */
    public Bit() {
        this.parentNode = null;
        this.childNode = null;
    }
    
    /**
     * Constructor for objects of class Bit using 1 parameter String id
     */
    public Bit(String id) {
        this.id = id;
        this.childNode = null;
    }
    
    /**
     * Constructor for objects of class Bit using 1 parameter Bit parentNode
     */
    public Bit(Bit parentNode) {
        this.parentNode = parentNode;
        this.childNode = null;
    }
    
    /**
     * Constructor for objects of class Bit using 2 parameters
     */
    public Bit(Bit parentNode, Bit childNode) {
        this.parentNode = parentNode;
        this.childNode = childNode;
    }
    
    public void toggle() {
        if(parentNode != null && on) {
            parentNode.toggle();
        }
        on = !on;
    }
    
    public boolean isOn() {
        return on;
    }
    
    public void setOn(boolean on) {
        this.on = on;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getId() {
        return this.id;
    }
    
    public void setParentNode(Bit node) {
        this.parentNode = node;
    }
    
    public Bit getParentNode() {
        return parentNode;
    }
    
    public void setChildNode(Bit node) {
        this.childNode = node;
    }
    
    public Bit getChildNode() {
        return childNode;
    }
    
    public void printStats() {
        System.out.println(id);
        System.out.println("on: " + on);
        if(parentNode != null) {
            System.out.println("  parentNode: " + parentNode.id);
        }
        if(childNode != null) {
            System.out.println("  childNode: " + childNode.id);
        }
        System.out.println("");
    }
}
