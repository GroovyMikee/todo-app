module todo.app.main {
    requires javafx.controls;
    requires com.rometools.rome;

    opens pl.kul.todos.adapter.gui to javafx.graphics;
}