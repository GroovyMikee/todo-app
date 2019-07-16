package pl.kul.todos.adapter.gui.itemdetails;

class ItemDetailsDto {
    private final String title;
    private final String status;
    private final String summary;
    private final String description;

    public ItemDetailsDto(String title, String description, String status, String summary) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.summary = summary;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getStatus() {
        return status;
    }

    public String getSummary() {
        return summary;
    }
}
