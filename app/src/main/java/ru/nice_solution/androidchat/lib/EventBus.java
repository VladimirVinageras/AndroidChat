package ru.nice_solution.androidchat.lib;

import java.util.Objects;

/**
 * Created by Madriguera on 12/06/2016.
 */
public interface EventBus {
    void register(Object subscriber);
    void unregister(Object subscriber);
    void post(Object event);

}
