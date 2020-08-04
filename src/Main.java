import java.time.LocalDate;

import java.time.Period;

public class Main {

    public static void main(String[] args) {

        LocalDate start = LocalDate.of(2020, 8,01);
        LocalDate end = LocalDate.of(2020, 9, 01);
        Period period = Period.ofWeeks(2);
        // create a period
        performAnimalEnrichment(start, end, period);
    }
    private static void performAnimalEnrichment(LocalDate start, LocalDate end,
                                                Period period) {
        // uses the generic period
        LocalDate upTo = start;
        while (upTo.isBefore(end)) {
            System.out.println("give new toy: " + upTo);
            upTo = upTo.plus(period);     // adds the period

        }
}}
