import java.awt.BasicStroke;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import javax.swing.JOptionPane;
 
public class Screensaver extends Canvas{
	/**
	 * Initialize variables, for all of the constants and parameters needed to draw the spirographs
	 */
    private double currentStep=0;//initialize the current point that the inner spirograph drawing is at
    private final double MAX_STEP=400;//initialize the point in which to stop the spirograph drawing
    private static Thread callRepeat;//initialize the thread to repeat the drawing
    private Image buffer;//initialize the Image for double buffering to occur
    private double sizeConstantInitial;//initialize the size of the out-most spirograph, as based on the screen size
    private double sizeConstant;//initialize the size of the current spirograph
    private boolean init=true;//initialize the parameter which is true if this is the first call of paint
    //initialize the two current randomized parameters for the spirograph
    private double lConstant=Math.random();
    private double kConstant=Math.random();
    //initialize the ArrayLists that store the randomized parameters for each nested spirograph
    private ArrayList<Double> lArray=new ArrayList<Double>();
    private ArrayList<Double> kArray=new ArrayList<Double>();
    private final int MAX_TIMES_NESTED=7;//initialize the constant which gives the maximum number of nested spirographs on the screen before clearing
    private int countTimesNested=0;//initialize the counter of the number of times the spirographs have been nested thus far
    private double time=0;//initialize the time variable
    private final double TIME_INCREMENT=0.01;//initialize the increment in which the time will rise
    private final double STEP_INCREMENT=1;//initialize the increment in which the step will rise
    //initialize the coordinates for the next point on the spirograph
    private double nextx=0;
    private double nexty=0;
    private int countColor=0;//initialize the counter for the color gradients
    private static Color[] colorArray=new Color[3];//initialize the array for the analogous colors
    //initialize the initial and final colors for the gradient
    private static Color initColor=Color.magenta;
    private Color finalColor;
    private final double SIZE_SCREEN_FACTOR=2;//initialize the factor for the initial spirograph size, as based on the screen size
    private final double NESTED_SIZE_FACTOR=1.1;//initialize the factor for each subsequent nested spirograph
    private boolean showEllipse=true;//initialize the boolean for if the tracker should be shown
    private BasicStroke spirographStroke=new BasicStroke(1);//initialize the stroke of the spirograph
    private static Color backgroundColor=Color.gray;//initialize the background color of the spirograph
    
    /**
     * Calculates the analogous colors to the initial color
     */
    public static void calcAnalogousColor(){
    	//initiate variable to hold the HSB version of the initial color
        float[] HSB=Color.RGBtoHSB(initColor.getRed(),initColor.getGreen(),initColor.getBlue(),null);
        float hColor=HSB[0];
        //perform operations to create the two analogous colors
        HSB[0]=((HSB[0]*360+330)%360)/360;
        Color color1=Color.getHSBColor(HSB[0], HSB[1], HSB[2]);
        HSB[0]=((hColor*360+30)%360)/360;
        Color color2=Color.getHSBColor(HSB[0],HSB[1],HSB[2]);
        //plase the initial color and the analogous colors in an array
        colorArray[0]=initColor;
        colorArray[1]=color1;
        colorArray[2]=color2;
    }
    
    /**
     * Draws the nested spirographs based on randomly generated parameter values with varying gradients of 
     * analogous colors
     * @param g System-provided Graphics object
     */
    public void spirograph(Graphics g){
        ((Graphics2D)g).setStroke(spirographStroke);//set the width of the spirograph stroke
        //set anti-aliasing
        ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
        g.translate(getWidth()/2,getHeight()/2);//translate the screen so that the spirographs will center correctly
        //reset and initialize necessary variables
        time=0;
        nextx=0;
        nexty=0;
        double useStep=MAX_STEP;
        int constantCount=0;
        countColor=0;
        for(double i=sizeConstantInitial;i>=sizeConstant;i=i/(NESTED_SIZE_FACTOR)){//for each nested spirograph
        	//retrieve the corresponding randomly generated parameters
            lConstant=lArray.get(constantCount);
            kConstant=kArray.get(constantCount);
            time=0;//reset the time
            //initialize the first points of the spirograph
            Point2D.Double nextpoint=new Point2D.Double(i*((1-kConstant)*Math.cos(time)+lConstant*kConstant*Math.cos(time*(1-kConstant)/kConstant)),i*((1-kConstant)*Math.sin(time)-lConstant*kConstant*Math.sin(time*(1-kConstant)/kConstant)));
            Point2D.Double prevpoint=new Point2D.Double(i*((1-kConstant)*Math.cos(time)+lConstant*kConstant*Math.cos(time*(1-kConstant)/kConstant)),i*((1-kConstant)*Math.sin(time)-lConstant*kConstant*Math.sin(time*(1-kConstant)/kConstant)));
            if(i==sizeConstant)//draw a complete spirograph, unless it is one of the inner nested spirographs in the process of being drawn
                useStep=currentStep;
            //initialize the two colors for the gradient
            initColor=colorArray[countColor%colorArray.length];
            finalColor=colorArray[(countColor+1)%colorArray.length];
            countColor++;
            double red=initColor.getRed();
            double blue=initColor.getBlue(); 
            double green=initColor.getGreen(); 
            while(time<useStep){//drawing each nested spirograph, for the necessary number of steps (complete spirograph if it is one of the outer ones, unless it is one of the inner nested spirographs in the process of being drawn)
                //set the location of the next points of hte spirograph
            	nextx=i*((1-kConstant)*Math.cos(time)+lConstant*kConstant*Math.cos(time*(1-kConstant)/kConstant));
                nexty=i*((1-kConstant)*Math.sin(time)-lConstant*kConstant*Math.sin(time*(1-kConstant)/kConstant));
                nextpoint.setLocation(nextx,nexty);
                time+=TIME_INCREMENT;//increment the time
                double colorRatio=(double)time/(double)(MAX_STEP);//initialize ratio in which the colors change 
                //increment each color 
                red=(finalColor.getRed()*colorRatio+initColor.getRed()*(1-colorRatio)); 
                green=(finalColor.getGreen()*colorRatio+initColor.getGreen()*(1-colorRatio)); 
                blue=(finalColor.getBlue()*colorRatio+initColor.getBlue()*(1-colorRatio)); 
                g.setColor(new Color((int)red,(int)green,(int)blue));//set the new color 
                g.drawLine((int)prevpoint.getX(), (int)prevpoint.getY(),(int)nextpoint.getX(),(int)nextpoint.getY());//draw the next section of the curve
                prevpoint.setLocation(nextx,nexty);//reset the new previous point
            }
            if(useStep<MAX_STEP && showEllipse==true)//if this is one of the inner nested spirographs, and if the tracker was requested
				g.fillOval((int) nextx-10, (int)nexty-10, 20, 20);//draw the tracker
            constantCount++;//increment to the next nested spirograph
        }
        if(currentStep<MAX_STEP){//if the spirograph has not been completed yet
            currentStep+=STEP_INCREMENT;//increment the step
        }
        else if(currentStep>=MAX_STEP){//if the spirograph has been completed
            countTimesNested++;//increment the number of nested spirographs saved
            currentStep=0;//reset the step
            if(countTimesNested==MAX_TIMES_NESTED){//if the number of nested spirographs saved has reached a certain point
            	//clear everything and restart everything
                sizeConstant=sizeConstantInitial;
                lArray.clear();
                kArray.clear();
                countTimesNested=0;
            }
            else{//if more nested spirographs are needed
                sizeConstant=sizeConstant/(NESTED_SIZE_FACTOR);//reduce the size for the next nested spirograph
            }
            //add more randomly generated parameters for the next spirograph
            lArray.add(Math.random());
            kArray.add(Math.random());
        }
    }
    
    /**
     * Sets initial conditions and then draws on an Image object before transferring it to the screen, for 
     * double buffering
     * @param g System-provided Graphics object
     */
    public void paint(Graphics g){
        if(init==true){//if this is the first call of paint, set initial conditions
            if(getWidth()<=getHeight()){//if the width is less than the height
            	//set size constants based on the width
                sizeConstantInitial=getWidth()/(SIZE_SCREEN_FACTOR);
                sizeConstant=getWidth()/(SIZE_SCREEN_FACTOR);
            }
            else{//if the height is less than the width
            	//set size constants based on the height
                sizeConstantInitial=getHeight()/(SIZE_SCREEN_FACTOR);
                sizeConstant=getHeight()/(SIZE_SCREEN_FACTOR);
            }
            //initiate randomly generated parameters for the first spirograph
            lArray.add(Math.random());
            kArray.add(Math.random());
            init=false;//this is no longer the first call of paint
            inputColor();//call method to ask for user input on the spirograph color
            calcAnalogousColor();//call method to calculate the analogous colors to be used
            inputOtherOptions();//call method to ask for user input on other options
        }
        if(buffer==null||getWidth()!=buffer.getWidth(null)||getHeight()!=buffer.getHeight(null))//if the Image hasn't been created yet or if the size specifications have changed
            buffer=createImage(getWidth(),getHeight());//create the Image
        else//if the Image has already been created
            buffer.getGraphics().clearRect(0,0,getWidth(),getHeight());//reset the Image
        spirograph(buffer.getGraphics());//draw on the Image
        g.drawImage(buffer,0,0,null);//draw on the screen
    }
    
    /**
     * Resets the constants and calls paint if the screen is resized
     * @param g System-provided Graphics object 
     */
    public void update(Graphics g){
        if(getWidth()<=getHeight()){//if the width is less than the height
        	//set the constants based on the width
            sizeConstant=(getWidth()/(SIZE_SCREEN_FACTOR))/(sizeConstantInitial/sizeConstant);
            sizeConstantInitial=getWidth()/(SIZE_SCREEN_FACTOR);
        }
        else{//if the height is less than the width
        	//set the constants based on the height
            sizeConstant=(getHeight()/(SIZE_SCREEN_FACTOR))/(sizeConstantInitial/sizeConstant);
            sizeConstantInitial=getHeight()/(SIZE_SCREEN_FACTOR);
        }
        paint(g);//paint the screen with these new specifications
    }
    
    /**
     * Asks for user input on a variety of other options, including the "tracker" circle following the 
     * spirograph and the width of the stroke of the spirograph.
     */
    public void inputOtherOptions(){
    	//initialize all necessary variables
    	String tempstring;
    	int strokeWidth;
    	boolean done=false;
    	int tracker=JOptionPane.showConfirmDialog(this,"Do you want a tracker?","Tracker",JOptionPane.YES_NO_OPTION);//ask if the user wants a tracker to appear
    	//if the user wants the tracker to appear, set the tracker to appear
    	if(tracker==0)
    		showEllipse=true;
    	//if the user does not want the tracker appear, set the tracker to disappear
    	else
    		showEllipse=false;
        tempstring= JOptionPane.showInputDialog("Please enter a stroke width: ");//ask the user for a stroke width
        //check to make sure that the value entered is an integer
        while(done==false){
        	try{
        		strokeWidth=Integer.parseInt(tempstring);
        		done=true;
        	}
        	catch(NumberFormatException e){
        		tempstring= JOptionPane.showInputDialog("Please enter a valid stroke width: ");//if the value entered is not an integer, ask again
        	}
        }
        strokeWidth=Integer.parseInt(tempstring);//set the stroke width
        spirographStroke=new BasicStroke(strokeWidth);//create the stroke with the new stroke width
    }
 
    /**
     * Asks for user input on the background color of the canvas on which the spirograph will be drawn, with 
     * error checking to ensure that the values entered are integers
     */
    public static void inputBackgroundColor(){
    	//initialize all necessary variables
        int red;
        int blue;
        int green;
        String tempstring;
        boolean done=false;
        tempstring= JOptionPane.showInputDialog("Please enter a red value: (integer, 0-255)");//ask for a red value
        //check to make sure that the value entered is an integer
        while(done==false){
        	try{
        		red=Integer.parseInt(tempstring);
        		done=true;
        	}
        	catch(NumberFormatException e){
        		tempstring= JOptionPane.showInputDialog("Please enter a valid red value: (integer, 0-255)");//if the value entered is not an integer, ask again
        	}
        }
        red=Integer.parseInt(tempstring);//set the red value
        done=false;//reset the checking parameter
        tempstring= JOptionPane.showInputDialog("Please enter a green value: (integer, 0-255)");//ask for a green value
        //check to make sure that the value entered is an integer
        while(done==false){
        	try{
        		green=Integer.parseInt(tempstring);
        		done=true;
        	}
        	catch(NumberFormatException e){
        		tempstring= JOptionPane.showInputDialog("Please enter a valid green value: (integer, 0-255)");//if the value entered is not an integer, ask again
        	}
        }
        green=Integer.parseInt(tempstring);//set the green value
        done=false;//reset the checking parameter
        tempstring= JOptionPane.showInputDialog("Please enter a blue value: (integer, 0-255)");//ask for a blue value
        //check to make sure that the value entered is an integer
        while(done==false){
        	try{
        		blue=Integer.parseInt(tempstring);
        		done=true;
        	}
        	catch(NumberFormatException e){
        		tempstring= JOptionPane.showInputDialog("Please enter a valid blue value: (integer, 0-255)");//if the value entered is not an integer, ask again
        	}
        }
        blue=Integer.parseInt(tempstring);//set the blue value
        backgroundColor=new Color(red,green,blue);//set the background color based on user input
    }
    
    /**
     * Asks for user input on the initial color of the spirograph, with error checking to ensure that the values
     * entered are integers.
     */
    public void inputColor(){
    	//initialize all necessary variables
        int red;
        int blue;
        int green;
        String tempstring;
        boolean done=false;
        tempstring= JOptionPane.showInputDialog("Please enter a red value: (integer, 0-255)");//ask for a red value
        //check to make sure that the value entered is an integer
        while(done==false){
        	try{
        		red=Integer.parseInt(tempstring);
        		done=true;
        	}
        	catch(NumberFormatException e){
        		tempstring= JOptionPane.showInputDialog("Please enter a valid red value: (integer, 0-255)");//if the value entered is not an integer, ask again
        	}
        }
        red=Integer.parseInt(tempstring);//set the red value
        done=false;//reset checking parameter
        tempstring= JOptionPane.showInputDialog("Please enter a green value: (integer, 0-255)");//ask for a green value
        //check to make sure that the value entered is an integer
        while(done==false){
        	try{
        		green=Integer.parseInt(tempstring);
        		done=true;
        	}
        	catch(NumberFormatException e){
        		tempstring= JOptionPane.showInputDialog("Please enter a valid green value: (integer, 0-255)");//if the value entered is not an integer, ask again
        	}
        }
        green=Integer.parseInt(tempstring);//set the green value
        done=false;//reset checking parameter
        tempstring= JOptionPane.showInputDialog("Please enter a blue value: (integer, 0-255)");//ask for a blue value
        //check to make sure that the value entered is an integer
        while(done==false){
        	try{
        		blue=Integer.parseInt(tempstring);
        		done=true;
        	}
        	catch(NumberFormatException e){
        		tempstring= JOptionPane.showInputDialog("Please enter a valid blue value: (integer, 0-255)");//if the value entered is not an integer, ask again
        	}
        }
        blue=Integer.parseInt(tempstring);//set the blue value
        initColor=new Color(red,green,blue);//set the initial color based on user input
    }
    
	/**
	 * Main method; creates a new Screensaver object, sets window size, background color, and closing command 
	 * @param args
	 */
    public static void main(String[] args) { 
    	initColor=Color.magenta;
    	calcAnalogousColor();
        Frame mf=new Frame("Hi");//set frame name 
        final Screensaver aCanvas=new Screensaver();//create new Screensaver object 
        mf.add(aCanvas);//add object to the frame 
        mf.setSize(2000,1000);//set the initial size of the frame 
        inputBackgroundColor();//call method to ask for user input on the background color
        mf.setBackground(backgroundColor);//set the background color
        mf.setVisible(true);
        //make sure the program ends when the window is closed  
        mf.addWindowListener(   
            new WindowAdapter(){   
                public void windowClosing(WindowEvent e){   
                    System.exit(0);   
                }   
            }   
        ); 
        //start double buffering
        callRepeat=new Thread(new Runnable(){
            public void run(){
                while(callRepeat==Thread.currentThread()){
                    aCanvas.repaint();//repaint to create animation
                    try {
                        Thread.sleep(50);//pause between frames
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        callRepeat.start();
    } 
 
}