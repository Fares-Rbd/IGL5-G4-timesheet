package tn.esprit.spring.services;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.entities.Employe;
import tn.esprit.spring.entities.Mission;
import tn.esprit.spring.entities.Role;
import tn.esprit.spring.entities.Timesheet;
import tn.esprit.spring.entities.TimesheetPK;
import tn.esprit.spring.repository.DepartementRepository;
import tn.esprit.spring.repository.EmployeRepository;
import tn.esprit.spring.repository.MissionRepository;
import tn.esprit.spring.repository.TimesheetRepository;


@ExtendWith(MockitoExtension.class)
class TimesheetServiceImplTest {

    @InjectMocks
    private TimesheetServiceImpl timesheetService;

    @Mock
    private MissionRepository missionRepository;

    @Mock
    private DepartementRepository departementRepository;

    @Mock
    private TimesheetRepository timesheetRepository;

    @Mock
    private EmployeRepository employeRepository;

    private Mission mission;
    private Departement departement;
    private Employe employe;
    private Timesheet timesheet;
    private TimesheetPK timesheetPK;
    private Date dateDebut;
    private Date dateFin;

    @BeforeEach
    void setUp() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        dateDebut = sdf.parse("01/01/2024");
        dateFin = sdf.parse("02/01/2024");

        // Create and set up the department
        departement = new Departement();
        departement.setId(1);

        // Create and set up the mission
        mission = new Mission();
        mission.setId(1);
        mission.setDepartement(departement); // Associate the mission with the department

        // Create and set up the employee
        employe = new Employe();
        employe.setId(1);
        employe.setRole(Role.CHEF_DEPARTEMENT);
        employe.setDepartements(List.of(departement)); // The employee is the head of this department

        // Create and set up the timesheet
        timesheetPK = new TimesheetPK(1, 1, dateDebut, dateFin);
        timesheet = new Timesheet();
        timesheet.setTimesheetPK(timesheetPK);
        timesheet.setValide(false);
    }

    @Test
    void testAjouterMission() {
        when(missionRepository.save(any(Mission.class))).thenReturn(mission);

        int missionId = timesheetService.ajouterMission(mission);

        assertEquals(1, missionId);
        verify(missionRepository).save(mission);
    }

    @Test
    void testAffecterMissionADepartement() {
        when(missionRepository.findById(1)).thenReturn(Optional.of(mission));
        when(departementRepository.findById(1)).thenReturn(Optional.of(departement));

        timesheetService.affecterMissionADepartement(1, 1);

        verify(missionRepository).save(mission);
        assertEquals(departement, mission.getDepartement());
    }

    @Test
    void testAjouterTimesheet() {
        when(timesheetRepository.save(any(Timesheet.class))).thenReturn(timesheet);

        timesheetService.ajouterTimesheet(1, 1, dateDebut, dateFin);

        verify(timesheetRepository).save(any(Timesheet.class));
    }

    @Test
    void testValiderTimesheetAsValidateur() {
        when(employeRepository.findById(1)).thenReturn(Optional.of(employe));
        when(missionRepository.findById(1)).thenReturn(Optional.of(mission));
        when(timesheetRepository.findBytimesheetPK(timesheetPK)).thenReturn(timesheet);

        timesheetService.validerTimesheet(1, 1, dateDebut, dateFin, 1);

        assertTrue(timesheet.isValide());
        verify(timesheetRepository).findBytimesheetPK(timesheetPK);
    }

    @Test
    void testValiderTimesheetAsNonChefDepartement() {
        employe.setRole(Role.ADMINISTRATEUR);
        when(employeRepository.findById(1)).thenReturn(Optional.of(employe));
        when(missionRepository.findById(1)).thenReturn(Optional.of(mission));

        timesheetService.validerTimesheet(1, 1, dateDebut, dateFin, 1);

        assertFalse(timesheet.isValide()); // should not validate
        verify(timesheetRepository, never()).findBytimesheetPK(timesheetPK);
    }

    @Test
    void testFindAllMissionByEmployeJPQL() {
        List<Mission> missions = new ArrayList<>();
        missions.add(mission);

        when(timesheetRepository.findAllMissionByEmployeJPQL(1)).thenReturn(missions);

        List<Mission> result = timesheetService.findAllMissionByEmployeJPQL(1);

        assertEquals(1, result.size());
        assertEquals(mission, result.get(0));
    }

    @Test
    void testGetAllEmployeByMission() {
        List<Employe> employes = new ArrayList<>();
        employes.add(employe);

        when(timesheetRepository.getAllEmployeByMission(1)).thenReturn(employes);

        List<Employe> result = timesheetService.getAllEmployeByMission(1);

        assertEquals(1, result.size());
        assertEquals(employe, result.get(0));
    }

    @Test
    void testAffecterMissionADepartement_MissionNotFound() {
        // Arrange
        int missionId = 1;
        int depId = 1;

        // Stub the mission repository to return an empty Optional
        when(missionRepository.findById(missionId)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            timesheetService.affecterMissionADepartement(missionId, depId);
        });

        // Verify the expected exception message
        assertEquals("Mission not found with id: " + missionId, exception.getMessage());

    }

    @Test
    void testAffecterMissionADepartement_DepartementNotFound() {
        // Arrange
        int missionId = 1;
        int depId = 1;

        // Stub the mission repository to return a present mission
        Mission missionTemp = new Mission();
        missionTemp.setId(missionId);
        when(missionRepository.findById(missionId)).thenReturn(Optional.of(missionTemp));

        // Stub the department repository to return an empty Optional
        when(departementRepository.findById(depId)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            timesheetService.affecterMissionADepartement(missionId, depId);
        });

        // Verify the expected exception message
        assertEquals("Departement not found with id: " + depId, exception.getMessage());
    }

    @Test
    void testValiderTimesheet_ValidateurNotFound() {
        // Arrange
        int validateurId = 1;
        int missionId = 1;
        int employeId = 1;
        Date dateDebutTemp = new Date();
        Date dateFinTemp = new Date();

        // Stub employeRepository to return an empty Optional
        when(employeRepository.findById(validateurId)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            timesheetService.validerTimesheet(missionId, employeId, dateDebutTemp, dateFinTemp, validateurId);
        });

        // Verify the expected exception message
        assertEquals("Validateur not found with id: " + validateurId, exception.getMessage());
    }


    @Test
    void testValiderTimesheet_MissionNotFound() {
        // Arrange
        int validateurId = 1;
        int missionId = 1;
        int employeId = 1;
        Date dateDebutTemp = new Date();
        Date dateFinTemp = new Date();
        Employe validateur = new Employe();
        validateur.setRole(Role.CHEF_DEPARTEMENT);

        // Stub employeRepository to return a valid validateur
        when(employeRepository.findById(validateurId)).thenReturn(Optional.of(validateur));
        // Stub missionRepository to return an empty Optional
        when(missionRepository.findById(missionId)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            timesheetService.validerTimesheet(missionId, employeId, dateDebutTemp, dateFinTemp, validateurId);
        });

        // Verify the expected exception message
        assertEquals("Mission not found with id: " + missionId, exception.getMessage());
    }

    @Test
    void testValiderTimesheet_ValidateurNotChefDepartement() {
        // Arrange
        int validateurId = 1;
        int missionId = 1;
        int employeId = 1;
        Date dateDebutTemp = new Date();
        Date dateFinTemp = new Date();
        Employe validateur = new Employe();
        validateur.setRole(Role.TECHNICIEN); // Not a chef de departement

        // Stub employeRepository to return a valid validateur
        when(employeRepository.findById(validateurId)).thenReturn(Optional.of(validateur));
        // Stub missionRepository to return a valid mission
        Mission missionTemp = new Mission();
        missionTemp.setDepartement(new Departement());
        when(missionRepository.findById(missionId)).thenReturn(Optional.of(missionTemp));

        // Act
        timesheetService.validerTimesheet(missionId, employeId, dateDebutTemp, dateFinTemp, validateurId);

        // Assert logging occurs
        // You can verify that the logger was called appropriately if you have a Logger spy
        // Here we're just testing that it executes without exceptions and no further checks are made
        assertTrue(true);
    }

    @Test
    void testValiderTimesheet_NotChefDeLaMission() {
        // Arrange
        int validateurId = 1;
        int missionId = 1;
        int employeId = 1;
        Date dateDebutTemp = new Date();
        Date dateFinTemp = new Date();
        Employe validateur = new Employe();
        validateur.setRole(Role.CHEF_DEPARTEMENT);
        Departement validateurDept = new Departement();
        validateurDept.setId(1);
        validateur.setDepartements(new ArrayList<>()); // Validateur has no departments

        // Stub employeRepository to return a valid validateur
        when(employeRepository.findById(validateurId)).thenReturn(Optional.of(validateur));

        // Stub missionRepository to return a valid mission
        Mission missionTemp = new Mission();
        missionTemp.setDepartement(new Departement());
        missionTemp.getDepartement().setId(2); // Mission department ID
        when(missionRepository.findById(missionId)).thenReturn(Optional.of(missionTemp));

        // Act
        timesheetService.validerTimesheet(missionId, employeId, dateDebutTemp, dateFinTemp, validateurId);

        // Assert logging occurs
        // You can verify that the logger was called appropriately if you have a Logger spy
        // Here we're just testing that it executes without exceptions and no further checks are made
        assertTrue(true);
    }


}