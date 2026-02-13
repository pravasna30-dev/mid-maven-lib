package com.example.midlib;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration tests for MidLibService.
 * Verifies end-to-end behavior and interactions between methods.
 */
class MidLibServiceIT {

    @Test
    void fullLifecycle_createProcessAndIdentify() {
        MidLibService service = new MidLibService();

        // Verify identity
        String id = service.identity();
        assertNotNull(id);
        assertTrue(id.contains(service.getLevel()));
        assertTrue(id.contains(service.getVersion()));

        // Process items and verify prefix matches level
        List<String> items = List.of("task-1", "task-2", "task-3");
        List<String> processed = service.process(items);
        assertEquals(items.size(), processed.size());
        for (String item : processed) {
            assertTrue(item.startsWith("[" + service.getLevel() + "]"));
        }
    }

    @Test
    void say_outputMatchesIdentity() {
        MidLibService service = new MidLibService();

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream original = System.out;
        System.setOut(new PrintStream(out));

        MidLibService.say();

        System.setOut(original);

        String output = out.toString().trim();
        assertTrue(output.contains(service.getLevel()));
    }

    @Test
    void process_largeDataset() {
        MidLibService service = new MidLibService();
        List<String> items = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            items.add("item-" + i);
        }

        List<String> processed = service.process(items);
        assertEquals(1000, processed.size());
        assertEquals("[MID] item-0", processed.get(0));
        assertEquals("[MID] item-999", processed.get(999));
    }

    @Test
    void multipleInstances_areIndependent() {
        MidLibService a = new MidLibService();
        MidLibService b = new MidLibService();

        assertEquals(a.getLevel(), b.getLevel());
        assertEquals(a.getVersion(), b.getVersion());
        assertEquals(a.identity(), b.identity());
    }

    @Test
    void process_preservesOrder() {
        MidLibService service = new MidLibService();
        List<String> items = List.of("z", "a", "m", "b");
        List<String> processed = service.process(items);

        assertEquals("[MID] z", processed.get(0));
        assertEquals("[MID] a", processed.get(1));
        assertEquals("[MID] m", processed.get(2));
        assertEquals("[MID] b", processed.get(3));
    }
}
