import java.awt.Canvas; 
import java.awt.Color; 
import java.awt.Graphics; 
import java.util.ArrayList;  

/**
 * Project description: Draws two six-pointed star and a dodecagon pursuit curves
 * @author      Jessica Shi
 * @since       2013-09-12
 */

public class PCanvas extends Canvas{ 
	/**
	 * Initialize private variables, for the points and side length of the polygon and repetition
	 * and factor constants of the pursuit curve
	 */
	//x and y coordinates of the polygons, to be used in calculating and drawing the pursuit curves
    private ArrayList<Double> xPoints=new ArrayList<Double>();
    private ArrayList<Double> yPoints=new ArrayList<Double>();
    private double sideLength; //side length of the polygon
    private int numRepetition=900;//number of repetitions
    private double pursuitFactor=0.05;//scale factor
    
    /**
     * Default constructor
     */
    public PCanvas(){ 
        super(); 
    }
    
    /**
     * Draws two six-pointed star pursuit curves and one dodecagon pursuit curve
     * @param g System-provided Graphics object
     */
    public void paint(Graphics g){ 
        g.clearRect(0,0,getWidth(),getHeight());//clear the screen
        sideLength=Math.min(getWidth(),3*getHeight()/(2*Math.sqrt(3)));//calculate the largest possible side length
        //x and y coordinates for one six-pointed star; used to create the other star and dodecagon
        double xArray[]={getWidth()/2-sideLength/2,
                getWidth()/2-sideLength/2+sideLength/3,
                getWidth()/2,
                getWidth()/2-sideLength/2+sideLength/3+sideLength/3,
                getWidth()/2+sideLength/2,
                getWidth()/2+sideLength/2-sideLength/6,
                getWidth()/2+sideLength/2,
                getWidth()/2+sideLength/2-sideLength/3,
                getWidth()/2,
                getWidth()/2+sideLength/2-sideLength/3-sideLength/3,
                getWidth()/2-sideLength/2,
                getWidth()/2-sideLength/2+sideLength/6
                };
        double yArray[]={(getHeight())/2-sideLength*Math.sqrt(3)/4+sideLength*Math.sqrt(3)/12,
                (getHeight())/2-sideLength*Math.sqrt(3)/4+sideLength*Math.sqrt(3)/12,
                (getHeight())/2-sideLength*Math.sqrt(3)/4-sideLength*Math.sqrt(3)/12,
                (getHeight())/2-sideLength*Math.sqrt(3)/4+sideLength*Math.sqrt(3)/12,
                (getHeight())/2-sideLength*Math.sqrt(3)/4+sideLength*Math.sqrt(3)/12,
                (getHeight())/2-sideLength*Math.sqrt(3)/4+sideLength*Math.sqrt(3)/12+sideLength*Math.sqrt(3)/6,
                (getHeight())/2+sideLength*Math.sqrt(3)/4-sideLength*Math.sqrt(3)/12,
                (getHeight())/2+sideLength*Math.sqrt(3)/4-sideLength*Math.sqrt(3)/12,
                (getHeight())/2+sideLength*Math.sqrt(3)/4+sideLength*Math.sqrt(3)/12,
                (getHeight())/2+sideLength*Math.sqrt(3)/4-sideLength*Math.sqrt(3)/12,
                (getHeight())/2+sideLength*Math.sqrt(3)/4-sideLength*Math.sqrt(3)/12,
                (getHeight())/2-sideLength*Math.sqrt(3)/4+sideLength*Math.sqrt(3)/12+sideLength*Math.sqrt(3)/6
                };
        initArray(xArray,yArray); //Initialize the ArrayLists of points for the star
        drawPursuit(g,new Color(204,0,51),new Color(255,53,51));//Draw the pursuit curve for the star
        Object[] rotatedArrays=initFlipArray(xArray,yArray);//Calculate the points for the rotated and flipped star
        double[] xArrayRotated=(double[]) rotatedArrays[0];
        double[] yArrayRotated=(double[]) rotatedArrays[1];
        initArray(xArrayRotated,yArrayRotated);//Initialize the ArrayLists of points for the rotated and flipped star
        g.translate(getWidth()/2,getHeight()/2);//Move the origin so that (0,0) is in the center, since the star was rotated about the origin instead of the center of the star
        drawPursuit(g,new Color(204,0,51),new Color(255,53,51));//Draw the pursuit curve for the rotated and flipped star
        initOuterArray(xArray,yArray,xArrayRotated,yArrayRotated);//Use the original star and the rotated and flipped star to calculate the points for a dodecagon
        drawPursuitOuter(g,new Color(190,0,63),new Color(3,0,251));//Draw the pursuit curve for the dodecagon, spiraling outwards instead of inwards
    }
    
    /**
     * Calculate and initialize the ArrayLists of the points for a dodecagon circumscribing the two stars
     * @param xArray	Array of x coordinates for the star
     * @param yArray	Array of y coordinates for the star
     * @param xArrayRotated	Array of x coordinates for the rotated and flipped star
     * @param yArrayRotated	Array of y coordinates for the rotated and flipped star
     */
    public void initOuterArray(double[] xArray,double[] yArray,double[] xArrayRotated,double[] yArrayRotated){
    	//Reverse the rotated star points, so that the star is no longer flipped
    	double[] xArrayRotatedReversed=new double[xArrayRotated.length];
    	double[] yArrayRotatedReversed=new double[yArrayRotated.length];
        for(int i=xArrayRotated.length-1;i>=0;i--){
        	xArrayRotatedReversed[xArrayRotated.length-1-i]=xArrayRotated[i];
        	yArrayRotatedReversed[yArrayRotated.length-1-i]=yArrayRotated[i];
        }
        //Initialize temporary ArrayLists
        ArrayList<Double> tempx=new ArrayList<Double>(); 
        ArrayList<Double> tempy=new ArrayList<Double>(); 
        int i=0; //counter
        //Alternate between adding an outer point from the star and the rotated star
    	for(i=0;i+2<xArray.length;i+=2){
    		tempx.add(xArray[i+2]-getWidth()/2);
    		tempy.add(yArray[i+2]-getHeight()/2);
    		tempx.add(xArrayRotatedReversed[i]);
    		tempy.add(yArrayRotatedReversed[i]);
    	}
    	tempx.add(xArray[0]-getWidth()/2);
    	tempy.add(yArray[0]-getHeight()/2);
		tempx.add(xArrayRotatedReversed[i]);
		tempy.add(yArrayRotatedReversed[i]);
		//Set the ArrayLists for the pursuit curve to the calculated points
    	xPoints.clear();
    	yPoints.clear();
    	xPoints=tempx;
    	yPoints=tempy;
    }
    
    /**
     * Calculate the coordinates for the rotated and flipped star
     * @param xArray Array of x coordinates for the star
     * @param yArray	Array of y coordinates for the star
     * @return	returns an object array that contains two arrays, of the x and y coordinates for the rotated and flipped star
     */
    public Object[] initFlipArray(double[] xArray,double[] yArray){
    	//Initialize the arrays for the rotated star
    	double xArrayRotated[]=new double[xArray.length];
    	double yArrayRotated[]=new double[yArray.length];
        double theta=Math.PI/2.0; //angle of rotation
        //Apply the rotation matrix to each of the x and y coordinates of the original star, reversing the order to achieve a flipped effect
        for(int i=xArray.length-1;i>=0;i--){
        	xArrayRotated[xArray.length-1-i]=(xArray[i]-getWidth()/2)*Math.cos(theta)-(yArray[i]-getHeight()/2)*Math.sin(theta);
        	yArrayRotated[yArray.length-1-i]=(xArray[i]-getWidth()/2)*Math.sin(theta)+(yArray[i]-getHeight()/2)*Math.cos(theta);
        }
        return new Object[]{xArrayRotated,yArrayRotated}; //return an object array containing the x and y coordinates for the rotated and flipped star
    }
    
    /**
     * Transfer x and y coordinates to the respective ArrayLists for use in the pursuit curve
     * @param xArray	Array of x coordinates
     * @param yArray	Array of y coordinates
     */
    public void initArray(double[] xArray, double[] yArray){
    	//Reset for the new polygon
        xPoints.clear();
        yPoints.clear();
        //Transfer the x coordinates
        for(double x:xArray){ 
            xPoints.add(x); 
        } 
        //Transfer the y coordinates
        for(double y:yArray){ 
            yPoints.add(y);
        }
    }
    
    /**
     * Draw a double gradient (so it starts with the initial color, gradients to the final color, and then back 
     * to the initial color) pursuit curve spiraling inside a given polygon
     * @param g	System-provided Graphics object
     * @param initColor	Initial color of the gradient
     * @param finalColor	Final color of the gradient
     */
    public void drawPursuit(Graphics g, Color initColor, Color finalColor){
        g.setColor(initColor);//Set the initial color
        //Draw the initial polygon
        for(int i=0;i<xPoints.size()-1;i++){ 
            g.drawLine(xPoints.get(i).intValue(),yPoints.get(i).intValue(),xPoints.get(i+1).intValue(),yPoints.get(i+1).intValue()); 
        }
        g.drawLine(xPoints.get(0).intValue(),yPoints.get(0).intValue(),xPoints.get(xPoints.size()-1).intValue(),yPoints.get(xPoints.size()-1).intValue()); 
        //Initialize each RGB color component with the initial color
        double red=initColor.getRed();
        double blue=initColor.getBlue();
        double green=initColor.getGreen();
        boolean done=false;//Initialize a boolean that tells whether the colors have already switched for the double gradient
        int stopIndexRep=0;//Initialize a step counter that tells when the colors switch for the double gradient
        //Calculate each step for the pursuit curve
        for(int indexRep=0;indexRep<numRepetition;indexRep++){
        	double colorRatio=(double)indexRep/(double)(numRepetition-850);//Initialize ratio in which the colors change
        	if(colorRatio>1){//If the color has reached the final color and is returning to the initial color
        		if(done==false){//If the color has not been switched yet
        			//Switch the colors
        			Color temp=initColor;
        			initColor=finalColor;
        			finalColor=temp;
        			stopIndexRep=indexRep;//Keep track of when the colors switched
        			done=true;//The colors have been switched
        		}
        		colorRatio=(double)(indexRep-stopIndexRep)/(double)(numRepetition-stopIndexRep);//Calculate the new ratio to account for the switching of colors
        	}
        	//Increment each color
        	red=(finalColor.getRed()*colorRatio+initColor.getRed()*(1-colorRatio));
            green=(finalColor.getGreen()*colorRatio+initColor.getGreen()*(1-colorRatio));
            blue=(finalColor.getBlue()*colorRatio+initColor.getBlue()*(1-colorRatio));
        	g.setColor(new Color((int)red,(int)green,(int)blue));//Set the color
        	//Initialize temporary ArrayLists of the coordinates
            ArrayList<Double> tempx=new ArrayList<Double>(); 
            ArrayList<Double> tempy=new ArrayList<Double>(); 
            //For each x coordinate, calculate the new x coordinate
            for(int i=0;i<xPoints.size()-1;i++){ 
                tempx.add(xPoints.get(i)-pursuitFactor*(xPoints.get(i)-xPoints.get(i+1))); 
            } 
            tempx.add(xPoints.get(xPoints.size()-1)-pursuitFactor*(xPoints.get(xPoints.size()-1)-xPoints.get(0))); 
            //For each y coordinate, calculate the new y coordinate
            for(int i=0;i<yPoints.size()-1;i++){ 
                tempy.add(yPoints.get(i)-pursuitFactor*(yPoints.get(i)-yPoints.get(i+1))); 
            } 
            tempy.add(yPoints.get(yPoints.size()-1)-pursuitFactor*(yPoints.get(yPoints.size()-1)-yPoints.get(0))); 
            //Replace the old x and y coordinates
            xPoints.clear(); 
            yPoints.clear(); 
            xPoints.addAll(tempx); 
            yPoints.addAll(tempy); 
            //Draw the newly calculated pursuit curves
            for(int i=0;i<xPoints.size()-1;i++){ 
                g.drawLine(xPoints.get(i).intValue(),yPoints.get(i).intValue(),xPoints.get(i+1).intValue(),yPoints.get(i+1).intValue()); 
            } 
            g.drawLine(xPoints.get(0).intValue(),yPoints.get(0).intValue(),xPoints.get(xPoints.size()-1).intValue(),yPoints.get(xPoints.size()-1).intValue());
        }
    }
    
    /**
     * Draw a gradient pursuit curve spiraling outside a given polygon
     * @param g	System-provided Graphics object
     * @param initColor	Initial color of the gradient
     * @param finalColor	Final color of the gradient
     */
    public void drawPursuitOuter(Graphics g, Color initColor, Color finalColor){
        g.setColor(initColor);//Set the initial color
        //Draw the initial polygon
        for(int i=0;i<xPoints.size()-1;i++){ 
            g.drawLine(xPoints.get(i).intValue(),yPoints.get(i).intValue(),xPoints.get(i+1).intValue(),yPoints.get(i+1).intValue()); 
        }
        g.drawLine(xPoints.get(0).intValue(),yPoints.get(0).intValue(),xPoints.get(xPoints.size()-1).intValue(),yPoints.get(xPoints.size()-1).intValue()); 
        //Initialize each RGB color component with the initial color
        double red=initColor.getRed();
        double blue=initColor.getBlue();
        double green=initColor.getGreen();
        int indexRep=0;//Initialize counter of repetitions
        int numStep=(int)(getWidth()*3*pursuitFactor);//Calculate the number of steps for the color gradient
        //Calculate each step for the pursuit curve, while the curve remains visible on the screen
        while(xPoints.get(0)<getWidth()/2){
        	indexRep++;//Increment the counter
        	double colorRatio=(double)indexRep/(double)(numStep);//Initialize ratio in which the colors change
        	//Increment each color
        	red=(finalColor.getRed()*colorRatio+initColor.getRed()*(1-colorRatio));
            green=(finalColor.getGreen()*colorRatio+initColor.getGreen()*(1-colorRatio));
            blue=(finalColor.getBlue()*colorRatio+initColor.getBlue()*(1-colorRatio));
        	g.setColor(new Color((int)red,(int)green,(int)blue));//Set the color
        	//Initialize temporary ArrayLists of the coordinates
            ArrayList<Double> tempx=new ArrayList<Double>(); 
            ArrayList<Double> tempy=new ArrayList<Double>(); 
            //For each x coordinate, calculate the new x coordinate
            for(int i=0;i<xPoints.size()-1;i++){ 
                tempx.add(xPoints.get(i)+pursuitFactor*(xPoints.get(i)-xPoints.get(i+1))); 
            } 
            tempx.add(xPoints.get(xPoints.size()-1)+pursuitFactor*(xPoints.get(xPoints.size()-1)-xPoints.get(0))); 
            //For each y coordinate, calculate the new y coordinate
            for(int i=0;i<yPoints.size()-1;i++){ 
                tempy.add(yPoints.get(i)+pursuitFactor*(yPoints.get(i)-yPoints.get(i+1))); 
            } 
            tempy.add(yPoints.get(yPoints.size()-1)+pursuitFactor*(yPoints.get(yPoints.size()-1)-yPoints.get(0))); 
            //Replace the old x and y coordinates
            xPoints.clear(); 
            yPoints.clear(); 
            xPoints.addAll(tempx); 
            yPoints.addAll(tempy); 
            //Draw the newly calculated pursuit curves
            for(int i=0;i<xPoints.size()-1;i++){ 
                g.drawLine(xPoints.get(i).intValue(),yPoints.get(i).intValue(),xPoints.get(i+1).intValue(),yPoints.get(i+1).intValue()); 
            } 
            g.drawLine(xPoints.get(0).intValue(),yPoints.get(0).intValue(),xPoints.get(xPoints.size()-1).intValue(),yPoints.get(xPoints.size()-1).intValue());
        }
    }
} 