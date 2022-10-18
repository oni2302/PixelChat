package com.oni.pixelchat;

import com.oni.pixelchat.databinding.FragmentHomeBinding;

import java.util.ArrayList;

public class InboxItem {
    int pic;
    String name;
    String lastMessage;

    public InboxItem(int pic, String name, String lastMessage) {
        this.pic = pic;
        this.name = name;
        this.lastMessage = lastMessage;
    }

    public int getPic() {
        return pic;
    }

    public void setPic(int pic) {
        this.pic = pic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public static ArrayList<InboxItem> test(){
        String[] names = {"Em yêu 1","Em yêu 2","Em yêu 3","Em yêu 4","Em yêu 5","Em yêu 6","Em yêu 7","Em yêu 8","Em yêu 9","Em yêu 10"};
        String[] lastMessages = {"Đêm qua anh tuyệt lắm!","Đêm qua anh tuyệt lắm!","Đêm qua anh tuyệt lắm!","Đêm qua anh tuyệt lắm!","Đêm qua anh tuyệt lắm!","Đêm qua anh tuyệt lắm!","Đêm qua anh tuyệt lắm!","Đêm qua anh tuyệt lắm!","Đêm qua anh tuyệt lắm!","Đêm qua anh tuyệt lắm!"};
        int[] pics = {
                R.drawable.test_avt_img,
                R.drawable.test_avt_img,
                R.drawable.test_avt_img,
                R.drawable.test_avt_img,
                R.drawable.test_avt_img,
                R.drawable.test_avt_img,
                R.drawable.test_avt_img,
                R.drawable.test_avt_img,
                R.drawable.test_avt_img,
                R.drawable.test_avt_img
        };
        ArrayList<InboxItem> arrayList = new ArrayList<>();
        for (int i =0;i<names.length;i++){
            InboxItem ibi = new InboxItem(pics[i],names[i],lastMessages[i]);
            arrayList.add(ibi);
        }
        return arrayList;
    }
}
