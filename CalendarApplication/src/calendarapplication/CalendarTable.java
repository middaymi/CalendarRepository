package calendarapplication;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.swing.BorderFactory;
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
    static private JLabel lblMonth, lblYear;
    static private JButton btnPrev, btnNext;
    static private JTable tblCalendar;
    //for choose a year from list
    static private JComboBox cmbYear;
    static private JFrame frmMain;
    static private Container pane;
    static private DefaultTableModel mtblCalendar; //Table model
    static private JScrollPane stblCalendar; //The scrollpane
    static private JPanel pnlCalendar;
    static private int realYear, realMonth, realDay, currentYear, currentMonth;
    
    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
//    static JPanel calendarPanel = new JPanel();
//    static JButton squareOfDay = new JButton();
    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    CalendarTable () {
        
        //Look and feel
        try {UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());}
        catch (ClassNotFoundException | 
               InstantiationException |
               IllegalAccessException |
               UnsupportedLookAndFeelException e) {}      
        
        //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        //кнопка для в таблице календаря
//        squareOfDay.setIcon(new ImageIcon("images\\square.png"));
//        squareOfDay.setBorderPainted(false);
//        squareOfDay.setFocusPainted(false);
//        squareOfDay.setContentAreaFilled(false);  
//        
//        calendarPanel.setLayout(new GridLayout(2,2,5,5));
        //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

        //Prepare frame
        setSize(1000, 1000); //Set size to 400x400 pixels

        //Create controls
        lblMonth = new JLabel ("January");
        lblYear = new JLabel ("Change year:");
        cmbYear = new JComboBox();
        btnPrev = new JButton ("Previous month");
        btnNext = new JButton ("Next month");
        mtblCalendar = new DefaultTableModel(){public boolean isCellEditable(int rowIndex, int mColIndex){return false;}};
        tblCalendar = new JTable(mtblCalendar);
        stblCalendar = new JScrollPane(tblCalendar);

        //Set border
        setBorder(BorderFactory.createTitledBorder("Calendar"));

        //добавляем панели (салендарь, месяц...) 
        add(lblMonth);
        add(lblYear);
        add(cmbYear);
        add(btnPrev);
        add(btnNext);
        add(stblCalendar);

        //установка границ
        setBounds(0, 0, 520, 535);
        lblMonth.setBounds(160-lblMonth.getPreferredSize().width/2, 25, 100, 25);
        lblYear.setBounds(10, 305, 80, 20);
        cmbYear.setBounds(230, 305, 80, 20);
        btnPrev.setBounds(10, 25, 50, 25);
        btnNext.setBounds(260, 25, 50, 25);
        stblCalendar.setBounds(10, 50, 300, 250);


        //Make frame visible
        setVisible(true);

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

        tblCalendar.getParent().setBackground(tblCalendar.getBackground()); //Set background

        //No resize/reorder
        tblCalendar.getTableHeader().setResizingAllowed(false);
        tblCalendar.getTableHeader().setReorderingAllowed(false);

        //Single cell selection
        tblCalendar.setColumnSelectionAllowed(true);
        tblCalendar.setRowSelectionAllowed(true);
        tblCalendar.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        //Set row/column count
        tblCalendar.setRowHeight(38);
        mtblCalendar.setColumnCount(7);
        mtblCalendar.setRowCount(6);

        //Populate table
        for (int i=realYear-100; i<=realYear+100; i++){
            cmbYear.addItem(String.valueOf(i));
        }

        refreshCalendar (realMonth, realYear); //Refresh calendar

        //Register action listeners
        btnPrev.addActionListener(new btnPrev_Action());
        btnNext.addActionListener(new btnNext_Action());
        cmbYear.addActionListener(new cmbYear_Action());
    }
    
    public static void refreshCalendar(int month, int year){
        //Variables
        String[] months =  {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        int nod, som; //Number Of Days, Start Of Month
        
        //Allow/disallow buttons
        btnPrev.setEnabled(true);
        btnNext.setEnabled(true);
        //if (month == 0 && year <= realYear-10){btnPrev.setEnabled(false);} //Too early
        //if (month == 11 && year >= realYear+100){btnNext.setEnabled(false);} //Too late
        lblMonth.setText(months[month]); //Refresh the month label (at the top)
        lblMonth.setBounds(160-lblMonth.getPreferredSize().width/2, 25, 180, 25); //Re-align label with calendar
        cmbYear.setSelectedItem(String.valueOf(year)); //Select the correct year in the combo box
        
        //Clear table
        for (int i=0; i<6; i++){
            for (int j=0; j<7; j++){
                mtblCalendar.setValueAt(null, i, j);
                //calendarPanel.add(squareOfDay);
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
            //calendarPanel.add(squareOfDay);
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
}