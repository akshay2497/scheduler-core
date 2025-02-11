package com.scheduler.service.scheduler.utils;

import com.cronutils.model.Cron;
import com.cronutils.model.CronType;
import com.cronutils.model.definition.CronDefinitionBuilder;
import com.cronutils.model.time.ExecutionTime;
import com.cronutils.parser.CronParser;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.LinkedList;
import java.util.List;

public class TimeUtils {

	public static LocalDateTime roundToNearestTenMinutes(LocalDateTime dateTime) {
		int minutes = dateTime.getMinute();

		int roundedMinutes = (minutes / 10) * 10;

		return dateTime.withMinute(roundedMinutes).withSecond(0).withNano(0);
	}

	public static List<LocalDateTime> createScheduleEventKeys(String cronExpression,
													   		  Integer totalRuns) {
		CronParser parser =
			new CronParser(
				CronDefinitionBuilder.instanceDefinitionFor(CronType.QUARTZ));
		Cron cron = parser.parse(cronExpression);

		ExecutionTime executionTime = ExecutionTime.forCron(cron);

		List<LocalDateTime> schedule = new LinkedList<>();

		ZonedDateTime currentDate = ZonedDateTime.now(ZoneOffset.UTC);

		for (int i = 0; i < totalRuns; i++) {
			currentDate = executionTime
				.nextExecution(currentDate)
				.orElseThrow(() -> new RuntimeException("No next execution found"));

			LocalDateTime nextLocalDateTime = currentDate.toLocalDateTime();

			schedule.add(nextLocalDateTime);
		}

		return schedule;
	}
}
