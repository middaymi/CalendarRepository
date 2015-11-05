package calendarapplication;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

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
        int saveEvent(CalendarEvent event);
        int findEventByDate(Date date);
    }
    
    // We work with XML-files
    static class XMLDumper implements Dumper {
        File xmlFile;

        @Override
        public int saveEvent(CalendarEvent event) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public int findEventByDate(Date date) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
        
        public java.util.List<CalendarEvent> getRangeOfEvents(Date startDate, Date endDate) {
            // do something...
            return (new ArrayList<>());
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