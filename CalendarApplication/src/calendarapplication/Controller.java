package calendarapplication;

import java.util.ArrayList;

public class Controller {
    static class CalendarEvent {
        private long ID;
        private String description;
        
        CalendarEvent(String eventDescription) {
            description = eventDescription;
            ID = ID(eventDescription);
        }
        
        long getID() { return ID; }
        
        void setDescription(String newDescription) {
            description = newDescription;
            ID = ID(newDescription);
        }
        
        String getDescription() {
            return description;
        }
        
        private long ID(String text) {
            return text.length() + text.charAt(0) + text.charAt(text.length() - 1);
        }
    }

    interface Dumper {
        int saveEvent();
        int findEventByDate();
    }
            
    static class XMLDumper implements Dumper {

        @Override
        public int saveEvent() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public int findEventByDate() {
            throw new UnsupportedOperationException("Not supported yet.");
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
                if (delEvent.getID() == i.getID()) {
                    EventList.remove(i);
                    break;
                }
            }
        }

        void printEvents() {
            for (CalendarEvent i: EventList) {
                System.out.println( i.getDescription() );
            }
        }
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