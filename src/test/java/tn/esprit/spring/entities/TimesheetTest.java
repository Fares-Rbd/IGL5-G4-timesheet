
package tn.esprit.spring.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TimesheetTest {

    private Timesheet timesheet;
    private TimesheetPK timesheetPK;
    private Mission mission;
    private Employe employe;

    @BeforeEach
    void setUp() {
        timesheet = new Timesheet();
        timesheetPK = new TimesheetPK(); // Assuming TimesheetPK has a no-args constructor
        mission = new Mission(); // Assuming Mission has a no-args constructor
        employe = new Employe(); // Assuming Employe has a no-args constructor
    }

    @Test
    void testGetAndSetTimesheetPK() {
        timesheet.setTimesheetPK(timesheetPK);
        assertEquals(timesheetPK, timesheet.getTimesheetPK(), "TimesheetPK should be correctly set and retrieved");
    }

    @Test
    void testGetAndSetMission() {
        timesheet.setMission(mission);
        assertEquals(mission, timesheet.getMission(), "Mission should be correctly set and retrieved");
    }

    @Test
    void testGetAndSetEmploye() {
        timesheet.setEmploye(employe);
        assertEquals(employe, timesheet.getEmploye(), "Employe should be correctly set and retrieved");
    }

    @Test
    void testIsAndSetValide() {
        timesheet.setValide(true);
        assertTrue(timesheet.isValide(), "Timesheet should be valid after setting to true");

        timesheet.setValide(false);
        assertFalse(timesheet.isValide(), "Timesheet should not be valid after setting to false");
    }
}
