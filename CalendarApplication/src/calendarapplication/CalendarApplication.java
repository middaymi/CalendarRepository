package calendarapplication;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Point;
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
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;


public class CalendarApplication {    
 
    //for create image on the frame
    public static class ImagePanel extends JPanel {        
        Image image;
        public void SetBackground(Image image) {
            this.image = image;
        }
        public ImagePanel() {
            setOpaque(false);
        }
        @Override
        public void paintComponent(Graphics G) {
            super.paintComponent(G);
            G.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), null);
            Graphics2D g2d = (Graphics2D) G.create();     
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.0f));
            g2d.setColor(getBackground());
            g2d.fill(getBounds());
            g2d.dispose();
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
            JPanel turnClosePanel;
            JPanel bottomPanel = new JPanel();
            ImageIcon iconWeek = new ImageIcon("images\\WEEK_1.png");;
            ImageIcon iconMonthYear = new ImageIcon("images\\MONTH_YEAR_1.png");
            ImageIcon iconSettings = new ImageIcon("images\\SET.png");
            ImageIcon iconTurn = new ImageIcon("images\\turn2.png");
            ImageIcon iconClose = new ImageIcon("images\\close2.png");
            Image imgWeek;
            Image imgMonthYear;
            //Image img;
            Image imgSettings;
            Image imgTurn;
            Image imgClose;
            JButton buttonWeek = new JButton();
            JButton buttonMonth = new JButton();
            JButton buttonYear = new JButton();;
            JButton buttonSettings = new JButton();;
            JButton turn = new JButton();;
            JButton close = new JButton();
        
    	PaintMainFrame() {
            super("Calendar");           
            ImagePanel backgroundPanel = null; 
            try {    
                setUndecorated(true);
                setBackground(new Color(0, 0, 0, 0));       
                backgroundPanel = new ImagePanel();
                backgroundPanel.SetBackground(ImageIO.read(new File("images\\M.png")));
                this.setOpacity(1);
    	    } catch (IOException e) {
    	    } 
            setDefaultCloseOperation(EXIT_ON_CLOSE); 
                                
            imgWeek = iconWeek.getImage();
            imgMonthYear = iconMonthYear.getImage();
            imgSettings = iconSettings.getImage();
            imgTurn = iconTurn.getImage();
            imgClose = iconClose.getImage();
           
            iconWeek = new ImageIcon(imgWeek);
            iconMonthYear = new ImageIcon(imgMonthYear);
            iconSettings = new ImageIcon(imgSettings);
            iconTurn = new ImageIcon(imgTurn);
            iconClose = new ImageIcon(imgClose);       
                                                                                          
            backgroundPanel.setLayout(null);             
            
            
            //*********************START TurnClosePanel*************************
            //settings panel
            turnClosePanel = new JPanel();
            turnClosePanel.setLayout(new GridBagLayout());
            turnClosePanel.setOpaque(false);
            setSizeLocationCloseTurnPanel(turnClosePanel);          
            GridBagConstraints tc = new GridBagConstraints();
            //button turn
            turn = new JButton();
            turn.setIcon(iconTurn);
            turn.setBorderPainted(false);
            turn.setFocusPainted(false);
            turn.setContentAreaFilled(false);
            tc.weightx = 0.25;
            tc.weighty = 0.25;
            tc.fill = GridBagConstraints.BOTH;
            tc.gridx = 0;
            tc.gridy = 0;
            turn.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                JButton button = (JButton) e.getComponent();
                Dimension size = button.getSize();
                Image scaled = imgTurn.getScaledInstance(size.width, size.height, java.awt.Image.SCALE_SMOOTH);
                button.setIcon(new ImageIcon(scaled));
            };});
            turnClosePanel.add(turn, tc);
            
            //button close
            close = new JButton();
            close.setIcon(iconClose);
            close.setBorderPainted(false);
            close.setFocusPainted(false);
            close.setContentAreaFilled(false);
            tc.weightx = 0.25;
            tc.weighty = 0.25;
            tc.fill = GridBagConstraints.BOTH;
            tc.gridx = 1;
            tc.gridy = 0;
            close.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                JButton button = (JButton) e.getComponent();
                Dimension size = button.getSize();
                Image scaled = imgClose.getScaledInstance(size.width, size.height, java.awt.Image.SCALE_SMOOTH);
                button.setIcon(new ImageIcon(scaled));
            };});
            turnClosePanel.add(close, tc);
            backgroundPanel.add(turnClosePanel); 
            //************************END TurnClosePanel************************          
            
          
            //******************START nextPrevPanel*****************************
            CalendarTable tb = new CalendarTable();
            backgroundPanel.add(tb.nextPrevPanel);  
            //******************END nextPrevPanel*******************************
          

            //******************START ChangeYearPanel*************************** 
            backgroundPanel.add(tb.changeYearPanel);
            //******************END ChangeYearPanel*****************************           
            
            
            //************************START BottomPanel*************************
            //settings bottom panel
            bottomPanel.setLayout(new GridBagLayout());
            setSizeLocationBottomPanel(bottomPanel);
            bottomPanel.setOpaque(false);
            GridBagConstraints bp = new GridBagConstraints(); 
            
            buttonWeek = new JButton();
            buttonWeek.setIcon(iconWeek);
            buttonWeek.setPressedIcon(new ImageIcon("images\\WeekPr.png"));
            buttonWeek.setRolloverIcon(new ImageIcon("images\\WeekPr.png"));
            buttonWeek.setBorderPainted(false);
            buttonWeek.setFocusPainted(false);
            buttonWeek.setContentAreaFilled(false);
            bp.weightx = 0.25;
            bp.weighty = 0.25;
            bp.fill = GridBagConstraints.BOTH;
            bp.gridx = 0;
            bp.gridy = 0;
            buttonWeek.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                JButton button = (JButton) e.getComponent();
                Dimension size = button.getSize();
                Image scaled = imgWeek.getScaledInstance(size.width, size.height, java.awt.Image.SCALE_SMOOTH);
                button.setIcon(new ImageIcon(scaled));
            };});
            bottomPanel.add(buttonWeek, bp);
        
            buttonMonth = new JButton();
            buttonMonth.setIcon(iconMonthYear);
            buttonMonth.setPressedIcon(new ImageIcon("images\\MonthAndYearPr.png"));
            buttonMonth.setRolloverIcon(new ImageIcon("images\\MonthAndYearPr.png"));
            buttonMonth.setBorderPainted(false);
            buttonMonth.setFocusPainted(false);
            buttonMonth.setContentAreaFilled(false);
            bp.fill = GridBagConstraints.BOTH;
            bp.weightx = 0.25;
            bp.weighty = 0.25;
            bp.gridx = 1;
            bp.gridy = 0;
            buttonMonth.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                JButton button = (JButton) e.getComponent();
                Dimension size = button.getSize();
                Image scaled = imgMonthYear.getScaledInstance(size.width, size.height, java.awt.Image.SCALE_SMOOTH);
                button.setIcon(new ImageIcon(scaled));
                };});
            bottomPanel.add(buttonMonth, bp);
        
            buttonYear = new JButton();
            buttonYear.setIcon(iconMonthYear);
            buttonYear.setPressedIcon(new ImageIcon("images\\MonthAndYearPr.png"));
            buttonYear.setRolloverIcon(new ImageIcon("images\\MonthAndYearPr.png"));
            buttonYear.setBorderPainted(false);
            buttonYear.setFocusPainted(false);
            buttonYear.setContentAreaFilled(false);
            bp.fill = GridBagConstraints.BOTH;
            bp.weightx = 0.25;
            bp.weighty = 0.25;
            bp.gridx = 2;
            bp.gridy = 0;
            buttonYear.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                JButton button = (JButton) e.getComponent();
                Dimension size = button.getSize();
                Image scaled = imgMonthYear.getScaledInstance(size.width, size.height, java.awt.Image.SCALE_SMOOTH);
                button.setIcon(new ImageIcon(scaled));
                };}); 
            bottomPanel.add(buttonYear, bp);
        
            buttonSettings = new JButton();
            buttonSettings.setIcon(iconSettings);
            buttonSettings.setPressedIcon(new ImageIcon("images\\SetPr.png"));
            buttonSettings.setRolloverIcon(new ImageIcon("images\\SetPr.png"));
            buttonSettings.setBorderPainted(false);
            buttonSettings.setFocusPainted(false);
            buttonSettings.setContentAreaFilled(false);
            bp.fill = GridBagConstraints.BOTH;
            bp.weightx = 0.25;
            bp.weighty = 0.25;
            bp.gridx = 3;
            bp.gridy = 0;
            buttonSettings.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                JButton button = (JButton) e.getComponent();
                Dimension size = button.getSize();
                Image scaled = imgSettings.getScaledInstance(size.width, size.height, java.awt.Image.SCALE_SMOOTH);
                button.setIcon(new ImageIcon(scaled));
                };});
            bottomPanel.add(buttonSettings, bp);          
            backgroundPanel.add(bottomPanel);
            //******************END BottomPanel*********************************
          
            setContentPane(backgroundPanel);
            pack();            
    	    setVisible(true);        
        }
    }
    
    //get rezolution of the screen
    public static Dimension getRezolution(){        
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        System.out.println(size);
        return (size);        
    }
    //get needed size of frame
    public static int frameHeight(Dimension exSize){
        System.out.println(exSize.height*2/3);
        return(exSize.height*2/3);
    }
    //set icon on the frame and application's icon
    private static void setApplicationIcon(JFrame frame) {
        Image im = Toolkit.getDefaultToolkit().getImage("images\\48.png");
        frame.setIconImage(im);
        frame.show();
    } 
    public static JPanel setSizeLocationBottomPanel(JPanel pane) {  
        Dimension size = new Dimension();
        size.height = (int)(frameHeight(getRezolution())/12); 
        size.width  = (int)(2*frameHeight(getRezolution())/3);
        
        Point location = new Point();
        location.x = (int)(0.15*frameHeight(getRezolution()));
        location.y = (int) (frameHeight(getRezolution()) 
                            - size.height
                            - CalendarTable.CountBorder()
                            - CalendarTable.CountBorder()/3);      
        pane.setSize(size);
        pane.setLocation(location);
        return (pane); 
    }    
    public static JPanel setSizeLocationChangeYearPanel(JPanel pane) {
        Dimension size = new Dimension();
        size.width  = (int)(5*frameHeight(getRezolution())/24);
        size.height = (int)(frameHeight(getRezolution())/30); 
               
        Point location = new Point();
        location.x = (int)(0.025*frameHeight(getRezolution()));
        location.y = (int)(11*frameHeight(getRezolution())/48);        
        
        pane.setSize(size);
        pane.setLocation(location);
        return (pane); 
    } 
    public static JPanel setSizeLocationNextPrevPanel(JPanel pane) {
        Dimension size = new Dimension();
        size.width  = (int)(frameHeight(getRezolution()) - 2*CalendarTable.CountBorder());
        size.height = (int)(0.075*frameHeight(getRezolution())); 
               
        Point location = new Point();
        location.x = (int)(0.026*frameHeight(getRezolution()));
        location.y = (int)(7*frameHeight(getRezolution())/48);        
        
        pane.setSize(size);
        pane.setLocation(location);
        return (pane); 
    }
    public static JButton setSizeButtonsNextPrevPanel(JButton button) {
        Dimension size = new Dimension();
        size.width  = (int)(0.925*frameHeight(getRezolution())/3);
        size.height = (int)(0.075*frameHeight(getRezolution()));       
        
        button.setSize(size);
        return (button); 
    } 
    public static JPanel setSizeLocationCloseTurnPanel(JPanel pane) {
        Dimension size = new Dimension();
        size.width  = (int)(11*frameHeight(getRezolution())/120);
        size.height = (int)(frameHeight(getRezolution())/24); 
               
        Point location = new Point();
        location.x = (int)(frameHeight(getRezolution()) 
                           - CalendarTable.CountBorder()
                           - size.width);
        location.y = (int)(frameHeight(getRezolution())/40);        
        
        pane.setSize(size);
        pane.setLocation(location);   
        return (pane); 
    }
    
    
    public static void main(String[] args) {
        PaintMainFrame frame = new PaintMainFrame();
        JFrame.setDefaultLookAndFeelDecorated(true);
        frame.setSize(frameHeight(getRezolution()),frameHeight(getRezolution()));
        setApplicationIcon(frame);
        
        
        frame.setLayout(null);
        frame.setVisible(true);       
        //MovementPanel
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        int a = (size.height*2/3);
        JPanel graphics =  new  JPanel();
        graphics.setBounds(0 , 0, a-a/a/3, a/8-6);
        graphics.setBorder(new LineBorder(new Color(0.0f, 0.0f, 0.0f,0.0f), 50, true));
        graphics.setOpaque (false);
        frame.add(graphics);
        MoveMouseListener mml = new MoveMouseListener(graphics, frame);
        graphics.addMouseListener(mml);
        graphics.addMouseMotionListener(mml);
    }    
}