package calendarapplication;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;

public class CalendarApplication {
     
    //отрисовка главного окна с фоном
    public static class PaintMainFrame extends JFrame  {
    	PaintMainFrame() {
            super("Calendar");
            
            JLabel backgroundLabel = null;
            
            try {
                
                backgroundLabel = new JLabel(new ImageIcon(ImageIO.read(new File("images\\MainPanel.png"))));
                
    	    } catch (IOException e) {
    	    } 
            setDefaultCloseOperation(EXIT_ON_CLOSE);           
            
            backgroundLabel.setLayout(null);
             
            //button "week"
            JButton buttonWeek = new JButton();
            buttonWeek.setIcon(new ImageIcon("images\\Week.png"));
            buttonWeek.setSize(200, 100);
            buttonWeek.setLocation(230,1110);
            //change when press
            buttonWeek.setPressedIcon(new ImageIcon("images\\WeekPr.png"));
            buttonWeek.setRolloverIcon(new ImageIcon("images\\WeekPr.png"));
            //unvisible button, visible icon
            buttonWeek.setBorderPainted(false);
            buttonWeek.setFocusPainted(false);
            buttonWeek.setContentAreaFilled(false);
            backgroundLabel.add(buttonWeek);
            
            //button "month"
            JButton buttonMonth = new JButton();
            buttonMonth.setIcon(new ImageIcon("images\\Month.png"));
            buttonMonth.setSize(200, 100);
            buttonMonth.setLocation(430,1110);
            buttonMonth.setPressedIcon(new ImageIcon("images\\MonthAndYearPr.png"));
            buttonMonth.setRolloverIcon(new ImageIcon("images\\MonthAndYearPr.png"));
            buttonMonth.setBorderPainted(false);
            buttonMonth.setFocusPainted(false);
            buttonMonth.setContentAreaFilled(false);
            backgroundLabel.add(buttonMonth);
            
            //button "year"
            JButton buttonYear = new JButton();
            buttonYear.setIcon(new ImageIcon("images\\Year.png"));
            buttonYear.setSize(200, 100);
            buttonYear.setLocation(630,1110);
            buttonYear.setPressedIcon(new ImageIcon("images\\MonthAndYearPr.png"));
            buttonYear.setRolloverIcon(new ImageIcon("images\\MonthAndYearPr.png"));
            buttonYear.setBorderPainted(false);
            buttonYear.setFocusPainted(false);
            buttonYear.setContentAreaFilled(false);
            backgroundLabel.add(buttonYear);
            
            //button "set"
            JButton buttonSettings = new JButton();
            buttonSettings.setIcon(new ImageIcon("images\\Set.png"));
            buttonSettings.setSize(200, 100);
            buttonSettings.setLocation(830,1110);
            buttonSettings.setPressedIcon(new ImageIcon("images\\SetPr.png"));
            buttonSettings.setRolloverIcon(new ImageIcon("images\\SetPr.png"));
            buttonSettings.setBorderPainted(false);
            buttonSettings.setFocusPainted(false);
            buttonSettings.setContentAreaFilled(false);
            backgroundLabel.add(buttonSettings);             
            
            //arrow-button left
            JButton ButtonLeft = new JButton();
            ButtonLeft.setIcon(new ImageIcon("images\\Left.png"));
            ButtonLeft.setSize(300, 115);
            ButtonLeft.setLocation(30,65);
            ButtonLeft.setPressedIcon(new ImageIcon("images\\LeftPr.png"));
            ButtonLeft.setRolloverIcon(new ImageIcon("images\\LeftPr.png"));
            ButtonLeft.setBorderPainted(false);
            ButtonLeft.setFocusPainted(false);
            ButtonLeft.setContentAreaFilled(false);
            backgroundLabel.add(ButtonLeft);  
            
            //arrow-button right
            JButton ButtonRight = new JButton();
            ButtonRight.setIcon(new ImageIcon("images\\Right.png"));
            ButtonRight.setSize(300, 115);
            ButtonRight.setLocation(930,65);
            ButtonRight.setPressedIcon(new ImageIcon("images\\RightPr.png"));
            ButtonRight.setRolloverIcon(new ImageIcon("images\\RightPr.png"));
            ButtonRight.setBorderPainted(false);
            ButtonRight.setFocusPainted(false);
            ButtonRight.setContentAreaFilled(false);
            backgroundLabel.add(ButtonRight);   
            
            //test buttons for measuring frame
            //JButton test1 = new JButton();
            //test1.setSize(300, 115);
            //test1.setLocation(30,65);          
            //backgroundLabel.add(test1);             
            
            //JButton test2 = new JButton();
            //test2.setSize(300, 115);
            //test2.setLocation(930,65);          
            //backgroundLabel.add(test2); 
            
              //close and turn buttons under each other on the right top
//            JButton Turn = new JButton();
//            Turn.setIcon(new ImageIcon("images\\Turn.png"));
//            Turn.setSize(40, 40);
//            Turn.setLocation(1160,70);
//            Turn.setBorderPainted(false);
//            Turn.setFocusPainted(false);
//            Turn.setContentAreaFilled(false);
//            backgroundLabel.add(Turn); 
//            
//            JButton Close = new JButton();
//            Close.setIcon(new ImageIcon("images\\Close.png"));
//            Close.setSize(40, 40);
//            Close.setLocation(1160,140);
//            Close.setBorderPainted(false);
//            Close.setFocusPainted(false);
//            Close.setContentAreaFilled(false);
//            backgroundLabel.add(Close);   
            
            //close and turn button near each other on the top
            JButton Turn = new JButton();
            Turn.setIcon(new ImageIcon("images\\2.png"));
            Turn.setSize(40, 40);
            Turn.setLocation(1100,25);
            Turn.setBorderPainted(false);
            Turn.setFocusPainted(false);
            Turn.setContentAreaFilled(false);
            backgroundLabel.add(Turn); 
            
            JButton Close = new JButton();
            Close.setIcon(new ImageIcon("images\\1.png"));
            Close.setSize(40, 40);
            Close.setLocation(1160,25);
            Close.setBorderPainted(false);
            Close.setFocusPainted(false);
            Close.setContentAreaFilled(false);
            backgroundLabel.add(Close);
            
            setContentPane(backgroundLabel);           
            pack();            
    	    setVisible(true);          
        }
    }
    
    public static Dimension getRezolution(){        
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        System.out.println(size);
        return (size);        
    }
    public static int FrameWidth(Dimension exSize){
        System.out.println(exSize.width/2 - 300);
        return(exSize.width/2 - 300);
    }
    public static int FrameHeight(Dimension exSize){
        System.out.println(exSize.height/2 + exSize.height/4);
        return(exSize.height/2 + exSize.height/4);
    }


    class CalendarEvent {
        private long ID;
        private String description;
        
        CalendarEvent(String eventDescription) {
            description = eventDescription;
            ID = ID(eventDescription);
        }
        
        long getID() { return ID; }
        
        void setDescription(String newDescription) {
            description = newDescription;
            ID = ID(newDescription);
        }
        
        String getDescription() {
            return description;
        }
        
        private long ID(String text) {
            return text.length() + text.charAt(0) + text.charAt(text.length() - 1);
        }
    }

    interface Dumper {
        int saveEvent();
        int findEventByDate();
    }
            
    class XMLDumper implements Dumper {

        @Override
        public int saveEvent() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public int findEventByDate() {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }

    class CalendarEventController {
        java.util.List<CalendarEvent> EventList;
        Dumper eventDumper;
        
        CalendarEventController() {
            EventList = new ArrayList<>();
        }
        
        void addEvent (CalendarEvent newEvent) {
            EventList.add(newEvent);
        }
        
        void deleteEvent(CalendarEvent delEvent) {
            for (CalendarEvent i: EventList) {
                if (delEvent.getID() == i.getID()) {
                    EventList.remove(i);
                    break;
                }
            }
        }

        void printEvents() {
            for (CalendarEvent i: EventList) {
                System.out.println( i.getDescription() );
            }
        }
    }
   
    public static void main(String[] args) {
        PaintMainFrame frame = new PaintMainFrame();
        frame.setSize(FrameWidth(getRezolution()),FrameHeight(getRezolution()));

        CalendarEventController ctrl = new CalendarEventController();
        CalendarEvent testEvent = new CalendarEvent("Trololo");
        CalendarEvent testEvent2 = new CalendarEvent("Trololo2");
        
        ctrl.addEvent(testEvent);
        ctrl.addEvent(testEvent2);
        ctrl.printEvents();
        ctrl.deleteEvent(testEvent);
        ctrl.printEvents();
    }    
}
