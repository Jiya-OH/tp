package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ASSESSMENT_LINK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ASSESSMENT_PLATFORM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_LOCATION;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_APPLICATION;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AssessmentCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.application.OnlineAssessment;

public class AssessmentCommandParserTest {

    private final AssessmentCommandParser parser = new AssessmentCommandParser();

    @Test
    public void parse_allFieldsPresent_success() throws Exception {
        AssessmentCommand command = parser.parse(" 1 "
                + PREFIX_EVENT_LOCATION + "home "
                + PREFIX_ASSESSMENT_PLATFORM + "HackerRank "
                + PREFIX_ASSESSMENT_LINK + "www.hackerrank.com");

        AssessmentCommand expectedCommand = new AssessmentCommand(INDEX_FIRST_APPLICATION,
                new OnlineAssessment("home", "HackerRank", "www.hackerrank.com"));

        assertEquals(expectedCommand, command);
    }

    @Test
    public void parse_missingLocation_throwsParseException() {
        assertThrows(ParseException.class,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AssessmentCommand.MESSAGE_USAGE), ()
                        -> parser.parse(" 1 "
                        + PREFIX_ASSESSMENT_PLATFORM + "HackerRank "
                        + PREFIX_ASSESSMENT_LINK + "www.hackerrank.com"));
    }

    @Test
    public void parse_missingPlatform_throwsParseException() {
        assertThrows(ParseException.class,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AssessmentCommand.MESSAGE_USAGE), ()
                        -> parser.parse(" 1 "
                        + PREFIX_EVENT_LOCATION + "home "
                        + PREFIX_ASSESSMENT_LINK + "www.hackerrank.com"));
    }

    @Test
    public void parse_missingLink_throwsParseException() {
        assertThrows(ParseException.class,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AssessmentCommand.MESSAGE_USAGE), ()
                        -> parser.parse(" 1 "
                        + PREFIX_EVENT_LOCATION + "home "
                        + PREFIX_ASSESSMENT_PLATFORM + "HackerRank"));
    }

    @Test
    public void parse_missingIndex_throwsParseException() {
        assertThrows(ParseException.class,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AssessmentCommand.MESSAGE_USAGE), ()
                        -> parser.parse(PREFIX_EVENT_LOCATION + "home "
                        + PREFIX_ASSESSMENT_PLATFORM + "HackerRank "
                        + PREFIX_ASSESSMENT_LINK + "www.hackerrank.com"));
    }

    @Test
    public void parse_invalidIndex_throwsParseException() {
        assertThrows(ParseException.class, ()
                -> parser.parse(" abc "
                + PREFIX_EVENT_LOCATION + "home "
                + PREFIX_ASSESSMENT_PLATFORM + "HackerRank "
                + PREFIX_ASSESSMENT_LINK + "www.hackerrank.com"));
    }

    @Test
    public void parse_zeroIndex_throwsParseException() {
        assertThrows(ParseException.class, ()
                -> parser.parse(" 0 "
                + PREFIX_EVENT_LOCATION + "home "
                + PREFIX_ASSESSMENT_PLATFORM + "HackerRank "
                + PREFIX_ASSESSMENT_LINK + "www.hackerrank.com"));
    }

    @Test
    public void parse_negativeIndex_throwsParseException() {
        assertThrows(ParseException.class, ()
                -> parser.parse(" -1 "
                + PREFIX_EVENT_LOCATION + "home "
                + PREFIX_ASSESSMENT_PLATFORM + "HackerRank "
                + PREFIX_ASSESSMENT_LINK + "www.hackerrank.com"));
    }

    @Test
    public void parse_emptyArgs_throwsParseException() {
        assertThrows(ParseException.class,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AssessmentCommand.MESSAGE_USAGE), ()
                        -> parser.parse(""));
    }

    @Test
    public void parse_duplicatePrefixes_throwsParseException() {
        assertThrows(ParseException.class, ()
                -> parser.parse(" 1 "
                + PREFIX_EVENT_LOCATION + "home "
                + PREFIX_EVENT_LOCATION + "office "
                + PREFIX_ASSESSMENT_PLATFORM + "HackerRank "
                + PREFIX_ASSESSMENT_LINK + "www.hackerrank.com"));
    }

    @Test
    public void parse_extraWhitespace_success() throws Exception {
        AssessmentCommand command = parser.parse("   1   "
                + PREFIX_EVENT_LOCATION + "home "
                + PREFIX_ASSESSMENT_PLATFORM + "HackerRank "
                + PREFIX_ASSESSMENT_LINK + "www.hackerrank.com");

        AssessmentCommand expectedCommand = new AssessmentCommand(INDEX_FIRST_APPLICATION,
                new OnlineAssessment("home", "HackerRank", "www.hackerrank.com"));

        assertEquals(expectedCommand, command);
    }
}
