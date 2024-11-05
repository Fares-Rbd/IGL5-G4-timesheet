package tn.esprit.spring.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tn.esprit.spring.dto.MissionDTO;
import tn.esprit.spring.entities.Employe;
import tn.esprit.spring.entities.Mission;
import tn.esprit.spring.services.IEmployeService;
import tn.esprit.spring.services.IEntrepriseService;
import tn.esprit.spring.services.ITimesheetService;

@RestController
public class RestControlTimesheet {

	private final ITimesheetService itimesheetservice;

	@Autowired
	public RestControlTimesheet(IEmployeService iemployeservice, IEntrepriseService ientrepriseservice, ITimesheetService itimesheetservice) {
		this.itimesheetservice = itimesheetservice;
	}

	@PostMapping("/ajouterMission")
	public int ajouterMission(@RequestBody MissionDTO missionDTO) {
		Mission mission = new Mission();
		mission.setName(missionDTO.getName());
		mission.setDescription(missionDTO.getDescription());
		itimesheetservice.ajouterMission(mission);
		return mission.getId();
	}

	// http://localhost:8081/SpringMVC/servlet/affecterMissionADepartement/4/4
	@PutMapping(value = "/affecterMissionADepartement/{idmission}/{iddept}")
	public void affecterMissionADepartement(@PathVariable("idmission") int missionId, @PathVariable("iddept") int depId) {
		itimesheetservice.affecterMissionADepartement(missionId, depId);

	}

	// http://localhost:8081/SpringMVC/servlet/ajouterTimesheet
	@PostMapping("/ajouterTimesheet/idmission/idemp/dated/datef")
	public void ajouterTimesheet(@PathVariable("idmission") int missionId, @PathVariable("idemp") int employeId, @PathVariable("dated") Date dateDebut, @PathVariable("datef") Date dateFin) {
		itimesheetservice.ajouterTimesheet(missionId, employeId, dateDebut, dateFin);

	}

	@PutMapping(value = "/validerTimesheet/{idmission}/{iddept}")
	public void validerTimesheet(@PathVariable("idmission") int missionId, @PathVariable("iddept") int employeId, @RequestParam Date dateDebut, @RequestParam Date dateFin, @RequestParam int validateurId) {
		itimesheetservice.validerTimesheet(missionId, employeId, dateDebut, dateFin, validateurId);

	}

	@GetMapping(value = "findAllMissionByEmployeJPQL/{idemp}")
	public List<Mission> findAllMissionByEmployeJPQL(@PathVariable("idemp") int employeId) {

		return itimesheetservice.findAllMissionByEmployeJPQL(employeId);
	}

	// URL : http://localhost:8081/SpringMVC/servlet/getAllEmployeByMission/1
	@GetMapping(value = "getAllEmployeByMission/{idmission}")
	public List<Employe> getAllEmployeByMission(@PathVariable("idmission") int missionId) {

		return itimesheetservice.getAllEmployeByMission(missionId);
	}
}