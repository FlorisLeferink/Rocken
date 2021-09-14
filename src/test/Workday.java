package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import controller.WorkdayController;

class Workday {
	private WorkdayController workdayController;
	@BeforeEach
	public void setup() {
		workdayController = new WorkdayController();
	}
	@AfterEach
	public void clear() {
		
	}
	@Test
	public void createNewWorkday() {
		LocalDate test = LocalDate.parse("2020-12-13");
		String note = "";
		Boolean published = false;

		model.Workday workday = workdayController.createNewWorkday(test, note, published);
		assertNotNull(workday);

	}
	
	@Test
	public void FindWorkdayByDate() {
		LocalDate test = LocalDate.parse("2020-12-13");
		String note = "";
		Boolean published = false;
	
		model.Workday workday = workdayController.findWorkdayByDate(test);
		assertNotNull(workday);
	}

	@Test
	public void createAndFindWorkday() {
		LocalDate test = LocalDate.parse("2020-12-14");
		String note = "";
		Boolean published = false;
		model.Workday createWorkday = workdayController.createNewWorkday(test, note, published);
		model.Workday findWorkday = workdayController.findWorkdayByDate(test);
		assertEquals(createWorkday.getId(), findWorkday.getId());
	}
	@Test
	public void createAndFindWorkdayNotTheSame() {
		LocalDate FirstDay = LocalDate.parse("2020-12-14");
		LocalDate SecDay = LocalDate.parse("2020-12-15");
		String note = "";
		Boolean published = false;
		model.Workday createWorkday = workdayController.createNewWorkday(FirstDay, note, published);
		model.Workday findWorkday = workdayController.findWorkdayByDate(SecDay);
		assertNotEquals(createWorkday.getDate(), findWorkday.getDate());
	}
	@Test
	public void insertToDataBase() {
		LocalDate test = LocalDate.parse("2020-12-14");
		String note = "";
		Boolean published = false;
				
		workdayController.setCurrWorkday(test);
		assertTrue(workdayController.saveWorkday());
	}
}
