package tn.esprit.spring.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.entities.Entreprise;
import tn.esprit.spring.repository.DepartementRepository;
import tn.esprit.spring.repository.EntrepriseRepository;

@ExtendWith(MockitoExtension.class)
 class EntrepriseServiceImplTest {

    @InjectMocks
    private EntrepriseServiceImpl entrepriseService;

    @Mock
    private EntrepriseRepository entrepriseRepoistory;

    @Mock
    private DepartementRepository deptRepoistory;

    private Entreprise entreprise;
    private Departement departement;

    @BeforeEach
     void setUp() {
        entreprise = new Entreprise();
        entreprise.setId(1);
        entreprise.setDepartements(new ArrayList<>());

        departement = new Departement();
        departement.setId(1);
        departement.setName("Development");
        departement.setEntreprise(entreprise);
    }

    @Test
     void testAjouterEntreprise() {
        when(entrepriseRepoistory.save(any(Entreprise.class))).thenReturn(entreprise);

        int id = entrepriseService.ajouterEntreprise(entreprise);

        assertEquals(1, id);
        verify(entrepriseRepoistory, times(1)).save(entreprise);
    }

    @Test
     void testAjouterDepartement() {
        when(deptRepoistory.save(any(Departement.class))).thenReturn(departement);

        int id = entrepriseService.ajouterDepartement(departement);

        assertEquals(1, id);
        verify(deptRepoistory, times(1)).save(departement);
    }

    @Test
     void testAffecterDepartementAEntreprise() {
        when(entrepriseRepoistory.findById(1)).thenReturn(Optional.of(entreprise));
        when(deptRepoistory.findById(1)).thenReturn(Optional.of(departement));

        entrepriseService.affecterDepartementAEntreprise(1, 1);

        assertEquals(entreprise, departement.getEntreprise());
        verify(deptRepoistory, times(1)).save(departement);
    }

    @Test
     void testGetAllDepartementsNamesByEntreprise() {
        List<Departement> departements = new ArrayList<>();
        departements.add(departement);
        entreprise.setDepartements(departements);
        when(entrepriseRepoistory.findById(1)).thenReturn(Optional.of(entreprise));

        List<String> names = entrepriseService.getAllDepartementsNamesByEntreprise(1);

        assertEquals(1, names.size());
        assertEquals("Development", names.get(0));
    }

    @Test
     void testDeleteEntrepriseById() {
        when(entrepriseRepoistory.findById(1)).thenReturn(Optional.of(entreprise));
        entrepriseService.deleteEntrepriseById(1);
        verify(entrepriseRepoistory, times(1)).delete(entreprise);
    }

    @Test
     void testDeleteDepartementById() {
        when(deptRepoistory.findById(1)).thenReturn(Optional.of(departement));
        entrepriseService.deleteDepartementById(1);
        verify(deptRepoistory, times(1)).delete(departement);
    }

    @Test
     void testGetEntrepriseById() {
        when(entrepriseRepoistory.findById(1)).thenReturn(Optional.of(entreprise));
        Entreprise foundEntreprise = entrepriseService.getEntrepriseById(1);
        assertEquals(entreprise, foundEntreprise);
    }
}
