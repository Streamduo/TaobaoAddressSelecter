package com.example.taobaoadressdialog;

/**
 * @ClassName: ProjectTitleItemBean
 * @Description: 顶部的实体类
 * @Author: Fuduo
 * @CreateDate: 2023/8/7 16:58
 * @UpdateUser: 更新者
 * @UpdateDate: 2023/8/7 16:58
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class ProjectTitleItemBean {
    private String name;
    private String code;
    private boolean isChoose = false;
    private boolean isSelect = false;

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

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }
}
