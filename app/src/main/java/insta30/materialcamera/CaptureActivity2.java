package insta30.materialcamera;

import android.app.Fragment;
import android.support.annotation.NonNull;

import insta30.materialcamera.internal.BaseCaptureActivity;
import insta30.materialcamera.internal.Camera2Fragment;


public class CaptureActivity2 extends BaseCaptureActivity {

  @Override
  @NonNull
  public Fragment getFragment() {
    return Camera2Fragment.newInstance();
  }
}
