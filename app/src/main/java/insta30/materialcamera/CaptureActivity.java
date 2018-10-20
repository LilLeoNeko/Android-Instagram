package insta30.materialcamera;

import android.app.Fragment;
import android.support.annotation.NonNull;

import insta30.materialcamera.internal.BaseCaptureActivity;
import insta30.materialcamera.internal.CameraFragment;

public class CaptureActivity extends BaseCaptureActivity {

  @Override
  @NonNull
  public Fragment getFragment() {
    return CameraFragment.newInstance();
  }
}
