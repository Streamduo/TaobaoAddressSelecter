package com.example.taobaoadressdialog;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: ProjectItemBean
 * @Description: 底部实体类
 * @Author: Fuduo
 * @CreateDate: 2023/8/7 16:58
 * @UpdateUser: 更新者
 * @UpdateDate: 2023/8/7 16:58
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class ProjectItemBean extends FirstLetterBean implements Serializable {

    private List<ProjectItemBean> children = new ArrayList<>();

    public List<ProjectItemBean> getChildList() {
        return children;
    }

    public void setChildList(List<ProjectItemBean> childList) {
        this.children = childList;
    }

}
