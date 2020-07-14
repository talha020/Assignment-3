package onlinedataappliaction.ln.infor.com.andriodapplication.Activities;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import java.util.ArrayList;

import onlinedataappliaction.ln.infor.com.andriodapplication.R;
import onlinedataappliaction.ln.infor.com.andriodapplication.adapters.CustomImageAdapter;
import onlinedataappliaction.ln.infor.com.andriodapplication.datamodels.ViewPagerImages;

/**
 * A simple {@link Fragment} subclass.
 */
public class TwoFragment extends Fragment implements View.OnClickListener {

    ImageView previous;
    ImageView next;
    ImageSwitcher imageSwitcher;
    DetailsActivity detailsActivity;
    ImageView imageView;
    int currentIndex = -1;
    public int imageIds[];
    int count;


    public TwoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_two, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        previous = (ImageView) view.findViewById(R.id.previous_button);
        next = (ImageView) view.findViewById(R.id.next_button);
        imageSwitcher = (ImageSwitcher) view.findViewById(R.id.image_switcher);


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        detailsActivity = (DetailsActivity) getActivity();

        imageIds = new int[]{R.drawable.images, R.drawable.roses, R.drawable.lotus, R.drawable.flowers};
        count = imageIds.length;
        imageSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                imageView = new ImageView(detailsActivity.getApplicationContext());
                // set Scale type of ImageView to Fit Center
                imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                // set the Height And Width of ImageView To FIll PARENT
                imageView.setLayoutParams(new ImageSwitcher.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
                return imageView;

            }
        });
        imageSwitcher.setBackgroundResource(imageIds[0]);

        next.setVisibility(View.VISIBLE);

        next.setOnClickListener(this);
        previous.setOnClickListener(this);


    }




    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.previous_button:
                if (currentIndex > 0) {
                    currentIndex--;
                    imageSwitcher.setBackgroundResource(imageIds[currentIndex]);
                    Log.d("ImageIndex", String.valueOf(currentIndex));
                    next.setVisibility(View.VISIBLE);
                }else {
                    previous.setVisibility(View.GONE);
                }
                break;
            case R.id.next_button:
                if (currentIndex < count - 1) {
                    currentIndex++;
                    imageSwitcher.setBackgroundResource(imageIds[currentIndex]);
                    Log.d("ImageIndex", String.valueOf(currentIndex));
                    previous.setVisibility(View.VISIBLE);
                } else {
                    next.setVisibility(View.GONE);
                }
                break;


        }
    }

}
