package pl.kul.todos.adapter.gui.mainwindow;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

public class DoneItemsLastComparatorTest {
    private static final String DEFAULT_TITLE = "First item";
    private static final String DEFAULT_DESCRIPTION = "Description";
    private static final String DEFAULT_SUMMARY = "Summary";

    private DoneItemsLastComparator comparator;

    @Before
    public void setUp() {
        comparator = new DoneItemsLastComparator();
    }

    @Test
    public void compare_should_consider_A_as_greater_when_A_is_done_and_B_is_not() {
        // given
        ItemDto A = new ItemDto(UUID.randomUUID(), DEFAULT_TITLE, DEFAULT_DESCRIPTION, true, DEFAULT_SUMMARY);
        ItemDto B = new ItemDto(UUID.randomUUID(), DEFAULT_TITLE, DEFAULT_DESCRIPTION, false, DEFAULT_SUMMARY);

        // when
        int result = comparator.compare(A, B);

        // then
        Assertions.assertThat(result).isEqualTo(1);
    }

    @Test
    public void compare_should_consider_A_and_B_as_equal_when_both_are_not_done() {
        // given
        ItemDto A = new ItemDto(UUID.randomUUID(), DEFAULT_TITLE, DEFAULT_DESCRIPTION, false, DEFAULT_SUMMARY);
        ItemDto B = new ItemDto(UUID.randomUUID(), DEFAULT_TITLE, DEFAULT_DESCRIPTION, false, DEFAULT_SUMMARY);

        // when
        int result = comparator.compare(A, B);

        // then
        Assertions.assertThat(result).isEqualTo(0);
    }

    @Test
    public void compare_should_consider_A_and_B_as_equal_when_both_are_done() {
        // given
        ItemDto A = new ItemDto(UUID.randomUUID(), DEFAULT_TITLE, DEFAULT_DESCRIPTION, true, DEFAULT_SUMMARY);
        ItemDto B = new ItemDto(UUID.randomUUID(), DEFAULT_TITLE, DEFAULT_DESCRIPTION, true, DEFAULT_SUMMARY);

        // when
        int result = comparator.compare(A, B);

        // then
        Assertions.assertThat(result).isEqualTo(0);
    }

    @Test
    public void compare_should_consider_A_as_smaller_when_A_is_not_done_and_B_is_done() {
        // given
        ItemDto A = new ItemDto(UUID.randomUUID(), DEFAULT_TITLE, DEFAULT_DESCRIPTION, false, DEFAULT_SUMMARY);
        ItemDto B = new ItemDto(UUID.randomUUID(), DEFAULT_TITLE, DEFAULT_DESCRIPTION, true, DEFAULT_SUMMARY);

        // when
        int result = comparator.compare(A, B);

        // then
        Assertions.assertThat(result).isEqualTo(-1);
    }
}