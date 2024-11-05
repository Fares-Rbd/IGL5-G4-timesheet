package tn.esprit.spring.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class MissionTest {

    private Mission mission;

    @BeforeEach
    void setUp() {
        mission = new Mission("Development", "Software development project");
    }

    @Test
    void testDefaultConstructor() {
        Mission defaultMission = new Mission();
        assertNotNull(defaultMission, "Mission default constructor should create a non-null object.");
    }

    @Test
    void testParameterizedConstructor() {
        Mission paramMission = new Mission("Marketing", "Marketing campaign project");
        assertEquals("Marketing", paramMission.getName());
        assertEquals("Marketing campaign project", paramMission.getDescription());
    }

    @Test
    void testSetAndGetId() {
        mission.setId(1);
        assertEquals(1, mission.getId());
    }

    @Test
    void testSetAndGetName() {
        mission.setName("Updated Name");
        assertEquals("Updated Name", mission.getName());
    }

    @Test
    void testSetAndGetDescription() {
        mission.setDescription("Updated Description");
        assertEquals("Updated Description", mission.getDescription());
    }

    @Test
    void testSetAndGetDepartement() {
        Departement departement = new Departement("IT");
        mission.setDepartement(departement);
        assertEquals(departement, mission.getDepartement());
    }

    @Test
    void testSetAndGetTimesheets() {
        List<Timesheet> timesheets = new ArrayList<>();
        Timesheet timesheet1 = new Timesheet();
        Timesheet timesheet2 = new Timesheet();
        timesheets.add(timesheet1);
        timesheets.add(timesheet2);

        mission.setTimesheets(timesheets);
        assertEquals(timesheets, mission.getTimesheets());
        assertEquals(2, mission.getTimesheets().size());
    }
}
