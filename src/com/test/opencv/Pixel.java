package com.test.opencv;
import java.util.Arrays;

/*
 * This file is adapted by UPenn CIS 120 course staff from code by
 * Richard Wicentowski and Tia Newhall (2005)
 * 
 * You do not need to modify this file.
 */

/** 
 * A point of color. 
 *
 * Pixels are represented as three color components (Red, Green, and Blue)
 * which should all be ints in the range of 0-255. Lower values mean less
 * color; higher means more. For example, new Pixel(255,255,255) represents
 * white, new Pixel(0,0,0) is black, and new Pixel(0,255,0) represents green.
 *
 * This data structure is immutable. Once pixels have been created
 * they cannot be modified.
 */
public class Pixel implements Comparable<Pixel> {
   
    /** Some constant colors */
    public static final Pixel BLACK = new Pixel(0,0,0);
    public static final Pixel BLUE = new Pixel(0,0,255);
    public static final Pixel RED = new Pixel(255,0,0);
    public static final Pixel GREEN = new Pixel(0,255,0);
    public static final Pixel WHITE = new Pixel(255,255,255);
   

    private int[] component = new int[]{0, 0, 0} ;

    /** creates a new black pixel */  
    Pixel() { this (0,0,0); }

    /** creates a new color pixel with the specified color intensities */
    Pixel(int r, int g, int b) {
        component = new int[3];
        component[0] = r;
        component[1] = g;
        component[2] = b;
    }

    /** creates a new pixel with the specified component intensities */ 
    Pixel(int[] c) {
        component = new int[c.length];
        for (int i = 0; i < c.length; i++) {
            component[i] = c[i];
        }
    }

    /** create a copy of the pixel with components bounded between 0 and 255 */
    public Pixel clip() {
        int[] c = new int[3];
        for (int i = 0; i < 3; i ++){
            if (component[i] < 0) 
                { c[i] = 0; }
            else if (component[i] > 255) 
                { c [i] = 255; }
            else 
                { c[i] = component[i]; }
        }
        return new Pixel(c);
    }
   
    /** gets the red component */ 
    public int getRed() { 
        return component[0];
    }

    /** gets the green component */ 
    public int getGreen() { 
        return component[1];
    }

    /** gets the blue component */ 
    public int getBlue() { 
        return component[2];
    }

    /** get the array of components */ 
    public int[] getComponents() {
        return Arrays.copyOf(component, 3);
    }
   
    /**
     * Determines how similar this pixel is to another.
     *
     * @param px The other pixel with which to compare
     * @return The sum of the differences in each of the color components
     */
    public int distance(Pixel px) {
        return
            Math.abs(getRed() - px.getRed()) +
            Math.abs(getBlue() - px.getBlue()) +
            Math.abs(getGreen() - px.getGreen());
    }

    /** Returns a String representation of this pixel */ 
    public String toString() {
        String s = "(";
        for (int k = 0; k < component.length; k++) {
            s += component[k];
            if (k != component.length - 1) { s += ","; }
        }
        return s + ")";
    }

    /** 
     * Checks whether this pixel has the same components as the given Object.
     * If the object is not a Pixel, then this returns false.
     */ 
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }
        if (other instanceof Pixel) {  
            Pixel o = (Pixel) other;
       
            if (o.component.length == component.length) {
      
                for (int k = 0; k < component.length; k++) {
                    if (o.component[k] != component[k])
                        return false;
                }
                return true;
            }
        }
        return false;
    }
    public int hashCode() {
        int h = 0;
        for (int k = 0; k < component.length; k++) {
            h += k*255 + component[k];
        }
        return h;
    }

    public int compareTo(Pixel o) {
        int rc = getRed() - o.getRed();
        int gc = getGreen() - o.getGreen();
        int bc = getBlue() - o.getBlue();
        
        if (rc != 0) {
            return rc;
        } else if (gc != 0) {
            return gc;
        } else {
            return bc;
        }
    }

}
