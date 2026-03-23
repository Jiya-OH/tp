package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalApplications.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.application.Application;
import seedu.address.testutil.ApplicationBuilder;

/**
 * Integration testing of SortCommand (interaction with Model).
 */
public class SortCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_sortByAlphabet_success() throws CommandException {
        SortCommand sortCommand = new SortCommand("alphabet");
        String expectedMessage = String.format(SortCommand.MESSAGE_SUCCESS, "alphabet");

        expectedModel.updateSortedApplicationList((a1, a2) ->
                a1.getRole().roleName.compareToIgnoreCase(a2.getRole().roleName));

        CommandResult result = sortCommand.execute(model);

        assertEquals(expectedMessage, result.getFeedbackToUser());
        assertEquals(expectedModel.getFilteredApplicationList(), model.getFilteredApplicationList());
    }

    @Test
    public void execute_sortByTime_success() throws CommandException { // 添加 throws
        SortCommand sortCommand = new SortCommand("time");
        String expectedMessage = String.format(SortCommand.MESSAGE_SUCCESS, "time");

        expectedModel.updateSortedApplicationList((a1, a2) ->
                a1.getDeadline().compareTo(a2.getDeadline()));

        CommandResult result = sortCommand.execute(model);

        assertEquals(expectedMessage, result.getFeedbackToUser());
        assertEquals(expectedModel.getFilteredApplicationList(), model.getFilteredApplicationList());
    }

    @Test
    public void execute_invalidCriteria_throwsCommandException() {
        SortCommand sortCommand = new SortCommand("invalid");

        CommandException exception = assertThrows(CommandException.class, () -> sortCommand.execute(model));
        assertEquals("Unknown criteria! Use 'time' or 'alphabet'.", exception.getMessage());
    }

    @Test
    public void equals_caseInsensitive() {
        assertTrue(new SortCommand("alphabet").equals(new SortCommand("ALPHABET")));
        assertFalse(new SortCommand("alphabet").equals(new SortCommand("time")));
        assertFalse(new SortCommand("time").equals(null));
        assertEquals(new SortCommand("time").hashCode(), new SortCommand("TIME").hashCode());
    }

    @Test
    public void execute_sortByTime_sortsByDeadlineAndEmptyLast() throws CommandException {
        Application early = new ApplicationBuilder()
                .withRole("Early Engineer")
                .withCompanyName("CoA")
                .withCompanyLocation("Singapore")
                .withPhone("11111111")
                .withHrEmail("early@example.com")
                .withDeadline("2026-01-01")
                .build();

        Application late = new ApplicationBuilder()
                .withRole("Late Engineer")
                .withCompanyName("CoB")
                .withCompanyLocation("Singapore")
                .withPhone("22222222")
                .withHrEmail("late@example.com")
                .withDeadline("2026-12-31")
                .build();

        Application emptyDeadline = new ApplicationBuilder()
                .withRole("Empty Deadline Engineer")
                .withCompanyName("CoC")
                .withCompanyLocation("Singapore")
                .withPhone("33333333")
                .withHrEmail("empty@example.com")
                .build();

        AddressBook addressBook = new AddressBook();
        addressBook.addApplication(early);
        addressBook.addApplication(late);
        addressBook.addApplication(emptyDeadline);

        AddressBook expectedAddressBook = new AddressBook();
        expectedAddressBook.addApplication(early);
        expectedAddressBook.addApplication(late);
        expectedAddressBook.addApplication(emptyDeadline);

        Model sortModel = new ModelManager(addressBook, new UserPrefs());
        Model expectedModel = new ModelManager(expectedAddressBook, new UserPrefs());

        expectedModel.updateSortedApplicationList((a1, a2) -> a1.getDeadline().compareTo(a2.getDeadline()));

        SortCommand sortCommand = new SortCommand("time");
        String expectedMessage = String.format(SortCommand.MESSAGE_SUCCESS, "time");
        CommandResult result = sortCommand.execute(sortModel);

        assertEquals(expectedMessage, result.getFeedbackToUser());
        assertEquals(expectedModel.getFilteredApplicationList(), sortModel.getFilteredApplicationList());
    }
}
