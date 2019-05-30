import java.beans.Transient;
import java.io.IOException;
import java.util.*;
import java.lang.*;
public class Main {
    public static void main(String[] args) throws IOException {
        String path="D:\\codeWorkspace\\RTT\\test102.dat";
        PreProcess preProcess=new PreProcess();
        preProcess.readFromFile(path);
        for (Summary s:preProcess.summary)
            System.out.println(s.summaryList);
    }
}
