package tn.esprit.spring.entities;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import tn.esprit.spring.services.EmployeServiceImpl; 
import tn.esprit.spring.repository.EmployeRepository; 

@ExtendWith(MockitoExtension.class) 
class EmployeTest {

    @InjectMocks
    private EmployeServiceImpl employeService; // Inject the actual implementation class

    @Mock
    private EmployeRepository employeRepository; // Mock any dependencies

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this); // Initialize mocks
    }

    @Test
    void testCreateEmploye() {

        Role role = Role.valueOf("ADMINISTRATEUR");
        Employe employe = new Employe("Doe", "John", "john.doe@example.com", true, role);

        when(employeRepository.save(employe)).thenReturn(employe);

        // This calls the repository (the mock)
        int result = employeService.addOrUpdateEmploye(employe);

        assertEquals(employe.getId(), result);
    }

    @Test
    void testGetEmployePrenomById() {

        int employeId = 1;
        Employe employe = new Employe("Doe", "John", "john.doe@example.com", true, Role.valueOf("ADMINISTRATEUR"));

        when(employeRepository.findById(employeId)).thenReturn(Optional.of(employe));

        // This calls the repository (mock)
        String result = employeService.getEmployePrenomById(employeId);

        assertNotNull(result);
        assertEquals(employe.getPrenom(), result);
    }

    @Test
    void testUpdateEmploye() {

        Role role = Role.valueOf("ADMINISTRATEUR");
        Employe employe = new Employe("Doe", "John", "john.doe@example.com", true, role);

        employe.setEmail("john.new@example.com");
        when(employeRepository.save(employe)).thenReturn(employe);

        // This calls the repository (mock)
        employeService.addOrUpdateEmploye(employe);

        assertEquals("john.new@example.com", employe.getEmail());
    }

    @Test
    void testDeleteEmployeById() {
        // Given
        int employeId = 1;
        Role role = Role.valueOf("ADMINISTRATEUR");
        Employe employe = new Employe("Doe", "John", "john.doe@example.com", true, role);
        employe.setId(employeId);

        // Initialize the Departement with an empty list
        Departement departement = new Departement();
        departement.setEmployes(new ArrayList<>());
        employe.setDepartements(List.of(departement));

        when(employeRepository.findById(employeId)).thenReturn(Optional.of(employe));

        employeService.deleteEmployeById(employeId);

        verify(employeRepository, times(1)).delete(employe);
    }


}
