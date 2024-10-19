package tn.esprit.spring.entities;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Date;

class ContratTest {

    private Contrat contrat;
    private Employe mockEmploye;

    @BeforeEach
    void setUp() {
        // Mock the Employe entity using Mockito
        mockEmploye = Mockito.mock(Employe.class);

        // Initialize a Contrat object
        contrat = new Contrat(new Date(), "CDI", 3000f);
        contrat.setEmploye(mockEmploye);
    }

    @Test
    void testGetAndSetDateDebut() {
        Date newDate = new Date();
        contrat.setDateDebut(newDate);
        assertEquals(newDate, contrat.getDateDebut(), "The start date should match the one set.");
    }

    @Test
    void testGetAndSetReference() {
        contrat.setReference(12345);
        assertEquals(12345, contrat.getReference(), "The reference should be set correctly.");
    }

    @Test
    void testGetAndSetTypeContrat() {
        contrat.setTypeContrat("CDD");
        assertEquals("CDD", contrat.getTypeContrat(), "The contract type should be 'CDD'.");
    }

    @Test
    void testGetAndSetSalaire() {
        contrat.setSalaire(4000f);
        assertEquals(4000f, contrat.getSalaire(), 0.01, "The salary should be 4000f.");
    }

    @Test
    void testGetAndSetEmploye() {
        Employe anotherEmploye = Mockito.mock(Employe.class);
        contrat.setEmploye(anotherEmploye);
        assertEquals(anotherEmploye, contrat.getEmploye(), "The employee should be set correctly.");
    }

    @Test
    void testNoArgConstructor() {
        Contrat emptyContrat = new Contrat();
        assertNull(emptyContrat.getDateDebut(), "The dateDebut should be null.");
        assertNull(emptyContrat.getTypeContrat(), "The typeContrat should be null.");
        assertEquals(0, emptyContrat.getSalaire(), "The salaire should be initialized to 0.");
    }

    @Test
    void testParameterizedConstructor() {
        Date startDate = new Date();
        Contrat paramContrat = new Contrat(startDate, "CDD", 4500f);

        assertEquals(startDate, paramContrat.getDateDebut(), "The start date should match.");
        assertEquals("CDD", paramContrat.getTypeContrat(), "The type of contract should be 'CDD'.");
        assertEquals(4500f, paramContrat.getSalaire(), 0.01, "The salary should be 4500f.");
    }
}
