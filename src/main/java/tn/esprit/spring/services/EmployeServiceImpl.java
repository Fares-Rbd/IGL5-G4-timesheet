package tn.esprit.spring.services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tn.esprit.spring.entities.Contrat;
import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.entities.Employe;
import tn.esprit.spring.entities.Entreprise;
import tn.esprit.spring.entities.Mission;
import tn.esprit.spring.entities.Timesheet;
import tn.esprit.spring.repository.ContratRepository;
import tn.esprit.spring.repository.DepartementRepository;
import tn.esprit.spring.repository.EmployeRepository;
import tn.esprit.spring.repository.TimesheetRepository;

@Service
public class EmployeServiceImpl implements IEmployeService {

	private final EmployeRepository employeRepository;
	private final DepartementRepository deptRepoistory;
	private final ContratRepository contratRepoistory;
	private final TimesheetRepository timesheetRepository;

	private static final String EMPLOYE_NOT_FOUND = "Employe not found with id: ";

	@Autowired
	public EmployeServiceImpl(EmployeRepository employeRepository, DepartementRepository deptRepoistory, ContratRepository contratRepoistory, TimesheetRepository timesheetRepository) {
		this.employeRepository = employeRepository;
		this.deptRepoistory = deptRepoistory;
		this.contratRepoistory = contratRepoistory;
		this.timesheetRepository = timesheetRepository;
	}

	@Override
	public Employe authenticate(String login, String password) {
		return employeRepository.getEmployeByEmailAndPassword(login, password);
	}

	@Override
	public int addOrUpdateEmploye(Employe employe) {
		employeRepository.save(employe);
		return employe.getId();
	}


	public void mettreAjourEmailByEmployeId(String email, int employeId) {
		employeRepository.findById(employeId).ifPresent(employe -> {
			employe.setEmail(email);
			employeRepository.save(employe);
		});
	}

	@Transactional    
	public void affecterEmployeADepartement(int employeId, int depId) {
		Departement depManagedEntity = deptRepoistory.findById(depId)
			.orElseThrow(() -> new RuntimeException(EMPLOYE_NOT_FOUND + employeId));
		Employe employeManagedEntity = employeRepository.findById(employeId)
			.orElseThrow(() -> new RuntimeException(EMPLOYE_NOT_FOUND + employeId));

		depManagedEntity.getEmployes().add(employeManagedEntity);
	}

	@Transactional
	public void desaffecterEmployeDuDepartement(int employeId, int depId) {
		Departement dep = deptRepoistory.findById(depId)
			.orElseThrow(() -> new RuntimeException("Departement not found with id: " + depId));
		
		dep.getEmployes().removeIf(employe -> employe.getId() == employeId);
	}

	public int ajouterContrat(Contrat contrat) {
		contratRepoistory.save(contrat);
		return contrat.getReference();
	}

	public void affecterContratAEmploye(int contratId, int employeId) {
		Contrat contratManagedEntity = contratRepoistory.findById(contratId)
			.orElseThrow(() -> new RuntimeException("Contrat not found with id: " + contratId));
		Employe employeManagedEntity = employeRepository.findById(employeId)
			.orElseThrow(() -> new RuntimeException(EMPLOYE_NOT_FOUND + employeId));

		contratManagedEntity.setEmploye(employeManagedEntity);
		contratRepoistory.save(contratManagedEntity);
	}

	public String getEmployePrenomById(int employeId) {
		return employeRepository.findById(employeId)
			.map(Employe::getPrenom)
			.orElseThrow(() -> new RuntimeException(EMPLOYE_NOT_FOUND + employeId));
	}

	public void deleteEmployeById(int employeId)
	{
		 Employe employe = employeRepository.findById(employeId)
		.orElseThrow(() -> new RuntimeException(EMPLOYE_NOT_FOUND + employeId));

    	//Desaffecter l'employe de tous les departements
		//c'est le bout master qui permet de mettre a jour
		//la table d'association
		for(Departement dep : employe.getDepartements()){
			dep.getEmployes().remove(employe);
		}

		employeRepository.delete(employe);
	}

	public void deleteContratById(int contratId) {
		Contrat contratManagedEntity = contratRepoistory.findById(contratId)
			.orElseThrow(() -> new RuntimeException("Contrat not found with id: " + contratId));
		contratRepoistory.delete(contratManagedEntity);
	}

	public int getNombreEmployeJPQL() {
		return employeRepository.countemp();
	}

	public List<String> getAllEmployeNamesJPQL() {
		return employeRepository.employeNames();

	}

	public List<Employe> getAllEmployeByEntreprise(Entreprise entreprise) {
		return employeRepository.getAllEmployeByEntreprisec(entreprise);
	}

	public void mettreAjourEmailByEmployeIdJPQL(String email, int employeId) {
		employeRepository.mettreAjourEmailByEmployeIdJPQL(email, employeId);

	}
	public void deleteAllContratJPQL() {
		employeRepository.deleteAllContratJPQL();
	}

	public float getSalaireByEmployeIdJPQL(int employeId) {
		return employeRepository.getSalaireByEmployeIdJPQL(employeId);
	}

	public Double getSalaireMoyenByDepartementId(int departementId) {
		return employeRepository.getSalaireMoyenByDepartementId(departementId);
	}

	public List<Timesheet> getTimesheetsByMissionAndDate(Employe employe, Mission mission, Date dateDebut,
			Date dateFin) {
		return timesheetRepository.getTimesheetsByMissionAndDate(employe, mission, dateDebut, dateFin);
	}

	public List<Employe> getAllEmployes() {
		return (List<Employe>) employeRepository.findAll();
	}

}
