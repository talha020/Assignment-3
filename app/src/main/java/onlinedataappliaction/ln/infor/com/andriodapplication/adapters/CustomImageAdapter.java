package onlinedataappliaction.ln.infor.com.andriodapplication.adapters;

import android.content.Context;
import android.print.PageRange;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import onlinedataappliaction.ln.infor.com.andriodapplication.R;
import onlinedataappliaction.ln.infor.com.andriodapplication.datamodels.ViewPagerImages;

public class CustomImageAdapter extends PagerAdapter {
    Context context;
    LayoutInflater layoutInflater;
    ArrayList<ViewPagerImages> imagesList = new ArrayList<>();


    public CustomImageAdapter(ArrayList<ViewPagerImages> imagesList,Context context) {
        this.imagesList = imagesList;
        this.context=context;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return imagesList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {


        return view==object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View itemView = layoutInflater.inflate(R.layout.image_detail, container, false);

        ImageView imageView = (ImageView) itemView.findViewById(R.id.images);
        imageView.setImageResource(imagesList.get(position).image);

        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        super.destroyItem(container, position, object);
        container.removeView((LinearLayout)object);
    }


}
