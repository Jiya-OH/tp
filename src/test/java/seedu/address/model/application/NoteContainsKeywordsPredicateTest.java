package seedu.address.model.application;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalApplications.GOOGLE_SWE;
import static seedu.address.testutil.TypicalApplications.META_DA;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.ApplicationBuilder;

public class NoteContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        NoteContainsKeywordsPredicate firstPredicate =
                new NoteContainsKeywordsPredicate(Collections.singletonList("follow"));
        NoteContainsKeywordsPredicate secondPredicate =
                new NoteContainsKeywordsPredicate(Collections.singletonList("recruiter"));

        // Same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // Same values -> returns true
        assertTrue(firstPredicate.equals(new NoteContainsKeywordsPredicate(Collections.singletonList("follow"))));

        // Different type -> returns false
        assertFalse(firstPredicate.equals(1));

        // Null -> returns false
        assertFalse(firstPredicate.equals(null));

        // Different predicate -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_noteContainsKeywords_returnsTrue() {
        // EP: note contains at least one valid keyword
        NoteContainsKeywordsPredicate predicate =
                new NoteContainsKeywordsPredicate(Arrays.asList("follow", "recruiter"));

        Application applicationWithFollow = new ApplicationBuilder(GOOGLE_SWE)
                .withNote("Follow up next Monday")
                .build();

        Application applicationWithRecruiter = new ApplicationBuilder(META_DA)
                .withNote("Met recruiter at career fair")
                .build();

        assertTrue(predicate.test(applicationWithFollow));
        assertTrue(predicate.test(applicationWithRecruiter));
    }

    @Test
    public void test_noteDoesNotContainKeywords_returnsFalse() {
        // EP: note contains none of the keywords
        NoteContainsKeywordsPredicate predicate =
                new NoteContainsKeywordsPredicate(Arrays.asList("follow", "recruiter"));

        Application application = new ApplicationBuilder(GOOGLE_SWE)
                .withNote("Send thank you email")
                .build();

        assertFalse(predicate.test(application));
    }

    @Test
    public void test_emptyNote_returnsFalse() {
        // EP: empty note
        // Boundary value: empty string
        NoteContainsKeywordsPredicate predicate =
                new NoteContainsKeywordsPredicate(Collections.singletonList("follow"));

        Application application = new ApplicationBuilder(GOOGLE_SWE)
                .withNote("")
                .build();

        assertFalse(predicate.test(application));
    }

    @Test
    public void test_multipleKeywordsOneMatch_returnsTrue() {
        // EP: multiple keywords, one matching is enough
        NoteContainsKeywordsPredicate predicate =
                new NoteContainsKeywordsPredicate(Arrays.asList("follow", "recruiter", "offer"));

        Application application = new ApplicationBuilder(GOOGLE_SWE)
                .withNote("Waiting for recruiter reply")
                .build();

        assertTrue(predicate.test(application));
    }
}
