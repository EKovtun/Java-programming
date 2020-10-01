public class StringUtils {
    /**
     * <p>Returns either the passed in String,
     * or if the String is {@code null}, an empty String ("").</p>
     *
     * <pre>
     * StringUtils.defaultString(null)  = ""
     * StringUtils.defaultString("")    = ""
     * StringUtils.defaultString("bat") = "bat"
     * </pre>
     *
     * @param str the String to check, may be {@code null}
     * @return the passed in String, or the empty String if it
     *  was {@code null}
     */
    public static String defaultString(String str) {
        return str == null ? "" : str;
    }
}
