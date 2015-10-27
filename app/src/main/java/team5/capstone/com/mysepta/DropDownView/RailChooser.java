package team5.capstone.com.mysepta.DropDownView;

import com.bignerdranch.expandablerecyclerview.Model.ParentObject;

import java.util.List;

/**
 * Model to hold parent for expandable list
 * Created by Kevin on 9/23/15.
 */
public class RailChooser implements ParentObject {

    private List<Object> mchildrenList;
    private String title;

    public RailChooser(String title){
        this.title = title;
    }

    @Override
    public List<Object> getChildObjectList() {
        return mchildrenList;
    }

    @Override
    public void setChildObjectList(List<Object> list) {
        mchildrenList = list;
    }

    public String getTitle() {
        return title;
    }
}
