package utils;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;
import java.util.*;

public class SessionLogger {

    Map<HttpSession,Queue<String>> sessionLogs =null;

    public SessionLogger() {
        this.sessionLogs = new HashMap<>();
    }

    public void log(HttpSession session, String s) {
        if (!sessionLogs.containsKey(session)) {
            sessionLogs.put(session,new LinkedList<String>());
        }
        sessionLogs.get(session).add(s);
    }

    public void log(HttpSession session, Exception e) {
        StringBuilder sb= new StringBuilder();
        sb.append("cause: "+e.getCause());
        sb.append("\nstack trace: ");
        for (StackTraceElement ste : e.getStackTrace()){
            sb.append(ste.toString()+"\n");
        }
        log(session,sb.toString());
    }

    public void spill(HttpSession session) {
        for (String s : sessionLogs.get(session)) {
            ForumLogger.actionLog(s);
        }
    }

    public static void main (String args[]){
        SessionLogger sl = new SessionLogger();
        StubSession ss = new StubSession("id1");
        sl.log(ss,"this is a log message!");
        sl.log(ss,"this is a log message2!");
        sl.spill(ss);

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
