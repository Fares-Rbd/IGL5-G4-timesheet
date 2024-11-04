package tn.esprit.spring.entities;

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EntrepriseTest {

    private Entreprise entreprise;

    @BeforeEach
    void setUp() {
        // This will run before each test case
        entreprise = new Entreprise("ABC Corp", "SA");
    }

    // 1. Test entity creation with parameters
    @Test
    void testEntrepriseCreation() {
        assertEquals("ABC Corp", entreprise.getName());
        assertEquals("SA", entreprise.getRaisonSocial());
    }

    // 2. Test default constructor
    @Test
    void testDefaultConstructor() {
        Entreprise defaultEntreprise = new Entreprise();
        assertNotNull(defaultEntreprise);
    }

    // 3. Test getters and setters
    @Test
    void testGettersAndSetters() {
        Entreprise tempEntreprise = new Entreprise();
        tempEntreprise.setName("XYZ Inc");
        tempEntreprise.setRaisonSocial("SARL");

        assertEquals("XYZ Inc", entreprise.getName());
        assertEquals("SARL", entreprise.getRaisonSocial());
    }

    // 4. Test adding a Departement to the Entreprise
    @Test
    void testAddDepartement() {
        Departement departement = new Departement();
        entreprise.addDepartement(departement);

        assertEquals(1, entreprise.getDepartements().size());
        assertEquals(entreprise, departement.getEntreprise());
    }

    // 5. Test departement list initialization
    @Test
    void testDepartementListInitialization() {
        Entreprise tempEntreprise = new Entreprise("ABC Corp", "SA");
        assertNotNull(tempEntreprise.getDepartements());
        assertTrue(tempEntreprise.getDepartements().isEmpty());
    }

    // 6. Test setting the departements list
    @Test
    void testSetDepartements() {
        List<Departement> departements = new ArrayList<>();
        Departement dep1 = new Departement();
        departements.add(dep1);

        entreprise.setDepartements(departements);

        assertEquals(1, entreprise.getDepartements().size());
        assertTrue(entreprise.getDepartements().contains(dep1));
    }

    // 7. Test null values for name and raisonSocial
    @Test
    void testNullNameOrRaisonSocial() {
        Entreprise tempEntreprise = new Entreprise();
        tempEntreprise.setName(null);
        tempEntreprise.setRaisonSocial(null);

        assertNull(tempEntreprise.getName());
        assertNull(tempEntreprise.getRaisonSocial());
    }

    // 8. Test updating the name and raisonSocial after creation
    @Test
    void testUpdateEntrepriseDetails() {
        entreprise.setName("New Corp");
        entreprise.setRaisonSocial("New SARL");

        assertEquals("New Corp", entreprise.getName());
        assertEquals("New SARL", entreprise.getRaisonSocial());
    }
}
