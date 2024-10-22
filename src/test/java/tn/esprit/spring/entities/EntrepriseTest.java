package tn.esprit.spring.entities;

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.entities.Entreprise;

public class EntrepriseTest {

    private Entreprise entreprise;

    @BeforeEach
    public void setUp() {
        // This will run before each test case
        entreprise = new Entreprise("ABC Corp", "SA");
    }

    // 1. Test entity creation with parameters
    @Test
    public void testEntrepriseCreation() {
        assertEquals("ABC Corp", entreprise.getName());
        assertEquals("SA", entreprise.getRaisonSocial());
    }

    // 2. Test default constructor
    @Test
    public void testDefaultConstructor() {
        Entreprise defaultEntreprise = new Entreprise();
        assertNotNull(defaultEntreprise);
    }

    // 3. Test getters and setters
    @Test
    public void testGettersAndSetters() {
        Entreprise entreprise = new Entreprise();
        entreprise.setName("XYZ Inc");
        entreprise.setRaisonSocial("SARL");

        assertEquals("XYZ Inc", entreprise.getName());
        assertEquals("SARL", entreprise.getRaisonSocial());
    }

    // 4. Test adding a Departement to the Entreprise
    @Test
    public void testAddDepartement() {
        Departement departement = new Departement();
        entreprise.addDepartement(departement);

        assertEquals(1, entreprise.getDepartements().size());
        assertEquals(entreprise, departement.getEntreprise());
    }

    // 5. Test departement list initialization
    @Test
    public void testDepartementListInitialization() {
        Entreprise entreprise = new Entreprise("ABC Corp", "SA");
        assertNotNull(entreprise.getDepartements());
        assertTrue(entreprise.getDepartements().isEmpty());
    }

    // 6. Test setting the departements list
    @Test
    public void testSetDepartements() {
        List<Departement> departements = new ArrayList<>();
        Departement dep1 = new Departement();
        departements.add(dep1);

        entreprise.setDepartements(departements);

        assertEquals(1, entreprise.getDepartements().size());
        assertTrue(entreprise.getDepartements().contains(dep1));
    }

    // 7. Test null values for name and raisonSocial
    @Test
    public void testNullNameOrRaisonSocial() {
        Entreprise entreprise = new Entreprise();
        entreprise.setName(null);
        entreprise.setRaisonSocial(null);

        assertNull(entreprise.getName());
        assertNull(entreprise.getRaisonSocial());
    }

    // 8. Test updating the name and raisonSocial after creation
    @Test
    public void testUpdateEntrepriseDetails() {
        entreprise.setName("New Corp");
        entreprise.setRaisonSocial("New SARL");

        assertEquals("New Corp", entreprise.getName());
        assertEquals("New SARL", entreprise.getRaisonSocial());
    }
}
