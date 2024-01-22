package frc.robot.math;

import java.util.ArrayList;

public class BetterArrayList<E> extends ArrayList<E> {

    public BetterArrayList<E> append(E e) {
        super.add(e);
        return this;
    }
}
