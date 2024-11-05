package tn.esprit.spring.entities;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.Arrays;

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

    @Test
    void testEmployeWithDifferentConstructors() {
        Employe employe1 = new Employe("Alice", "WonderLand", "alice@example.com", "securePassword", true, Role.TECHNICIEN);
        assertEquals("WonderLand", employe1.getPrenom());
        assertEquals("Alice", employe1.getNom());
        assertEquals("alice@example.com", employe1.getEmail());
        assertTrue(employe1.isActif());
        assertEquals(Role.TECHNICIEN, employe1.getRole());

        Employe employe2 = new Employe("Bob", "Builder", "bob@example.com", false, Role.CHEF_DEPARTEMENT);
        assertEquals("Builder", employe2.getPrenom());
        assertEquals("Bob", employe2.getNom());
        assertEquals("bob@example.com", employe2.getEmail());
        assertFalse(employe2.isActif());
        assertEquals(Role.CHEF_DEPARTEMENT, employe2.getRole());
    }

    @Test
    void testDepartements() {
        Employe employe = new Employe();
        Departement departement1 = new Departement(); // Assuming you have a default constructor
        Departement departement2 = new Departement();
        employe.setDepartements(Arrays.asList(departement1, departement2));

        assertEquals(2, employe.getDepartements().size());
        assertTrue(employe.getDepartements().contains(departement1));
        assertTrue(employe.getDepartements().contains(departement2));
    }

    @Test
    void testContrat() {
        Employe employe = new Employe();
        Contrat contrat = new Contrat(); // Assuming you have a default constructor
        employe.setContrat(contrat);
        assertEquals(contrat, employe.getContrat());
    }

    @Test
    void testTimesheets() {
        Employe employe = new Employe();
        Timesheet timesheet1 = new Timesheet(); // Assuming you have a default constructor
        Timesheet timesheet2 = new Timesheet();
        employe.setTimesheets(Arrays.asList(timesheet1, timesheet2));

        assertEquals(2, employe.getTimesheets().size());
        assertTrue(employe.getTimesheets().contains(timesheet1));
        assertTrue(employe.getTimesheets().contains(timesheet2));
    }

    @Test
    void testPasswords(){
        Employe employe = new Employe();
        employe.setPassword("password123");
        assertEquals("password123", employe.getPassword());
    }
}
