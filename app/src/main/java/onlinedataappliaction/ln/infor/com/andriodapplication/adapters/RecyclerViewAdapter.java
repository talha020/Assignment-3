package onlinedataappliaction.ln.infor.com.andriodapplication.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import onlinedataappliaction.ln.infor.com.andriodapplication.R;
import onlinedataappliaction.ln.infor.com.andriodapplication.datamodels.Model;

public class RecyclerViewAdapter extends RecyclerView.Adapter {

    ArrayList<Model> modelArrayList = new ArrayList<>();
    private static  OnRecyclerItemClickListener onItemClickListener=null;
    private LayoutInflater layoutInflater;
    Context context;

    public RecyclerViewAdapter(ArrayList<Model> modelArrayList, Context context,OnRecyclerItemClickListener itemClickListener) {
        this.modelArrayList = modelArrayList;
        this.context = context;
        this.onItemClickListener=itemClickListener;

    }
  /* public RecyclerViewAdapter(ArrayList<Model> modelArrayList){
       this.modelArrayList = modelArrayList;
   }*/

    public static class ModelViewHolder extends RecyclerView.ViewHolder {
        TextView name;

        public ModelViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.text_cardview);

        }
    }

    public static class ImageViewHolder extends RecyclerView.ViewHolder {
        TextView description;
        TextView title;
        //TextView time;
        ImageView photo;

        public ImageViewHolder(View itemView) {
            super(itemView);
          title=  (TextView) itemView.findViewById(R.id.image_title);
            description = (TextView) itemView.findViewById(R.id.image_desc);
           // time=(TextView) itemView.findViewById(R.id.time_des);
            photo = (ImageView) itemView.findViewById(R.id.image_cardview);

        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup viewGroup, final int viewType) {
        View view = null;
        switch (viewType) {
            case Model.TEXT_TYPE:
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.text, viewGroup, false);
                return new ModelViewHolder(view);

            case Model.IMAGE_TYPE:
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.image, viewGroup, false);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onItemClickListener.OnItemClick(0);
                        }
                });
                return new ImageViewHolder(view);

        }

        return null;
    }


  /*  public void setOnItemClickListener(OnRecyclerItemClickListener recyclerItemClickListener){
        this.onItemClickListener=recyclerItemClickListener;
}*/

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Model object = modelArrayList.get(position);
        if (object != null) {
            switch (object.type) {
                case Model.TEXT_TYPE:
                    ((ModelViewHolder) holder).name.setText(object.data);

                    break;
                case Model.IMAGE_TYPE:
                    ((ImageViewHolder)holder).title.setText(object.data);
                    ((ImageViewHolder) holder).description.setText(object.dataDesc);
                    ((ImageViewHolder) holder).photo.setImageResource(object.image);


                    break;
            }
        }

    }

    @Override
    public int getItemCount() {
        return modelArrayList.size();
    }

    @Override
    public int getItemViewType(int position) {

        switch (modelArrayList.get(position).type) {
            case 0:
                return Model.IMAGE_TYPE;
            case 1:
                return Model.TEXT_TYPE;
        }
        return 0;
    }

}
