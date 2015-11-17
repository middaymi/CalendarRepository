package calendarapplication;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JPanel;

public class CalendarApplication {    
 
    //for create image on the frame
    public static class ImagePanel extends JPanel {        
        Image image;
        public void SetBackground(Image image) {
            this.image = image;
        }
        @Override
        public void paintComponent(Graphics G) {
            super.paintComponent(G);
            G.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), null);
        }
    }
          
    //create main frame
    public static class PaintMainFrame extends JFrame  {  
        
    	PaintMainFrame() {
            super("Calendar");           
            ImagePanel backgroundPanel = null; 
            try {                
                backgroundPanel = new ImagePanel();
                backgroundPanel.SetBackground(ImageIO.read(new File("images\\MainPanel.png")));
                this.setOpacity(1);
    	    } catch (IOException e) {
    	    } 
            setDefaultCloseOperation(EXIT_ON_CLOSE); 
            
            ImageIcon icon1 = new ImageIcon("images\\WEEK_1.png");
            ImageIcon icon2 = new ImageIcon("images\\MONTH_YEAR_1.png");
            ImageIcon icon3 = new ImageIcon("images\\MONTH_YEAR_1.png");
            ImageIcon icon4 = new ImageIcon("images\\SET.png");
            
            Image img1 = icon1.getImage();
            Image img2 = icon2.getImage();
            Image img3 = icon3.getImage() ;
            Image img4 = icon4.getImage();
            
            icon1 = new ImageIcon(img1);
            icon2 = new ImageIcon(img2);
            icon3 = new ImageIcon(img3);
            icon4 = new ImageIcon(img4);
            
            //create bottom panel
            //HoorizontalStrut on the bottom panel = -34 
            JPanel BottomPanel = new JPanel();
            BottomPanel.setLayout(new GridBagLayout());
	    GridBagConstraints c = new GridBagConstraints();
           
            //***BUTTONS START***
            //button "week"
            JButton buttonWeek = new JButton();
            buttonWeek.setIcon(icon1);
//            buttonWeek.setText("Week"); 
//            buttonWeek.setFont(new Font("Arial", Font.PLAIN, 40));
//            buttonWeek.setHorizontalTextPosition(AbstractButton.CENTER);
            //change when press
            buttonWeek.setPressedIcon(new ImageIcon("images\\WeekPr.png"));
            buttonWeek.setRolloverIcon(new ImageIcon("images\\WeekPr.png"));
            //unvisible button, visible icon
            buttonWeek.setBorderPainted(false);
            buttonWeek.setFocusPainted(false);
            buttonWeek.setContentAreaFilled(false);
            c.weightx = 0.5;
            c.weighty = 0.25;
            c.fill = GridBagConstraints.BOTH;
            c.ipadx = 100;
            c.ipady = 200;
            c.gridx = 0;
            c.gridy = 1;
            BottomPanel.add(buttonWeek, c);
                                          
            //button "month"
            JButton buttonMonth = new JButton();
            buttonMonth.setIcon(icon2); 
//            buttonMonth.setText("Month"); 
//            buttonMonth.setFont(new Font("Arial", Font.PLAIN, 40));
//            buttonMonth.setHorizontalTextPosition(AbstractButton.CENTER);
            buttonMonth.setPressedIcon(new ImageIcon("images\\MonthAndYearPr.png"));
            buttonMonth.setRolloverIcon(new ImageIcon("images\\MonthAndYearPr.png"));
//            buttonMonth.setBorderPainted(false);
//            buttonMonth.setFocusPainted(false);
//            buttonMonth.setContentAreaFilled(false);
            c.weightx = 0.5;
            c.weighty = 0.25;
            c.fill = GridBagConstraints.BOTH;
            c.ipadx = 100;
            c.ipady = 200;
            c.gridx = 1;
            c.gridy = 1;
            BottomPanel.add(buttonMonth, c);
            
            //button "year"
            JButton buttonYear = new JButton();
            buttonYear.setIcon(icon2);
//            buttonYear.setText("Year"); 
//            buttonYear.setFont(new Font("Arial", Font.PLAIN, 40));
//            buttonYear.setHorizontalTextPosition(AbstractButton.CENTER);
            buttonYear.setPressedIcon(new ImageIcon("images\\MonthAndYearPr.png"));
            buttonYear.setRolloverIcon(new ImageIcon("images\\MonthAndYearPr.png"));
//            buttonYear.setBorderPainted(false);
//            buttonYear.setFocusPainted(false);
//            buttonYear.setContentAreaFilled(false);
            c.weightx = 0.5;
            c.weighty = 0.25;
            c.fill = GridBagConstraints.BOTH;
            c.ipadx = 100;
            c.ipady = 200;
            c.gridx = 2;
            c.gridy = 1;
            BottomPanel.add(buttonYear, c);
           
            //button "set"
            JButton buttonSettings = new JButton();
            buttonSettings.setIcon(icon4);
            buttonSettings.setPressedIcon(new ImageIcon("images\\SetPr.png"));
            buttonSettings.setRolloverIcon(new ImageIcon("images\\SetPr.png"));
//            buttonSettings.setBorderPainted(false);
//            buttonSettings.setFocusPainted(false);
//            buttonSettings.setContentAreaFilled(false);
            c.weightx = 0.5;
            c.weighty = 0.25;
            c.fill = GridBagConstraints.BOTH;
            c.ipadx = 100;
            c.ipady = 200;
            c.gridx = 3;
            c.gridy = 1;
            BottomPanel.add(buttonSettings, c);
             
            //arrow-button left
            JButton ButtonLeft = new JButton();
            ButtonLeft.setIcon(new ImageIcon("images\\Left.png"));
            ButtonLeft.setPressedIcon(new ImageIcon("images\\LeftPr.png"));
            ButtonLeft.setRolloverIcon(new ImageIcon("images\\LeftPr.png"));
            ButtonLeft.setBorderPainted(false);
            ButtonLeft.setFocusPainted(false);
            ButtonLeft.setContentAreaFilled(false);
            
            //between arrow keys
            JButton betweenLeftAndRight = new JButton("Month");
            betweenLeftAndRight.setBorderPainted(false);
            betweenLeftAndRight.setFocusPainted(false);
            betweenLeftAndRight.setContentAreaFilled(false);
            
            //arrow-button right
            JButton ButtonRight = new JButton();
            ButtonRight.setIcon(new ImageIcon("images\\Right.png"));
            ButtonRight.setPressedIcon(new ImageIcon("images\\RightPr.png"));
            ButtonRight.setRolloverIcon(new ImageIcon("images\\RightPr.png"));
            ButtonRight.setBorderPainted(false);
            ButtonRight.setFocusPainted(false);
            ButtonRight.setContentAreaFilled(false);
            
            //close and turn buttons under each other on the right top
            JButton Turn = new JButton();
            Turn.setIcon(new ImageIcon("images\\Turn.png"));
            Turn.setBorderPainted(false);
            Turn.setFocusPainted(false);
            Turn.setContentAreaFilled(false);
                      
            JButton Close = new JButton();
            Close.setIcon(new ImageIcon("images\\Close.png"));
            Close.setBorderPainted(false);
            Close.setFocusPainted(false);
            Close.setContentAreaFilled(false);
            Close.setSize(300, 115);
            //***BUTTONS END***             
            
            
            //top panel
            //JPanel TopPanel = new JPanel();
            //BorderLayout top = new BorderLayout(); 
            //TopPanel.setLayout(top);   
            //TopPanel.add(ButtonLeft);
            //TopPanel.add(betweenLeftAndRight);         
            //TopPanel.add(ButtonRight);            
            
            //BottomPanel.add(createVerticalStrut(FrameHeight(getRezolution())*105/61 + 1000));
          
            //del gap between buttons
            //del Border between buttons and icon(image)
            //buttonWeek.setMargin(new Insets(-3, -3, -3, -3));
            //bottomPanel.add(buttonWeek);                        
            //buttonMonth.setMargin(new Insets(-3, -3, -3, -3));
            //BottomPanel.add(buttonMonth);                       
            //buttonYear.setMargin(new Insets(-3, -3, -3, -3));
            //BottomPanel.add(buttonYear);            
            //buttonSettings.setMargin(new Insets(-3, -3, -3, -3));
            //BottomPanel.add(buttonSettings); 
            
            CalendarTable tb = new CalendarTable();
            //backgroundPanel.setLayout(new BorderLayout());
            backgroundPanel.setLayout(new GridBagLayout());
            GridBagConstraints a = new GridBagConstraints(); 
            
            //CalendarTable
            //a.weightx = 1;
            //a.weighty = 0.70;
            a.fill = GridBagConstraints.BOTH;
            a.ipady = 600;
            a.gridx = 0;
            a.gridy = 0;
            a.insets = new Insets (10, 10, 10, 10);
            backgroundPanel.add(tb, a);            
            
            //BottomPanel
            a.weightx = 0.25;
            a.weighty = 0.25;
            a.fill = GridBagConstraints.BOTH;
            a.ipady = 1;
            a.gridx = 0;
            a.gridy = 200;
            a.anchor = GridBagConstraints.CENTER;
            a.insets = new Insets (135, 170, 40, 190);
            backgroundPanel.add(BottomPanel, a);          
            
            //backgroundPanel.add(tb, BorderLayout.NORTH);
            //backgroundPanel.add(BottomPanel, BorderLayout.CENTER);
            //backgroundPanel.add(BottomPanel);
           
            //BottomPanel.setVisible(true);

            setContentPane(backgroundPanel);
            pack();            
    	    setVisible(true);
            
        buttonWeek.addComponentListener(new ComponentAdapter() {
        @Override
        public void componentResized(ComponentEvent e) {
            JButton btn1 = (JButton) e.getComponent();
            Dimension size1 = btn1.getSize();
            //System.out.println("size_1 =" + size1);
            Image scaled = img1.getScaledInstance(size1.width, size1.height, java.awt.Image.SCALE_SMOOTH);
            btn1.setIcon(new ImageIcon(scaled));
            };});
        buttonMonth.addComponentListener(new ComponentAdapter() {
        @Override
        public void componentResized(ComponentEvent e) {
            JButton btn2 = (JButton) e.getComponent();
            Dimension size2 = btn2.getSize();
            System.out.println("size_2 =" + size2);
            Image scaled = img2.getScaledInstance(size2.width, size2.height, java.awt.Image.SCALE_SMOOTH);
            btn2.setIcon(new ImageIcon(scaled));
            };});     
        buttonYear.addComponentListener(new ComponentAdapter() {
        @Override
        public void componentResized(ComponentEvent e) {
            JButton btn3 = (JButton) e.getComponent();
            Dimension size3 = btn3.getSize();
            System.out.println("size_2 =" + size3);
            Image scaled = img3.getScaledInstance(size3.width, size3.height, java.awt.Image.SCALE_SMOOTH);
            btn3.setIcon(new ImageIcon(scaled));
            };}); 
        buttonSettings.addComponentListener(new ComponentAdapter() {
        @Override
        public void componentResized(ComponentEvent e) {
            JButton btn4 = (JButton) e.getComponent();
            Dimension size4 = btn4.getSize();
            //System.out.println("size_2 =" + size4);
            Image scaled = img4.getScaledInstance(size4.width, size4.height, java.awt.Image.SCALE_SMOOTH);
            btn4.setIcon(new ImageIcon(scaled));
            };});
        }
    }
    //get rezolution of the screen
    private static Dimension getRezolution(){        
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        System.out.println(size);
        return (size);        
    }
    //get needed size of frame
    private static int FrameHeight(Dimension exSize){
        System.out.println(exSize.height*2/3);
        return(exSize.height*2/3);
    }
    //set icon on the frame and application's icon
    private static void SetApplicationIcon(JFrame frame) {
        Image im = Toolkit.getDefaultToolkit().getImage("images\\48.png");
        frame.setIconImage(im);
        frame.show();
    }   
    public static void main(String[] args) {
        PaintMainFrame frame = new PaintMainFrame();
        JFrame.setDefaultLookAndFeelDecorated(true);
        frame.setSize(FrameHeight(getRezolution()),FrameHeight(getRezolution()));
        SetApplicationIcon(frame);
    }    
}