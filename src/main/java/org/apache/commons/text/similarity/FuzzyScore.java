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
package org.apache.commons.text.similarity;

import java.util.Locale;

/**
 * A matching algorithm that is similar to the searching algorithms implemented in editors such
 * as Sublime Text, TextMate, Atom and others.
 *
 * <p>
 * One point is given for every matched character. Subsequent matches yield two bonus points. A higher score
 * indicates a higher similarity.
 * </p>
 *
 * <p>
 * This code has been adapted from Apache Commons Lang 3.3.
 * </p>
 *
 * @since 1.0
 */
public class FuzzyScore {

    /**
     * Locale used to change the case of text.
     */
    private final Locale locale;

    /**
     * Constructs a new instance for a {@link Locale}-specific {@link FuzzyScore}.
     *
     * @param locale The string matching logic is case insensitive. A {@link Locale} is necessary to normalize both Strings to lower case.
     * @throws IllegalArgumentException This is thrown if the {@link Locale} parameter is {@code null}.
     */
    public FuzzyScore(final Locale locale) {
        if (locale == null) {
            throw new IllegalArgumentException("Locale must not be null");
        }
        this.locale = locale;
    }

    /**
     * Computes the Fuzzy Score which indicates the similarity score between two Strings.
     *
     * <pre>
     * score.fuzzyScore(null, null)                          = Throws {@link IllegalArgumentException}
     * score.fuzzyScore("not null", null)                    = Throws {@link IllegalArgumentException}
     * score.fuzzyScore(null, "not null")                    = Throws {@link IllegalArgumentException}
     * score.fuzzyScore("", "")                              = 0
     * score.fuzzyScore("Workshop", "b")                     = 0
     * score.fuzzyScore("Room", "o")                         = 1
     * score.fuzzyScore("Workshop", "w")                     = 1
     * score.fuzzyScore("Workshop", "ws")                    = 2
     * score.fuzzyScore("Workshop", "wo")                    = 4
     * score.fuzzyScore("Apache Software Foundation", "asf") = 3
     * </pre>
     *
     * @param term  a full term that should be matched against, must not be null.
     * @param query the query that will be matched against a term, must not be null.
     * @return result score.
     * @throws IllegalArgumentException if the term or query is {@code null}.
     */
    public Integer fuzzyScore(final CharSequence term, final CharSequence query) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Gets the locale.
     *
     * @return The locale
     */
    public Locale getLocale() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
