package seedu.address.model.application;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class OnlineAssessmentTest {

    @Test
    public void constructor_allFields_success() {
        OnlineAssessment oa = new OnlineAssessment("home", "HackerRank", "www.hackerrank.com", "bring notes");
        assertEquals("home", oa.getLocation());
        assertEquals("HackerRank", oa.getPlatform());
        assertEquals("www.hackerrank.com", oa.getLink());
        assertEquals("bring notes", oa.getNotes());
    }

    @Test
    public void constructor_withoutNotes_defaultNotesSet() {
        OnlineAssessment oa = new OnlineAssessment("home", "HackerRank", "www.hackerrank.com");
        assertEquals("home", oa.getLocation());
        assertEquals("HackerRank", oa.getPlatform());
        assertEquals("www.hackerrank.com", oa.getLink());
        assertEquals(OnlineAssessment.EMPTY_NOTES_VALUE, oa.getNotes());
    }

    @Test
    public void constructor_emptyLocation_success() {
        OnlineAssessment oa = new OnlineAssessment("", "LeetCode", "www.leetcode.com");
        assertEquals("", oa.getLocation());
    }

    @Test
    public void getLocation_returnsCorrectLocation() {
        OnlineAssessment oa = new OnlineAssessment("Singapore", "Codility", "www.codility.com");
        assertEquals("Singapore", oa.getLocation());
    }

    @Test
    public void getPlatform_returnsCorrectPlatform() {
        OnlineAssessment oa = new OnlineAssessment("home", "Codility", "www.codility.com");
        assertEquals("Codility", oa.getPlatform());
    }

    @Test
    public void getLink_returnsCorrectLink() {
        OnlineAssessment oa = new OnlineAssessment("home", "Codility", "www.codility.com");
        assertEquals("www.codility.com", oa.getLink());
    }

    @Test
    public void getNotes_customNotes_returnsCorrectNotes() {
        OnlineAssessment oa = new OnlineAssessment("home", "HackerRank", "www.hackerrank.com", "custom notes");
        assertEquals("custom notes", oa.getNotes());
    }

    @Test
    public void emptyNotesValue_isCorrectConstant() {
        assertEquals("No notes set", OnlineAssessment.EMPTY_NOTES_VALUE);
    }

    @Test
    public void isInstanceOfApplicationEvent() {
        OnlineAssessment oa = new OnlineAssessment("home", "HackerRank", "www.hackerrank.com");
        assert(oa instanceof ApplicationEvent);
    }
}
