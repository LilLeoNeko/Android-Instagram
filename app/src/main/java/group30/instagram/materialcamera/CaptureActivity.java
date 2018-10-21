package group30.instagram.materialcamera;

import android.app.Fragment;
import android.support.annotation.NonNull;

import group30.instagram.materialcamera.internal.BaseCaptureActivity;
import group30.instagram.materialcamera.internal.CameraFragment;

public class CaptureActivity extends BaseCaptureActivity {

  @Override
  @NonNull
  public Fragment getFragment() {
    return CameraFragment.newInstance();
  }
}
