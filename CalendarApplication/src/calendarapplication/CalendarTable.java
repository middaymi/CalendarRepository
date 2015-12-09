package calendarapplication;

import static calendarapplication.CalendarApplication.frameHeight;
import static calendarapplication.CalendarApplication.getRezolution;
import static calendarapplication.CalendarApplication.setSizeButtonsNextPrevPanel;
import static calendarapplication.CalendarApplication.setSizeLocationChangeYearPanel;
import static calendarapplication.CalendarApplication.setSizeLocationNextPrevPanel;
import static calendarapplication.CalendarApplication.PaintMainFrame;
import static calendarapplication.CalendarApplication.frameHeight;
import static calendarapplication.CalendarApplication.getRezolution;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;


enum panelType {DAYPANEL, MONTHPANEL}
enum direction {PREV, NEXT}

public class CalendarTable extends JPanel {

    int realYear, realMonth, realDay, currentYear, currentMonth;
    int currentDay;
    JLabel lblMonth, lblYear;
    JComboBox cmbYear;    
    JPanel changeYearPanel;
    JPanel nextPrevPanel;
    JPanel weekPanel;
    JPanel calendarPanel;
    JButton btnPrev, btnNext;
    JButton ButtonLeft;
    JButton betweenLeftAndRight;
    JButton ButtonRight;
    JButton btn;
    Image imgBtnPrev;
    Image imgBtnNext;
    Image imgDay;
    Image imgCurrentDay;
    Image imgNotVisible;    
    JButton[][] buttons;
    int rows = 6, collumns = 7;  
    JPanel pane;
    
    
    
    static panelType panelflag; 
    
    ImageIcon iconCurrentDay = new ImageIcon("images\\whitePr.png");
    ImageIcon iconNotVisible = new ImageIcon("images\\grey.png");
    ImageIcon iconDay = new ImageIcon("images\\white.png");
    ImageIcon iconBtnPrev = new ImageIcon("images\\Left.png");
    ImageIcon iconBtnNext = new ImageIcon("images\\Right.png");   
    
    String[] headers = {"Mon", "Tue", "Wed ", "Thu   ", "Fri  ", "Sat", "Sun"}; 
    String[] months =  {"January", "February", "March", "April", "May", "June", 
                            "July", "August", "September", "October", "November", "December"};
    
    int currentPosition = 0;
    
    
    CalendarTable () {
        
        makeWeekPanel();
        makeCalendarPanel(); 
        makeChangeYearPanel();
        makeNextPrevPanel();
        panelflag = panelType.MONTHPANEL;
        
        imgNotVisible = iconNotVisible.getImage();                               
        imgBtnPrev = iconBtnPrev.getImage();
        imgBtnNext = iconBtnNext.getImage();
        imgDay = iconDay.getImage();
        imgCurrentDay = iconCurrentDay.getImage();
        
        iconBtnPrev = new ImageIcon(imgBtnPrev);
        iconBtnNext = new ImageIcon(imgBtnNext);
        iconDay = new ImageIcon(imgDay);
        iconCurrentDay = new ImageIcon(imgCurrentDay);
        iconNotVisible = new ImageIcon(imgNotVisible);
        
        //Prepare frame
        setOpaque(false);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(CountBorder(), CountBorder(), 
                                                  CountBorder(), CountBorder()));
        //Get real month/year
        GregorianCalendar cal = new GregorianCalendar(); //Create calendar
        realDay = cal.get(GregorianCalendar.DAY_OF_MONTH); //Get day
        realMonth = cal.get(GregorianCalendar.MONTH); //Get month
        realYear = cal.get(GregorianCalendar.YEAR); //Get year
        currentMonth = realMonth; //Match month and year       
        currentYear = realYear;      
       
        //Populate table
        for (int i=realYear-100; i<=realYear+100; i++){
            cmbYear.addItem(String.valueOf(i));
        }          
       
        refreshCalendar (realMonth, realYear); //Refresh calendar

        //Register action listeners
        cmbYear.addActionListener(new cmbYear_Action());
    }        
 
    private void refreshDay (int day, int month, int year, direction d) {
        int nod;
        
        if (d == direction.NEXT) {
            Calendar calTmp = Calendar.getInstance();
            calTmp.set(Calendar.DATE, 1);
            calTmp.set(Calendar.MONTH, month);
            calTmp.set(Calendar.YEAR, year);
            calTmp.set(Calendar.DAY_OF_MONTH, 1);
            nod = calTmp.getActualMaximum(Calendar.DAY_OF_MONTH);
            if (day == nod) {
                day = 1;
                if(month == 12) {
                    month = 1;
                    year++;
                } else {
                    month++;
                }
            } else {
                day++;
            }
        }
        if (d == direction.PREV) {
            if (day == 1) {
                if (month == 1) {
                    month = 12;
                    year--;
                } else {
                    month--;
                }
                Calendar calTmp = Calendar.getInstance();
                calTmp.set(Calendar.DATE, 1);
                calTmp.set(Calendar.MONTH, month);
                calTmp.set(Calendar.YEAR, year);
                calTmp.set(Calendar.DAY_OF_MONTH, 1);
                nod = calTmp.getActualMaximum(Calendar.DAY_OF_MONTH);
                day = nod; 
            } else {
                day--;
            }
        }
        lblMonth.setText("" + day + "." + month + "." + year);
    }
    
    
    private void refreshCalendar(int month, int year){
        int nod, som; //Number Of Days, Start Of Month
        
        lblMonth.setText(months[month]); //Refresh the month label (at the top)
        lblMonth.setHorizontalAlignment(JLabel.CENTER);
        Font fontMonth = (new Font("Arial", Font.PLAIN, frameHeight(getRezolution())/30));
        lblMonth.setFont(fontMonth);
        
                
        cmbYear.setSelectedItem(String.valueOf(year)); //Select the correct year in the combo box   
        
        //Clear table
        calendarPanel.setVisible(false);
        for (int m = 0; m < rows; m++){
            for (int n = 0; n < collumns; n++){
                buttons[m][n].setText("");
                buttons[m][n].setBackground(Color.LIGHT_GRAY);
                buttons[m][n].removeActionListener(new selectedDay_Action());
            }  
        }

        //Get first day of month and number of days        
        Calendar calTmp = Calendar.getInstance();
        calTmp.set(Calendar.DATE, 1);
        calTmp.set(Calendar.MONTH, month);
        calTmp.set(Calendar.YEAR, year);
        calTmp.set(Calendar.DAY_OF_MONTH, 1);
        Date firstDayOfMonth = calTmp.getTime();
        som = firstDayOfMonth.getDay();
        nod = calTmp.getActualMaximum(Calendar.DAY_OF_MONTH);
        
        if (som == 0)
            som = 6;
        else
            som = som - 1;   
        
        GridBagConstraints cp = new GridBagConstraints();
        //Draw calendar
        for(int i = 0; i < rows; i++) {
            for (int j = 0; j < collumns; j++) {
                cp.gridx = j;
                cp.gridy = i;
                cp.gridwidth = 1;
                cp.gridheight = 1;
                cp.weightx = cp.weighty = 0.5;
                cp.insets = new Insets(5, 5, 5, 5);                
                cp.fill = GridBagConstraints.BOTH; 
                
                currentPosition = (i+1)*7-som-(7-(j+1));
                //set current day
                if (currentPosition == realDay && currentMonth == realMonth && currentYear == realYear &&
                    currentPosition<= nod){
                    buttons[i][j].setBackground(Color.red);
                    buttons[i][j].setText("" + currentPosition);
                    buttons[i][j].setFont(new Font("Arial", Font.PLAIN, frameHeight(getRezolution())/60));
                    buttons[i][j].setHorizontalTextPosition(AbstractButton.CENTER); 
                    buttons[i][j].addActionListener(new selectedDay_Action());
                }
                
                //if not current day
                else {
                    buttons[i][j].setBackground(Color.lightGray);
                    //if not used
                    if ((j < som && i == 0) || (currentPosition > nod)) {
                        buttons[i][j].setText(" ");
                        buttons[i][j].setHorizontalTextPosition(AbstractButton.CENTER);
                    } else {
                        //if used
                        if (currentPosition <= nod) {
                            buttons[i][j].setText("" + currentPosition);
                            buttons[i][j].setFont(new Font("Arial", Font.PLAIN, frameHeight(getRezolution())/60));
                            buttons[i][j].setHorizontalTextPosition(AbstractButton.CENTER);
                            buttons[i][j].addActionListener(new selectedDay_Action());
                        }
                    }
                }
                calendarPanel.add(buttons[i][j], cp);
            }
        }
        calendarPanel.setVisible(true);
    }    
    
    JPanel getYearPanel() {
        return changeYearPanel;
    }
    
    JPanel nextPrevPanel() {
        return nextPrevPanel;
    }
      
    JPanel calendarPanel() {
        return calendarPanel;
    }
    
    JPanel weekPanel() {
        return weekPanel;
    }
    
    public static int CountBorder() {
        int count = 0;
        count = (int)(frameHeight(getRezolution())*0.025);
        return count;      
    } 
    
    private JPanel makeWeekPanel(){
        weekPanel = new JPanel();
        weekPanel.setOpaque(false);
        weekPanel.setBackground(Color.red);
        weekPanel.setSize((int)(frameHeight(getRezolution())*53/60),
                          (int)(frameHeight(getRezolution())/40));
        weekPanel.setLocation((int)((frameHeight(getRezolution()) 
                                    - weekPanel.getSize().width)/2), 
                              (int) (frameHeight(getRezolution())*67/240));
        weekPanel.setLayout(new GridBagLayout());
        GridBagConstraints wp = new GridBagConstraints();
        
        for (int j = 0; j < 7; j++) {
            wp.gridx = j;
            wp.gridy = 0;
            wp.gridwidth = 1;
            wp.gridheight = 1;
            wp.weightx = wp.weighty = 1.0;
            wp.insets = new Insets(0, 5, 0, 5);
            wp.fill = GridBagConstraints.BOTH;
            btn = new JButton();
            btn = new JButton(); 
            btn.setBorderPainted(false);
            btn.setFocusPainted(false);
            btn.setContentAreaFilled(false);
            btn.setText(headers[j]); 
            btn.setFont(new Font("Arial", Font.PLAIN, (int) (frameHeight(getRezolution())*0.015)));
            btn.setHorizontalTextPosition(AbstractButton.CENTER);
            weekPanel.add(btn, wp);
        }        
        return weekPanel;    
    }
    
    private JPanel sizeCentralPanel(JPanel pane) {
        Dimension size = new Dimension();
        size.width  = (int)(53*frameHeight(getRezolution())/60);
        size.height = (int)(13*frameHeight(getRezolution())/24);         
        
        Point location = new Point();
        location.x = (int)((frameHeight(getRezolution()) - size.width)/2);
        location.y = (int)(frameHeight(getRezolution())*37/120); 
        
        pane.setSize(size);
        pane.setLocation(location);
        
        return pane;
    }
    
    private JPanel makeCalendarPanel(){
        calendarPanel = new JPanel();
//        calendarPanel.setSize(53*frameHeight(getRezolution())/60,
//                              13*frameHeight(getRezolution())/24);
//        calendarPanel.setLocation((int)((frameHeight(getRezolution()) 
//                                         - calendarPanel.getSize().width)/2), 
//                                  (int) (frameHeight(getRezolution())*37/120));
        sizeCentralPanel(calendarPanel);
        calendarPanel.setBackground(Color.red);
        calendarPanel.setOpaque(false);
        calendarPanel.setLayout(new GridBagLayout());
        buttons = new JButton[rows][collumns];
        GridBagConstraints cp = new GridBagConstraints();
        for(int i = 0; i < rows; i++) {
            for (int j = 0; j < collumns; j++) {
                buttons[i][j] = new JButton();
                buttons[i][j].setBorderPainted(false);
                buttons[i][j].setFocusPainted(false);
//                buttons[i][j].setContentAreaFilled(false);
            }
        }
        
       return calendarPanel;
    }
    
    private JPanel makeNextPrevPanel(){
        nextPrevPanel = new JPanel();
        nextPrevPanel.setOpaque(false);
        nextPrevPanel.setBackground(Color.ORANGE);
        setSizeLocationNextPrevPanel(nextPrevPanel);
        nextPrevPanel.setLayout(new GridBagLayout());
        GridBagConstraints btn = new GridBagConstraints();         
        btnPrev = new JButton();
        btnPrev.setIcon(iconBtnPrev);
        setSizeButtonsNextPrevPanel(btnPrev);
        btnPrev.setPressedIcon(new ImageIcon("images\\LeftPr.png"));
        btnPrev.setRolloverIcon(new ImageIcon("images\\LeftPr.png"));
        btnPrev.setBorderPainted(false);
        btnPrev.setFocusPainted(false);
        btnPrev.setContentAreaFilled(false);
        btn.weightx = 0.25;
        btn.weighty = 0.25;
        btn.fill = GridBagConstraints.BOTH;
        btn.gridx = 0;
        btn.gridy = 0;
        nextPrevPanel.add(btnPrev, btn);
        btnPrev.addActionListener(new btnPrev_Action());
        
        lblMonth = new JLabel("MONTH");
        lblMonth.setHorizontalAlignment(JLabel.CENTER);
        lblMonth.setSize(100,100);
        btn.fill = GridBagConstraints.BOTH;
        btn.weightx = 0.25;
        btn.weighty = 0.25;
        btn.gridx = 1;
        btn.gridy = 0;
        nextPrevPanel.add(lblMonth, btn);     
        
        btnNext = new JButton();
        btnNext.setIcon(iconBtnNext);
        setSizeButtonsNextPrevPanel(btnNext);
        btnNext.setPressedIcon(new ImageIcon("images\\RightPr.png"));
        btnNext.setRolloverIcon(new ImageIcon("images\\RightPr.png"));
        btnNext.setBorderPainted(false);
        btnNext.setFocusPainted(false);
        btnNext.setContentAreaFilled(false);
        btn.fill = GridBagConstraints.BOTH;
        btn.weightx = 0.25;
        btn.weighty = 0.25;
        btn.gridx = 2;
        btn.gridy = 0;
        nextPrevPanel.add(btnNext, btn);
        btnNext.addActionListener(new btnNext_Action());                
        return nextPrevPanel;
    }
    
    private JPanel makeChangeYearPanel() {
        changeYearPanel = new JPanel();
        changeYearPanel.setOpaque(false);
        changeYearPanel.setLayout(new GridBagLayout());
        GridBagConstraints cyp = new GridBagConstraints();
        setSizeLocationChangeYearPanel(changeYearPanel);
        lblYear = new JLabel ("Change year:");
        lblYear.setHorizontalAlignment(JLabel.LEFT);
        Font fontYear = (new Font("Arial", Font.PLAIN, frameHeight(getRezolution())/60));
        lblYear.setFont(fontYear);
        cmbYear = new JComboBox();
        cmbYear.setFont(fontYear);
        cyp.weightx = 0.5;
        cyp.weighty = 0.5;
        cyp.fill = GridBagConstraints.BOTH;
        cyp.gridx = 0;
        cyp.gridy = 0;
        changeYearPanel.add(lblYear, cyp);
        cyp.weightx = 0.5;
        cyp.weighty = 0.5;
        cyp.fill = GridBagConstraints.BOTH;
        cyp.gridx = 1;
        cyp.gridy = 0;
        changeYearPanel.add(cmbYear, cyp);
        return changeYearPanel;
    }  

    
    
    class btnPrev_Action implements ActionListener{
        public void actionPerformed (ActionEvent e){
            if (panelflag == panelType.DAYPANEL) {
                String dateText = lblMonth.getText();
                String[] dateParts = dateText.split("\\.");
                refreshDay(Integer.parseInt(dateParts[0]),
                           Integer.parseInt(dateParts[1]),
                           Integer.parseInt(dateParts[2]),
                           direction.PREV);               
            } else {
            if (currentMonth == 0){ //Back one year
                currentMonth = 11;
                currentYear -= 1;
            } else{ //Back one month
                currentMonth -= 1;
            }
            calendarPanel.setVisible(false);
            refreshCalendar(currentMonth, currentYear);
            }
        }
    }
    class btnNext_Action implements ActionListener{
        public void actionPerformed (ActionEvent e){
            if (panelflag == panelType.DAYPANEL) {
                String dateText = lblMonth.getText();
                String[] dateParts = dateText.split("\\.");
                refreshDay(Integer.parseInt(dateParts[0]),
                           Integer.parseInt(dateParts[1]),
                           Integer.parseInt(dateParts[2]),
                           direction.NEXT);               
            }
            
            else {
                if (currentMonth == 11){ //Foward one year
                    currentMonth = 0;
                    currentYear += 1;
                }
                else{ //Foward one month
                    currentMonth += 1;
                }
                refreshCalendar(currentMonth, currentYear);
            }
        }
    }
    
     
    
    class cmbYear_Action implements ActionListener{
        public void actionPerformed (ActionEvent e){
            if (cmbYear.getSelectedItem() != null){
                String b = cmbYear.getSelectedItem().toString();
                currentYear = Integer.parseInt(b);
                //calendarPanel.setVisible(false);
                refreshCalendar(currentMonth, currentYear);
            }
        }
    }
    class selectedDay_Action implements ActionListener{        
        public void actionPerformed(ActionEvent e) {
            JPanel panel = dayPanel();
            PaintMainFrame.changeCentralPanel(panel);
            JButton btn = (JButton)e.getSource();
            lblMonth.setText(btn.getText() +"." + (currentMonth + 1) + "." + currentYear);
            panelflag = panelType.DAYPANEL;
            currentDay = Integer.parseInt(btn.getText());
        }
    }
    
    class selectedMonth_Action implements ActionListener{        
        public void actionPerformed(ActionEvent e) {         
            PaintMainFrame.changeCentralPanel(calendarPanel);
            JButton btn = (JButton)e.getSource();
            lblMonth.setText("" + currentMonth);
            panelflag = panelType.MONTHPANEL;
            currentDay = Integer.parseInt(btn.getText());
        }
    }
    
    private JPanel dayPanel() {
        calendarPanel.setVisible(false);
        changeYearPanel.setVisible(false);
        pane = new JPanel();
        JPanel paneRight = new JPanel();
        JPanel paneLeft = new JPanel();
        sizeCentralPanel(pane);
        pane.setBackground(Color.red);
        pane.setVisible(true); 
        return pane;
    }
}
