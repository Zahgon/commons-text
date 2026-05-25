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
package org.apache.commons.text.translate;

import java.io.IOException;
import java.io.StringWriter;
import java.io.UncheckedIOException;
import java.io.Writer;
import java.util.Locale;
import org.apache.commons.lang3.Validate;

/**
 * An API for translating text.
 * Its core use is to escape and unescape text. Because escaping and unescaping
 * is completely contextual, the API does not present two separate signatures.
 *
 * @since 1.0
 */
public abstract class CharSequenceTranslator {

    /**
     * Array containing the hexadecimal alphabet.
     */
    static final char[] HEX_DIGITS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

    /**
     * Returns an upper case hexadecimal {@code String} for the given
     * character.
     *
     * @param codePoint The code point to convert.
     * @return An upper case hexadecimal {@code String}
     */
    public static String hex(final int codePoint) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Construct a new instance.
     */
    public CharSequenceTranslator() {
        // empty
    }

    /**
     * Helper for non-Writer usage.
     *
     * @param input CharSequence to be translated.
     * @return String output of translation.
     */
    public final String translate(final CharSequence input) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Translate a set of code points, represented by an int index into a CharSequence,
     * into another set of code points. The number of code points consumed must be returned,
     * and the only IOExceptions thrown must be from interacting with the Writer so that
     * the top level API may reliably ignore StringWriter IOExceptions.
     *
     * @param input CharSequence that is being translated.
     * @param index int representing the current point of translation.
     * @param writer Writer to translate the text to.
     * @return int count of code points consumed.
     * @throws IOException if and only if the Writer produces an IOException.
     */
    public abstract int translate(CharSequence input, int index, Writer writer) throws IOException;

    /**
     * Translate an input onto a Writer. This is intentionally final as its algorithm is
     * tightly coupled with the abstract method of this class.
     *
     * @param input CharSequence that is being translated.
     * @param writer Writer to translate the text to.
     * @throws IOException if and only if the Writer produces an IOException.
     */
    public final void translate(final CharSequence input, final Writer writer) throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Helper method to create a merger of this translator with another set of
     * translators. Useful in customizing the standard functionality.
     *
     * @param translators CharSequenceTranslator array of translators to merge with this one.
     * @return CharSequenceTranslator merging this translator with the others.
     */
    public final CharSequenceTranslator with(final CharSequenceTranslator... translators) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
