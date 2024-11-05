package tn.esprit.spring.services;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.entities.Employe;
import tn.esprit.spring.entities.Mission;
import tn.esprit.spring.entities.Role;
import tn.esprit.spring.entities.Timesheet;
import tn.esprit.spring.entities.TimesheetPK;
import tn.esprit.spring.repository.DepartementRepository;
import tn.esprit.spring.repository.EmployeRepository;
import tn.esprit.spring.repository.MissionRepository;
import tn.esprit.spring.repository.TimesheetRepository;

@Service
public class TimesheetServiceImpl implements ITimesheetService {
    static final Logger logger = LoggerFactory.getLogger(TimesheetServiceImpl.class);

    private final MissionRepository missionRepository;
    private final DepartementRepository deptRepoistory;
    private final TimesheetRepository timesheetRepository;
    private final EmployeRepository employeRepository;

    @Autowired
    public TimesheetServiceImpl(MissionRepository missionRepository, DepartementRepository deptRepoistory, TimesheetRepository timesheetRepository, EmployeRepository employeRepository) {
        this.missionRepository = missionRepository;
        this.deptRepoistory = deptRepoistory;
        this.timesheetRepository = timesheetRepository;
        this.employeRepository = employeRepository;
    }

    public int ajouterMission(Mission mission) {
        missionRepository.save(mission);
        return mission.getId();
    }

    public void affecterMissionADepartement(int missionId, int depId) {
        Mission mission = missionRepository.findById(missionId)
                .orElseThrow(() -> new RuntimeException("Mission not found with id: " + missionId));
        Departement dep = deptRepoistory.findById(depId)
                .orElseThrow(() -> new RuntimeException("Departement not found with id: " + depId));
        mission.setDepartement(dep);
        missionRepository.save(mission);

    }

    public void ajouterTimesheet(int missionId, int employeId, Date dateDebut, Date dateFin) {
        TimesheetPK timesheetPK = new TimesheetPK();
        timesheetPK.setDateDebut(dateDebut);
        timesheetPK.setDateFin(dateFin);
        timesheetPK.setIdEmploye(employeId);
        timesheetPK.setIdMission(missionId);

        Timesheet timesheet = new Timesheet();
        timesheet.setTimesheetPK(timesheetPK);
        timesheet.setValide(false); //par defaut non valide
        timesheetRepository.save(timesheet);

    }

    public void validerTimesheet(int missionId, int employeId, Date dateDebut, Date dateFin, int validateurId) {
        logger.info("In valider Timesheet");
        Employe validateur = employeRepository.findById(validateurId)
                .orElseThrow(() -> new RuntimeException("Validateur not found with id: " + validateurId));
        Mission mission = missionRepository.findById(missionId)
                .orElseThrow(() -> new RuntimeException("Mission not found with id: " + missionId));

        //verifier s'il est un chef de departement (interet des enum)
        if (!validateur.getRole().equals(Role.CHEF_DEPARTEMENT)) {
            logger.info("l'employe doit etre chef de departement pour valider une feuille de temps !");
            return;
        }

        //verifier s'il est le chef de departement de la mission en question
        boolean chefDeLaMission = false;
        for (Departement dep : validateur.getDepartements()) {
            if (dep.getId() == mission.getDepartement().getId()) {
                chefDeLaMission = true;
                break;
            }
        }
        if (!chefDeLaMission) {
            logger.info("l'employe doit etre chef de departement de la mission en question");
            return;
        }
//
        TimesheetPK timesheetPK = new TimesheetPK(missionId, employeId, dateDebut, dateFin);
        Timesheet timesheet = timesheetRepository.findBytimesheetPK(timesheetPK);
        timesheet.setValide(true);

        //Comment Lire une date de la base de données
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        if (logger.isInfoEnabled()) {
            logger.info("dateDebut : {}", dateFormat.format(timesheet.getTimesheetPK().getDateDebut()));
        }

    }


    public List<Mission> findAllMissionByEmployeJPQL(int employeId) {
        return timesheetRepository.findAllMissionByEmployeJPQL(employeId);
    }


    public List<Employe> getAllEmployeByMission(int missionId) {
        return timesheetRepository.getAllEmployeByMission(missionId);
    }

}
