package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import javafx.scene.control.Label;
import javafx.stage.Stage;
import seedu.address.model.application.ApplicationEvent;
import seedu.address.model.application.OnlineAssessment;

/**
 * Tests for {@link EventDetailsWindow} using TestFX with Monocle headless rendering.
 *
 * <h2>Why {@code robot.lookup()} is NOT used for label queries</h2>
 * {@link EventDetailsWindow} extends {@code UiPart<Stage>}, so its root object
 * is the {@link Stage} itself.  TestFX's {@code FxRobot.lookup()} only searches
 * the scene graphs of windows that {@code ApplicationExtension} opened — it does
 * not know about the {@link Stage} we hand to the constructor.  Querying via
 * {@code robot.lookup("#titleLabel")} therefore always returns an empty
 * {@code NodeQuery}, producing the error:
 * <pre>
 *   "there is no node in the scene-graph matching the query: NodeQuery: from nodes: []"
 * </pre>
 *
 * <h2>The fix</h2>
 * We scope every lookup explicitly to the window's own scene root:
 * <pre>
 *   robot.from(window.getRoot().getScene().getRoot()).lookup("#titleLabel")
 * </pre>
 * This bypasses TestFX's window registry entirely and searches exactly the
 * scene that {@code EventDetailsWindow.fxml} loaded into.
 *
 * <p>The window is shown in {@code @Start} so that JavaFX fully initialises the
 * scene (attaches it to the stage, lays out nodes, etc.) before any test runs.
 * Without {@code show()}, {@code getScene()} returns {@code null} on some
 * platforms and the lookup throws a {@link NullPointerException}.
 */
@ExtendWith(ApplicationExtension.class)
public class EventDetailsWindowTest {

    // ── Constants ─────────────────────────────────────────────────────────────

    private static final DateTimeFormatter DISPLAY_FORMATTER =
            DateTimeFormatter.ofPattern("dd MMM yyyy, HH:mm");

    // ── State ─────────────────────────────────────────────────────────────────

    /**
     * Fresh window created before every test by {@link #start(Stage)}.
     */
    private EventDetailsWindow window;

    // ── @Start ────────────────────────────────────────────────────────────────

    /**
     * Runs on the FX thread before each test.
     *
     * <p>We pass the {@link Stage} provided by {@code ApplicationExtension} directly
     * to the constructor so that the window is wired into a stage that already has
     * a scene attached.  Calling {@link EventDetailsWindow#show()} ensures the scene
     * graph is fully realised before any lookup is attempted.
     */
    @Start
    public void start(Stage stage) {
        window = new EventDetailsWindow(stage);
        window.show();
    }

    // ── Helper ────────────────────────────────────────────────────────────────

    /**
     * Looks up a {@link Label} by its {@code fx:id} within the window's own
     * scene graph.  Avoids the empty-NodeQuery error that arises when using
     * {@code robot.lookup()} directly (see class-level Javadoc).
     */
    private Label label(FxRobot robot, String fxId) {
        Label node = robot
                .from(window.getRoot().getScene().getRoot())
                .lookup("#" + fxId)
                .queryAs(Label.class);
        assertNotNull(node, "No Label with fx:id='" + fxId + "' found in scene");
        return node;
    }

    // ── Constructor ───────────────────────────────────────────────────────────

    @Test
    public void constructor_noArg_producesNonNullRoot(FxRobot robot) {
        robot.interact(() -> {
            EventDetailsWindow noArg = new EventDetailsWindow();
            assertNotNull(noArg.getRoot());
        });
    }

    @Test
    public void constructor_withStage_rootIsProvidedStage(FxRobot robot) {
        robot.interact(() -> {
            Stage stage = new Stage();
            EventDetailsWindow edw = new EventDetailsWindow(stage);
            assertSame(edw.getRoot(), stage, "getRoot() should return the exact Stage passed to the constructor");
        });
    }

    // ── setEventDetails – OnlineAssessment (no explicit notes) ───────────────

    @Test
    public void setEventDetails_onlineAssessmentNoNotes_allLabelsCorrect(FxRobot robot) {
        LocalDateTime dt = LocalDateTime.of(2026, 8, 10, 10, 0);
        OnlineAssessment oa = new OnlineAssessment(
                "Office", dt, "Codility", "https://codility.com");

        robot.interact(() -> window.setEventDetails(oa));

        assertEquals("Online Assessment Details", label(robot, "titleLabel").getText());
        assertEquals("Office", label(robot, "locationLabel").getText());
        assertEquals(dt.format(DISPLAY_FORMATTER), label(robot, "dateTimeLabel").getText());
        assertEquals("Codility", label(robot, "platformLabel").getText());
        assertEquals("https://codility.com", label(robot, "linkLabel").getText());
        assertEquals(OnlineAssessment.EMPTY_NOTES_VALUE, label(robot, "notesLabel").getText());
    }

    @Test
    public void setEventDetails_onlineAssessment_titleIsCorrect(FxRobot robot) {
        OnlineAssessment oa = new OnlineAssessment(
                "Zoom", LocalDateTime.of(2026, 10, 1, 9, 0), "LeetCode", "https://lc.com");

        robot.interact(() -> window.setEventDetails(oa));

        assertEquals("Online Assessment Details", label(robot, "titleLabel").getText());
    }

    @Test
    public void setEventDetails_onlineAssessment_locationIsCorrect(FxRobot robot) {
        OnlineAssessment oa = new OnlineAssessment(
                "Building A", LocalDateTime.of(2026, 11, 5, 8, 0), "Karat", "https://karat.com");

        robot.interact(() -> window.setEventDetails(oa));

        assertEquals("Building A", label(robot, "locationLabel").getText());
    }

    @Test
    public void setEventDetails_onlineAssessment_platformIsCorrect(FxRobot robot) {
        OnlineAssessment oa = new OnlineAssessment(
                "Online", LocalDateTime.of(2026, 5, 3, 11, 0), "Pymetrics", "https://p.com");

        robot.interact(() -> window.setEventDetails(oa));

        assertEquals("Pymetrics", label(robot, "platformLabel").getText());
    }

    @Test
    public void setEventDetails_onlineAssessment_linkIsCorrect(FxRobot robot) {
        OnlineAssessment oa = new OnlineAssessment(
                "Online", LocalDateTime.of(2026, 5, 3, 11, 0),
                "Pymetrics", "https://pymetrics.com");

        robot.interact(() -> window.setEventDetails(oa));

        assertEquals("https://pymetrics.com", label(robot, "linkLabel").getText());
    }

    // ── setEventDetails – OnlineAssessment (with explicit notes) ─────────────

    @Test
    public void setEventDetails_onlineAssessmentWithNotes_notesLabelShowsNotes(FxRobot robot) {
        OnlineAssessment oa = new OnlineAssessment(
                "Remote", LocalDateTime.of(2026, 9, 20, 14, 30),
                "HackerRank", "https://hr.com", "Bring pen and paper");

        robot.interact(() -> window.setEventDetails(oa));

        assertEquals("Bring pen and paper", label(robot, "notesLabel").getText());
    }

    @Test
    public void setEventDetails_onlineAssessmentWithNotes_allLabelsCorrect(FxRobot robot) {
        LocalDateTime dt = LocalDateTime.of(2026, 9, 20, 14, 30);
        OnlineAssessment oa = new OnlineAssessment(
                "Remote", dt, "HackerRank", "https://hr.com", "Bring pen and paper");

        robot.interact(() -> window.setEventDetails(oa));

        assertEquals("Online Assessment Details", label(robot, "titleLabel").getText());
        assertEquals("Remote", label(robot, "locationLabel").getText());
        assertEquals(dt.format(DISPLAY_FORMATTER), label(robot, "dateTimeLabel").getText());
        assertEquals("HackerRank", label(robot, "platformLabel").getText());
        assertEquals("https://hr.com", label(robot, "linkLabel").getText());
        assertEquals("Bring pen and paper", label(robot, "notesLabel").getText());
    }

    // ── setEventDetails – generic ApplicationEvent fallback ───────────────────

    @Test
    public void setEventDetails_genericEvent_allFallbackLabelsCorrect(FxRobot robot) {
        LocalDateTime dt = LocalDateTime.of(2026, 9, 5, 9, 0);
        ApplicationEvent generic = new ApplicationEvent("Conference room", dt) {
        };

        robot.interact(() -> window.setEventDetails(generic));

        assertEquals("Event Details", label(robot, "titleLabel").getText());
        assertEquals("Conference room", label(robot, "locationLabel").getText());
        assertEquals(dt.format(DISPLAY_FORMATTER), label(robot, "dateTimeLabel").getText());
        assertEquals("N/A", label(robot, "platformLabel").getText());
        assertEquals("N/A", label(robot, "linkLabel").getText());
        assertEquals("N/A", label(robot, "notesLabel").getText());
    }

    @Test
    public void setEventDetails_genericEvent_titleIsEventDetails(FxRobot robot) {
        ApplicationEvent generic =
                new ApplicationEvent("Room 3B", LocalDateTime.of(2026, 4, 10, 9, 0)) {
                };

        robot.interact(() -> window.setEventDetails(generic));

        assertEquals("Event Details", label(robot, "titleLabel").getText());
    }

    @Test
    public void setEventDetails_genericEvent_platformIsNa(FxRobot robot) {
        ApplicationEvent generic =
                new ApplicationEvent("Room 3B", LocalDateTime.of(2026, 4, 10, 9, 0)) {
                };

        robot.interact(() -> window.setEventDetails(generic));

        assertEquals("N/A", label(robot, "platformLabel").getText());
    }

    @Test
    public void setEventDetails_genericEvent_linkIsNa(FxRobot robot) {
        ApplicationEvent generic =
                new ApplicationEvent("Room 3B", LocalDateTime.of(2026, 4, 10, 9, 0)) {
                };

        robot.interact(() -> window.setEventDetails(generic));

        assertEquals("N/A", label(robot, "linkLabel").getText());
    }

    @Test
    public void setEventDetails_genericEvent_notesIsNa(FxRobot robot) {
        ApplicationEvent generic =
                new ApplicationEvent("Room 3B", LocalDateTime.of(2026, 4, 10, 9, 0)) {
                };

        robot.interact(() -> window.setEventDetails(generic));

        assertEquals("N/A", label(robot, "notesLabel").getText());
    }

    // ── setEventDetails called twice ──────────────────────────────────────────

    @Test
    public void setEventDetails_calledTwice_labelsReflectSecondCall(FxRobot robot) {
        OnlineAssessment first = new OnlineAssessment(
                "First", LocalDateTime.of(2026, 3, 1, 8, 0), "PlatformA", "https://a.com");
        OnlineAssessment second = new OnlineAssessment(
                "Second", LocalDateTime.of(2026, 4, 1, 10, 0), "PlatformB", "https://b.com");

        robot.interact(() -> window.setEventDetails(first));
        assertEquals("First", label(robot, "locationLabel").getText());

        robot.interact(() -> window.setEventDetails(second));
        assertEquals("Second", label(robot, "locationLabel").getText());
        assertEquals("PlatformB", label(robot, "platformLabel").getText());
        assertEquals("https://b.com", label(robot, "linkLabel").getText());
    }

    @Test
    public void setEventDetails_switchFromOaToGeneric_labelsUpdated(FxRobot robot) {
        OnlineAssessment oa = new OnlineAssessment(
                "Office", LocalDateTime.of(2026, 6, 1, 9, 0), "Codility", "https://c.com");
        ApplicationEvent generic =
                new ApplicationEvent("Online", LocalDateTime.of(2026, 7, 1, 10, 0)) {
                };

        robot.interact(() -> window.setEventDetails(oa));
        assertEquals("Online Assessment Details", label(robot, "titleLabel").getText());

        robot.interact(() -> window.setEventDetails(generic));
        assertEquals("Event Details", label(robot, "titleLabel").getText());
        assertEquals("N/A", label(robot, "platformLabel").getText());
        assertEquals("N/A", label(robot, "linkLabel").getText());
        assertEquals("N/A", label(robot, "notesLabel").getText());
    }

    // ── Date/time formatting ──────────────────────────────────────────────────

    @Test
    public void setEventDetails_dateTimeLabel_matchesDisplayFormatter(FxRobot robot) {
        LocalDateTime dt = LocalDateTime.of(2025, 12, 31, 23, 59);
        OnlineAssessment oa = new OnlineAssessment("Lab", dt, "Platform", "https://x.com");

        robot.interact(() -> window.setEventDetails(oa));

        assertEquals(dt.format(DISPLAY_FORMATTER), label(robot, "dateTimeLabel").getText());
    }

    @Test
    public void setEventDetails_singleDigitDay_isPaddedWithZero(FxRobot robot) {
        LocalDateTime dt = LocalDateTime.of(2026, 1, 5, 10, 0);
        OnlineAssessment oa = new OnlineAssessment("Lab", dt, "Platform", "https://x.com");

        robot.interact(() -> window.setEventDetails(oa));

        String text = label(robot, "dateTimeLabel").getText();
        assertTrue(text.startsWith("05 "), "Day should be zero-padded: got '" + text + "'");
    }

    @Test
    public void setEventDetails_january_monthAbbreviationIsJan(FxRobot robot) {
        LocalDateTime dt = LocalDateTime.of(2026, 1, 7, 15, 45);
        OnlineAssessment oa = new OnlineAssessment("HQ", dt, "TestGorilla", "https://tg.com");

        robot.interact(() -> window.setEventDetails(oa));

        assertEquals("07 Jan 2026, 15:45", label(robot, "dateTimeLabel").getText());
    }

    @Test
    public void setEventDetails_singleDigitMinute_isPaddedWithZero(FxRobot robot) {
        LocalDateTime dt = LocalDateTime.of(2026, 3, 15, 9, 5);
        OnlineAssessment oa = new OnlineAssessment("Lab", dt, "Platform", "https://x.com");

        robot.interact(() -> window.setEventDetails(oa));

        String text = label(robot, "dateTimeLabel").getText();
        assertTrue(text.endsWith(":05"), "Minutes should be zero-padded: got '" + text + "'");
    }

    // ── isShowing ─────────────────────────────────────────────────────────────

    @Test
    public void isShowing_afterStart_returnsTrue(FxRobot robot) {
        // window.show() is called in @Start, so it must be showing by the time
        // any test runs.
        assertTrue(window.isShowing());
    }

    // ── hide ─────────────────────────────────────────────────────────────────

    @Test
    public void hide_afterShow_windowIsNoLongerShowing(FxRobot robot) {
        assertTrue(window.isShowing(), "Precondition: window should be showing after @Start");

        robot.interact(window::hide);

        assertFalse(window.isShowing());
    }

    @Test
    public void hide_calledTwice_doesNotThrow(FxRobot robot) {
        robot.interact(() -> {
            window.hide();
            window.hide();
        });
        assertFalse(window.isShowing());
    }

    // ── focus ─────────────────────────────────────────────────────────────────

    @Test
    public void focus_onVisibleWindow_doesNotThrow(FxRobot robot) {
        assertTrue(window.isShowing(), "Precondition: window should be visible");
        robot.interact(window::focus);
        // no exception → pass
    }

    @Test
    public void focus_onHiddenWindow_doesNotThrow(FxRobot robot) {
        robot.interact(window::hide);
        robot.interact(window::focus);
        // no exception → pass
    }

    // ── handleClose ───────────────────────────────────────────────────────────

    @Test
    public void handleClose_whenShowing_hidesWindow(FxRobot robot) throws Exception {
        assertTrue(window.isShowing(), "Precondition: window should be showing before handleClose");

        Method handleClose = EventDetailsWindow.class.getDeclaredMethod("handleClose");
        handleClose.setAccessible(true);
        robot.interact(() -> {
            try {
                handleClose.invoke(window);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        assertFalse(window.isShowing(), "Window should be hidden after handleClose()");
    }

    @Test
    public void handleClose_whenAlreadyHidden_remainsHidden(FxRobot robot) throws Exception {
        robot.interact(window::hide);
        assertFalse(window.isShowing(), "Precondition: window must be hidden");

        Method handleClose = EventDetailsWindow.class.getDeclaredMethod("handleClose");
        handleClose.setAccessible(true);
        robot.interact(() -> {
            try {
                handleClose.invoke(window);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        assertFalse(window.isShowing());
    }

    @Test
    public void handleClose_calledTwice_doesNotThrow(FxRobot robot) throws Exception {
        Method handleClose = EventDetailsWindow.class.getDeclaredMethod("handleClose");
        handleClose.setAccessible(true);

        robot.interact(() -> {
            try {
                handleClose.invoke(window);
                handleClose.invoke(window);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        assertFalse(window.isShowing());
    }
}
