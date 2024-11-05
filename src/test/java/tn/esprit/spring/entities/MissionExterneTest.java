package tn.esprit.spring.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MissionExterneTest {

    private MissionExterne missionExterne;

    @BeforeEach
    void setUp() {
        missionExterne = new MissionExterne("External Mission", "This is a test mission", "billing@example.com", 150.0f);
    }

    @Test
    void testDefaultConstructor() {
        MissionExterne defaultMissionExterne = new MissionExterne();
        assertNotNull(defaultMissionExterne, "MissionExterne default constructor should create a non-null object.");
    }

    @Test
    void testParameterizedConstructor() {
        MissionExterne paramMissionExterne = new MissionExterne("Finance Project", "Finance-related mission", "finance@example.com", 200.0f);
        assertEquals("Finance Project", paramMissionExterne.getName());
        assertEquals("Finance-related mission", paramMissionExterne.getDescription());
        assertEquals("finance@example.com", paramMissionExterne.getEmailFacturation());
        assertEquals(200.0f, paramMissionExterne.getTauxJournalierMoyen());
    }

    @Test
    void testSetAndGetEmailFacturation() {
        missionExterne.setEmailFacturation("new.email@example.com");
        assertEquals("new.email@example.com", missionExterne.getEmailFacturation());
    }

    @Test
    void testSetAndGetTauxJournalierMoyen() {
        missionExterne.setTauxJournalierMoyen(180.0f);
        assertEquals(180.0f, missionExterne.getTauxJournalierMoyen(), 0.01f);
    }

    @Test
    void testSetAndGetName() {
        missionExterne.setName("Updated Mission Name");
        assertEquals("Updated Mission Name", missionExterne.getName());
    }

    @Test
    void testSetAndGetDescription() {
        missionExterne.setDescription("Updated Description");
        assertEquals("Updated Description", missionExterne.getDescription());
    }
}
