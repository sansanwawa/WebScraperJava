package info.university.runnable;
 
import java.io.IOException;
import info.university.CampusAction;
import info.university.CampusAction.DEGREE;

public class RunCampusFile extends RunCampus implements Runnable {

    public RunCampusFile(int index,String campusName, DEGREE degree) {
        super(index, campusName, degree);
    } 
   
    @Override
    public void run() {
        try {
            CampusAction.getDataFromFile(index, campusName, degree);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}