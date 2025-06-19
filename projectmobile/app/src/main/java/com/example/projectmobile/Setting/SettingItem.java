package com.example.projectmobile.Setting;

public class SettingItem {
    public static final int TYPE_HEADER = 0;
    public static final int TYPE_ITEM = 1;
    public SettingItem(int type,int icon, String title) {
        this.type = type;
        this.icon = icon;
        this.title = title;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    private int type;
    private int icon;
    private String title;
}
