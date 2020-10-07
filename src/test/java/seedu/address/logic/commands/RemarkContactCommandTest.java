package seedu.address.logic.commands;

import org.junit.jupiter.api.Test;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.contact.RemarkContactCommand;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.TrackIter;
import seedu.address.model.UserPrefs;
import seedu.address.model.contact.Contact;
import seedu.address.model.contact.Remark;
import seedu.address.testutil.PersonBuilder;

import static org.junit.jupiter.api.Assertions.*;
import static seedu.address.logic.commands.CommandTestUtil.*;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

class RemarkContactCommandTest {

    private static final String REMARK_STUB = "Some remark";

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_addRemarkUnfilteredList_success() {
        Contact firstPerson = model.getFilteredContactList().get(INDEX_FIRST_PERSON.getZeroBased());
        Contact editedPerson = new PersonBuilder(firstPerson).withRemark(REMARK_STUB).build();

        RemarkContactCommand remarkCommand = new RemarkContactCommand(INDEX_FIRST_PERSON, new Remark(editedPerson.getRemark().value));

        String expectedMessage = String.format(RemarkContactCommand.MESSAGE_ADD_REMARK_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new TrackIter(model.getTrackIter()), new UserPrefs());
        expectedModel.setContact(firstPerson, editedPerson);

        assertCommandSuccess(remarkCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_deleteRemarkUnfilteredList_success() {
        Contact firstPerson = model.getFilteredContactList().get(INDEX_FIRST_PERSON.getZeroBased());
        Contact editedPerson = new PersonBuilder(firstPerson).withRemark("").build();

        RemarkContactCommand remarkCommand = new RemarkContactCommand(INDEX_FIRST_PERSON,
                new Remark(editedPerson.getRemark().toString()));

        String expectedMessage = String.format(RemarkContactCommand.MESSAGE_DELETE_REMARK_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new TrackIter(model.getTrackIter()), new UserPrefs());
        expectedModel.setContact(firstPerson, editedPerson);

        assertCommandSuccess(remarkCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Contact firstPerson = model.getFilteredContactList().get(INDEX_FIRST_PERSON.getZeroBased());
        Contact editedPerson = new PersonBuilder(model.getFilteredContactList().get(INDEX_FIRST_PERSON.getZeroBased()))
                .withRemark(REMARK_STUB).build();

        RemarkContactCommand remarkCommand = new RemarkContactCommand(INDEX_FIRST_PERSON, new Remark(editedPerson.getRemark().value));

        String expectedMessage = String.format(RemarkContactCommand.MESSAGE_ADD_REMARK_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new TrackIter(model.getTrackIter()), new UserPrefs());
        expectedModel.setContact(firstPerson, editedPerson);

        assertCommandSuccess(remarkCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredContactList().size() + 1);
        RemarkContactCommand remarkCommand = new RemarkContactCommand(outOfBoundIndex, new Remark(VALID_REMARK_BOB));

        assertCommandFailure(remarkCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getTrackIter().getContactList().size());

        RemarkContactCommand remarkCommand = new RemarkContactCommand(outOfBoundIndex, new Remark(VALID_REMARK_BOB));
    }
}