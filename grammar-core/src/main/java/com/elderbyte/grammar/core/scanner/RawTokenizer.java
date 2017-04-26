package com.elderbyte.grammar.core.scanner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


class RawTokenizer {

    static class Builder{

        private final List<String> dels = new ArrayList<>();
        private final List<String> stringToggles = new ArrayList<>();

        private Builder(){}

        public Builder withDelemiting(String delemiting){
            dels.add(delemiting);
            return this;
        }

        public Builder withDelemiting(Collection<String> delemiting){
            dels.addAll(delemiting);
            return this;
        }

        public Builder withStringToggle(String toggle){
            stringToggles.add(toggle);
            return this;
        }

        public Builder withStringToggle(Collection<String> toggle){
            stringToggles.addAll(toggle);
            return this;
        }


        public RawTokenizer build(){
            return new  RawTokenizer(dels, stringToggles);
        }

    }

    public static Builder start(){
        return new Builder();
    }


    private final List<String> dels;
    private final List<String> stringToggles;


    private RawTokenizer(List<String> delims, List<String> stringToggles){

        this.dels = (new ArrayList<>(delims)).stream()
                .sorted((a,b) -> Integer.compare(b.length(), a.length()))
                .collect(Collectors.toList());

        this.stringToggles = stringToggles;
    }


    public List<String> tokenize(String input){

        List<String> rawTokens = new ArrayList<>(input.length()/2);

        int noDelStartPos = -1;
        boolean inStringContext = false;

        for(int i=0;i < input.length(); i++){

            String current = input.substring(i);

            String toggle = stringToggle(current);

            if(toggle != null){

                // String literal has been toggled

                if(!inStringContext){
                    noDelStartPos = i+toggle.length();
                    inStringContext = true;
                }else{
                    inStringContext = false;
                    String literal = input.substring(noDelStartPos, i);
                    rawTokens.add(literal);
                    noDelStartPos = -1;
                }

            }else{
                if(!inStringContext) {
                    String del = startingDelemiter(current);
                    if (del != null) {

                        if (noDelStartPos != -1) {
                            String raw = input.substring(noDelStartPos, i);
                            rawTokens.add(raw);
                            noDelStartPos = -1;
                        }

                        rawTokens.add(input.substring(i, i + del.length()));
                        i += del.length() - 1;
                    } else {
                        if (noDelStartPos == -1) {
                            noDelStartPos = i;
                        }
                    }
                }
            }
        }

        // Ensure the rest is accounted for
        if(noDelStartPos != -1){
            rawTokens.add(input.substring(noDelStartPos));
        }

        return rawTokens;
    }


    private String stringToggle(String input){
        for (String stringToggle : stringToggles) {
            if(input.startsWith(stringToggle))
            {
                return stringToggle;
            }
        }
        return null;
    }

    private String startingDelemiter(String input){
        for (String del : dels) { // TODO Improve performance with intelligent index o.0
            if(input.startsWith(del))
            {
                return del;
            }
        }
        return null;
    }


}
