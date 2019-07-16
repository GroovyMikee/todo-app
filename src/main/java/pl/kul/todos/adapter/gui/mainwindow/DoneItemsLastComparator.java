package pl.kul.todos.adapter.gui.mainwindow;

import java.util.Comparator;

class DoneItemsLastComparator implements Comparator<ItemDto> {
    @Override
    public int compare(ItemDto o1, ItemDto o2) {
        if (o1.isDone()) {
            return o2.isDone() ? 0 : 1;
        } else {
            return o2.isDone() ? -1 : 0;
        }
    }
}
