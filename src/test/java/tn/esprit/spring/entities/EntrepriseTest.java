package tn.esprit.spring.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.entities.Entreprise;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EntrepriseTest {

    private Entreprise entreprise;
    private List<Departement> departements;

    @BeforeEach
    void setUp() {
        entreprise = new Entreprise();
        departements = new ArrayList<>();
    }

    @Test
    void testDefaultConstructor() {
        assertNotNull(entreprise);
        assertNull(entreprise.getName());
        assertNull(entreprise.getRaisonSocial());
        assertNotNull(entreprise.getDepartements());
        assertTrue(entreprise.getDepartements().isEmpty());
    }

    @Test
    void testParameterizedConstructor() {
        Entreprise entreprise = new Entreprise("Tech Corp", "Société Anonyme");
        assertEquals("Tech Corp", entreprise.getName());
        assertEquals("Société Anonyme", entreprise.getRaisonSocial());
        assertNotNull(entreprise.getDepartements());
        assertTrue(entreprise.getDepartements().isEmpty());
    }

    @Test
    void testSettersAndGetters() {
        entreprise.setId(1);
        entreprise.setName("Tech Solutions");
        entreprise.setRaisonSocial("SARL");

        assertEquals(1, entreprise.getId());
        assertEquals("Tech Solutions", entreprise.getName());
        assertEquals("SARL", entreprise.getRaisonSocial());
    }

    @Test
    void testSetAndGetDepartements() {
        Departement dep1 = new Departement("IT");
        Departement dep2 = new Departement("Finance");

        departements.add(dep1);
        departements.add(dep2);

        entreprise.setDepartements(departements);

        assertEquals(2, entreprise.getDepartements().size());
        assertTrue(entreprise.getDepartements().contains(dep1));
        assertTrue(entreprise.getDepartements().contains(dep2));
    }

    @Test
    void testAddDepartement() {
        Departement dep = new Departement("HR");
        entreprise.addDepartement(dep);

        assertEquals(1, entreprise.getDepartements().size());
        assertTrue(entreprise.getDepartements().contains(dep));
        assertEquals(entreprise, dep.getEntreprise());
    }

    @Test
    void testAddMultipleDepartements() {
        Departement dep1 = new Departement("HR");
        Departement dep2 = new Departement("Marketing");

        entreprise.addDepartement(dep1);
        entreprise.addDepartement(dep2);

        assertEquals(2, entreprise.getDepartements().size());
        assertTrue(entreprise.getDepartements().contains(dep1));
        assertTrue(entreprise.getDepartements().contains(dep2));
        assertEquals(entreprise, dep1.getEntreprise());
        assertEquals(entreprise, dep2.getEntreprise());
    }
}
