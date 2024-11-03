package tn.esprit.spring.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

class TimesheetPKTest {

    private TimesheetPK timesheetPK;
    private TimesheetPK anotherTimesheetPK;
    private Date dateDebut;
    private Date dateFin;

    @BeforeEach
    void setUp() {
        dateDebut = new Date();
        dateFin = new Date();
        timesheetPK = new TimesheetPK(1, 2, dateDebut, dateFin);
        anotherTimesheetPK = new TimesheetPK(1, 2, dateDebut, dateFin);
    }

    @Test
    void testGetAndSetIdMission() {
        timesheetPK.setIdMission(3);
        assertEquals(3, timesheetPK.getIdMission(), "idMission should be correctly set and retrieved");
    }

    @Test
    void testGetAndSetIdEmploye() {
        timesheetPK.setIdEmploye(4);
        assertEquals(4, timesheetPK.getIdEmploye(), "idEmploye should be correctly set and retrieved");
    }

    @Test
    void testGetAndSetDateDebut() {
        Date newDateDebut = new Date();
        timesheetPK.setDateDebut(newDateDebut);
        assertEquals(newDateDebut, timesheetPK.getDateDebut(), "dateDebut should be correctly set and retrieved");
    }

    @Test
    void testGetAndSetDateFin() {
        Date newDateFin = new Date();
        timesheetPK.setDateFin(newDateFin);
        assertEquals(newDateFin, timesheetPK.getDateFin(), "dateFin should be correctly set and retrieved");
    }

    @Test
    void testEquals_SameAttributes() {
        assertEquals(timesheetPK, anotherTimesheetPK, "Two TimesheetPK objects with the same attributes should be equal");
    }

    @Test
    void testEquals_DifferentAttributes() {
        anotherTimesheetPK.setIdMission(99);
        assertNotEquals(timesheetPK, anotherTimesheetPK, "Two TimesheetPK objects with different attributes should not be equal");
    }

    @Test
    void testHashCode_SameAttributes() {
        assertEquals(timesheetPK.hashCode(), anotherTimesheetPK.hashCode(), "Hash codes should be equal for objects with the same attributes");
    }

    @Test
    void testHashCode_DifferentAttributes() {
        anotherTimesheetPK.setIdEmploye(99);
        assertNotEquals(timesheetPK.hashCode(), anotherTimesheetPK.hashCode(), "Hash codes should be different for objects with different attributes");
    }
}
