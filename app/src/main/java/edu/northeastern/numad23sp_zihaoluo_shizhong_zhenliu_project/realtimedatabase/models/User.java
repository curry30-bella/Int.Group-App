package edu.northeastern.numad23sp_zihaoluo_shizhong_zhenliu_project.realtimedatabase.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

/**
 * Created by aniru on 2/18/2017.
 */

public class User implements Parcelable {

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
    public String username;
    public String password;
    public String profilePic;
    public String[] groups;
    public int activities, joinedGroups, createdGroups, signeddays;
    public String email, phone, location;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    //
    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.profilePic = "https://i.stack.imgur.com/dr5qp.jpg";
        this.activities = 0;
        this.joinedGroups = 0;
        this.createdGroups = 0;
        this.signeddays = 0;
        this.email = username + "@gmail.com";
        this.phone = "206-123-4567";
        this.location = "Seattle - Washington";
    }


    public User(String username, String password, String profilePic) {
        this.username = username;
        this.password = password;
        this.profilePic = profilePic;
    }

    protected User(Parcel in) {
        username = in.readString();
        password = in.readString();
        profilePic = in.readString();
//        groups = in.createStringArray();
        activities = in.readInt();
        joinedGroups = in.readInt();
        createdGroups = in.readInt();
        signeddays = in.readInt();
        email = in.readString();
        phone = in.readString();
        location = in.readString();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String[] getGroups() {
        return groups;
    }

    public void setGroups(String[] groups) {
        this.groups = groups;
    }

    public int getActivities() {
        return activities;
    }

    public void setActivities(int activities) {
        this.activities = activities;
    }

    public int getJoinedGroups() {
        return joinedGroups;
    }

    public void setJoinedGroups(int joinedGroups) {
        this.joinedGroups = joinedGroups;
    }

    public int getCreatedGroups() {
        return createdGroups;
    }

    public void setCreatedGroups(int createdGroups) {
        this.createdGroups = createdGroups;
    }

    public int getSigneddays() {
        return signeddays;
    }

    public void setSigneddays(int signeddays) {
        this.signeddays = signeddays;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(username);
        parcel.writeString(password);
        parcel.writeString(profilePic);
//        parcel.writeStringArray(groups);
        parcel.writeInt(activities);
        parcel.writeInt(joinedGroups);
        parcel.writeInt(createdGroups);
        parcel.writeInt(signeddays);
        parcel.writeString(email);
        parcel.writeString(phone);
        parcel.writeString(location);
    }

    public String toString() {
        return "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", profilePic='" + profilePic + '\''
                + ", activities='" + activities + '\''
                + ", joinedGroups='" + joinedGroups + '\''
                + ", createdGroups='" + createdGroups + '\''
                + ", signeddays='" + signeddays + '\''
                + ", email='" + email + '\''
                + ", phone='" + phone + '\''
                + ", location='" + location + '\'';
    }
}
