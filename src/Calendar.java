import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

/**
 * Created by Artem Klots on 04.06.2016.
 */
public class Calendar {
    static LocalDateTime date;
    static LocalDateTime now = LocalDateTime.now();

    /**
     * This function checks if selected day is today.
     *
     * @param currentDay - verifiable data.
     * @return - is it today, or not.
     */
    private static boolean isItToday(LocalDateTime currentDay) {
        return (currentDay.getDayOfMonth() == now.getDayOfMonth()) && (currentDay.getYear() == now.getYear() && (currentDay.getMonth() == now.getMonth()));
    }

    /**
     * This method writes title of month and days of week into the top of calendar, and empty cells at the beginning of month.
     */
    private static void printCalendarHeader() {
        System.out.printf("%20s", date.getMonth() + "\n");
        System.out.println(" Mon  Tue  Wen  Thu  Fri  Sut  Sun");

        for (int i = 1; i < date.with(TemporalAdjusters.firstDayOfMonth()).getDayOfWeek().getValue(); i++)
            System.out.print("     ");
    }

    /**
     * This method writes all days of month.
     */
    private static void printCalendarBodyPart() {
        int lastDayOfMonth = date.with(TemporalAdjusters.lastDayOfMonth()).getDayOfMonth();

        // Loop for all days of month
        for (int i = 1; i <= lastDayOfMonth; i++) {
            LocalDateTime currentDay = date.plusDays(i - 1);
            // Write current day in green rectangle if it is today.
            if (isItToday(currentDay)) {
                System.out.printf("%17s ", (char) 27 + "[42m  " + i + "\033[39;49m");
            } else {
                // Write Sut. and Sun. in red color
                if (currentDay.getDayOfWeek().getValue() >= 6) {
                    System.out.print("\033[31;1m"); //change console color to red.
                    System.out.printf("%4d ", i);
                    System.out.print("\033[39;49m");//change console color to default.
                } else {
                    // Write normal weekday.
                    System.out.printf("%4d ", i);
                }
            }
            // Go to a new line, if week is over.
            if (((currentDay.getDayOfWeek().getValue())) % 7 == 0) {
                System.out.println();
            }
        }
    }

    /**
     * This method joins all printing method in one.
     */
    public static void printCalendar() {
        printCalendarHeader();
        printCalendarBodyPart();
    }

    /**
     * The main method receive incoming parameters in numeric format.
     * @param args - args[0] - month; args[1] - year.
     */
    public static void main(String[] args) {

        /**
         * In the code below wrote checking for arguments and their validity.
         * If arguments is empty, program will select the current month.
         * If arguments is incorrect, program will stop.
         */
        if (args.length < 1) {
            date = LocalDateTime.of(now.getYear(), now.getMonth(), 1, 1, 0);
        } else {
            try {
                int month = Integer.parseInt(args[0]);
                int year = Integer.parseInt(args[1]);
                if ((month < 1) || (month > 12))
                    throw new IllegalArgumentException();
                date = LocalDateTime.of(year, month, 1, 1, 0);

            } catch (NumberFormatException e) {
                System.out.println("Wrong type of arguments!");
                return;
            } catch (IllegalArgumentException e) {
                System.out.println("Wrong input!");
                return;
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Wrong number of arguments!");
                return;
            }
        }
        printCalendar();
    }
}
