package tn.esprit.spring.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EntrepriseDTOTest {

    @Test
    void testGetName() {
        EntrepriseDTO entreprise = new EntrepriseDTO();
        entreprise.setName("Tech Corp");
        assertEquals("Tech Corp", entreprise.getName());
    }

    @Test
    void testSetName() {
        EntrepriseDTO entreprise = new EntrepriseDTO();
        entreprise.setName("Innovate Ltd");
        assertEquals("Innovate Ltd", entreprise.getName());
    }

    @Test
    void testGetRaisonSocial() {
        EntrepriseDTO entreprise = new EntrepriseDTO();
        entreprise.setRaisonSocial("Innovative Solutions");
        assertEquals("Innovative Solutions", entreprise.getRaisonSocial());
    }

    @Test
    void testSetRaisonSocial() {
        EntrepriseDTO entreprise = new EntrepriseDTO();
        entreprise.setRaisonSocial("Tech Innovations");
        assertEquals("Tech Innovations", entreprise.getRaisonSocial());
    }
}
