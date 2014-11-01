import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

/**
 * Project description: Creates a layout that emulates a Google Chrome browser (see ref.png)
 * @author      Jessica Shi
 * @since       2014-01-03
 */

/*Layout Descriptions
 * 
 * BorderLayout: BorderLayout has five sections, name a top section that spans the width (PAGE_START), a bottom section
 * that spans the width (PAGE_END), a left section (LINE_START; the top and bottom sections take precedence), a 
 * right section (LINE_END; again, the top and bottom sections take precedence), and a center section that fills the 
 * remaining area. Here, we use a BorderLayout for the entire contraption; the three bars are contained in one 
 * layout which is contained in the top section, while the rest of the content is within the center section.
 * 
 * BoxLayout: BoxLayout has options that allow the elements within it to be arranged vertically or horizontally, and 
 * then aligned (top, bottom, center, left right) depending on the arrangement. Here, we use a BoxLayout for
 * everything else, namely the outer container that holds the three bars (aligned vertically and to the left) and 
 * the bars themselves (all aligned horizontally). Within the BoxLayout, the elements stretch up to their maximum 
 * width depending on the space available due to the other elements. We set the maximum widths of certain elements,
 * such as within the tab bar, to control this.
 * 
 * CardLayout: CardLayout allows the user to switch between panes using a drop down menu. However, using a tabbed pane
 * is simpler. Neither is used here.
 * 
 * FlowLayout: FlowLayout is the simplest of layouts and simply places objects side by side horizontally; once it 
 * runs out of space, it defaults to the next line. This is not used here.
 * 
 * GridBagLayout: GridBagLayout is essentially a GridLayout that allows for colspan and rowspan options. This is not 
 * used here.
 * 
 * GridLayout: GridLayout places elements in a grid that fills the screen, and the elements fill the cells of the grid.
 * The number of rows and columns as well as gaps between rows and columns can be specified. This is not used here.
 * 
 * GroupLayout: GroupLayout allows for horizontally arranged or vertically arranged groups of elements to be created and 
 * pieced together, with specified alignments (such as left and baseline). This is not used here.
 * 
 * SpringLayout: SpringLayout can emulate the features of other layouts, by default placing elements at the origin.
 * The elements can be specified to be at various locations (north, south, east, west, baseline, horizontal center,
 * and vertical center), and grids can be easily created as well.
 */

public class LayoutManager extends JFrame{
	/**
	 * Initializes the private variables, for the names and corresponding images of the various variable elements of 
	 * the layout, and for the constant sizes and colors
	 */
	//Arrays for the names and corresponding images of the tabs
	private String[] tabNames={"Amazon","DeviantArt","Facebook","Google","Yahoo","Dropbox"};
	private String[] tabIconNames={"amazon.png","deviantart.png","facebook.png","google.png","yahoo.png","dropbox.png"};
	private int MAX_TAB_WIDTH=140;//Constant for the maximum width of a tab
	private int TAB_ICON_SIZE=20;//Constant for the minimum width of a tab, and the icon size
	private int TAB_ADD_ICON_SIZE=10;//Constant for the width of the button to add more tabs
	private Color TAB_COLOR=new Color(251,168,238);//Constant for the color of the tabs
	
	//Preset buttons for the address bar
	private JButton back=new JButton();
	private JButton forward=new JButton();
	private JButton refresh=new JButton();
	private JButton home=new JButton();
	private JButton menu=new JButton();
	private int ADDRESS_ICON_SIZE=15;//Constant for the size of the icons for the address bar
	private int ADDRESS_URL_BAR_ICON_SIZE=20;//Constant for the size of the symbols in the URL field
	private Color ADDRESS_COLOR=new Color(13,95,145);//Constant for the color of the address bar and the tab bar
	
	//Arrays for the names and corresponding images of the favorite folders and pages
	private String[] favNames={"Songs","Vimeo","Twitter","Phys","Math", "Wordpress","Apple","Instagram","Pinterest"};
	private String[] favIconNames={"","vimeo.png","twitter.png","","","wordpress.png","apple.png","instagram.png","pinterest.png"};
	private int FAV_ICON_SIZE=15;//Constant for the size of the favorite folder and page icons
	private Color FAV_COLOR=new Color(48,48,48);//Constant for the color of the favorites bar
	private Color FAV_TEXT_COLOR=new Color(158,196,197);//Constant for the color of the text on the favorites bar
	
	/**
	 * Constructs the elements of the frame, which include a tab bar, an address bar, and a favorites bar
	 * @param title	Title of the frame window
	 */
	public LayoutManager(String title){
		super(title);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//Exit the frame when the close button is pressed
		Container mainContent=getContentPane();//Retrieve the main content pane
		mainContent.setLayout(new BorderLayout());//Set the main pane as a border layout, so that the three bars can lie on top and the content will resize properly
		
		Container topMainContent=new Container();//Initialize a container to hold the three bars
		topMainContent.setLayout(new BoxLayout(topMainContent,BoxLayout.PAGE_AXIS));//Set the container to a vertically-aligned box layout
		
		JPanel tabBar=new JPanel();//Initialize the tab bar
		tabBar=createTabBar(tabBar);//Call a method to create the elements of the tab bar
		
		JPanel addressBar=new JPanel();//Initialize the address bar
		addressBar=createAddressBar(addressBar);//Call a method to create the elements of the address bar
		
		JPanel favBar=new JPanel();//Initialize the favorites bar
		favBar=createFavBar(favBar);//Call a method to create the elements of the favorites bar
		
		//Add the three bars to the previously defined outer container
		topMainContent.add(tabBar);
		topMainContent.add(addressBar);
		topMainContent.add(favBar);
		
		mainContent.add(topMainContent,BorderLayout.PAGE_START);//Add the previously defined outer container to the main pane
		
		//Create a wallpaper background for the main content
		ImageIcon wallpaperIcon = new ImageIcon(getClass().getResource("wallpaper.jpg"));
		JLabel wallpaperLabel=new JLabel(wallpaperIcon);
		mainContent.add(wallpaperLabel,BorderLayout.WEST);//Add the wallpaper background to the main pane
	}
	
	/**
	 * Creates and adds all of the tabs to the tab bar
	 * @param tabBar	JPanel which contains all of the tabs
	 * @return	JPanel with all of the tabs properly inserted and formatted
	 */
	public JPanel createTabBar(JPanel tabBar){
		tabBar.setAlignmentX(Component.LEFT_ALIGNMENT);//Ensure that the tab bar itself is left-aligned in the outer container
		tabBar.setLayout(new BoxLayout(tabBar,BoxLayout.LINE_AXIS));//Set the tab bar to be a horizontally-aligned box layout
		tabBar.setMaximumSize(new Dimension(Integer.MAX_VALUE,tabBar.getMaximumSize().height));//Set the tab bar to take the entire width in the outer container
		tabBar.setBackground(ADDRESS_COLOR);//Set the background color of the tab bar to match that of the address bar
		int padding=20;//Set a padding constant for the minimum size of the tab bar
		for(int i=0;i<tabNames.length;i++){//Iterate through each tab name
			ImageIcon tabIcon = new ImageIcon(getClass().getResource("tabIcons/"+tabIconNames[i]));//Create the corresponding image for the name
			tabIcon=new ImageIcon(tabIcon.getImage().getScaledInstance(TAB_ICON_SIZE, TAB_ICON_SIZE, Image.SCALE_SMOOTH));//Scale the corresponding image to be the correct size
			JButton tab=new JButton(tabNames[i],tabIcon);//Create the actual button with the image and the name
			tab.setMaximumSize(new Dimension(MAX_TAB_WIDTH,tab.getMaximumSize().height));//Set a maximum size for the tab
			tab.setMinimumSize(new Dimension(TAB_ICON_SIZE+padding,tab.getMaximumSize().height));//Set a minimum size for the tab, so that the icons are still displayed
			tab.setBackground(TAB_COLOR);//Set the tab color
			tabBar.add(tab);//Add the tab to the tab bar
		}
		ImageIcon addIcon=new ImageIcon(getClass().getResource("add.png"));//Create the image for the icon to add new tabs
		addIcon=new ImageIcon(addIcon.getImage().getScaledInstance(TAB_ADD_ICON_SIZE, TAB_ADD_ICON_SIZE, Image.SCALE_SMOOTH));//Scale the image to be the correct size
		JButton tabAdd=new JButton(addIcon);//Create the button to add new tabs
		//Set the size for the button to add new tabs
		tabAdd.setMaximumSize(new Dimension(TAB_ADD_ICON_SIZE+5,tabAdd.getMaximumSize().height));
		tabAdd.setMinimumSize(new Dimension(TAB_ADD_ICON_SIZE+5,tabAdd.getMaximumSize().height));
		//Ensure that the background and border of the button to add new tabs is transparent
		tabAdd.setOpaque(false);
		tabAdd.setContentAreaFilled(false);
		tabAdd.setBorderPainted(false);
		tabBar.add(tabAdd);//Add the button to add new tabs to the tab bar
		return tabBar;
	}
	
	/**
	 * Creates and adds the URL field and the preset buttons to the address bar
	 * @param addressBar	JPanel which contains the URL field and various preset buttons
	 * @return	JPanel with the URL field and the preset buttons properly formatted
	 */
	public JPanel createAddressBar(JPanel addressBar){
		addressBar.setAlignmentX(Component.LEFT_ALIGNMENT);//Ensure that the address bar itself is left-aligned in the outer container
		addressBar.setLayout(new BoxLayout(addressBar,BoxLayout.LINE_AXIS));//Set the address bar to be a horizontally-aligned box layout
		//Create and set a border for the address bar, to add padding between the other two bars
		Border line=BorderFactory.createLineBorder(ADDRESS_COLOR,5);
		addressBar.setBorder(line);
		addressBar.setBackground(ADDRESS_COLOR);//Set the background color of the address bar
		createAddressIcon();//Call a method to create all of the preset buttons on the address bar
		//Add all of the preset buttons to the address bar except
		addressBar.add(back);
		addressBar.add(forward);
		addressBar.add(refresh);
		addressBar.add(home);
		JTextField address=createAddressURL();//Initialize and call a method to create the URL field
		addressBar.add(address);//Add the URL field to the address bar
		addressBar.add(menu);//Add the last preset button to the address bar
		return addressBar;
	}
	
	/**
	 * Creates the URL field, with the requisite properties
	 * @return URL field for the address bar
	 */
	public JTextField createAddressURL(){
		JTextField address=new JTextField(){//Initialize the URL field
			 protected void paintComponent(Graphics g) {//Override paintComponent to add the globe and the favorite symbols to the URL field
			        super.paintComponent(g);
			        //Initialize the two symbols
					BufferedImage globe=null;
					BufferedImage heart=null;
					try {
						//Retrieve the two images for the symbols
						globe = ImageIO.read(getClass().getResource("globe.png"));
						heart=ImageIO.read(getClass().getResource("heart.png"));
					} catch (IOException e) {
						System.out.println("Address bar image error.");//Output an error if there is one
					}
					//Resize the globe symbol
					BufferedImage globeResize=new BufferedImage(ADDRESS_URL_BAR_ICON_SIZE,ADDRESS_URL_BAR_ICON_SIZE,globe.getType());
					Graphics2D gGlobe=globeResize.createGraphics();
					gGlobe.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
					gGlobe.drawImage(globe,0,0,ADDRESS_URL_BAR_ICON_SIZE,ADDRESS_URL_BAR_ICON_SIZE,null);
					gGlobe.dispose();
					//Resize the heart symbol
					BufferedImage heartResize=new BufferedImage(ADDRESS_URL_BAR_ICON_SIZE,ADDRESS_URL_BAR_ICON_SIZE,heart.getType());
					Graphics2D gHeart=heartResize.createGraphics();
					gHeart.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
					gHeart.drawImage(heart,0,0,ADDRESS_URL_BAR_ICON_SIZE,ADDRESS_URL_BAR_ICON_SIZE,null);
					gHeart.dispose();
					//Draw the two symbols in their proper locations
			        g.drawImage(globeResize,5,(getHeight()-globeResize.getHeight())/2,this);
			        g.drawImage(heartResize,getWidth()-heartResize.getWidth()-5,(getHeight()-globeResize.getHeight())/2,this);
			    }
		};
		int padding=5;//Set a padding constant for the margins
		address.setMargin(new Insets(0,ADDRESS_URL_BAR_ICON_SIZE+padding,0,ADDRESS_URL_BAR_ICON_SIZE+padding));//Set the margins so that the cursor never overlaps the symbols
		address.setMaximumSize(new Dimension(Integer.MAX_VALUE,address.getMaximumSize().height));//Make the URL field span as large of a width as it can
		return address;
	}
	
	/**
	 * Creates and adds all of the favorite folders and pages to the favorites bar
	 * @param favBar	JPanel which contains all of the favorite folders and pages
	 * @return	JPanel with all of the favorite folders and pages properly inserted and formatted
	 */
	public JPanel createFavBar(JPanel favBar){
		favBar.setAlignmentX(Component.LEFT_ALIGNMENT);//Ensure that the favorites bar itself is left-aligned in the outer container
		favBar.setLayout(new BoxLayout(favBar,BoxLayout.LINE_AXIS));//Set the favorites bar to be a horizontally-aligned box layout
		//Create and set a border for the favorites bar, to add padding
		Border line=BorderFactory.createLineBorder(FAV_COLOR,8);
		favBar.setBorder(line);
		favBar.setBackground(FAV_COLOR);//Set the background color of the favorites bar
		favBar.setMaximumSize(new Dimension(Integer.MAX_VALUE,favBar.getMaximumSize().height));//Set the favorites bar to take the entire width in the outer container
		JLabel fav=new JLabel();//Initialize the JLabel for the favorite folders and pages 
		ImageIcon favIcon = new ImageIcon(getClass().getResource("folder.png"));//Create the icon for the folders
		favIcon=new ImageIcon(favIcon.getImage().getScaledInstance(FAV_ICON_SIZE, FAV_ICON_SIZE, Image.SCALE_SMOOTH));//Scale the icon to the appropriate size
		favBar.add(Box.createRigidArea(new Dimension(10,0)));//Add some initial spacing so that the first favorite folder or page is not too close to the left edge
		for(int i=0;i<favNames.length;i++){//Iterate through each favorite name
			if(favIconNames[i].equals("")){//Set the icon as the folder icon if no page icon is specified
				fav=new JLabel(favNames[i],favIcon,JLabel.LEFT);//Set the JLabel for the favorite folder
			}
			else{//Set the icon as the specified page icon
				ImageIcon favIconOther=new ImageIcon(getClass().getResource("tabIcons/"+favIconNames[i]));//Create the icon for the corresponding page
				favIconOther=new ImageIcon(favIconOther.getImage().getScaledInstance(FAV_ICON_SIZE, FAV_ICON_SIZE, Image.SCALE_SMOOTH));//Scale the icon to the appropriate size
				fav=new JLabel(favNames[i],favIconOther,JLabel.LEFT);//Set the JLabel for the favorite page
			}
			fav.setForeground(FAV_TEXT_COLOR);//Set the text color of the favorite page or folder
			favBar.add(fav);//Add the JLabel to the favorites bar
			favBar.add(Box.createRigidArea(new Dimension(20,0)));//Add spacing between each favorite page or folder
		}
		return favBar;
	}
	
	/**
	 * Creates the corresponding images and sets all of the preset icons in the address bar
	 */
	public void createAddressIcon(){
		//Create the images for each of the icons
		ImageIcon backIcon = new ImageIcon(getClass().getResource("back.png"));
		ImageIcon forwardIcon = new ImageIcon(getClass().getResource("forward.png"));
		ImageIcon refreshIcon = new ImageIcon(getClass().getResource("refresh.png"));
		ImageIcon homeIcon=new ImageIcon(getClass().getResource("home.png"));
		ImageIcon menuIcon=new ImageIcon(getClass().getResource("menu.png"));
		//Resize the images for each of the icons to the appropriate size
		backIcon=new ImageIcon(backIcon.getImage().getScaledInstance(ADDRESS_ICON_SIZE, ADDRESS_ICON_SIZE, Image.SCALE_SMOOTH));
		forwardIcon=new ImageIcon(forwardIcon.getImage().getScaledInstance(ADDRESS_ICON_SIZE, ADDRESS_ICON_SIZE, Image.SCALE_SMOOTH));
		refreshIcon=new ImageIcon(refreshIcon.getImage().getScaledInstance(ADDRESS_ICON_SIZE, ADDRESS_ICON_SIZE, Image.SCALE_SMOOTH));
		homeIcon=new ImageIcon(homeIcon.getImage().getScaledInstance(ADDRESS_ICON_SIZE, ADDRESS_ICON_SIZE, Image.SCALE_SMOOTH));
		menuIcon=new ImageIcon(menuIcon.getImage().getScaledInstance(ADDRESS_ICON_SIZE, ADDRESS_ICON_SIZE, Image.SCALE_SMOOTH));
		//Set each icon to the corresponding button
		back=new JButton(backIcon);
		forward=new JButton(forwardIcon);
		refresh=new JButton(refreshIcon);
		home=new JButton(homeIcon);
		menu=new JButton(menuIcon);
		//Make the background and borders of each button transparent
		back.setContentAreaFilled(false);
		back.setBorderPainted(false);
		back.setOpaque(false);
		forward.setContentAreaFilled(false);
		forward.setBorderPainted(false);
		forward.setOpaque(false);
		refresh.setContentAreaFilled(false);
		refresh.setBorderPainted(false);
		refresh.setOpaque(false);
		home.setContentAreaFilled(false);
		home.setBorderPainted(false);
		home.setOpaque(false);
		menu.setContentAreaFilled(false);
		menu.setBorderPainted(false);
		menu.setOpaque(false);
		int padding=10;//Set a padding constant for the dimensions of each button
		Dimension iconDim=new Dimension(ADDRESS_ICON_SIZE+padding,ADDRESS_ICON_SIZE+padding);//Set the dimension constant for each button
		//Set the dimensions of each button
		back.setPreferredSize(iconDim);
		forward.setPreferredSize(iconDim);
		refresh.setPreferredSize(iconDim);
		home.setPreferredSize(iconDim);
		menu.setPreferredSize(iconDim);
		back.setMaximumSize(iconDim);
		forward.setMaximumSize(iconDim);
		refresh.setMaximumSize(iconDim);
		home.setMaximumSize(iconDim);
		menu.setMaximumSize(iconDim);
		back.setMinimumSize(iconDim);
		forward.setMinimumSize(iconDim);
		refresh.setMinimumSize(iconDim);
		home.setMinimumSize(iconDim);
		menu.setMinimumSize(iconDim);
	}
	
	/**
	 * Create the frame for the layout
	 * @param args
	 */
	public static void main(String[] args) {
		LayoutManager aFrame=new LayoutManager("Hi");//Initialize a new frame
		aFrame.setSize(1000,600);//Explicitly set the initial size
		aFrame.setVisible(true);//Show the frame
	}

}
