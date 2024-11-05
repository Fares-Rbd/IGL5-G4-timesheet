package tn.esprit.spring.dto;

import org.junit.jupiter.api.Test;
import tn.esprit.spring.dto.MissionDTO;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class MissionDTOTest {
    @Test
    void testDefaultConstructor() {
        MissionDTO mission = new MissionDTO();
        assertNull(mission.getName());
        assertNull(mission.getDescription());
        assertEquals(0, mission.getId());
    }

    @Test
    void testParameterizedConstructorWithNameAndDescription() {
        MissionDTO mission = new MissionDTO("Mission Alpha", "Description of Mission Alpha");
        assertEquals("Mission Alpha", mission.getName());
        assertEquals("Description of Mission Alpha", mission.getDescription());
    }

    @Test
    void testParameterizedConstructorWithName() {
        MissionDTO mission = new MissionDTO("Mission Beta");
        assertEquals("Mission Beta", mission.getName());
    }

    @Test
    void testParameterizedConstructorWithId() {
        MissionDTO mission = new MissionDTO(101);
        assertEquals(101, mission.getId());
    }

    @Test
    void testParameterizedConstructorWithNameAndId() {
        MissionDTO mission = new MissionDTO("Mission Gamma", 202);
        assertEquals("Mission Gamma", mission.getName());
        assertEquals(202, mission.getId());
    }

    @Test
    void testParameterizedConstructorWithNameDescriptionAndId() {
        MissionDTO mission = new MissionDTO("Mission Delta", "Description of Mission Delta", 303);
        assertEquals("Mission Delta", mission.getName());
        assertEquals("Description of Mission Delta", mission.getDescription());
        assertEquals(303, mission.getId());
    }

    @Test
    void testSetName() {
        MissionDTO mission = new MissionDTO();
        mission.setName("Mission Epsilon");
        assertEquals("Mission Epsilon", mission.getName());
    }

    @Test
    void testSetDescription() {
        MissionDTO mission = new MissionDTO();
        mission.setDescription("Description of Mission Epsilon");
        assertEquals("Description of Mission Epsilon", mission.getDescription());
    }

    @Test
    void testSetId() {
        MissionDTO mission = new MissionDTO();
        mission.setId(404);
        assertEquals(404, mission.getId());
    }


}
