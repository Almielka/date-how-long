import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

/**
 * Develop a method that determines how long time has passed since a given date
 *
 * @author Anna S. Almielka
 */

public class Solution {
    public static void main(String[] args) {

        LocalDateTime now = LocalDateTime.now();

        try (Scanner in = new Scanner(System.in)) {
            System.out.println("Enter date in format YYYY-MM-DD HH:MM");
            String inDate = in.nextLine();
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"); // can add seconds {@code ss}
            LocalDateTime endDate = LocalDateTime.parse(inDate, dtf);

            if (endDate.isBefore(now)) { // check entered date, is not future

                Period period = getDate(endDate, now);
                int days = period.getDays();    // need to know the total number of days,
                // in the method {@code getTime()} we will reduce by one day, if less than 24 hours have passed
                long[] time = getTime(endDate, now, days);

                System.out.println("Passed " + period.getYears() + " years, " +
                        period.getMonths() + " months, " +
                        time[0] + " days, " +
                        time[1] + " hours, " +
                        time[2] + " minutes, " +
                        time[3] + " seconds");
            } else {
                System.out.println("Date and time must be less than the current date and time.");
            }

        } catch (Exception e) {
            System.out.println("Date and time entered incorrectly!");
        }
    }

    public static Period getDate(LocalDateTime endDate, LocalDateTime now) {
        return Period.between(endDate.toLocalDate(), now.toLocalDate());
    }

    public static long[] getTime(LocalDateTime endDate, LocalDateTime now, int days) {
        int HH = now.getHour() - endDate.getHour(); // count a difference in hours
        if (HH < 0) {                               // if this difference is negative
            days = days - 1;                        // the number of days we reduce by 1
            HH = 24 + HH;                           // the difference increase for a full one day = 24 hours

        }
        int mm = now.getMinute() - endDate.getMinute(); // similarly — count a difference in minutes
        if (mm < 0) {                                   // if this difference is negative
            HH = HH - 1;                                // the number of hours we reduce by 1
            mm = 60 + mm;                               // the difference increase for a full one hour = 60 minutes
        }
        int ss = now.getSecond() - endDate.getSecond(); // similarly — count a difference in minutes
        if (ss < 0) {                                   // if this difference is negative
            mm = mm - 1;                                // the number of minutes we reduce by 1
            ss = 60 + ss;                               // the difference increase for a full one minute = 60 seconds
        }
        return new long[]{days, HH, mm, ss}; // return the time and the number of days, which could decrease by a whole day
    }
}