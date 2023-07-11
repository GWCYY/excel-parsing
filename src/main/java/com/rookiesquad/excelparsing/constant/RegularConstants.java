package com.rookiesquad.excelparsing.constant;

import java.util.regex.Pattern;

public final class RegularConstants {

    private RegularConstants() {
    }

    public static final String NUMBER_REGULAR = "\\d+";
    public static final Pattern NUMBER_PATTERN = Pattern.compile(NUMBER_REGULAR);
}
