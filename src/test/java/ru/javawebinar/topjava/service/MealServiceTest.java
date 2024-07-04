package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertThrows;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.*;
import static ru.javawebinar.topjava.UserTestData.assertMatch;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    static {
        // Only for postgres driver logging
        // It uses java.util.logging and logged via jul-to-slf4j bridge
        SLF4JBridgeHandler.install();
    }
    @Autowired
    private MealService service;

    @Test
    public void get() {
        Meal meal = service.get(MEAL_BY_USER_BREAKFAST_ID,USER_ID);
        MealTestData.assertMatch(meal, mealByUserBreakfast);
    }

    @Test
    public void delete() {
        service.delete(MEAL_BY_USER_BREAKFAST_ID,USER_ID);
        assertThrows(NotFoundException.class, () -> service.get(MEAL_BY_USER_BREAKFAST_ID,USER_ID));
    }

    @Test
    public void getBetweenInclusive() {
        List<Meal> betweenInclusive = service.getBetweenInclusive(LocalDate.of(2020, Month.JANUARY, 30 ), LocalDate.of(2020, Month.JANUARY, 31), USER_ID);
        List<Meal> betweenInclusiveTest = Arrays.asList(mealByUserDinner, mealByUserLunch,mealByUserBreakfast) ;
    }

    @Test
    public void getAll() {
        List<Meal> all = service.getAll(UserTestData.USER_ID);
        MealTestData.assertMatch(all,mealByUserDinner, mealByUserLunch,mealByUserBreakfast );
    }

    @Test
    public void update() {
        Meal updated = MealTestData.getUpdated();
        service.update(updated,UserTestData.USER_ID);
        MealTestData.assertMatch(service.get(MEAL_BY_USER_BREAKFAST_ID,UserTestData.USER_ID), MealTestData.getUpdated());
    }

    @Test
    public void create() {
        Meal created = service.create(MealTestData.getNew(), UserTestData.USER_ID);
        Integer newId = created.getId();
        Meal newMeal = MealTestData.getNew();
        newMeal.setId(newId);
        MealTestData.assertMatch(created, newMeal);
        MealTestData.assertMatch(service.get(newId, UserTestData.USER_ID), newMeal);
    }
}