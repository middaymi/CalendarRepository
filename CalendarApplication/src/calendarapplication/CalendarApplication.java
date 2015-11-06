package calendarapplication;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.Box;
import static javax.swing.Box.createHorizontalGlue;
import static javax.swing.Box.createHorizontalStrut;
import static javax.swing.Box.createVerticalStrut;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
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
     
      public static class ImageLabel extends JLabel {        
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
    	    } catch (IOException e) {
    	    } 
            setDefaultCloseOperation(EXIT_ON_CLOSE); 
                                    
            //***BUTTONS START***
            //button "week"
            JButton buttonWeek = new JButton();
            buttonWeek.setIcon(new ImageIcon("images\\Week.png"));
            //change when press
            buttonWeek.setPressedIcon(new ImageIcon("images\\WeekPr.png"));
            buttonWeek.setRolloverIcon(new ImageIcon("images\\WeekPr.png"));
            //unvisible button, visible icon
            buttonWeek.setBorderPainted(false);
            buttonWeek.setFocusPainted(false);
            buttonWeek.setContentAreaFilled(false);
                        
            //button "month"
            JButton buttonMonth = new JButton();
            buttonMonth.setIcon(new ImageIcon("images\\Month.png"));
            buttonMonth.setPressedIcon(new ImageIcon("images\\MonthAndYearPr.png"));
            buttonMonth.setRolloverIcon(new ImageIcon("images\\MonthAndYearPr.png"));
            buttonMonth.setBorderPainted(false);
            buttonMonth.setFocusPainted(false);
            buttonMonth.setContentAreaFilled(false);
            
            //button "year"
            JButton buttonYear = new JButton();
            buttonYear.setIcon(new ImageIcon("images\\Year.png"));
            buttonYear.setPressedIcon(new ImageIcon("images\\MonthAndYearPr.png"));
            buttonYear.setRolloverIcon(new ImageIcon("images\\MonthAndYearPr.png"));
            buttonYear.setBorderPainted(false);
            buttonYear.setFocusPainted(false);
            buttonYear.setContentAreaFilled(false);
           
            //button "set"
            JButton buttonSettings = new JButton();
            buttonSettings.setIcon(new ImageIcon("images\\Set.png"));
            buttonSettings.setPressedIcon(new ImageIcon("images\\SetPr.png"));
            buttonSettings.setRolloverIcon(new ImageIcon("images\\SetPr.png"));
            buttonSettings.setBorderPainted(false);
            buttonSettings.setFocusPainted(false);
            buttonSettings.setContentAreaFilled(false);
             
            //arrow-button left
            JButton ButtonLeft = new JButton();
            ButtonLeft.setIcon(new ImageIcon("images\\Left.png"));
            ButtonLeft.setPressedIcon(new ImageIcon("images\\LeftPr.png"));
            ButtonLeft.setRolloverIcon(new ImageIcon("images\\LeftPr.png"));
            ButtonLeft.setBorderPainted(false);
            ButtonLeft.setFocusPainted(false);
            ButtonLeft.setContentAreaFilled(false);
            
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
            JPanel TopPanel = new JPanel();
            BorderLayout top = new BorderLayout(); 
            TopPanel.setLayout(top);   
            TopPanel.add(ButtonLeft);
            TopPanel.add(betweenLeftAndRight);         
            TopPanel.add(ButtonRight);
              
            //bottom 
            //HoorizontalStrut on the bottom panel = -34 
            JPanel BottomPanel = new JPanel();
            BottomPanel.setOpaque(false);
            BoxLayout bottom = new BoxLayout(BottomPanel, BoxLayout.LINE_AXIS); 
            BottomPanel.setLayout(bottom);            
            BottomPanel.add(createVerticalStrut(FrameHeight(getRezolution())*105/61));
          
            buttonWeek.setMargin(new Insets(-3, -3, -3, -3));
            BottomPanel.add(buttonWeek);                        
            buttonMonth.setMargin(new Insets(-3, -3, -3, -3));
            BottomPanel.add(buttonMonth);                       
            buttonYear.setMargin(new Insets(-3, -3, -3, -3));
            BottomPanel.add(buttonYear);            
            buttonSettings.setMargin(new Insets(-3, -3, -3, -3));
            BottomPanel.add(buttonSettings); 
            
            CalendarTable tb = new CalendarTable();
            backgroundPanel.setLayout(new BorderLayout());
            backgroundPanel.add(tb, BorderLayout.NORTH);
            //backgroundPanel.add(TopPanel);
            backgroundPanel.add(BottomPanel, BorderLayout.CENTER);
            //backgroundPanel.setLayout(new BoxLayout(backgroundPanel, BoxLayout.Y_AXIS));
            //backgroundPanel.add(tb);
            //backgroundPanel.add(BottomPanel);
            setContentPane(backgroundPanel);            
            pack();            
    	    setVisible(true);          
        }
    }
    
    private static Dimension getRezolution(){        
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        System.out.println(size);
        return (size);        
    }
    private static int FrameHeight(Dimension exSize){
        System.out.println(exSize.height*2/3);
        return(exSize.height*2/3);
    }    
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