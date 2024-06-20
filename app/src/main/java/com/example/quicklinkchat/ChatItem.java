package com.example.quicklinkchat;

import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;

import androidx.annotation.NonNull;

public class ChatItem implements Parcelable {
    private int profileImage;
    private String username;
    private String message;
    private View.OnClickListener onClickListener;

    private long number;

    public ChatItem(int profileImage, String username, String message) {
        this.profileImage = profileImage;
        this.username = username;
        this.message = message;
        this.number = 8347762286L;
    }

    protected ChatItem(Parcel in) {
        profileImage = in.readInt();
        username = in.readString();
        message = in.readString();
        number = in.readLong();
    }

    public static final Creator<ChatItem> CREATOR = new Creator<ChatItem>() {
        @Override
        public ChatItem createFromParcel(Parcel in) {
            return new ChatItem(in);
        }

        @Override
        public ChatItem[] newArray(int size) {
            return new ChatItem[size];
        }
    };

    public int getProfileImage() {
        return profileImage;
    }

    public String getUsername() {
        return username;
    }

    public String getMessage() {
        return message;
    }

    public String getUserName() {
        return null;
    }

    public long getUserNumber(){return number;}

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;

    }
    public View.OnClickListener getOnClickListener() {
        return onClickListener;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(profileImage);
        dest.writeString(username);
        dest.writeString(message);
        dest.writeLong(number);
    }
}
