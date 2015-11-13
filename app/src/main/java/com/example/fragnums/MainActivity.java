package com.example.fragnums;

import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import com.squareup.enumsbatter.R;
import java.util.ArrayList;

import static android.view.animation.AnimationUtils.loadAnimation;

public final class MainActivity extends AppCompatActivity {

  ArrayList<BackstackFrame> backstack;
  @Screen int currentScreen;

  FrameLayout container;
  View currentView;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    container = (FrameLayout) findViewById(R.id.main_container);
    if (savedInstanceState == null) {
      currentScreen = Screen.MAIN;
      backstack = new ArrayList<>();
    } else {
      //noinspection ResourceType
      currentScreen = savedInstanceState.getInt("currentScreen");
      backstack = savedInstanceState.getParcelableArrayList("backstack");
    }
    currentView = Screens.inflate(currentScreen, container);
    container.addView(currentView);
    Screens.bind(currentScreen, currentView);
    updateActionBar();
  }

  @Override public void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    outState.putInt("currentScreen", currentScreen);
    outState.putParcelableArrayList("backstack", backstack);
  }

  @Override public void onBackPressed() {
    if (backstack.size() > 0) {
      goBack();
      return;
    }
    super.onBackPressed();
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home:
        goBack();
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }

  public void goTo(@Screen int screen) {
    currentView.startAnimation(loadAnimation(this, R.anim.exit_forward));
    container.removeView(currentView);
    BackstackFrame backstackFrame = new BackstackFrame(currentScreen, currentView);
    backstack.add(backstackFrame);

    currentScreen = screen;
    currentView = Screens.inflate(currentScreen, container);
    currentView.startAnimation(loadAnimation(this, R.anim.enter_forward));
    container.addView(currentView);
    Screens.bind(currentScreen, currentView);

    updateActionBar();
  }

  public void goBack() {
    currentView.startAnimation(loadAnimation(this, R.anim.exit_backward));
    container.removeView(currentView);

    BackstackFrame latest = backstack.remove(backstack.size() - 1);
    currentScreen = latest.screen;
    currentView = Screens.inflate(currentScreen, container);
    currentView.startAnimation(loadAnimation(this, R.anim.enter_backward));
    container.addView(currentView, 0);
    latest.restore(currentView);
    Screens.bind(currentScreen, currentView);

    updateActionBar();
  }

  private void updateActionBar() {
    ActionBar actionBar = getSupportActionBar();
    actionBar.setDisplayHomeAsUpEnabled(backstack.size() != 0);
    @StringRes Integer title = Screens.getTitle(currentScreen);
    if (title == null) {
      actionBar.setTitle(getTitle());
    } else {
      actionBar.setTitle(title);
    }
  }
}
