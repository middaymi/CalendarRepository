package calendarapplication;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

public class Controller {
    enum EventType {BIRTHDAY, CASUAL}
    static class CalendarEvent {
        Date eventDate;
        EventType eventType;
        private String ID;
        private String description;
        
        CalendarEvent(String eventDescription) {
            description = eventDescription;
            ID = ID(eventDescription);
        }
        
        String getID() { return ID; }
        
        void setDescription(String newDescription) {
            description = newDescription;
            ID = ID(newDescription);
        }
        
        String getDescription() {
            return description;
        }
        
        private String ID(String text) {
            Long tmp = (long) text.length() + text.charAt(0) + text.charAt(text.length() - 1);
            return eventDate.toString() + tmp.toString();
        }
    }

    // Working with database of events (XML-file, DB etc.)
    interface Dumper {
        void saveEvent(String date, String text);
        String[] findEventsByDate(String date);
    }
    
    // We work with XML-files
    static class XMLDumper implements Dumper {
        File xmlFile;
        DocumentBuilderFactory dbFactory;
        DocumentBuilder dBuilder;
        Document doc;

        public XMLDumper(String path) {
            try {
                xmlFile = new File(path);
                dbFactory = DocumentBuilderFactory.newInstance();
                dBuilder = dbFactory.newDocumentBuilder();
                doc = dBuilder.parse(xmlFile);
                doc.getDocumentElement().normalize();

            //this.title = doc.getDocumentElement().getElementsByTagName("application-name").item(0).getTextContent();
            //this.isAdditionalActive = (Integer.parseInt(doc.getDocumentElement().getElementsByTagName("additional-info").item(0).getTextContent()) == 1) ? true : false;
            //this.addInfoFileName = doc.getDocumentElement().getElementsByTagName("file-name").item(0).getTextContent();           	
        
            } catch (ParserConfigurationException ex) {
                createDB(path);
            } catch (SAXException ex) {
                createDB(path);
            } catch (IOException ex) {
                createDB(path);
            }
        }
        
        private void createDB(String path){       
            try {
                // write the content into xml file
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                DOMSource source = new DOMSource(doc);
                StreamResult result = new StreamResult(new File(path));          
                transformer.transform(source, result);
            } catch (TransformerConfigurationException ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            } catch (TransformerException ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        @Override
        public void saveEvent(String date, String text) {

            try {
                // root element
                Element dateElement = null;
                doc = dBuilder.parse(xmlFile);
                doc.getDocumentElement().normalize();
                String event = doc.getDocumentElement().getElementsByTagName("d" + date).item(0).getTextContent();
                if (event == null) {
                    doc = dBuilder.newDocument();
                    dateElement = doc.createElement("d"+date);
                }
                doc.appendChild(dateElement);
                dateElement.appendChild(doc.createTextNode(text));

                // write the content into xml file
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                DOMSource source = new DOMSource(doc);
                StreamResult result = new StreamResult(xmlFile);
                
                transformer.transform(source, result);
            } catch (TransformerConfigurationException ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            } catch (TransformerException ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SAXException ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        @Override
        public String[] findEventsByDate(String date) {
            String[] events = {"123", "456", "789", "101", "211",
                               "123", "456", "789", "101", "211",
                               "123", "456", "789", "101", "211"};
            return events;
        }
    }

    static class CalendarEventController {
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
                if (delEvent.getID().equals(i.getID())) {
                    EventList.remove(i);
                    break;
                }
            }
        }

        /*void printEvents() {
            for (CalendarEvent i: EventList) {
                System.out.println( i.getDescription() );
            }
        }*/
    }
    
}

//for check in main
//        CalendarEventController ctrl = new CalendarEventController();
//        CalendarEvent testEvent = new CalendarEvent("Trololo");
//        CalendarEvent testEvent2 = new CalendarEvent("Trololo2");
        
//        ctrl.addEvent(testEvent);
//        ctrl.addEvent(testEvent2);
//        ctrl.printEvents();
//        ctrl.deleteEvent(testEvent);
//        ctrl.printEvents();