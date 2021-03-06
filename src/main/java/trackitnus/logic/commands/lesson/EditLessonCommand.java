package trackitnus.logic.commands.lesson;

import static java.util.Objects.requireNonNull;
import static trackitnus.commons.core.Messages.MESSAGE_DUPLICATE_LESSON;
import static trackitnus.commons.core.Messages.MESSAGE_MODULE_DOES_NOT_EXIST;
import static trackitnus.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static trackitnus.logic.parser.CliSyntax.PREFIX_CODE;
import static trackitnus.logic.parser.CliSyntax.PREFIX_DATE;
import static trackitnus.logic.parser.CliSyntax.PREFIX_TYPE;

import java.util.List;
import java.util.Optional;

import trackitnus.commons.core.Messages;
import trackitnus.commons.core.index.Index;
import trackitnus.commons.util.CollectionUtil;
import trackitnus.logic.commands.Command;
import trackitnus.logic.commands.CommandResult;
import trackitnus.logic.commands.exceptions.CommandException;
import trackitnus.model.Model;
import trackitnus.model.commons.Address;
import trackitnus.model.commons.Code;
import trackitnus.model.lesson.Lesson;
import trackitnus.model.lesson.LessonDateTime;
import trackitnus.model.lesson.Type;

public final class EditLessonCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = Lesson.TYPE + " " + COMMAND_WORD
        + ": Edits the details of the lesson"
        + " identified by the index number currently displayed on the screen."
        + " At least one of the details has to be specified.\n"
        + "Parameters: INDEX "
        + "[" + PREFIX_CODE + "CODE] "
        + "[" + PREFIX_TYPE + "TYPE] "
        + "[" + PREFIX_DATE + "DATE] "
        + "[" + PREFIX_ADDRESS + "ADDRESS]\n"
        + "Example: " + Lesson.TYPE + " " + COMMAND_WORD + " 1 "
        + PREFIX_ADDRESS + "LT17";

    private final Index index;
    private final EditLessonDescriptor editLessonDescriptor;

    /**
     * Creates a EditLessonCommand to edit the specified {@code Lesson}
     *
     * @param index                index of the lesson to edit in the current FilteredLessonList
     * @param editLessonDescriptor the descriptor of the new lesson
     */
    public EditLessonCommand(Index index, EditLessonDescriptor editLessonDescriptor) {
        requireNonNull(index);
        requireNonNull(editLessonDescriptor);

        this.index = index;
        this.editLessonDescriptor = new EditLessonDescriptor(editLessonDescriptor);
    }

    /**
     * Creates and returns a {@code Lesson} with the details of {@code lessonToEdit}
     * edited with {@code editLessonDescriptor}.
     */
    private static Lesson createEditedLesson(Lesson lessonToEdit, EditLessonDescriptor editLessonDescriptor) {
        assert lessonToEdit != null;

        Code updatedCode = editLessonDescriptor.getCode().orElse(lessonToEdit.getCode());
        Type updatedType = editLessonDescriptor.getType().orElse(lessonToEdit.getType());
        LessonDateTime updatedDate = editLessonDescriptor.getDate().orElse(lessonToEdit.getTime());
        Address updatedAddress = editLessonDescriptor.getAddress().orElse(lessonToEdit.getAddress());

        return new Lesson(updatedCode, updatedType, updatedDate, updatedAddress);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Lesson> lastShownList = model.getFilteredLessonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_LESSON_DISPLAYED_INDEX);
        }

        if (!editLessonDescriptor.isAnyFieldEdited()) {
            throw new CommandException(Messages.MESSAGE_NOT_EDITED);
        }

        Lesson lessonToEdit = lastShownList.get(index.getZeroBased());
        Lesson editedLesson = createEditedLesson(lessonToEdit, editLessonDescriptor);

        if (lessonToEdit.equals(editedLesson)) {
            throw new CommandException(Messages.MESSAGE_LESSON_UNCHANGED);
        }

        if (!lessonToEdit.isSameLesson(editedLesson) && model.hasLesson(editedLesson)) {
            throw new CommandException(MESSAGE_DUPLICATE_LESSON);
        }

        if (!model.hasModule(editedLesson.getCode())) {
            throw new CommandException(MESSAGE_MODULE_DOES_NOT_EXIST);
        }

        model.setLesson(lessonToEdit, editedLesson);
        return new CommandResult(String.format(Messages.MESSAGE_EDIT_LESSON_SUCCESS, editedLesson));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditLessonCommand)) {
            return false;
        }

        // state check
        EditLessonCommand e = (EditLessonCommand) other;
        return index.equals(e.index)
            && editLessonDescriptor.equals(e.editLessonDescriptor);
    }

    /**
     * Stores the details to edit the lesson with. Each non-empty field value will replace the
     * corresponding field value of the lesson.
     */
    public static final class EditLessonDescriptor {
        private Code code;
        private Type type;
        private LessonDateTime date;
        private Address address;

        public EditLessonDescriptor() {
        }

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditLessonDescriptor(EditLessonDescriptor toCopy) {
            setCode(toCopy.code);
            setType(toCopy.type);
            setDate(toCopy.date);
            setAddress(toCopy.address);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(code, type, date, address);
        }

        public Optional<Code> getCode() {
            return Optional.ofNullable(code);
        }

        public void setCode(Code code) {
            this.code = code;
        }

        public Optional<Type> getType() {
            return Optional.ofNullable(type);
        }

        public void setType(Type type) {
            this.type = type;
        }

        public Optional<LessonDateTime> getDate() {
            return Optional.ofNullable(date);
        }

        public void setDate(LessonDateTime date) {
            this.date = date;
        }

        public Optional<Address> getAddress() {
            return Optional.ofNullable(address);
        }

        public void setAddress(Address address) {
            this.address = address;
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditLessonDescriptor)) {
                return false;
            }

            // state check
            EditLessonDescriptor e = (EditLessonDescriptor) other;

            return getCode().equals(e.getCode())
                && getType().equals(e.getType())
                && getDate().equals(e.getDate())
                && getAddress().equals(e.getAddress());
        }
    }
}
