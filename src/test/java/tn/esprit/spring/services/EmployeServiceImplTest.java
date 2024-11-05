package tn.esprit.spring.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.spring.entities.*;
import tn.esprit.spring.repository.ContratRepository;
import tn.esprit.spring.repository.DepartementRepository;
import tn.esprit.spring.repository.EmployeRepository;
import tn.esprit.spring.repository.TimesheetRepository;

@ExtendWith(MockitoExtension.class)
class EmployeServiceImplTest {

    @InjectMocks
    private EmployeServiceImpl employeService;

    @Mock
    private EmployeRepository employeRepository;

    @Mock
    private DepartementRepository deptRepoistory;

    @Mock
    private ContratRepository contratRepoistory;

    @Mock
    private TimesheetRepository timesheetRepository;

    private Employe employe;
    private Departement departement;
    private Contrat contrat;

    @BeforeEach
    void setUp() {
        employe = new Employe(1, "John", "Doe", "john.doe@example.com", "password", true, Role.INGENIEUR);
        departement = new Departement("Engineering");
        departement.setId(1);
        contrat = new Contrat();
        contrat.setReference(100);
    }

    @Test
    void testAuthenticate() {
        when(employeRepository.getEmployeByEmailAndPassword("john.doe@example.com", "password")).thenReturn(employe);

        Employe result = employeService.authenticate("john.doe@example.com", "password");

        assertNotNull(result);
        assertEquals(employe, result);
    }

    @Test
    void testAddOrUpdateEmploye() {
        when(employeRepository.save(employe)).thenReturn(employe);

        int result = employeService.addOrUpdateEmploye(employe);

        assertEquals(employe.getId(), result);
        verify(employeRepository).save(employe);
    }

    @Test
    void testMettreAjourEmailByEmployeId() {
        when(employeRepository.findById(1)).thenReturn(Optional.of(employe));

        employeService.mettreAjourEmailByEmployeId("new.email@example.com", 1);

        assertEquals("new.email@example.com", employe.getEmail());
        verify(employeRepository).save(employe);
    }

    @Test
    void testAffecterEmployeADepartement() {
        when(deptRepoistory.findById(1)).thenReturn(Optional.of(departement));
        when(employeRepository.findById(1)).thenReturn(Optional.of(employe));

        employeService.affecterEmployeADepartement(1, 1);

        assertTrue(departement.getEmployes().contains(employe));
        verify(deptRepoistory).findById(1);
        verify(employeRepository).findById(1);
    }

    @Test
    void testDesaffecterEmployeDuDepartement() {
        // Use ArrayList to allow modifications
        departement.setEmployes(new ArrayList<>(Arrays.asList(employe)));
        when(deptRepoistory.findById(1)).thenReturn(Optional.of(departement));

        employeService.desaffecterEmployeDuDepartement(1, 1);

        assertFalse(departement.getEmployes().contains(employe));
    }


    @Test
    void testAjouterContrat() {
        when(contratRepoistory.save(contrat)).thenReturn(contrat);

        int result = employeService.ajouterContrat(contrat);

        assertEquals(contrat.getReference(), result);
        verify(contratRepoistory).save(contrat);
    }

    @Test
    void testAffecterContratAEmploye() {
        when(contratRepoistory.findById(100)).thenReturn(Optional.of(contrat));
        when(employeRepository.findById(1)).thenReturn(Optional.of(employe));

        employeService.affecterContratAEmploye(100, 1);

        assertEquals(employe, contrat.getEmploye());
        verify(contratRepoistory).save(contrat);
    }

    @Test
    void testGetEmployePrenomById() {
        when(employeRepository.findById(1)).thenReturn(Optional.of(employe));

        String result = employeService.getEmployePrenomById(1);

        assertEquals(employe.getPrenom(), result);
    }

    @Test
    void testDeleteEmployeById() {
        employe.setDepartements(Arrays.asList(departement));
        when(employeRepository.findById(1)).thenReturn(Optional.of(employe));

        employeService.deleteEmployeById(1);

        assertFalse(departement.getEmployes().contains(employe));
        verify(employeRepository).delete(employe);
    }

    @Test
    void testDeleteContratById() {
        when(contratRepoistory.findById(100)).thenReturn(Optional.of(contrat));

        employeService.deleteContratById(100);

        verify(contratRepoistory).delete(contrat);
    }

    @Test
    void testGetNombreEmployeJPQL() {
        when(employeRepository.countemp()).thenReturn(5);

        int result = employeService.getNombreEmployeJPQL();

        assertEquals(5, result);
    }

    @Test
    void testGetAllEmployeNamesJPQL() {
        when(employeRepository.employeNames()).thenReturn(Arrays.asList("John Doe", "Jane Doe"));

        List<String> result = employeService.getAllEmployeNamesJPQL();

        assertEquals(2, result.size());
        assertTrue(result.contains("John Doe"));
        assertTrue(result.contains("Jane Doe"));
    }

    @Test
    void testGetAllEmployeByEntreprise() {
        Entreprise entreprise = new Entreprise();
        when(employeRepository.getAllEmployeByEntreprisec(entreprise)).thenReturn(Arrays.asList(employe));

        List<Employe> result = employeService.getAllEmployeByEntreprise(entreprise);

        assertEquals(1, result.size());
        assertEquals(employe, result.get(0));
    }

    @Test
    void testMettreAjourEmailByEmployeIdJPQL() {
        employeService.mettreAjourEmailByEmployeIdJPQL("new.email@example.com", 1);
        verify(employeRepository).mettreAjourEmailByEmployeIdJPQL("new.email@example.com", 1);
    }

    @Test
    void testDeleteAllContratJPQL() {
        employeService.deleteAllContratJPQL();
        verify(employeRepository).deleteAllContratJPQL();
    }

    @Test
    void testGetSalaireByEmployeIdJPQL() {
        when(employeRepository.getSalaireByEmployeIdJPQL(1)).thenReturn(3000f);

        float result = employeService.getSalaireByEmployeIdJPQL(1);

        assertEquals(3000f, result);
    }

    @Test
    void testGetSalaireMoyenByDepartementId() {
        when(employeRepository.getSalaireMoyenByDepartementId(1)).thenReturn(2500.0);

        Double result = employeService.getSalaireMoyenByDepartementId(1);

        assertEquals(2500.0, result);
    }

    @Test
    void testGetTimesheetsByMissionAndDate() {
        Mission mission = new Mission();
        List<Timesheet> timesheets = Arrays.asList(new Timesheet());
        when(timesheetRepository.getTimesheetsByMissionAndDate(employe, mission, null, null)).thenReturn(timesheets);

        List<Timesheet> result = employeService.getTimesheetsByMissionAndDate(employe, mission, null, null);

        assertEquals(timesheets, result);
    }

    @Test
    void testGetAllEmployes() {
        when(employeRepository.findAll()).thenReturn(Arrays.asList(employe));

        List<Employe> result = employeService.getAllEmployes();

        assertEquals(1, result.size());
        assertEquals(employe, result.get(0));
    }

    @Test
    void testMettreAjourEmailByEmployeId_EmployeNotFound() {
        when(employeRepository.findById(1)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            employeService.mettreAjourEmailByEmployeId("new.email@example.com", 1);
        });

        assertEquals("Employe not found with id: 1", exception.getMessage());
    }

    @Test
    void testAffecterEmployeADepartement_DepartementNotFound() {
        when(deptRepoistory.findById(1)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            employeService.affecterEmployeADepartement(1, 1);
        });

        assertEquals("Employe not found with id: 1", exception.getMessage());
    }

    @Test
    void testDesaffecterEmployeDuDepartement_EmployeNotInDepartement() {

        // Create an employee instance
        Employe employeTemp = new Employe();
        employeTemp.setId(2); // Set the ID to 2

        // Create a department instance and add the employee to it
        Departement departementTemp = new Departement();
        departementTemp.setId(1); // Set the department ID
        departementTemp.setEmployes(new ArrayList<>(List.of(employeTemp))); // Add the employee to the department
        when(deptRepoistory.findById(1)).thenReturn(Optional.of(departementTemp));

        // Ensure that the department does not contain the employee
        employeService.desaffecterEmployeDuDepartement(1, 1);

        assertFalse(departement.getEmployes().contains(employe));
    }

    @Test
    void testDeleteEmployeById_EmployeNotFound() {
        when(employeRepository.findById(1)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            employeService.deleteEmployeById(1);
        });

        assertEquals("Employe not found with id: 1", exception.getMessage());
    }

    @Test
    void testDeleteContratById_ContratNotFound() {
        when(contratRepoistory.findById(100)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            employeService.deleteContratById(100);
        });

        assertEquals("Contrat not found with id: 100", exception.getMessage());
    }

    @Test
    void testAffecterEmployeADepartement_EmployeNotFound() {
        when(deptRepoistory.findById(1)).thenReturn(Optional.of(departement));
        when(employeRepository.findById(1)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            employeService.affecterEmployeADepartement(1, 1);
        });

        assertEquals("Employe not found with id: 1", exception.getMessage());
    }


    @Test
    void testDesaffecterEmployeDuDepartement_DepartementNotFound() {
        when(deptRepoistory.findById(1)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            employeService.desaffecterEmployeDuDepartement(1, 1);
        });

        assertEquals("Departement not found with id: 1", exception.getMessage());
    }

    @Test
    void testGetEmployePrenomById_EmployeNotFound() {
        when(employeRepository.findById(1)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            employeService.getEmployePrenomById(1);
        });

        assertEquals("Employe not found with id: 1", exception.getMessage());
    }

    @Test
    void testDesaffecterEmployeDuDepartement_EmployeExists() {
        // Setup
        Employe employeTemp = new Employe();
        employeTemp.setId(1);

        Departement departementTemp = new Departement();
        departementTemp.setEmployes(new ArrayList<>(List.of(employeTemp))); // Add the employee to the department

        when(deptRepoistory.findById(1)).thenReturn(Optional.of(departementTemp));

        // Act
        employeService.desaffecterEmployeDuDepartement(1, 1);

        // Assert
        assertFalse(departement.getEmployes().contains(employe), "Employee should be removed from the department");
    }

    @Test
    void testDesaffecterEmployeDuDepartement_EmployeExistsAndRemoved() {
        // Create an employee instance
        Employe employeTemp = new Employe();
        employeTemp.setId(1); // Set the ID to 1

        // Create a department instance and add the employee to it
        Departement departementTemp = new Departement();
        departementTemp.setId(1); // Set the department ID
        departementTemp.setEmployes(new ArrayList<>(List.of(employeTemp))); // Add the employee to the department

        // Mock the repository behavior
        when(deptRepoistory.findById(1)).thenReturn(Optional.of(departementTemp)); // Department exists
//        when(employeRepository.findById(1)).thenReturn(Optional.of(employe)); // Employee exists

        // Act
        employeService.desaffecterEmployeDuDepartement(1, 1); // Attempt to remove the employee from the department

        // Assert
        assertFalse(departement.getEmployes().contains(employe), "Employee should be removed from the department");
    }


    @Test
    void testAffecterContratAEmploye_ContratNotFound() {
        when(contratRepoistory.findById(1)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            employeService.affecterContratAEmploye(1, 1);
        });

        assertEquals("Contrat not found with id: 1", exception.getMessage());
    }

    @Test
    void testAffecterContratAEmploye_EmployeNotFound() {
        Contrat contratTemp = new Contrat();

        when(contratRepoistory.findById(1)).thenReturn(Optional.of(contratTemp));
        when(employeRepository.findById(1)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            employeService.affecterContratAEmploye(1, 1);
        });

        assertEquals("Employe not found with id: 1", exception.getMessage());
    }

}
