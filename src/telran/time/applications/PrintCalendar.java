package telran.time.applications;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.time.format.TextStyle;
import java.time.temporal.ChronoField;
import java.util.Locale;

public class PrintCalendar {
	private static final int TITLE_OFFSET = 10;
	private static final int WEEK_DAYS_OFFSET = 1;
	private static final int COLUMN_WIDTH = 4;
	private static DayOfWeek[] weekDays = DayOfWeek.values();
	private static Locale LOCALE = Locale.getDefault();

	public static void main(String[] args) {
		try {
			RecordArguments recordArguments = getRecordArguments(args);
			printCalendar(recordArguments);
		} catch (RuntimeException e) {
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	private static void printCalendar(RecordArguments recordArguments) {
		printTitle(recordArguments.month(), recordArguments.year());
		printWeekDays(recordArguments.firstDay());
		printDays(recordArguments.month(), recordArguments.year(), recordArguments.firstDay());
		
	}

	private static void printTitle(int month, int year) {
		Month monthEn = Month.of(month);
		System.out.printf("%s%s %d\n", " ".repeat(TITLE_OFFSET), 
				monthEn.getDisplayName(TextStyle.FULL, LOCALE), year);
	}

	private static void printWeekDays(DayOfWeek dayOfWeek) {
		System.out.printf("%s", " ".repeat(WEEK_DAYS_OFFSET));
		int index = dayOfWeek.getValue() - 1;
		for(int i = 0; i < weekDays.length; i++) {
			if(index > 6) {
				index = 0;
			}
			System.out.printf("%s  ", weekDays[index].getDisplayName(TextStyle.SHORT, LOCALE));
			index++;
		}
		System.out.println();
	}

	private static void printDays(int month, int year, DayOfWeek dayOfWeek) {
		int nDays = getMonthDays(month, year);
		int firstDayOfWeek = dayOfWeek.getValue();
		int firstMonthWeekDay = getFirstMonthWeekDay(month, year);
		int currentWeekDay = getCurrentDay(firstDayOfWeek, firstMonthWeekDay);
		
		System.out.printf("%s", " ".repeat(getFirstColumnOffset(currentWeekDay)));
		for(int day = 1; day <= nDays; day++) {
			System.out.printf("%3d ", day);
			
			if(currentWeekDay >= 6) {
				currentWeekDay = 0;
				System.out.println();
			} else {
				currentWeekDay++;
			}	
		}
		
	}

	private static int getCurrentDay(int firstDayOfWeek, int firstMonthWeekDay) {
		int[] newWeek = new int [weekDays.length];
		int index = firstDayOfWeek - 1;
		int res = 0;
		for(int i = 0; i < newWeek.length; i++) {
			newWeek[i] = weekDays[index].getValue();
			index++;
			if(newWeek[i] == firstMonthWeekDay) {
				res = i;
			}
			if(index > 6) {
				index = 0;
			}
		}
		return res;
	}

	private static int getFirstColumnOffset(int currentWeekDay) {

		return COLUMN_WIDTH * currentWeekDay;
	}

	private static int getMonthDays(int month, int year) {
		YearMonth ym = YearMonth.of(year, month);
		return ym.lengthOfMonth();
	}
	private static int getFirstMonthWeekDay(int month, int year) {
		LocalDate ld = LocalDate.of(year, month, 1);
		return ld.get(ChronoField.DAY_OF_WEEK);
	}
	private static RecordArguments getRecordArguments(String[] args) throws Exception{
		
		int month = getMonthAgr(args);
		int year = getYearArg(args);
		DayOfWeek dayOfWeek = getFirstDayOfWeek(args);
		return new RecordArguments(month, year, dayOfWeek);
	}

	private static DayOfWeek getFirstDayOfWeek(String[] args) {
		DayOfWeek firstDayRes = DayOfWeek.MONDAY;
		if(args.length > 2) {
			try {
				firstDayRes = DayOfWeek.valueOf(args[2].toUpperCase());
			}catch(IllegalArgumentException e) {
				throw new IllegalArgumentException("argument must be a day of week");
			}
		}
		return firstDayRes;
	}

	private static int getYearArg(String[] args) throws Exception {
		int yearRes = LocalDate.now().getYear();
		if(args.length > 1) {
			try {
				yearRes = Integer.parseInt(args[1]);
				
			} catch(NumberFormatException e) {
				throw new Exception("Year must be a number");
			}
			
		}
		return yearRes;
	}

	private static int getMonthAgr(String[] args) throws Exception {
		int monthRes = LocalDate.now().getMonthValue();
		if(args.length > 0) {
			try {
				monthRes = Integer.parseInt(args[0]);
				if(monthRes < 1) {
					throw new Exception("Month value must not be less than 1");
				}
				if(monthRes > 12) {
					throw new Exception("Month value must not be greater than 1");
				}
			} catch(NumberFormatException e) {
				throw new Exception("Month value must be a number");
			}
			
		}
		return monthRes;
	}
}
