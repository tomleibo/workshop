package utils;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class SessionLogger {

    Map<String,Queue<String>> sessionLogs =null;

    //singleton stuff

    private static SessionLogger instance = null;

    public static SessionLogger get() {
        if (instance == null) {
            synchronized (SessionLogger.class) {
                if (instance == null) {
                    instance = new SessionLogger();
                }
            }
        }
        return instance;
    }

    //instance stuff


    private SessionLogger() {
        this.sessionLogs = new ConcurrentHashMap<>();
    }

    public void startSession(HttpSession session) {
        log(session.getId(),"Session started at "+new Date().getTime());
    }

    public void log(String session, String s) {
        if (!sessionLogs.containsKey(session)) {
            sessionLogs.put(session,new LinkedList<String>());
        }
        sessionLogs.get(session).add("session: " + session + ":   " + s);
    }

    public void log(String session, Exception e) {
        StringBuilder sb= new StringBuilder();
        sb.append("cause: "+e.getCause());
        sb.append("\nstack trace: ");
        for (StackTraceElement ste : e.getStackTrace()){
            sb.append(ste.toString()+"\n");
        }
        log(session, sb.toString());
    }

    public void stopSession(String session) {
        log(session, "session stopped at: " + new Date().getTime());
        for (String s : sessionLogs.get(session)) {
            ForumLogger.actionLog(s);
            //System.out.println(s); //for testing only!
        }
        sessionLogs.remove(session);
    }

    public Set<String> getAllActiveSessions() {
        return sessionLogs.keySet();
    }

    public Queue<String> getSessionLog(String session) {
        return sessionLogs.get(session);
    }

    public static void main (String args[]){

        StubSession ss = new StubSession("id1");
        SessionLogger.get().startSession(ss);
        System.out.println("all active sessions: \n");
        for (String s : SessionLogger.get().getAllActiveSessions()) {
            System.out.println(s);
        }
        SessionLogger.get().log(ss.getId(), "\nthis is a log message!");
        SessionLogger.get().log(ss.getId(), "this is a log message2!");
        SessionLogger.get().stopSession(ss.getId());

    }

    static class StubSession implements HttpSession {
        String id;
        public StubSession(String id) {
            this.id=id;
        }

        @Override
        public long getCreationTime() {
            return 0;
        }

        @Override
        public String getId() {
            return this.id;
        }

        @Override
        public long getLastAccessedTime() {
            return 0;
        }

        @Override
        public ServletContext getServletContext() {
            return null;
        }

        @Override
        public void setMaxInactiveInterval(int i) {

        }

        @Override
        public int getMaxInactiveInterval() {
            return 0;
        }

        @Override
        public HttpSessionContext getSessionContext() {
            return null;
        }

        @Override
        public Object getAttribute(String s) {
            return null;
        }

        @Override
        public Object getValue(String s) {
            return null;
        }

        @Override
        public Enumeration<String> getAttributeNames() {
            return null;
        }

        @Override
        public String[] getValueNames() {
            return new String[0];
        }

        @Override
        public void setAttribute(String s, Object o) {

        }

        @Override
        public void putValue(String s, Object o) {

        }

        @Override
        public void removeAttribute(String s) {

        }

        @Override
        public void removeValue(String s) {

        }

        @Override
        public void invalidate() {

        }

        @Override
        public boolean isNew() {
            return false;
        }
    }
}
