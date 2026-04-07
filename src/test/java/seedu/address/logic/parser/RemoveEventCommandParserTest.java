package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.RemoveEventCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class RemoveEventCommandParserTest {

    private final RemoveEventCommandParser parser = new RemoveEventCommandParser();

    @Test
    public void parse_validIndex_returnsRemoveEventCommand() throws Exception {
        RemoveEventCommand command = parser.parse(" 1");
        assertEquals(new RemoveEventCommand(Index.fromOneBased(1)), command);
    }

    @Test
    public void parse_validIndexWithWhitespace_returnsRemoveEventCommand() throws Exception {
        RemoveEventCommand command = parser.parse("   3   ");
        assertEquals(new RemoveEventCommand(Index.fromOneBased(3)), command);
    }

    @Test
    public void parse_emptyArgs_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse(""));
        assertThrows(ParseException.class, () -> parser.parse("   "));
    }

    @Test
    public void parse_nonIntegerArg_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse(" abc"));
    }

    @Test
    public void parse_negativeIndex_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse(" -1"));
    }

    @Test
    public void parse_zeroIndex_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse(" 0"));
    }
}
