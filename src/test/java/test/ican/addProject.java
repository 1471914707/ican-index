package test.ican;

import com.ican.IcanIndexApplication;
import com.ican.config.Constant;
import com.ican.domain.Project;
import com.ican.exception.icanServiceException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = IcanIndexApplication.class)
public class addProject {
    @Test
    public void add() {
        for (int i=0; i<500; i++) {
            Project project = new Project();
            project.setCurrent(2018);
            project.setActivityId(12);
            project.setSchoolId(100002);
            project.setCollegeId(100020);
            project.setTeacherId(100031);
            try {
                Constant.ServiceFacade.getProjectService().save(project);
            } catch (icanServiceException e) {
                e.printStackTrace();
            }
        }
    }
}
