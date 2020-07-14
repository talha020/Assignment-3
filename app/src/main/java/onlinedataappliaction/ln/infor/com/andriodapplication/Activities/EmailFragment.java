package onlinedataappliaction.ln.infor.com.andriodapplication.Activities;


import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

import onlinedataappliaction.ln.infor.com.andriodapplication.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class EmailFragment extends Fragment {


    private TextView websiteText;
    TextView youTubeLink;
    DetailsActivity detailsActivity;
    ImageView thumbNailImage;

    public EmailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_email, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        websiteText = (TextView) view.findViewById(R.id.text_website);
        youTubeLink = (TextView) view.findViewById(R.id.text_youtubeUrl);
        thumbNailImage = (ImageView) view.findViewById(R.id.imageView);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        detailsActivity = (DetailsActivity) getActivity();

        websiteText.setClickable(true);
        websiteText.setMovementMethod(LinkMovementMethod.getInstance());
        websiteText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://wiki.infor.com/confluence/display/InforERPLNApps/Infor+LN+-+Apps#HomePageWidgets"));
                startActivity(intent);
            }
        });
        youTubeLink.setClickable(true);
        youTubeLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                watchYoutubeVideo(detailsActivity, "https://www.youtube.com/watch?v=W5WM1t00uyw");
            }
        });


        File extDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File file = new File(extDir, "");
        String fName = System.currentTimeMillis() + "";
        File compressedFile = new File(file, fName + ".jpeg");

        final int THUMBSIZE = 64;

        Bitmap ThumbImage = ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(fName), THUMBSIZE, THUMBSIZE);
        thumbNailImage.setImageBitmap(ThumbImage);
    }

    public static void watchYoutubeVideo(Context context, String url) {

        String id = url.substring(url.lastIndexOf("=") + 1, url.length());
        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + id));
        Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        try {
            context.startActivity(appIntent);
        } catch (ActivityNotFoundException ex) {
            context.startActivity(webIntent);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.email, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.email_menu:

                sendEmail();
                break;


        }
        return super.onOptionsItemSelected(item);

    }

    private void sendEmail() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("*/");
        intent.putExtra(Intent.EXTRA_SUBJECT, "EMAIL SENDING");
        intent.putExtra(Intent.EXTRA_TITLE, "Email_title");
        startActivity(Intent.createChooser(intent, "Send"));

    }
}
