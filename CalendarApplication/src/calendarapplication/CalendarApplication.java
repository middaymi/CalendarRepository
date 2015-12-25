package calendarapplication;


import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
 static Sizes size;
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
            
            static JPanel turnClosePanel;           
            
            static CalendarTable tb = new CalendarTable();
            static ImagePanel backgroundPanel = null;        
            
            ImageIcon iconTurn = new ImageIcon("images\\turn2.png");
            ImageIcon iconClose = new ImageIcon("images\\close2.png");
            Image imgTurn;
            Image imgClose;
            
            JButton turn = new JButton();
            JButton close = new JButton();
        
    	PaintMainFrame() {
            super("Viseman"); 
            size = new Sizes();
            try {    
                setUndecorated(true);
                setBackground(new Color(0, 0, 0, 0));       
                backgroundPanel = new ImagePanel();
                backgroundPanel.SetBackground(ImageIO.read(new File("images\\M.png")));
                this.setOpacity(1);
    	    } catch (IOException e) {
    	    } 
            setDefaultCloseOperation(EXIT_ON_CLOSE); 
                                
            imgTurn = iconTurn.getImage();
            imgClose = iconClose.getImage();
            iconTurn = new ImageIcon(imgTurn);
            iconClose = new ImageIcon(imgClose);       
                                                                                          
            backgroundPanel.setLayout(null);             
            
            
            //*********************START TurnClosePanel*************************
            //settings panel
            turnClosePanel = new JPanel();
            turnClosePanel.setLayout(new GridBagLayout());
            turnClosePanel.setOpaque(false);
            size.setSizeLocationCloseTurnPanel(turnClosePanel);          
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
            //ActionTurnBotton
            turn.addActionListener(new ActionListener() {
               public void actionPerformed(ActionEvent e) {
              setState  (JFrame.ICONIFIED);                      
               }
            });
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
            //ActionCloseBotton
            close.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {	
	            System.exit(0);
		}
	    });
            turnClosePanel.add(close, tc);
            backgroundPanel.add(turnClosePanel); 
            //************************END TurnClosePanel************************                      
          
            //******************START nextPrevPanel*****************************
            backgroundPanel.add(tb.nextPrevPanel);  
            //******************END nextPrevPanel*******************************          

            //******************START ChangeYearPanel*************************** 
            backgroundPanel.add(tb.changeYearPanel);
            //******************END ChangeYearPanel*****************************           
            
            backgroundPanel.add(tb.topWeekPanel);
            
            //************START calendarPanel***********************************
            backgroundPanel.add(tb.calendarPanel);
            //************END calendarPanel*************************************
            
                       
            //************************START BottomPanel*************************
            //settings bottom panel
            backgroundPanel.add(tb.bottomPanel);
            //******************END BottomPanel*********************************
          
            setContentPane(backgroundPanel);
            pack();            
    	    setVisible(true);        
        }
        
        public static void changeCentralPanel(JPanel panel, panelType type) {
            
            if (type == panelType.DAYPANEL) {
                tb.nextPrevPanel.setVisible(true);
                if (tb.changeYearPanel != null)
                    backgroundPanel.remove(tb.changeYearPanel);
                if (tb.calendarPanel != null)
                    backgroundPanel.remove(tb.calendarPanel);
                if (tb.WeekPANEL != null)
                    backgroundPanel.remove(tb.WeekPANEL);
                if (tb.settingsPanel != null)
                    backgroundPanel.remove(tb.settingsPanel);
                backgroundPanel.add(tb.topWeekPanel);
                backgroundPanel.add(panel);
                backgroundPanel.add(tb.topWeekDaysPanel);
                backgroundPanel.updateUI();
            }
            if (type == panelType.MONTHPANEL) {
                tb.nextPrevPanel.setVisible(true);
                if (tb.settingsPanel != null)
                    backgroundPanel.remove(tb.settingsPanel);
                if (tb.dayPanel != null)
                    backgroundPanel.remove(tb.dayPanel.pane);
                if (tb.calendarPanel != null)
                    backgroundPanel.remove(tb.topWeekDaysPanel);                
                if (tb.calendarPanel != null)
                    backgroundPanel.remove(tb.yearPanel);
                if (tb.WeekPANEL != null)
                    backgroundPanel.remove(tb.WeekPANEL);
                backgroundPanel.add(tb.changeYearPanel);
                backgroundPanel.add(tb.topWeekPanel);
                backgroundPanel.add(tb.calendarPanel);
                backgroundPanel.updateUI();
            }
            if (type == panelType.WEEKPANEL) {
                tb.nextPrevPanel.setVisible(true);
                if (tb.calendarPanel != null)
                    backgroundPanel.remove(tb.calendarPanel);
                if (tb.changeYearPanel != null)
                    backgroundPanel.remove(tb.changeYearPanel);
                if (tb.topWeekPanel != null)
                    backgroundPanel.remove(tb.topWeekPanel);
                if (tb.dayPanel != null)
                    backgroundPanel.remove(tb.dayPanel.pane);
                if (tb.yearPanel != null) 
                    backgroundPanel.remove(tb.yearPanel);
                backgroundPanel.add(tb.WeekPANEL);
                backgroundPanel.updateUI();
            }
            
            if (type == panelType.YEARPANEL) {
                tb.nextPrevPanel.setVisible(true);
                backgroundPanel.remove(tb.settingsPanel);
                if (tb.calendarPanel != null)
                    backgroundPanel.remove(tb.calendarPanel);
                if (tb.changeYearPanel != null)
                    backgroundPanel.remove(tb.changeYearPanel);
                if (tb.dayPanel != null)
                    backgroundPanel.remove(tb.dayPanel.pane);
                if (tb.topWeekDaysPanel != null)
                    backgroundPanel.remove(tb.topWeekDaysPanel);
                if (tb.topWeekPanel != null)
                    backgroundPanel.remove(tb.topWeekPanel);
                if (tb.WeekPANEL != null)
                    backgroundPanel.remove(tb.WeekPANEL);
                backgroundPanel.add(tb.yearPanel);
                backgroundPanel.updateUI();
            }
            
             if (type == panelType.SETPANEL) {
                tb.nextPrevPanel.setVisible(false);
                backgroundPanel.remove(tb.calendarPanel);
                backgroundPanel.remove(tb.changeYearPanel);
                if (tb.dayPanel != null)
                    backgroundPanel.remove(tb.dayPanel.pane);
                if (tb.topWeekDaysPanel != null)
                    backgroundPanel.remove(tb.topWeekDaysPanel);
                backgroundPanel.remove(tb.topWeekPanel);
                if (tb.WeekPANEL != null)
                    backgroundPanel.remove(tb.WeekPANEL);
                if (tb.yearPanel != null)
                    backgroundPanel.remove(tb.yearPanel);
                backgroundPanel.add(tb.settingsPanel);
                backgroundPanel.updateUI();
            }
        }
    }
     //set icon on the frame and application's icon
    private static void setApplicationIcon(JFrame frame) {
        Image im = Toolkit.getDefaultToolkit().getImage("images\\logo50.png");
        frame.setIconImage(im);
        frame.show();
    }   
    
    public static void main(String[] args) {
        PaintMainFrame frame = new PaintMainFrame();
        JFrame.setDefaultLookAndFeelDecorated(true);
        setApplicationIcon(frame);
        frame.setSize(size.frameHeight(size.getRezolution()),size.frameHeight(size.getRezolution()));
        
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