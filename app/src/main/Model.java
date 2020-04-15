
package app.src.main.;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import javax.validation.Valid;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Model implements Parcelable
{

    @SerializedName("data")
    @Expose
    @Valid
    private List<Datum> data = new ArrayList<Datum>();
    public final static Parcelable.Creator<Model> CREATOR = new Creator<Model>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Model createFromParcel(Parcel in) {
            Model instance = new Model();
            in.readList(instance.data, (app.src.main..Datum.class.getClassLoader()));
            return instance;
        }

        public Model[] newArray(int size) {
            return (new Model[size]);
        }

    }
    ;

    /**
     * 
     * @return
     *     The data
     */
    public List<Datum> getData() {
        return data;
    }

    /**
     * 
     * @param data
     *     The data
     */
    public void setData(List<Datum> data) {
        this.data = data;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(data);
    }

    public int describeContents() {
        return  0;
    }

}
