package trackitnus.commons.core;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_CONTACT_DISPLAYED_INDEX = "The contact index provided is invalid";
    public static final String MESSAGE_INVALID_TASK_DISPLAYED_INDEX = "The task index provided is invalid";
    public static final String MESSAGE_INVALID_TYPE = "The type provided is invalid";
    public static final String MESSAGE_INVALID_TAB_VALUE = "Invalid tab values.";
    public static final String MESSAGE_CONTACTS_LISTED_OVERVIEW = "%1$d contacts listed!";
    public static final String MESSAGE_LESSON_DOES_NOT_EXIST = "There is no such lesson";
    public static final String MESSAGE_INVALID_LESSON_DISPLAYED_INDEX = "The lesson index provided is invalid";

    public static final String MESSAGE_MODULE_DOES_NOT_EXIST = "The module specified in the command doesn't exist.";
    public static final String MESSAGE_TASK_DOES_NOT_EXIST = "There is no such task";

    public static final String MESSAGE_DUPLICATE_CONTACT = "This contact already exists";
    public static final String MESSAGE_DUPLICATE_LESSON = "This lesson already exists";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_MODULE = "This module already exists";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists";
    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";
    public static final String DATE_MESSAGE_CONSTRAINTS = "Date should be in the format dd/MM/yyyy or dd/MM/yyyy hh:mm";
    public static final String WEIGHTAGE_MESSAGE_CONSTRAINTS = "Weightage should be in the"
        + " form of a floating point number";
}
