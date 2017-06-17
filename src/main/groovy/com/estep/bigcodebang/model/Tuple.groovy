package com.estep.bigcodebang.model

/**
 * Modeled after Python's Tuple implementation, this class is an immutable container for a finite list of values of any
 * type.
 *
 * @author dougestep.
 */
class Tuple {
    private static final String SINGLE = "'";
    private static final String COMMA_SP = ", ";
    private Object[] arguments;

    /**
     * Constructs this class.
     *
     * @param arguments the tuple arguments.
     */
    Tuple(Object... arguments) {
        if (arguments == null || arguments.length == 0) {
            throw new IllegalArgumentException("A tuple requires at least one value");
        }
        this.arguments = arguments;
    }

    /**
     * Returns the number of values assigned to this tuple.
     *
     * @return the number of values.
     */
    int size() {
        arguments.length;
    }

    /**
     * Returns the value assigned at the specified index.
     *
     * @param index the index.  The index is zero based.
     * @param <T>   defines the type of the return value.
     * @return the value.
     */
    @SuppressWarnings("unchecked")
    <T> T get(final int index) {
        if (index < 0 || index >= size()) {
            final int max = size() - 1;
            final String validValues;
            if (max == 0) {
                validValues = "Valid values are 0";
            } else {
                validValues = "Valid values are 0 through " + max;
            }
            throw new IllegalArgumentException("Invalid index value. " + validValues);
        }

        (T) arguments[index];
    }

    @Override
    String toString() {
        final StringBuilder buf = new StringBuilder();
        buf.append('(');
        for (int i = 0; i < size(); i++) {
            final Object value = arguments[i];
            if (value instanceof String) {
                String strValue = (String) value;
                if (strValue.contains(",")) {
                    strValue = strValue.replace(SINGLE, "''");
                    buf.append('\'').append(strValue).append(COMMA_SP);
                    continue;
                }
            }
            buf.append(arguments[i]).append(COMMA_SP);
        }
        String str = buf.toString();
        int idx = str.lastIndexOf(',');

        str.substring(0, idx) + ")";
    }

    @Override
    boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof Tuple)) return false;

        Tuple tuple = (Tuple) o;
        arguments.equals(tuple.arguments)
    }

    @Override
    int hashCode() {
        arguments.hashCode()
    }
}
