package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.RemoveEventCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new {@link RemoveEventCommand} object.
 */
public class RemoveEventCommandParser implements Parser<RemoveEventCommand> {

    @Override
    public RemoveEventCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();

        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveEventCommand.MESSAGE_USAGE));
        }

        Index index = ParserUtil.parseIndex(trimmedArgs);
        return new RemoveEventCommand(index);
    }
}
