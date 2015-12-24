package calendarapplication;

import static calendarapplication.CalendarApplication.PaintMainFrame;
import calendarapplication.Controller.Dumper;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
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
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;


enum panelType {DAYPANEL, MONTHPANEL, WEEKPANEL, YEARPANEL, SETPANEL}
enum direction {PREV, NEXT}

public class CalendarTable extends JPanel {

    int realYear, realMonth, realDay, currentYear, currentMonth;
    int currentDay;
    Dumper dumper;
    JLabel lblMonth, lblYear;
    JComboBox cmbYear;    
    JPanel changeYearPanel;
    JPanel nextPrevPanel;
    JPanel topWeekPanel;
    JPanel topWeekDaysPanel;
    JPanel calendarPanel;
    dayPanel dayPanel;
    JPanel WeekPANEL;
    JPanel yearPanel;
    JPanel bottomPanel;
    JButton btnPrev, btnNext;
    JButton ButtonLeft;
    JButton betweenLeftAndRight;
    JButton ButtonRight;
    JButton btn;
    JButton btnD;
    
    Image imgBtnPrev;
    Image imgBtnNext;
    Image imgDay;
    Image imgCurrentDay;
    Image imgNotVisible; 
    Image imgSelectedDay;
    Image imgbtnPrevPr;
    Image imgbtnNextPr;
    Image imgWeek;
    Image imgMonthYear;
    Image imgSettings;
    Image imgWeekPr;
    Image imgMonthYearPr;
    Image imgSettingsPr;
    
    JButton[][] buttons;
    int rows = 6, collumns = 7;  
    JPanel pane;
    boolean isInit; 
    String selectedNumber;
    JPanel settingsPanel;
    
    static panelType panelflag; 
    
    ImageIcon iconCurrentDay = new ImageIcon("images\\whitePr.png");
    ImageIcon iconNotVisible = new ImageIcon("images\\grey.png");
    ImageIcon iconDay = new ImageIcon("images\\white.png");
    ImageIcon iconBtnPrev = new ImageIcon("images\\Left.png");
    ImageIcon iconBtnNext = new ImageIcon("images\\Right.png"); 
    ImageIcon iconSelectedDay = new ImageIcon("images\\selDay.png");
    ImageIcon iconbtnPrevPr = new ImageIcon("images\\LeftPr.png");
    ImageIcon iconbtnNextPr = new ImageIcon("images\\RightPr.png");
    ImageIcon iconWeek = new ImageIcon("images\\WEEK_1.png");;
    ImageIcon iconMonthYear = new ImageIcon("images\\MONTH_YEAR_1.png");
    ImageIcon iconSettings = new ImageIcon("images\\SET.png");
    ImageIcon iconWeekPr = new ImageIcon("images\\WeekPr.png");
    ImageIcon iconMonthYearPr = new ImageIcon("images\\MonthAndYearPr.png");
    ImageIcon iconSettingsPr = new ImageIcon("images\\setPr.png");
    
    String[] headers = {"Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"}; 
    String[] months =  {"January", "February", "March", "April", "May", "June", 
                        "July", "August", "September", "October", "November", "December"};
    JButton[] dateInWeek;
    JButton[] yearButtons;
    
    int currentPosition = 0;
    Sizes size;
    
    CalendarTable () {
        
        size = new Sizes();
        makeWeekPanel();
        makeCalendarPanel(); 
        makeChangeYearPanel();
        makeNextPrevPanel();
        makeBottomPanel();
        makeWeekPANEL();
        weekDaysPanel();
        yearPanel = yearPanel();
        settingsPanel = settingsPanel();
        panelflag = panelType.MONTHPANEL;
        isInit = false;
        
        dumper = new Controller.XMLDumper("db\\events.xml");
        
        imgNotVisible = iconNotVisible.getImage();                               
        imgBtnPrev = iconBtnPrev.getImage();
        imgBtnNext = iconBtnNext.getImage();
        imgDay = iconDay.getImage();
        imgCurrentDay = iconCurrentDay.getImage();
        imgSelectedDay = iconSelectedDay.getImage();
        imgbtnPrevPr = iconbtnPrevPr.getImage();
        imgbtnNextPr = iconbtnNextPr.getImage();
        imgWeek = iconWeek.getImage();
        imgMonthYear = iconMonthYear.getImage();
        imgSettings = iconSettings.getImage();
        imgWeekPr = iconWeekPr.getImage();
        imgMonthYearPr = iconMonthYearPr.getImage();
        imgSettingsPr = iconSettingsPr.getImage();
        
        iconBtnPrev = new ImageIcon(imgBtnPrev);
        iconBtnNext = new ImageIcon(imgBtnNext);
        iconDay = new ImageIcon(imgDay);
        iconCurrentDay = new ImageIcon(imgCurrentDay);
        iconNotVisible = new ImageIcon(imgNotVisible);
        iconSelectedDay = new ImageIcon(imgSelectedDay);
        iconbtnPrevPr = new ImageIcon(imgbtnPrevPr);
        iconbtnNextPr = new ImageIcon(imgbtnNextPr);
        iconWeek = new ImageIcon(imgWeek);
        iconMonthYear = new ImageIcon(imgMonthYear);
        iconSettings = new ImageIcon(imgSettings);
        iconWeekPr = new ImageIcon(imgWeekPr);
        iconMonthYearPr = new ImageIcon(imgSettingsPr);        
        iconSettingsPr = new ImageIcon(imgSettingsPr);
        
        //Prepare frame
        setOpaque(false);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(size.CountBorder(), size.CountBorder(), 
                                                  size.CountBorder(), size.CountBorder()));
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
            calTmp.set(Calendar.MONTH, month - 1);
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
                calTmp.set(Calendar.MONTH, month - 1);
                calTmp.set(Calendar.YEAR, year);
                calTmp.set(Calendar.DAY_OF_MONTH, 1);
                nod = calTmp.getActualMaximum(Calendar.DAY_OF_MONTH);
                day = nod; 
            } else {
                day--;
            }
        }
        lblMonth.setText("" + day + "." + month + "." + year);
        dayPanel.updateContent("" + day + "." + month + "." + year);
        makeWeekDaysPanel("" + day + "." + month + "." + year, topWeekDaysPanel);
    }
    
    
    private void refreshCalendar(int month, int year) {

        int nod, som; //Number Of Days, Start Of Month
        Border thickBorder = new LineBorder(Color.GREEN, size.frameHeight(size.getRezolution())/240);
        
        
        lblMonth.setHorizontalAlignment(JLabel.CENTER);
        Font fontMonth = (new Font("Arial", Font.PLAIN, size.frameHeight(size.getRezolution())/30));
        lblMonth.setFont(fontMonth);        
                
        cmbYear.setSelectedItem(String.valueOf(year)); //Select the correct year in the combo box 
        lblMonth.setText(months[month]); //Refresh the month label (at the top)
        
        //Clear table
        for (int m = 0; m < rows; m++){
            for (int n = 0; n < collumns; n++){
                buttons[m][n].setText("");
                buttons[m][n].setBackground(Color.LIGHT_GRAY);
                buttons[m][n].setBorder(null);
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
                    buttons[i][j].setFont(new Font("Arial", Font.PLAIN, size.frameHeight(size.getRezolution())/60));
                    buttons[i][j].setHorizontalTextPosition(AbstractButton.CENTER); 
                    buttons[i][j].addActionListener(new selectedDay_Action());
                } else { //if not current day
                    if (!dumper.findEventsByDate(currentPosition + "." + (month + 1) + "." + year).isEmpty()) 
                        //buttons[i][j].setBackground(Color.GREEN);
                        buttons[i][j].setBorder(thickBorder);
                     
                    else
                        buttons[i][j].setBackground(Color.LIGHT_GRAY);
                    //if not used
                    if ((j < som && i == 0) || (currentPosition > nod)) {
                        buttons[i][j].setText(" ");
                        buttons[i][j].setHorizontalTextPosition(AbstractButton.CENTER);
                    } else {
                        //if used
                        if (currentPosition <= nod) {
                            buttons[i][j].setText("" + currentPosition);
                            buttons[i][j].setFont(new Font("Arial", Font.PLAIN, size.frameHeight(size.getRezolution())/60));
                            buttons[i][j].setHorizontalTextPosition(AbstractButton.CENTER);
                            buttons[i][j].addActionListener(new selectedDay_Action());
                        }
                    }
                }
                if (isInit == false)
                  calendarPanel.add(buttons[i][j], cp);
            }
        }
        isInit = true;
        calendarPanel.setVisible(true);
        
    }    

    private JPanel makeWeekPanel(){
        topWeekPanel = new JPanel();
        topWeekPanel.setOpaque(false);
        topWeekPanel.setBackground(Color.red);
        size.sizeLocationTopWeekPanel(topWeekPanel);        
        topWeekPanel.setLayout(null);
        for (int i = 0; i < 7; ++i) {
            btn = new JButton();            
            size.sizeLocationButtonsTopWeekPanel(btn, i);
            btn.setBorderPainted(false);
            btn.setFocusPainted(false);
            btn.setContentAreaFilled(false);
            btn.setText(headers[i]); 
            btn.setFont(new Font("Arial", Font.PLAIN, (int) (size.frameHeight(size.getRezolution())*0.015)));
            btn.setHorizontalTextPosition(AbstractButton.CENTER);
            topWeekPanel.add(btn);
        }
        return topWeekPanel;    
    }
    private void weekDaysPanel() {
        topWeekDaysPanel = new JPanel();
        size.sizeLocationWeekDayPanel(topWeekDaysPanel);
        
        dateInWeek = new JButton[7];
        for (int i = 0; i < 7; ++i) {
            dateInWeek[i] = new JButton();
            size.sizeButtonsTopWeekDayPanel(dateInWeek[i]);
        }        
    }   
    private void makeWeekDaysPanel(String date, JPanel pane) {
        String[] dateParts = date.split("\\.");
        int day = Integer.parseInt(dateParts[0]);
        int month = Integer.parseInt(dateParts[1]);
        int year = Integer.parseInt(dateParts[2]);
        
        for (int i = 0; i < 7; ++i) {
            dateInWeek[i].setText("");
            dateInWeek[i].setForeground(Color.BLACK);
            dateInWeek[i].setIcon(null);
        }        
        topWeekDaysPanel.setOpaque(false);
        topWeekDaysPanel.setBackground(Color.red);
        topWeekDaysPanel.setLayout(null);        
                
        int nod, som; //Number Of Days, Start Of Month
        //Get first day of month and number of days        
        Calendar calTmp = Calendar.getInstance();
        calTmp.set(Calendar.DATE, 1);
        calTmp.set(Calendar.MONTH, month - 1);
        calTmp.set(Calendar.YEAR, year);
        calTmp.set(Calendar.DAY_OF_MONTH, 1);
        Date firstDayOfMonth = calTmp.getTime();
        som = firstDayOfMonth.getDay();
        nod = calTmp.getActualMaximum(Calendar.DAY_OF_MONTH);
        
        if (som == 0)
            som = 6;
        else
            som = som - 1; 
        
        int currentPositionOfDay = (day + som) % 7;
        if (currentPositionOfDay == 0)
            currentPositionOfDay = 7;
       
        for (int i = 0; i < 7; ++i) {
            int dateNumber = day - currentPositionOfDay + i + 1;
            if (dateNumber > nod)
                dateNumber = dateNumber % nod;
            
            if (dateNumber <= 0) {
                int prevNod;       
                Calendar prevCalTmp = Calendar.getInstance();
                prevCalTmp.set(Calendar.DATE, 1);
                prevCalTmp.set(Calendar.MONTH, month - 2);
                prevCalTmp.set(Calendar.YEAR, year);
                prevCalTmp.set(Calendar.DAY_OF_MONTH, 1);
                prevNod = prevCalTmp.getActualMaximum(Calendar.DAY_OF_MONTH);
                dateNumber = prevNod + dateNumber;
            }
            
            size.sizeButtonsTopWeekDayPanel(dateInWeek[i]);
            size.LocationButtonsTopWeekDayPanel(dateInWeek[i], i);
            dateInWeek[i].setBorderPainted(false);
            dateInWeek[i].setFocusPainted(false);
            dateInWeek[i].setContentAreaFilled(false);

            if ((i + 1) == currentPositionOfDay) {
                //dateInWeek[j].setForeground(Color.red);
                imgSelectedDay = iconSelectedDay.getImage().getScaledInstance
                                                (dateInWeek[i].getHeight(),
                                                 dateInWeek[i].getHeight(),
                                                 java.awt.Image.SCALE_SMOOTH); 
                iconSelectedDay = new ImageIcon(imgSelectedDay);
                dateInWeek[i].setIcon(iconSelectedDay);
            }
            dateInWeek[i].setText("" + dateNumber);
            dateInWeek[i].setHorizontalTextPosition(AbstractButton.CENTER);
            dateInWeek[i].setFont(new Font("Arial", Font.PLAIN, (int)(size.frameHeight(size.getRezolution())*0.015)));
            pane.add(dateInWeek[i]);
        }
    }   
    
    private void makeCalendarPanel(){
        calendarPanel = new JPanel();
        calendarPanel.setOpaque(false);
        //calendarPanel.setBackground(Color.red);
        size.sizeLocationCentralPanel(calendarPanel);
        calendarPanel.setLayout(new GridBagLayout());
        buttons = new JButton[rows][collumns];
        GridBagConstraints cp = new GridBagConstraints();
        for(int i = 0; i < rows; i++) {
            for (int j = 0; j < collumns; j++) {
                buttons[i][j] = new JButton();
                //buttons[i][j].setBorderPainted(false);
                //buttons[i][j].setFocusPainted(false);
                //buttons[i][j].setContentAreaFilled(false);
            }
        }
    }
    
    private JPanel makeNextPrevPanel(){
        nextPrevPanel = new JPanel();
        nextPrevPanel.setOpaque(false);
        size.setSizeLocationNextPrevPanel(nextPrevPanel);
        nextPrevPanel.setLayout(new GridBagLayout());
        GridBagConstraints btn = new GridBagConstraints();         
        btnPrev = new JButton();
        btnPrev.setIcon(iconBtnPrev);
        size.setSizeButtonsNextPrevPanel(btnPrev);       
        btnPrev.setPressedIcon(iconbtnPrevPr);
        btnPrev.setRolloverIcon(iconbtnPrevPr);
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
        size.setSizeButtonsNextPrevPanel(btnNext);
        btnNext.setPressedIcon(iconbtnNextPr);
        btnNext.setRolloverIcon(iconbtnNextPr);
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
        size.setSizeLocationChangeYearPanel(changeYearPanel);
        lblYear = new JLabel ("Change year:");
        lblYear.setHorizontalAlignment(JLabel.LEFT);
        Font fontYear = (new Font("Arial", Font.PLAIN, size.frameHeight(size.getRezolution())/60));
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
    
    private JPanel makeWeekPANEL() {
        String[] testStr = {    "aaaaaaaaaaaaa", "aaaaaaaaaaaaa", "aaaaaaaaaaaaa", "aaaaaaaaaaaaa",
                                "aaaaaaaaaaaaa", "aaaaaaaaaaaaa", "aaaaaaaaaaaaa", "aaaaaaaaaaaaa", "aaaaaaaaaaaaa", "aaaaaaaaaaaaa",
                                "aaaaaaaaaaaaa", "aaaaaaaaaaaaa", "aaaaaaaaaaaaa", "aaaaaaaaaaaaa", "aaaaaaaaaaaaa", "aaaaaaaaaaaaa",
                                "aaaaaaaaaaaaa", "aaaaaaaaaaaaa", "aaaaaaaaaaaaa","aaaaaaaaaaaaa", "aaaaaaaaaaaaa", "aaaaaaaaaaaaa",
                                "aaaaaaaaaaaaa", "aaaaaaaaaaaaa", "aaaaaaaaaaaaa"};
        pane = new JPanel();
        JPanel paneInScroll = new JPanel();
        JScrollPane scrlPane = new JScrollPane(paneInScroll);
               
        size.sizeLocationCentralPanel(pane);
        paneInScroll.setSize(450, pane.getHeight());
        scrlPane.setSize(450, pane.getWidth());      
        
        scrlPane.setLocation(0, 0);
        paneInScroll.setLocation(0,0);    
        
        pane.setLayout(null);
        paneInScroll.setLayout(new GridBagLayout());
        GridBagConstraints sp = new GridBagConstraints();      
        
        //pane.setBackground(Color.red);
        pane.setOpaque(false);
                   
        scrlPane.setWheelScrollingEnabled(true);        
        scrlPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
                      
        for (int i = 0; i < testStr.length; ++i) {
            sp.gridx = 0;
            sp.gridy = i;
            sp.gridwidth = 1;
            sp.gridheight = 1;
            sp.weightx = sp.weighty = 1.0;
            //sp.insets = new Insets(0, 0, 0, 0);
            sp.fill = GridBagConstraints.BOTH;
            JButton btn = new JButton(testStr[i]);
            //setPreferredSize 430*100
            size.sizeButtonsInScrollPaneForEvents(btn);
            btn.setBackground(Color.WHITE);
            paneInScroll.add(btn, sp);
        }        
        for (int i = 0; i < 7; ++i) {
            scrlPane.setLocation(i*pane.getWidth()/7, 0);
            pane.add(scrlPane);
        }       
        return pane;
    }
    
    private JPanel makeBottomPanel() {
        bottomPanel = new JPanel();
     
        JButton buttonWeek;
        JButton buttonMonth;
        JButton buttonYear;
        JButton buttonSettings;
              
        bottomPanel.setLayout(new GridBagLayout());
        size.setSizeLocationBottomPanel(bottomPanel);
        bottomPanel.setOpaque(false);
        GridBagConstraints bp = new GridBagConstraints(); 

        buttonWeek = new JButton();
        buttonWeek.addActionListener(new selectedWeek_Action());
        buttonWeek.setText("WEEK");
        buttonWeek.setFont(new Font("Arial", Font.PLAIN, size.frameHeight(size.getRezolution())/60));
        buttonWeek.setHorizontalTextPosition(AbstractButton.CENTER);
        buttonWeek.setIcon(iconWeek);
        buttonWeek.setPressedIcon(setPrSizesBottomPanel(imgWeekPr, iconWeekPr));
        buttonWeek.setRolloverIcon(setPrSizesBottomPanel(imgWeekPr, iconWeekPr));
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
        buttonMonth.addActionListener(new selectedMonth_Action());
        buttonMonth.setIcon(iconMonthYear);
        buttonMonth.setText("MONTH");
        buttonMonth.setFont(new Font("Arial", Font.PLAIN, size.frameHeight(size.getRezolution())/60));
        buttonMonth.setHorizontalTextPosition(AbstractButton.CENTER);
        buttonMonth.setPressedIcon(setPrSizesBottomPanel(imgMonthYearPr, iconMonthYearPr));
        buttonMonth.setRolloverIcon(setPrSizesBottomPanel(imgMonthYearPr, iconMonthYearPr));
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
        buttonYear.setText("YEAR");
        buttonYear.setFont(new Font("Arial", Font.PLAIN, size.frameHeight(size.getRezolution())/60));
        buttonYear.setHorizontalTextPosition(AbstractButton.CENTER);
        buttonYear.setPressedIcon(setPrSizesBottomPanel(imgMonthYearPr, iconMonthYearPr));
        buttonYear.setRolloverIcon(setPrSizesBottomPanel(imgMonthYearPr, iconMonthYearPr));
        buttonYear.setBorderPainted(false);
        buttonYear.setFocusPainted(false);
        buttonYear.setContentAreaFilled(false);
        buttonYear.addActionListener(new selectedYear_Action());
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
        buttonSettings.setPressedIcon(setPrSizesBottomPanel(imgSettingsPr, iconSettingsPr));
        buttonSettings.setRolloverIcon(setPrSizesBottomPanel(imgSettingsPr, iconSettingsPr));
        buttonSettings.setBorderPainted(false);
        buttonSettings.setFocusPainted(false);
        buttonSettings.setContentAreaFilled(false);
        buttonSettings.addActionListener(new selectedSettings_Action());
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

        return bottomPanel;
    }
    
    public JPanel yearPanel() {
        yearPanel = new JPanel();
        size.sizeLocationCentralPanel(yearPanel);
        yearPanel.setLayout(new GridBagLayout());
        yearPanel.setOpaque(false);
        GridBagConstraints yp = new GridBagConstraints();
        yearButtons = new JButton[12];
        for(int i = 0; i < 4; i++) {
            for (int j = 0; j < 3; j++) {
                yearButtons[j] = new JButton();
                yearButtons[j].addActionListener(new selectedMonthOfYear_Action());
                yearButtons[j].setBackground(Color.LIGHT_GRAY);
                yearButtons[j].setText(months[i*3 + j]);
                size.setFont30(yearButtons[j]);
                yp.gridx = j;
                yp.gridy = i;
                yp.gridwidth = 1;
                yp.gridheight = 1;
                yp.weightx = yp.weighty = 0.5;
                yp.insets = new Insets(5, 5, 5, 5);                
                yp.fill = GridBagConstraints.BOTH; 
                yearPanel.add(yearButtons[j], yp);
            }
        }
        yearPanel.setVisible(true);
        return yearPanel;
    }
    
    public ImageIcon setPrSizesBottomPanel(Image img, ImageIcon icon) {
    img = icon.getImage().getScaledInstance
           ((size.frameHeight(size.getRezolution())/6), 
            (size.frameHeight(size.getRezolution())/12), java.awt.Image.SCALE_SMOOTH); 
        icon = new ImageIcon(img);
        return icon;
}
    
    public JPanel settingsPanel() {
        settingsPanel = new JPanel();
        size.sizeLocationCentralPanel(settingsPanel);
        settingsPanel.setOpaque(false);
        settingsPanel.setLayout(null);
        JTextArea textSettingsPanel = new JTextArea();
        textSettingsPanel.setSize(settingsPanel.getWidth(), settingsPanel.getHeight());
        textSettingsPanel.setLocation(0,0);
        textSettingsPanel.setOpaque(false);
        textSettingsPanel.setLineWrap(true);
        textSettingsPanel.setWrapStyleWord(true);
        textSettingsPanel.setText("\n          WARNING!\n"
                                + "          You are using \n "
                                + "          the demo version of the product.\n \n" 
                                + "           To access the advanced settings \n"
                                + "           please activate the license.\n" 
                                + "                All rights reserved.");
        size.setFont50(textSettingsPanel);
        textSettingsPanel.setEditable(false);
        textSettingsPanel.setForeground(Color.GRAY);        
        settingsPanel.add(textSettingsPanel);
        return settingsPanel;
    }
     class cmbYear_Action implements ActionListener{
        public void actionPerformed (ActionEvent e){
            if (cmbYear.getSelectedItem() != null){
                String b = cmbYear.getSelectedItem().toString();
                currentYear = Integer.parseInt(b);
                refreshCalendar(currentMonth, currentYear);
            }
        }
    }
class selectedDay_Action implements ActionListener{        
        public void actionPerformed(ActionEvent e) {
            JButton btn = (JButton)e.getSource();
            selectedNumber = btn.getText();
            String selectedDate = btn.getText() +"." + (currentMonth + 1) + "." + currentYear;
            if (" ".equals(btn.getText()))
                return;
            if (dayPanel == null) {
                dayPanel = new dayPanel(selectedDate, dumper);
                dayPanel.setDumper(dumper);
            }
            PaintMainFrame.changeCentralPanel(dayPanel.pane, panelType.DAYPANEL);
            makeWeekDaysPanel(selectedDate, topWeekDaysPanel);
            topWeekPanel.setLocation(topWeekPanel.getLocation().x, (int) (size.frameHeight(size.getRezolution())*19/80));
            lblMonth.setText(selectedDate);
            panelflag = panelType.DAYPANEL;
            currentDay = Integer.parseInt(btn.getText());
            dayPanel.updateContent("" + currentDay + "." + (currentMonth + 1) + "." + currentYear);  
        }
    }    
    class selectedMonth_Action implements ActionListener{        
        public void actionPerformed(ActionEvent e) {
            if (panelflag == panelType.MONTHPANEL)
                return;
            PaintMainFrame.changeCentralPanel(calendarPanel, panelType.MONTHPANEL);
            panelflag = panelType.MONTHPANEL;
            topWeekPanel.setLocation(topWeekPanel.getLocation().x, (size.frameHeight(size.getRezolution())*67/240));
            refreshCalendar(currentMonth, currentYear);
        }
    }    
    class selectedWeek_Action implements ActionListener {
        public void actionPerformed(ActionEvent e) {            
            if (WeekPANEL == null) 
                WeekPANEL = makeWeekPANEL();
            if (panelflag == panelType.WEEKPANEL)
                return;
            PaintMainFrame.changeCentralPanel(WeekPANEL, panelType.WEEKPANEL);
            panelflag = panelType.WEEKPANEL;
        }
    }
    
    class selectedYear_Action implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (yearPanel == null)
                yearPanel = yearPanel();
            if (panelflag == panelType.YEARPANEL)
                return;
            lblMonth.setText("" + currentYear);
            PaintMainFrame.changeCentralPanel(yearPanel, panelType.YEARPANEL);
            panelflag = panelType.YEARPANEL;
        }
    }
    
    class selectedMonthOfYear_Action implements ActionListener{        
        public void actionPerformed(ActionEvent e) {
            PaintMainFrame.changeCentralPanel(calendarPanel, panelType.MONTHPANEL);
            panelflag = panelType.MONTHPANEL;
            topWeekPanel.setLocation(topWeekPanel.getLocation().x, (size.frameHeight(size.getRezolution())*67/240));
            JButton btn = (JButton)e.getSource();
            for (int i = 0; i < months.length; ++i)
                if (months[i].equals(btn.getText())) {
                    currentMonth = i;
                    refreshCalendar(currentMonth, currentYear);
                    break;
                }
        }
    }
    
    class selectedSettings_Action implements ActionListener{        
        public void actionPerformed(ActionEvent e) {
            lblMonth.setText("");
            PaintMainFrame.changeCentralPanel(settingsPanel, panelType.SETPANEL);
            panelflag = panelType.SETPANEL;
        }
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
            } else if (panelflag == panelType.MONTHPANEL) {
                if (currentMonth == 0){ //Back one year
                    currentMonth = 11;
                    currentYear -= 1;
                } else{ //Back one month
                    currentMonth -= 1;
                }
                refreshCalendar(currentMonth, currentYear);
            } else if (panelflag == panelType.YEARPANEL) {
                currentYear--;
                lblMonth.setText("" + currentYear);
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
            else if (panelflag == panelType.MONTHPANEL) {
                if (currentMonth == 11){ //Foward one year
                    currentMonth = 0;
                    currentYear += 1;
                }
                else{ //Foward one month
                    currentMonth += 1;
                }
                refreshCalendar(currentMonth, currentYear);
            } else if (panelflag == panelType.YEARPANEL) {
                currentYear++;
                lblMonth.setText("" + currentYear);
            }
        }
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
        return topWeekPanel;
    }
}
