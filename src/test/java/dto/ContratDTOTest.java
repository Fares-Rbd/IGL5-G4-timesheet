package dto;

import org.junit.jupiter.api.Test;
import tn.esprit.spring.dto.ContratDTO;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class ContratDTOTest {

    @Test
    void testDefaultConstructor() {
        ContratDTO contrat = new ContratDTO();
        assertEquals(0, contrat.getReference());
        assertNull(contrat.getDateDebut());
        assertNull(contrat.getTypeContrat());
        assertEquals(0.0f, contrat.getSalaire());
    }

    @Test
    void testParameterizedConstructor() {
        Date dateDebut = new Date();
        ContratDTO contrat = new ContratDTO(1, dateDebut, "Full-Time", 3000.0f);

        assertEquals(1, contrat.getReference());
        assertEquals(dateDebut, contrat.getDateDebut());
        assertEquals("Full-Time", contrat.getTypeContrat());
        assertEquals(3000.0f, contrat.getSalaire());
    }

    @Test
    void testSetReference() {
        ContratDTO contrat = new ContratDTO();
        contrat.setReference(10);
        assertEquals(10, contrat.getReference());
    }

    @Test
    void testSetDateDebut() {
        ContratDTO contrat = new ContratDTO();
        Date dateDebut = new Date();
        contrat.setDateDebut(dateDebut);
        assertEquals(dateDebut, contrat.getDateDebut());
    }

    @Test
    void testSetTypeContrat() {
        ContratDTO contrat = new ContratDTO();
        contrat.setTypeContrat("Part-Time");
        assertEquals("Part-Time", contrat.getTypeContrat());
    }

    @Test
    void testSetSalaire() {
        ContratDTO contrat = new ContratDTO();
        contrat.setSalaire(1500.0f);
        assertEquals(1500.0f, contrat.getSalaire());
    }
}
