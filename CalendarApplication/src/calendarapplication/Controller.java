package calendarapplication;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
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
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
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
        void removeEvent(String date, String text);
        void modifyEvent(String date, String oldText, String newText);
        ArrayList<String> findEventsByDate(String date);
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
        
            } catch (ParserConfigurationException ex) {
                createDB(path);
            } catch (SAXException ex) {
                createDB(path);
            } catch (IOException ex) {
                createDB(path);
            }
        }
        
        private void createDB(String path){       
            transferChanges(xmlFile);
        }
        
        private void transferChanges(File f) {
            try {
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                DOMSource source = new DOMSource(doc);
                StreamResult result = new StreamResult(f);
                transformer.transform(source, result);
            } catch (TransformerConfigurationException ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            } catch (TransformerException ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        //for save event
        @Override
        public void saveEvent(String date, String text) {
            Node node = doc.getDocumentElement().getElementsByTagName("d" + date).item(0);
            if (node == null) {
                Element rootElem = doc.getDocumentElement();
                Element dateElem = doc.createElement("d" + date);
                Element eventElem = doc.createElement("event");
                eventElem.appendChild(doc.createTextNode(text));
                dateElem.appendChild(eventElem);
                rootElem.appendChild(dateElem);
            } else {
                Element dateElement = null;
                if (node.getNodeType() == Node.ELEMENT_NODE)
                    dateElement = (Element) node;
                NodeList eventsElements = dateElement.getElementsByTagName("event");
                for (int i = 0; eventsElements.item(i) != null; ++i)
                    if (eventsElements.item(i).getTextContent().equals(text))
                        return;
                Element eventElem = doc.createElement("event");
                eventElem.appendChild(doc.createTextNode(text));
                dateElement.appendChild(eventElem);
            }

            transferChanges(xmlFile);
        }
        //find event
        @Override
        public ArrayList<String> findEventsByDate(String date) {
            ArrayList<String> eventsArray = new ArrayList<>();
            Node node = doc.getDocumentElement().getElementsByTagName("d" + date).item(0);
            if (node == null) {
                return eventsArray;
            } else {
                Element dateElement = null;
                if (node.getNodeType() == Node.ELEMENT_NODE)
                    dateElement = (Element) node;
                NodeList eventsElements = dateElement.getElementsByTagName("event");
                for (int i = 0; eventsElements.item(i) != null; ++i)
                    eventsArray.add(eventsElements.item(i).getTextContent());
            }            
            return eventsArray;
        }
        //delete element
        @Override
        public void removeEvent(String date, String text) {
            Node node = doc.getDocumentElement().getElementsByTagName("d" + date).item(0);
            if (node != null) {
                Element dateElement = null;
                if (node.getNodeType() == Node.ELEMENT_NODE)
                    dateElement = (Element) node;
                NodeList eventsElements = dateElement.getElementsByTagName("event");
                for (int i = 0; eventsElements.item(i) != null; ++i)
                    if (eventsElements.item(i).getTextContent().equals(text)) {
                        dateElement.removeChild(eventsElements.item(i));
                    }
            }
            transferChanges(xmlFile);
        }
        //for change event
        @Override
        public void modifyEvent(String date, String oldText, String newText) {
            Node node = doc.getDocumentElement().getElementsByTagName("d" + date).item(0);
            if (node != null) {
                Element dateElement = null;
                if (node.getNodeType() == Node.ELEMENT_NODE)
                    dateElement = (Element) node;
                NodeList eventNodes = dateElement.getChildNodes();
                for (int i = 0; eventNodes.item(i) != null; ++i)
                    if (eventNodes.item(i).getTextContent().equals(newText)) {
                        return;
                    }
                for (int i = 0; eventNodes.item(i) != null; ++i)
                    if (eventNodes.item(i).getTextContent().equals(oldText)) {
                        Element eventElem = doc.createElement("event");
                        eventElem.appendChild(doc.createTextNode(newText));
                        dateElement.replaceChild(eventElem, eventNodes.item(i));
                    }
            }
            transferChanges(xmlFile);
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
    }    
}
