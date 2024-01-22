package ed.Utils.List;

import pt.ipp.estg.data.structures.List.ListADT;

public interface UnorderedListADT<T> extends ListADT<T> {
    void addToFront(T element);

    void addToRear(T element);

    void addAfter(T element, T target);
}
