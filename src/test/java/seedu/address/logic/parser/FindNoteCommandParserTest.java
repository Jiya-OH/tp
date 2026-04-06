package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindNoteCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.application.NoteContainsKeywordsPredicate;

public class FindNoteCommandParserTest {

    private final FindNoteCommandParser parser = new FindNoteCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        // EP: empty / blank input
        // Boundary value: whitespace-only input
        assertThrows(ParseException.class,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindNoteCommand.MESSAGE_USAGE), ()
                        -> parser.parse("   "));
    }

    @Test
    public void parse_validArgsSingleKeyword_returnsFindNoteCommand() throws Exception {
        // EP: valid input with one keyword
        FindNoteCommand expectedFindNoteCommand =
                new FindNoteCommand(new NoteContainsKeywordsPredicate(Collections.singletonList("follow")));

        assertEquals(expectedFindNoteCommand, parser.parse(" follow"));
    }

    @Test
    public void parse_validArgsMultipleKeywords_returnsFindNoteCommand() throws Exception {
        // EP: valid input with multiple keywords
        FindNoteCommand expectedFindNoteCommand =
                new FindNoteCommand(new NoteContainsKeywordsPredicate(Arrays.asList("follow", "recruiter")));

        assertEquals(expectedFindNoteCommand, parser.parse(" follow recruiter"));
    }

    @Test
    public void parse_validArgsWithExtraSpaces_returnsFindNoteCommand() throws Exception {
        // EP: valid input with irregular spacing
        FindNoteCommand expectedFindNoteCommand =
                new FindNoteCommand(new NoteContainsKeywordsPredicate(Arrays.asList("follow", "recruiter")));

        assertEquals(expectedFindNoteCommand, parser.parse("   follow    recruiter   "));
    }
}
