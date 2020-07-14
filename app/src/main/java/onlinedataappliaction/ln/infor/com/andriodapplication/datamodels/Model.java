package onlinedataappliaction.ln.infor.com.andriodapplication.datamodels;

public class Model {
    public static final int IMAGE_TYPE = 0;
    public static final int TEXT_TYPE  = 1;

    public String data;
    public String dataDesc;
   // public String time;
    public int image;
    public int type;

    public int getType() {
        return type;
    }

    public Model(String data, int image, int type,String dataDesc/*,String time*/) {
        this.data = data;
        this.image = image;
        this.type=type;
        this.dataDesc=dataDesc;
       // this.time=time;

    }

    public String getData() {
        return data;
    }

    public int getImage() {
        return image;
    }


    public String getDataDesc() {
        return dataDesc;
    }

    /*public String getTime() {
        return time;
    }*/
}
