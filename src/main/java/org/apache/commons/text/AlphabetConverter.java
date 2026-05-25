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

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * <p>
 * Convert from one alphabet to another, with the possibility of leaving certain
 * characters unencoded.
 * </p>
 *
 * <p>
 * The target and 'do not encode' languages must be in the Unicode BMP, but the
 * source language does not.
 * </p>
 *
 * <p>
 * The encoding will all be of a fixed length, except for the 'do not encode'
 * chars, which will be of length 1
 * </p>
 *
 * <h2>Sample usage</h2>
 *
 * <pre>
 * Character[] originals;   // a, b, c, d
 * Character[] encoding;    // 0, 1, d
 * Character[] doNotEncode; // d
 *
 * AlphabetConverter ac = AlphabetConverter.createConverterFromChars(originals,
 * encoding, doNotEncode);
 *
 * ac.encode("a");    // 00
 * ac.encode("b");    // 01
 * ac.encode("c");    // 0d
 * ac.encode("d");    // d
 * ac.encode("abcd"); // 00010dd
 * </pre>
 *
 * <p>
 * #ThreadSafe# AlphabetConverter class methods are thread-safe as they do not
 * change internal state.
 * </p>
 *
 * @since 1.0
 */
public final class AlphabetConverter {

    /**
     * Arrow constant, used for converting the object into a string.
     */
    private static final String ARROW = " -> ";

    /**
     * Creates new String that contains just the given code point.
     *
     * @param i code point.
     * @return a new string with the new code point.
     * @see "http://www.oracle.com/us/technologies/java/supplementary-142654.html"
     */
    private static String codePointToString(final int i) {
        if (Character.charCount(i) == 1) {
            return String.valueOf((char) i);
        }
        return new String(Character.toChars(i));
    }

    /**
     * Converts characters to integers.
     *
     * @param chars array of characters.
     * @return an equivalent array of integers.
     */
    private static Integer[] convertCharsToIntegers(final Character[] chars) {
        if (ArrayUtils.isEmpty(chars)) {
            return ArrayUtils.EMPTY_INTEGER_OBJECT_ARRAY;
        }
        final Integer[] integers = new Integer[chars.length];
        Arrays.setAll(integers, i -> (int) chars[i]);
        return integers;
    }

    /**
     * Creates an alphabet converter, for converting from the original alphabet, to the encoded alphabet, while leaving the characters in <em>doNotEncode</em>
     * as they are (if possible).
     *
     * <p>
     * Duplicate letters in either original or encoding will be ignored.
     * </p>
     *
     * @param original    an array of ints representing the original alphabet in code points.
     * @param encoding    an array of ints representing the alphabet to be used for encoding, in code points.
     * @param doNotEncode an array of ints representing the chars to be encoded using the original alphabet - every char here must appear in both the previous
     *                    params.
     * @return The AlphabetConverter.
     * @throws IllegalArgumentException if an AlphabetConverter cannot be constructed.
     */
    public static AlphabetConverter createConverter(final Integer[] original, final Integer[] encoding, final Integer[] doNotEncode) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Creates an alphabet converter, for converting from the original alphabet,
     * to the encoded alphabet, while leaving the characters in
     * <em>doNotEncode</em> as they are (if possible).
     *
     * <p>Duplicate letters in either original or encoding will be ignored.</p>
     *
     * @param original an array of chars representing the original alphabet
     * @param encoding an array of chars representing the alphabet to be used
     *                 for encoding
     * @param doNotEncode an array of chars to be encoded using the original
     *                    alphabet - every char here must appear in
     *                    both the previous params
     * @return The AlphabetConverter
     * @throws IllegalArgumentException if an AlphabetConverter cannot be
     *                                  constructed
     */
    public static AlphabetConverter createConverterFromChars(final Character[] original, final Character[] encoding, final Character[] doNotEncode) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Creates a new converter from a map.
     *
     * @param originalToEncoded a map returned from getOriginalToEncoded().
     * @return The reconstructed AlphabetConverter.
     * @see AlphabetConverter#getOriginalToEncoded()
     */
    public static AlphabetConverter createConverterFromMap(final Map<Integer, String> originalToEncoded) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Original string to be encoded.
     */
    private final Map<Integer, String> originalToEncoded;

    /**
     * Encoding alphabet.
     */
    private final Map<String, String> encodedToOriginal;

    /**
     * Length of the encoded letter.
     */
    private final int encodedLetterLength;

    /**
     * Hidden constructor for alphabet converter. Used by static helper methods.
     *
     * @param originalToEncoded original string to be encoded.
     * @param encodedToOriginal encoding alphabet.
     * @param encodedLetterLength length of the encoded letter.
     */
    private AlphabetConverter(final Map<Integer, String> originalToEncoded, final Map<String, String> encodedToOriginal, final int encodedLetterLength) {
        this.originalToEncoded = originalToEncoded;
        this.encodedToOriginal = encodedToOriginal;
        this.encodedLetterLength = encodedLetterLength;
    }

    /**
     * Recursive method used when creating encoder/decoder.
     *
     * @param level at which point it should add a single encoding.
     * @param currentEncoding current encoding.
     * @param encoding letters encoding.
     * @param originals original values.
     * @param doNotEncodeMap map of values that should not be encoded.
     */
    private void addSingleEncoding(final int level, final String currentEncoding, final Collection<Integer> encoding, final Iterator<Integer> originals, final Map<Integer, String> doNotEncodeMap) {
        if (level > 0) {
            for (final int encodingLetter : encoding) {
                if (!originals.hasNext()) {
                    // done encoding all the original alphabet
                    return;
                }
                // this skips the doNotEncode chars if they are in the
                // leftmost place
                if (level != encodedLetterLength || !doNotEncodeMap.containsKey(encodingLetter)) {
                    addSingleEncoding(level - 1, currentEncoding + codePointToString(encodingLetter), encoding, originals, doNotEncodeMap);
                }
            }
        } else {
            Integer next = originals.next();
            while (doNotEncodeMap.containsKey(next)) {
                final String originalLetterAsString = codePointToString(next);
                originalToEncoded.put(next, originalLetterAsString);
                encodedToOriginal.put(originalLetterAsString, originalLetterAsString);
                if (!originals.hasNext()) {
                    return;
                }
                next = originals.next();
            }
            final String originalLetterAsString = codePointToString(next);
            originalToEncoded.put(next, currentEncoding);
            encodedToOriginal.put(currentEncoding, originalLetterAsString);
        }
    }

    /**
     * Decodes a given string.
     *
     * @param encoded a string that has been encoded using this AlphabetConverter.
     * @return The decoded string, {@code null} if the given string is null.
     * @throws UnsupportedEncodingException if unexpected characters that cannot be handled are encountered.
     */
    public String decode(final String encoded) throws UnsupportedEncodingException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Encodes a given string.
     *
     * @param original the string to be encoded.
     * @return The encoded string, {@code null} if the given string is null.
     * @throws UnsupportedEncodingException if chars that are not supported are encountered.
     */
    public String encode(final String original) throws UnsupportedEncodingException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public boolean equals(final Object obj) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Gets the length of characters in the encoded alphabet that are necessary for each character in the original alphabet.
     *
     * @return The length of the encoded char.
     */
    public int getEncodedCharLength() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Gets the mapping from integer code point of source language to encoded string. Use to reconstruct converter from serialized map.
     *
     * @return The original map.
     */
    public Map<Integer, String> getOriginalToEncoded() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public int hashCode() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public String toString() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
