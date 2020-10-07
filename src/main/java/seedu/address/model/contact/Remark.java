package seedu.address.model.contact;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

public class Remark {
    public final String value;

    public static final String MESSAGE_CONSTRAINTS = "Remarks can be anything. No constraints";

    /**
     * Constructs a {@code Remark}.
     *
     * @param remark A valid remark.
     */
    public Remark(String remark) {
        requireNonNull(remark);
//        checkArgument(isValidRemark(phone), MESSAGE_CONSTRAINTS);
        value = remark;
    }

    public static boolean isValidRemark(String remark) {
        return true;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Phone // instanceof handles nulls
                && value.equals(((Phone) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
