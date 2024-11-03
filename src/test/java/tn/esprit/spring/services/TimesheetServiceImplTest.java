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
public class TimesheetServiceImplTest {

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
    public void setUp() throws ParseException {
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
    public void testAjouterMission() {
        when(missionRepository.save(any(Mission.class))).thenReturn(mission);

        int missionId = timesheetService.ajouterMission(mission);

        assertEquals(1, missionId);
        verify(missionRepository).save(mission);
    }

    @Test
    public void testAffecterMissionADepartement() {
        when(missionRepository.findById(1)).thenReturn(Optional.of(mission));
        when(departementRepository.findById(1)).thenReturn(Optional.of(departement));

        timesheetService.affecterMissionADepartement(1, 1);

        verify(missionRepository).save(mission);
        assertEquals(departement, mission.getDepartement());
    }

    @Test
    public void testAjouterTimesheet() {
        when(timesheetRepository.save(any(Timesheet.class))).thenReturn(timesheet);

        timesheetService.ajouterTimesheet(1, 1, dateDebut, dateFin);

        verify(timesheetRepository).save(any(Timesheet.class));
    }

    @Test
    public void testValiderTimesheetAsValidateur() {
        when(employeRepository.findById(1)).thenReturn(Optional.of(employe));
        when(missionRepository.findById(1)).thenReturn(Optional.of(mission));
        when(timesheetRepository.findBytimesheetPK(timesheetPK)).thenReturn(timesheet);

        timesheetService.validerTimesheet(1, 1, dateDebut, dateFin, 1);

        assertTrue(timesheet.isValide());
        verify(timesheetRepository).findBytimesheetPK(timesheetPK);
    }

    @Test
    public void testValiderTimesheetAsNonChefDepartement() {
        employe.setRole(Role.ADMINISTRATEUR);
        when(employeRepository.findById(1)).thenReturn(Optional.of(employe));
        when(missionRepository.findById(1)).thenReturn(Optional.of(mission));

        timesheetService.validerTimesheet(1, 1, dateDebut, dateFin, 1);

        assertFalse(timesheet.isValide()); // should not validate
        verify(timesheetRepository, never()).findBytimesheetPK(timesheetPK);
    }

    @Test
    public void testFindAllMissionByEmployeJPQL() {
        List<Mission> missions = new ArrayList<>();
        missions.add(mission);

        when(timesheetRepository.findAllMissionByEmployeJPQL(1)).thenReturn(missions);

        List<Mission> result = timesheetService.findAllMissionByEmployeJPQL(1);

        assertEquals(1, result.size());
        assertEquals(mission, result.get(0));
    }

    @Test
    public void testGetAllEmployeByMission() {
        List<Employe> employes = new ArrayList<>();
        employes.add(employe);

        when(timesheetRepository.getAllEmployeByMission(1)).thenReturn(employes);

        List<Employe> result = timesheetService.getAllEmployeByMission(1);

        assertEquals(1, result.size());
        assertEquals(employe, result.get(0));
    }
}