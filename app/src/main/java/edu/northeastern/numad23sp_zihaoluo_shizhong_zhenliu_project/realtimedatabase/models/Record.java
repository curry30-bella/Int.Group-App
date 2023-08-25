package edu.northeastern.numad23sp_zihaoluo_shizhong_zhenliu_project.realtimedatabase.models;


/**
 * Created by aniru on 2/18/2017.
 */

public class Record {

    public String groupName;
    public User user;
    public String username;
    public String text;
    public String stickerRecivedDate;


    public Record() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }


    public Record(User user, String username, String groupName, String text, String stickerRecivedDate) {
        this.user = user;
        this.groupName = groupName;
        this.username = username;
        this.text = text;
        this.stickerRecivedDate = stickerRecivedDate;

    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getImageId() {
        return text;
    }

    public void setImageId(String imageId) {
        this.text = imageId;
    }

    public String getStickerRecivedDate() {
        return stickerRecivedDate;
    }

    public void setStickerRecivedDate(String stickerRecivedDate) {
        this.stickerRecivedDate = stickerRecivedDate;
    }

    @Override
    public String toString() {
        return
                "sender='" + groupName + '\'' +
                        ", reciver='" + username + '\'' +
                        ",profilePic='" + user.profilePic + '\'' +
                        ", recivedDate='" + stickerRecivedDate + '\'';
    }
}
