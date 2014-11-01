import java.awt.Canvas;
import java.awt.Color;
import java.awt.Frame;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.awt.image.RescaleOp;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class Instagram extends Canvas{
	/**
	 * Initialize variables, for the name of the image files, the original and final ArrayLists of images, and all of the various 
	 * images needed in between creating the initial and final version of the image
	 */
	//file names of images
	public final String JPEG_NAME_INIT="img.jpg";
	public final String TEXTURE_NAME="texture-1.png";
	public final String TIGHT_TEXTURE_NAME="Noise.jpg";
	public final String JPEG_NAMES[]={"img2.jpg","img3.jpg","img4.jpg"};
	//ArrayLists to hold the original and final images
	private ArrayList<BufferedImage> finalImages=new ArrayList<BufferedImage>();
	private ArrayList<BufferedImage> origImages=new ArrayList<BufferedImage>();
	//Various images needed either to overlay or to contain the images in between the initial and final versions
	private BufferedImage initjpeg;
	private BufferedImage jpeg;
	private BufferedImage newjpeg;
	private BufferedImage sharpenjpeg;
	private BufferedImage blurjpeg;
	private BufferedImage speck;
	private BufferedImage tiledspeck;
	private BufferedImage gradient;
	private BufferedImage gradientblur;
	private BufferedImage noise;
	private BufferedImage tilednoise;
	
	/**
	 * Main method; creates a new Instagram object, sets window size, background color, and closing command 
	 * @param args
	 */
	public static void main(String[] args) {
        Frame mf=new Frame("Hi");//set frame name
        Instagram aCanvas=new Instagram();//create new Instagram object
        mf.add(aCanvas);//add object to the frame
        mf.setSize(2000,1000);//set the initial size of the frame
        mf.setBackground(Color.white);//set the background color to white
        mf.setVisible(true); 
        //Make sure the program ends when the window is closed 
        mf.addWindowListener(  
            new WindowAdapter(){  
                public void windowClosing(WindowEvent e){  
                    System.exit(0);  
                }  
            }  
        );  
	}
	
	/**
	 * Constructor: for each of the images, loads the images and places them through the filters
	 */
	public Instagram(){
		//for each of the image names
		for(int i=0;i<JPEG_NAMES.length;i++){
			//Load and initialize all necessary images
			loadImages(i);
			initialize();
			//Apply various filters
			sharpen();
			blur();
			contrast();
			redTone();
			blueTone();
			whiteGradient();
			mainTexture();
			secondaryTexture();
			finalImages.add(newjpeg);//Add final images to an ArrayList, to be printed later
		}
	}
	
	/**
	 * Read and input all of the necessary images, including the initial image and the texture image overlays
	 * @param jpegindex Indicates which image name to retrieve in the array of image names
	 */
	public void loadImages(int jpegindex){
		try{
			//Retrieve all necessary images
			initjpeg=ImageIO.read(new File(JPEG_NAME_INIT));
			jpeg=ImageIO.read(new File(JPEG_NAMES[jpegindex]));
			speck=ImageIO.read(new File(TEXTURE_NAME));
			noise=ImageIO.read(new File(TIGHT_TEXTURE_NAME));
		}
		//In case of file error
		catch(IOException e){
			//Output error message
			System.out.println("Couldn't open images.");
			System.exit(0);
		}
	}
	
	/**
	 * Initialize all BufferedImages, including the ones needed in between the original and final versions and the final version
	 */
	public void initialize(){
		origImages.add(jpeg);//keep the original image, to be printed later
		newjpeg=new BufferedImage(jpeg.getWidth(),jpeg.getHeight(),BufferedImage.TYPE_INT_ARGB);//initialize the new version of the image
		((Graphics2D)newjpeg.getGraphics()).drawImage(jpeg, 0, 0, null);//draw the original image onto the new version of the image
		//initialize all images needed in between the original and final versions
		sharpenjpeg=new BufferedImage(jpeg.getWidth(),jpeg.getHeight(),BufferedImage.TYPE_INT_ARGB);
		blurjpeg=new BufferedImage(jpeg.getWidth(),jpeg.getHeight(),BufferedImage.TYPE_INT_ARGB);
		gradient=new BufferedImage(jpeg.getWidth(),jpeg.getHeight(),BufferedImage.TYPE_INT_ARGB);
		gradientblur=new BufferedImage(jpeg.getWidth(),jpeg.getHeight(),BufferedImage.TYPE_INT_ARGB);
	}
	
	/**
	 * Use a ConvolveOp with a 3x3 matrix to sharpen the image slightly
	 */
	public void sharpen(){
		((Graphics2D)sharpenjpeg.getGraphics()).drawImage(newjpeg,0,0,null);//place the original image in a temporary image
		//3x3 matrix for sharpening
		float[] sharpen = {
		       0f,-.5f,0f,
		       -.5f,3f,-.5f,
		       0f,-.5f,0f
		};
		//create and apply the ConvolveOp
		BufferedImageOp sharpenOp = new ConvolveOp( new Kernel(3,3, sharpen) );
		newjpeg = sharpenOp.filter(sharpenjpeg,newjpeg);
	}
	
	/**
	 * Use a ConvolveOp with a 5x5 matrix to blur the image slightly with increased transparency, to overlay over the original image and 
	 * cancel out some of the previous sharpening
	 */
	public void blur(){
		//3x3 matrix for Gaussian blurring
		float[] blur = {
				0.0030f,0.0133f,0.0219f,0.0133f,0.0030f,
				0.0133f,0.0596f,0.0983f,0.0596f,0.0133f,
				0.0219f,0.0983f,0.1621f,0.0983f,0.0219f,
				0.0133f,0.0596f,0.0983f,0.0596f,0.0133f,
				0.0030f,0.0133f,0.0219f,0.0133f,0.0030f
		};
		//create and apply the ConvolveOp
		BufferedImageOp blurOp = new ConvolveOp( new Kernel(5, 5, blur) );
		blurjpeg = blurOp.filter(newjpeg,blurjpeg);
		//use a RescaleOp to increase the transparency and overlay over the original image
		RescaleOp transparent=new RescaleOp(new float[]{1f,1f,1f,0.5f},new float[]{0f,0f,0f,0f},null);
		transparent.filter(blurjpeg,blurjpeg);
		((Graphics2D)newjpeg.getGraphics()).drawImage(blurjpeg, 0, 0, null);
	}
	
	/**
	 * Use a RescaleOp to increase the contrast slightly
	 */
	public void contrast(){
		RescaleOp highcontrast=new RescaleOp(new float[]{1.6f,1.6f,1.6f,1f},new float[]{-102f,-102f,-102f,0f},null);
		highcontrast.filter(newjpeg,newjpeg);
	}
	
	/**
	 * Use a RescaleOp to add a slight red-orange tint
	 */
	public void redTone(){
		RescaleOp redtone=new RescaleOp(new float[]{1.1f,1.08f,1f,1f},new float[]{0f,0f,0f,0f},null);
		redtone.filter(newjpeg,newjpeg);
	}
	
	/**
	 * Use a RescaleOp to add a slight blue tint
	 */
	public void blueTone(){
		RescaleOp blueorangetone=new RescaleOp(new float[]{1f,1f,1.02f,1f},new float[]{0f,0f,0f,0f},null);
		blueorangetone.filter(newjpeg,newjpeg);
	}
	
	/**
	 * Use GradientPaint to overlay a white transparent gradient to on the left of the image, and a more transparent gradient on 
	 * the right of the image
	 */
	public void whiteGradient(){
		//create a gradient image with a slightly transparent white to a more transparent white gradient, from right to left
		Graphics2D g2 = ((Graphics2D) gradient.createGraphics());
		g2.setBackground(new Color(0x00FF0000));
		g2.setColor(new Color(0,0,0,0));
		g2.fillRect(0, 0, newjpeg.getWidth(), newjpeg.getHeight());
		GradientPaint transparentBright=new GradientPaint(0,0,new Color(255,255,255,75),(float)(newjpeg.getWidth()*0.85),0,new Color(255,255,255,140));
		g2.setPaint(transparentBright);
		g2.fillRect(0, 0, (int)(newjpeg.getWidth()*0.84), newjpeg.getHeight());
		//add to the gradient image a slightly transparent white to a more transparent white gradient, from left to right
		GradientPaint transparentDark=new GradientPaint((float)(newjpeg.getWidth()*0.84),0,new Color(255,255,255,110),(float)(newjpeg.getWidth()),0,new Color(255,255,255,140));
		g2.setPaint(transparentDark);
		g2.fillRect((int)(newjpeg.getWidth()*0.84),0,newjpeg.getWidth(),newjpeg.getHeight());
		//blur the edge of the gradient, where the two gradients meet
		//3x3 matrix for blurring
		float[] blurGrad = {
        0.11f, 0.11f, 0.11f, 
        0.11f, 0.11f, 0.11f, 
        0.11f, 0.11f, 0.11f
		};
		//create and apply the ConvolveOp
		BufferedImageOp blurGradOp = new ConvolveOp( new Kernel(3, 3, blurGrad) );
		gradientblur = blurGradOp.filter(gradient,gradientblur);
		((Graphics2D)newjpeg.getGraphics()).drawImage(gradientblur, 0, 0, null);//overlay the blurred section onto the image
	}
	
	/**
	 * Tile and overlay the main texture onto the image
	 */
	public void mainTexture(){
		//tile the texture image
		int tileX=(int)(jpeg.getWidth()/speck.getWidth());
		int tileY=(int)(jpeg.getHeight()/speck.getHeight());
		tiledspeck=new BufferedImage(jpeg.getWidth(),jpeg.getHeight(),BufferedImage.TYPE_INT_ARGB);
		for(int y=0;y<=tileY;y++){
			for(int x=0;x<=tileX;x++){
				tiledspeck.getGraphics().drawImage(speck,x*speck.getWidth(),y*speck.getHeight(),speck.getWidth(),speck.getHeight(),null);
			}
		}
		//make all unnecessary sections of the texture image transparent
		for(int x=0;x<jpeg.getWidth();x++){
			for(int y=0;y<jpeg.getHeight();y++){
				if(tiledspeck.getRGB(x, y)<=0xFFBBBBBB){
					tiledspeck.setRGB(x,y,0x00000000);
				}
			}
		}
		//use RescaleOp to lower the transparency of the rest of the texture image
		RescaleOp transparent=new RescaleOp(new float[]{1f,1f,1f,0.5f},new float[]{0f,0f,0f,0f},null);
		transparent.filter(tiledspeck,tiledspeck);
		((Graphics2D)newjpeg.getGraphics()).drawImage(tiledspeck, 0, 0, null);//overlay the texture onto the image
	}
	
	/**
	 * Tile and overlay the secondary, smaller texture onto the image
	 */
	public void secondaryTexture(){
		//tile the texture image
		int tileX=(int)(jpeg.getWidth()/noise.getWidth());
		int tileY=(int)(jpeg.getHeight()/noise.getHeight());
		tilednoise=new BufferedImage(jpeg.getWidth(),jpeg.getHeight(),BufferedImage.TYPE_INT_ARGB);
		for(int y=0;y<=tileY;y++){
			for(int x=0;x<=tileX;x++){
				tilednoise.getGraphics().drawImage(noise,x*noise.getWidth(),y*noise.getHeight(),noise.getWidth(),noise.getHeight(),null);
			}
		}
		//make all unnecessary sections of the texture image transparent
		for(int x=0;x<jpeg.getWidth();x++){
			for(int y=0;y<jpeg.getHeight();y++){
				if(tilednoise.getRGB(x, y)<=0xFFDDDDDD){
					tilednoise.setRGB(x,y,0x00000000);
				}
			}
		}
		//use RescaleOp to lower the transparency of the rest of the texture image
		RescaleOp moretransparent=new RescaleOp(new float[]{1.2f,1.2f,1.2f,0.1f},new float[]{0f,0f,0f,0f},null);
		moretransparent.filter(tilednoise,tilednoise);
		((Graphics2D)newjpeg.getGraphics()).drawImage(tilednoise, 0, 0, null);//overlay the texture onto the image
	}
	
	/**
	 * Draw the initial image, and then loop through the other original and final images
	 * @param g System-provided Graphics object 
	 */
	public void paint(Graphics g){	
		g.drawImage(initjpeg,0,0,null);//print the original image
		//loop through the rest of the original and final images
		int prevHeight=initjpeg.getHeight();
		for(int i=0;i<JPEG_NAMES.length-1;i++){
			g.drawImage(origImages.get(i),0,prevHeight,null);
			g.drawImage(finalImages.get(i),origImages.get(i).getWidth(),prevHeight,null);
			prevHeight+=origImages.get(i).getHeight();
		}
		g.drawImage(origImages.get(2),initjpeg.getWidth(),0,null);
		g.drawImage(finalImages.get(2),initjpeg.getWidth()+origImages.get(2).getWidth(),0,null);
	}
}
