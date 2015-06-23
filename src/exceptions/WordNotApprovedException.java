package exceptions;

/**
 * Created by thinkPAD on 6/23/2015.
 */
public class WordNotApprovedException extends Exception {
    public String word;

    public WordNotApprovedException(String word) {
        super();
        this.word = word;
    }
}
