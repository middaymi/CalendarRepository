package calendarapplication;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.Toolkit;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Sizes {
    //get rezolution of the screen
    public  Dimension getRezolution(){        
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        return (size);        
    }
    //get needed size of frame
    public  int frameHeight(Dimension exSize){
        return(exSize.height*2/3);
    } 
    
    //for bottom panel
    public JButton sizeLocationBtnBottomPanel(JButton btn, int i) {
        Dimension size = new Dimension();
        size.height = (int)(frameHeight(getRezolution())/12); 
        size.width  = (int)(frameHeight(getRezolution())/6);
        
        Point location = new Point(); 
        location.x = i * size.width;
        location.y = 0;
        
        btn.setSize(size);
        btn.setLocation(location);
        return btn;
    }
    
    public  JPanel setSizeLocationBottomPanel(JPanel pane) {  
        Dimension size = new Dimension();
        size.height = (int)(frameHeight(getRezolution())/12); 
        size.width  = (int)(frameHeight(getRezolution()) - frameHeight(getRezolution())/2);
        
        Point location = new Point();
        location.x = (int)(0.25*frameHeight(getRezolution()));
        location.y = (int) (frameHeight(getRezolution()) 
                            - size.height
                            - CountBorder()
                            - CountBorder()/3);      
        pane.setSize(size);
        pane.setLocation(location);
        return (pane); 
    }    
    public  void setSizeLocationChangeYearPanel(JPanel pane) {
        Dimension size = new Dimension();
        size.width  = (int)(5*frameHeight(getRezolution())/24);
        size.height = (int)(frameHeight(getRezolution())/30); 
               
        Point location = new Point();
        location.x = (int)(0.025*frameHeight(getRezolution()));
        location.y = (int)(11*frameHeight(getRezolution())/48);        
        
        pane.setSize(size);
        pane.setLocation(location); 
    } 
    public  JPanel setSizeLocationNextPrevPanel(JPanel pane) {
        Dimension size = new Dimension();
        size.width  = (int)(frameHeight(getRezolution()) - 2*CountBorder());
        size.height = (int)(0.075*frameHeight(getRezolution())); 
               
        Point location = new Point();
        location.x = (int)(0.026*frameHeight(getRezolution()));
        location.y = (int)(7*frameHeight(getRezolution())/48);        
        
        pane.setSize(size);
        pane.setLocation(location);
        return (pane); 
    }
    public  JButton setSizeButtonsNextPrevPanel(JButton button) {
        Dimension size = new Dimension();
        size.width  = (int)(0.925*frameHeight(getRezolution())/3);
        size.height = (int)(0.075*frameHeight(getRezolution()));       
        
        button.setSize(size);
        return (button); 
    } 
    public  void setSizeLocationCloseTurnPanel(JPanel pane) {
        Dimension size = new Dimension();
        size.width  = (int)(11*frameHeight(getRezolution())/120);
        size.height = (int)(frameHeight(getRezolution())/24); 
               
        Point location = new Point();
        location.x = (int)(frameHeight(getRezolution()) 
                           - CountBorder()
                           - size.width);
        location.y = (int)(frameHeight(getRezolution())/40);        
        
        pane.setSize(size);
        pane.setLocation(location);   
    }
    public void sizeLocationCentralPanel(JPanel pane) {
        Dimension size = new Dimension();
        size.width  = (int)(53*frameHeight(getRezolution())/60);
        size.height = (int)(13*frameHeight(getRezolution())/24);         
        
        Point location = new Point();
        location.x = (int)((frameHeight(getRezolution()) - size.width)/2);
        location.y = (int)(frameHeight(getRezolution())*37/120); 
        
        pane.setSize(size);
        pane.setLocation(location);
    }
    public  int CountBorder() {
        int count = 0;
        count = (int)(frameHeight(getRezolution())*0.025);
        return count;      
    }
    
//*****************************FOR DAY PANEL + sizeLocationCentralPanel()*******
    public void sizeLocationPaneInScroll(JPanel pane) {
        Dimension size = new Dimension();
        size.width  = (int)(43*frameHeight(getRezolution())/120);
        size.height = (int)(13*frameHeight(getRezolution())/24);         

        Point location = new Point();
        location.x = 0;
        location.y = 0;

        pane.setSize(size);
        pane.setLocation(location);  
    }
    public void sizeLocationScrollPaneTextArea(JScrollPane spane) {
        Dimension size = new Dimension();
        size.width  = (int)(0.375*frameHeight(getRezolution()));
        size.height = (int)(5*frameHeight(getRezolution())/24);         

        Point location = new Point();
        location.x = (int)(frameHeight(getRezolution())/16);
        location.y = (int)(frameHeight(getRezolution())/24);

        spane.setSize(size);
        spane.setLocation(location); 
    }
    public void sizeLocationPaneRight(JPanel pane) {
        Dimension size = new Dimension();
        size.width  = (int)(38*frameHeight(getRezolution())/75);
        size.height = (int)(13*frameHeight(getRezolution())/24);         

        Point location = new Point();
        location.x = (int)(0.375*frameHeight(getRezolution()));
        location.y = 0;

        pane.setSize(size);
        pane.setLocation(location);
    }
    public void sizeLocationScrlPane(JScrollPane spane){
        Dimension size = new Dimension();
        size.width  = (int)(0.375*frameHeight(getRezolution()));
        size.height = (int)(13*frameHeight(getRezolution())/24);         

        Point location = new Point();
        location.x = 0;
        location.y = 0;

        spane.setSize(size);
        spane.setLocation(location);
    }
    public void sizeButtonsOnPaneRight(JButton btn) {
        Dimension size = new Dimension();
        size.width  = (int)(frameHeight(getRezolution())/12);
        size.height = (int)(frameHeight(getRezolution())/24);
        btn.setSize(size);
    }
    public void sizeLocationButtonOnPaneRight(JButton btn, int number) {
        sizeButtonsOnPaneRight(btn);
        
        Point location = new Point();
        if (number == 1) location.x = (int)(frameHeight(getRezolution())/16);
        if (number == 2) location.x = (int)(5*frameHeight(getRezolution())/24);
        if (number == 3) location.x = (int)(17*frameHeight(getRezolution())/48);
        
        location.y = (int)(11*frameHeight(getRezolution())/24);
        
        btn.setLocation(location);
    }
    public void sizeLocationTextAreaInScroll(JTextArea textArea) {
        Dimension size = new Dimension();
        size.width  = (int)(0.375*frameHeight(getRezolution()));
        size.height = (int)(5*frameHeight(getRezolution())/24);         

        Point location = new Point();
        location.x = 0;
        location.y = 0;

        textArea.setSize(size);
        textArea.setLocation(location);
    }
    public void setFont50(JTextArea textArea) {
        textArea.setFont(new Font("Arial", Font.PLAIN, 
                             (int)(frameHeight(getRezolution())/24)));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        
    }
    public void setFont30(JButton btn) {
        btn.setFont(new Font("Arial", Font.PLAIN, 
                        (int)(0.025*frameHeight(getRezolution()))));
    }
     public void setFont40(JButton btn) {
        btn.setFont(new Font("Arial", Font.PLAIN, 
                        (int)(frameHeight(getRezolution())/30)));
    }
    public void setFont15(JButton btn) {
        btn.setFont(new Font("Arial", Font.PLAIN, 
                        (int)(0.0125*frameHeight(getRezolution()))));    
    }
    public void sizeButtonsInScrollPaneForEvents(JButton btn) {
        Dimension size = new Dimension();
        size.width  = (int)(43*frameHeight(getRezolution())/120);
        size.height = (int)(frameHeight(getRezolution())/12);
        btn.setMaximumSize(size);
        btn.setMinimumSize(size);
        btn.setPreferredSize(size);
    }
//*************************END DAY PANEL****************************************

//WEEKS DAY PANEL START*********************************************************
    public void sizeLocationWeekDayPanel(JPanel pane) {
        Dimension size = new Dimension();
        size.width  = (int)(frameHeight(getRezolution())*53/60);
        size.height = (int)(frameHeight(getRezolution())/24);        

        Point location = new Point();
        location.x = (int)((frameHeight(getRezolution()) - size.width)/2);
        location.y = (int)(0.265*frameHeight(getRezolution()));

        pane.setSize(size);
        pane.setLocation(location);                            
    }
    public void sizeButtonsTopWeekDayPanel(JButton btn) {
        Dimension size = new Dimension();
        size.width  = (int)(29*frameHeight(getRezolution())/240);
        size.height = (int)(frameHeight(getRezolution())/24);
        btn.setSize(size);
    }
    public void LocationButtonsTopWeekDayPanel(JButton btn, int number) {
        Point location = new Point();
        location.x = (int) ((5 + number*frameHeight(getRezolution())*0.125));
        location.y = 0;
        btn.setLocation(location);
    } 
//WEEKS DAY PANEL END***********************************************************
    
    
    
//TOP WEEK PANEL START**********************************************************
    public void sizeLocationTopWeekPanel(JPanel pane) {
        Dimension size = new Dimension();
        size.width  = (int)(53*frameHeight(getRezolution())/60);
        size.height = (int)(frameHeight(getRezolution())/40);        

        Point location = new Point();
        location.x = (int)((frameHeight(getRezolution()) - size.getWidth())/2);
        location.y = (int)((frameHeight(getRezolution())*67/240));

        pane.setSize(size);
        pane.setLocation(location);
    }    
    public void sizeLocationButtonsTopWeekPanel(JButton btn, int number) {
        Dimension size = new Dimension();
        size.width  = (int)(29*frameHeight(getRezolution())/240);
        size.height = (int)(frameHeight(getRezolution())/40);       

        Point location = new Point();
        location.x = (int) ((frameHeight(getRezolution())/240) + number*frameHeight(getRezolution())*0.125);
        location.y = 0;
        
        btn.setSize(size);
        btn.setLocation(location);
    }    
//TOP WEEK PANEL END************************************************************    

}
