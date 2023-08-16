package telran.time;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;

public class NextFriday13 implements TemporalAdjuster {

	@Override
	public Temporal adjustInto(Temporal temporal) {
		
		LocalDate nextFriday13 = (LocalDate) temporal;
	    do {
	    	nextFriday13 = nextFriday13.with(TemporalAdjusters.next(DayOfWeek.FRIDAY));
	    }while(nextFriday13.getDayOfMonth() != 13);
	    
		return nextFriday13;
		
	}

}
