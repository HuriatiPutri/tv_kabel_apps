
package app.src.main.;

import javax.annotation.Generated;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Datum implements Parcelable
{

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("employee_id")
    @Expose
    private String employeeId;
    @SerializedName("contract_id")
    @Expose
    private String contractId;
    @SerializedName("client_id")
    @Expose
    private String clientId;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("started_at")
    @Expose
    private Object startedAt;
    @SerializedName("ended_at")
    @Expose
    private Object endedAt;
    @SerializedName("is_late")
    @Expose
    private Object isLate;
    @SerializedName("overtime_started_at")
    @Expose
    private Object overtimeStartedAt;
    @SerializedName("overtime_ended_at")
    @Expose
    private Object overtimeEndedAt;
    @SerializedName("overtime_summary")
    @Expose
    private Object overtimeSummary;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("created_by")
    @Expose
    private String createdBy;
    @SerializedName("updated_by")
    @Expose
    private String updatedBy;
    public final static Parcelable.Creator<Datum> CREATOR = new Creator<Datum>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Datum createFromParcel(Parcel in) {
            Datum instance = new Datum();
            instance.id = ((String) in.readValue((String.class.getClassLoader())));
            instance.employeeId = ((String) in.readValue((String.class.getClassLoader())));
            instance.contractId = ((String) in.readValue((String.class.getClassLoader())));
            instance.clientId = ((String) in.readValue((String.class.getClassLoader())));
            instance.date = ((String) in.readValue((String.class.getClassLoader())));
            instance.status = ((String) in.readValue((String.class.getClassLoader())));
            instance.startedAt = ((Object) in.readValue((Object.class.getClassLoader())));
            instance.endedAt = ((Object) in.readValue((Object.class.getClassLoader())));
            instance.isLate = ((Object) in.readValue((Object.class.getClassLoader())));
            instance.overtimeStartedAt = ((Object) in.readValue((Object.class.getClassLoader())));
            instance.overtimeEndedAt = ((Object) in.readValue((Object.class.getClassLoader())));
            instance.overtimeSummary = ((Object) in.readValue((Object.class.getClassLoader())));
            instance.createdAt = ((String) in.readValue((String.class.getClassLoader())));
            instance.updatedAt = ((String) in.readValue((String.class.getClassLoader())));
            instance.createdBy = ((String) in.readValue((String.class.getClassLoader())));
            instance.updatedBy = ((String) in.readValue((String.class.getClassLoader())));
            return instance;
        }

        public Datum[] newArray(int size) {
            return (new Datum[size]);
        }

    }
    ;

    /**
     * 
     * @return
     *     The id
     */
    public String getId() {
        return id;
    }

    /**
     * 
     * @param id
     *     The id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 
     * @return
     *     The employeeId
     */
    public String getEmployeeId() {
        return employeeId;
    }

    /**
     * 
     * @param employeeId
     *     The employee_id
     */
    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    /**
     * 
     * @return
     *     The contractId
     */
    public String getContractId() {
        return contractId;
    }

    /**
     * 
     * @param contractId
     *     The contract_id
     */
    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    /**
     * 
     * @return
     *     The clientId
     */
    public String getClientId() {
        return clientId;
    }

    /**
     * 
     * @param clientId
     *     The client_id
     */
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    /**
     * 
     * @return
     *     The date
     */
    public String getDate() {
        return date;
    }

    /**
     * 
     * @param date
     *     The date
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * 
     * @return
     *     The status
     */
    public String getStatus() {
        return status;
    }

    /**
     * 
     * @param status
     *     The status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * 
     * @return
     *     The startedAt
     */
    public Object getStartedAt() {
        return startedAt;
    }

    /**
     * 
     * @param startedAt
     *     The started_at
     */
    public void setStartedAt(Object startedAt) {
        this.startedAt = startedAt;
    }

    /**
     * 
     * @return
     *     The endedAt
     */
    public Object getEndedAt() {
        return endedAt;
    }

    /**
     * 
     * @param endedAt
     *     The ended_at
     */
    public void setEndedAt(Object endedAt) {
        this.endedAt = endedAt;
    }

    /**
     * 
     * @return
     *     The isLate
     */
    public Object getIsLate() {
        return isLate;
    }

    /**
     * 
     * @param isLate
     *     The is_late
     */
    public void setIsLate(Object isLate) {
        this.isLate = isLate;
    }

    /**
     * 
     * @return
     *     The overtimeStartedAt
     */
    public Object getOvertimeStartedAt() {
        return overtimeStartedAt;
    }

    /**
     * 
     * @param overtimeStartedAt
     *     The overtime_started_at
     */
    public void setOvertimeStartedAt(Object overtimeStartedAt) {
        this.overtimeStartedAt = overtimeStartedAt;
    }

    /**
     * 
     * @return
     *     The overtimeEndedAt
     */
    public Object getOvertimeEndedAt() {
        return overtimeEndedAt;
    }

    /**
     * 
     * @param overtimeEndedAt
     *     The overtime_ended_at
     */
    public void setOvertimeEndedAt(Object overtimeEndedAt) {
        this.overtimeEndedAt = overtimeEndedAt;
    }

    /**
     * 
     * @return
     *     The overtimeSummary
     */
    public Object getOvertimeSummary() {
        return overtimeSummary;
    }

    /**
     * 
     * @param overtimeSummary
     *     The overtime_summary
     */
    public void setOvertimeSummary(Object overtimeSummary) {
        this.overtimeSummary = overtimeSummary;
    }

    /**
     * 
     * @return
     *     The createdAt
     */
    public String getCreatedAt() {
        return createdAt;
    }

    /**
     * 
     * @param createdAt
     *     The created_at
     */
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * 
     * @return
     *     The updatedAt
     */
    public String getUpdatedAt() {
        return updatedAt;
    }

    /**
     * 
     * @param updatedAt
     *     The updated_at
     */
    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    /**
     * 
     * @return
     *     The createdBy
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * 
     * @param createdBy
     *     The created_by
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * 
     * @return
     *     The updatedBy
     */
    public String getUpdatedBy() {
        return updatedBy;
    }

    /**
     * 
     * @param updatedBy
     *     The updated_by
     */
    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(employeeId);
        dest.writeValue(contractId);
        dest.writeValue(clientId);
        dest.writeValue(date);
        dest.writeValue(status);
        dest.writeValue(startedAt);
        dest.writeValue(endedAt);
        dest.writeValue(isLate);
        dest.writeValue(overtimeStartedAt);
        dest.writeValue(overtimeEndedAt);
        dest.writeValue(overtimeSummary);
        dest.writeValue(createdAt);
        dest.writeValue(updatedAt);
        dest.writeValue(createdBy);
        dest.writeValue(updatedBy);
    }

    public int describeContents() {
        return  0;
    }

}
