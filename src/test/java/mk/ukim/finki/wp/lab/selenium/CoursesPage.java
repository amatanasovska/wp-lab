package mk.ukim.finki.wp.lab.selenium;

import lombok.Getter;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

@Getter
public class CoursesPage extends AbstractPage{
    @FindBy(css="#addNewCourse")
    List<WebElement> addButton;
    @FindBy(css=".editBtn")
    List<WebElement> editButtons;
    @FindBy(css=".deleteBtn")
    List<WebElement> deleteButtons;

    @FindBy(css="input[type=radio]")
    List<WebElement> courses;

    public CoursesPage(WebDriver driver) {
        super(driver);
    }
    public static CoursesPage to(WebDriver driver)
    {
        get(driver,"/products");
        return PageFactory.initElements(driver,CoursesPage.class);
    }
    public void assertAbsentElements() {
        Assert.assertEquals("There are present edit buttons.", 0, this.getEditButtons().size());
        Assert.assertEquals("There are present delete buttons", 0, this.getDeleteButtons().size());
        Assert.assertEquals("There is present add new course button.", 0,this.getAddButton().size());

    }
    public void assertPresentElements() {
        Assert.assertNotEquals("There is not present add new course button.", 0,this.getAddButton().size());
        Assert.assertNotEquals("There are not present edit buttons.", 0, this.getEditButtons().size());
        Assert.assertNotEquals("There are not present delete buttons", 0, this.getDeleteButtons().size());

    }

}
