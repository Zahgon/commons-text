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

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

/**
 * Class holding various entity data for HTML and XML - generally for use with
 * the LookupTranslator.
 * All Maps are generated using {@code java.util.Collections.unmodifiableMap()}.
 *
 * @since 1.0
 */
public class EntityArrays {

    /**
     * A Map&lt;CharSequence, CharSequence&gt; to escape
     * <a href="https://secure.wikimedia.org/wikipedia/en/wiki/ISO/IEC_8859-1">ISO-8859-1</a>
     * characters to their named HTML 3.x equivalents.
     */
    public static final Map<CharSequence, CharSequence> ISO8859_1_ESCAPE;

    static {
        final Map<CharSequence, CharSequence> initialMap = new HashMap<>();
        // non-breaking space
        initialMap.put("\u00A0", "&nbsp;");
        // inverted exclamation mark
        initialMap.put("\u00A1", "&iexcl;");
        // cent sign
        initialMap.put("\u00A2", "&cent;");
        // pound sign
        initialMap.put("\u00A3", "&pound;");
        // currency sign
        initialMap.put("\u00A4", "&curren;");
        // yen sign = yuan sign
        initialMap.put("\u00A5", "&yen;");
        // broken bar = broken vertical bar
        initialMap.put("\u00A6", "&brvbar;");
        // section sign
        initialMap.put("\u00A7", "&sect;");
        // dieresis = spacing dieresis
        initialMap.put("\u00A8", "&uml;");
        // © - copyright sign
        initialMap.put("\u00A9", "&copy;");
        // feminine ordinal indicator
        initialMap.put("\u00AA", "&ordf;");
        // left-pointing double angle quotation mark = left pointing guillemet
        initialMap.put("\u00AB", "&laquo;");
        // not sign
        initialMap.put("\u00AC", "&not;");
        // soft hyphen = discretionary hyphen
        initialMap.put("\u00AD", "&shy;");
        // ® - registered trademark sign
        initialMap.put("\u00AE", "&reg;");
        // macron = spacing macron = overline = APL overbar
        initialMap.put("\u00AF", "&macr;");
        // degree sign
        initialMap.put("\u00B0", "&deg;");
        // plus-minus sign = plus-or-minus sign
        initialMap.put("\u00B1", "&plusmn;");
        // superscript two = superscript digit two = squared
        initialMap.put("\u00B2", "&sup2;");
        // superscript three = superscript digit three = cubed
        initialMap.put("\u00B3", "&sup3;");
        // acute accent = spacing acute
        initialMap.put("\u00B4", "&acute;");
        // micro sign
        initialMap.put("\u00B5", "&micro;");
        // pilcrow sign = paragraph sign
        initialMap.put("\u00B6", "&para;");
        // middle dot = Georgian comma = Greek middle dot
        initialMap.put("\u00B7", "&middot;");
        // cedilla = spacing cedilla
        initialMap.put("\u00B8", "&cedil;");
        // superscript one = superscript digit one
        initialMap.put("\u00B9", "&sup1;");
        // masculine ordinal indicator
        initialMap.put("\u00BA", "&ordm;");
        // right-pointing double angle quotation mark = right pointing guillemet
        initialMap.put("\u00BB", "&raquo;");
        // vulgar fraction one quarter = fraction one quarter
        initialMap.put("\u00BC", "&frac14;");
        // vulgar fraction one half = fraction one half
        initialMap.put("\u00BD", "&frac12;");
        // vulgar fraction three quarters = fraction three quarters
        initialMap.put("\u00BE", "&frac34;");
        // inverted question mark = turned question mark
        initialMap.put("\u00BF", "&iquest;");
        // À - uppercase A, grave accent
        initialMap.put("\u00C0", "&Agrave;");
        // Á - uppercase A, acute accent
        initialMap.put("\u00C1", "&Aacute;");
        // Â - uppercase A, circumflex accent
        initialMap.put("\u00C2", "&Acirc;");
        // Ã - uppercase A, tilde
        initialMap.put("\u00C3", "&Atilde;");
        // Ä - uppercase A, umlaut
        initialMap.put("\u00C4", "&Auml;");
        // � - uppercase A, ring
        initialMap.put("\u00C5", "&Aring;");
        // Æ - uppercase AE
        initialMap.put("\u00C6", "&AElig;");
        // Ç - uppercase C, cedilla
        initialMap.put("\u00C7", "&Ccedil;");
        // È - uppercase E, grave accent
        initialMap.put("\u00C8", "&Egrave;");
        // É - uppercase E, acute accent
        initialMap.put("\u00C9", "&Eacute;");
        // Ê - uppercase E, circumflex accent
        initialMap.put("\u00CA", "&Ecirc;");
        // Ë - uppercase E, umlaut
        initialMap.put("\u00CB", "&Euml;");
        // Ì - uppercase I, grave accent
        initialMap.put("\u00CC", "&Igrave;");
        // Í - uppercase I, acute accent
        initialMap.put("\u00CD", "&Iacute;");
        // Î - uppercase I, circumflex accent
        initialMap.put("\u00CE", "&Icirc;");
        // Ï - uppercase I, umlaut
        initialMap.put("\u00CF", "&Iuml;");
        // Ð - uppercase Eth, Icelandic
        initialMap.put("\u00D0", "&ETH;");
        // Ñ - uppercase N, tilde
        initialMap.put("\u00D1", "&Ntilde;");
        // Ò - uppercase O, grave accent
        initialMap.put("\u00D2", "&Ograve;");
        // Ó - uppercase O, acute accent
        initialMap.put("\u00D3", "&Oacute;");
        // Ô - uppercase O, circumflex accent
        initialMap.put("\u00D4", "&Ocirc;");
        // Õ - uppercase O, tilde
        initialMap.put("\u00D5", "&Otilde;");
        // Ö - uppercase O, umlaut
        initialMap.put("\u00D6", "&Ouml;");
        // multiplication sign
        initialMap.put("\u00D7", "&times;");
        // Ø - uppercase O, slash
        initialMap.put("\u00D8", "&Oslash;");
        // Ù - uppercase U, grave accent
        initialMap.put("\u00D9", "&Ugrave;");
        // Ú - uppercase U, acute accent
        initialMap.put("\u00DA", "&Uacute;");
        // Û - uppercase U, circumflex accent
        initialMap.put("\u00DB", "&Ucirc;");
        // Ü - uppercase U, umlaut
        initialMap.put("\u00DC", "&Uuml;");
        // Ý - uppercase Y, acute accent
        initialMap.put("\u00DD", "&Yacute;");
        // Þ - uppercase THORN, Icelandic
        initialMap.put("\u00DE", "&THORN;");
        // ß - lowercase sharps, German
        initialMap.put("\u00DF", "&szlig;");
        // à - lowercase a, grave accent
        initialMap.put("\u00E0", "&agrave;");
        // á - lowercase a, acute accent
        initialMap.put("\u00E1", "&aacute;");
        // â - lowercase a, circumflex accent
        initialMap.put("\u00E2", "&acirc;");
        // ã - lowercase a, tilde
        initialMap.put("\u00E3", "&atilde;");
        // ä - lowercase a, umlaut
        initialMap.put("\u00E4", "&auml;");
        // å - lowercase a, ring
        initialMap.put("\u00E5", "&aring;");
        // æ - lowercase ae
        initialMap.put("\u00E6", "&aelig;");
        // ç - lowercase c, cedilla
        initialMap.put("\u00E7", "&ccedil;");
        // è - lowercase e, grave accent
        initialMap.put("\u00E8", "&egrave;");
        // é - lowercase e, acute accent
        initialMap.put("\u00E9", "&eacute;");
        // ê - lowercase e, circumflex accent
        initialMap.put("\u00EA", "&ecirc;");
        // ë - lowercase e, umlaut
        initialMap.put("\u00EB", "&euml;");
        // ì - lowercase i, grave accent
        initialMap.put("\u00EC", "&igrave;");
        // í - lowercase i, acute accent
        initialMap.put("\u00ED", "&iacute;");
        // î - lowercase i, circumflex accent
        initialMap.put("\u00EE", "&icirc;");
        // ï - lowercase i, umlaut
        initialMap.put("\u00EF", "&iuml;");
        // ð - lowercase eth, Icelandic
        initialMap.put("\u00F0", "&eth;");
        // ñ - lowercase n, tilde
        initialMap.put("\u00F1", "&ntilde;");
        // ò - lowercase o, grave accent
        initialMap.put("\u00F2", "&ograve;");
        // ó - lowercase o, acute accent
        initialMap.put("\u00F3", "&oacute;");
        // ô - lowercase o, circumflex accent
        initialMap.put("\u00F4", "&ocirc;");
        // õ - lowercase o, tilde
        initialMap.put("\u00F5", "&otilde;");
        // ö - lowercase o, umlaut
        initialMap.put("\u00F6", "&ouml;");
        // division sign
        initialMap.put("\u00F7", "&divide;");
        // ø - lowercase o, slash
        initialMap.put("\u00F8", "&oslash;");
        // ù - lowercase u, grave accent
        initialMap.put("\u00F9", "&ugrave;");
        // ú - lowercase u, acute accent
        initialMap.put("\u00FA", "&uacute;");
        // û - lowercase u, circumflex accent
        initialMap.put("\u00FB", "&ucirc;");
        // ü - lowercase u, umlaut
        initialMap.put("\u00FC", "&uuml;");
        // ý - lowercase y, acute accent
        initialMap.put("\u00FD", "&yacute;");
        // þ - lowercase thorn, Icelandic
        initialMap.put("\u00FE", "&thorn;");
        // ÿ - lowercase y, umlaut
        initialMap.put("\u00FF", "&yuml;");
        ISO8859_1_ESCAPE = Collections.unmodifiableMap(initialMap);
    }

    /**
     * Reverse of {@link #ISO8859_1_ESCAPE} for unescaping purposes.
     */
    public static final Map<CharSequence, CharSequence> ISO8859_1_UNESCAPE;

    static {
        ISO8859_1_UNESCAPE = Collections.unmodifiableMap(invert(ISO8859_1_ESCAPE));
    }

    /**
     * A Map&lt;CharSequence, CharSequence&gt; to escape additional
     * <a href="https://www.w3.org/TR/REC-html40/sgml/entities.html">character entity
     * references</a>. Note that this must be used with {@link #ISO8859_1_ESCAPE} to get the full list of
     * HTML 4.0 character entities.
     */
    public static final Map<CharSequence, CharSequence> HTML40_EXTENDED_ESCAPE;

    static {
        final Map<CharSequence, CharSequence> initialMap = new HashMap<>();
        // <!-- Latin Extended-B -->
        // latin small f with hook = function= florin, U+0192 ISOtech -->
        initialMap.put("\u0192", "&fnof;");
        // <!-- Greek -->
        // greek capital letter alpha, U+0391 -->
        initialMap.put("\u0391", "&Alpha;");
        // greek capital letter beta, U+0392 -->
        initialMap.put("\u0392", "&Beta;");
        // greek capital letter gamma,U+0393 ISOgrk3 -->
        initialMap.put("\u0393", "&Gamma;");
        // greek capital letter delta,U+0394 ISOgrk3 -->
        initialMap.put("\u0394", "&Delta;");
        // greek capital letter epsilon, U+0395 -->
        initialMap.put("\u0395", "&Epsilon;");
        // greek capital letter zeta, U+0396 -->
        initialMap.put("\u0396", "&Zeta;");
        // greek capital letter eta, U+0397 -->
        initialMap.put("\u0397", "&Eta;");
        // greek capital letter theta,U+0398 ISOgrk3 -->
        initialMap.put("\u0398", "&Theta;");
        // greek capital letter iota, U+0399 -->
        initialMap.put("\u0399", "&Iota;");
        // greek capital letter kappa, U+039A -->
        initialMap.put("\u039A", "&Kappa;");
        // greek capital letter lambda,U+039B ISOgrk3 -->
        initialMap.put("\u039B", "&Lambda;");
        // greek capital letter mu, U+039C -->
        initialMap.put("\u039C", "&Mu;");
        // greek capital letter nu, U+039D -->
        initialMap.put("\u039D", "&Nu;");
        // greek capital letter xi, U+039E ISOgrk3 -->
        initialMap.put("\u039E", "&Xi;");
        // greek capital letter omicron, U+039F -->
        initialMap.put("\u039F", "&Omicron;");
        // greek capital letter pi, U+03A0 ISOgrk3 -->
        initialMap.put("\u03A0", "&Pi;");
        // greek capital letter rho, U+03A1 -->
        initialMap.put("\u03A1", "&Rho;");
        // <!-- there is no Sigmaf, and no U+03A2 character either -->
        // greek capital letter sigma,U+03A3 ISOgrk3 -->
        initialMap.put("\u03A3", "&Sigma;");
        // greek capital letter tau, U+03A4 -->
        initialMap.put("\u03A4", "&Tau;");
        // greek capital letter upsilon,U+03A5 ISOgrk3 -->
        initialMap.put("\u03A5", "&Upsilon;");
        // greek capital letter phi,U+03A6 ISOgrk3 -->
        initialMap.put("\u03A6", "&Phi;");
        // greek capital letter chi, U+03A7 -->
        initialMap.put("\u03A7", "&Chi;");
        // greek capital letter psi,U+03A8 ISOgrk3 -->
        initialMap.put("\u03A8", "&Psi;");
        // greek capital letter omega,U+03A9 ISOgrk3 -->
        initialMap.put("\u03A9", "&Omega;");
        // greek small letter alpha,U+03B1 ISOgrk3 -->
        initialMap.put("\u03B1", "&alpha;");
        // greek small letter beta, U+03B2 ISOgrk3 -->
        initialMap.put("\u03B2", "&beta;");
        // greek small letter gamma,U+03B3 ISOgrk3 -->
        initialMap.put("\u03B3", "&gamma;");
        // greek small letter delta,U+03B4 ISOgrk3 -->
        initialMap.put("\u03B4", "&delta;");
        // greek small letter epsilon,U+03B5 ISOgrk3 -->
        initialMap.put("\u03B5", "&epsilon;");
        // greek small letter zeta, U+03B6 ISOgrk3 -->
        initialMap.put("\u03B6", "&zeta;");
        // greek small letter eta, U+03B7 ISOgrk3 -->
        initialMap.put("\u03B7", "&eta;");
        // greek small letter theta,U+03B8 ISOgrk3 -->
        initialMap.put("\u03B8", "&theta;");
        // greek small letter iota, U+03B9 ISOgrk3 -->
        initialMap.put("\u03B9", "&iota;");
        // greek small letter kappa,U+03BA ISOgrk3 -->
        initialMap.put("\u03BA", "&kappa;");
        // greek small letter lambda,U+03BB ISOgrk3 -->
        initialMap.put("\u03BB", "&lambda;");
        // greek small letter mu, U+03BC ISOgrk3 -->
        initialMap.put("\u03BC", "&mu;");
        // greek small letter nu, U+03BD ISOgrk3 -->
        initialMap.put("\u03BD", "&nu;");
        // greek small letter xi, U+03BE ISOgrk3 -->
        initialMap.put("\u03BE", "&xi;");
        // greek small letter omicron, U+03BF NEW -->
        initialMap.put("\u03BF", "&omicron;");
        // greek small letter pi, U+03C0 ISOgrk3 -->
        initialMap.put("\u03C0", "&pi;");
        // greek small letter rho, U+03C1 ISOgrk3 -->
        initialMap.put("\u03C1", "&rho;");
        // greek small letter final sigma,U+03C2 ISOgrk3 -->
        initialMap.put("\u03C2", "&sigmaf;");
        // greek small letter sigma,U+03C3 ISOgrk3 -->
        initialMap.put("\u03C3", "&sigma;");
        // greek small letter tau, U+03C4 ISOgrk3 -->
        initialMap.put("\u03C4", "&tau;");
        // greek small letter upsilon,U+03C5 ISOgrk3 -->
        initialMap.put("\u03C5", "&upsilon;");
        // greek small letter phi, U+03C6 ISOgrk3 -->
        initialMap.put("\u03C6", "&phi;");
        // greek small letter chi, U+03C7 ISOgrk3 -->
        initialMap.put("\u03C7", "&chi;");
        // greek small letter psi, U+03C8 ISOgrk3 -->
        initialMap.put("\u03C8", "&psi;");
        // greek small letter omega,U+03C9 ISOgrk3 -->
        initialMap.put("\u03C9", "&omega;");
        // greek small letter theta symbol,U+03D1 NEW -->
        initialMap.put("\u03D1", "&thetasym;");
        // greek upsilon with hook symbol,U+03D2 NEW -->
        initialMap.put("\u03D2", "&upsih;");
        // greek pi symbol, U+03D6 ISOgrk3 -->
        initialMap.put("\u03D6", "&piv;");
        // <!-- General Punctuation -->
        // bullet = black small circle,U+2022 ISOpub -->
        initialMap.put("\u2022", "&bull;");
        // <!-- bullet is NOT the same as bullet operator, U+2219 -->
        // horizontal ellipsis = three dot leader,U+2026 ISOpub -->
        initialMap.put("\u2026", "&hellip;");
        // prime = minutes = feet, U+2032 ISOtech -->
        initialMap.put("\u2032", "&prime;");
        // double prime = seconds = inches,U+2033 ISOtech -->
        initialMap.put("\u2033", "&Prime;");
        // overline = spacing overscore,U+203E NEW -->
        initialMap.put("\u203E", "&oline;");
        // fraction slash, U+2044 NEW -->
        initialMap.put("\u2044", "&frasl;");
        // <!-- Letterlike Symbols -->
        // script capital P = power set= Weierstrass p, U+2118 ISOamso -->
        initialMap.put("\u2118", "&weierp;");
        // blackletter capital I = imaginary part,U+2111 ISOamso -->
        initialMap.put("\u2111", "&image;");
        // blackletter capital R = real part symbol,U+211C ISOamso -->
        initialMap.put("\u211C", "&real;");
        // trade mark sign, U+2122 ISOnum -->
        initialMap.put("\u2122", "&trade;");
        // alef symbol = first transfinite cardinal,U+2135 NEW -->
        initialMap.put("\u2135", "&alefsym;");
        // <!-- alef symbol is NOT the same as hebrew letter alef,U+05D0 although the
        // same glyph could be used to depict both characters -->
        // <!-- Arrows -->
        // leftwards arrow, U+2190 ISOnum -->
        initialMap.put("\u2190", "&larr;");
        // upwards arrow, U+2191 ISOnum-->
        initialMap.put("\u2191", "&uarr;");
        // rightwards arrow, U+2192 ISOnum -->
        initialMap.put("\u2192", "&rarr;");
        // downwards arrow, U+2193 ISOnum -->
        initialMap.put("\u2193", "&darr;");
        // left right arrow, U+2194 ISOamsa -->
        initialMap.put("\u2194", "&harr;");
        // downwards arrow with corner leftwards= carriage return, U+21B5 NEW -->
        initialMap.put("\u21B5", "&crarr;");
        // leftwards double arrow, U+21D0 ISOtech -->
        initialMap.put("\u21D0", "&lArr;");
        // <!-- ISO 10646 does not say that lArr is the same as the 'is implied by'
        // arrow but also does not have any other character for that function.
        // So ? lArr canbe used for 'is implied by' as ISOtech suggests -->
        // upwards double arrow, U+21D1 ISOamsa -->
        initialMap.put("\u21D1", "&uArr;");
        // rightwards double arrow,U+21D2 ISOtech -->
        initialMap.put("\u21D2", "&rArr;");
        // <!-- ISO 10646 does not say this is the 'implies' character but does not
        // have another character with this function so ?rArr can be used for
        // 'implies' as ISOtech suggests -->
        // downwards double arrow, U+21D3 ISOamsa -->
        initialMap.put("\u21D3", "&dArr;");
        // left right double arrow,U+21D4 ISOamsa -->
        initialMap.put("\u21D4", "&hArr;");
        // <!-- Mathematical Operators -->
        // for all, U+2200 ISOtech -->
        initialMap.put("\u2200", "&forall;");
        // partial differential, U+2202 ISOtech -->
        initialMap.put("\u2202", "&part;");
        // there exists, U+2203 ISOtech -->
        initialMap.put("\u2203", "&exist;");
        // empty set = null set = diameter,U+2205 ISOamso -->
        initialMap.put("\u2205", "&empty;");
        // nabla = backward difference,U+2207 ISOtech -->
        initialMap.put("\u2207", "&nabla;");
        // element of, U+2208 ISOtech -->
        initialMap.put("\u2208", "&isin;");
        // not an element of, U+2209 ISOtech -->
        initialMap.put("\u2209", "&notin;");
        // contains as member, U+220B ISOtech -->
        initialMap.put("\u220B", "&ni;");
        // <!-- should there be a more memorable name than 'ni'? -->
        // n-ary product = product sign,U+220F ISOamsb -->
        initialMap.put("\u220F", "&prod;");
        // <!-- prod is NOT the same character as U+03A0 'greek capital letter pi'
        // though the same glyph might be used for both -->
        // n-ary summation, U+2211 ISOamsb -->
        initialMap.put("\u2211", "&sum;");
        // <!-- sum is NOT the same character as U+03A3 'greek capital letter sigma'
        // though the same glyph might be used for both -->
        // minus sign, U+2212 ISOtech -->
        initialMap.put("\u2212", "&minus;");
        // asterisk operator, U+2217 ISOtech -->
        initialMap.put("\u2217", "&lowast;");
        // square root = radical sign,U+221A ISOtech -->
        initialMap.put("\u221A", "&radic;");
        // proportional to, U+221D ISOtech -->
        initialMap.put("\u221D", "&prop;");
        // infinity, U+221E ISOtech -->
        initialMap.put("\u221E", "&infin;");
        // angle, U+2220 ISOamso -->
        initialMap.put("\u2220", "&ang;");
        // logical and = wedge, U+2227 ISOtech -->
        initialMap.put("\u2227", "&and;");
        // logical or = vee, U+2228 ISOtech -->
        initialMap.put("\u2228", "&or;");
        // intersection = cap, U+2229 ISOtech -->
        initialMap.put("\u2229", "&cap;");
        // union = cup, U+222A ISOtech -->
        initialMap.put("\u222A", "&cup;");
        // integral, U+222B ISOtech -->
        initialMap.put("\u222B", "&int;");
        // therefore, U+2234 ISOtech -->
        initialMap.put("\u2234", "&there4;");
        // tilde operator = varies with = similar to,U+223C ISOtech -->
        initialMap.put("\u223C", "&sim;");
        // <!-- tilde operator is NOT the same character as the tilde, U+007E,although
        // the same glyph might be used to represent both -->
        // approximately equal to, U+2245 ISOtech -->
        initialMap.put("\u2245", "&cong;");
        // almost equal to = asymptotic to,U+2248 ISOamsr -->
        initialMap.put("\u2248", "&asymp;");
        // not equal to, U+2260 ISOtech -->
        initialMap.put("\u2260", "&ne;");
        // identical to, U+2261 ISOtech -->
        initialMap.put("\u2261", "&equiv;");
        // less-than or equal to, U+2264 ISOtech -->
        initialMap.put("\u2264", "&le;");
        // greater-than or equal to,U+2265 ISOtech -->
        initialMap.put("\u2265", "&ge;");
        // subset of, U+2282 ISOtech -->
        initialMap.put("\u2282", "&sub;");
        // superset of, U+2283 ISOtech -->
        initialMap.put("\u2283", "&sup;");
        // <!-- note that nsup, 'not a superset of, U+2283' is not covered by the
        // Symbol font encoding and is not included. Should it be, for symmetry?
        // It is in ISOamsn -->,
        // not a subset of, U+2284 ISOamsn -->
        initialMap.put("\u2284", "&nsub;");
        // subset of or equal to, U+2286 ISOtech -->
        initialMap.put("\u2286", "&sube;");
        // superset of or equal to,U+2287 ISOtech -->
        initialMap.put("\u2287", "&supe;");
        // circled plus = direct sum,U+2295 ISOamsb -->
        initialMap.put("\u2295", "&oplus;");
        // circled times = vector product,U+2297 ISOamsb -->
        initialMap.put("\u2297", "&otimes;");
        // up tack = orthogonal to = perpendicular,U+22A5 ISOtech -->
        initialMap.put("\u22A5", "&perp;");
        // dot operator, U+22C5 ISOamsb -->
        initialMap.put("\u22C5", "&sdot;");
        // <!-- dot operator is NOT the same character as U+00B7 middle dot -->
        // <!-- Miscellaneous Technical -->
        // left ceiling = apl upstile,U+2308 ISOamsc -->
        initialMap.put("\u2308", "&lceil;");
        // right ceiling, U+2309 ISOamsc -->
        initialMap.put("\u2309", "&rceil;");
        // left floor = apl downstile,U+230A ISOamsc -->
        initialMap.put("\u230A", "&lfloor;");
        // right floor, U+230B ISOamsc -->
        initialMap.put("\u230B", "&rfloor;");
        // left-pointing angle bracket = bra,U+2329 ISOtech -->
        initialMap.put("\u2329", "&lang;");
        // <!-- lang is NOT the same character as U+003C 'less than' or U+2039 'single left-pointing angle quotation
        // mark' -->
        // right-pointing angle bracket = ket,U+232A ISOtech -->
        initialMap.put("\u232A", "&rang;");
        // <!-- rang is NOT the same character as U+003E 'greater than' or U+203A
        // 'single right-pointing angle quotation mark' -->
        // <!-- Geometric Shapes -->
        // lozenge, U+25CA ISOpub -->
        initialMap.put("\u25CA", "&loz;");
        // <!-- Miscellaneous Symbols -->
        // black spade suit, U+2660 ISOpub -->
        initialMap.put("\u2660", "&spades;");
        // <!-- black here seems to mean filled as opposed to hollow -->
        // black club suit = shamrock,U+2663 ISOpub -->
        initialMap.put("\u2663", "&clubs;");
        // black heart suit = valentine,U+2665 ISOpub -->
        initialMap.put("\u2665", "&hearts;");
        // black diamond suit, U+2666 ISOpub -->
        initialMap.put("\u2666", "&diams;");
        // <!-- Latin Extended-A -->
        // -- latin capital ligature OE,U+0152 ISOlat2 -->
        initialMap.put("\u0152", "&OElig;");
        // -- latin small ligature oe, U+0153 ISOlat2 -->
        initialMap.put("\u0153", "&oelig;");
        // <!-- ligature is a misnomer, this is a separate character in some languages -->
        // -- latin capital letter S with caron,U+0160 ISOlat2 -->
        initialMap.put("\u0160", "&Scaron;");
        // -- latin small letter s with caron,U+0161 ISOlat2 -->
        initialMap.put("\u0161", "&scaron;");
        // -- latin capital letter Y with dieresis,U+0178 ISOlat2 -->
        initialMap.put("\u0178", "&Yuml;");
        // <!-- Spacing Modifier Letters -->
        // -- modifier letter circumflex accent,U+02C6 ISOpub -->
        initialMap.put("\u02C6", "&circ;");
        // small tilde, U+02DC ISOdia -->
        initialMap.put("\u02DC", "&tilde;");
        // <!-- General Punctuation -->
        // en space, U+2002 ISOpub -->
        initialMap.put("\u2002", "&ensp;");
        // em space, U+2003 ISOpub -->
        initialMap.put("\u2003", "&emsp;");
        // thin space, U+2009 ISOpub -->
        initialMap.put("\u2009", "&thinsp;");
        // zero width non-joiner,U+200C NEW RFC 2070 -->
        initialMap.put("\u200C", "&zwnj;");
        // zero width joiner, U+200D NEW RFC 2070 -->
        initialMap.put("\u200D", "&zwj;");
        // left-to-right mark, U+200E NEW RFC 2070 -->
        initialMap.put("\u200E", "&lrm;");
        // right-to-left mark, U+200F NEW RFC 2070 -->
        initialMap.put("\u200F", "&rlm;");
        // en dash, U+2013 ISOpub -->
        initialMap.put("\u2013", "&ndash;");
        // em dash, U+2014 ISOpub -->
        initialMap.put("\u2014", "&mdash;");
        // left single quotation mark,U+2018 ISOnum -->
        initialMap.put("\u2018", "&lsquo;");
        // right single quotation mark,U+2019 ISOnum -->
        initialMap.put("\u2019", "&rsquo;");
        // single low-9 quotation mark, U+201A NEW -->
        initialMap.put("\u201A", "&sbquo;");
        // left double quotation mark,U+201C ISOnum -->
        initialMap.put("\u201C", "&ldquo;");
        // right double quotation mark,U+201D ISOnum -->
        initialMap.put("\u201D", "&rdquo;");
        // double low-9 quotation mark, U+201E NEW -->
        initialMap.put("\u201E", "&bdquo;");
        // dagger, U+2020 ISOpub -->
        initialMap.put("\u2020", "&dagger;");
        // double dagger, U+2021 ISOpub -->
        initialMap.put("\u2021", "&Dagger;");
        // per mille sign, U+2030 ISOtech -->
        initialMap.put("\u2030", "&permil;");
        // single left-pointing angle quotation mark,U+2039 ISO proposed -->
        initialMap.put("\u2039", "&lsaquo;");
        // <!-- lsaquo is proposed but not yet ISO standardized -->
        // single right-pointing angle quotation mark,U+203A ISO proposed -->
        initialMap.put("\u203A", "&rsaquo;");
        // <!-- rsaquo is proposed but not yet ISO standardized -->
        // -- euro sign, U+20AC NEW -->
        initialMap.put("\u20AC", "&euro;");
        HTML40_EXTENDED_ESCAPE = Collections.unmodifiableMap(initialMap);
    }

    /**
     * Reverse of {@link #HTML40_EXTENDED_ESCAPE} for unescaping purposes.
     */
    public static final Map<CharSequence, CharSequence> HTML40_EXTENDED_UNESCAPE;

    static {
        HTML40_EXTENDED_UNESCAPE = Collections.unmodifiableMap(invert(HTML40_EXTENDED_ESCAPE));
    }

    /**
     * A Map&lt;CharSequence, CharSequence&gt; to escape the basic XML and HTML
     * character entities.
     *
     * Namely: {@code " & < >}
     */
    public static final Map<CharSequence, CharSequence> BASIC_ESCAPE;

    static {
        final Map<CharSequence, CharSequence> initialMap = new HashMap<>();
        // " - double-quote
        initialMap.put("\"", "&quot;");
        // & - ampersand
        initialMap.put("&", "&amp;");
        // < - less-than
        initialMap.put("<", "&lt;");
        // > - greater-than
        initialMap.put(">", "&gt;");
        BASIC_ESCAPE = Collections.unmodifiableMap(initialMap);
    }

    /**
     * Reverse of {@link #BASIC_ESCAPE} for unescaping purposes.
     */
    public static final Map<CharSequence, CharSequence> BASIC_UNESCAPE;

    static {
        BASIC_UNESCAPE = Collections.unmodifiableMap(invert(BASIC_ESCAPE));
    }

    /**
     * A Map&lt;CharSequence, CharSequence&gt; to escape the apostrophe character to
     * its XML character entity.
     */
    public static final Map<CharSequence, CharSequence> APOS_ESCAPE;

    static {
        final Map<CharSequence, CharSequence> initialMap = new HashMap<>();
        // XML apostrophe
        initialMap.put("'", "&apos;");
        APOS_ESCAPE = Collections.unmodifiableMap(initialMap);
    }

    /**
     * Reverse of {@link #APOS_ESCAPE} for unescaping purposes.
     */
    public static final Map<CharSequence, CharSequence> APOS_UNESCAPE;

    static {
        APOS_UNESCAPE = Collections.unmodifiableMap(invert(APOS_ESCAPE));
    }

    /**
     * A Map&lt;CharSequence, CharSequence&gt; to escape the Java control characters.
     *
     * Namely: {@code \b \n \t \f \r}
     */
    public static final Map<CharSequence, CharSequence> JAVA_CTRL_CHARS_ESCAPE;

    static {
        final Map<CharSequence, CharSequence> initialMap = new HashMap<>();
        initialMap.put("\b", "\\b");
        initialMap.put("\n", "\\n");
        initialMap.put("\t", "\\t");
        initialMap.put("\f", "\\f");
        initialMap.put("\r", "\\r");
        JAVA_CTRL_CHARS_ESCAPE = Collections.unmodifiableMap(initialMap);
    }

    /**
     * Reverse of {@link #JAVA_CTRL_CHARS_ESCAPE} for unescaping purposes.
     */
    public static final Map<CharSequence, CharSequence> JAVA_CTRL_CHARS_UNESCAPE;

    static {
        JAVA_CTRL_CHARS_UNESCAPE = Collections.unmodifiableMap(invert(JAVA_CTRL_CHARS_ESCAPE));
    }

    /**
     * Inverts an escape Map into an unescape Map.
     *
     * @param map Map&lt;String, String&gt; to be inverted.
     * @return Map&lt;String, String&gt; inverted array.
     */
    public static Map<CharSequence, CharSequence> invert(final Map<CharSequence, CharSequence> map) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Deprecated, only defines static methods.
     *
     * @deprecated Will be private.
     */
    @Deprecated
    public EntityArrays() {
        // empty
    }
}
