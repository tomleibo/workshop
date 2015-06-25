package utils;

import java.util.ArrayList;
import java.util.List;

public class ContentDigestUtils {
    static String[] words = {"anal","anus","arse","ass","ballsack","balls","bastard","bitch","biatch","bloody",
            "blowjob","blow job","bollock","bollok","boner","boob","bugger","bum","butt","buttplug","clitoris",
            "cock","coon","crap","cunt","damn","dick","dildo","dyke","fag","feck","fellate","fellatio","felching",
            "fuck","f u c k","fudgepacker","fudge packer","flange","Goddamn","God damn","hell","homo","jerk","jizz"
            ,"knobend","knob end","labia","lmao","lmfao","muff","nigger","nigga","omg","penis","piss","poop","prick"
            ,"pube","pussy","queer","scrotum","sex","shit","s hit","sh1t","slut","smegma","spunk","tit","tosser","turd"
            ,"twat","vagina","wank","whore","wtf"};

    public static String isTextOk(String text) {
        for (String word : words) {
            if (text.contains(word) && (text.contains(" "+word+" ") || text.contains(" "+word+" ") || text.startsWith(word+" ") || text.endsWith(" "+word) || text.equals(word))) {
                return word;
            }
        }
        return null;
    }

    public static float percentageOfRelevance(List<content.Thread> threads, String content) {
        float numberOfWordsMatched = 0;
        String[] words = splitToWords(content);
        int numberOfWordsInText = words.length;
        for (int i=0;i<10 && i<threads.size();i++) {
            for (String word : words) {
                if (threads.get(i).getOpeningMessage().getBody().contains(word)) {
                    numberOfWordsInText++;
                }
            }
        }
        return ((float)numberOfWordsInText)*10/numberOfWordsMatched;
    }

    private static String[] splitToWords(String content) {
        String[] words = content.split(" ");
        return words;
    }

    public static void main(String[] args) {
        System.out.println(isTextOk("fuck shit"));
        System.out.println(isTextOk("balls"));

    }
}
