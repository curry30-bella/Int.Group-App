package edu.northeastern.numad23sp_zihaoluo_shizhong_zhenliu_project;

import android.os.Parcel;
import android.os.Parcelable;

public class GroupRVModal implements Parcelable {
    public static final Creator<GroupRVModal> CREATOR = new Creator<GroupRVModal>() {
        @Override
        public GroupRVModal createFromParcel(Parcel in) {
            return new GroupRVModal(in);
        }

        @Override
        public GroupRVModal[] newArray(int size) {
            return new GroupRVModal[size];
        }
    };
    //creating variables for our different fields.
    private String groupName;
    private String groupDescription;
    private String groupPrice;
    private String activityDetail;
    private String groupImg;
    private String groupLink;
    private String groupId;

    //creating an empty constructor.
    public GroupRVModal() {

    }


    protected GroupRVModal(Parcel in) {
        groupName = in.readString();
        groupId = in.readString();
        groupDescription = in.readString();
        groupPrice = in.readString();
        activityDetail = in.readString();
        groupImg = in.readString();
        groupLink = in.readString();
    }

    public GroupRVModal(String groupId, String groupName, String groupDescription, String groupPrice, String activityDetail, String groupImg, String groupLink) {
        this.groupName = groupName;
        this.groupId = groupId;
        this.groupDescription = groupDescription;
        this.groupPrice = groupPrice;
        this.activityDetail = activityDetail;
        this.groupImg = groupImg;
        this.groupLink = groupLink;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    //creating getter and setter methods.
    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupDescription() {
        return groupDescription;
    }

    public void setGroupDescription(String groupDescription) {
        this.groupDescription = groupDescription;
    }

    public String getGroupPrice() {
        return groupPrice;
    }

    public void setGroupPrice(String groupPrice) {
        this.groupPrice = groupPrice;
    }

    public String getActivityDetail() {
        return activityDetail;
    }

    public void setActivityDetail(String activityDetail) {
        this.activityDetail = activityDetail;
    }

    public String getGroupImg() {
        return groupImg;
    }

    public void setGroupImg(String groupImg) {
        this.groupImg = groupImg;
    }

    public String getGroupLink() {
        return groupLink;
    }

    public void setGroupLink(String groupLink) {
        this.groupLink = groupLink;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(groupName);
        dest.writeString(groupId);
        dest.writeString(groupDescription);
        dest.writeString(groupPrice);
        dest.writeString(activityDetail);
        dest.writeString(groupImg);
        dest.writeString(groupLink);
    }
}
