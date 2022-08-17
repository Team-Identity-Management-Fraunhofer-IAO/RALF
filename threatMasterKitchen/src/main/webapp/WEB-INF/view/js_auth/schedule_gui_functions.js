function initializeSchedulerGUI() {
	// Initialize Button functions in the scheduler
	$("#schedule-new-assessment-btn").click(
			function() {
				$("button.schedule-button").prop("disabled", true);
				$("#schedule-editor").find("button.schedule-button").prop(
						"disabled", false);
				$("#schedule-editor").fadeToggle("slow", "linear");
			});

	// Add a schedule
	$("#add-schedule-btn").click(
			function() {
				var firstDay = $("#schedule-first-day-id").val();

				var recurrence = $("#schedule-recurrence-id").val();
				var rec_h = $("#schedule-edit-hours-input-id").val();
				var rec_d = $("#schedule-edit-days-input-id").val();
				var rec_w = $("#schedule-edit-weeks-input-id").val();
				var rec_m = $("#schedule-edit-months-input-id").val();

				var appID = $("#application-id").val();

				var assessmentMatchingFocus = $("#schedule-matching-focus-id")
						.val();
				var assessmentMatchingFocusText = "";
				if (assessmentMatchingFocus === "application"){
					assessmentMatchingFocusText = "Application";
				}else if (assessmentMatchingFocus === "implicit"){
					assessmentMatchingFocusText = "Application and vulnerable combinations";
				}
				
				var assessmentNotification = $(
						"#schedule-assessment-notification-id").val();
				var assessmentFocus = $("#schedule-assessment-focus-id").val();
				var assessmentFocusText = "";
				if (assessmentFocus === "new_only") {
					assessmentFocusText = "Only new vulnerabilities";
				} else if (assessmentFocus === "untreated") {
					assessmentFocusText = "New and untreated vulnerabilities";
				} else if (assessmentFocus === "all") {
					assessmentFocusText = "All vulnerabilities";
				}

				var row = $("#template-row").clone(true, true);
				row.removeAttr("id");
				row.css("display", "none");
				$(row.find("td.first-day")).find("span.schedule-edit-text")
						.text(firstDay);
				row.find("td.next-assessment").text(firstDay);

				$(row.find("td.recurrence")).find(
						"span.schedule-edit-text-hours").text(rec_h);
				$(row.find("td.recurrence")).find(
						"span.schedule-edit-text-days").text(rec_d);
				$(row.find("td.recurrence")).find(
						"span.schedule-edit-text-weeks").text(rec_w);
				$(row.find("td.recurrence")).find(
						"span.schedule-edit-text-months").text(rec_m);

				$(row.find("td.focus")).find("span.schedule-edit-text").text(
						assessmentMatchingFocusText);
				$(row.find("td.notification")).find("span.schedule-edit-text")
						.text(assessmentNotification);
				$(row.find("td.assessment-focus")).find(
						"span.schedule-edit-text").text(assessmentFocusText);
				$("#schedules").append(row);
				// Move this after successful AJAX response
				var schedule = {
					'scheduleId':-1,
					'appID' : appID,
					'firstDay' : firstDay,
					'hours' : rec_h,
					'days' : rec_d,
					'weeks' : rec_w,
					'months' : rec_m,
					'matchingFocus' : assessmentMatchingFocus,
					'notification' : assessmentNotification,
					'assessmentFocus' : assessmentFocus
				};
				createSchedule(schedule, row);
			});

	// Discard schedule editor
	$("#discard-schedule-btn").click(function() {
		$("#schedule-editor").fadeToggle("slow", function() {
			$("button.schedule-button").prop("disabled", false);
		});
	});

	// Edit schedule
	$(".edit-schedule-button").click(
			function() {
				var parent = $(this).parent().parent();
				$(parent).find(".schedule-edit-text").fadeToggle(
						"slow",
						function() {
							$(this).parent().find(".schedule-edit-input")
									.fadeToggle("fast", "linear");
						});
				$(this).fadeToggle(
						"slow",
						function() {
							$(this).parent().find(".schedule-editor-button")
									.fadeToggle("fast", "linear");
						});
			});

	// Save schedule changes
	$(".schedule-editor-save-button").click(
			function() {
				var parent = $(this).parent().parent();
				var recurrenceFaded = false;
				$($(parent).find(".schedule-edit-input")).each(
						function() {
							if ($(this).hasClass("schedule-edit-days-input")) {
								$(this).parent().find(".schedule-edit-text-days").text($(this).val());
							} else if ($(this).hasClass("schedule-edit-hours-input")) {
								$(this).parent().find(".schedule-edit-text-hours").text($(this).val());
							} else if ($(this).hasClass("schedule-edit-weeks-input")) {
								$(this).parent().find(".schedule-edit-text-weeks").text($(this).val());
							} else if ($(this).hasClass("schedule-edit-months-input")) {
								$(this).parent().find(".schedule-edit-text-months").text($(this).val());
							} else if ($(this).is("select")){
								$(this).parent().find(".schedule-edit-text").text($(this).find("option:selected").text());
							} else {
								$(this).parent().find(".schedule-edit-text").text($(this).val());
							}
						});
				$(parent).find(".schedule-edit-input").fadeToggle(
						"slow",
						function() {
							if (!$(this).hasClass("schedule-edit-hours-input") && !$(this).hasClass("schedule-edit-days-input") && !$(this).hasClass("schedule-edit-weeks-input") && !$(this).hasClass("schedule-edit-months-input")){
								$(this).parent().find(".schedule-edit-text")
								.fadeToggle("fast", "linear");
							}					
						});
				$(parent).find(".schedule-edit-text-recurrence").fadeToggle("slow","linear"); 
				$(parent).find(".schedule-editor-button")
						.fadeToggle(
								"slow",
								function() {
									if ($(this).hasClass(
											"schedule-editor-save-button")) {
										$(parent).find(".edit-schedule-button")
												.fadeToggle("fast", "linear");
									}
								});
				
				
				var firstDay = parent.find("input.persisted-schedule-timeString").val();//$("#schedule-first-day-id").val();
				var rec_h = parent.find("select.schedule-edit-hours-input").val();
				var rec_d = parent.find("select.schedule-edit-days-input").val();
				var rec_w = parent.find("select.schedule-edit-weeks-input").val();
				var rec_m = parent.find("select.schedule-edit-months-input").val();
				var appID = $("#application-id").val();
				var assessmentMatchingFocus = parent.find("select.persisted-schedule-explicit").val();		
				var assessmentNotification = parent.find("select.persisted-schedule-notification").val();
				var assessmentFocus = parent.find("select.persisted-schedule-focus").val();
				
				var id = parent.data('id');
				
				var schedule = {
						'scheduleId':id,
						'appID' : appID,
						'firstDay' : firstDay,
						'hours' : rec_h,
						'days' : rec_d,
						'weeks' : rec_w,
						'months' : rec_m,
						'matchingFocus' : assessmentMatchingFocus,
						'notification' : assessmentNotification,
						'assessmentFocus' : assessmentFocus
					};
				updateSchedule(schedule);
			}); 

	// Discard schedule changes
	$(".schedule-editor-cancel-button").click(
			function() {
				var parent = $(this).parent().parent();
				$(parent).find(".schedule-edit-input").fadeToggle(
						"slow",
						function() {
							if (!$(this).hasClass("schedule-edit-hours-input") && !$(this).hasClass("schedule-edit-days-input") && !$(this).hasClass("schedule-edit-weeks-input") && !$(this).hasClass("schedule-edit-months-input")){
								$(this).parent().find(".schedule-edit-text")
								.fadeToggle("fast", "linear");
							}	
						});
				$(parent).find(".schedule-edit-text-recurrence").fadeToggle("slow","linear"); 
				$(parent).find(".schedule-editor-button")
						.fadeToggle(
								"slow",
								function() {
									if ($(this).hasClass(
											"schedule-editor-save-button")) {
										$(parent).find(".edit-schedule-button")
												.fadeToggle("fast", "linear");
									}
								});

				$($(parent).find(".schedule-edit-text")).each(
						function() {
							if ($(this).hasClass("schedule-edit-text-recurrence")){
								$(this).parent().find("select.schedule-edit-hours-input").val($(this).find(".schedule-edit-text-hours").text());	
								$(this).parent().find("select.schedule-edit-days-input").val($(this).find(".schedule-edit-text-days").text());
								$(this).parent().find("select.schedule-edit-weeks-input").val($(this).find(".schedule-edit-text-weeks").text());
								$(this).parent().find("select.schedule-edit-months-input").val($(this).find(".schedule-edit-text-months").text());
							}else if ($(this).parent().find(".schedule-edit-input").prop('name') === "schedule-assessment-focus"){
								if ($(this).text() === "Application"){
									$(this).parent().find(".schedule-edit-input").val("application");
								}else if ($(this).text() === "Application and vulnerable combinations"){
									$(this).parent().find(".schedule-edit-input").val("implicit");
								}
							}else if ($(this).parent().find(".schedule-edit-input").prop('name') === "schedule-assessment-notification"){
								if ($(this).text() === "Group"){
									$(this).parent().find(".schedule-edit-input").val("group");
								}else if ($(this).text() === "User"){
									$(this).parent().find(".schedule-edit-input").val("user");
								}else if ($(this).text() === "No Notification"){
									$(this).parent().find(".schedule-edit-input").val("none");
								}
							}else{
								$(this).parent().find(".schedule-edit-input").val(
										$(this).text());
							}							
						});
			});

	// Delete schedule
	$(".schedule-editor-delete-button").click(function() {
		if (confirm("This will remove the schedule entirely. Are you sure?")) {
			var id = $(this).parent().parent().data('id');
			var appID = $("#application-id").val();
			var schedule = {
					'scheduleId':id,
					'appID' : appID,
					'firstDay' : '',
					'hours' : 0,
					'days' : 0,
					'weeks' : 0,
					'months' : 0,
					'matchingFocus' : true,
					'notification' : '',
					'assessmentFocus' : ''
				};
			deleteSchedule(schedule);
			//$(this).parent().parent().remove();
		}
	});

	bindCSRFToAJAXHeaders();

}