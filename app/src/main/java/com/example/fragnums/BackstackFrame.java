package com.example.fragnums;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.SparseArray;
import android.view.View;

public class BackstackFrame implements Parcelable {

  public final static Creator<BackstackFrame> CREATOR = new Creator<BackstackFrame>() {
    @Override public BackstackFrame createFromParcel(Parcel source) {
      @Screen int screen = source.readInt();
      Bundle bundle = source.readBundle();
      SparseArray<Parcelable> viewState = bundle.getSparseParcelableArray("viewState");
      return new BackstackFrame(screen, viewState);
    }

    @Override public BackstackFrame[] newArray(int size) {
      return new BackstackFrame[size];
    }
  };

  @Screen public final int screen;

  private final SparseArray<Parcelable> viewState;

  private BackstackFrame(@Screen int screen, SparseArray<Parcelable> viewState) {
    this.screen = screen;
    this.viewState = viewState;
  }

  public BackstackFrame(@Screen int screen, View view) {
    this.screen = screen;
    viewState = new SparseArray<>();
    view.saveHierarchyState(viewState);
  }

  public void restore(View view) {
    view.restoreHierarchyState(viewState);
  }

  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel dest, int flags) {
    dest.writeInt(screen);
    Bundle bundle = new Bundle();
    bundle.putSparseParcelableArray("viewState", viewState);
    dest.writeBundle(bundle);
  }
}
