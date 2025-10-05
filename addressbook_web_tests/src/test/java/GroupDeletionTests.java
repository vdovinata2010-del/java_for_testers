import org.junit.jupiter.api.Test;

public class GroupDeletionTests extends TestBase {

    @Test
    public void canDeleteGroup() {
        openGroupsPage();
        if (!isGroupPresent()) {
            createGroup();
        }
        removeGroup();
    }

}
