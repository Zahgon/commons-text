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
import java.io.Writer;

/**
 * Translate escaped octal Strings back to their octal values.
 *
 * For example, "\45" should go back to being the specific value (a %).
 *
 * Note that this currently only supports the viable range of octal for Java; namely
 * 1 to 377. This is because parsing Java is the main use case.
 *
 * @since 1.0
 */
public class OctalUnescaper extends CharSequenceTranslator {

    /**
     * Creates a new instance.
     */
    public OctalUnescaper() {
        // empty
    }

    /**
     * Tests if the given char is an octal digit. Octal digits are the character representations of the digits 0 to 7.
     *
     * @param ch the char to check.
     * @return true if the given char is the character representation of one of the digits from 0 to 7.
     */
    private boolean isOctalDigit(final char ch) {
        return ch >= '0' && ch <= '7';
    }

    /**
     * Tests if the given char is the character representation of one of the digit from 0 to 3.
     *
     * @param ch the char to check.
     * @return true if the given char is the character representation of one of the digits from 0 to 3.
     */
    private boolean isZeroToThree(final char ch) {
        return ch >= '0' && ch <= '3';
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int translate(final CharSequence input, final int index, final Writer writer) throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
