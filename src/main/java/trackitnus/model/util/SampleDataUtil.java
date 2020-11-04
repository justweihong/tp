package trackitnus.model.util;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import trackitnus.logic.parser.ParserUtil;
import trackitnus.model.ReadOnlyTrackIter;
import trackitnus.model.TrackIter;
import trackitnus.model.commons.Address;
import trackitnus.model.commons.Code;
import trackitnus.model.commons.Name;
import trackitnus.model.contact.Contact;
import trackitnus.model.contact.Email;
import trackitnus.model.contact.Phone;
import trackitnus.model.lesson.DayOfWeek;
import trackitnus.model.lesson.Lesson;
import trackitnus.model.lesson.LessonDateTime;
import trackitnus.model.lesson.Type;
import trackitnus.model.module.Module;
import trackitnus.model.tag.Tag;
import trackitnus.model.task.Task;

/**
 * Contains utility methods for populating {@code TrackIter} with sample data.
 */
public class SampleDataUtil {
    public static Contact[] getSampleContacts() {
        return new Contact[]{
            new Contact(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@gmail.com"),
                getTagSet("friends", "MA1101R")),
            new Contact(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@gmail.com"),
                getTagSet("colleagues", "friends")),
            new Contact(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@gmail.com"),
                getTagSet("neighbours")),
            new Contact(new Name("David Li"), new Phone("91031282"), new Email("lidavid@gmail.com"),
                getTagSet("family", "CS2100")),
            new Contact(new Name("Uncle Soo"), new Phone("65162100"), new Email("dcssooyj@nus.edu.sg"),
                getTagSet("Professor", "CS2100")),
            new Contact(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@gmail.com"),
                getTagSet("classmates", "CS2103T")),
            new Contact(new Name("Jeffry Lum"), new Phone("91122334"), new Email("jeffry@u.nus.edu"),
                getTagSet("TA", "CS2103T")),
            new Contact(new Name("Prof Damith"), new Phone("65162103"), new Email("dcsdcr@nus.edu.sg"),
                getTagSet("Professor", "CS2103T")),
            new Contact(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@gmail.com"),
                getTagSet("colleagues")),
        };
    }

    public static Module[] getSampleModules() {
        return new Module[]{
            new Module(new Code("CS2100"), new Name("Computer Organisation")),
            new Module(new Code("CS2103T"), new Name("Software Engineering")),
            new Module(new Code("MA1101R"), new Name("Linear Algebra")),
            new Module(new Code("GER1000H"), new Name("Quantitative Reasoning")),
        };
    }

    public static Lesson[] getSampleLessons() {
        return new Lesson[]{
            new Lesson(new Code("CS2100"), Type.LEC, new LessonDateTime(DayOfWeek.Tue, LocalTime.NOON.plusHours(4),
                LocalTime.NOON.plusHours(6)), new Address("E-learning")),
            new Lesson(new Code("CS2100"), Type.LAB, new LessonDateTime(DayOfWeek.Mon, LocalTime.MIDNIGHT.plusHours(9),
                LocalTime.MIDNIGHT.plusHours(10)), new Address("E-learning")),
            new Lesson(new Code("CS2103T"), Type.LEC, new LessonDateTime(DayOfWeek.Wed, LocalTime.NOON.plusHours(2),
                LocalTime.NOON.plusHours(4)), new Address("E-learning")),
            new Lesson(new Code("CS2103T"), Type.TUT, new LessonDateTime(DayOfWeek.Thu, LocalTime.NOON.plusHours(2),
                LocalTime.NOON.plusHours(3)), new Address("E-learning")),
            new Lesson(new Code("MA1101R"), Type.LEC, new LessonDateTime(DayOfWeek.Thu, LocalTime.NOON.plusHours(4),
                LocalTime.NOON.plusHours(6)), new Address("LT27")),
            new Lesson(new Code("MA1101R"), Type.SEC, new LessonDateTime(DayOfWeek.Wed, LocalTime.NOON,
                LocalTime.NOON.plusHours(1)), new Address("E-learning")),
            new Lesson(new Code("GER1000H"), Type.LEC, new LessonDateTime(DayOfWeek.Tue, LocalTime.NOON,
                LocalTime.NOON.plusHours(2)), new Address("E-learning")),
            new Lesson(new Code("GER1000H"), Type.SEC, new LessonDateTime(DayOfWeek.Fri, LocalTime.NOON.plusHours(2),
                LocalTime.NOON.plusHours(4)), new Address("PGPH-FR4")),
        };
    }

    public static Task[] getSampleTasks() {
        return new Task[]{
            new Task(new Name("Plan for Alex's birthday"), ParserUtil.parseValidDate("12/12/2020"),
                null, "Buy a cake"),
            new Task(new Name("Buy Mooncakes for Mum"), LocalDate.now().plusDays(3), null, ""),
            new Task(new Name("Do Tutorial"), LocalDate.now().plusDays(5),
                new Code("CS2100"), ""),
            new Task(new Name("Catch up on Pipelining webcast"), LocalDate.now().plusDays(-2),
                new Code("CS2100"), "focus on latency and data forwarding"),
            new Task(new Name("Work on the final report"), LocalDate.now().plusDays(0),
                new Code("GER1000H"), "min 500 words"),
            new Task(new Name("Study for Final Exam"), ParserUtil.parseValidDate("05/11/2020"),
                new Code("MA1101R"), "Focus on Diagonalisation"),
            new Task(new Name("Prepare for v1.4 Demo"), LocalDate.now().plusDays(0),
                new Code("CS2103T"), "Ensure app runs smoothly"),
            new Task(new Name("Practical Exam on week 13"), LocalDate.now().plusDays(5),
                new Code("CS2103T"), "remember to upgrade to the latest CATCHER")
        };
    }

    public static ReadOnlyTrackIter getSampleTrackIter() {
        TrackIter sampleTrackIter = new TrackIter();
        for (Module sampleModule : getSampleModules()) {
            sampleTrackIter.addModule(sampleModule);
        }
        for (Lesson sampleLesson : getSampleLessons()) {
            sampleTrackIter.addLesson(sampleLesson);
        }
        for (Task sampleTask : getSampleTasks()) {
            sampleTrackIter.addTask(sampleTask);
        }
        for (Contact sampleContact : getSampleContacts()) {
            sampleTrackIter.addContact(sampleContact);
        }
        return sampleTrackIter;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
            .map(Tag::new)
            .collect(Collectors.toSet());
    }

}
