/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2011-2014, Yannis Gonianakis
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.info.ip;

import java.io.Serializable;
import java.util.NoSuchElementException;

public abstract class Optional<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final Optional<?> ABSENT = new Absent<Object>();

    public static <T> Optional<T> of(T value) {
        return value != null ? new Present<T>(value) : Optional.<T>absent();
    }

    @SuppressWarnings("unchecked")
    public static <T> Optional<T> absent() {
        return (Optional<T>) ABSENT;
    }

    public static <T> T getOrNull(Optional<T> optional) {
        return optional.isPresent() ? optional.get() : null;
    }

    public abstract T get();

    public abstract boolean isPresent();

    public boolean isAbsent() {
        return !isPresent();
    }

    public static final class Present<T> extends Optional<T> {

        private final T value;

        private Present(T value) {
            this.value = value;
        }

        @Override
        public T get() {
            return value;
        }

        @Override
        public boolean isPresent() {
            return true;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Present present = (Present) o;
            return value.equals(present.value);
        }

        @Override
        public int hashCode() {
            return value.hashCode();
        }

        @Override
        public String toString() {
            return String.format("Present(%s)", value);
        }
    }

    public static final class Absent<T> extends Optional<T> {

        private Absent() {
        }

        @Override
        public T get() {
            throw new NoSuchElementException("Absent.get");
        }

        @Override
        public boolean isPresent() {
            return false;
        }

        @Override
        public String toString() {
            return "Absent";
        }
    }
}
