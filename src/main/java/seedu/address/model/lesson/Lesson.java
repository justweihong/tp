package seedu.address.model.lesson;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDate;
import java.util.Objects;


/**
 * Represents a Lesson in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Lesson {
    public static final String TYPE = "L";

    private final Type type;
    private final LocalDate time;
    private final double weightage;

    /**
     * Every field must be present and not null.
     *
     * @param type
     * @param time
     * @param weightage
     */
    public Lesson(Type type, LocalDate time, double weightage) {
        requireAllNonNull(type, time, weightage);
        this.time = time;
        this.weightage = weightage;
        this.type = type;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Lesson)) {
            return false;
        }

        Lesson otherLesson = (Lesson) other;
        return otherLesson.type.equals(type)
            && otherLesson.time.equals(time)
            && (otherLesson.weightage == weightage);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(type, time, weightage);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        // TODO: Implement this toString
        return "Lesson's toString hasn't been implemented";
    }

    public Type getType() {
        return type;
    }

    public LocalDate getTime() {
        return time;
    }

    public double getWeightage() {
        return weightage;
    }

    /**
     * Returns true if the two lessons are the same
     * This methods is here for to act as a compatibility layer for UniqueModuleList
     */
    public boolean isSameLesson(Lesson lesson) {
        return this.equals(lesson);
    }
}
