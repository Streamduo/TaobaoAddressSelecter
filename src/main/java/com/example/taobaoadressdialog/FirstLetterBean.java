package com.example.taobaoadressdialog;

/**
 * Created by Android Studio.
 * User: lishuai
 * Date: 2021/1/28
 * Time: 上午10:43
 */
class FirstLetterBean {
    private String firstLetter;
    private String name;
    private String code;
    private boolean isChoose = false;
    private int level;

    public String getFirstLetter() {
        return firstLetter;
    }

    public void setFirstLetter(String firstLetter) {
        this.firstLetter = firstLetter;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isChoose() {
        return isChoose;
    }

    public void setChoose(boolean choose) {
        isChoose = choose;
    }

    public int getLevel() {
        return level - 1;
    }

    public void setLevel(int level) {
        this.level = level;
    }

}
