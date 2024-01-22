package frc.robot.math;

import java.util.ArrayList;

/**
 * Extension of ArrayList that has an additional append function, useful for adding multiple elements to the list upon initialization.
 * @param <E> the type of elements in this list
 */
public class BetterArrayList<E> extends ArrayList<E> {
    public BetterArrayList<E> append(E e) {
        super.add(e);
        return this;
    }
}
