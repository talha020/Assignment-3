package onlinedataappliaction.ln.infor.com.andriodapplication.Activities;


import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

import onlinedataappliaction.ln.infor.com.andriodapplication.BuildConfig;
import onlinedataappliaction.ln.infor.com.andriodapplication.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class OneFragment extends Fragment implements View.OnClickListener {
    Button pdfbutton;

    DetailsActivity detailsActivity;

    public OneFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_one, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        pdfbutton = (Button) view.findViewById(R.id.oneFragment_openPdf);


    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        pdfbutton.setOnClickListener(this);

       detailsActivity= (DetailsActivity) getActivity();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.oneFragment_openPdf:
                File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/os.pdf");
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.fromFile(file), "application/pdf");
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);

               /* try {
                    openPDF();
                } catch (IOException e) {
                    e.printStackTrace();
                }
*/
                break;

        }
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String PathHolder = data.getData().getPath();
        Toast.makeText(detailsActivity, PathHolder, Toast.LENGTH_LONG).show();

    }

    private void openPDF() throws IOException{
        File file = new File(detailsActivity.getFilesDir(), "os.pdf");
        if(!file.exists()){
            try {
                file.createNewFile();
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                AssetManager assetManager = detailsActivity.getAssets();
                InputStream inputStream = assetManager.open("os.pdf");
                byte[] bytes = new byte[1024];
                int read = inputStream.read(bytes, 0, bytes.length);
                while (read != -1) {
                    fileOutputStream.write(bytes, 0, bytes.length);
                    read = inputStream.read(bytes, 0, bytes.length);
                }

                inputStream.close();
                fileOutputStream.flush();
                fileOutputStream.close();
                openFile(file.getName());
            }catch (Exception e){
                e.printStackTrace();
            }
        }else{
            openFile(file.getName());
        }

    }

    private void openFile(String name) {
        File file=new File(name);
        Intent  intent=new Intent(Intent.ACTION_VIEW);


        Uri uriForFile = FileProvider.getUriForFile(detailsActivity, BuildConfig.APPLICATION_ID + ".provider", file);
        intent.setDataAndType(uriForFile,"application/pdf");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        Intent chooser = Intent.createChooser(intent, "Open file");
        startActivity(chooser);

    }
    private void openDoc(File url){

        Uri uri=Uri.fromFile(url);
        Intent docIntent=new Intent(Intent.ACTION_VIEW);
        if(url.toString().contains("doc")||url.toString().contains("docx")){
            docIntent.setDataAndType(uri,"application/msword");
            }else if (url.toString().contains(".pdf")) {
            // PDF file
            docIntent.setDataAndType(uri, "application/pdf");
        } else if (url.toString().contains(".ppt") || url.toString().contains(".pptx")) {
            // Powerpoint file
            docIntent.setDataAndType(uri, "application/vnd.ms-powerpoint");
        } else if (url.toString().contains(".xls") || url.toString().contains(".xlsx")) {
            // Excel file
            docIntent.setDataAndType(uri, "application/vnd.ms-excel");
        } else if (url.toString().contains(".zip") || url.toString().contains(".rar")) {
            // WAV audio file
            docIntent.setDataAndType(uri, "application/x-wav");
        } else if (url.toString().contains(".rtf")) {
            // RTF file
            docIntent.setDataAndType(uri, "application/rtf");
        } else if (url.toString().contains(".wav") || url.toString().contains(".mp3")) {
            // WAV audio file
            docIntent.setDataAndType(uri, "audio/x-wav");
        } else if (url.toString().contains(".gif")) {
            // GIF file
            docIntent.setDataAndType(uri, "image/gif");
        } else if (url.toString().contains(".jpg") || url.toString().contains(".jpeg") || url.toString().contains(".png")) {
            // JPG file
            docIntent.setDataAndType(uri, "image/jpeg");
        } else if (url.toString().contains(".txt")) {
            // Text file
            docIntent.setDataAndType(uri, "text/plain");
        } else if (url.toString().contains(".3gp") || url.toString().contains(".mpg") || url.toString().contains(".mpeg") || url.toString().contains(".mpe") || url.toString().contains(".mp4") || url.toString().contains(".avi")) {
            // Video files
            docIntent.setDataAndType(uri, "video/*");
        } else {
            //if you want you can also define the intent type for any other file
            //additionally use else clause below, to manage other unknown extensions
            //in this case, Android will show all applications installed on the device
            //so you can choose which application to use
            docIntent.setDataAndType(uri, "*/*");
        }
        docIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(docIntent);

    }
}
