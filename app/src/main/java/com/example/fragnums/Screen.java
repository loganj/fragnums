package com.example.fragnums;

import android.support.annotation.IntDef;
import com.squareup.enumsbatter.R;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@IntDef({ Screen.MAIN, Screen.ENUMSMATTER, Screen.T_SHIRT }) @Retention(RetentionPolicy.SOURCE)
public @interface Screen {
  int MAIN = R.layout.main;
  int ENUMSMATTER = R.layout.enumsmatter;
  int T_SHIRT = R.layout.tshirt;
}
