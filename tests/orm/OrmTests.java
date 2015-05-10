package orm;

import content.Forum;
import org.junit.BeforeClass;
import org.junit.Test;
import utils.HibernateUtils;

/**
 * Created by Roee on 10-05-15.
 */
public class OrmTests {

    @BeforeClass
    public static void setUp() {
        HibernateUtils.start();
    }

    @Test
    public void testSave() {
        HibernateUtils.save(new Forum());
        HibernateUtils.session.flush();
        HibernateUtils.session.close();
    }
}
