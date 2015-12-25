package calendarapplication;

import calendarapplication.Controller.Dumper;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;

public class dayPanel {
    Sizes size = new Sizes();
    JPanel pane;
    JPanel paneInScroll;
    JPanel paneRight;
    JTextArea textAreaInScroll;
    JButton pressedButton;
    GridBagConstraints sp;
    int countOfEvents;
    Dumper dumper;
    String currentDate;
    
    dayPanel(String date, Dumper dumper) {
        currentDate = date;
        countOfEvents = 0;
        ArrayList<String> eventsText = dumper.findEventsByDate(date);
        pane = new JPanel();
        paneInScroll = new JPanel();
        paneRight = new JPanel();
        textAreaInScroll = new JTextArea(5, 10);
        JButton del = new JButton("del");
        JButton ok = new JButton("save");
        JButton create = new JButton("add");
        JScrollPane scrlPane = new JScrollPane(paneInScroll);
        JScrollPane scrollPaneTextArea = new JScrollPane(textAreaInScroll);
        
        create.addActionListener(new createEventButton_Action());
        del.addActionListener(new deleteEventButton_Action());
        ok.addActionListener(new modifyEventButton_Action());    
        
        size.sizeLocationCentralPanel(pane);
        size.sizeLocationPaneInScroll(paneInScroll);
        size.sizeLocationScrollPaneTextArea(scrollPaneTextArea);
        size.sizeLocationPaneRight(paneRight);
        size.sizeLocationScrlPane(scrlPane);
        size.sizeLocationButtonOnPaneRight(del, 1);
        size.sizeLocationButtonOnPaneRight(create, 2);
        size.sizeLocationButtonOnPaneRight(ok, 3);
        size.sizeLocationTextAreaInScroll(textAreaInScroll); 
               
        pane.setLayout(null);
        paneInScroll.setLayout(new GridBagLayout());
        sp = new GridBagConstraints();
        paneRight.setLayout(null);       
        
        pane.setOpaque(false);
        paneInScroll.setOpaque(false);
        scrlPane.setOpaque(false);
        scrollPaneTextArea.setOpaque(false);
                
        del.setBackground(Color.WHITE);
        create.setBackground(Color.WHITE);
        ok.setBackground(Color.WHITE);
        
        textAreaInScroll.setLineWrap(true);
        textAreaInScroll.setWrapStyleWord(true);       
        textAreaInScroll.setBorder(new TitledBorder("CREATE YOUR EVENT"));
        size.setFont50(textAreaInScroll);
        textAreaInScroll.setBackground(Color.WHITE);       
              
        scrlPane.setWheelScrollingEnabled(true);
        scrlPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        
        scrollPaneTextArea.setWheelScrollingEnabled(true);
        scrollPaneTextArea.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
               
        countOfEvents = eventsText.size();
        
        for (int i = 0; i < eventsText.size(); ++i) {
            sp.gridx = 0;
            sp.gridy = i;
            sp.gridwidth = 1;
            sp.gridheight = 1;
            sp.weightx = sp.weighty = 1.0;
            sp.insets = new Insets(5, 0, 5, 0);
            sp.fill = GridBagConstraints.BOTH;

            JButton btn = new JButton(eventsText.get(i));
         
            btn.addActionListener(new dayEventButton_Action());
            size.sizeButtonsInScrollPaneForEvents(btn);
            btn.setBackground(Color.WHITE);
            btn.setBorderPainted(false);
            btn.setFocusPainted(false);
            //btn.setContentAreaFilled(false);
            size.setFont40(btn);
            paneInScroll.add(btn, sp);
        }
        
        paneRight.add(scrollPaneTextArea);       
        paneRight.add(del);
        paneRight.add(create);
        paneRight.add(ok);
        
        pane.add(scrlPane);
        pane.add(paneRight);
    }
    
    public void updateContent(String date) {
        currentDate = date;
        paneInScroll.removeAll();
        paneInScroll.updateUI();
        ArrayList<String> eventsText = dumper.findEventsByDate(date);
        countOfEvents = eventsText.size();
        
        for (int i = 0; i < eventsText.size(); ++i) {
            sp.gridx = 0;
            sp.gridy = i;
            sp.gridwidth = 1;
            sp.gridheight = 1;
            sp.weightx = sp.weighty = 1.0;
            sp.insets = new Insets(5, 0, 5, 0);
            sp.fill = GridBagConstraints.BOTH;
            
            JButton btn = new JButton(eventsText.get(i));
            size.setFont40(btn);
         
            btn.addActionListener(new dayEventButton_Action());
            size.sizeButtonsInScrollPaneForEvents(btn);
            btn.setBackground(Color.WHITE);
            btn.setBorderPainted(false);
            btn.setFocusPainted(false);
            //btn.setContentAreaFilled(false);
            
            paneInScroll.add(btn, sp);
        }
        textAreaInScroll.setText("");
    }
    
    void setDumper(Dumper newDumper) {
        if (newDumper != null)
            dumper = newDumper;
    }
    
    class dayEventButton_Action implements ActionListener{        
        public void actionPerformed(ActionEvent e) {
            JButton btn = (JButton)e.getSource();
            btn.setBackground(Color.LIGHT_GRAY);
            String eventText = btn.getText();
            if (pressedButton != null)
                pressedButton.setBackground(Color.WHITE);
            pressedButton = btn;
            textAreaInScroll.setText(eventText);
        }
    }
    
    class createEventButton_Action implements ActionListener{        
        public void actionPerformed(ActionEvent e) {
            String eventText = textAreaInScroll.getText();
            
            ArrayList<String> eventsByDate = dumper.findEventsByDate(currentDate);
            for (String i : eventsByDate) {
                if (i.equals(eventText))
                    return;
            }

            if ("".equals(eventText))
                return;
            JButton btn = new JButton(eventText);
            btn.addActionListener(new dayEventButton_Action());
            size.sizeButtonsInScrollPaneForEvents(btn);
            btn.setBackground(Color.RED);
            size.setFont40(btn);
            
            sp.gridx = 0;
            sp.gridy = countOfEvents;
            countOfEvents++;
            sp.gridwidth = 1;
            sp.gridheight = 1;
            sp.weightx = sp.weighty = 1.0;
            //paneInScroll.setSize(435, paneInScroll.getHeight() + 500);
            sp.fill = GridBagConstraints.BOTH;
            paneInScroll.add(btn, sp);
            paneInScroll.updateUI();
            
            dumper.saveEvent(currentDate, eventText);
        }
    }
    
    class deleteEventButton_Action implements ActionListener{        
        public void actionPerformed(ActionEvent e) {
            if (pressedButton != null) {
                dumper.removeEvent(currentDate, pressedButton.getText());
                paneInScroll.remove(pressedButton);

                    countOfEvents--;
                    pressedButton = null;
                    textAreaInScroll.setText("");
                    paneInScroll.updateUI();

                countOfEvents--;
                pressedButton = null;
                paneInScroll.updateUI();

            }
        }
    }
          
    class modifyEventButton_Action implements ActionListener {        
        public void actionPerformed(ActionEvent e) {
            String eventText = textAreaInScroll.getText();
            if (pressedButton != null) {
                if ("".equals(eventText)) {
                    dumper.removeEvent(currentDate, pressedButton.getText());
                    paneInScroll.remove(pressedButton);
                    return;
                }
                dumper.modifyEvent(currentDate, pressedButton.getText(), eventText);
                pressedButton.setText(eventText);
            }
        }
    }
    }


