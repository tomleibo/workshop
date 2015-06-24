package orm;

import content.Forum;
import content.Message;
import content.SubForum;
import controllers.AdminController;
import controllers.ContentController;
import controllers.SuperAdminController;
import controllers.UserController;
import exceptions.*;
import org.junit.*;
import policy.ForumPolicy;
import users.FriendRequest;
import users.User;
import utils.HibernateUtils;

import java.lang.reflect.InvocationTargetException;
import java.security.NoSuchAlgorithmException;

//import org.apache.commons.beanutils.BeanUtils;


public class ForumTests {
    @BeforeClass
    public static void Setup() {
        //HibernateUtils.init();\
        HibernateUtils.start();
    }

    @AfterClass
    public static void tearDown() {
        //HibernateUtils.cleanUp();
    }

    @Test
    public void testSaveForum() {
        System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName());
        Forum f = new Forum(new User(),new ForumPolicy(),"forum_name");
        int id = HibernateUtils.saveReturnId(f);
        Assert.assertEquals(f, (Forum) HibernateUtils.load(Forum.class, id));
    }


    @Test
    public void testAddSubforumFromForum() {
        System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName());
        User u =User.newMember("user","pass","mail@gmail.com");
        int uid = (int)HibernateUtils.saveReturnId(u);
        Assert.assertTrue(uid > 0);
        Forum f = new Forum(u,new ForumPolicy(),"forum_name");
        int fid = HibernateUtils.saveReturnId(f);
        //f.id=fid;
        SubForum sub = new SubForum("sub name",u,5);
        f.addSubForum(sub);
        boolean b = HibernateUtils.update(f);
        Assert.assertTrue(b);
        Forum ormf = (Forum) HibernateUtils.load(Forum.class, fid);
        Assert.assertTrue(ormf.getSubForums().contains(sub));
    }

    @Test
    public void testGetAdmin() {
        System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName());
        User u =User.newMember("asdsad", "asd", "sdkfdjk@sldkf.com");
        //int uid = HibernateUtils.saveReturnId(u);
        //System.out.println("testGetAdmin save user returned: "+uid);
        Forum f = new Forum(u,new ForumPolicy(),"forum_name");
        int fid = HibernateUtils.saveReturnId(f);
        Forum ormf = (Forum) HibernateUtils.load(Forum.class, fid);
        Assert.assertEquals(u, ormf.getAdmin());
        //Assert.assertEquals(u.getId(),uid);
    }


    @Test
    public void testAddForumPolicy(){
        ForumPolicy policy = new ForumPolicy(5,"*****", ForumPolicy.HashFunction.MD5, false);
        int id = (int)HibernateUtils.saveReturnId(policy);
        Assert.assertEquals(HibernateUtils.load(ForumPolicy.class, id), policy);
    }

    @Test
    public void testAddSubforumFromController() throws UserNotAuthorizedException {
        User u =User.newSuperAdmin("bivan", "dooogi", "sdkfdjk@sld;kf.com");
        ForumPolicy policy = new ForumPolicy(5,".*", ForumPolicy.HashFunction.MD5, false);
        Forum f = SuperAdminController.createNewForum(u, policy, "forum1");
        Assert.assertEquals(HibernateUtils.load(Forum.class, f.id), f);
        SubForum sub = ContentController.addSubForum(f, "hadar's sub", u);
        Assert.assertEquals(HibernateUtils.load(SubForum.class, sub.id), sub);
    }

    @Test
    public void testAddSubforumToLoadedForumFromController_roee() throws UserNotAuthorizedException, InvocationTargetException, IllegalAccessException {
        User u =User.newSuperAdmin("bivan", "dooogi", "sdkfdjk@sld;kf.com");
        ForumPolicy policy = new ForumPolicy(5,".*", ForumPolicy.HashFunction.MD5, false);
        Forum f = SuperAdminController.createNewForum(u, policy, "forum1");
        Forum loadedForum = (Forum) HibernateUtils.load(Forum.class, f.id);
        f.setAdmin(User.newSuperAdmin("hello", "fsdfsd", ""));
        Assert.assertEquals(loadedForum, f);
        SubForum sub = ContentController.addSubForum(loadedForum, "roee's sub", u);
        SubForum loadedSub = (SubForum) HibernateUtils.load(SubForum.class, sub.id);
        Assert.assertEquals(loadedSub, sub);
        Forum loadedForumAfterAdd = (Forum) HibernateUtils.load(Forum.class, f.id);
        Assert.assertEquals(loadedForumAfterAdd.getSubForums().size(), 1);
        Assert.assertEquals(loadedForumAfterAdd.getSubForums().get(0), loadedSub);
    }

    @Test
    public void testAddMemberToLoadedForumFromController_roee() throws Exception {
        User u =User.newSuperAdmin("bivan", "dooogi", "sdkfdjk@sld;kf.com");
        ForumPolicy policy = new ForumPolicy(5,".*", ForumPolicy.HashFunction.MD5, false);
        Forum f = SuperAdminController.createNewForum(u, policy, "forum1");
        Forum loadedForum = (Forum) HibernateUtils.load(Forum.class, f.id);
        Assert.assertEquals(loadedForum, f);
        User registered = UserController.register(loadedForum, "newMember", "asdf", "asdf@asdf.com");
        Forum loadedForumAfterAdd = (Forum) HibernateUtils.load(Forum.class, f.id);
        Assert.assertEquals(loadedForumAfterAdd.getMembers().size(), 2);
        Assert.assertEquals(loadedForumAfterAdd.getMembers().get(1), registered);
    }

    @Test
    public void testAddAndDeleteSubforumFromController() throws Exception {
        User u =User.newSuperAdmin("bivan", "dooogi", "sdkfdjk@sld;kf.com");
        ForumPolicy policy = new ForumPolicy(5,".*", ForumPolicy.HashFunction.MD5, false);
        Forum f = SuperAdminController.createNewForum(u, policy, "forum1");
        Assert.assertEquals(HibernateUtils.load(Forum.class, f.id), f);
        SubForum sub = ContentController.addSubForum(f, "hadar's sub", u);
        Assert.assertEquals(HibernateUtils.load(SubForum.class, sub.id), sub);
        AdminController.deleteSubForum(f, sub, u);
        Assert.assertEquals(HibernateUtils.load(SubForum.class, sub.id), null);
    }

    @Test
    public void testAppointModeratorFromController() throws Exception {
        User u =User.newSuperAdmin("bivan", "dooogi", "sdkfdjk@sld;kf.com");
        ForumPolicy policy = new ForumPolicy(5,".*", ForumPolicy.HashFunction.MD5, false);
        Forum f = SuperAdminController.createNewForum(u, policy, "forum1");
        User mod = UserController.register(f, "hadar", "hadarosh", "sdkfdjk@sld;kf.com");
        SubForum sub = ContentController.addSubForum(f, "hadar's sub", u);
        AdminController.appointModerator(f, sub, u, mod);
        Assert.assertTrue(((SubForum) HibernateUtils.load(SubForum.class, sub.id)).getModerators().contains(mod));
    }

    @Test
    public void testAppointAndUnAppointFromController() throws Exception {
        User admin =User.newSuperAdmin("bivan", "dooogi", "sdkfdjk@sld;kf.com");
        ForumPolicy policy = new ForumPolicy(5,".*", ForumPolicy.HashFunction.MD5, false);
        Forum f = SuperAdminController.createNewForum(admin, policy, "forum1");
        User mod =UserController.register(f, "hadar", "hadarosh", "sdkfdjk@sld;kf.com");
        SubForum sub = ContentController.addSubForum(f, "hadar's sub", admin);
        Assert.assertTrue(((SubForum) HibernateUtils.load(SubForum.class, sub.id)).getModerators().contains(admin));
        AdminController.appointModerator(f,sub,admin,mod);
        Assert.assertTrue(((SubForum) HibernateUtils.load(SubForum.class, sub.id)).getModerators().contains(mod));
        AdminController.unAppoint(f, sub, admin, mod);
        Assert.assertFalse(((SubForum) HibernateUtils.load(SubForum.class, sub.id)).getModerators().contains(mod));
    }

    @Test
    public void testReplaceModeratorFromController() throws Exception {
        User admin =User.newSuperAdmin("1", "dooogi", "sdkfdjk@sld;kf.com");

        ForumPolicy policy = new ForumPolicy(5,".+", ForumPolicy.HashFunction.MD5, false);
        Forum forum = SuperAdminController.createNewForum(admin, policy, "forum1");
        User mod = null;
        try {
            mod = UserController.register(forum, "mod", "adsf", "asd@asd@asd");
        } catch (UsernameAlreadyExistsException e) {
            e.printStackTrace();
            Assert.fail();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            Assert.fail();
        }
        SubForum sub = ContentController.addSubForum(forum, "hadar's sub", admin);
        Assert.assertTrue(((SubForum) HibernateUtils.load(SubForum.class, sub.id)).getModerators().contains(admin));
        AdminController.replaceModerator(forum, sub, admin, admin, mod);
        Assert.assertTrue(((SubForum) HibernateUtils.load(SubForum.class, sub.id)).getModerators().contains(mod));
        Assert.assertFalse(((SubForum) HibernateUtils.load(SubForum.class, sub.id)).getModerators().contains(admin));
    }


    @Test
    public void testAddNewThreadFromController() throws UserNotAuthorizedException, EmptyMessageTitleAndBodyException {
        User admin =User.newSuperAdmin("bivan", "dooogi", "sdkfdjk@sld;kf.com");
        ForumPolicy policy = new ForumPolicy(5,".*", ForumPolicy.HashFunction.MD5, false);
        Forum forum = SuperAdminController.createNewForum(admin, policy, "forum1");
        SubForum sub = ContentController.addSubForum(forum, "hadar's sub", admin);
        content.Thread thread = ContentController.openNewThread(forum, sub, "chika is cool", "chika is very cool", admin);
        Assert.assertTrue(((SubForum) HibernateUtils.load(SubForum.class, sub.id)).viewThreads().contains(thread));
    }

    @Test
    public void testAddNewReplyToMessageFromController() throws Exception {
        User admin = User.newSuperAdmin("bivan", "dooogi", "sdkfdjk@sldkf.com");
        ForumPolicy policy = new ForumPolicy(5,".*", ForumPolicy.HashFunction.MD5, false);
        Forum forum = SuperAdminController.createNewForum(admin, policy, "forum1");
        User user = UserController.register(forum, "CHIKA", "theCoolit", "jkffh@gmail.com");
        SubForum sub = ContentController.addSubForum(forum, "hadars sub", admin);
        content.Thread thread = ContentController.openNewThread(forum, sub, "a", "b", admin);
        Assert.assertTrue(((SubForum) HibernateUtils.load(SubForum.class, sub.id)).viewThreads().contains(thread));
        Message msg = ContentController.reply(forum, thread.getOpeningMessage(), "hi", "SHALOM", user);
        Assert.assertTrue(((Message) HibernateUtils.load(Message.class, thread.getOpeningMessage().id)).getComments().contains(msg));
    }

    @Test
    public void testAddAndDeleteNewMessageFromController() throws Exception {
        User admin = User.newSuperAdmin("bivan", "dooogi", "sdkfdjk@sldkf.com");
        ForumPolicy policy = new ForumPolicy(5,".*", ForumPolicy.HashFunction.MD5, false);
        Forum forum = SuperAdminController.createNewForum(admin, policy, "forum1");
        User user = UserController.register(forum, "CHIKA", "theCoolit", "jkffh@gmail.com");
        SubForum sub = ContentController.addSubForum(forum, "hadars sub", admin);
        content.Thread thread = ContentController.openNewThread(forum, sub, "a", "b", admin);
        Message msg = ContentController.reply(forum, thread.getOpeningMessage(), "hi", "SHALOM", user);
        ContentController.deletePost(msg);
        Assert.assertFalse(((Message) HibernateUtils.load(Message.class, thread.getOpeningMessage().id)).getComments().contains(msg));
        Assert.assertEquals((HibernateUtils.load(Message.class, msg.id)), null);
    }

    @Test
    public void testAddAndEditPostNewMessageFromController() throws Exception {
        User admin = User.newSuperAdmin("bivan", "dooogi", "sdkfdjk@sldkf.com");
        ForumPolicy policy = new ForumPolicy(5,".*", ForumPolicy.HashFunction.MD5, false);
        Forum forum = SuperAdminController.createNewForum(admin, policy, "forum1");
        User user = UserController.register(forum, "CHIKA", "theCoolit", "jkffh@gmail.com");
        SubForum sub = ContentController.addSubForum(forum, "hadars sub", admin);
        content.Thread thread = ContentController.openNewThread(forum, sub, "a", "b", admin);
        Message msg = ContentController.reply(forum, thread.getOpeningMessage(), "hi", "SHALOM", user);
        String newTxt = "this post eddited";
        ContentController.editPost(msg, newTxt);
        Assert.assertEquals(((Message) (HibernateUtils.load(Message.class, msg.id))).getBody(), newTxt);
    }

    @Test
    public void testAddAndDeleteSubForumFromController() throws UserNotAuthorizedException {
        User admin = User.newSuperAdmin("bivan", "dooogi", "sdkfdjk@sldkf.com");
        ForumPolicy policy = new ForumPolicy(5,".*", ForumPolicy.HashFunction.MD5, false);
        Forum forum = SuperAdminController.createNewForum(admin, policy, "forum1");
        SubForum sub = ContentController.addSubForum(forum, "hadars sub", admin);
        ContentController.deleteSubForum(forum, sub);
        Assert.assertFalse(((Forum) HibernateUtils.load(Forum.class, forum.id)).getSubForums().contains(sub));
    }

    @Test
    public void testAddAndDeleteForumFromController() throws UserNotAuthorizedException {
        User admin = User.newSuperAdmin("bivan", "dooogi", "sdkfdjk@sldkf.com");
        ForumPolicy policy = new ForumPolicy(5,".*", ForumPolicy.HashFunction.MD5, false);
        Forum forum = SuperAdminController.createNewForum(admin, policy, "forum1");
        int forumId = forum.id;
        SuperAdminController.deleteForum(admin, forum);
        Assert.assertEquals((HibernateUtils.load(Forum.class, forumId)), null);
    }
/*
    @Test
    public void testDeleteAllForumsFromController() throws UserNotAuthorizedException {
        User admin = User.newSuperAdmin("bivan", "dooogi", "sdkfdjk@sldkf.com");
        ForumPolicy policy = new ForumPolicy(5,".*", ForumPolicy.HashFunction.MD5, false);
        Forum forum1 = SuperAdminController.createNewForum(admin, policy, "forum1");
        Forum forum2 = SuperAdminController.createNewForum(admin, policy, "forum2");
        Forum forum3 = SuperAdminController.createNewForum(admin, policy, "forum3");

        SuperAdminController.deleteAllForums(admin);

        String hql = "FROM Forum";
        Query query = HibernateUtils.getSession().createQuery(hql);
        List results = query.list();
        Assert.assertTrue(results.isEmpty());

    }*/


    @Test
    public void testEnterAsGuest() throws UserNotAuthorizedException {
        User admin = User.newSuperAdmin("bivan", "dooogi", "sdkfdjk@sldkf.com");
        ForumPolicy policy = new ForumPolicy(5,".*", ForumPolicy.HashFunction.MD5, false);
        Forum forum1 = SuperAdminController.createNewForum(admin, policy, "forum1");
        User guest = UserController.enterAsGuest(forum1);
        Assert.assertEquals(HibernateUtils.load(User.class, guest.getId()), guest);
    }

    @Test
    public void testSendFriendRequest() throws Exception {
        User admin = User.newSuperAdmin("tom", "leibo", "yeah");
        ForumPolicy policy = new ForumPolicy(155,".*", ForumPolicy.HashFunction.MD5, false);
        Forum forum1 = SuperAdminController.createNewForum(admin, policy, "forum123432");
        User user = UserController.register(forum1, "tom2", "2", "mail@mail.com");
        FriendRequest fr= UserController.sendFriendRequest(forum1, user, admin, "msg123");
        FriendRequest dbfr=(FriendRequest)HibernateUtils.load(FriendRequest.class, fr.id);
        Assert.assertEquals(dbfr, fr);
    }

    @Test
    public void testReplyToFriendRequest() throws Exception {
        User admin = User.newSuperAdmin("bivan", "dooogi", "sdkfdjk@sldkf.com");
        ForumPolicy policy = new ForumPolicy(5,".*", ForumPolicy.HashFunction.MD5, false);
        Forum forum1 = SuperAdminController.createNewForum(admin, policy, "forum1");
        User user = UserController.register(forum1, "tom", "l", "mail@mail.com");
        FriendRequest fr= UserController.sendFriendRequest(forum1, user, admin, "msg");
        UserController.replyToFriendRequest(forum1, admin, fr, true);
        Assert.assertTrue(((User) HibernateUtils.load(User.class, user.getId())).getFriends().contains(admin));
        user = (User) HibernateUtils.load(User.class, user.getId());
        Assert.assertTrue(((User) HibernateUtils.load(User.class, admin.getId())).getFriends().contains(user));
    }

    @Test
    public void testReomveFriend() throws Exception {
        User admin = User.newSuperAdmin("admin", "user", "@");
        ForumPolicy policy = new ForumPolicy(5,".*", ForumPolicy.HashFunction.MD5, false);
        Forum forum1 = SuperAdminController.createNewForum(admin, policy, "forum1");
        User user = UserController.register(forum1, "tom", "leibo", "0605@dsad.com");
        FriendRequest fr= UserController.sendFriendRequest(forum1, user, admin, "msg");
        UserController.replyToFriendRequest(forum1, admin, fr, true);
        UserController.removeFriend(forum1, user, admin);
        Assert.assertFalse(((User) HibernateUtils.load(User.class, user.getId())).getFriends().contains(admin));
        Assert.assertFalse(((User) HibernateUtils.load(User.class, admin.getId())).getFriends().contains(user));
    }

    @After
    public void deleteRows() {

    }

    @AfterClass
    public static void TearDown(){
       // HibernateUtils.cleanUp();
    }


}
