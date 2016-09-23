package com.elderbyte.grammar.scanner;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


public class RawTokenizer {

    private String input;
    private List<String> dels;

    public RawTokenizer(String input, Collection<String> delims){
        this.input = input;
        this.dels = (new ArrayList<>(delims)).stream()
                .sorted((a,b) -> Integer.compare(b.length(), a.length()))
                .collect(Collectors.toList());
    }


    public List<String> tokenize(){

        List<String> rawTokens = new ArrayList<>(input.length()/2);

        int noDelStartPos = -1;

        for(int i=0;i < input.length(); i++){

            String current = input.substring(i);

            String del = startingDelemiter(current);
            if(del != null){

                if(noDelStartPos != -1){
                    String raw = input.substring(noDelStartPos, i);
                    rawTokens.add(raw);
                    noDelStartPos = -1;
                }

                rawTokens.add(input.substring(i, i + del.length()));
                i += del.length()-1;
            }else{
                if(noDelStartPos == -1){
                    noDelStartPos = i;
                }
            }
        }

        if(noDelStartPos != -1){
            rawTokens.add(input.substring(noDelStartPos));
        }

        return rawTokens;
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
