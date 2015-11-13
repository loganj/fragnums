package com.example.fragnums;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.squareup.enumsbatter.R;

import static android.content.Intent.ACTION_VIEW;

public final class Screens {

  public static void bind(@Screen @LayoutRes int screen, View view) {
    switch (screen) {
      case Screen.MAIN:
        view.findViewById(R.id.enumsmatter).setOnClickListener(new View.OnClickListener() {
          @Override public void onClick(View v) {
            goTo(Screen.ENUMSMATTER, v);
          }
        });
        view.findViewById(R.id.tshirt).setOnClickListener(new View.OnClickListener() {
          @Override public void onClick(View v) {
            goTo(Screen.T_SHIRT, v);
          }
        });
        break;
      case Screen.ENUMSMATTER:
        break;
      case Screen.T_SHIRT:
        view.findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
          @Override public void onClick(View view) {
            Intent intent = new Intent(ACTION_VIEW, Uri.parse("https://teespring.com/enumsmatter"));
            view.getContext().startActivity(intent);
          }
        });
        break;
    }
  }

  @Nullable @StringRes public static Integer getTitle(@Screen int screen) {
    switch(screen) {
      case Screen.ENUMSMATTER:
        return R.string.enumsmatter_title;
      case Screen.T_SHIRT:
        return R.string.tshirt_title;
      case Screen.MAIN:
      default:
        return null;
    }
  }

  public static View inflate(@Screen @LayoutRes int screen, ViewGroup container) {
    Context context = container.getContext();
    return LayoutInflater.from(context).inflate(screen, container, false);
  }

  private static void goTo(@Screen int screen, View view) {
    MainActivity activity = (MainActivity) view.getContext();
    activity.goTo(screen);
  }
}
