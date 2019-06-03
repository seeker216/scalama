package Engine;

import RTREE.Point;
import RTREE.Region;

import java.io.IOException;
import java.lang.*;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException {
        String path="D:\\codeWorkspace\\RTT\\test103.dat";
        PreProcess preProcess=new PreProcess();
        preProcess.readFromFile(path);
        Sampling sampling=new Sampling();
        sampling.sample(path);
        Point low=new Point(5,5);
        Point high=new Point(990,990);
        Region r=new Region(low,high);
        KwQuery kwQuery=new KwQuery();
        ArrayList result=kwQuery.queryKeyword(preProcess,sampling,r,5);
        System.out.println(result);
    }
}
