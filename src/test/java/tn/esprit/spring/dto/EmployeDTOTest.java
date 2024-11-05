package tn.esprit.spring.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class EmployeDTOTest {


    @Test
    void testDefaultConstructor() {
        EmployeDTO employe = new EmployeDTO();
        assertNull(employe.getName());
        assertNull(employe.getEmail());
    }

    @Test
    void testParameterizedConstructor() {
        EmployeDTO employe = new EmployeDTO("John Doe", "john.doe@example.com");
        assertEquals("John Doe", employe.getName());
        assertEquals("john.doe@example.com", employe.getEmail());
    }

    @Test
    void testSetName() {
        EmployeDTO employe = new EmployeDTO();
        employe.setName("Jane Doe");
        assertEquals("Jane Doe", employe.getName());
    }

    @Test
    void testSetEmail() {
        EmployeDTO employe = new EmployeDTO();
        employe.setEmail("jane.doe@example.com");
        assertEquals("jane.doe@example.com", employe.getEmail());
    }
}
