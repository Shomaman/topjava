package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static final int MEAL_BY_USER_BREAKFAST_ID = START_SEQ + 3;
    public static final int MEAL_BY_USER_LUNCH_ID = START_SEQ + 4;
    public static final int MEAL_BY_USER_DINNER_ID = START_SEQ + 5;
    public static final int MEAL_BY_ADMIN_WITH_BORDER_ID = START_SEQ + 6;
    public static final int MEAL_BY_ADMIN_BREAKFAST_ID = START_SEQ + 7;
    public static final int MEAL_BY_ADMIN_LUNCH_ID = START_SEQ + 8;
    public static final int MEAL_BY_ADMIN_DINNER_ID = START_SEQ + 9;
    public static final int NOT_FOUND = 10;

    public static final Meal mealByUserBreakfast = new Meal(MEAL_BY_USER_BREAKFAST_ID, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500);
    public static final Meal mealByUserLunch = new Meal(MEAL_BY_USER_LUNCH_ID, LocalDateTime.of(2020, Month.JANUARY, 30, 15, 0), "Обед", 1000);
    public static final Meal mealByUserDinner = new Meal(MEAL_BY_USER_DINNER_ID, LocalDateTime.of(2020, Month.JANUARY, 30, 18, 0), "Ужин", 500);
    public static final Meal mealByAdminWithBorder = new Meal(MEAL_BY_ADMIN_WITH_BORDER_ID, LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100);
    public static final Meal mealByAdminBreakfast = new Meal(MEAL_BY_ADMIN_BREAKFAST_ID, LocalDateTime.of(2020, Month.JANUARY, 31, 7, 0), "Завтрак", 1000);
    public static final Meal mealByAdminLunch = new Meal(MEAL_BY_ADMIN_LUNCH_ID, LocalDateTime.of(2020, Month.JANUARY, 31, 12, 0), "Обед", 500);
    public static final Meal mealByAdminDinner = new Meal(MEAL_BY_ADMIN_DINNER_ID, LocalDateTime.of(2020, Month.JANUARY, 31, 18, 0), "Ужин", 410);

    public static Meal getNew() {
        return new Meal(null, LocalDateTime.of(2024, Month.MAY, 31, 10, 0), "Завтрак", 500);
    }

    public static Meal getUpdated() {
        Meal updated = new Meal(mealByUserBreakfast);
        updated.setDateTime(LocalDateTime.of(2024, Month.MAY, 31, 13, 0));
        updated.setDescription("Test");
        updated.setCalories(1330);
        return updated;
    }

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).usingRecursiveComparison().ignoringFields("date_time").isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingRecursiveFieldByFieldElementComparatorIgnoringFields("date_time").isEqualTo(expected);
    }
}
