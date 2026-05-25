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

import java.util.Objects;

/**
 * Container class to store Levenshtein distance between two character sequences.
 *
 * <p>Stores the count of insert, deletion and substitute operations needed to
 * change one character sequence into another.</p>
 *
 * <p>This class is immutable.</p>
 *
 * @since 1.0
 */
public class LevenshteinResults {

    /**
     * Edit distance.
     */
    private final Integer distance;

    /**
     * Insert character count.
     */
    private final Integer insertCount;

    /**
     * Delete character count.
     */
    private final Integer deleteCount;

    /**
     * Substitute character count.
     */
    private final Integer substituteCount;

    /**
     * Constructs the results for a detailed Levenshtein distance.
     *
     * @param distance distance between two character sequences.
     * @param insertCount insert character count.
     * @param deleteCount delete character count.
     * @param substituteCount substitute character count.
     */
    public LevenshteinResults(final Integer distance, final Integer insertCount, final Integer deleteCount, final Integer substituteCount) {
        this.distance = distance;
        this.insertCount = insertCount;
        this.deleteCount = deleteCount;
        this.substituteCount = substituteCount;
    }

    @Override
    public boolean equals(final Object o) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Gets the number of character deletion needed to change one character sequence to other.
     *
     * @return delete character count.
     */
    public Integer getDeleteCount() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Gets the distance between two character sequences.
     *
     * @return distance between two character sequence.
     */
    public Integer getDistance() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Gets the number of insertion needed to change one character sequence into another.
     *
     * @return insert character count.
     */
    public Integer getInsertCount() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Gets the number of character substitution needed to change one character sequence into another.
     *
     * @return substitute character count.
     */
    public Integer getSubstituteCount() {
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
