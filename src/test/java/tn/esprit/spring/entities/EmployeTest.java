package tn.esprit.spring.entities;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class EmployeTest {

    @Test
    void testEmployeCreation() {
        Employe employe = new Employe(1, "Jane", "Smith", "jane.smith@example.com", "password456", true, Role.ADMINISTRATEUR);

        assertEquals(1, employe.getId());
        assertEquals("Jane", employe.getPrenom());
        assertEquals("Smith", employe.getNom());
        assertEquals("jane.smith@example.com", employe.getEmail());
        assertTrue(employe.isActif());
        assertEquals(Role.ADMINISTRATEUR, employe.getRole());
    }

    @Test
    void testSettersAndGetters() {
        Employe employe = new Employe();
        employe.setId(2);
        employe.setPrenom("John");
        employe.setNom("Doe");
        employe.setEmail("john.doe@example.com");
        employe.setActif(false);
        employe.setRole(Role.TECHNICIEN);

        assertEquals(2, employe.getId());
        assertEquals("John", employe.getPrenom());
        assertEquals("Doe", employe.getNom());
        assertEquals("john.doe@example.com", employe.getEmail());
        assertFalse(employe.isActif());
        assertEquals(Role.TECHNICIEN, employe.getRole());
    }

    @Test
    void testToString() {
        Employe employe = new Employe(1, "Jane", "Doe", "jane.doe@example.com", "password", true, Role.ADMINISTRATEUR);
        String expected = "Employe [id=1, prenom=Jane, nom=Doe, email=jane.doe@example.com, password=password, actif=true, role=ADMINISTRATEUR]";
        assertEquals(expected, employe.toString());
    }
}
