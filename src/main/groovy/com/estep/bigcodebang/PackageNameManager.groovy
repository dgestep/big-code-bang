package com.estep.bigcodebang

/**
 * Manages the creation of Java packages, adhering to the rules established for a valid package name.
 *
 * @author dougestep.
 */
class PackageNameManager {
    private static final String UND = "_"
    private static final Set<String> KEYWORDS = new HashSet<String>()
    private static final Set<String> RESERVED = new HashSet<String>()

    static {
        KEYWORDS.add("abstract")
        KEYWORDS.add("assert")
        KEYWORDS.add("boolean")
        KEYWORDS.add("break")
        KEYWORDS.add("byte")
        KEYWORDS.add("case")
        KEYWORDS.add("catch")
        KEYWORDS.add("char")
        KEYWORDS.add("class")
        KEYWORDS.add("const")
        KEYWORDS.add("continue")
        KEYWORDS.add("default")
        KEYWORDS.add("do")
        KEYWORDS.add("double")
        KEYWORDS.add("else")
        KEYWORDS.add("enum")
        KEYWORDS.add("extends")
        KEYWORDS.add("final")
        KEYWORDS.add("finally")
        KEYWORDS.add("float")
        KEYWORDS.add("for")
        KEYWORDS.add("goto")
        KEYWORDS.add("if")
        KEYWORDS.add("implements")
        KEYWORDS.add("import")
        KEYWORDS.add("instanceof")
        KEYWORDS.add("int")
        KEYWORDS.add("interface")
        KEYWORDS.add("long")
        KEYWORDS.add("native")
        KEYWORDS.add("new")
        KEYWORDS.add("package")
        KEYWORDS.add("private")
        KEYWORDS.add("protected")
        KEYWORDS.add("public")
        KEYWORDS.add("return")
        KEYWORDS.add("short")
        KEYWORDS.add("static")
        KEYWORDS.add("strictfp")
        KEYWORDS.add("super")
        KEYWORDS.add("switch")
        KEYWORDS.add("synchronized")
        KEYWORDS.add("this")
        KEYWORDS.add("throw")
        KEYWORDS.add("throws")
        KEYWORDS.add("transient")
        KEYWORDS.add("try")
        KEYWORDS.add("void")
        KEYWORDS.add("volatile")
        KEYWORDS.add("while")

        RESERVED.add("true")
        RESERVED.add("null")
        RESERVED.add("false")
    }

    String formatPackageElement(String element) {
        element = element.toLowerCase().trim()
        element = adjustForInvalidChars(element)
        element = adjustForKeywords(element)

        element
    }

    private String adjustForInvalidChars(String element) {
        StringBuilder buf = new StringBuilder(64)
        for (int i = 0; i < element.size(); i++) {
            int ch = element.charAt(i);
            if (i == 0 && Character.isDigit(ch)) {
                buf.append(UND).append((char) ch);
            } else if (!Character.isAlphabetic(ch) && !Character.isDigit(ch) && ch != '_') {
                buf.append(UND)
            } else {
                buf.append((char) ch)
            }
        }
        buf.toString()
    }

    private String adjustForKeywords(String element) {
        if (KEYWORDS.contains(element) || RESERVED.contains(element)) {
            element = UND + element
        }
        element
    }
}
