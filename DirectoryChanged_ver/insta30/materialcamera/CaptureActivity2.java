package group30.com.instagramclone2.materialcamera;

import android.app.Fragment;
import android.support.annotation.NonNull;

import group30.com.instagramclone2.materialcamera.internal.BaseCaptureActivity;
import group30.com.instagramclone2.materialcamera.internal.Camera2Fragment;


public class CaptureActivity2 extends BaseCaptureActivity {

  @Override
  @NonNull
  public Fragment getFragment() {
    return Camera2Fragment.newInstance();
  }
}
