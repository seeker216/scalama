package Engine;

import RTREE.Point;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.*;

import java.util.ArrayList;

public class Sampling {
    JavaRDD<String> samples;
    ArrayList<Point> samPoints;
    public void sample(String path){
        SparkConf conf = new SparkConf().setAppName("Engine.Sampling");
        JavaSparkContext sc = new JavaSparkContext(conf);
//        JavaRDD<String> javaRDD = sc.textFile("D:\\codeWorkspace\\RTT\\test103.dat");
        JavaRDD<String> javaRDD = sc.textFile(path);
        //设置抽取后不放回和抽取概率
        JavaRDD<String> sample = javaRDD.sample(false, 0.1, 123);
        long sampleDataSize = sample.count();
        long rawDataSize = javaRDD.count();
//        System.out.println(rawDataSize + " and after the sampling: " + sampleDataSize);
//        System.out.println("RDD is:" + sample.collect());

//        //取指定数量的随机数据
//        List<String> list = javaRDD.takeSample(false, 3);
//        System.out.println(list);
//
//        //取排序好的指定数量的数据
//        List<String> orderList = javaRDD.takeOrdered(5);
//        sc.stop();
//        System.out.println(orderList);
        samples=sample.sample(false,0.1,123);
        samPoints=new ArrayList<>();
        for (String s:samples.collect()){
            String[] ss=s.split(" ");
            Point p=new Point(ss);
            samPoints.add(p);
        }
    }

    public static void main(String[] args) {
        Sampling sampling=new Sampling();
        sampling.sample("D:\\codeWorkspace\\RTT\\test102.dat");
        for (String s:sampling.samples.collect()){
            String[] ss=s.split(" ");
            Point p=new Point(ss);
            sampling.samPoints.add(p);
        }
    }
}
