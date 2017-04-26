package com.elderbyte.grammar.core.scanner;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;


public class RawTokenizerTest {

    @Test
    public void tokenize_simple_space() throws Exception {

        RawTokenizer tokenizer = RawTokenizer.start().withDelemiting(" ").build();

        List<String> rawtokens = tokenizer.tokenize("a b c");
        Assert.assertEquals("a", rawtokens.get(0));
        Assert.assertEquals(" ", rawtokens.get(1));
        Assert.assertEquals("b", rawtokens.get(2));
        Assert.assertEquals(" ", rawtokens.get(3));
        Assert.assertEquals("c", rawtokens.get(4));
    }

    @Test
    public void tokenize_simple_space_twice() throws Exception {

        RawTokenizer tokenizer = RawTokenizer.start().withDelemiting(" ").build();

        List<String> rawtokens = tokenizer.tokenize("a b  c");
        Assert.assertEquals("a", rawtokens.get(0));
        Assert.assertEquals(" ", rawtokens.get(1));
        Assert.assertEquals("b", rawtokens.get(2));
        Assert.assertEquals(" ", rawtokens.get(3));
        Assert.assertEquals(" ", rawtokens.get(4));
        Assert.assertEquals("c", rawtokens.get(5));
    }

    @Test
    public void tokenize_ops() throws Exception {

        RawTokenizer tokenizer = RawTokenizer.start()
                .withDelemiting(" ")
                .withDelemiting("+")
                .withDelemiting("-")
                .build();

        List<String> rawtokens = tokenizer.tokenize("a+bu - hello");
        Assert.assertEquals("a", rawtokens.get(0));
        Assert.assertEquals("+", rawtokens.get(1));
        Assert.assertEquals("bu", rawtokens.get(2));
        Assert.assertEquals(" ", rawtokens.get(3));
        Assert.assertEquals("-", rawtokens.get(4));
        Assert.assertEquals(" ", rawtokens.get(5));
        Assert.assertEquals("hello", rawtokens.get(6));
    }

    @Test
    public void tokenize_literal_string_sequence() throws Exception {

        RawTokenizer tokenizer = RawTokenizer.start()
                .withDelemiting(" ")
                .withStringToggle("'")
                .build();

        List<String> rawtokens = tokenizer.tokenize("test 'hello world' 12");
        Assert.assertEquals("test", rawtokens.get(0));
        Assert.assertEquals(" ", rawtokens.get(1));
        Assert.assertEquals("'hello world'", rawtokens.get(2));
        Assert.assertEquals(" ", rawtokens.get(3));
        Assert.assertEquals("12", rawtokens.get(4));
    }

    @Test
    public void tokenize_literal_string_sequence_twice() throws Exception {

        RawTokenizer tokenizer = RawTokenizer.start()
                .withDelemiting(" ")
                .withStringToggle("'")
                .build();

        List<String> rawtokens = tokenizer.tokenize("test 'hello world''move 12' 12");
        Assert.assertEquals("test", rawtokens.get(0));
        Assert.assertEquals(" ", rawtokens.get(1));
        Assert.assertEquals("'hello world'", rawtokens.get(2));
        Assert.assertEquals("'move 12'", rawtokens.get(3));
        Assert.assertEquals(" ", rawtokens.get(4));
        Assert.assertEquals("12", rawtokens.get(5));
    }

    @Test
    public void tokenize_literal_string_doublequotes_sequence_twice() throws Exception {

        RawTokenizer tokenizer = RawTokenizer.start()
                .withDelemiting(" ")
                .withStringToggle("\"")
                .build();

        List<String> rawtokens = tokenizer.tokenize("test \"hello world\" 12");
        Assert.assertEquals("test", rawtokens.get(0));
        Assert.assertEquals(" ", rawtokens.get(1));
        Assert.assertEquals("\"hello world\"", rawtokens.get(2));
        Assert.assertEquals(" ", rawtokens.get(3));
        Assert.assertEquals("12", rawtokens.get(4));
    }

}