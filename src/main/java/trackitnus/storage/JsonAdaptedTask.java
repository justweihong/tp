package trackitnus.storage;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import trackitnus.commons.core.Messages;
import trackitnus.commons.exceptions.IllegalValueException;
import trackitnus.logic.parser.ParserUtil;
import trackitnus.logic.parser.exceptions.ParseException;
import trackitnus.model.commons.Code;
import trackitnus.model.commons.Name;
import trackitnus.model.task.Task;

/**
 * Jackson-friendly version of {@link Task}.
 */
public class JsonAdaptedTask {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Task's %s field is missing!";

    private final String name;
    private final String date;
    private final String code;
    private final String remark;

    /**
     * Constructs a {@code JsonAdaptedTask} with the given task details.
     */
    @JsonCreator
    public JsonAdaptedTask(@JsonProperty("name") String name, @JsonProperty("date") String date,
                           @JsonProperty("code") String code, @JsonProperty("remark") String remark) {
        this.name = name;
        this.date = date;
        this.code = code;
        this.remark = remark;
    }

    /**
     * Converts a given {@code Task} into this class for Jackson use.
     */
    public JsonAdaptedTask(Task source) {
        name = source.getName().value;
        date = source.getDate().format(ParserUtil.DATE_PATTERN);
        code = source.getCode().isPresent() ? source.getCode().get().code : null;
        remark = source.getRemark();
    }

    /**
     * Converts this Jackson-friendly adapted contact object into the model's {@code Task} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted contact.
     */
    public Task toModelType() throws IllegalValueException {

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (date == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                LocalDate.class.getSimpleName()));
        }

        LocalDate modelDate;
        try {
            modelDate = ParserUtil.parseDate(date);
        } catch (ParseException e) {
            throw new IllegalValueException(Messages.DATE_MESSAGE_CONSTRAINTS);
        }

        if (remark == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Remark"));
        }

        if (code != null && !Code.isValidCode(code)) {
            throw new IllegalValueException(Code.MESSAGE_CONSTRAINTS);
        }
        final Code modelCode = code == null ? null : new Code(code);

        return new Task(modelName, modelDate, modelCode, remark);
    }
}
