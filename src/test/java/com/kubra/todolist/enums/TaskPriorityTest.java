package com.kubra.todolist.enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TaskPriorityTest {

    @Test
    void testEnumValuesCount() {
        TaskPriority[] values = TaskPriority.values();
        assertEquals(3, values.length);
    }

    @Test
    void testEnumNames() {
        assertNotNull(TaskPriority.valueOf("LOW"));
        assertNotNull(TaskPriority.valueOf("MEDIUM"));
        assertNotNull(TaskPriority.valueOf("HIGH"));
    }

    @Test
    void testOrdinalValues() {
        assertEquals(0, TaskPriority.LOW.ordinal());
        assertEquals(1, TaskPriority.MEDIUM.ordinal());
        assertEquals(2, TaskPriority.HIGH.ordinal());
    }

    @Test
    void testValueOfThrowsExceptionForInvalidValue() {
        assertThrows(IllegalArgumentException.class, () -> TaskPriority.valueOf("INVALID"));
    }
}