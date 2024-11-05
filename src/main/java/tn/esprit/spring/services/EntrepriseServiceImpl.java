package tn.esprit.spring.services;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.entities.Entreprise;
import tn.esprit.spring.repository.DepartementRepository;
import tn.esprit.spring.repository.EntrepriseRepository;

@Service
public class EntrepriseServiceImpl implements IEntrepriseService {

	private static final Logger l = LogManager.getLogger(EntrepriseServiceImpl.class);

	private final EntrepriseRepository entrepriseRepoistory;
	private final DepartementRepository deptRepoistory;

	private static final String ENTREPRISE_NOT_FOUND = "Entreprise not found with id: ";
	private static final String DEPARTEMENT_NOT_FOUND = "Departement not found with id: ";

	@Autowired
	public EntrepriseServiceImpl(EntrepriseRepository entrepriseRepoistory, DepartementRepository deptRepoistory) {
		this.entrepriseRepoistory = entrepriseRepoistory;
		this.deptRepoistory = deptRepoistory;
	}
	
	public int ajouterEntreprise(Entreprise entreprise) {
		entrepriseRepoistory.save(entreprise);
		return entreprise.getId();
	}

	public int ajouterDepartement(Departement dep) {
		deptRepoistory.save(dep);
		return dep.getId();
	}
	
	public void affecterDepartementAEntreprise(int depId, int entrepriseId) {
		//Le bout Master de cette relation N:1 est departement  
				//donc il faut rajouter l'entreprise a departement 
				// ==> c'est l'objet departement(le master) qui va mettre a jour l'association
				//Rappel : la classe qui contient mappedBy represente le bout Slave
				//Rappel : Dans une relation oneToMany le mappedBy doit etre du cote one.
		Entreprise entrepriseManagedEntity = entrepriseRepoistory.findById(entrepriseId)
            .orElseThrow(() -> new RuntimeException(ENTREPRISE_NOT_FOUND + entrepriseId));
        Departement depManagedEntity = deptRepoistory.findById(depId)
            .orElseThrow(() -> new RuntimeException(DEPARTEMENT_NOT_FOUND + depId));

		depManagedEntity.setEntreprise(entrepriseManagedEntity);
		deptRepoistory.save(depManagedEntity);
	}
	
	public List<String> getAllDepartementsNamesByEntreprise(int entrepriseId) {
		Entreprise entrepriseManagedEntity = entrepriseRepoistory.findById(entrepriseId)
					.orElseThrow(() -> new RuntimeException(ENTREPRISE_NOT_FOUND + entrepriseId));
		List<String> depNames = new ArrayList<>();
		for(Departement dep : entrepriseManagedEntity.getDepartements()){
			depNames.add(dep.getName());
		}
		
		return depNames;
	}

	@Transactional
	public void deleteEntrepriseById(int entrepriseId) {
   		Entreprise entreprise = entrepriseRepoistory.findById(entrepriseId)
            .orElseThrow(() -> new RuntimeException(ENTREPRISE_NOT_FOUND + entrepriseId));
        entrepriseRepoistory.delete(entreprise);
	}

	@Transactional
	public void deleteDepartementById(int depId) {
 		Departement departement = deptRepoistory.findById(depId)
            .orElseThrow(() -> new RuntimeException(DEPARTEMENT_NOT_FOUND + depId));
        deptRepoistory.delete(departement);
	}


	public Entreprise getEntrepriseById(int entrepriseId) {
		l.info("méthode: getEntrepriseById");
		return entrepriseRepoistory.findById(entrepriseId)
				.orElseThrow(() -> new RuntimeException(ENTREPRISE_NOT_FOUND + entrepriseId));
	}

}
