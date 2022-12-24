package mk.ukim.finki.wp.lab.selenium;

import mk.ukim.finki.wp.lab.model.Course;
import mk.ukim.finki.wp.lab.model.Teacher;
import mk.ukim.finki.wp.lab.model.enumerations.Type;
import mk.ukim.finki.wp.lab.service.CourseService;
import mk.ukim.finki.wp.lab.service.TeacherService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)

public class SeleniumScenarioTest {
    private HtmlUnitDriver driver;
    private static String admin = "admin";

    @Autowired
    CourseService courseService;

    @Autowired
    TeacherService teacherService;



    private static boolean dataInitialized = false;


    @BeforeEach
    private void setup() {
        this.driver = new HtmlUnitDriver(true);
        initData();
    }

    private void initData() {
        if(!dataInitialized)
        {
            dataInitialized = true;
            Teacher t = new Teacher("Teacher1","Teacher1",null);
            teacherService.saveTeacher(t);
            courseService.saveCourse((long)1,"Course1","CourseDesc", t.getId(), String.valueOf(Type.SUMMER));
        }
    }

    @AfterEach
    public void destroy() {
        if (this.driver != null) {
            this.driver.close();
        }
    }


    @Test
    public void testScenario() throws Exception {
        CoursesPage coursesPage = CoursesPage.to(this.driver);
        coursesPage.assertAbsentElements();
        LoginPage loginPage = LoginPage.openLogin(this.driver);

        coursesPage = LoginPage.doLogin(this.driver, loginPage, admin, admin);
        coursesPage.assertPresentElements();




    }

}
