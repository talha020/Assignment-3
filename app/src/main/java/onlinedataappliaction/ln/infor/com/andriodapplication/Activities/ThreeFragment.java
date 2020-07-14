package onlinedataappliaction.ln.infor.com.andriodapplication.Activities;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import onlinedataappliaction.ln.infor.com.andriodapplication.R;
import onlinedataappliaction.ln.infor.com.andriodapplication.adapters.CustomImageAdapter;
import onlinedataappliaction.ln.infor.com.andriodapplication.datamodels.ViewPagerImages;

/**
 * A simple {@link Fragment} subclass.
 */
public class ThreeFragment extends Fragment {
    ViewPager viewPager;
    ArrayList<ViewPagerImages> viewPagerImages = new ArrayList<>();
    CustomImageAdapter customImageAdapter;
    DetailsActivity detailsActivity;

    public ThreeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_three, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
         viewPager = (ViewPager) view.findViewById(R.id.viewPagerImages);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        detailsActivity = (DetailsActivity) getActivity();

        viewPagerImages.add(new ViewPagerImages(R.drawable.images));
        viewPagerImages.add(new ViewPagerImages(R.drawable.roses));
        customImageAdapter = new CustomImageAdapter(viewPagerImages, detailsActivity);
        viewPager.setAdapter(customImageAdapter);



    }
}
