package com.sebaslogen.blendletje.ui.pages;

import com.sebaslogen.blendletje.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.AllOf.allOf;

/**
 * This object represents the graphical user interface for the main screen (a.k.a. page)
 *
 * Methods in this class abstract the actions a user can perform on this page
 * the flows the user can follow to other pages
 * and also the checks that can be done on this page
 */
public class MainPage {

    /**
     * The constructor checks the basic features of any instance of this page:
     * main layout, toolbar and title
     */
    public MainPage() {
        onView(withId(R.id.cl_activity_main)).check(matches(isDisplayed()));
        onView(withId(R.id.tb_toolbar)).check(matches(isDisplayed()));
        onView(allOf(isDescendantOfA(withId(R.id.tb_toolbar)),
                withText(R.string.popular_articles_title)))
                .check(matches(isDisplayed()));
    }

    public void checkLoadingAnimationIsShown() {
        onView(withId(R.id.iv_animation)).check(matches(isDisplayed()));
    }
}