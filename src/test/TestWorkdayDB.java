package test;

import model.*;
import controller.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import controller.DataAccessException;
import controller.WorkdayController;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;


class TestWorkdayDB {
	private WorkdayController workdayController;

	@BeforeEach
	void init() throws DataAccessException {
		workdayController = new WorkdayController();
		
	}

	@Test
	void testInsertCorrect() {
		int id = 555;
		LocalDate date = LocalDate.now();
		LocalTime starAt = LocalTime.now();
		LocalTime endAt = LocalTime.now();
		workdayController.createNewWorkday(date, "iasdasd", true);
		workdayController.createNewShift(starAt, endAt, "dette er en note", 2);
		Shift shift = new Shift(id, starAt, endAt, "dette er en note", new Role("test"));
		ArrayList<Shift> shiftList = workdayController.findWorkdayByDate(date).getShiftList();
		assertTrue(shiftList.get(0) == shift); 
	}


}
