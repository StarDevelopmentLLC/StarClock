package com.stardevllc.starclock;

import com.stardevllc.starlib.observable.ChangeListener;
import com.stardevllc.starlib.observable.property.BindChangeListener;
import com.stardevllc.starlib.observable.property.LongProperty;

public class ClockLongProperty extends LongProperty {
    public ClockLongProperty() {
    }

    public ClockLongProperty(Long object) {
        super(object);
    }

    @Override
    public void addChangeListener(ChangeListener<Long> changeListener) {
        if (changeListener instanceof BindChangeListener<Long> bindChangeListener) {
            throw new UnsupportedOperationException("Cannot bind a clock property to another one.");
        }
    }
}
