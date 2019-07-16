package pl.kul.todos.adapter.gui.mainwindow;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import pl.kul.todos.adapter.gui.edititem.UpdateItemDto;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class TodosTest {
    private static final UUID FIRST_ITEM_ID = UUID.randomUUID();
    private static final UUID SECOND_ITEM_ID = UUID.randomUUID();
    private static final String FIRST_ITEM_TITLE = "First element";
    private static final String SECOND_ITEM_TITLE = "Second element";
    private static final String FIRST_ITEM_SUMMARY = "";
    private static final String SECOND_ITEM_SUMMARY = "";

    private Todos todos;

    @Before
    public void setUp() {
        todos = new Todos(List.of(
                new ItemDto(FIRST_ITEM_ID, FIRST_ITEM_TITLE, "", false, FIRST_ITEM_SUMMARY),
                new ItemDto(SECOND_ITEM_ID, SECOND_ITEM_TITLE, "", false, SECOND_ITEM_SUMMARY)
        ));
    }

    @Test
    public void addItem_should_add_item_to_the_list() {
        // given
        UUID id = UUID.randomUUID();
        String name = "New item";
        boolean done = false;
        String summary = "Summary";

        // and
        ItemDto newItem = new ItemDto(id, name, "", done, summary);

        // when
        todos.addItem(newItem);

        // then
        Assertions.assertThat(todos.getItems()).hasSize(3);
        Assertions.assertThat(todos.getItems()).last().extracting(itemDto -> itemDto.getId()).isEqualTo(id);
        Assertions.assertThat(todos.getItems()).last().extracting(itemDto -> itemDto.getName()).isEqualTo(name);
        Assertions.assertThat(todos.getItems()).last().extracting(itemDto -> itemDto.isDone()).isEqualTo(done);
        Assertions.assertThat(todos.getItems()).last().extracting(itemDto -> itemDto.getSummary()).isEqualTo(summary);
    }

    @Test
    public void updateItem_should_update_all_fields_of_item_associated_with_given_id() {
        // given
        UpdateItemDto command = new UpdateItemDto("New title");

        // when
        todos.updateItem(SECOND_ITEM_ID, command);

        // then
        Assertions.assertThat(todos.getItems()).hasSize(2);
        Assertions.assertThat(todos.getItems()).extracting(ItemDto::getName)
                .containsExactly(FIRST_ITEM_TITLE, "New title");
    }

    @Test
    public void updateItem_should_throw_exception_when_trying_to_update_unknown_id() {
        // given
        UUID unknownId = UUID.randomUUID();

        // and
        UpdateItemDto command = new UpdateItemDto("New title");

        // expect
        Assertions.assertThatThrownBy(() -> todos.updateItem(unknownId, command))
                .hasMessage("There is no item with id of %s", unknownId);
    }

    @Test
    public void getItems_should_return_unmodifiable_list() {
        // given
        List<ItemDto> items = todos.getItems();

        // and
        ItemDto input = new ItemDto("New item");

        // expect
        Assertions.assertThatThrownBy(() -> items.add(input))
                .isInstanceOf(RuntimeException.class);
    }

    @Test
    public void removeItems_should_remove_all_given_items() {
        // given
        Set<UUID> itemsToRemove = Set.of(FIRST_ITEM_ID, SECOND_ITEM_ID);

        // when
        todos.removeItems(itemsToRemove);

        // then
        Assertions.assertThat(todos.getItems()).isEmpty();
    }

    @Test
    public void removeItems_should_do_nothing_when_trying_remove_non_existing_items() {
        // given
        Set<UUID> nonExistingItem = Set.of(UUID.randomUUID());

        // and
        Assertions.assertThat(todos.getItems()).hasSize(2);

        // when
        todos.removeItems(nonExistingItem);

        // then
        Assertions.assertThat(todos.getItems()).hasSize(2);
    }

    @Test
    public void markAsDone_should_throw_exception_when_given_item_does_not_exist() {
        // given
        UUID nonExistingId = UUID.randomUUID();
        String summary = "Summary";

        // expect
        Assertions.assertThatThrownBy(() -> todos.markAsDone(nonExistingId, summary))
                .isInstanceOf(RuntimeException.class);
    }

    @Test
    public void markAsDone_should_mark_given_element_as_done() {
        // given
        UUID itemId = FIRST_ITEM_ID;
        String summary = "Summary";
        ItemDto item = new ItemDto(itemId, FIRST_ITEM_TITLE, "", false, FIRST_ITEM_SUMMARY);

        // and
        Todos todos = new Todos(Collections.singletonList(item));

        // when
        todos.markAsDone(itemId, summary);

        // then
        Assertions.assertThat(todos.getItems()).first().extracting(itemDto -> itemDto.isDone()).isEqualTo(true);
    }

    @Test
    public void markAsDone_should_do_nothing_when_given_element_is_already_marked_as_done() {
        // given
        UUID itemId = FIRST_ITEM_ID;
        String summary = "Summary";
        ItemDto item = new ItemDto(itemId, FIRST_ITEM_TITLE, "", false, FIRST_ITEM_SUMMARY);

        // and
        Todos todos = new Todos(Collections.singletonList(item));

        // when
        todos.markAsDone(itemId, summary);

        // then
        Assertions.assertThat(todos.getItems()).first().extracting(itemDto -> itemDto.isDone()).isEqualTo(true);
    }

    @Test
    public void markAsDone_should_throw_exception_when_summary_is_null() {
        // given
        UUID itemId = FIRST_ITEM_ID;
        ItemDto item = new ItemDto(itemId, FIRST_ITEM_TITLE, "", false, FIRST_ITEM_SUMMARY);

        // and
        Todos todos = new Todos(Collections.singletonList(item));

        // and
        String summary = null;

        // expect
        Assertions.assertThatThrownBy(() -> todos.markAsDone(itemId, summary))
                .isInstanceOf(RuntimeException.class);
    }

    @Test
    public void markAsDone_should_throw_exception_when_summary_is_empty() {
        // given
        UUID itemId = FIRST_ITEM_ID;
        ItemDto item = new ItemDto(itemId, FIRST_ITEM_TITLE, "", false, FIRST_ITEM_SUMMARY);

        // and
        Todos todos = new Todos(Collections.singletonList(item));

        // and
        String summary = "";

        // expect
        Assertions.assertThatThrownBy(() -> todos.markAsDone(itemId, summary))
                .isInstanceOf(RuntimeException.class);
    }

    @Test
    public void markAsDone_should_throw_exception_when_summary_is_blank() {
        // given
        UUID itemId = FIRST_ITEM_ID;
        ItemDto item = new ItemDto(itemId, FIRST_ITEM_TITLE, "", false, FIRST_ITEM_SUMMARY);

        // and
        Todos todos = new Todos(Collections.singletonList(item));

        // and
        String summary = " ";

        // expect
        Assertions.assertThatThrownBy(() -> todos.markAsDone(itemId, summary))
                .isInstanceOf(RuntimeException.class);
    }
}