package com.elderbyte.grammar.scanner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 *
 */
public final class StringUtils {

    public static List<String> splitKeep(String input, Iterable<String> delims)
    {
        String regex = "";

        for(String delim : delims){
            regex += "((?<="+Pattern.quote(delim)+")|(?="+Pattern.quote(delim)+"))" + "|";
        }
        regex = regex.substring(0, regex.length()-1);

        return Arrays.asList(Pattern.compile(regex).split(input));
    }




}
