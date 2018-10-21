package group30.instagram.Post;


import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

//import com.example.test.R;
import group30.com.instagramclone2.R;
import group30.instagram.Utils.PhotoFilter;

import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;


public class MainActivity extends AppCompatActivity {
    private String imgUrl;
    private Intent intent;
    ImageView imageView;
    TextView filterTypeView;
    Bitmap currentMap,iBitmap,oBitmap;
    int count=0;
    PhotoFilter photoFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intent=getIntent();
        imgUrl = intent.getStringExtra(getString(R.string.selected_image));
        filterTypeView=(TextView)findViewById(R.id.filterType);
        imageView=(ImageView)findViewById(R.id.quick_start_cropped_image);
        imageView.setImageURI(Uri.parse(imgUrl));
        photoFilter=new PhotoFilter();
        //CropImage.activity(null).setGuidelines(CropImageView.Guidelines.ON).start(this);
        try {
            iBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), Uri.fromFile(new File(imgUrl)));
            currentMap=iBitmap;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** Start pick image activity with chooser. */
    public void onCropImageClick(View view) {
        //intent=getIntent();
        //imgUrl = intent.getStringExtra(getString(R.string.selected_image));
        String currentPath=SaveImage(currentMap);
        Uri uri= Uri.fromFile(new File(currentPath));
        //Uri uri= Uri.fromFile(new File(imgUrl));
        CropImage.activity(uri).setGuidelines(CropImageView.Guidelines.ON).start(this);

    }
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void onImageViewClick(View view) {
        count++;
        switch (count) {
            case 1:
                currentMap=photoFilter.one(this, currentMap);
                imageView.setImageBitmap(currentMap);
                filterTypeView.setText("Filter 1");
                //Toast.makeText(this, "Filter 1", Toast.LENGTH_SHORT).show();
                break;
            case 2:
                currentMap=photoFilter.two(this, currentMap);
                imageView.setImageBitmap(currentMap);
                filterTypeView.setText("Filter 2");
                //Toast.makeText(this, "Filter 2", Toast.LENGTH_SHORT).show();
                break;
            case 3:
                currentMap=photoFilter.three(this, currentMap);
                imageView.setImageBitmap(currentMap);
                filterTypeView.setText("Filter 3");
                //Toast.makeText(this, "Filter 3", Toast.LENGTH_SHORT).show();
                break;
            case 4:
                imageView.setImageBitmap(currentMap);
                filterTypeView.setText("original");
        }
        if(count==4)
            count=0;
    }

    public void onUploadImageClick(View view) {
        String uploadPath=SaveImage(currentMap);
        Intent intent = new Intent(getApplicationContext(), NextActivity.class);
        intent.putExtra(getString(R.string.selected_image), uploadPath);
        startActivity(intent);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        // handle result of CropImageActivity
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri croppedUri=result.getUri();
                //String croppedPath=croppedUri.getPath().replace("file://","");
                ((ImageView) findViewById(R.id.quick_start_cropped_image)).setImageURI(croppedUri);
                Toast.makeText(
                        this, "Cropping successful, Sample: " + result.getSampleSize(), Toast.LENGTH_LONG)
                        .show();
                try {
                    //Uri.fromFile(new File(croppedUri.toString()))
                    currentMap = MediaStore.Images.Media.getBitmap(this.getContentResolver(),croppedUri );
                } catch (IOException e) {
                    e.printStackTrace();
                }


            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Toast.makeText(this, "Cropping failed: " + result.getError(), Toast.LENGTH_LONG).show();
            }
        }
    }

    private String SaveImage(Bitmap finalBitmap) {

        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/saved_images");
        myDir.mkdirs();
        Random generator = new Random();
        int n = 10000;
        n = generator.nextInt(n);
        String fname = "Image-"+ n +".jpg";
        File file = new File (myDir, fname);
        String savedPath=root+"/saved_images/"+fname;
        if (file.exists ()) file.delete ();
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();


        } catch (Exception e) {
            e.printStackTrace();
        }
        return savedPath;
    }

}
