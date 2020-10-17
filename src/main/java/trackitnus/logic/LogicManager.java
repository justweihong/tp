package trackitnus.logic;

import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import trackitnus.commons.core.GuiSettings;
import trackitnus.commons.core.LogsCenter;
import trackitnus.logic.commands.Command;
import trackitnus.logic.commands.CommandResult;
import trackitnus.logic.commands.exceptions.CommandException;
import trackitnus.logic.parser.TrackIterParser;
import trackitnus.logic.parser.exceptions.ParseException;
import trackitnus.model.Model;
import trackitnus.model.ReadOnlyTrackIter;
import trackitnus.model.contact.Contact;
import trackitnus.model.lesson.Lesson;
import trackitnus.model.module.Module;
import trackitnus.model.task.Task;
import trackitnus.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final TrackIterParser trackIterParser;

    /**
     * Constructs a {@code LogicManager} with the given {@code Model} and {@code Storage}.
     */
    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        trackIterParser = new TrackIterParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;
        Command command = trackIterParser.parseCommand(commandText);
        commandResult = command.execute(model);

        try {
            storage.saveTrackIter(model.getTrackIter());
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }

        return commandResult;
    }

    @Override
    public ReadOnlyTrackIter getTrackIter() {
        return model.getTrackIter();
    }

    @Override
    public ObservableList<Contact> getFilteredContactList() {
        return model.getFilteredContactList();
    }

    @Override
    public ObservableList<Module> getFilteredModuleList() {
        return model.getFilteredModuleList();
    }

    @Override
    public ObservableList<Task> getFilteredTaskList() {
        return model.getFilteredTaskList();
    }

    @Override
    public ObservableList<Lesson> getFilteredLessonList() {
        return model.getFilteredLessonList();
    }

    @Override
    public Path getTrackIterFilePath() {
        return model.getTrackIterFilePath();
    }

    @Override
    public GuiSettings getGuiSettings() {
        return model.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        model.setGuiSettings(guiSettings);
    }
}