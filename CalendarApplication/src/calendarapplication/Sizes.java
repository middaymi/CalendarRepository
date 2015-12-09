package calendarapplication;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import javax.swing.JButton;
import javax.swing.JPanel;

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
    public  JPanel setSizeLocationBottomPanel(JPanel pane) {  
        Dimension size = new Dimension();
        size.height = (int)(frameHeight(getRezolution())/12); 
        size.width  = (int)(2*frameHeight(getRezolution())/3);
        
        Point location = new Point();
        location.x = (int)(0.15*frameHeight(getRezolution()));
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
}
