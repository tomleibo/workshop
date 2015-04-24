package policy;

/**
 * Created by Hadar on 24/4/2015.
 */
public class UserStatusPolicy {
    private int seniority;
    private long loginTime;
    private int numberOfMessages;

    public UserStatusPolicy(int seniority, long loginTime, int numberOfMessages){
        this.seniority = seniority;
        this.loginTime = loginTime;
        this.numberOfMessages = numberOfMessages;
    }

    public int getSeniority(){
        return seniority;
    }

    public int getNumberOfMessages(){
        return numberOfMessages;
    }

    public long getLoginTime(){
        return loginTime;
    }
}
