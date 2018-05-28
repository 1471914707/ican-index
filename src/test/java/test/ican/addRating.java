package test.ican;

import com.ican.IcanIndexApplication;
import com.ican.config.Constant;
import com.ican.domain.AnswerArrange;
import com.ican.domain.Project;
import com.ican.domain.Rating;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Random;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = IcanIndexApplication.class)
public class addRating {
    @Test
    public void add() {
        try {
            Random random = new Random();
            List<Project> projectList = Constant.ServiceFacade.getProjectService().list(null, 12, 0, 0, 0, 0, 0, 0, 0, null, 0, null, 1, 600);
            for (Project project : projectList) {
                int max = random.nextInt(10);
                Rating rating1 = new Rating();
                rating1.setTeacherId(project.getTeacherId());
                rating1.setAnswerId(8);
                rating1.setGroupsId(1);
                rating1.setProjectId(project.getId());
                int score = random.nextInt(100);
                while (score < 60) {
                    score = random.nextInt(100);
                }
                rating1.setScore(score);
                Constant.ServiceFacade.getRatingService().save(rating1);
                for (int i=0; i<max; i++) {
                    Rating rating2 = new Rating();
                    rating2.setTeacherId(0);
                    rating2.setAnswerId(8);
                    rating2.setGroupsId(1);
                    rating2.setProjectId(project.getId());
                    int score1 = random.nextInt(100);
                    while (score1 < 60) {
                        score1 = random.nextInt(100);
                    }
                    rating2.setScore(score1);
                    Constant.ServiceFacade.getRatingService().save(rating2);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
