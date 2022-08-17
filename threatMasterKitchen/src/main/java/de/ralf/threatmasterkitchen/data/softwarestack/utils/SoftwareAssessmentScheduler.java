package de.ralf.threatmasterkitchen.data.softwarestack.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceException;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;

import com.sql.data.provider.ScheduleProvider;
import com.sql.hibernate.HibernateReportInstance;
import com.sql.data.objects.persistence.report.schedule.Schedule;
import com.sql.data.objects.persistence.report.schedule.SchedulerFact;

/*
 * This class parses existing schedule plans and creates a schedule for upcoming assessments
 */
public class SoftwareAssessmentScheduler {

	public static final DateTimeFormatter dateFormat = DateTimeFormatter.ISO_LOCAL_DATE_TIME; // Format for
																								// YYYY-MM-DDTHH:mm
	public static final String timeZone = "+1"; // UTC

	public void addNewAssessmentSchedule(int swStackId, String timeString, boolean explicit, int hours, int days,
			int weeks, int months, String assessmentFocus, String notification) {
		SchedulerFact schedFact = new SchedulerFact();
		schedFact.setSwStackId(swStackId);
		schedFact.setTimeString(timeString);
		schedFact.setExplicit(explicit);
		schedFact.setHours(hours);
		schedFact.setDays(days);
		schedFact.setWeeks(weeks);
		schedFact.setMonths(months);
		schedFact.setLastExecutedTimeString(timeString);
		schedFact.setNotification(notification);
		schedFact.setAssessmentFocus(assessmentFocus);
		Session session = HibernateReportInstance.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		session.save(schedFact);
		Schedule sched = new Schedule();
		sched.setSwStackID(swStackId);
		sched.setExplicit(explicit);
		sched.setScheduleId(schedFact.getScheduleId());
		sched.setExecutionTimeString(timeString);
		sched.setNotification(notification);
		sched.setAssessmentFocus(assessmentFocus);
		session.save(sched);
		tx.commit();
		session.close();
	}

	public boolean updateAssessmentSchedule(int scheduleId, int swStackId, String timeString, boolean explicit,
			int hours, int days, int weeks, int months, String assessmentFocus, String notification) {
		Session session = HibernateReportInstance.getSessionFactory().openSession();
		SchedulerFact schedFact = session.get(SchedulerFact.class, scheduleId);
		if (schedFact == null) {
			session.close();
			return false;
		}
		Transaction tx = session.beginTransaction();
		schedFact.setSwStackId(swStackId);
		schedFact.setTimeString(timeString);
		schedFact.setExplicit(explicit);
		schedFact.setHours(hours);
		schedFact.setDays(days);
		schedFact.setWeeks(weeks);
		schedFact.setMonths(months);
		schedFact.setLastExecutedTimeString(timeString);
		schedFact.setNotification(notification);
		schedFact.setAssessmentFocus(assessmentFocus);
		session.save(schedFact);
		tx.commit();
		session.close();
		return true;
	}

	public boolean deleteAssesmentSchedule(int scheduleId) {
		Session session = HibernateReportInstance.getSessionFactory().openSession();
		SchedulerFact schedFact = session.get(SchedulerFact.class, scheduleId);
		if (schedFact == null) {
			session.close();
			System.out.println("Fact is null");
			return false;
		}
		Transaction tx = session.beginTransaction();
		session.delete(schedFact);
		tx.commit();
		session.close();
		return true;
	}

	public void cleanAssessmentSchedule() {
		Session session = HibernateReportInstance.getSessionFactory().openSession();
		NativeQuery<SchedulerFact> query_scheduler_facts = session.createNativeQuery("SELECT * FROM scheduler_facts",
				SchedulerFact.class);
		List<SchedulerFact> scheduler_facts = query_scheduler_facts.list();
		for (SchedulerFact scheduler_fact : scheduler_facts) {
			/*
			 * Prüfen ob ein schedule bereits existiert
			 */
			if (!(scheduler_fact.getHours() == 0 && scheduler_fact.getDays() == 0 && scheduler_fact.getWeeks() == 0
					&& scheduler_fact.getMonths() == 0)) {
				NativeQuery<Schedule> query_schedule = session
						.createNativeQuery("SELECT * FROM schedule WHERE scheduleID = :scheduleID", Schedule.class);
				query_schedule.setParameter("scheduleID", scheduler_fact.getScheduleId());
				try {
					Schedule schedule = query_schedule.getSingleResult();
				} catch (NoResultException ex) {
					scheduleNewAssessment(session, scheduler_fact);
				} catch (NonUniqueResultException ex) {
					System.err.println(
							"There are multiple scheduled assessments! Only one upcoming assessment should be scheduled!");
				} catch (PersistenceException ex) {
					System.err.println(ex.getLocalizedMessage());
				}
			}
		}
		session.close();
	}

	public void scheduleNewUniqueAssessment(String executionTimeString, boolean explicit, int swStackID,
			String assessmentFocus, String notification) {
		Session session = HibernateReportInstance.getSessionFactory().openSession();
		SchedulerFact scheduleFact = new SchedulerFact();
		scheduleFact.setDays(0);
		scheduleFact.setExplicit(explicit);
		scheduleFact.setHours(0);
		scheduleFact.setMonths(0);
		scheduleFact.setWeeks(0);
		scheduleFact.setLastExecutedTimeString("");
		scheduleFact.setTimeString(executionTimeString);
		scheduleFact.setSwStackId(swStackID);
		scheduleFact.setAssessmentFocus(assessmentFocus);
		scheduleFact.setNotification(notification);
		Transaction tx = session.beginTransaction();
		session.save(scheduleFact);
		tx.commit();
		scheduleNewAssessment(session, scheduleFact);
	}

	public static Instant getInstantFromTimeString(String timeString, DateTimeFormatter formatter) {
		DateTimeFormatter timeFormatter = formatter;
		ZonedDateTime zonedDate = LocalDateTime.parse(timeString, timeFormatter).atZone(ZoneId.systemDefault());
				
				//.atZone(ZoneId.of(SoftwareAssessmentScheduler.timeZone));
		String zonedTimeString = zonedDate.format(DateTimeFormatter.ISO_INSTANT);
		Instant instantDate = Instant.parse(zonedTimeString);
		return instantDate;
	}

	public static String getStringFromInstant(Instant time, DateTimeFormatter formatter) {
		return formatter.withZone(ZoneId.systemDefault()).format(time);
	}

	private void scheduleNewAssessment(Session session, SchedulerFact scheduleFact) {
		try {
			if (scheduleFact.getLastExecutedTimeString().equals("")) {
				Schedule schedule = new Schedule();
				String executionTime = getStringFromInstant(Instant.now(), SoftwareAssessmentScheduler.dateFormat);// SoftwareAssessmentScheduler.dateFormat.format(executionDate);
				schedule.setExecutionTimeString(executionTime);
				schedule.setExplicit(scheduleFact.isExplicit());
				schedule.setSwStackID(scheduleFact.getSwStackId());
				schedule.setScheduleId(scheduleFact.getScheduleId());
				schedule.setAssessmentFocus(scheduleFact.getAssessmentFocus());
				schedule.setNotification(scheduleFact.getNotification());
				Transaction tx = session.beginTransaction();
				session.save(schedule);
				scheduleFact.setLastExecutedTimeString(executionTime);
				session.update(scheduleFact);
				tx.commit();
			} else {
				Instant lastExecutionDate = getInstantFromTimeString(scheduleFact.getLastExecutedTimeString(),
						SoftwareAssessmentScheduler.dateFormat);
				Instant currentDate = Instant.now();
				int executionState = lastExecutionDate.compareTo(currentDate);
				if (executionState < 0) {
					// Execution was in the past
					Instant executionDate = lastExecutionDate
							.plus(scheduleFact.getMonths()*30*24, ChronoUnit.HOURS)
							.plus(scheduleFact.getWeeks()*24, ChronoUnit.HOURS)
							.plus(scheduleFact.getDays(), ChronoUnit.DAYS)
							.plus(scheduleFact.getHours(), ChronoUnit.HOURS);
					if (executionDate.compareTo(currentDate) < 0) {
						executionDate = Instant.now();
					}
					Schedule schedule = new Schedule();
					String executionTime = getStringFromInstant(executionDate, SoftwareAssessmentScheduler.dateFormat);// SoftwareAssessmentScheduler.dateFormat.format(executionDate);
					schedule.setExecutionTimeString(executionTime);
					schedule.setExplicit(scheduleFact.isExplicit());
					schedule.setSwStackID(scheduleFact.getSwStackId());
					schedule.setScheduleId(scheduleFact.getScheduleId());
					schedule.setAssessmentFocus(scheduleFact.getAssessmentFocus());
					schedule.setNotification(scheduleFact.getNotification());
					Transaction tx = session.beginTransaction();
					session.save(schedule);
					scheduleFact.setLastExecutedTimeString(executionTime);
					session.update(scheduleFact);
					tx.commit();
				}
			}
		} catch (Exception ex) {
			System.err.println(ex.getLocalizedMessage());
		}
	}

	public void populateSchedule(Map<Instant, Schedule> schedule) {
		ScheduleProvider scheduleProvider = new ScheduleProvider();
		List<Schedule> schedules = scheduleProvider.getAllSchedules();
		// SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-ddTHH:mm");
		for (Schedule schedule_object : schedules) {
			Instant executionTime = getInstantFromTimeString(schedule_object.getExecutionTimeString(),
					SoftwareAssessmentScheduler.dateFormat);
			// each scheduled assessment should be in the schedule only once
			if (!schedule.containsValue(schedule_object)) {
				schedule.put(executionTime, schedule_object);
			}
		}
	}

	public void removeSchedule(Schedule schedule) {
		ScheduleProvider scheduleProvider = new ScheduleProvider();
		scheduleProvider.delete(schedule);

	}

}
