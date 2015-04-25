package users.userState;

import content.SubForum;

import java.util.ArrayList;
import java.util.List;

public class ModeratorState extends UserState {

    private List<SubForum> managedSubForumsList;

    public ModeratorState() {
        this.managedSubForumsList = new ArrayList<>();
    }

    @Override
    public boolean addManagedSubForum(SubForum subForum) {
        return managedSubForumsList.add(subForum);
    }

    @Override
    public boolean removeManagedSubForum(SubForum subForum) {
        return managedSubForumsList.remove(subForum);
    }

    @Override
    public List<SubForum> getManagedSubForums() {
        return managedSubForumsList;
    }

    @Override
    public int getNumberOfManagedSubForums() {
        return managedSubForumsList.size();
    }

    @Override
    public boolean isModerator(){
        return true;
    }

    @Override
    public boolean isMember(){
        return true;
    }

    @Override
    public String toString() {
        return "Moderator";
    }
}
