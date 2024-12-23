package tn.esprit.spring.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import tn.esprit.spring.dto.ContratDTO;
import tn.esprit.spring.dto.EmployeDTO;
import tn.esprit.spring.entities.Contrat;
import tn.esprit.spring.entities.Employe;
import tn.esprit.spring.entities.Entreprise;
import tn.esprit.spring.entities.Mission;
import tn.esprit.spring.entities.Timesheet;
import tn.esprit.spring.services.IEmployeService;
import tn.esprit.spring.services.IEntrepriseService;
import tn.esprit.spring.services.ITimesheetService;

@RestController
public class RestControlEmploye {


    private final IEmployeService iemployeservice;
    private final IEntrepriseService ientrepriseservice;

    @Autowired
    public RestControlEmploye(IEmployeService iemployeservice, IEntrepriseService ientrepriseservice, ITimesheetService itimesheetservice) {
        this.iemployeservice = iemployeservice;
        this.ientrepriseservice = ientrepriseservice;
    }

    @PostMapping("/ajouterEmployer")
    public EmployeDTO ajouterEmploye(@RequestBody EmployeDTO employeDTO) {
        Employe employe = new Employe();
        employe.setNom(employeDTO.getName());
        employe.setEmail(employeDTO.getEmail());
        iemployeservice.addOrUpdateEmploye(employe);
        return employeDTO;
    }

    // Modifier email : http://localhost:8081/SpringMVC/servlet/modifyEmail/1/newemail
    @PutMapping(value = "/modifyEmail/{id}/{newemail}")
    public void mettreAjourEmailByEmployeId(@PathVariable("newemail") String email, @PathVariable("id") int employeId) {
        iemployeservice.mettreAjourEmailByEmployeId(email, employeId);

    }

    // http://localhost:8081/SpringMVC/servlet/affecterEmployeADepartement/1/1
    @PutMapping(value = "/affecterEmployeADepartement/{idemp}/{iddept}")
    public void affecterEmployeADepartement(@PathVariable("idemp") int employeId, @PathVariable("iddept") int depId) {
        iemployeservice.affecterEmployeADepartement(employeId, depId);

    }

    // http://localhost:8081/SpringMVC/servlet/desaffecterEmployeDuDepartement/1/1
    @PutMapping(value = "/desaffecterEmployeDuDepartement/{idemp}/{iddept}")
    public void desaffecterEmployeDuDepartement(@PathVariable("idemp") int employeId, @PathVariable("iddept") int depId) {
        iemployeservice.desaffecterEmployeDuDepartement(employeId, depId);
    }

    @PostMapping("/ajouterContrat")
    public int ajouterContrat(@RequestBody ContratDTO contratDTO) {
        Contrat contrat = new Contrat();
        contrat.setDateDebut(contratDTO.getDateDebut());
        contrat.setTypeContrat(contratDTO.getTypeContrat());
        contrat.setSalaire(contratDTO.getSalaire());

        iemployeservice.ajouterContrat(contrat);
        return contrat.getReference();
    }



    // http://localhost:8081/SpringMVC/servlet/affecterContratAEmploye/6/1
    @PutMapping(value = "/affecterContratAEmploye/{idcontrat}/{idemp}")
    public void affecterContratAEmploye(@PathVariable("idcontrat") int contratId, @PathVariable("idemp") int employeId) {
        iemployeservice.affecterContratAEmploye(contratId, employeId);
    }


    // URL : http://localhost:8081/SpringMVC/servlet/getEmployePrenomById/2
    @GetMapping(value = "getEmployePrenomById/{idemp}")
    public String getEmployePrenomById(@PathVariable("idemp") int employeId) {
        return iemployeservice.getEmployePrenomById(employeId);
    }

    // URL : http://localhost:8081/SpringMVC/servlet/deleteEmployeById/1
    @DeleteMapping("/deleteEmployeById/{idemp}")
    public void deleteEmployeById(@PathVariable("idemp") int employeId) {
        iemployeservice.deleteEmployeById(employeId);

    }

    // URL : http://localhost:8081/SpringMVC/servlet/deleteContratById/2
    @DeleteMapping("/deleteContratById/{idcontrat}")
    public void deleteContratById(@PathVariable("idcontrat") int contratId) {
        iemployeservice.deleteContratById(contratId);
    }


    // URL : http://localhost:8081/SpringMVC/servlet/getNombreEmployeJPQL
    @GetMapping(value = "getNombreEmployeJPQL")
    public int getNombreEmployeJPQL() {

        return iemployeservice.getNombreEmployeJPQL();
    }

    // URL : http://localhost:8081/SpringMVC/servlet/getAllEmployeNamesJPQL
    @GetMapping(value = "getAllEmployeNamesJPQL")
    public List<String> getAllEmployeNamesJPQL() {

        return iemployeservice.getAllEmployeNamesJPQL();
    }

    // URL : http://localhost:8081/SpringMVC/servlet/getAllEmployeByEntreprise/1
    @GetMapping(value = "getAllEmployeByEntreprise/{identreprise}")
    public List<Employe> getAllEmployeByEntreprise(@PathVariable("identreprise") int identreprise) {
        Entreprise entreprise = ientrepriseservice.getEntrepriseById(identreprise);
        return iemployeservice.getAllEmployeByEntreprise(entreprise);
    }

    // Modifier email : http://localhost:8081/SpringMVC/servlet/mettreAjourEmailByEmployeIdJPQL/2/newemail
    @PutMapping(value = "/mettreAjourEmailByEmployeIdJPQL/{id}/{newemail}")
    public void mettreAjourEmailByEmployeIdJPQL(@PathVariable("newemail") String email, @PathVariable("id") int employeId) {
        iemployeservice.mettreAjourEmailByEmployeIdJPQL(email, employeId);

    }

    // URL : http://localhost:8081/SpringMVC/servlet/deleteAllContratJPQL
    @DeleteMapping("/deleteAllContratJPQL")
    public void deleteAllContratJPQL() {
        iemployeservice.deleteAllContratJPQL();

    }

    // URL : http://localhost:8081/SpringMVC/servlet/getSalaireByEmployeIdJPQL/2
    @GetMapping(value = "getSalaireByEmployeIdJPQL/{idemp}")
    public float getSalaireByEmployeIdJPQL(@PathVariable("idemp") int employeId) {
        return iemployeservice.getSalaireByEmployeIdJPQL(employeId);
    }

    // URL : http://localhost:8081/SpringMVC/servlet/getSalaireMoyenByDepartementId/2
    @GetMapping(value = "getSalaireMoyenByDepartementId/{iddept}")
    public Double getSalaireMoyenByDepartementId(@PathVariable("iddept") int departementId) {
        return iemployeservice.getSalaireMoyenByDepartementId(departementId);
    }


    public List<Timesheet> getTimesheetsByMissionAndDate(Employe employe, Mission mission, Date dateDebut,
                                                         Date dateFin) {
        return iemployeservice.getTimesheetsByMissionAndDate(employe, mission, dateDebut, dateFin);
    }


    // URL : http://localhost:8081/SpringMVC/servlet/getAllEmployes
    @GetMapping(value = "/getAllEmployes")
    public List<Employe> getAllEmployes() {

        return iemployeservice.getAllEmployes();
    }


}
