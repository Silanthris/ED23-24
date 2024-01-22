package ed.Utils.List;

import ed.Utils.List.ListADT;

public interface UnorderedListADT<T> extends ListADT<T> {
    void addToFront(T element);

    void addToRear(T element);

    void addAfter(T element, T target);
}
