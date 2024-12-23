package tn.esprit.spring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import tn.esprit.spring.dto.DepartementDTO;
import tn.esprit.spring.dto.EntrepriseDTO;
import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.entities.Entreprise;
import tn.esprit.spring.services.IEmployeService;
import tn.esprit.spring.services.IEntrepriseService;
import tn.esprit.spring.services.ITimesheetService;

@RestController
public class RestControlEntreprise {


	private final IEntrepriseService ientrepriseservice;

	@Autowired
	public RestControlEntreprise(IEmployeService iemployeservice, IEntrepriseService ientrepriseservice, ITimesheetService itimesheetservice) {
		this.ientrepriseservice = ientrepriseservice;
	}

	@PostMapping("/ajouterEntreprise")
	public int ajouterEntreprise(@RequestBody EntrepriseDTO entrepriseDTO) {
		Entreprise ssiiConsulting = new Entreprise();
		ssiiConsulting.setName(entrepriseDTO.getName());
		ssiiConsulting.setRaisonSocial(entrepriseDTO.getRaisonSocial());
		ientrepriseservice.ajouterEntreprise(ssiiConsulting);
		return ssiiConsulting.getId();
	}

	// http://localhost:8081/SpringMVC/servlet/affecterDepartementAEntreprise/1/1
	@PutMapping(value = "/affecterDepartementAEntreprise/{iddept}/{identreprise}")
	public void affecterDepartementAEntreprise(@PathVariable("iddept") int depId, @PathVariable("identreprise") int entrepriseId) {
		ientrepriseservice.affecterDepartementAEntreprise(depId, entrepriseId);
	}

	// http://localhost:8081/SpringMVC/servlet/deleteEntrepriseById/1
	@DeleteMapping("/deleteEntrepriseById/{identreprise}")
	public void deleteEntrepriseById(@PathVariable("identreprise") int entrepriseId) {
		ientrepriseservice.deleteEntrepriseById(entrepriseId);
	}

	// http://localhost:8081/SpringMVC/servlet/getEntrepriseById/1
	@GetMapping(value = "getEntrepriseById/{identreprise}")
	public Entreprise getEntrepriseById(@PathVariable("identreprise") int entrepriseId) {

		return ientrepriseservice.getEntrepriseById(entrepriseId);
	}

	@PostMapping("/ajouterDepartement")
	public int ajouterDepartement(@RequestBody DepartementDTO depDTO) {
		Departement dep = new Departement();
		dep.setName(depDTO.getName());
		return ientrepriseservice.ajouterDepartement(dep);
	}

	// http://localhost:8081/SpringMVC/servlet/getAllDepartementsNamesByEntreprise/1
	@GetMapping(value = "getAllDepartementsNamesByEntreprise/{identreprise}")
	public List<String> getAllDepartementsNamesByEntreprise(@PathVariable("identreprise") int entrepriseId) {
		return ientrepriseservice.getAllDepartementsNamesByEntreprise(entrepriseId);
	}

	// URL : http://localhost:8081/SpringMVC/servlet/deleteDepartementById/3
	@DeleteMapping("/deleteDepartementById/{iddept}")
	public void deleteDepartementById(@PathVariable("iddept") int depId) {
		ientrepriseservice.deleteDepartementById(depId);

	}
}