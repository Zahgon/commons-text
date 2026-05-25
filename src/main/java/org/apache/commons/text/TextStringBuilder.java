/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.commons.text;

import java.io.IOException;
import java.io.Reader;
import java.io.Serializable;
import java.io.Writer;
import java.nio.CharBuffer;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.matcher.StringMatcher;

/**
 * Builds a string from constituent parts providing a more flexible and powerful API than {@link StringBuffer} and
 * {@link StringBuilder}.
 * <p>
 * The main differences from StringBuffer/StringBuilder are:
 * </p>
 * <ul>
 * <li>Not synchronized</li>
 * <li>Not final</li>
 * <li>Subclasses have direct access to character array</li>
 * <li>Additional methods
 * <ul>
 * <li>appendWithSeparators - adds an array of values, with a separator</li>
 * <li>appendPadding - adds a length padding characters</li>
 * <li>appendFixedLength - adds a fixed width field to the builder</li>
 * <li>toCharArray/getChars - simpler ways to get a range of the character array</li>
 * <li>delete - delete char or string</li>
 * <li>replace - search and replace for a char or string</li>
 * <li>leftString/rightString/midString - substring without exceptions</li>
 * <li>contains - whether the builder contains a char or string</li>
 * <li>size/clear/isEmpty - collections style API methods</li>
 * </ul>
 * </li>
 * <li>Views
 * <ul>
 * <li>asTokenizer - uses the internal buffer as the source of a StrTokenizer</li>
 * <li>asReader - uses the internal buffer as the source of a Reader</li>
 * <li>asWriter - allows a Writer to write directly to the internal buffer</li>
 * </ul>
 * </li>
 * </ul>
 * <p>
 * The aim has been to provide an API that mimics very closely what StringBuffer provides, but with additional methods.
 * It should be noted that some edge cases, with invalid indices or null input, have been altered - see individual
 * methods. The biggest of these changes is that by default, null will not output the text 'null'. This can be
 * controlled by a property, {@link #setNullText(String)}.
 * </p>
 * <p>
 * This class is called {@code TextStringBuilder} instead of {@code StringBuilder} to avoid clashing with
 * {@link StringBuilder}.
 * </p>
 *
 * @since 1.3
 */
public class TextStringBuilder implements CharSequence, Appendable, Serializable, Builder<String> {

    /**
     * Inner class to allow StrBuilder to operate as a reader.
     */
    final class TextStringBuilderReader extends Reader {

        /**
         * The last mark position.
         */
        private int mark;

        /**
         * The current stream position.
         */
        private int pos;

        /**
         * Default constructor.
         */
        TextStringBuilderReader() {
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void close() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void mark(final int readAheadLimit) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean markSupported() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int read() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int read(final char[] b, final int off, int len) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean ready() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void reset() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public long skip(long n) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }

    /**
     * Inner class to allow StrBuilder to operate as a tokenizer.
     */
    final class TextStringBuilderTokenizer extends StringTokenizer {

        /**
         * Default constructor.
         */
        TextStringBuilderTokenizer() {
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String getContent() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        protected List<String> tokenize(final char[] chars, final int offset, final int count) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }

    /**
     * Inner class to allow StrBuilder to operate as a writer.
     */
    final class TextStringBuilderWriter extends Writer {

        /**
         * Default constructor.
         */
        TextStringBuilderWriter() {
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void close() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void flush() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void write(final char[] cbuf) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void write(final char[] cbuf, final int off, final int len) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void write(final int c) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void write(final String str) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void write(final String str, final int off, final int len) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }

    /**
     * The space character.
     */
    private static final char SPACE = ' ';

    /**
     * The extra capacity for new builders.
     */
    static final int CAPACITY = 32;

    /**
     * End-Of-Stream.
     */
    private static final int EOS = -1;

    /**
     * The size of the string {@code "false"}.
     */
    private static final int FALSE_STRING_SIZE = Boolean.FALSE.toString().length();

    /**
     * Required for serialization support.
     *
     * @see java.io.Serializable
     */
    private static final long serialVersionUID = 1L;

    /**
     * The size of the string {@code "true"}.
     */
    private static final int TRUE_STRING_SIZE = Boolean.TRUE.toString().length();

    /**
     * The maximum size buffer to allocate.
     *
     * <p>This is set to the same size used in the JDK {@link java.util.ArrayList}:</p>
     * <blockquote>
     * Some VMs reserve some header words in an array.
     * Attempts to allocate larger arrays may result in
     * OutOfMemoryError: Requested array size exceeds VM limit.
     * </blockquote>
     */
    private static final int MAX_BUFFER_SIZE = Integer.MAX_VALUE - 8;

    /**
     * Creates a positive capacity at least as large the minimum required capacity.
     * If the minimum capacity is negative then this throws an OutOfMemoryError as no array
     * can be allocated.
     *
     * @param minCapacity the minimum capacity
     * @return the capacity
     * @throws OutOfMemoryError if the {@code minCapacity} is negative
     */
    private static int createPositiveCapacity(final int minCapacity) {
        if (minCapacity < 0) {
            // overflow
            throw new OutOfMemoryError("Unable to allocate array size: " + Integer.toUnsignedString(minCapacity));
        }
        // This is called when we require buffer expansion to a very big array.
        // Use the conservative maximum buffer size if possible, otherwise the biggest required.
        //
        // Note: In this situation JDK 1.8 java.util.ArrayList returns Integer.MAX_VALUE.
        // This excludes some VMs that can exceed MAX_BUFFER_SIZE but not allocate a full
        // Integer.MAX_VALUE length array.
        // The result is that we may have to allocate an array of this size more than once if
        // the capacity must be expanded again.
        return Math.max(minCapacity, MAX_BUFFER_SIZE);
    }

    /**
     * Constructs an instance from a reference to a character array. Changes to the input chars are reflected in this
     * instance until the internal buffer needs to be reallocated. Using a reference to an array allows the instance to
     * be initialized without copying the input array.
     *
     * @param initialBuffer The initial array that will back the new builder.
     * @return A new instance.
     * @since 1.9
     */
    public static TextStringBuilder wrap(final char[] initialBuffer) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Constructs an instance from a reference to a character array. Changes to the input chars are reflected in this
     * instance until the internal buffer needs to be reallocated. Using a reference to an array allows the instance to
     * be initialized without copying the input array.
     *
     * @param initialBuffer The initial array that will back the new builder.
     * @param length The length of the subarray to be used; must be non-negative and no larger than
     *        {@code initialBuffer.length}. The new builder's size will be set to {@code length}.
     * @return A new instance.
     * @since 1.9
     */
    public static TextStringBuilder wrap(final char[] initialBuffer, final int length) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Internal data storage.
     */
    private char[] buffer;

    /**
     * The new line, {@code null} means use the system default from {@link System#lineSeparator()}.
     */
    private String newLine;

    /**
     * The null text.
     */
    private String nullText;

    /**
     * Incremented when the buffer is reallocated.
     */
    private int reallocations;

    /**
     * Current size of the buffer.
     */
    private int size;

    /**
     * Constructs an empty builder with an initial capacity of 32 characters.
     */
    public TextStringBuilder() {
        this(CAPACITY);
    }

    /**
     * Constructs an instance from a reference to a character array.
     *
     * @param initialBuffer a reference to a character array, must not be null.
     * @param length        The length of the subarray to be used; must be non-negative and no larger than {@code initialBuffer.length}. The new builder's size
     *                      will be set to {@code length}.
     * @throws NullPointerException     If {@code initialBuffer} is null.
     * @throws IllegalArgumentException if {@code length} is bad.
     */
    private TextStringBuilder(final char[] initialBuffer, final int length) {
        this.buffer = Objects.requireNonNull(initialBuffer, "initialBuffer");
        if (length < 0 || length > initialBuffer.length) {
            throw new IllegalArgumentException("initialBuffer.length=" + initialBuffer.length + ", length=" + length);
        }
        this.size = length;
    }

    /**
     * Constructs an instance from a character sequence, allocating 32 extra characters for growth.
     *
     * @param seq the string to copy, null treated as blank string.
     * @since 1.9
     */
    public TextStringBuilder(final CharSequence seq) {
        this(StringUtils.length(seq) + CAPACITY);
        if (seq != null) {
            append(seq);
        }
    }

    /**
     * Constructs an instance with the specified initial capacity.
     *
     * @param initialCapacity the initial capacity, zero or less will be converted to 32.
     */
    public TextStringBuilder(final int initialCapacity) {
        buffer = new char[initialCapacity <= 0 ? CAPACITY : initialCapacity];
    }

    /**
     * Constructs an instance from a string, allocating 32 extra characters for growth.
     *
     * @param str the string to copy, null treated as blank string.
     */
    public TextStringBuilder(final String str) {
        this(StringUtils.length(str) + CAPACITY);
        if (str != null) {
            append(str);
        }
    }

    /**
     * Appends a boolean value to the string builder.
     *
     * @param value the value to append.
     * @return {@code this} instance.
     */
    public TextStringBuilder append(final boolean value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Appends a char value to the string builder.
     *
     * @param ch the value to append.
     * @return {@code this} instance.
     */
    @Override
    public TextStringBuilder append(final char ch) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Appends a char array to the string builder. Appending null will call {@link #appendNull()}.
     *
     * @param chars the char array to append.
     * @return {@code this} instance.
     */
    public TextStringBuilder append(final char[] chars) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Appends a char array to the string builder. Appending null will call {@link #appendNull()}.
     *
     * @param chars      the char array to append.
     * @param startIndex the start index, inclusive, must be valid.
     * @param length     the length to append, must be valid.
     * @return {@code this} instance.
     * @throws StringIndexOutOfBoundsException if {@code startIndex} is not in the range {@code 0 <= startIndex <= chars.length}.
     * @throws StringIndexOutOfBoundsException if {@code length < 0}.
     * @throws StringIndexOutOfBoundsException if {@code startIndex + length > chars.length}.
     */
    public TextStringBuilder append(final char[] chars, final int startIndex, final int length) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Appends the contents of a char buffer to this string builder. Appending null will call {@link #appendNull()}.
     *
     * @param str the char buffer to append.
     * @return {@code this} instance.
     */
    public TextStringBuilder append(final CharBuffer str) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Appends the contents of a char buffer to this string builder. Appending null will call {@link #appendNull()}.
     *
     * @param buf the char buffer to append.
     * @param startIndex the start index, inclusive, must be valid.
     * @param length the length to append, must be valid.
     * @return {@code this} instance.
     */
    public TextStringBuilder append(final CharBuffer buf, final int startIndex, final int length) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Appends a CharSequence to this string builder. Appending null will call {@link #appendNull()}.
     *
     * @param seq the CharSequence to append.
     * @return {@code this} instance.
     */
    @Override
    public TextStringBuilder append(final CharSequence seq) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Appends part of a CharSequence to this string builder. Appending null will call {@link #appendNull()}.
     *
     * @param seq the CharSequence to append.
     * @param startIndex the start index, inclusive, must be valid.
     * @param endIndex the end index, exclusive, must be valid.
     * @return {@code this} instance.
     */
    @Override
    public TextStringBuilder append(final CharSequence seq, final int startIndex, final int endIndex) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Appends a double value to the string builder using {@code String.valueOf}.
     *
     * @param value the value to append.
     * @return {@code this} instance.
     */
    public TextStringBuilder append(final double value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Appends a float value to the string builder using {@code String.valueOf}.
     *
     * @param value the value to append.
     * @return {@code this} instance.
     */
    public TextStringBuilder append(final float value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Appends an int value to the string builder using {@code String.valueOf}.
     *
     * @param value the value to append.
     * @return {@code this} instance.
     */
    public TextStringBuilder append(final int value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Appends a long value to the string builder using {@code String.valueOf}.
     *
     * @param value the value to append.
     * @return {@code this} instance.
     */
    public TextStringBuilder append(final long value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Appends an object to this string builder. Appending null will call {@link #appendNull()}.
     *
     * @param obj the object to append.
     * @return {@code this} instance.
     */
    public TextStringBuilder append(final Object obj) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Appends a string to this string builder. Appending null will call {@link #appendNull()}.
     *
     * @param str the string to append.
     * @return {@code this} instance.
     */
    public TextStringBuilder append(final String str) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Appends part of a string to this string builder. Appending null will call {@link #appendNull()}.
     *
     * @param str        the string to append.
     * @param startIndex the start index, inclusive, must be valid.
     * @param length     the length to append, must be valid.
     * @return {@code this} instance.
     * @throws StringIndexOutOfBoundsException if {@code startIndex} is not in the range {@code 0 <= startIndex <= str.length()}.
     * @throws StringIndexOutOfBoundsException if {@code length < 0}.
     * @throws StringIndexOutOfBoundsException if {@code startIndex + length > str.length()}.
     */
    public TextStringBuilder append(final String str, final int startIndex, final int length) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Calls {@link String#format(String, Object...)} and appends the result.
     *
     * @param format the format string.
     * @param objs   the objects to use in the format string.
     * @return {@code this} to enable chaining.
     * @see String#format(String, Object...)
     */
    public TextStringBuilder append(final String format, final Object... objs) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Appends a string buffer to this string builder. Appending null will call {@link #appendNull()}.
     *
     * @param str the string buffer to append.
     * @return {@code this} instance.
     */
    public TextStringBuilder append(final StringBuffer str) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Appends part of a string buffer to this string builder. Appending null will call {@link #appendNull()}.
     *
     * @param str        the string to append.
     * @param startIndex the start index, inclusive, must be valid.
     * @param length     the length to append, must be valid.
     * @return {@code this} instance.
     */
    public TextStringBuilder append(final StringBuffer str, final int startIndex, final int length) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Appends a StringBuilder to this string builder. Appending null will call {@link #appendNull()}.
     *
     * @param str the StringBuilder to append.
     * @return {@code this} instance.
     */
    public TextStringBuilder append(final StringBuilder str) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Appends part of a StringBuilder to this string builder. Appending null will call {@link #appendNull()}.
     *
     * @param str the StringBuilder to append.
     * @param startIndex the start index, inclusive, must be valid.
     * @param length the length to append, must be valid.
     * @return {@code this} instance.
     */
    public TextStringBuilder append(final StringBuilder str, final int startIndex, final int length) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Appends another string builder to this string builder. Appending null will call {@link #appendNull()}.
     *
     * @param str the string builder to append.
     * @return {@code this} instance.
     */
    public TextStringBuilder append(final TextStringBuilder str) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Appends part of a string builder to this string builder. Appending null will call {@link #appendNull()}.
     *
     * @param str the string to append.
     * @param startIndex the start index, inclusive, must be valid.
     * @param length the length to append, must be valid.
     * @return {@code this} instance.
     */
    public TextStringBuilder append(final TextStringBuilder str, final int startIndex, final int length) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Appends each item in an iterable to the builder without any separators. Appending a null iterable will have no effect. Each object is appended using
     * {@link #append(Object)}.
     *
     * @param iterable the iterable to append.
     * @return {@code this} instance.
     */
    public TextStringBuilder appendAll(final Iterable<?> iterable) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Appends each item in an iterator to the builder without any separators. Appending a null iterator will have no effect. Each object is appended using
     * {@link #append(Object)}.
     *
     * @param it the iterator to append.
     * @return {@code this} instance.
     */
    public TextStringBuilder appendAll(final Iterator<?> it) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Appends each item in an array to the builder without any separators. Appending a null array will have no effect. Each object is appended using
     * {@link #append(Object)}.
     *
     * @param <T>   the element type.
     * @param array the array to append.
     * @return {@code this} instance.
     */
    public <T> TextStringBuilder appendAll(@SuppressWarnings("unchecked") final T... array) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Appends {@code "false"}.
     */
    private void appendFalse(int index) {
        buffer[index++] = 'f';
        buffer[index++] = 'a';
        buffer[index++] = 'l';
        buffer[index++] = 's';
        buffer[index] = 'e';
        size += FALSE_STRING_SIZE;
    }

    /**
     * Appends an object to the builder padding on the left to a fixed width. The {@code String.valueOf} of the {@code int} value is used. If the formatted
     * value is larger than the length, the left hand side is lost.
     *
     * @param value   the value to append.
     * @param width   the fixed field width, zero or negative has no effect.
     * @param padChar the pad character to use.
     * @return {@code this} instance.
     */
    public TextStringBuilder appendFixedWidthPadLeft(final int value, final int width, final char padChar) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Appends an object to the builder padding on the left to a fixed width. The {@code toString} of the object is used. If the object is larger than the
     * length, the left hand side is lost. If the object is null, the null text value is used.
     *
     * @param obj     the object to append, null uses null text.
     * @param width   the fixed field width, zero or negative has no effect.
     * @param padChar the pad character to use.
     * @return {@code this} instance.
     */
    public TextStringBuilder appendFixedWidthPadLeft(final Object obj, final int width, final char padChar) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Appends an object to the builder padding on the right to a fixed length. The {@code String.valueOf} of the {@code int} value is used. If the object is
     * larger than the length, the right hand side is lost.
     *
     * @param value   the value to append.
     * @param width   the fixed field width, zero or negative has no effect.
     * @param padChar the pad character to use.
     * @return {@code this} instance.
     */
    public TextStringBuilder appendFixedWidthPadRight(final int value, final int width, final char padChar) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Appends an object to the builder padding on the right to a fixed length. The {@code toString} of the object is used. If the object is larger than the
     * length, the right hand side is lost. If the object is null, null text value is used.
     *
     * @param obj     the object to append, null uses null text.
     * @param width   the fixed field width, zero or negative has no effect.
     * @param padChar the pad character to use.
     * @return {@code this} instance.
     */
    public TextStringBuilder appendFixedWidthPadRight(final Object obj, final int width, final char padChar) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Appends a boolean value followed by a new line to the string builder.
     *
     * @param value the value to append.
     * @return {@code this} instance.
     */
    public TextStringBuilder appendln(final boolean value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Appends a char value followed by a new line to the string builder.
     *
     * @param ch the value to append.
     * @return {@code this} instance.
     */
    public TextStringBuilder appendln(final char ch) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Appends a char array followed by a new line to the string builder. Appending null will call
     * {@link #appendNull()}.
     *
     * @param chars the char array to append.
     * @return {@code this} instance.
     */
    public TextStringBuilder appendln(final char[] chars) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Appends a char array followed by a new line to the string builder. Appending null will call
     * {@link #appendNull()}.
     *
     * @param chars the char array to append.
     * @param startIndex the start index, inclusive, must be valid.
     * @param length the length to append, must be valid.
     * @return {@code this} instance.
     */
    public TextStringBuilder appendln(final char[] chars, final int startIndex, final int length) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Appends a double value followed by a new line to the string builder using {@code String.valueOf}.
     *
     * @param value the value to append.
     * @return {@code this} instance.
     */
    public TextStringBuilder appendln(final double value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Appends a float value followed by a new line to the string builder using {@code String.valueOf}.
     *
     * @param value the value to append.
     * @return {@code this} instance.
     */
    public TextStringBuilder appendln(final float value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Appends an int value followed by a new line to the string builder using {@code String.valueOf}.
     *
     * @param value the value to append.
     * @return {@code this} instance.
     */
    public TextStringBuilder appendln(final int value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Appends a long value followed by a new line to the string builder using {@code String.valueOf}.
     *
     * @param value the value to append.
     * @return {@code this} instance.
     */
    public TextStringBuilder appendln(final long value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Appends an object followed by a new line to this string builder. Appending null will call {@link #appendNull()}.
     *
     * @param obj the object to append.
     * @return {@code this} instance.
     */
    public TextStringBuilder appendln(final Object obj) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Appends a string followed by a new line to this string builder. Appending null will call {@link #appendNull()}.
     *
     * @param str the string to append.
     * @return {@code this} instance.
     */
    public TextStringBuilder appendln(final String str) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Appends part of a string followed by a new line to this string builder. Appending null will call
     * {@link #appendNull()}.
     *
     * @param str the string to append.
     * @param startIndex the start index, inclusive, must be valid.
     * @param length the length to append, must be valid.
     * @return {@code this} instance.
     */
    public TextStringBuilder appendln(final String str, final int startIndex, final int length) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Calls {@link String#format(String, Object...)} and appends the result.
     *
     * @param format the format string.
     * @param objs the objects to use in the format string.
     * @return {@code this} to enable chaining.
     * @see String#format(String, Object...)
     */
    public TextStringBuilder appendln(final String format, final Object... objs) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Appends a string buffer followed by a new line to this string builder. Appending null will call
     * {@link #appendNull()}.
     *
     * @param str the string buffer to append.
     * @return {@code this} instance.
     */
    public TextStringBuilder appendln(final StringBuffer str) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Appends part of a string buffer followed by a new line to this string builder. Appending null will call
     * {@link #appendNull()}.
     *
     * @param str the string to append.
     * @param startIndex the start index, inclusive, must be valid.
     * @param length the length to append, must be valid.
     * @return {@code this} instance.
     */
    public TextStringBuilder appendln(final StringBuffer str, final int startIndex, final int length) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Appends a string builder followed by a new line to this string builder. Appending null will call
     * {@link #appendNull()}.
     *
     * @param str the string builder to append.
     * @return {@code this} instance.
     */
    public TextStringBuilder appendln(final StringBuilder str) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Appends part of a string builder followed by a new line to this string builder. Appending null will call
     * {@link #appendNull()}.
     *
     * @param str the string builder to append.
     * @param startIndex the start index, inclusive, must be valid.
     * @param length the length to append, must be valid.
     * @return {@code this} instance.
     */
    public TextStringBuilder appendln(final StringBuilder str, final int startIndex, final int length) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Appends another string builder followed by a new line to this string builder. Appending null will call
     * {@link #appendNull()}.
     *
     * @param str the string builder to append.
     * @return {@code this} instance.
     */
    public TextStringBuilder appendln(final TextStringBuilder str) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Appends part of a string builder followed by a new line to this string builder. Appending null will call
     * {@link #appendNull()}.
     *
     * @param str the string to append.
     * @param startIndex the start index, inclusive, must be valid.
     * @param length the length to append, must be valid.
     * @return {@code this} instance.
     */
    public TextStringBuilder appendln(final TextStringBuilder str, final int startIndex, final int length) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Appends this builder's new line string to this builder.
     * <p>
     * By default, the new line is the system default from {@link System#lineSeparator()}.
     * </p>
     * <p>
     * The new line string can be changed using {@link #setNewLineText(String)}. For example, you can use this to force the output to always use Unix line
     * endings even when on Windows.
     * </p>
     *
     * @return {@code this} instance.
     * @see #getNewLineText()
     * @see #setNewLineText(String)
     */
    public TextStringBuilder appendNewLine() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Appends the text representing {@code null} to this string builder.
     *
     * @return {@code this} instance.
     */
    public TextStringBuilder appendNull() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Appends the pad character to the builder the specified number of times.
     *
     * @param length the length to append, negative means no append.
     * @param padChar the character to append.
     * @return {@code this} instance.
     */
    public TextStringBuilder appendPadding(final int length, final char padChar) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Appends a separator if the builder is currently non-empty. The separator is appended using {@link #append(char)}.
     * <p>
     * This method is useful for adding a separator each time around the loop except the first.
     * </p>
     *
     * <pre>
     * for (Iterator it = list.iterator(); it.hasNext();) {
     *     appendSeparator(',');
     *     append(it.next());
     * }
     * </pre>
     *
     * <p>
     * Note that for this simple example, you should use {@link #appendWithSeparators(Iterable, String)}.
     * </p>
     *
     * @param separator the separator to use.
     * @return {@code this} instance.
     */
    public TextStringBuilder appendSeparator(final char separator) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Appends one of both separators to the builder If the builder is currently empty it will append the defaultIfEmpty-separator Otherwise it will append the
     * standard-separator
     * <p>
     * The separator is appended using {@link #append(char)}.
     * </p>
     *
     * @param standard       the separator if builder is not empty.
     * @param defaultIfEmpty the separator if builder is empty.
     * @return {@code this} instance.
     */
    public TextStringBuilder appendSeparator(final char standard, final char defaultIfEmpty) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Appends a separator to the builder if the loop index is greater than zero. The separator is appended using
     * {@link #append(char)}.
     * <p>
     * This method is useful for adding a separator each time around the loop except the first.
     * </p>
     *
     * <pre>
     * for (int i = 0; i &lt; list.size(); i++) {
     *     appendSeparator(",", i);
     *     append(list.get(i));
     * }
     * </pre>
     *
     * <p>
     * Note that for this simple example, you should use {@link #appendWithSeparators(Iterable, String)}.
     * </p>
     *
     * @param separator the separator to use.
     * @param loopIndex the loop index.
     * @return {@code this} instance.
     */
    public TextStringBuilder appendSeparator(final char separator, final int loopIndex) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Appends a separator if the builder is currently non-empty. Appending a null separator will have no effect. The
     * separator is appended using {@link #append(String)}.
     * <p>
     * This method is useful for adding a separator each time around the loop except the first.
     * </p>
     *
     * <pre>
     * for (Iterator it = list.iterator(); it.hasNext();) {
     *     appendSeparator(",");
     *     append(it.next());
     * }
     * </pre>
     *
     * <p>
     * Note that for this simple example, you should use {@link #appendWithSeparators(Iterable, String)}.
     * </p>
     *
     * @param separator the separator to use, null means no separator.
     * @return {@code this} instance.
     */
    public TextStringBuilder appendSeparator(final String separator) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Appends a separator to the builder if the loop index is greater than zero. Appending a null separator will have
     * no effect. The separator is appended using {@link #append(String)}.
     * <p>
     * This method is useful for adding a separator each time around the loop except the first.
     * </p>
     *
     * <pre>
     * for (int i = 0; i &lt; list.size(); i++) {
     *     appendSeparator(",", i);
     *     append(list.get(i));
     * }
     * </pre>
     *
     * <p>
     * Note that for this simple example, you should use {@link #appendWithSeparators(Iterable, String)}.
     * </p>
     *
     * @param separator the separator to use, null means no separator.
     * @param loopIndex the loop index.
     * @return {@code this} instance.
     */
    public TextStringBuilder appendSeparator(final String separator, final int loopIndex) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Appends one of both separators to the StrBuilder. If the builder is currently empty, it will append the
     * defaultIfEmpty-separator, otherwise it will append the standard-separator.
     * <p>
     * Appending a null separator will have no effect. The separator is appended using {@link #append(String)}.
     * </p>
     * <p>
     * This method is for example useful for constructing queries
     * </p>
     *
     * <pre>
     * StrBuilder whereClause = new StrBuilder();
     * if (searchCommand.getPriority() != null) {
     *  whereClause.appendSeparator(" and", " where");
     *  whereClause.append(" priority = ?")
     * }
     * if (searchCommand.getComponent() != null) {
     *  whereClause.appendSeparator(" and", " where");
     *  whereClause.append(" component = ?")
     * }
     * selectClause.append(whereClause)
     * </pre>
     *
     * @param standard the separator if builder is not empty, null means no separator.
     * @param defaultIfEmpty the separator if builder is empty, null means no separator.
     * @return {@code this} instance.
     */
    public TextStringBuilder appendSeparator(final String standard, final String defaultIfEmpty) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Appends current contents of this {@code StrBuilder} to the provided {@link Appendable}.
     * <p>
     * This method tries to avoid doing any extra copies of contents.
     * </p>
     *
     * @param appendable the appendable to append data to.
     * @throws IOException if an I/O error occurs.
     * @see #readFrom(Readable)
     */
    public void appendTo(final Appendable appendable) throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Appends {@code "true"}.
     */
    private void appendTrue(int index) {
        buffer[index++] = 't';
        buffer[index++] = 'r';
        buffer[index++] = 'u';
        buffer[index] = 'e';
        size += TRUE_STRING_SIZE;
    }

    /**
     * Appends an iterable placing separators between each value, but not before the first or after the last. Appending
     * a null iterable will have no effect. Each object is appended using {@link #append(Object)}.
     *
     * @param iterable the iterable to append.
     * @param separator the separator to use, null means no separator.
     * @return {@code this} instance.
     */
    public TextStringBuilder appendWithSeparators(final Iterable<?> iterable, final String separator) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Appends an iterator placing separators between each value, but not before the first or after the last. Appending
     * a null iterator will have no effect. Each object is appended using {@link #append(Object)}.
     *
     * @param it the iterator to append.
     * @param separator the separator to use, null means no separator.
     * @return {@code this} instance.
     */
    public TextStringBuilder appendWithSeparators(final Iterator<?> it, final String separator) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Appends an array placing separators between each value, but not before the first or after the last. Appending a
     * null array will have no effect. Each object is appended using {@link #append(Object)}.
     *
     * @param array the array to append.
     * @param separator the separator to use, null means no separator.
     * @return {@code this} instance.
     */
    public TextStringBuilder appendWithSeparators(final Object[] array, final String separator) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Gets the contents of this builder as a Reader.
     * <p>
     * This method allows the contents of the builder to be read using any standard method that expects a Reader.
     * </p>
     * <p>
     * To use, simply create a {@code StrBuilder}, populate it with data, call {@code asReader}, and then read away.
     * </p>
     * <p>
     * The internal character array is shared between the builder and the reader. This allows you to append to the
     * builder after creating the reader, and the changes will be picked up. Note however, that no synchronization
     * occurs, so you must perform all operations with the builder and the reader in one thread.
     * </p>
     * <p>
     * The returned reader supports marking, and ignores the flush method.
     * </p>
     *
     * @return a reader that reads from this builder.
     */
    public Reader asReader() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Creates a tokenizer that can tokenize the contents of this builder.
     * <p>
     * This method allows the contents of this builder to be tokenized. The tokenizer will be setup by default to
     * tokenize on space, tab, newline and form feed (as per StringTokenizer). These values can be changed on the
     * tokenizer class, before retrieving the tokens.
     * </p>
     * <p>
     * The returned tokenizer is linked to this builder. You may intermix calls to the builder and tokenizer within
     * certain limits, however there is no synchronization. Once the tokenizer has been used once, it must be
     * {@link StringTokenizer#reset() reset} to pickup the latest changes in the builder. For example:
     * </p>
     *
     * <pre>
     * StrBuilder b = new StrBuilder();
     * b.append("a b ");
     * StrTokenizer t = b.asTokenizer();
     * String[] tokens1 = t.getTokenArray(); // returns a,b
     * b.append("c d ");
     * String[] tokens2 = t.getTokenArray(); // returns a,b (c and d ignored)
     * t.reset(); // reset causes builder changes to be picked up
     * String[] tokens3 = t.getTokenArray(); // returns a,b,c,d
     * </pre>
     *
     * <p>
     * In addition to simply intermixing appends and tokenization, you can also call the set methods on the tokenizer to
     * alter how it tokenizes. Just remember to call reset when you want to pickup builder changes.
     * </p>
     * <p>
     * Calling {@link StringTokenizer#reset(String)} or {@link StringTokenizer#reset(char[])} with a non-null value will
     * break the link with the builder.
     * </p>
     *
     * @return a tokenizer that is linked to this builder.
     */
    public StringTokenizer asTokenizer() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Gets this builder as a Writer that can be written to.
     * <p>
     * This method allows you to populate the contents of the builder using any standard method that takes a Writer.
     * </p>
     * <p>
     * To use, simply create a {@code StrBuilder}, call {@code asWriter}, and populate away. The data is available at
     * any time using the methods of the {@code StrBuilder}.
     * </p>
     * <p>
     * The internal character array is shared between the builder and the writer. This allows you to intermix calls that
     * append to the builder and write using the writer and the changes will be occur correctly. Note however, that no
     * synchronization occurs, so you must perform all operations with the builder and the writer in one thread.
     * </p>
     * <p>
     * The returned writer ignores the close and flush methods.
     * </p>
     *
     * @return a writer that populates this builder.
     */
    public Writer asWriter() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Converts this instance to a String.
     *
     * @return This instance as a String.
     * @see #toString()
     * @deprecated Use {@link #get()}.
     */
    @Deprecated
    @Override
    public String build() {
        return toString();
    }

    /**
     * Gets the current size of the internal character array buffer.
     *
     * @return The capacity.
     */
    public int capacity() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Gets the character at the specified index.
     *
     * @param index the index to retrieve, must be valid.
     * @return The character at the index.
     * @throws IndexOutOfBoundsException if the index is invalid.
     * @see #setCharAt(int, char)
     * @see #deleteCharAt(int)
     */
    @Override
    public char charAt(final int index) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Clears the string builder (convenience Collections API style method).
     * <p>
     * This method does not reduce the size of the internal character buffer. To do that, call {@code clear()} followed
     * by {@link #minimizeCapacity()}.
     * </p>
     * <p>
     * This method is the same as {@link #setLength(int)} called with zero and is provided to match the API of
     * Collections.
     * </p>
     *
     * @return {@code this} instance.
     */
    public TextStringBuilder clear() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Tests if the string builder contains the specified char.
     *
     * @param ch the character to find.
     * @return true if the builder contains the character.
     */
    public boolean contains(final char ch) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Tests if the string builder contains the specified string.
     *
     * @param str the string to find.
     * @return true if the builder contains the string.
     */
    public boolean contains(final String str) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Tests if the string builder contains a string matched using the specified matcher.
     * <p>
     * Matchers can be used to perform advanced searching behavior. For example you could write a matcher to search for
     * the character 'a' followed by a number.
     * </p>
     *
     * @param matcher the matcher to use, null returns -1.
     * @return true if the matcher finds a match in the builder.
     */
    public boolean contains(final StringMatcher matcher) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Deletes the characters between the two specified indices.
     *
     * @param startIndex the start index, inclusive, must be valid.
     * @param endIndex the end index, exclusive, must be valid except that if too large it is treated as end of string.
     * @return {@code this} instance.
     * @throws IndexOutOfBoundsException if the index is invalid.
     */
    public TextStringBuilder delete(final int startIndex, final int endIndex) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Deletes the character wherever it occurs in the builder.
     *
     * @param ch the character to delete.
     * @return {@code this} instance.
     */
    public TextStringBuilder deleteAll(final char ch) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Deletes the string wherever it occurs in the builder.
     *
     * @param str the string to delete, null causes no action.
     * @return {@code this} instance.
     */
    public TextStringBuilder deleteAll(final String str) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Deletes all parts of the builder that the matcher matches.
     * <p>
     * Matchers can be used to perform advanced deletion behavior. For example you could write a matcher to delete all
     * occurrences where the character 'a' is followed by a number.
     * </p>
     *
     * @param matcher the matcher to use to find the deletion, null causes no action.
     * @return {@code this} instance.
     */
    public TextStringBuilder deleteAll(final StringMatcher matcher) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Deletes the character at the specified index.
     *
     * @param index the index to delete.
     * @return {@code this} instance.
     * @throws IndexOutOfBoundsException if the index is invalid.
     * @see #charAt(int)
     * @see #setCharAt(int, char)
     */
    public TextStringBuilder deleteCharAt(final int index) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Deletes the character wherever it occurs in the builder.
     *
     * @param ch the character to delete.
     * @return {@code this} instance.
     */
    public TextStringBuilder deleteFirst(final char ch) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Deletes the string wherever it occurs in the builder.
     *
     * @param str the string to delete, null causes no action.
     * @return {@code this} instance.
     */
    public TextStringBuilder deleteFirst(final String str) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Deletes the first match within the builder using the specified matcher.
     * <p>
     * Matchers can be used to perform advanced deletion behavior. For example you could write a matcher to delete where
     * the character 'a' is followed by a number.
     * </p>
     *
     * @param matcher the matcher to use to find the deletion, null causes no action.
     * @return {@code this} instance.
     */
    public TextStringBuilder deleteFirst(final StringMatcher matcher) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Internal method to delete a range without validation.
     *
     * @param startIndex the start index, must be valid.
     * @param endIndex the end index (exclusive), must be valid.
     * @param len the length, must be valid.
     * @throws IndexOutOfBoundsException if any index is invalid.
     */
    private void deleteImpl(final int startIndex, final int endIndex, final int len) {
        System.arraycopy(buffer, endIndex, buffer, startIndex, size - endIndex);
        size -= len;
    }

    /**
     * Gets the character at the specified index before deleting it.
     *
     * @param index the index to retrieve, must be valid
     * @return The character at the index
     * @throws IndexOutOfBoundsException if the index is invalid
     * @since 1.9
     * @see #charAt(int)
     * @see #deleteCharAt(int)
     */
    public char drainChar(final int index) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Drains (copies, then deletes) this character sequence into the specified array. This is equivalent to copying the
     * characters from this sequence into the target and then deleting those character from this sequence.
     *
     * @param startIndex first index to copy, inclusive.
     * @param endIndex last index to copy, exclusive.
     * @param target the target array, must not be {@code null}.
     * @param targetIndex the index to start copying in the target.
     * @return How many characters where copied (then deleted). If this builder is empty, return {@code 0}.
     * @since 1.9
     */
    public int drainChars(final int startIndex, final int endIndex, final char[] target, final int targetIndex) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Checks whether this builder ends with the specified string.
     * <p>
     * Note that this method handles null input quietly, unlike String.
     * </p>
     *
     * @param str the string to search for, null returns false.
     * @return true if the builder ends with the string.
     */
    public boolean endsWith(final String str) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Tests the capacity and ensures that it is at least the size specified.
     *
     * <p>
     * Note: This method can be used to minimise memory reallocations during
     * repeated addition of values by pre-allocating the character buffer.
     * The method ignores a negative {@code capacity} argument.
     * </p>
     *
     * @param capacity the capacity to ensure.
     * @return {@code this} instance.
     * @throws OutOfMemoryError if the capacity cannot be allocated.
     */
    public TextStringBuilder ensureCapacity(final int capacity) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Ensures that the buffer is at least the size specified. The {@code capacity} argument
     * is treated as an unsigned integer.
     *
     * <p>
     * This method will raise an {@link OutOfMemoryError} if the capacity is too large
     * for an array, or cannot be allocated.
     * </p>
     *
     * @param capacity the capacity to ensure.
     * @throws OutOfMemoryError if the capacity cannot be allocated.
     */
    private void ensureCapacityInternal(final int capacity) {
        // Check for overflow of the current buffer.
        // Assumes capacity is an unsigned integer up to Integer.MAX_VALUE * 2
        // (the largest possible addition of two maximum length arrays).
        if (capacity - buffer.length > 0) {
            resizeBuffer(capacity);
        }
    }

    /**
     * Tests the contents of this builder against another to see if they contain the same character content.
     *
     * @param obj the object to check, null returns false.
     * @return true if the builders contain the same characters in the same order.
     */
    @Override
    public boolean equals(final Object obj) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Tests the contents of this builder against another to see if they contain the same character content.
     *
     * @param other the object to check, null returns false.
     * @return true if the builders contain the same characters in the same order.
     */
    public boolean equals(final TextStringBuilder other) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Tests the contents of this builder against another to see if they contain the same character content ignoring
     * case.
     *
     * @param other the object to check, null returns false.
     * @return true if the builders contain the same characters in the same order.
     */
    public boolean equalsIgnoreCase(final TextStringBuilder other) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Converts this instance to a String.
     *
     * @return This instance as a String.
     * @see #toString()
     * @since 1.12.0
     */
    @Override
    public String get() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Gets a direct reference to internal storage, not for public consumption.
     */
    char[] getBuffer() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Copies this character array into the specified array.
     *
     * @param target the target array, null will cause an array to be created.
     * @return The input array, unless that was null or too small.
     */
    public char[] getChars(char[] target) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Copies this character array into the specified array.
     *
     * @param startIndex first index to copy, inclusive, must be valid.
     * @param endIndex last index to copy, exclusive, must be valid.
     * @param target the target array, must not be null or too small.
     * @param targetIndex the index to start copying in target.
     * @throws NullPointerException if the array is null.
     * @throws IndexOutOfBoundsException if any index is invalid.
     */
    public void getChars(final int startIndex, final int endIndex, final char[] target, final int targetIndex) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Gets the text to be appended when a {@link #appendNewLine() new line} is added.
     *
     * @return The new line text, {@code null} means use the system default from {@link System#lineSeparator()}.
     */
    public String getNewLineText() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Gets the text to be appended when null is added.
     *
     * @return The null text, null means no append.
     */
    public String getNullText() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Gets a suitable hash code for this builder.
     *
     * @return a hash code.
     */
    @Override
    public int hashCode() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Searches the string builder to find the first reference to the specified char.
     *
     * @param ch the character to find.
     * @return The first index of the character, or -1 if not found.
     */
    public int indexOf(final char ch) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Searches the string builder to find the first reference to the specified char.
     *
     * @param ch the character to find.
     * @param startIndex the index to start at, invalid index rounded to edge.
     * @return The first index of the character, or -1 if not found.
     */
    public int indexOf(final char ch, int startIndex) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Searches the string builder to find the first reference to the specified string.
     * <p>
     * Note that a null input string will return -1, whereas the JDK throws an exception.
     * </p>
     *
     * @param str the string to find, null returns -1.
     * @return The first index of the string, or -1 if not found.
     */
    public int indexOf(final String str) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Searches the string builder to find the first reference to the specified string starting searching from the given
     * index.
     * <p>
     * Note that a null input string will return -1, whereas the JDK throws an exception.
     * </p>
     *
     * @param str the string to find, null returns -1.
     * @param startIndex the index to start at, invalid index rounded to edge.
     * @return The first index of the string, or -1 if not found.
     */
    public int indexOf(final String str, int startIndex) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Searches the string builder using the matcher to find the first match.
     * <p>
     * Matchers can be used to perform advanced searching behavior. For example you could write a matcher to find the
     * character 'a' followed by a number.
     * </p>
     *
     * @param matcher the matcher to use, null returns -1.
     * @return The first index matched, or -1 if not found.
     */
    public int indexOf(final StringMatcher matcher) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Searches the string builder using the matcher to find the first match searching from the given index.
     * <p>
     * Matchers can be used to perform advanced searching behavior. For example you could write a matcher to find the
     * character 'a' followed by a number.
     * </p>
     *
     * @param matcher the matcher to use, null returns -1.
     * @param startIndex the index to start at, invalid index rounded to edge.
     * @return The first index matched, or -1 if not found.
     */
    public int indexOf(final StringMatcher matcher, int startIndex) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Inserts the value into this builder.
     *
     * @param index the index to add at, must be valid.
     * @param value the value to insert.
     * @return {@code this} instance.
     * @throws IndexOutOfBoundsException if the index is invalid.
     */
    public TextStringBuilder insert(final int index, final boolean value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Inserts the value into this builder.
     *
     * @param index the index to add at, must be valid.
     * @param value the value to insert.
     * @return {@code this} instance.
     * @throws IndexOutOfBoundsException if the index is invalid.
     */
    public TextStringBuilder insert(final int index, final char value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Inserts the character array into this builder. Inserting null will use the stored null text value.
     *
     * @param index the index to add at, must be valid.
     * @param chars the char array to insert.
     * @return {@code this} instance.
     * @throws IndexOutOfBoundsException if the index is invalid.
     */
    public TextStringBuilder insert(final int index, final char[] chars) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Inserts part of the character array into this builder. Inserting null will use the stored null text value.
     *
     * @param index the index to add at, must be valid.
     * @param chars the char array to insert.
     * @param offset the offset into the character array to start at, must be valid.
     * @param length the length of the character array part to copy, must be positive.
     * @return {@code this} instance.
     * @throws IndexOutOfBoundsException if any index is invalid.
     */
    public TextStringBuilder insert(final int index, final char[] chars, final int offset, final int length) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Inserts the value into this builder.
     *
     * @param index the index to add at, must be valid.
     * @param value the value to insert.
     * @return {@code this} instance.
     * @throws IndexOutOfBoundsException if the index is invalid.
     */
    public TextStringBuilder insert(final int index, final double value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Inserts the value into this builder.
     *
     * @param index the index to add at, must be valid.
     * @param value the value to insert.
     * @return {@code this} instance.
     * @throws IndexOutOfBoundsException if the index is invalid.
     */
    public TextStringBuilder insert(final int index, final float value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Inserts the value into this builder.
     *
     * @param index the index to add at, must be valid.
     * @param value the value to insert.
     * @return {@code this} instance.
     * @throws IndexOutOfBoundsException if the index is invalid.
     */
    public TextStringBuilder insert(final int index, final int value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Inserts the value into this builder.
     *
     * @param index the index to add at, must be valid.
     * @param value the value to insert.
     * @return {@code this} instance.
     * @throws IndexOutOfBoundsException if the index is invalid.
     */
    public TextStringBuilder insert(final int index, final long value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Inserts the string representation of an object into this builder. Inserting null will use the stored null text
     * value.
     *
     * @param index the index to add at, must be valid.
     * @param obj the object to insert.
     * @return {@code this} instance.
     * @throws IndexOutOfBoundsException if the index is invalid.
     */
    public TextStringBuilder insert(final int index, final Object obj) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Inserts the string into this builder. Inserting null will use the stored null text value.
     *
     * @param index the index to add at, must be valid.
     * @param str the string to insert.
     * @return {@code this} instance.
     * @throws IndexOutOfBoundsException if the index is invalid.
     */
    public TextStringBuilder insert(final int index, String str) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Checks is the string builder is empty (convenience Collections API style method).
     * <p>
     * This method is the same as checking {@link #length()} and is provided to match the API of Collections.
     * </p>
     *
     * @return {@code true} if the size is {@code 0}.
     */
    public boolean isEmpty() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Checks is the string builder is not empty.
     * <p>
     * This method is the same as checking {@link #length()}.
     * </p>
     *
     * @return {@code true} if the size is not {@code 0}.
     * @since 1.9
     */
    public boolean isNotEmpty() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Gets whether the internal buffer has been reallocated.
     *
     * @return Whether the internal buffer has been reallocated.
     * @since 1.9
     */
    public boolean isReallocated() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Searches the string builder to find the last reference to the specified char.
     *
     * @param ch the character to find.
     * @return The last index of the character, or -1 if not found.
     */
    public int lastIndexOf(final char ch) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Searches the string builder to find the last reference to the specified char.
     *
     * @param ch the character to find.
     * @param startIndex the index to start at, invalid index rounded to edge.
     * @return The last index of the character, or -1 if not found.
     */
    public int lastIndexOf(final char ch, int startIndex) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Searches the string builder to find the last reference to the specified string.
     * <p>
     * Note that a null input string will return -1, whereas the JDK throws an exception.
     * </p>
     *
     * @param str the string to find, null returns -1.
     * @return The last index of the string, or -1 if not found.
     */
    public int lastIndexOf(final String str) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Searches the string builder to find the last reference to the specified string starting searching from the given
     * index.
     * <p>
     * Note that a null input string will return -1, whereas the JDK throws an exception.
     * </p>
     *
     * @param str the string to find, null returns -1.
     * @param startIndex the index to start at, invalid index rounded to edge.
     * @return The last index of the string, or -1 if not found.
     */
    public int lastIndexOf(final String str, int startIndex) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Searches the string builder using the matcher to find the last match.
     * <p>
     * Matchers can be used to perform advanced searching behavior. For example you could write a matcher to find the
     * character 'a' followed by a number.
     * </p>
     *
     * @param matcher the matcher to use, null returns -1.
     * @return The last index matched, or -1 if not found.
     */
    public int lastIndexOf(final StringMatcher matcher) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Searches the string builder using the matcher to find the last match searching from the given index.
     * <p>
     * Matchers can be used to perform advanced searching behavior. For example you could write a matcher to find the
     * character 'a' followed by a number.
     * </p>
     *
     * @param matcher the matcher to use, null returns -1.
     * @param startIndex the index to start at, invalid index rounded to edge.
     * @return The last index matched, or -1 if not found.
     */
    public int lastIndexOf(final StringMatcher matcher, int startIndex) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Extracts the leftmost characters from the string builder without throwing an exception.
     * <p>
     * This method extracts the left {@code length} characters from the builder. If this many characters are not
     * available, the whole builder is returned. Thus the returned string may be shorter than the length requested.
     * </p>
     *
     * @param length the number of characters to extract, negative returns empty string.
     * @return The new string.
     */
    public String leftString(final int length) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Gets the length of the string builder.
     *
     * @return The length
     */
    @Override
    public int length() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Extracts some characters from the middle of the string builder without throwing an exception.
     * <p>
     * This method extracts {@code length} characters from the builder at the specified index. If the index is negative
     * it is treated as zero. If the index is greater than the builder size, it is treated as the builder size. If the
     * length is negative, the empty string is returned. If insufficient characters are available in the builder, as
     * much as possible is returned. Thus the returned string may be shorter than the length requested.
     * </p>
     *
     * @param index the index to start at, negative means zero.
     * @param length the number of characters to extract, negative returns empty string.
     * @return The new string.
     */
    public String midString(int index, final int length) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Minimizes the capacity to the actual length of the string.
     *
     * @return {@code this} instance.
     */
    public TextStringBuilder minimizeCapacity() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * If possible, reads chars from the provided {@link CharBuffer} directly into underlying character buffer without
     * making extra copies.
     *
     * @param charBuffer CharBuffer to read.
     * @return The number of characters read.
     * @see #appendTo(Appendable)
     * @since 1.9
     */
    public int readFrom(final CharBuffer charBuffer) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * If possible, reads all chars from the provided {@link Readable} directly into underlying character buffer without
     * making extra copies.
     *
     * @param readable object to read from.
     * @return The number of characters read.
     * @throws IOException if an I/O error occurs.
     * @see #appendTo(Appendable)
     */
    public int readFrom(final Readable readable) throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * If possible, reads all chars from the provided {@link Reader} directly into underlying character buffer without
     * making extra copies.
     *
     * @param reader Reader to read.
     * @return The number of characters read or -1 if we reached the end of stream.
     * @throws IOException if an I/O error occurs.
     * @see #appendTo(Appendable)
     * @since 1.9
     */
    public int readFrom(final Reader reader) throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * If possible, reads {@code count} chars from the provided {@link Reader} directly into underlying character buffer without making extra copies.
     *
     * @param reader Reader to read.
     * @param count  The maximum characters to read, a value &lt;= 0 returns 0.
     * @return The number of characters read. If less than {@code count}, then we've reached the end-of-stream, or -1 if we reached the end of stream.
     * @throws IOException if an I/O error occurs.
     * @see #appendTo(Appendable)
     * @since 1.9
     */
    public int readFrom(final Reader reader, final int count) throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Reallocates the buffer to the new length.
     *
     * @param newLength the length of the copy to be returned.
     */
    private void reallocate(final int newLength) {
        this.buffer = Arrays.copyOf(buffer, newLength);
        this.reallocations++;
    }

    /**
     * Replaces a portion of the string builder with another string. The length of the inserted string does not have to match the removed length.
     *
     * @param startIndex the start index, inclusive, must be valid.
     * @param endIndex   the end index, exclusive, must be valid except that if too large it is treated as end of string.
     * @param replaceStr the string to replace with, null means delete range.
     * @return {@code this} instance.
     * @throws IndexOutOfBoundsException if the index is invalid.
     */
    public TextStringBuilder replace(final int startIndex, int endIndex, final String replaceStr) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Advanced search and replaces within the builder using a matcher.
     * <p>
     * Matchers can be used to perform advanced behavior. For example you could write a matcher to delete all occurrences where the character 'a' is followed by
     * a number.
     * </p>
     *
     * @param matcher      the matcher to use to find the deletion, null causes no action.
     * @param replaceStr   the string to replace the match with, null is a delete.
     * @param startIndex   the start index, inclusive, must be valid.
     * @param endIndex     the end index, exclusive, must be valid except that if too large it is treated as end of string.
     * @param replaceCount the number of times to replace, -1 for replace all.
     * @return {@code this} instance.
     * @throws IndexOutOfBoundsException if start index is invalid.
     */
    public TextStringBuilder replace(final StringMatcher matcher, final String replaceStr, final int startIndex, int endIndex, final int replaceCount) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Replaces the search character with the replace character throughout the builder.
     *
     * @param search the search character.
     * @param replace the replace character.
     * @return {@code this} instance.
     */
    public TextStringBuilder replaceAll(final char search, final char replace) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Replaces the search string with the replace string throughout the builder.
     *
     * @param searchStr the search string, null causes no action to occur.
     * @param replaceStr the replace string, null is equivalent to an empty string.
     * @return {@code this} instance.
     */
    public TextStringBuilder replaceAll(final String searchStr, final String replaceStr) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Replaces all matches within the builder with the replace string.
     * <p>
     * Matchers can be used to perform advanced replace behavior. For example you could write a matcher to replace all
     * occurrences where the character 'a' is followed by a number.
     * </p>
     *
     * @param matcher the matcher to use to find the deletion, null causes no action.
     * @param replaceStr the replace string, null is equivalent to an empty string.
     * @return {@code this} instance.
     */
    public TextStringBuilder replaceAll(final StringMatcher matcher, final String replaceStr) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Replaces the first instance of the search character with the replace character in the builder.
     *
     * @param search the search character.
     * @param replace the replace character.
     * @return {@code this} instance.
     */
    public TextStringBuilder replaceFirst(final char search, final char replace) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Replaces the first instance of the search string with the replace string.
     *
     * @param searchStr the search string, null causes no action to occur.
     * @param replaceStr the replace string, null is equivalent to an empty string.
     * @return {@code this} instance.
     */
    public TextStringBuilder replaceFirst(final String searchStr, final String replaceStr) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Replaces the first match within the builder with the replace string.
     * <p>
     * Matchers can be used to perform advanced replace behavior. For example you could write a matcher to replace where
     * the character 'a' is followed by a number.
     * </p>
     *
     * @param matcher the matcher to use to find the deletion, null causes no action.
     * @param replaceStr the replace string, null is equivalent to an empty string.
     * @return {@code this} instance.
     */
    public TextStringBuilder replaceFirst(final StringMatcher matcher, final String replaceStr) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Internal method to delete a range without validation.
     *
     * @param startIndex the start index, must be valid.
     * @param endIndex the end index (exclusive), must be valid.
     * @param removeLen the length to remove (endIndex - startIndex), must be valid.
     * @param insertStr the string to replace with, null means delete range.
     * @param insertLen the length of the insert string, must be valid.
     * @throws IndexOutOfBoundsException if any index is invalid.
     */
    private void replaceImpl(final int startIndex, final int endIndex, final int removeLen, final String insertStr, final int insertLen) {
        final int newSize = size - removeLen + insertLen;
        if (insertLen != removeLen) {
            ensureCapacityInternal(newSize);
            System.arraycopy(buffer, endIndex, buffer, startIndex + insertLen, size - endIndex);
            size = newSize;
        }
        if (insertLen > 0) {
            insertStr.getChars(0, insertLen, buffer, startIndex);
        }
    }

    /**
     * Replaces within the builder using a matcher.
     * <p>
     * Matchers can be used to perform advanced behavior. For example you could write a matcher to delete all
     * occurrences where the character 'a' is followed by a number.
     * </p>
     *
     * @param matcher the matcher to use to find the deletion, null causes no action.
     * @param replaceStr the string to replace the match with, null is a delete.
     * @param from the start index, must be valid.
     * @param to the end index (exclusive), must be valid.
     * @param replaceCount the number of times to replace, -1 for replace all.
     * @return {@code this} instance.
     * @throws IndexOutOfBoundsException if any index is invalid.
     */
    private TextStringBuilder replaceImpl(final StringMatcher matcher, final String replaceStr, final int from, int to, int replaceCount) {
        if (matcher == null || size == 0) {
            return this;
        }
        final int replaceLen = replaceStr == null ? 0 : replaceStr.length();
        for (int i = from; i < to && replaceCount != 0; i++) {
            final char[] buf = buffer;
            final int removeLen = matcher.isMatch(buf, i, from, to);
            if (removeLen > 0) {
                replaceImpl(i, i + removeLen, removeLen, replaceStr, replaceLen);
                to = to - removeLen + replaceLen;
                i = i + replaceLen - 1;
                if (replaceCount > 0) {
                    replaceCount--;
                }
            }
        }
        return this;
    }

    /**
     * Resizes the buffer to at least the size specified.
     *
     * @param minCapacity the minimum required capacity.
     * @throws OutOfMemoryError if the {@code minCapacity} is negative.
     */
    private void resizeBuffer(final int minCapacity) {
        // Overflow-conscious code treats the min and new capacity as unsigned.
        final int oldCapacity = buffer.length;
        int newCapacity = oldCapacity * 2;
        if (Integer.compareUnsigned(newCapacity, minCapacity) < 0) {
            newCapacity = minCapacity;
        }
        if (Integer.compareUnsigned(newCapacity, MAX_BUFFER_SIZE) > 0) {
            newCapacity = createPositiveCapacity(minCapacity);
        }
        reallocate(newCapacity);
    }

    /**
     * Reverses the string builder placing each character in the opposite index.
     *
     * @return {@code this} instance.
     */
    public TextStringBuilder reverse() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Extracts the rightmost characters from the string builder without throwing an exception.
     * <p>
     * This method extracts the right {@code length} characters from the builder. If this many characters are not
     * available, the whole builder is returned. Thus the returned string may be shorter than the length requested.
     * </p>
     *
     * @param length the number of characters to extract, negative returns empty string.
     * @return The new string.
     */
    public String rightString(final int length) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Clears and sets this builder to the given value.
     *
     * @param str the new value.
     * @return {@code this} instance.
     * @since 1.9
     * @see #charAt(int)
     * @see #deleteCharAt(int)
     */
    public TextStringBuilder set(final CharSequence str) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Sets the character at the specified index.
     *
     * @param index the index to set
     * @param ch the new character
     * @return {@code this} instance.
     * @throws IndexOutOfBoundsException if the index is invalid
     * @see #charAt(int)
     * @see #deleteCharAt(int)
     */
    public TextStringBuilder setCharAt(final int index, final char ch) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Updates the length of the builder by either dropping the last characters or adding filler of Unicode zero.
     *
     * @param length the length to set to, must be zero or positive.
     * @return {@code this} instance.
     * @throws IndexOutOfBoundsException if the length is negative.
     */
    public TextStringBuilder setLength(final int length) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Sets the text to be appended when {@link #appendNewLine() new line} is called.
     *
     * @param newLine the new line text, {@code null} means use the system default from {@link System#lineSeparator()}.
     * @return {@code this} instance.
     */
    public TextStringBuilder setNewLineText(final String newLine) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Sets the text to be appended when null is added.
     *
     * @param nullText the null text, null means no append.
     * @return {@code this} instance.
     */
    public TextStringBuilder setNullText(String nullText) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Gets the length of the string builder.
     * <p>
     * This method is the same as {@link #length()} and is provided to match the API of Collections.
     * </p>
     *
     * @return The length.
     */
    public int size() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Checks whether this builder starts with the specified string.
     * <p>
     * Note that this method handles null input quietly, unlike String.
     * </p>
     *
     * @param str the string to search for, null returns false.
     * @return true if the builder starts with the string.
     */
    public boolean startsWith(final String str) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CharSequence subSequence(final int startIndex, final int endIndex) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Extracts a portion of this string builder as a string.
     *
     * @param start the start index, inclusive, must be valid.
     * @return The new string.
     * @throws IndexOutOfBoundsException if the index is invalid.
     */
    public String substring(final int start) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Extracts a portion of this string builder as a string.
     * <p>
     * Note: This method treats an endIndex greater than the length of the builder as equal to the length of the
     * builder, and continues without error, unlike StringBuffer or String.
     * </p>
     *
     * @param startIndex the start index, inclusive, must be valid.
     * @param endIndex the end index, exclusive, must be valid except that if too large it is treated as end of string.
     * @return The new string.
     * @throws IndexOutOfBoundsException if the index is invalid.
     */
    public String substring(final int startIndex, int endIndex) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Copies the builder's character array into a new character array.
     *
     * @return a new array that represents the contents of the builder.
     */
    public char[] toCharArray() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Copies part of the builder's character array into a new character array.
     *
     * @param startIndex the start index, inclusive, must be valid.
     * @param endIndex   the end index, exclusive, must be valid except that if too large it is treated as end of string.
     * @return a new array that holds part of the contents of the builder.
     * @throws IndexOutOfBoundsException if startIndex is invalid, or if endIndex is invalid (but endIndex greater than size is valid).
     */
    public char[] toCharArray(final int startIndex, int endIndex) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Gets a String version of the string builder, creating a new instance each time the method is called.
     * <p>
     * Note that unlike StringBuffer, the string version returned is independent of the string builder.
     * </p>
     *
     * @return The builder as a String.
     */
    @Override
    public String toString() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Gets a StringBuffer version of the string builder, creating a new instance each time the method is called.
     *
     * @return The builder as a StringBuffer.
     */
    public StringBuffer toStringBuffer() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Gets a StringBuilder version of the string builder, creating a new instance each time the method is called.
     *
     * @return The builder as a StringBuilder.
     */
    public StringBuilder toStringBuilder() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Trims the builder by removing characters less than or equal to a space from the beginning and end.
     *
     * @return {@code this} instance.
     */
    public TextStringBuilder trim() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Validates that an index is in the range {@code 0 <= index < size}.
     *
     * @param index the index to test.
     * @throws IndexOutOfBoundsException Thrown when the index is not the range {@code 0 <= index < size}.
     */
    protected void validateIndex(final int index) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Validates parameters defining a range of the builder.
     *
     * @param startIndex the start index, inclusive, must be valid.
     * @param endIndex the end index, exclusive, must be valid except that if too large it is treated as end of string.
     * @return A valid end index.
     * @throws StringIndexOutOfBoundsException if the index is invalid.
     */
    protected int validateRange(final int startIndex, int endIndex) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
