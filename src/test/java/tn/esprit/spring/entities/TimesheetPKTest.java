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
    void testEquals_SameReference() {
        assertEquals(timesheetPK, timesheetPK, "Same object reference should be equal");
    }

    @Test
    void testEquals_Null() {
        assertNotEquals(null, timesheetPK, "Object should not be equal to null");
    }

    @Test
    void testEquals_DifferentClass() {
        Object differentClass = new Object();
        assertNotEquals(timesheetPK, differentClass, "Objects of different classes should not be equal");
    }

    @Test
    void testEquals_SameAttributes() {
        assertEquals(timesheetPK, anotherTimesheetPK, "Two TimesheetPK objects with the same attributes should be equal");
    }

    @Test
    void testEquals_DifferentAttributes() {
        // Test with different idMission
        anotherTimesheetPK.setIdMission(99);
        assertNotEquals(timesheetPK, anotherTimesheetPK, "Two TimesheetPK objects with different idMission should not be equal");
        anotherTimesheetPK.setIdMission(1); // Reset for further tests

        // Test with different idEmploye
        anotherTimesheetPK.setIdEmploye(99);
        assertNotEquals(timesheetPK, anotherTimesheetPK, "Two TimesheetPK objects with different idEmploye should not be equal");
        anotherTimesheetPK.setIdEmploye(2); // Reset for further tests

        // Test with different dateDebut
        Date newDateDebut = new Date(System.currentTimeMillis() + 100000);
        anotherTimesheetPK.setDateDebut(newDateDebut);
        assertNotEquals(timesheetPK, anotherTimesheetPK, "Two TimesheetPK objects with different dateDebut should not be equal");
        anotherTimesheetPK.setDateDebut(dateDebut); // Reset for further tests

        // Test with different dateFin
        Date newDateFin = new Date(System.currentTimeMillis() + 100000);
        anotherTimesheetPK.setDateFin(newDateFin);
        assertNotEquals(timesheetPK, anotherTimesheetPK, "Two TimesheetPK objects with different dateFin should not be equal");
    }

    @Test
    void testEquals_NullDates() {
        // Test dateDebut null combinations
        TimesheetPK pk1 = new TimesheetPK(1, 2, null, dateFin);
        TimesheetPK pk2 = new TimesheetPK(1, 2, null, dateFin);
        assertEquals(pk1, pk2, "Objects with null dateDebut should be equal");

        pk2.setDateDebut(new Date());
        assertNotEquals(pk1, pk2, "One null dateDebut, one non-null should not be equal");

        // Test dateFin null combinations
        pk1 = new TimesheetPK(1, 2, dateDebut, null);
        pk2 = new TimesheetPK(1, 2, dateDebut, null);
        assertEquals(pk1, pk2, "Objects with null dateFin should be equal");

        pk2.setDateFin(new Date());
        assertNotEquals(pk1, pk2, "One null dateFin, one non-null should not be equal");

        // Test both dates null
        pk1 = new TimesheetPK(1, 2, null, null);
        pk2 = new TimesheetPK(1, 2, null, null);
        assertEquals(pk1, pk2, "Objects with both null dates should be equal");
    }

    @Test
    void testHashCode_SameAttributes() {
        assertEquals(timesheetPK.hashCode(), anotherTimesheetPK.hashCode(),
                "Hash codes should be equal for objects with the same attributes");
    }

    @Test
    void testHashCode_DifferentAttributes() {
        // Test with different idMission
        anotherTimesheetPK.setIdMission(99);
        assertNotEquals(timesheetPK.hashCode(), anotherTimesheetPK.hashCode(),
                "Hash codes should be different for objects with different idMission");

        // Test with different idEmploye
        anotherTimesheetPK = new TimesheetPK(1, 99, dateDebut, dateFin);
        assertNotEquals(timesheetPK.hashCode(), anotherTimesheetPK.hashCode(),
                "Hash codes should be different for objects with different idEmploye");

        // Test with different dateDebut
        anotherTimesheetPK = new TimesheetPK(1, 2, new Date(System.currentTimeMillis() + 100000), dateFin);
        assertNotEquals(timesheetPK.hashCode(), anotherTimesheetPK.hashCode(),
                "Hash codes should be different for objects with different dateDebut");

        // Test with different dateFin
        anotherTimesheetPK = new TimesheetPK(1, 2, dateDebut, new Date(System.currentTimeMillis() + 100000));
        assertNotEquals(timesheetPK.hashCode(), anotherTimesheetPK.hashCode(),
                "Hash codes should be different for objects with different dateFin");
    }

    @Test
    void testHashCode_NullDates() {
        // Test with both dates null
        TimesheetPK pk1 = new TimesheetPK(1, 2, null, null);
        TimesheetPK pk2 = new TimesheetPK(1, 2, null, null);
        assertEquals(pk1.hashCode(), pk2.hashCode(),
                "Hash codes should be equal for objects with null dates");

        // Test with only dateDebut null
        pk1 = new TimesheetPK(1, 2, null, dateFin);
        pk2 = new TimesheetPK(1, 2, null, dateFin);
        assertEquals(pk1.hashCode(), pk2.hashCode(),
                "Hash codes should be equal for objects with null dateDebut");

        // Test with only dateFin null
        pk1 = new TimesheetPK(1, 2, dateDebut, null);
        pk2 = new TimesheetPK(1, 2, dateDebut, null);
        assertEquals(pk1.hashCode(), pk2.hashCode(),
                "Hash codes should be equal for objects with null dateFin");
    }
}