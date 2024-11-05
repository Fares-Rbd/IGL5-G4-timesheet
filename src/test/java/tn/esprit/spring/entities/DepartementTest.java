package tn.esprit.spring.entities;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DepartementTest {

    private Departement departement;
    private List<Employe> employes;
    private List<Mission> missions;
    private Entreprise entreprise;

    @BeforeEach
    void setUp() {
        departement = new Departement("IT");
        employes = new ArrayList<>();
        missions = new ArrayList<>();
        entreprise = new Entreprise();
    }

    @Test
    void testDefaultConstructor() {
        Departement defaultDepartement = new Departement();
        assertNotNull(defaultDepartement);
    }

    @Test
    void testParameterizedConstructor() {
        Departement paramDepartement = new Departement("Finance");
        assertEquals("Finance", paramDepartement.getName());
    }

    @Test
    void testSetAndGetId() {
        departement.setId(1);
        assertEquals(1, departement.getId());
    }

    @Test
    void testSetAndGetName() {
        departement.setName("HR");
        assertEquals("HR", departement.getName());
    }

    @Test
    void testSetAndGetEmployes() {
        Employe employe = new Employe();
        employes.add(employe);
        departement.setEmployes(employes);
        assertEquals(employes, departement.getEmployes());
    }

    @Test
    void testSetAndGetMissions() {
        Mission mission = new Mission();
        missions.add(mission);
        departement.setMissions(missions);
        assertEquals(missions, departement.getMissions());
    }

    @Test
    void testSetAndGetEntreprise() {
        departement.setEntreprise(entreprise);
        assertEquals(entreprise, departement.getEntreprise());
    }

    @Test
    void testEmployesListIsIgnoredInJsonSerialization() throws NoSuchFieldException {
        assertTrue(departement.getClass().getDeclaredField("employes").isAnnotationPresent(JsonIgnore.class),
                "The employes field should be ignored in JSON serialization.");
    }
}
