package model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.*;

/**
 * @author sheetal
 * This is the test class for Date Range Model
 */

public class DateRangeModelTest {

	@Rule
	public ExpectedException expectedEx = ExpectedException.none();
	
	Date todayDate;
	Date yesterDayDate;
	Date tommorowDate;
	
	static Double DELTA = 1e-3;
	
	private Date getDateFromDateString (String dateString){
		String dateFormat = "dd/MM/yyyy";
		DateFormat dateFormatOj = new SimpleDateFormat(dateFormat);
		try {
			return dateFormatOj.parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
    @Before 
    public void initialize() {
    	todayDate= new Date();
    	
    	Calendar cal1 = Calendar.getInstance();
    	cal1.add(Calendar.DATE, -1);
    	yesterDayDate = new Date ();
    	yesterDayDate.setTime(cal1.getTimeInMillis());
    	
    	Calendar cal2 = Calendar.getInstance();
    	cal2.add(Calendar.DATE, +1);
    	tommorowDate = new Date ();
    	tommorowDate.setTime(cal2.getTimeInMillis());    	
     }
	
	/**
	 * This method test the creation of the date range with 
	 * the valid condition of start date greater than end date    
	 */
	@Test
	public void creatDateRangeWithStartDateLessThanEndDate()
	{
		DateRange dateRange1 = new DateRange( todayDate, tommorowDate);
		assertNotNull(dateRange1);
		assertEquals( todayDate, dateRange1.startDate() );
		assertEquals( tommorowDate, dateRange1.endDate() );
	}	
	/**
	 * This method test the creation of the date range with 
	 * the invalid condition of start date been empty     
	 */
	@Test
	public void creatDateRangeWithEmptyStartDate()
	{
		expectedEx.expect(IllegalArgumentException.class);
		expectedEx.expectMessage("start date is required for the date range");
		DateRange dateRange1 = new DateRange(null, todayDate);
		assertNull(dateRange1);

	}
	
	/**
	 * This method test the creation of the date range with 
	 * the invalid condition of end date been empty     
	 */
	@Test
	public void creatDateRangeWithEmptyEndDate()
	{
		expectedEx.expect(IllegalArgumentException.class);
		expectedEx.expectMessage("end date is required for the date range");
		DateRange dateRange1 = new DateRange( todayDate, null);
		assertNull(dateRange1);
	}
	
	/**
	 * This method test the creation of the date range with 
	 * the invalid condition of start date been greater than end date     
	 */
	@Test
	public void creatDateRangeWithStartDateMoreThanEndDateEg1()
	{
		expectedEx.expect(IllegalArgumentException.class);
		expectedEx.expectMessage("start date cannot be greater than end date");
		DateRange dateRange1 = new DateRange( todayDate, yesterDayDate);
		assertNull(dateRange1);
	}	
	
	/**
	 * This method test the creation of the date range with 
	 * the invalid condition of start date been greater than end date     
	 */
	@Test
	public void creatDateRangeWithStartDateMoreThanEndDateEg2()
	{
		expectedEx.expect(IllegalArgumentException.class);
		expectedEx.expectMessage("start date cannot be greater than end date");
		DateRange dateRange1 = new DateRange( tommorowDate, yesterDayDate );
		assertNull(dateRange1);
	}
	
	/**
	 * This method test the creation of the date range with 
	 * the time component truncated for start date  
	 */
	@Test
	public void creatDateRangeCheckTruncatedStartDate()
	{
		DateRange dateRange1 = new DateRange( todayDate, tommorowDate );
		assertNotNull(dateRange1);
		Calendar cal = Calendar.getInstance();
		cal.setTime(dateRange1.startDate());
		assertEquals(0, cal.get(Calendar.HOUR_OF_DAY));
		assertEquals(0,  cal.get(Calendar.MINUTE));
		assertEquals(0,  cal.get(Calendar.SECOND));
		assertEquals(0,  cal.get(Calendar.MILLISECOND));
	}	
	/**
	 * This method test the creation of the date range with 
	 * the time component truncated for end date  
	 */
	@Test
	public void creatDateRangeCheckTruncatedEndDate()
	{
		DateRange dateRange1 = new DateRange( todayDate, tommorowDate );
		assertNotNull(dateRange1);
		Calendar cal = Calendar.getInstance();
		cal.setTime(dateRange1.endDate());
		assertEquals(0, cal.get(Calendar.HOUR_OF_DAY));
		assertEquals(0,  cal.get(Calendar.MINUTE));
		assertEquals(0,  cal.get(Calendar.SECOND));
		assertEquals(0,  cal.get(Calendar.MILLISECOND));
	}	
	/**
	 * This method test the creation of the date range 
	 * and checking the value of the start date 
	 */
	@Test
	public void creatDateRangeCheckStartDate()
	{
		DateRange dateRange1 = new DateRange( todayDate, tommorowDate );
		assertNotNull(dateRange1);
		Calendar cal = Calendar.getInstance();
		cal.setTime(dateRange1.startDate());
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(new Date());
		assertEquals( cal.get(Calendar.DAY_OF_MONTH), cal2.get(Calendar.DAY_OF_MONTH));
		assertEquals( cal.get(Calendar.MONTH), cal2.get(Calendar.MONTH));
		assertEquals( cal.get(Calendar.YEAR), cal2.get(Calendar.YEAR));
	}	
	/**
	 * This method test the creation of the date range 
	 * and checking the value of the end date 
	 */
	@Test
	public void creatDateRangeCheckEndDate()
	{
		DateRange dateRange1 = new DateRange( yesterDayDate, todayDate );
		assertNotNull(dateRange1);
		Calendar cal = Calendar.getInstance();
		cal.setTime(dateRange1.endDate());
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(new Date());
		assertEquals( cal.get(Calendar.DAY_OF_MONTH), cal2.get(Calendar.DAY_OF_MONTH));
		assertEquals( cal.get(Calendar.MONTH), cal2.get(Calendar.MONTH));
		assertEquals( cal.get(Calendar.YEAR), cal2.get(Calendar.YEAR));
	}	

	/**
	 * This method test the creation of the date range 
	 * and get the month difference on the two dates
	 */
	@Test
	public void getMonthDiffForDateRange()
	{
		Date d1 = getDateFromDateString ("01/05/2013");
		Date d2 = getDateFromDateString ("01/06/2013");
		DateRange dateRange1 = new DateRange( d1, d2 );
		assertNotNull(dateRange1);
		assertEquals(1.018, dateRange1.getMonthsDiff() ,  DELTA);
	}	
	
	/**
	 * This method test the creation of the date range 
	 * and get the month difference on the two dates
	 */
	@Test
	public void getMonthDiffForDateRangeEg2()
	{
		Date d1 = getDateFromDateString ("01/07/2013");
		Date d2 = getDateFromDateString ("16/08/2013");
		DateRange dateRange1 = new DateRange( d1, d2 );
		assertNotNull(dateRange1);
		assertEquals(1.511, dateRange1.getMonthsDiff() ,DELTA);
	}	
	
	/**
	 * This method test the creation of the date range 
	 * and get the days difference on the two dates
	 */
	@Test
	public void getDaysDiffForDateRangeEq1()
	{
		Date d1 = getDateFromDateString ("01/08/2013");
		Date d2 = getDateFromDateString ("16/08/2013");
		DateRange dateRange1 = new DateRange( d1, d2 );
		assertEquals(15, dateRange1.getDaysDiff());
	}
	
	/**
	 * This method test the creation of the date range 
	 * and get the days difference on the two dates
	 */
	@Test
	public void getDaysDiffForDateRangeEq2()
	{
		Date d1 = getDateFromDateString ("11/09/2013");
		Date d2 = getDateFromDateString ("16/09/2013");
		DateRange dateRange1 = new DateRange( d1, d2 );
		assertEquals(5, dateRange1.getDaysDiff());
	}

	/**
	 * This method test the creation of the date range 
	 * and get the days difference on the two dates
	 */
	@Test
	public void getDaysDiffForDateRangeEq3()
	{

		DateRange dateRange1 = new DateRange( yesterDayDate, tommorowDate );
		assertEquals(2, dateRange1.getDaysDiff());
	}
	
	/**
	 * This method test the creation of the date range 
	 * and check if the date parameter is between the two dates 
	 */
	@Test
	public void isDateBetweenTheDateRangeModel()
	{
		Date d1 = getDateFromDateString ("01/07/2013");
		Date d2 = getDateFromDateString ("16/08/2013");
		Date d3 = getDateFromDateString ("01/08/2013");
		DateRange dateRange1 = new DateRange( d1, d2 );
		assertNotNull(dateRange1);
		assertTrue(dateRange1.isBetweenTheDateRange(d3));
	}	
	
	/**
	 * This method test the creation of the date range 
	 * and check if the date parameter is between the two dates 
	 */
	@Test
	public void isDateBetweenTheDateRangeModelEg1()
	{
		DateRange dateRange1 = new DateRange( yesterDayDate, tommorowDate );
		assertNotNull(dateRange1);
		assertTrue(dateRange1.isBetweenTheDateRange(todayDate));
	}
	
	/**
	 * This method test the creation of the date range 
	 * and check if the date parameter is between the two dates 
	 */
	@Test
	public void isDateBetweenTheDateRangeModelOnBoundary()
	{
		Date d1 = getDateFromDateString ("01/07/2013");
		Date d2 = getDateFromDateString ("16/08/2013");
		Date d3 = getDateFromDateString ("01/07/2013");
		DateRange dateRange1 = new DateRange( d1, d2 );
		assertNotNull(dateRange1);
		assertFalse(dateRange1.isBetweenTheDateRange(d3));
	}	
}
