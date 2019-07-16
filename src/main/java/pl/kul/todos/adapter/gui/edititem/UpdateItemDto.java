package pl.kul.todos.adapter.gui.edititem;

public class UpdateItemDto {
    private final String title;

    public UpdateItemDto(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
