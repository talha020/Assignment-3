package onlinedataappliaction.ln.infor.com.andriodapplication.datamodels;

public class SampleList {
    public String fullName;
   public String companyName;
  public String country;

    public SampleList(String fullName, String companyName, String country) {
        this.fullName=fullName;
        this.companyName=companyName;
        this.country=country;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
