package seedu.address.model.application;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class hrEmailTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new hrEmail(null));
    }

    @Test
    public void constructor_invalidHrEmail_throwsIllegalArgumentException() {
        String invalidHrEmail = "";
        assertThrows(IllegalArgumentException.class, () -> new hrEmail(invalidHrEmail));
    }

    @Test
    public void isValidHrEmail() {
        // null hrEmail
        assertThrows(NullPointerException.class, () -> hrEmail.isValidHrEmail(null));

        // blank hrEmail
        assertFalse(hrEmail.isValidHrEmail("")); // empty string
        assertFalse(hrEmail.isValidHrEmail(" ")); // spaces only

        // missing parts
        assertFalse(hrEmail.isValidHrEmail("@example.com")); // missing local part
        assertFalse(hrEmail.isValidHrEmail("peterjackexample.com")); // missing '@' symbol
        assertFalse(hrEmail.isValidHrEmail("peterjack@")); // missing domain role

        // invalid parts
        assertFalse(hrEmail.isValidHrEmail("peterjack@-")); // invalid domain role
        assertFalse(hrEmail.isValidHrEmail("peterjack@exam_ple.com")); // underscore in domain role
        assertFalse(hrEmail.isValidHrEmail("peter jack@example.com")); // spaces in local part
        assertFalse(hrEmail.isValidHrEmail("peterjack@exam ple.com")); // spaces in domain role
        assertFalse(hrEmail.isValidHrEmail(" peterjack@example.com")); // leading space
        assertFalse(hrEmail.isValidHrEmail("peterjack@example.com ")); // trailing space
        assertFalse(hrEmail.isValidHrEmail("peterjack@@example.com")); // double '@' symbol
        assertFalse(hrEmail.isValidHrEmail("peter@jack@example.com")); // '@' symbol in local part
        assertFalse(hrEmail.isValidHrEmail("-peterjack@example.com")); // local part starts with a hyphen
        assertFalse(hrEmail.isValidHrEmail("peterjack-@example.com")); // local part ends with a hyphen
        assertFalse(hrEmail.isValidHrEmail("peter..jack@example.com")); // local part has two consecutive periods
        assertFalse(hrEmail.isValidHrEmail("peterjack@example@com")); // '@' symbol in domain role
        assertFalse(hrEmail.isValidHrEmail("peterjack@.example.com")); // domain role starts with a period
        assertFalse(hrEmail.isValidHrEmail("peterjack@example.com.")); // domain role ends with a period
        assertFalse(hrEmail.isValidHrEmail("peterjack@-example.com")); // domain role starts with a hyphen
        assertFalse(hrEmail.isValidHrEmail("peterjack@example.com-")); // domain role ends with a hyphen
        assertFalse(hrEmail.isValidHrEmail("peterjack@example.c")); // top level domain has less than two chars

        // valid hrEmail
        assertTrue(hrEmail.isValidHrEmail("PeterJack_1190@example.com")); // underscore in local part
        assertTrue(hrEmail.isValidHrEmail("PeterJack.1190@example.com")); // period in local part
        assertTrue(hrEmail.isValidHrEmail("PeterJack+1190@example.com")); // '+' symbol in local part
        assertTrue(hrEmail.isValidHrEmail("PeterJack-1190@example.com")); // hyphen in local part
        assertTrue(hrEmail.isValidHrEmail("a@bc")); // minimal
        assertTrue(hrEmail.isValidHrEmail("test@localhost")); // alphabets only
        assertTrue(hrEmail.isValidHrEmail("123@145")); // numeric local part and domain role
        assertTrue(hrEmail.isValidHrEmail("a1+be.d@example1.com")); // mixture of alphanumeric and special characters
        assertTrue(hrEmail.isValidHrEmail("peter_jack@very-very-very-long-example.com")); // long domain role
        assertTrue(hrEmail.isValidHrEmail("if.you.dream.it_you.can.do.it@example.com")); // long local part
        assertTrue(hrEmail.isValidHrEmail("e1234567@u.nus.edu")); // more than one period in domain
    }

    @Test
    public void equals() {
        hrEmail hrEmail = new hrEmail("valid@hrEmail");

        // same values -> returns true
        assertTrue(hrEmail.equals(new hrEmail("valid@hrEmail")));

        // same object -> returns true
        assertTrue(hrEmail.equals(hrEmail));

        // null -> returns false
        assertFalse(hrEmail.equals(null));

        // different types -> returns false
        assertFalse(hrEmail.equals(5.0f));

        // different values -> returns false
        assertFalse(hrEmail.equals(new hrEmail("other.valid@hrEmail")));
    }
}
