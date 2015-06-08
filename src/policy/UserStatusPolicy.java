package policy;

import javax.persistence.*;

@Entity
public class UserStatusPolicy {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "seniority")
    private int seniority;
    @Column(name = "login_time")
    private long loginTime;
    @Column(name = "number_of_messages")
    private int numberOfMessages;

    public UserStatusPolicy(){

    }

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
