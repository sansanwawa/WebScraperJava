package info.university.runnable;

import java.io.IOException;
import info.university.CampusAction;
import info.university.CampusAction.DEGREE;

public class RunCampusUrl extends RunCampus implements Runnable {

    public RunCampusUrl(final int index, final String campusName, final String url, final String tagSelector,
            final DEGREE degree) {
        super(index, campusName, url, tagSelector, degree);
    }

    @Override
    public void run() {
        try {
            CampusAction.getDataFromUrl(index, campusName, url, tagSelector, degree);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}