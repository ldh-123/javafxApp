package ldh.musicfx.model;

import java.util.List;

/**
 * Created by Puhui on 2016/10/8.
 */
public class NavGroup {

    private String title;
    private List<NavData> navDataList;

    public NavGroup(String title, List<NavData> navDataList) {
        this.title = title;
        this.navDataList = navDataList;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<NavData> getNavDataList() {
        return navDataList;
    }

    public void setNavGroupList(List<NavData> navDataList) {
        this.navDataList = navDataList;
    }
}
