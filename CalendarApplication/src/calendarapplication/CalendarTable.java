package calendarapplication;

import static calendarapplication.CalendarApplication.frameHeight;
import static calendarapplication.CalendarApplication.getRezolution;
import static calendarapplication.CalendarApplication.setSizeButtonsNextPrevPanel;
import static calendarapplication.CalendarApplication.setSizeLocationChangeYearPanel;
import static calendarapplication.CalendarApplication.setSizeLocationNextPrevPanel;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
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
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;


public class CalendarTable extends JPanel {

    static JLabel lblMonth, lblYear;
    static JButton btnPrev, btnNext;
    static JTable tblCalendar;
    //for choose a year from list
    static JComboBox cmbYear;
    static JFrame frmMain;
    static Container pane;
    static DefaultTableModel mtblCalendar; //Table model
    static JScrollPane stblCalendar; //The scrollpane
    static int realYear, realMonth, realDay, currentYear, currentMonth;
    JPanel changeYearPanel;
    JPanel nextPrevPanel;
    static JPanel pnlCalendar;
    JButton ButtonLeft;
    JButton betweenLeftAndRight;
    JButton ButtonRight;
    ImageIcon iconBtnPrev;
    ImageIcon iconBtnNext;
    ImageIcon iconDay;
    Image imgBtnPrev;
    Image imgBtnNext;
    Image imgDay;
    JPanel calendarPanel;
       
  
    CalendarTable () {
        iconBtnPrev = new ImageIcon("images\\Left.png");
        iconBtnNext = new ImageIcon("images\\Right.png");
        iconDay = new ImageIcon("images\\white.png");
        
                                 
        imgBtnPrev = iconBtnPrev.getImage();
        imgBtnNext = iconBtnNext.getImage();
        imgDay = iconDay.getImage();
        
        iconBtnPrev = new ImageIcon(imgBtnPrev);
        iconBtnNext = new ImageIcon(imgBtnNext);
        iconDay = new ImageIcon(imgDay);
        
        //Look and feel
        try {UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());}
        catch (ClassNotFoundException | 
               InstantiationException |
               IllegalAccessException |
               UnsupportedLookAndFeelException e) {}      
        
        //Prepare frame
        setOpaque(false);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(CountBorder(), CountBorder(), 
                                                  CountBorder(), CountBorder()));
        
        //Create controls
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
       
        //changeYearPanel.add(lblYear);
        //changeYearPanel.add(cmbYear);
        
        //**********************START nextPrevPanel*****************************
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
        //*************************END nextPrexPanel****************************
        
        mtblCalendar = new DefaultTableModel(){public boolean isCellEditable(int rowIndex, int mColIndex){return false;}};
        tblCalendar = new JTable(mtblCalendar);
        stblCalendar = new JScrollPane(tblCalendar);
        
        
        //************************START calendarPanel***************************
        calendarPanel = new JPanel();
        calendarPanel.setSize(53*frameHeight(getRezolution())/60,
                              13*frameHeight(getRezolution())/24);
        calendarPanel.setLocation((int)((frameHeight(getRezolution()) 
                                         - calendarPanel.getSize().width)/2), 
                                  (int) (frameHeight(getRezolution())*0.2875));
        calendarPanel.setBackground(Color.red);
        calendarPanel.setOpaque(false);
        calendarPanel.setLayout(new GridBagLayout());
        GridBagConstraints cp = new GridBagConstraints();
        
        int m, n;
        for (m = 0; m < 7; m++){
            for (n = 0; n < 7; n++) {
                cp.gridx = n;
                cp.gridy = m;
                cp.gridwidth = 1;
                cp.gridheight = 1;
                cp.weightx = cp.weighty = 1.0;
                cp.insets = new Insets(5, 5, 5, 5);
                JButton b = new JButton();
                b.setIcon(iconDay);
                b.setBorderPainted(false);
                b.setFocusPainted(false);
                b.setContentAreaFilled(false);
                b.addComponentListener(new ComponentAdapter() {
                @Override
                public void componentResized(ComponentEvent e) {
                    JButton button = (JButton) e.getComponent();
                    Dimension size = button.getSize();
                    Image scaled = imgDay.getScaledInstance(size.width, size.height, java.awt.Image.SCALE_SMOOTH);
                    button.setIcon(new ImageIcon(scaled));
                };});
                calendarPanel.add(b, cp);
            }
            n = 0;
        }
        //***********************END calendarPanel******************************

        //add panels (calendar, month, et) 

        //добавляем панели (календарь, месяц...) 
        //add(changeYearPanel);
        //add(nextPrevPanel);
        //add(stblCalendar);

        //Make frame visible
        //setVisible(true);
        
        //Get real month/year
        GregorianCalendar cal = new GregorianCalendar(); //Create calendar
        realDay = cal.get(GregorianCalendar.DAY_OF_MONTH); //Get day
        realMonth = cal.get(GregorianCalendar.MONTH); //Get month
        realYear = cal.get(GregorianCalendar.YEAR); //Get year
        currentMonth = realMonth; //Match month and year
        currentYear = realYear;
        

        //Add headers
        String[] headers = {"Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"}; 
        for (int i=0; i<7; i++){
            mtblCalendar.addColumn(headers[i]);
        }


        //tblCalendar.getParent().setBackground(tblCalendar.getBackground()); //Set background
        tblCalendar.setOpaque(false);
        //No resize/reorder
        tblCalendar.getTableHeader().setResizingAllowed(true);
        tblCalendar.getTableHeader().setReorderingAllowed(false);

        //Single cell selection
        tblCalendar.setColumnSelectionAllowed(true);
        tblCalendar.setRowSelectionAllowed(true);
        tblCalendar.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        //Set row/column count
        tblCalendar.setRowHeight(50);
        mtblCalendar.setColumnCount(7);
        mtblCalendar.setRowCount(6);

        //Populate table
        for (int i=realYear-100; i<=realYear+100; i++){
            cmbYear.addItem(String.valueOf(i));
        }

        refreshCalendar (realMonth, realYear); //Refresh calendar

        //Register action listeners
        cmbYear.addActionListener(new cmbYear_Action());
    }
    
       
    
    public static void refreshCalendar(int month, int year){
        //Variables
        String[] months =  {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        int nod, som; //Number Of Days, Start Of Month

        lblMonth.setText(months[month]); //Refresh the month label (at the top)
        lblMonth.setHorizontalAlignment(JLabel.CENTER);
        Font fontMonth = (new Font("Arial", Font.PLAIN, frameHeight(getRezolution())/30));
        lblMonth.setFont(fontMonth);
                
        cmbYear.setSelectedItem(String.valueOf(year)); //Select the correct year in the combo box
        
        //Clear table
        for (int i=0; i<6; i++){
            for (int j=0; j<7; j++){
                mtblCalendar.setValueAt(null, i, j);
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

        //Draw calendar
        for (int i=1; i<=nod; i++){
            int row     =  (i+som-1)/7;
            int column  =  (i+som-1)%7;
            mtblCalendar.setValueAt(i, row, column);
        }

        //Apply renderers
        tblCalendar.setDefaultRenderer(tblCalendar.getColumnClass(0), new tblCalendarRenderer());
    }
    
    static class tblCalendarRenderer extends DefaultTableCellRenderer{
        public Component getTableCellRendererComponent (JTable table, Object value, boolean selected, boolean focused, int row, int column){
            super.getTableCellRendererComponent(table, value, selected, focused, row, column);
            if (column == 5 || column == 6){ //Week-end
                setBackground(new Color(255, 220, 220));
            }
            else{ //Week
                setBackground(new Color(255, 255, 255));
            }
            if (value != null){
                if (Integer.parseInt(value.toString()) == realDay && currentMonth == realMonth && currentYear == realYear){ //Today
                    setBackground(new Color(220, 220, 255));
                }
            }
            setBorder(null);
            setForeground(Color.black);
            return this;
        }
    }
    
    static class btnPrev_Action implements ActionListener{
        public void actionPerformed (ActionEvent e){
            if (currentMonth == 0){ //Back one year
                currentMonth = 11;
                currentYear -= 1;
            }
            else{ //Back one month
                currentMonth -= 1;
            }
            refreshCalendar(currentMonth, currentYear);
        }
    }
    static class btnNext_Action implements ActionListener{
        public void actionPerformed (ActionEvent e){
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
    static class cmbYear_Action implements ActionListener{
        public void actionPerformed (ActionEvent e){
            if (cmbYear.getSelectedItem() != null){
                String b = cmbYear.getSelectedItem().toString();
                currentYear = Integer.parseInt(b);
                refreshCalendar(currentMonth, currentYear);
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
    
    public static int CountBorder() {
        int count = 0;
        count = (int)(frameHeight(getRezolution())*0.025);
        return count;      
    }
    
}