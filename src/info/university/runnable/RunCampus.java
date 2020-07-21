package info.university.runnable;

import info.university.CampusAction.DEGREE;

public class RunCampus {
    int index;
    String campusName;
    String url;
    String tagSelector;
    DEGREE degree;
    
    public RunCampus(int index, String campusName, String url,String tagSelector, DEGREE degree){
        this.index = index;
        this.campusName = campusName;
        this.url = url;
        this.tagSelector = tagSelector;
        this.degree = degree;
    }

    public RunCampus(int index, String campusName, DEGREE degree){
        this.index = index;
        this.campusName = campusName;
        this.degree = degree;
    }
}