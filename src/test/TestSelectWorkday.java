package test;

import controller.*;
import model.*;
import database.*;

import java.time.LocalDate;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;


public class TestSelectWorkday {
	@Before
	public void setup() {
		try {
			DBConnection con = DBConnection.getInstance();
			con.commitTransaction();
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@After
	public void tearDown() {
		try {
			DBConnection con = DBConnection.getInstance();
			con.rollbackTransaction();
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testfindWorkdayByDatewithNewWorkday() {
		WorkdayController workContr = new WorkdayController();
		LocalDate date = LocalDate.of(2020, 12, 24);
		model.Workday workday = workContr.createNewWorkday(date, "", false);
		assertEquals(workContr.findWorkdayByDate(date).getDate(), workday.getDate());
		assertEquals(workContr.findWorkdayByDate(date).getNote(), workday.getNote());
		assertEquals(workContr.findWorkdayByDate(date).isPublished(), workday.isPublished());
	}

	@Test
	public void testfindWorkdayByDateWithExistingWorkday() {
		WorkdayController workContr = new WorkdayController();
		LocalDate date = LocalDate.of(2011, 11, 11);
		workContr.setCurrWorkday(date);
		try {
			DBConnection con = DBConnection.getInstance();
			con.rollbackTransaction();
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		model.Workday workday = workContr.createNewWorkday(date, "", false);
	}
	

}
