package com.assignmet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, UserListAdapter.ClickListener {
    Activity mContext;
    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int TAKE_PICTURE =  2;
    TextView title_toolbar,name_tv,age_tv,gender_tv,hobbies_tv;
    ImageView back_btn_iv;
    Button sign_up_btn,sign_in_btn;
    String convertedImage="";
    String imagePath="";
    Uri imageUri1 = null;
    RecyclerView user_list_rv;
    CircleImageView profile_image;
    DatabaseHelper databaseHelper;
    LinearLayout sign_up_ll,edit_image_icon,sign_in_ll,user_list_ll,users_details_ll;
    TextInputEditText name_et,age_et,gender_et,hobies_et,dob_et,username_et;
    List<User> userList,users_list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext=this;
        userList=new ArrayList<>();
        users_list=new ArrayList<>();
        databaseHelper=new DatabaseHelper(mContext);
        initViews();
    }

    private void initViews() {
        title_toolbar=findViewById(R.id.titleToolbar);
        users_details_ll=findViewById(R.id.users_details_ll);
        name_tv=findViewById(R.id.name_tv);
        age_tv=findViewById(R.id.age_tv);
        gender_tv=findViewById(R.id.gender_tv);
        hobbies_tv=findViewById(R.id.hobbies_tv);
        back_btn_iv=findViewById(R.id.back_btn_iv);
        title_toolbar.setText("Sign Up User");

        sign_up_ll=findViewById(R.id.sign_up_ll);
        user_list_rv=findViewById(R.id.user_list_rv);
        user_list_ll=findViewById(R.id.user_list_ll);

        username_et=findViewById(R.id.username_et);
        name_et=findViewById(R.id.name_et);
        age_et=findViewById(R.id.age_et);
        gender_et=findViewById(R.id.gender_et);
        hobies_et=findViewById(R.id.hobbies_et);
        dob_et=findViewById(R.id.dob_et);
        edit_image_icon=findViewById(R.id.edit_image_icon);
        sign_up_btn=findViewById(R.id.sign_up_btn);
        sign_in_ll=findViewById(R.id.sign_in_ll);
        sign_in_btn=findViewById(R.id.sign_in_btn);
        profile_image=findViewById(R.id.profile_image);
        edit_image_icon.setOnClickListener(this);
        sign_up_btn.setOnClickListener(this);
        sign_in_btn.setOnClickListener(this);


    }
    private boolean validateFields() {
        boolean validate = true;
        String message = "";
        if(name_et.getText().toString().trim().equals(""))
        {
            message = "Name is empty";
            validate =false;
        }
        else if(age_et.getText().toString().trim().equals(""))
        {
            message = "Age is empty";
            validate =false;
        } else if(hobies_et.getText().toString().trim().equals(""))
        {
            message = "Hobbies is empty";
            validate =false;
        }else if(gender_et.getText().toString().trim().equals(""))
        {
            message = "Gender is empty";
            validate =false;
        }else if(dob_et.getText().toString().trim().equals(""))
        {
            message = "Date of birth is empty";
            validate =false;
        }
        if(!validate)
        {
            CustomDialog.statusDialog(Constants.ERROR_DIALOG,message,mContext,null);
        }

        return validate;
    }
    private void openDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.custom_dialog_box);
        dialog.setTitle(mContext.getResources().getString(R.string.select_image_from));
        // That new View is just there to have something inside the dialog that can grow big enough to cover the whole screen.)
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        Button btnExit = (Button) dialog.findViewById(R.id.cancel);
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.Gallery)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        if(Utility.checkExternalStoragePermission(mContext))
                        {
                            goToAttachmentActivity();
                        }else
                        {
                            Utility.readExPermissionDialog(mContext);
                        }
                    }
                });
        dialog.findViewById(R.id.Camera)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        if(Utility.checkCameraPermission(mContext))
                        {
                            goToCameraActivity("image1");
                        }else
                        {
                            Utility.cameraPermissionDialog(mContext);
                        }
                    }
                });
        // show dialog on screen
        dialog.show();
        dialog.getWindow().setAttributes(lp);

    }
    private void goToAttachmentActivity(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }
    private void goToCameraActivity(String imageName){

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        Intent camintent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File photo = new File(Environment.getExternalStorageDirectory(),  imageName+".jpg");
        camintent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photo));
        imageUri1 = Uri.fromFile(photo);

        startActivityForResult(camintent, TAKE_PICTURE);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case TAKE_PICTURE:
                Uri selectedImage = null;
                if (resultCode == mContext.RESULT_OK) {
                    selectedImage = null;
                    Bitmap cameraBitmap = null;
                    selectedImage = imageUri1;
                    cameraBitmap = compressImage(selectedImage.toString());
                    profile_image.setImageBitmap(cameraBitmap);
                    //  show_image.setScaleType(ImageView.ScaleType.FIT_CENTER);
                }
                // file1= new File(selectedImage.getPath());
                imagePath = selectedImage.getPath();
                convertedImage = Utility.convertImageBase64(imagePath);
                break;
            case PICK_IMAGE_REQUEST:
                try {
                    Uri selectedImageURI = data.getData();
                    final Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageURI);
                    profile_image.setImageBitmap(bitmap);

                    //file1= new File(Utility.getRealPathFromURI_API19(selectedImageURI,mContext));
                    imagePath = Utility.getRealPathFromURI_API19(selectedImageURI, mContext);

                    convertedImage = Utility.convertImageBase64(imagePath);

                } catch (Exception e) {
                    Utility.toastShort(mContext, mContext.getResources().getString(R.string.canceled));
                }
                break;

        }
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == Constants.CAMERA_PERMISSION_CODE && grantResults[0] == PackageManager.PERMISSION_GRANTED)
        {
            goToCameraActivity("image1");
        }else if(requestCode == Constants.READ_EXTERNAL_STORAGE && grantResults[0] == PackageManager.PERMISSION_GRANTED)
        {
            goToAttachmentActivity();
        }
    }
    public Bitmap compressImage(String imageUri) {

        String filePath = getRealPathFromURI(imageUri);
        Bitmap scaledBitmap = null;

        BitmapFactory.Options options = new BitmapFactory.Options();

//      by setting this field as true, the actual bitmap pixels are not loaded in the memory. Just the bounds are loaded. If
//      you try the use the bitmap here, you will get null.
        options.inJustDecodeBounds = true;
        Bitmap bmp = BitmapFactory.decodeFile(filePath, options);

        int actualHeight = options.outHeight;
        int actualWidth = options.outWidth;

//      max Height and width values of the compressed image is taken as 816x612

        float maxHeight = 816.0f;
        float maxWidth = 612.0f;
        float imgRatio = actualWidth / actualHeight;
        float maxRatio = maxWidth / maxHeight;

//      width and height values are set maintaining the aspect ratio of the image

        if (actualHeight > maxHeight || actualWidth > maxWidth) {
            if (imgRatio < maxRatio) {               imgRatio = maxHeight / actualHeight;                actualWidth = (int) (imgRatio * actualWidth);               actualHeight = (int) maxHeight;             } else if (imgRatio > maxRatio) {
                imgRatio = maxWidth / actualWidth;
                actualHeight = (int) (imgRatio * actualHeight);
                actualWidth = (int) maxWidth;
            } else {
                actualHeight = (int) maxHeight;
                actualWidth = (int) maxWidth;

            }
        }

//      setting inSampleSize value allows to load a scaled down version of the original image

        options.inSampleSize = calculateInSampleSize(options, actualWidth, actualHeight);

//      inJustDecodeBounds set to false to load the actual bitmap
        options.inJustDecodeBounds = false;

//      this options allow android to claim the bitmap memory if it runs low on memory
        options.inPurgeable = true;
        options.inInputShareable = true;
        options.inTempStorage = new byte[16 * 1024];

        try {
//          load the bitmap from its path
            bmp = BitmapFactory.decodeFile(filePath, options);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();

        }
        try {
            scaledBitmap = Bitmap.createBitmap(actualWidth, actualHeight, Bitmap.Config.ARGB_8888);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();
        }

        float ratioX = actualWidth / (float) options.outWidth;
        float ratioY = actualHeight / (float) options.outHeight;
        float middleX = actualWidth / 2.0f;
        float middleY = actualHeight / 2.0f;

        Matrix scaleMatrix = new Matrix();
        scaleMatrix.setScale(ratioX, ratioY, middleX, middleY);

        Canvas canvas = new Canvas(scaledBitmap);
        canvas.setMatrix(scaleMatrix);
        canvas.drawBitmap(bmp, middleX - bmp.getWidth() / 2, middleY - bmp.getHeight() / 2, new Paint(Paint.FILTER_BITMAP_FLAG));

//      check the rotation of the image and display it properly
        ExifInterface exif;
        try {
            exif = new ExifInterface(filePath);

            int orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION, 0);
            Log.d("EXIF", "Exif: " + orientation);
            Matrix matrix = new Matrix();
            if (orientation == 6) {
                matrix.postRotate(90);
                Log.d("EXIF", "Exif: " + orientation);
            } else if (orientation == 3) {
                matrix.postRotate(180);
                Log.d("EXIF", "Exif: " + orientation);
            } else if (orientation == 8) {
                matrix.postRotate(270);
                Log.d("EXIF", "Exif: " + orientation);
            }
            scaledBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0,
                    scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix,
                    true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return scaledBitmap;

    }
    private String getRealPathFromURI(String contentURI) {
        Uri contentUri = Uri.parse(contentURI);
        Cursor cursor = getContentResolver().query(contentUri, null, null, null, null);
        if (cursor == null) {
            return contentUri.getPath();
        } else {
            cursor.moveToFirst();
            int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(index);
        }
    }
    public int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height/ (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;      }

        final float totalPixels = width * height;
        final float totalReqPixelsCap = reqWidth * reqHeight * 2;
        while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
            inSampleSize++;
        }

        return inSampleSize;
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.edit_image_icon){
            openDialog();
        }else if(v.getId()==R.id.sign_up_btn){
           if(validateFields()){
               SignupUser();
           }
        }else if(v.getId()==R.id.sign_in_btn){
               SignInUser();
        }

    }
private void  SignInUser() {
    users_list = databaseHelper.getUsersList();
    if (users_list.size() > 0) {
        if (sign_in_ll.getVisibility() == View.VISIBLE) {
            sign_in_ll.setVisibility(View.GONE);
            sign_up_ll.setVisibility(View.GONE);
            user_list_ll.setVisibility(View.VISIBLE);
            UserListAdapter adapter = new UserListAdapter(mContext, users_list);
            user_list_rv.setLayoutManager(new LinearLayoutManager(mContext));
            user_list_rv.setAdapter(adapter);
            adapter.setClickListener(MainActivity.this);
        }
    }
}

    private  void SignupUser(){

        User user=new User();
        user.setName(name_et.getText().toString().trim());
        user.setAge(age_et.getText().toString().trim());
        user.setGender(gender_et.getText().toString().trim());
        user.setHobbies(hobies_et.getText().toString().trim());
        user.setDOB(dob_et.getText().toString().trim());
        //  signInUser(user);
     //   userList.add(0,user);
        boolean isInserted=databaseHelper.addNewUser(user);
        if(isInserted){
            Utility.toastShort(mContext,"Sign up successfully");
            sign_up_ll.setVisibility(View.GONE);
            sign_in_ll.setVisibility(View.VISIBLE);

           }


        }

    @Override
    public void itemClicked(View v, int position) {
        user_list_ll.setVisibility(View.GONE);
        users_details_ll.setVisibility(View.VISIBLE);
        name_tv.setText(users_list.get(position).getName());
        age_tv.setText(users_list.get(position).getAge());
        gender_tv.setText(users_list.get(position).getGender());
        hobbies_tv.setText(users_list.get(position).getHobbies());

    }

}
