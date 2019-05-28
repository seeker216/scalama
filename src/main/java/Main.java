import java.beans.Transient;
import java.util.*;
import java.lang.*;
public class Main {
    public static void main(String[] args) {
        String path="D:\\codeWorkspace\\RTT\\test103.dat";
        Sampling sp=new Sampling();
        sp.sample(path);
        Kmeans km=new Kmeans();
        km.cluster(path);
    }
}
