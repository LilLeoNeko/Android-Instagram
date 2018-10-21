package group30.instagram.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicColorMatrix;
import android.support.annotation.RequiresApi;



public class PhotoFilter {

    RenderScript renderScript;
    Allocation inputAllocation,outputAllocation;
    Bitmap outBitmap;

    //filter1
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public Bitmap one(Context context, Bitmap bitmap){
        renderScript=RenderScript.create(context);
        outBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());
        inputAllocation=Allocation.createFromBitmap(renderScript,bitmap);
        outputAllocation=Allocation.createTyped(renderScript,inputAllocation.getType());
        final ScriptIntrinsicColorMatrix colorMatrix1=ScriptIntrinsicColorMatrix.create(renderScript, Element.U8_4(renderScript));
        colorMatrix1.setColorMatrix(new android.renderscript.Matrix4f(new float[]
                {
                        -0.33f, -0.33f, -0.33f, 1.0f,
                        -0.59f, -0.59f, -0.59f, 1.0f,
                        -0.11f, -0.11f, -0.11f, 1.0f,
                        1.0f, 1.0f, 1.0f, 1.0f
                }));
        colorMatrix1.forEach(inputAllocation,outputAllocation);
        outputAllocation.copyTo(outBitmap);
        return outBitmap;
    }

    //filter2
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public Bitmap two(Context context, Bitmap bitmap){
        renderScript=RenderScript.create(context);
        outBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());
        inputAllocation=Allocation.createFromBitmap(renderScript,bitmap);
        outputAllocation=Allocation.createTyped(renderScript,inputAllocation.getType());
        final ScriptIntrinsicColorMatrix colorMatrix2=ScriptIntrinsicColorMatrix.create(renderScript,Element.U8_4(renderScript));
        colorMatrix2.setGreyscale();
        colorMatrix2.forEach(inputAllocation,outputAllocation);
        outputAllocation.copyTo(outBitmap);
        return outBitmap;
    }

    //filter3
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public Bitmap three(Context context, Bitmap bitmap){
        renderScript=RenderScript.create(context);
        outBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());
        inputAllocation=Allocation.createFromBitmap(renderScript,bitmap);
        outputAllocation=Allocation.createTyped(renderScript,inputAllocation.getType());
        final ScriptIntrinsicColorMatrix colorMatrix3 = ScriptIntrinsicColorMatrix.create(renderScript, Element.U8_4(renderScript));
        colorMatrix3.setColorMatrix(new android.renderscript.Matrix4f(new float[]
                {
                        0f, 0f, 0f, 0f,
                        0f, 0.78f, 0f, 0f,
                        0f, 0f, 1f, 0f,
                        0f, 0f, 0f, 1f,
                }));
        colorMatrix3.forEach(inputAllocation, outputAllocation);
        outputAllocation.copyTo(outBitmap);
        return outBitmap;
    }
}
