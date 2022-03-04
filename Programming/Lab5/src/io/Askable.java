package io;

import exceptions.*;

/**
 * user input callback
 */
public interface Askable<T> {
    T ask() throws InvalidDataException;
}
