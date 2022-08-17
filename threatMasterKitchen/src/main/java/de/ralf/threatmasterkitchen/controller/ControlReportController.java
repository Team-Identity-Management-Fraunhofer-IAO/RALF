package de.ralf.threatmasterkitchen.controller;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.sql.data.objects.persistence.threatmaster.assessment.ControlReport;
import com.sql.data.objects.persistence.threatmaster.assessment.ControlReportControl;
import com.sql.data.objects.persistence.threatmaster.assessment.ControlReportControlExcludedThreats;
import com.sql.data.objects.persistence.threatmaster.assessment.ControlReportPlatform;
import com.sql.data.provider.threatmaster.ControlReportProvider;

import de.ralf.threatmasterkitchen.controller.datatransfer.ControlReportDTO;
import de.ralf.threatmasterkitchen.controller.datatransfer.MitigationReportDTO;
import de.ralf.threatmasterkitchen.security.utils.SecurityUtils;

@Controller
public class ControlReportController {

	@PostMapping(value = "/RiskAssessment/{service_id}/RiskQuestionnaire/submitControlReport")
	public ResponseEntity<Integer> updateRequest(@RequestBody final MitigationReportDTO mitigationReportDTO,
			@PathVariable(name = "service_id", required = true) Integer service_id, Authentication authentication) {
		boolean isAdmin = new SecurityUtils().isAdmin(authentication);
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		boolean isAuthorized = false;
		if (isAdmin) {
			isAuthorized = true;
		}
		if (isAuthorized) {
			if (mitigationReportDTO != null) {
				ControlReportProvider controlReportProvider = new ControlReportProvider();
				ControlReport controlReport = new ControlReport();
				controlReport.setService_id(service_id);
				controlReport.setReport_timestamp(new Timestamp(System.currentTimeMillis()));
				controlReportProvider.persist(controlReport);
				
				String collections = mitigationReportDTO.getCollections();
				String[] platforms = mitigationReportDTO.getPlatforms().split(",");
				for (String platform : platforms) {
					ControlReportPlatform controlReportPlatform = new ControlReportPlatform();
					controlReportPlatform.setControl_report_id(controlReport.getControl_report_id());
					controlReportPlatform.setPlatform_id(0);
					controlReportPlatform.setPlatform(platform);
					controlReportProvider.persist(controlReportPlatform);
				}
				for (ControlReportDTO controlReportDTO : mitigationReportDTO.getControls()) {
					
					ControlReportControl controlReportControl = new ControlReportControl();
					controlReportControl.setControl_report_id(controlReport.getControl_report_id());
					controlReportControl.setControl_id(controlReportDTO.getControlId());
					controlReportControl.setAppliesTo(controlReportDTO.getAppliesTo());
					controlReportControl.setAllThreats(controlReportDTO.isAllThreats());
					controlReportProvider.persist(controlReportControl);
					if (!controlReportControl.isAllThreats()) {
						for (Integer threat_id : controlReportDTO.getExcludedThreats()) {
							ControlReportControlExcludedThreats excludedThreat = new ControlReportControlExcludedThreats();
							excludedThreat.setThreat_id(threat_id);
							excludedThreat.setControl_report_control_id(controlReportControl.getControl_report_control_id());
							controlReportProvider.persist(excludedThreat);
						}
						
					}
				}
				return new ResponseEntity<Integer>(controlReport.getControl_report_id(), HttpStatus.OK);
			}
			
		}
		return new ResponseEntity(HttpStatus.FORBIDDEN);
	}
}
