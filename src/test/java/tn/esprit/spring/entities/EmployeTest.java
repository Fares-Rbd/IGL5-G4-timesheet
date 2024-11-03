package tn.esprit.spring.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tn.esprit.spring.entities.Contrat;
import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.entities.Employe;
import tn.esprit.spring.entities.Role;
import tn.esprit.spring.entities.Timesheet;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EmployeTest {

    private Employe employe;
    private List<Departement> departements;
    private List<Timesheet> timesheets;

    @BeforeEach
    void setUp() {
        employe = new Employe();
        departements = new ArrayList<>();
        timesheets = new ArrayList<>();
    }

    @Test
    void testDefaultConstructor() {
        assertNotNull(employe);
    }

    @Test
    void testParameterizedConstructor1() {
        Employe employe = new Employe(1, "John", "Doe", "john@example.com", "password123", true, Role.ADMINISTRATEUR);
        assertEquals(1, employe.getId());
        assertEquals("John", employe.getPrenom());
        assertEquals("Doe", employe.getNom());
        assertEquals("john@example.com", employe.getEmail());
        assertEquals("password123", employe.getPassword());
        assertTrue(employe.isActif());
        assertEquals(Role.ADMINISTRATEUR, employe.getRole());
    }

    @Test
    void testParameterizedConstructor2() {
        Employe employe = new Employe("Doe", "John", "john@example.com", "password123", true, Role.ADMINISTRATEUR);
        assertEquals("John", employe.getPrenom());
        assertEquals("Doe", employe.getNom());
        assertEquals("john@example.com", employe.getEmail());
        assertEquals("password123", employe.getPassword());
        assertTrue(employe.isActif());
        assertEquals(Role.ADMINISTRATEUR, employe.getRole());
    }

    @Test
    void testParameterizedConstructor3() {
        Employe employe = new Employe("Doe", "John", "john@example.com", true, Role.INGENIEUR);
        assertEquals("John", employe.getPrenom());
        assertEquals("Doe", employe.getNom());
        assertEquals("john@example.com", employe.getEmail());
        assertNull(employe.getPassword());
        assertTrue(employe.isActif());
        assertEquals(Role.INGENIEUR, employe.getRole());
    }

    @Test
    void testSettersAndGetters() {
        employe.setId(1);
        employe.setPrenom("John");
        employe.setNom("Doe");
        employe.setEmail("john@example.com");
        employe.setPassword("password123");
        employe.setActif(true);
        employe.setRole(Role.ADMINISTRATEUR);

        assertEquals(1, employe.getId());
        assertEquals("John", employe.getPrenom());
        assertEquals("Doe", employe.getNom());
        assertEquals("john@example.com", employe.getEmail());
        assertEquals("password123", employe.getPassword());
        assertTrue(employe.isActif());
        assertEquals(Role.ADMINISTRATEUR, employe.getRole());
    }

    @Test
    void testSetAndGetDepartements() {
        Departement dep1 = new Departement("IT");
        Departement dep2 = new Departement("HR");

        departements.add(dep1);
        departements.add(dep2);

        employe.setDepartements(departements);

        assertEquals(2, employe.getDepartements().size());
        assertTrue(employe.getDepartements().contains(dep1));
        assertTrue(employe.getDepartements().contains(dep2));
    }

    @Test
    void testSetAndGetContrat() {
        Contrat contrat = new Contrat();
        employe.setContrat(contrat);

        assertEquals(contrat, employe.getContrat());
    }

    @Test
    void testSetAndGetTimesheets() {
        Timesheet ts1 = new Timesheet();
        Timesheet ts2 = new Timesheet();

        timesheets.add(ts1);
        timesheets.add(ts2);

        employe.setTimesheets(timesheets);

        assertEquals(2, employe.getTimesheets().size());
        assertTrue(employe.getTimesheets().contains(ts1));
        assertTrue(employe.getTimesheets().contains(ts2));
    }

    @Test
    void testToString() {
        Employe employe = new Employe(1, "John", "Doe", "john@example.com", "password123", true, Role.ADMINISTRATEUR);
        String expectedString = "Employe [id=1, prenom=John, nom=Doe, email=john@example.com, password=password123, actif=true, role=ADMINISTRATEUR]";

        assertEquals(expectedString, employe.toString());
    }

    @Test
    void testActifStatus() {
        employe.setActif(true);
        assertTrue(employe.isActif());

        employe.setActif(false);
        assertFalse(employe.isActif());
    }

    @Test
    void testRoleAssignment() {
        employe.setRole(Role.CHEF_DEPARTEMENT);
        assertEquals(Role.CHEF_DEPARTEMENT, employe.getRole());

        employe.setRole(Role.INGENIEUR);
        assertEquals(Role.INGENIEUR, employe.getRole());
    }

    @Test
    void testEmailField() {
        employe.setEmail("test@example.com");
        assertEquals("test@example.com", employe.getEmail());
    }
}
