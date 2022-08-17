package com.sql.data.objects.aggregations;

import java.io.Serializable;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;

public class ReportOverview implements Serializable {

	private int reportID;
	private String timeString;
	private List<RatedAttackVector> attackVectors;

	public int getReportID() {
		return reportID;
	}

	public void setReportID(int reportID) {
		this.reportID = reportID;
	}

	public String getTimeString() {
		return timeString;
	}

	public void setTimeString(String timeString) {
		if (timeString != null) {
			try {
				Instant reportDate = Instant.parse(timeString).truncatedTo(ChronoUnit.SECONDS);
				//Instant reportDate = Instant.parse(lastReportDate+"Z").truncatedTo(ChronoUnit.MINUTES);
				DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_TIME.withZone(ZoneId.systemDefault());
				String lastReportTime = formatter.format(reportDate);
				formatter = DateTimeFormatter.ISO_LOCAL_DATE.withZone(ZoneId.systemDefault());
				String lastReportDays = formatter.format(reportDate);
				this.timeString = lastReportDays+" at "+lastReportTime;
			} catch (DateTimeParseException ex) {
				ZonedDateTime reportDate = ZonedDateTime.parse(timeString).truncatedTo(ChronoUnit.SECONDS);
				DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_TIME.withZone(ZoneId.systemDefault());
				String lastReportTime = formatter.format(reportDate);
				formatter = DateTimeFormatter.ISO_LOCAL_DATE.withZone(ZoneId.systemDefault());
				String lastReportDays = formatter.format(reportDate);
				this.timeString = lastReportDays+" at "+lastReportTime;
			}
		} else {
			this.timeString = "";
		}
		
		
	}

	public List<RatedAttackVector> getAttackVectors() {
		return attackVectors;
	}

	public void setAttackVectors(List<RatedAttackVector> attackVectors) {
		this.attackVectors = attackVectors;
	}

	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (o instanceof ReportOverview) {
			if (((ReportOverview) o).getReportID() == this.reportID) {
				return true;
			}
		}
		return false;
	}

	public int hashCode() {
		return Objects.hash(reportID);
	}

}
