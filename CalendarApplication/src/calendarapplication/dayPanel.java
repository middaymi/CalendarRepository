/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calendarapplication;

import calendarapplication.Controller.Dumper;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;

/**
 *
 * @author Светлана
 */
public class dayPanel {
    Sizes size = new Sizes();
    JPanel pane;
    JPanel paneInScroll;
    JPanel paneRight;
    JTextArea textArea;
    JButton pressedButton;
    GridBagConstraints sp;
    int countOfEvents;
    Dumper dumper;
    String currentDate;
    
    dayPanel(String date, Dumper dumper) {
        currentDate = date;
        countOfEvents = 0;
        String[] eventsText = dumper.findEventsByDate(date);
        pane = new JPanel();
        paneInScroll = new JPanel();
        JScrollPane scrlPane = new JScrollPane(paneInScroll);
        paneRight = new JPanel();
        textArea = new JTextArea(5, 10);
        JButton del = new JButton("DEL");
        JButton ok = new JButton("SAVE");
        JButton create = new JButton("CREATE");
        
        create.addActionListener(new createEventButton_Action());
        del.addActionListener(new deleteEventButton_Action());
        ok.addActionListener(new modifyEventButton_Action());
        
        size.sizeLocationCentralPanel(pane);
        paneInScroll.setSize(435, 600);
        paneRight.setSize(600, pane.getWidth());
        textArea.setSize(450, 250);
        del.setSize(100, 50);
        ok.setSize(100, 50);
        create.setSize(100, 50);
        scrlPane.setSize(450, pane.getWidth());      
        
        scrlPane.setLocation(0, 0);
        paneInScroll.setLocation(0,0);
        paneRight.setLocation(450, 0);
        textArea.setLocation(75, 50);
        del.setLocation(175, 550);
        create.setLocation(200, 550);
        ok.setLocation(325, 550); 
        
        pane.setLayout(null);
        paneInScroll.setLayout(new GridBagLayout());
        sp = new GridBagConstraints();
        paneRight.setLayout(null);
        
        //pane.setBackground(Color.red);
        pane.setOpaque(false);
        
        del.setBackground(Color.WHITE);
        ok.setBackground(Color.WHITE);
        
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);       
        textArea.setBorder(new TitledBorder(""));
        textArea.setFont(new Font("Arial", Font.PLAIN, 50));
        textArea.setBackground(Color.WHITE);       
              
        scrlPane.setWheelScrollingEnabled(true);
        scrlPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        
        paneRight.setBackground(Color.LIGHT_GRAY);
        
        countOfEvents = eventsText.length;
        
        for (int i = 0; i < eventsText.length; ++i) {
            sp.gridx = 0;
            sp.gridy = i;
            sp.gridwidth = 1;
            sp.gridheight = 1;
            sp.weightx = sp.weighty = 1.0;
            //sp.insets = new Insets(0, 0, 0, 0);
            sp.fill = GridBagConstraints.BOTH;

            JButton btn = new JButton(eventsText[i]);
            btn.addActionListener(new dayEventButton_Action());
            btn.setPreferredSize(new Dimension(430, 100));
            btn.setBackground(Color.WHITE);
            paneInScroll.add(btn, sp);
        }        
      
        paneRight.add(textArea);       
        paneRight.add(del);
        paneRight.add(create);
        paneRight.add(ok);
        
        pane.add(scrlPane);
        pane.add(paneRight);
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
            textArea.setText(eventText);
        }
    }
    
    class createEventButton_Action implements ActionListener{        
        public void actionPerformed(ActionEvent e) {
            String eventText = textArea.getText();
            if ("".equals(eventText))
                return;
            JButton btn = new JButton(eventText);
            btn.addActionListener(new dayEventButton_Action());
            btn.setPreferredSize(new Dimension(430, 100));
            btn.setBackground(Color.RED);
            
            sp.gridx = 0;
            sp.gridy = countOfEvents;
            System.out.println(countOfEvents);
            countOfEvents++;
            sp.gridwidth = 1;
            sp.gridheight = 1;
            sp.weightx = sp.weighty = 1.0;
            paneInScroll.setSize(435, paneInScroll.getHeight() + 100);
            sp.fill = GridBagConstraints.BOTH;
            paneInScroll.add(btn, sp);
            paneInScroll.updateUI();
            
            dumper.saveEvent(currentDate, eventText);
        }
    }
    
    class deleteEventButton_Action implements ActionListener{        
        public void actionPerformed(ActionEvent e) {
            if (pressedButton != null) {
                paneInScroll.remove(pressedButton);
                    countOfEvents--;
                    pressedButton = null;
                    paneInScroll.updateUI();
            }
        }
    }
    
    class modifyEventButton_Action implements ActionListener{        
        public void actionPerformed(ActionEvent e) {
            String eventText = textArea.getText();
            if ("".equals(eventText) && pressedButton != null) {
                paneInScroll.remove(pressedButton);
                return;
            }
            pressedButton.setText(eventText);
        }
    }

}
