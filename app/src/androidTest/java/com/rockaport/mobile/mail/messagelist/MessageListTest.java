package com.rockaport.mobile.mail.messagelist;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.Intents;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;

import com.rockaport.mobile.mail.R;
import com.rockaport.mobile.mail.composemessage.ComposeMessageActivity;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class MessageListTest {
    @Rule
    public ActivityTestRule<MessageListActivity> messageListActivityTestRule =
            new ActivityTestRule<>(MessageListActivity.class);

    @Test
    public void clickFabLaunchComposeMessageActivity() {
        Intents.init();

        onView(withId(R.id.floating_action_button)).perform(click());
        intended(hasComponent(ComposeMessageActivity.class.getName()));

        Intents.release();
    }

    @Test
    public void swipeLeftToDelete() {
        onView(withId(R.id.recycler_view))
                .perform(RecyclerViewActions.scrollToHolder(new TypeSafeMatcher<RecyclerView.ViewHolder>() {
                    @Override
                    protected boolean matchesSafely(RecyclerView.ViewHolder item) {
                        return item.getAdapterPosition() == 0;
                    }

                    @Override
                    public void describeTo(Description description) {
                        description.appendText("item at the beinning");
                    }
                }));

        onView(withId(R.id.recycler_view))
                .perform(swipeLeft());
    }
}
