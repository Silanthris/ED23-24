package ed.Utils.List;

import pt.ipp.estg.data.structures.List.ListADT;

public interface OrderedListADT<T> extends ListADT<T> {
    void add(T element);
}
