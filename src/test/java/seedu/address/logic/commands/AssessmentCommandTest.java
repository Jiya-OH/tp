package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalApplications.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_APPLICATION;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_APPLICATION;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.application.Application;
import seedu.address.model.application.ApplicationEvent;
import seedu.address.model.application.OnlineAssessment;
import seedu.address.testutil.ApplicationBuilder;

public class AssessmentCommandTest {

    private static final ApplicationEvent VALID_EVENT = new OnlineAssessment(
            "home", "HackerRank", "www.hackerrank.com");
    private static final ApplicationEvent VALID_EVENT_WITH_NOTES = new OnlineAssessment(
            "office", "LeetCode", "www.leetcode.com", "bring ID");

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Application applicationToEdit = model.getFilteredApplicationList()
                .get(INDEX_FIRST_APPLICATION.getZeroBased());

        AssessmentCommand assessmentCommand = new AssessmentCommand(INDEX_FIRST_APPLICATION, VALID_EVENT);

        Application editedApplication = new ApplicationBuilder(applicationToEdit)
                .withApplicationEvent(VALID_EVENT)
                .build();

        String expectedMessage = "Online Assessment updated for: " + editedApplication.getCompany();

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setApplication(applicationToEdit, editedApplication);

        assertCommandSuccess(assessmentCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validIndexWithNotes_success() {
        Application applicationToEdit = model.getFilteredApplicationList()
                .get(INDEX_FIRST_APPLICATION.getZeroBased());

        AssessmentCommand assessmentCommand = new AssessmentCommand(
                INDEX_FIRST_APPLICATION, VALID_EVENT_WITH_NOTES);

        Application editedApplication = new ApplicationBuilder(applicationToEdit)
                .withApplicationEvent(VALID_EVENT_WITH_NOTES)
                .build();

        String expectedMessage = "Online Assessment updated for: " + editedApplication.getCompany();

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setApplication(applicationToEdit, editedApplication);

        assertCommandSuccess(assessmentCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredApplicationList().size() + 1);
        AssessmentCommand assessmentCommand = new AssessmentCommand(outOfBoundIndex, VALID_EVENT);

        assertCommandFailure(assessmentCommand, model, Messages.MESSAGE_INVALID_APPLICATION_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validSecondIndex_success() {
        Application applicationToEdit = model.getFilteredApplicationList()
                .get(INDEX_SECOND_APPLICATION.getZeroBased());

        AssessmentCommand assessmentCommand = new AssessmentCommand(INDEX_SECOND_APPLICATION, VALID_EVENT);

        Application editedApplication = new ApplicationBuilder(applicationToEdit)
                .withApplicationEvent(VALID_EVENT)
                .build();

        String expectedMessage = "Online Assessment updated for: " + editedApplication.getCompany();

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setApplication(applicationToEdit, editedApplication);

        assertCommandSuccess(assessmentCommand, model, expectedMessage, expectedModel);
    }
}
