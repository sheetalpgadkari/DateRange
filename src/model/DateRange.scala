package model

import java.util.Date
import java.util.Calendar

/** 
* @author sheetal
* @constructor create a new Date Range Model with start date and end date.
* @param sDate start date
* @param eDate end Date
*/

class DateRange (sDate : Date, eDate : Date) {
    // prerequisites for creating the date range model
	require (sDate!=null, "start date is required for the date range")
	require (eDate!=null, "end date is required for the date range")
	require (!(sDate.getTime() > eDate.getTime()), "start date cannot be greater than end date")
	
	// truncate the time component and then assign dates to immutable date variables
	var truncateTime = (dt : Date) => {	
	  val cal = Calendar.getInstance()
	  cal.setTime(dt)
	  cal.set(Calendar.HOUR_OF_DAY, 0)
	  cal.clear(Calendar.MINUTE) 
	  cal.clear(Calendar.SECOND) 
	  cal.clear(Calendar.MILLISECOND) 
	  dt.setTime(cal.getTimeInMillis())
	  dt
	}
	val startDate: Date = truncateTime(sDate)
	val endDate: Date = truncateTime(eDate)

	/**  
	* Get the days between the date range
	*/
	def getDaysDiff(): Long = {
		val AVERAGE_MILLIS_PER_DAY =  86400000
		(endDate.getTime() - startDate.getTime())/AVERAGE_MILLIS_PER_DAY
	}
	
	/**  
	* Get the months between the date range
	*/
	def getMonthsDiff(): Double = {
	  	val AVERAGE_MILLIS_PER_MONTH =  365.24 * 24 * 60 * 60 * 1000 / 12
		(endDate.getTime() - startDate.getTime())/AVERAGE_MILLIS_PER_MONTH
	}
	
	/**  
	* Checks if the date is between the date range 
	* @param dt date to check for
	*/
	def isBetweenTheDateRange( dt : Date): Boolean = {
	  dt.after(startDate) && dt.before(endDate)
	}

	
}