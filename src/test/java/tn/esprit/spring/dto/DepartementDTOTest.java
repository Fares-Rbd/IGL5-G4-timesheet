package tn.esprit.spring.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DepartementDTOTest {

    @Test
    void testGetName() {
        DepartementDTO departement = new DepartementDTO();
        departement.setName("Human Resources");
        assertEquals("Human Resources", departement.getName());
    }

    @Test
    void testSetName() {
        DepartementDTO departement = new DepartementDTO();
        departement.setName("Finance");
        assertEquals("Finance", departement.getName());
    }

}
