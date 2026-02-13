package com.example.midlib;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MidLibServiceTest {

    private MidLibService service;

    @BeforeEach
    void setUp() {
        service = new MidLibService();
    }

    @Test
    void say_printsCorrectMessage() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        MidLibService.say();

        assertEquals("I am a MID level library", out.toString().trim());
        System.setOut(System.out);
    }

    @Test
    void getLevel_returnsMID() {
        assertEquals("MID", service.getLevel());
    }

    @Test
    void getVersion_returns100() {
        assertEquals("1.0.0", service.getVersion());
    }

    @Test
    void identity_returnsFormattedString() {
        assertEquals("mid-maven-lib v1.0.0 [MID]", service.identity());
    }

    @Test
    void identity_containsLevel() {
        assertTrue(service.identity().contains("MID"));
    }

    @Test
    void identity_containsVersion() {
        assertTrue(service.identity().contains("1.0.0"));
    }

    @Test
    void process_prefixesItems() {
        List<String> result = service.process(List.of("alpha", "beta"));
        assertEquals(2, result.size());
        assertEquals("[MID] alpha", result.get(0));
        assertEquals("[MID] beta", result.get(1));
    }

    @Test
    void process_singleItem() {
        List<String> result = service.process(List.of("hello"));
        assertEquals(1, result.size());
        assertEquals("[MID] hello", result.get(0));
    }

    @Test
    void process_emptyList_returnsEmpty() {
        List<String> result = service.process(List.of());
        assertTrue(result.isEmpty());
    }

    @Test
    void process_null_returnsEmpty() {
        List<String> result = service.process(null);
        assertTrue(result.isEmpty());
    }
}
