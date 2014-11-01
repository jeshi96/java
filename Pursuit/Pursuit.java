import java.awt.Frame; 
import java.awt.Color; 
import java.awt.event.WindowEvent; 
import java.awt.event.WindowAdapter; 

/**
 * Project description: Creates a new interface, on which the pursuit curves will be drawn
 * @author      Jessica Shi
 * @since       2013-09-12
 */

public class Pursuit {
	/**
	 * Constructor: creates a new PCanvas object, sets window size, background color, and closing command
	 */
    public Pursuit(){ 
        Frame mf=new Frame("Pursuit Curve"); 
        PCanvas aCanvas=new PCanvas(); 
        mf.add(aCanvas); 
        mf.setSize(1000,1000); 
        mf.setBackground(Color.black); 
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
    
    public static void main(String[] args) { 
        new Pursuit(); 
    } 
}