package group30.com.instagramclone2.materialcamera;

import android.app.Fragment;
import android.support.annotation.NonNull;

import group30.com.instagramclone2.materialcamera.internal.BaseCaptureActivity;
import group30.com.instagramclone2.materialcamera.internal.CameraFragment;

public class CaptureActivity extends BaseCaptureActivity {

  @Override
  @NonNull
  public Fragment getFragment() {
    return CameraFragment.newInstance();
  }
}
