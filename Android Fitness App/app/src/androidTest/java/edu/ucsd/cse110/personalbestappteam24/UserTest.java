package edu.ucsd.cse110.personalbestappteam24;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.test.InstrumentationRegistry;

import org.junit.Before;
import org.junit.Test;

import edu.ucsd.cse110.personalbestappteam24.user.User;

import static org.junit.Assert.*;

public class UserTest {
    Context appContext;
    SharedPreferences sharedPreferences;
    private final String TEST_EMAIL = "TEST_EMAIL";

    @Before
    public void resetSharedPreferences() {
        appContext = InstrumentationRegistry.getTargetContext();
        sharedPreferences = this.appContext.getSharedPreferences(TEST_EMAIL, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

    @Test
    public void testStepGoalSet() {
        User testUser = new User(TEST_EMAIL, appContext);

        // Test that an invalid step goal is not set.
        int invalidStepGoal = -1000;
        testUser.setStepGoal(invalidStepGoal);
        assertEquals(testUser.getStepGoal(), User.DEFAULT_STEP_GOAL);

        // Test that a valid step goal is properly set.
        int validStepGoal = 5500;
        testUser.setStepGoal(validStepGoal);
        assertEquals(testUser.getStepGoal(), validStepGoal);
    }
}
