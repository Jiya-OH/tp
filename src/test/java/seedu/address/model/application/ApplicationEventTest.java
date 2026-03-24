package seedu.address.model.application;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class ApplicationEventTest {

    // Concrete subclass for testing the abstract class
    private static class ConcreteApplicationEvent extends ApplicationEvent {
        public ConcreteApplicationEvent(String location) {
            super(location);
        }
    }

    @Test
    public void getLocation_validLocation_returnsLocation() {
        ApplicationEvent event = new ConcreteApplicationEvent("Singapore");
        assertEquals("Singapore", event.getLocation());
    }

    @Test
    public void getLocation_emptyLocation_returnsEmptyString() {
        ApplicationEvent event = new ConcreteApplicationEvent("");
        assertEquals("", event.getLocation());
    }

    @Test
    public void getLocation_locationWithSpaces_returnsLocation() {
        ApplicationEvent event = new ConcreteApplicationEvent("123 Main Street");
        assertEquals("123 Main Street", event.getLocation());
    }
}
