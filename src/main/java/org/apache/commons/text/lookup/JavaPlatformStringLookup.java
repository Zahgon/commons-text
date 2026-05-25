/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache license, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the license for the specific language governing permissions and
 * limitations under the license.
 */
package org.apache.commons.text.lookup;

import java.util.Locale;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringSubstitutor;

/**
 * Looks up keys related to Java: Java version, JRE version, VM version, and so on.
 * <p>
 * The lookup keys with examples are:
 * </p>
 * <ul>
 * <li><strong>version</strong>: "Java version 1.8.0_181"</li>
 * <li><strong>runtime</strong>: "Java(TM) SE Runtime Environment (build 1.8.0_181-b13) from Oracle Corporation"</li>
 * <li><strong>vm</strong>: "Java HotSpot(TM) 64-Bit Server VM (build 25.181-b13, mixed mode)"</li>
 * <li><strong>os</strong>: "Windows 10 10.0, architecture: amd64-64"</li>
 * <li><strong>hardware</strong>: "processors: 4, architecture: amd64-64, instruction sets: amd64"</li>
 * <li><strong>locale</strong>: "default locale: en_US, platform encoding: iso-8859-1"</li>
 * </ul>
 *
 * <p>
 * Using a {@link StringLookup} from the {@link StringLookupFactory}:
 * </p>
 *
 * <pre>
 * StringLookupFactory.INSTANCE.javaPlatformStringLookup().lookup("version");
 * </pre>
 * <p>
 * Using a {@link StringSubstitutor}:
 * </p>
 *
 * <pre>
 * StringSubstitutor.createInterpolator().replace("... ${java:version} ..."));
 * </pre>
 * <p>
 * The above examples convert {@code "version"} to the current VM version, for example,
 * {@code "Java version 1.8.0_181"}.
 * </p>
 * <p>
 * Public access is through {@link StringLookupFactory}.
 * </p>
 *
 * @see StringLookupFactory
 * @since 1.3
 */
final class JavaPlatformStringLookup extends AbstractStringLookup {

    /**
     * Defines the singleton for this class.
     */
    static final JavaPlatformStringLookup INSTANCE = new JavaPlatformStringLookup();

    /**
     * {@code hardware} key for driving {@link JavaPlatformStringLookup#lookup(String)}.
     */
    private static final String KEY_HARDWARE = "hardware";

    /**
     * {@code locale} key for driving {@link JavaPlatformStringLookup#lookup(String)}.
     */
    private static final String KEY_LOCALE = "locale";

    /**
     * {@code os} key for driving {@link JavaPlatformStringLookup#lookup(String)}.
     */
    private static final String KEY_OS = "os";

    /**
     * {@code runtime} key for driving {@link JavaPlatformStringLookup#lookup(String)}.
     */
    private static final String KEY_RUNTIME = "runtime";

    /**
     * {@code version} key for driving {@link JavaPlatformStringLookup#lookup(String)}.
     */
    private static final String KEY_VERSION = "version";

    /**
     * {@code vm} key for driving {@link JavaPlatformStringLookup#lookup(String)}.
     */
    private static final String KEY_VM = "vm";

    /**
     * The main method for running the JavaPlatformStringLookup.
     *
     * @param args the standard Java main method parameter which is unused for our running of this class.
     */
    public static void main(final String[] args) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * No need to build instances for now.
     */
    private JavaPlatformStringLookup() {
        // empty
    }

    /**
     * Accessible through the Lookup key {@code hardware}.
     *
     * @return hardware processor information.
     */
    String getHardware() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Accessible through the Lookup key {@code locale}.
     *
     * @return system locale and file encoding information.
     */
    String getLocale() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Accessible through the Lookup key {@code os}.
     *
     * @return operating system information.
     */
    String getOperatingSystem() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Accessible through the Lookup key {@code runtime}.
     *
     * @return Java Runtime Environment information.
     */
    String getRuntime() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Gets the given system property.
     *
     * @param name a system property name.
     * @return a system property value.
     */
    private String getSystemProperty(final String name) {
        return StringLookupFactory.INSTANCE_SYSTEM_PROPERTIES.apply(name);
    }

    /**
     * Gets the given system property.
     *
     * @param prefix the prefix to use for the result string.
     * @param name a system property name.
     * @return The prefix + a system property value.
     */
    private String getSystemProperty(final String prefix, final String name) {
        final String value = getSystemProperty(name);
        if (StringUtils.isEmpty(value)) {
            return StringUtils.EMPTY;
        }
        return prefix + value;
    }

    /**
     * Accessible through the Lookup key {@code vm}.
     *
     * @return Java Virtual Machine information.
     */
    String getVirtualMachine() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Looks up the value of the Java platform key.
     * <p>
     * The lookup keys with examples are:
     * </p>
     * <ul>
     * <li><strong>version</strong>: "Java version 1.8.0_181"</li>
     * <li><strong>runtime</strong>: "Java(TM) SE Runtime Environment (build 1.8.0_181-b13) from Oracle Corporation"</li>
     * <li><strong>vm</strong>: "Java HotSpot(TM) 64-Bit Server VM (build 25.181-b13, mixed mode)"</li>
     * <li><strong>os</strong>: "Windows 10 10.0, architecture: amd64-64"</li>
     * <li><strong>hardware</strong>: "processors: 4, architecture: amd64-64, instruction sets: amd64"</li>
     * <li><strong>locale</strong>: "default locale: en_US, platform encoding: iso-8859-1"</li>
     * </ul>
     *
     * @param key the key to be looked up, may be null.
     * @return The value of the environment variable.
     */
    @Override
    public String lookup(final String key) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
