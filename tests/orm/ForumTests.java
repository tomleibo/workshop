package orm;

import content.Forum;
import content.Message;
import content.SubForum;
import controllers.AdminController;
import controllers.ContentController;
import controllers.SuperAdminController;
import controllers.UserController;
import exceptions.EmptyMessageTitleAndBodyException;
import exceptions.UserNotAuthorizedException;
import exceptions.UsernameAlreadyExistsException;
import org.hibernate.Query;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import policy.ForumPolicy;
import users.FriendRequest;
import users.User;
import utils.HibernateUtils;

import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * Created by thinkPAD on 5/11/2015.
 */
public class ForumTests {
    @BeforeClass
    public static void Setup() {
        //HibernateUtils.init();\
        HibernateUtils.start();


    }

    @Test
    public void testSaveForum() {
        Forum f = new Forum(new User(),new ForumPolicy(),"forum_name");
        HibernateUtils.save(f);
        Assert.assertEquals(f, (Forum) HibernateUtils.load(Forum.class, 0));
    }

    @Test
    public void testGetAdmin() {

        User u =User.newMember("bivan","dooogi","sdkfdjk@sld;kf.com");
        //int uid = (int)HibernateUtils.save(u,null);
        Forum f = new Forum(u,new ForumPolicy(),"forum_name");
        int fid = HibernateUtils.save(f,null);
        Forum ormf = (Forum) HibernateUtils.load(Forum.class, fid);
        Assert.assertEquals(u, ormf.getAdmin());
        //Assert.assertEquals(u.getId(),uid);
    }

    @Test
    public void testAddSubforumFromForum() {
        User u =User.newMember("bivan","dooogi","sdkfdjk@sld;kf.com");
        //int uid = (int)HibernateUtils.save(u,null);
        Forum f = new Forum(u,new ForumPolicy(),"forum_name");
        int fid = HibernateUtils.save(f,null);
        SubForum sub = new SubForum("sub name",u,5);
        f.addSubForum(sub);
        HibernateUtils.save(f);
        Forum ormf = (Forum) HibernateUtils.load(Forum.class, fid);
        Assert.assertEquals(ormf.getSubForums().get(0),sub);
    }

    @Test
    public void testAddForumPolicy(){
        ForumPolicy policy = new ForumPolicy(5,"*****", ForumPolicy.HashFunction.MD5, false);
        int id = (int)HibernateUtils.save(policy, null);
        Assert.assertEquals(HibernateUtils.load(ForumPolicy.class, id), policy);
    }

    @Test
    public void testAddSubforumFromController() throws UserNotAuthorizedException {
        User u =User.newSuperAdmin("bivan", "dooogi", "sdkfdjk@sld;kf.com");
        ForumPolicy policy = new ForumPolicy(5,"*****", ForumPolicy.HashFunction.MD5, false);
        Forum f = SuperAdminController.createNewForum(u, policy, "forum1");
        Assert.assertEquals(HibernateUtils.load(Forum.class, f.id), f);
        SubForum sub = ContentController.addSubForum(f, "hadar's sub", u);
        Assert.assertEquals(HibernateUtils.load(SubForum.class, sub.id), sub);
    }

    @Test
    public void testAddAndDeleteSubforumFromController() throws UserNotAuthorizedException {
        User u =User.newSuperAdmin("bivan", "dooogi", "sdkfdjk@sld;kf.com");
        ForumPolicy policy = new ForumPolicy(5,"*****", ForumPolicy.HashFunction.MD5, false);
        Forum f = SuperAdminController.createNewForum(u, policy, "forum1");
        Assert.assertEquals(HibernateUtils.load(Forum.class, f.id), f);
        SubForum sub = ContentController.addSubForum(f, "hadar's sub", u);
        Assert.assertEquals(HibernateUtils.load(SubForum.class, sub.id), sub);
        AdminController.deleteSubForum(f, sub, u);
        Assert.assertEquals(HibernateUtils.load(SubForum.class, sub.id), null);
    }

    @Test
    public void testAppointModeratorFromController() throws UserNotAuthorizedException {
        User u =User.newSuperAdmin("bivan", "dooogi", "sdkfdjk@sld;kf.com");
        User mod =User.newSuperAdmin("hadar", "hadarosh", "sdkfdjk@sld;kf.com");
        ForumPolicy policy = new ForumPolicy(5,"*****", ForumPolicy.HashFunction.MD5, false);
        Forum f = SuperAdminController.createNewForum(u, policy, "forum1");
        SubForum sub = ContentController.addSubForum(f, "hadar's sub", u);
        AdminController.appointModerator(f, sub, u, mod);
        Assert.assertTrue(((SubForum) HibernateUtils.load(SubForum.class, sub.id)).getModerators().contains(mod));
    }

    @Test
    public void testAppointAndUnAppointFromController() throws UserNotAuthorizedException {
        User admin =User.newSuperAdmin("bivan", "dooogi", "sdkfdjk@sld;kf.com");
        User mod =User.newSuperAdmin("hadar", "hadarosh", "sdkfdjk@sld;kf.com");
        ForumPolicy policy = new ForumPolicy(5,"*****", ForumPolicy.HashFunction.MD5, false);
        Forum f = SuperAdminController.createNewForum(admin, policy, "forum1");
        SubForum sub = ContentController.addSubForum(f, "hadar's sub", admin);
        Assert.assertTrue(((SubForum) HibernateUtils.load(SubForum.class, sub.id)).getModerators().contains(admin));
        AdminController.appointModerator(f,sub,admin,mod);
        Assert.assertTrue(((SubForum) HibernateUtils.load(SubForum.class, sub.id)).getModerators().contains(mod));
        AdminController.unAppoint(f, sub, admin, mod);
        Assert.assertFalse(((SubForum) HibernateUtils.load(SubForum.class, sub.id)).getModerators().contains(mod));
    }

    @Test
    public void testReplacrModeratorFromController() throws UserNotAuthorizedException {
        User admin =User.newSuperAdmin("bivan", "dooogi", "sdkfdjk@sld;kf.com");
        User mod =User.newSuperAdmin("hadar", "hadarosh", "sdkfdjk@sld;kf.com");
        ForumPolicy policy = new ForumPolicy(5,"*****", ForumPolicy.HashFunction.MD5, false);
        Forum forum = SuperAdminController.createNewForum(admin, policy, "forum1");
        SubForum sub = ContentController.addSubForum(forum, "hadar's sub", admin);
        Assert.assertTrue(((SubForum) HibernateUtils.load(SubForum.class, sub.id)).getModerators().contains(admin));
        AdminController.replaceModerator(forum, sub, admin, admin, mod);
        Assert.assertTrue(((SubForum) HibernateUtils.load(SubForum.class, sub.id)).getModerators().contains(mod));
        Assert.assertFalse(((SubForum) HibernateUtils.load(SubForum.class, sub.id)).getModerators().contains(admin));
    }

    @Test
    public void testAddNewThreadFromController() throws UserNotAuthorizedException, EmptyMessageTitleAndBodyException {
        User admin =User.newSuperAdmin("bivan", "dooogi", "sdkfdjk@sld;kf.com");
        ForumPolicy policy = new ForumPolicy(5,"*****", ForumPolicy.HashFunction.MD5, false);
        Forum forum = SuperAdminController.createNewForum(admin, policy, "forum1");
        SubForum sub = ContentController.addSubForum(forum, "hadar's sub", admin);
        content.Thread thread = ContentController.openNewThread(forum, sub, "chika is cool", "chika is very cool", admin);
        Assert.assertTrue(((SubForum) HibernateUtils.load(SubForum.class, sub.id)).viewThreads().contains(thread));
    }

    @Test
    public void testAddNewReplyToMessageFromController() throws UserNotAuthorizedException, EmptyMessageTitleAndBodyException, NoSuchAlgorithmException, UsernameAlreadyExistsException {
        User admin = User.newSuperAdmin("bivan", "dooogi", "sdkfdjk@sldkf.com");
        ForumPolicy policy = new ForumPolicy(5,"*****", ForumPolicy.HashFunction.MD5, false);
        Forum forum = SuperAdminController.createNewForum(admin, policy, "forum1");
        User user = UserController.register(forum,"CHIKA","theCoolit","jkffh@gmail.com");
        SubForum sub = ContentController.addSubForum(forum, "hadars sub", admin);
        content.Thread thread = ContentController.openNewThread(forum, sub, "a", "b", admin);
        Assert.assertTrue(((SubForum) HibernateUtils.load(SubForum.class, sub.id)).viewThreads().contains(thread));
        Message msg = ContentController.reply(forum, thread.getOpeningMessage(), "hi", "SHALOM", user);
        Assert.assertTrue(((Message)HibernateUtils.load(Message.class,thread.getOpeningMessage().id)).getComments().contains(msg));
    }

    @Test
    public void testAddAndDeleteNewMessageFromController() throws UserNotAuthorizedException, NoSuchAlgorithmException, UsernameAlreadyExistsException, EmptyMessageTitleAndBodyException {
        User admin = User.newSuperAdmin("bivan", "dooogi", "sdkfdjk@sldkf.com");
        ForumPolicy policy = new ForumPolicy(5,"*****", ForumPolicy.HashFunction.MD5, false);
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
    public void testAddAndEditPostNewMessageFromController() throws UserNotAuthorizedException, NoSuchAlgorithmException, UsernameAlreadyExistsException, EmptyMessageTitleAndBodyException {
        User admin = User.newSuperAdmin("bivan", "dooogi", "sdkfdjk@sldkf.com");
        ForumPolicy policy = new ForumPolicy(5,"*****", ForumPolicy.HashFunction.MD5, false);
        Forum forum = SuperAdminController.createNewForum(admin, policy, "forum1");
        User user = UserController.register(forum, "CHIKA", "theCoolit", "jkffh@gmail.com");
        SubForum sub = ContentController.addSubForum(forum, "hadars sub", admin);
        content.Thread thread = ContentController.openNewThread(forum, sub, "a", "b", admin);
        Message msg = ContentController.reply(forum, thread.getOpeningMessage(), "hi", "SHALOM", user);
        String newTxt = "this post eddited";
        ContentController.editPost(msg, newTxt);
        Assert.assertEquals(((Message)(HibernateUtils.load(Message.class, msg.id))).getBody(), newTxt);
    }

    @Test
    public void testAddAndDeleteSubForumFromController() throws UserNotAuthorizedException {
        User admin = User.newSuperAdmin("bivan", "dooogi", "sdkfdjk@sldkf.com");
        ForumPolicy policy = new ForumPolicy(5,"*****", ForumPolicy.HashFunction.MD5, false);
        Forum forum = SuperAdminController.createNewForum(admin, policy, "forum1");
        SubForum sub = ContentController.addSubForum(forum, "hadars sub", admin);
        ContentController.deleteSubForum(forum, sub);
        Assert.assertFalse(((Forum) HibernateUtils.load(Forum.class, forum.id)).getSubForums().contains(sub));
    }

    @Test
    public void testAddAndDeleteForumFromController() throws UserNotAuthorizedException {
        User admin = User.newSuperAdmin("bivan", "dooogi", "sdkfdjk@sldkf.com");
        ForumPolicy policy = new ForumPolicy(5,"*****", ForumPolicy.HashFunction.MD5, false);
        Forum forum = SuperAdminController.createNewForum(admin, policy, "forum1");
        int forumId = forum.id;
        SuperAdminController.deleteForum(admin, forum);
        Assert.assertEquals((HibernateUtils.load(Forum.class, forumId)), null);
    }

    @Test
    public void testDeleteAllForumsFromController() throws UserNotAuthorizedException {
        User admin = User.newSuperAdmin("bivan", "dooogi", "sdkfdjk@sldkf.com");
        ForumPolicy policy = new ForumPolicy(5,"*****", ForumPolicy.HashFunction.MD5, false);
        Forum forum1 = SuperAdminController.createNewForum(admin, policy, "forum1");
        Forum forum2 = SuperAdminController.createNewForum(admin, policy, "forum2");
        Forum forum3 = SuperAdminController.createNewForum(admin, policy, "forum3");
        SuperAdminController.deleteAllForums(admin);
        String hql = "FROM Forum";
        Query query = HibernateUtils.getSession().createQuery(hql);
        List results = query.list();
        Assert.assertTrue(results.isEmpty());

    }

    @Test
    public void testEnterAsGuest() throws UserNotAuthorizedException {
        User admin = User.newSuperAdmin("bivan", "dooogi", "sdkfdjk@sldkf.com");
        ForumPolicy policy = new ForumPolicy(5,"*****", ForumPolicy.HashFunction.MD5, false);
        Forum forum1 = SuperAdminController.createNewForum(admin, policy, "forum1");

        User guest = UserController.enterAsGuest(forum1);
        Assert.assertEquals(HibernateUtils.load(User.class, guest.getId()), guest);
    }

    @Test
    public void testSendFriendRequest() throws UserNotAuthorizedException, NoSuchAlgorithmException, UsernameAlreadyExistsException {
        User admin = User.newSuperAdmin("bivan", "dooogi", "sdkfdjk@sldkf.com");
        ForumPolicy policy = new ForumPolicy(5,"*****", ForumPolicy.HashFunction.MD5, false);
        Forum forum1 = SuperAdminController.createNewForum(admin, policy, "forum1");

        User user = UserController.register(forum1, "tom", "l", "mail@mail.com");
        FriendRequest fr= UserController.sendFriendRequest(forum1, user, admin, "msg");
        Assert.assertEquals(HibernateUtils.load(FriendRequest.class, fr.id), fr);
    }

    @Test
    public void testReplyToFriendRequest() throws UserNotAuthorizedException, NoSuchAlgorithmException, UsernameAlreadyExistsException {
        User admin = User.newSuperAdmin("bivan", "dooogi", "sdkfdjk@sldkf.com");
        ForumPolicy policy = new ForumPolicy(5,"*****", ForumPolicy.HashFunction.MD5, false);
        Forum forum1 = SuperAdminController.createNewForum(admin, policy, "forum1");
        User user = UserController.register(forum1, "tom", "l", "mail@mail.com");
        FriendRequest fr= UserController.sendFriendRequest(forum1, user, admin, "msg");
        UserController.replyToFriendRequest(forum1,admin,fr,true);
        Assert.assertTrue(((User) HibernateUtils.load(User.class, user.getId())).getFriends().contains(admin));
        Assert.assertTrue(((User) HibernateUtils.load(User.class, admin.getId())).getFriends().contains(user));
    }

    @Test
    public void testReomveFriend() throws UserNotAuthorizedException, NoSuchAlgorithmException, UsernameAlreadyExistsException {
        User admin = User.newSuperAdmin("bivan", "dooogi", "sdkfdjk@sldkf.com");
        ForumPolicy policy = new ForumPolicy(5,"*****", ForumPolicy.HashFunction.MD5, false);
        Forum forum1 = SuperAdminController.createNewForum(admin, policy, "forum1");
        User user = UserController.register(forum1, "tom", "l", "mail@mail.com");
        FriendRequest fr= UserController.sendFriendRequest(forum1, user, admin, "msg");
        UserController.replyToFriendRequest(forum1,admin,fr,true);
        UserController.removeFriend(forum1,user,admin);
        Assert.assertFalse(((User) HibernateUtils.load(User.class, user.getId())).getFriends().contains(admin));
        Assert.assertFalse(((User) HibernateUtils.load(User.class, admin.getId())).getFriends().contains(user));
    }

    @Test
    public void testAddSubAndDelete() {
        User u =User.newMember("bivan","dooogi","sdkfdjk@sld;kf.com");
        //int uid = (int)HibernateUtils.save(u,null);
        Forum f = new Forum(u,new ForumPolicy(),"forum_name");
        int fid = HibernateUtils.save(f,null);
        SubForum sub = new SubForum("sub name",u,5);
        f.addSubForum(sub);
        HibernateUtils.save(f);
        Forum ormf = (Forum) HibernateUtils.load(Forum.class, fid);
        Assert.assertEquals(ormf.getSubForums().get(0), sub);
        ormf.deleteSubForum(sub);

    }

    @AfterClass
    public static void TearDown(){
        HibernateUtils.stop();
    }


}