package com.test.opencv;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.highgui.Highgui;
import org.opencv.highgui.VideoCapture;
 class My_Panel extends JPanel{  
      private static final long serialVersionUID = 1L;  
      private BufferedImage image; 
      private BufferedImage pimage; 
      private BufferedImage eimage; 
      // Create a constructor method  
      public My_Panel(){  
           super();   
      }  
      
      public void start(){
    	  eimage = deepCopy(image);
    	  final int w = image.getWidth();
          final int h = image.getHeight();
    	  int [][] colors = new int[h][w];
    	  for(int i = 0; i < h; i++){
    		  for (int j = 0; j < w; j++){
    			  int[] color = {0,0,0};
    			  colors[i][j] = setColor(color);
    			  
    			  //colors[i][j] = add(oldColors[i][j], newColors[i][j]);
    			  
    			  //System.out.println(colors[i][j]);
    			  
    			  eimage.setRGB(j, i, colors[i][j]);
   			  
    		  }
    	  }
      }
      /*  
       * Converts/writes a Mat into a BufferedImage.  
       *   
       * @param matrix Mat of type CV_8UC3 or CV_8UC1  
       * @return BufferedImage of type TYPE_3BYTE_BGR or TYPE_BYTE_GRAY  
       */       
      public boolean matToBufferedImage(Mat matrix) {  
           MatOfByte mb=new MatOfByte();  
           Highgui.imencode(".jpg", matrix, mb);  
           try {  
                this.image = ImageIO.read(new ByteArrayInputStream(mb.toArray()));
           } catch (IOException e) {  
           // TODO Auto-generated catch block  
                e.printStackTrace();  
                return false; // Error  
           }  
        return true; // Successful  
      }  
      public void paintComponent(Graphics g){  
           super.paintComponent(g);   
           
           if (this.eimage==null) return; 
           g.drawImage(this.eimage,10,10,this.eimage.getWidth(),this.eimage.getHeight(), null); 
            
           //g.drawString("This is my custom Panel!",10,20);  
      }  
      public int[][] getPixels(BufferedImage image) {

           final byte[] pixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
           final int width = image.getWidth();
           final int height = image.getHeight();
           final boolean hasAlphaChannel = image.getAlphaRaster() != null;

          int[][] result = new int[height][width];
          if (hasAlphaChannel) {
             final int pixelLength = 4;
             for (int pixel = 0, row = 0, col = 0; pixel < pixels.length; pixel += pixelLength) {
                int argb = 0;
                argb += (((int) pixels[pixel] & 0xff) << 24); // alpha
                argb += ((int) pixels[pixel + 1] & 0xff); // blue
                argb += (((int) pixels[pixel + 2] & 0xff) << 8); // green
                argb += (((int) pixels[pixel + 3] & 0xff) << 16); // red
                result[row][col] = argb;
                col++;
                if (col == width) {
                   col = 0;
                   row++;
                }
             }
          } else {
             final int pixelLength = 3;
             for (int pixel = 0, row = 0, col = 0; pixel < pixels.length; pixel += pixelLength) {
                int argb = 0;
                argb += -16777216; // 255 alpha
                argb += ((int) pixels[pixel] & 0xff); // blue
                argb += (((int) pixels[pixel + 1] & 0xff) << 8); // green
                argb += (((int) pixels[pixel + 2] & 0xff) << 16); // red
                result[row][col] = argb;
                col++;
                if (col == width) {
                   col = 0;
                   row++;
                }
             }
          }
          
          return result;
       }
      
      public int[] getColors(int rgb){
    	  int red = (rgb >> 16) & 0x000000FF;
    	  int green = (rgb >>8 ) & 0x000000FF;
    	  int blue = (rgb) & 0x000000FF;
    	  int[] Colors = {red,green,blue};
		return Colors ;
      }
      
      public int setColor(int [] colors){
    	  int rgb = ((colors[0]&0x0ff)<<16)|((colors[1]&0x0ff)<<8)|(colors[2]&0x0ff);
    	  return rgb;
      }
      
      public int add(int color1, int color2){
    	  int[] list1 = getColors(color1);
    	  int[] list2 = getColors(color2);
    	  int[] list3 = new int[3];
    	  int black = 0;
    	  int white = 0;
    	  for(int i = 0; i < 3; i++){
    		  if(list2[i] < list1[i] + 30 && list2[i] > list1[i] - 30){
    		  	black++;
    		  }
    		  else {
    			white++;
    		  }
    	  }
    	  if(white < black){
			  list3[0] = 0;
			  list3[1] = 0;
			  list3[2] = 0;
		  } 
    	  else {
    		  list3[0] = 255;
			  list3[1] = 255;
			  list3[2] = 255;
    	  }
    	  int color = setColor(list3);
		  return color;
      }
      
      public BufferedImage deepCopy(BufferedImage bi) {

    	    ColorModel cm = bi.getColorModel();
    		boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
    		WritableRaster raster = bi.copyData(null);
    		return new BufferedImage(cm, raster, isAlphaPremultiplied,null);
    		  
    	  
      }
      int timer = 10;
      public void setPast(){
    	  if(timer % 10 != 0) {
    		  pimage = deepCopy(image);
    	  }
    	  else {
    		  //pimage = deepCopy(pimage);
    	  }
    		  
    	  timer++;
    		  
    	 
      }
      
      public void mapMotion() {
    	  
    	  final int w = image.getWidth();
          final int h = image.getHeight();
          int[][] oldColors = new int[h][w];
          int[][] newColors = new int[h][w];
    	  
    	  if (image != null) {
    		  newColors = getPixels(image);
 
    		 
    	  }
    	  if (pimage != null) {
    		  oldColors = getPixels(pimage);

    		  
    		  int [][] colors = new int[h][w];
    		  
        	  for(int i = 0; i < h; i++){
        		  for (int j = 0; j < w; j++){
        			  int[] color = {0,0,255};
        			  colors[i][j] = setColor(color);
        			  
        			  colors[i][j] = add(oldColors[i][j], newColors[i][j]);
        			  
        			  //System.out.println(colors[i][j]);
        			  
        			  eimage.setRGB(j, i, colors[i][j]);
       			  
        		  }
        	  }
    	  }
    	  
      }
     

 }  

 public class window {  
      public static void main(String arg[]){  
       // Load the native library.  
       System.loadLibrary("opencv_java246");       
       String window_name = "Capture";  
       JFrame frame = new JFrame(window_name);  
       frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
       frame.setSize(400,400);  
       My_Panel my_panel = new My_Panel();  
       frame.setContentPane(my_panel);       
       frame.setVisible(true);       
       boolean hasntStarted = true;
       //-- 2. Read the video stream  
        Mat webcam_image=new Mat();  
        VideoCapture capture =new VideoCapture(0);   
        if( capture.isOpened())  
           {  
        	int timer = 0;
            while( true )  
            {  
                 capture.read(webcam_image);  
              if( !webcam_image.empty() )  
               {   
                    frame.setSize(webcam_image.width()+40,webcam_image.height()+60); 
                    
                    //-- 3. Apply the classifier to the captured image  
                    
                   //-- 4. Display the image 
                    
                    my_panel.matToBufferedImage(webcam_image); // We could look at the error...
                    if(hasntStarted){
                    	 my_panel.start();
                    	 hasntStarted = false;
                    }
                    my_panel.mapMotion();
                
					my_panel.setPast();

                    my_panel.repaint();   
                    
               }  
               else  
               {   
                    System.out.println(" --(!) No captured frame -- Break!");   
                    break;   
               }  
              }  
             }  
             return;  
      }  
 }  