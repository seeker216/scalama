import org.apache.spark.SparkConf;
import org.apache.spark.api.java.*;
import java.io.*;
import java.util.List;
import org.apache.spark.mllib.*;

public class simple {
    public static void main(String[] args) throws IOException {
//        String file="D:/hello.txt";
//        SparkConf conf=new SparkConf().setAppName("Simple Application");
//        JavaSparkContext sc=new JavaSparkContext(conf);
//        JavaRDD<String> data=sc.textFile(file).cache();
//        long numa=data.filter(new Function<String, Boolean>() {
//            public Boolean call(String s) throws Exception {
//                return s.contains("i");
//            }
//        }).count();
//        long numb=data.filter(new Function<String, Boolean>() {
//            public Boolean call(String s) throws Exception {
//                return s.contains("jump");
//            }
//        }).count();
//        System.out.println("lines with a:"+numa+",lines with b:"+numb);
//        sc.stop();
        SparkConf conf=new SparkConf().setAppName("JavaWordCount");
        JavaSparkContext sc = new JavaSparkContext(conf);
        JavaRDD<String> javaRDD = sc.textFile("D:/test1.txt");
        //以fraction的几率去取数据，随机数种子自己设定，也可以不设定
        JavaRDD<String> sample = javaRDD.sample(false, 0.5, 123);
        long sampleDataSize = sample.count();
        long rawDataSize = javaRDD.count();
        System.out.println(rawDataSize + " and after the sampling: " + sampleDataSize);
        System.out.println("RDD is:"+sample.collect());

        //取指定数量的随机数据
        List<String> list = javaRDD.takeSample(false, 3);
        System.out.println(list);

        //取排序好的指定数量的数据
        List<String> orderList = javaRDD.takeOrdered(5);
        sc.stop();
//        System.out.println(orderList);
//        File file=new File("D:/testWrite.txt");
//        writeTest(file);
//        readTest(file);
    }
//    public static void writeTest(File file) throws IOException {
//        DecimalFormat df = new DecimalFormat("#.00");
//        Writer writer=new FileWriter(file,true);
//        for (int i=0;i<10;i++){
//            writer.write(df.format(Math.random()*100)+" "+df.format(Math.random()*100)+" address\r\n");
//        }
//        writer.close();
//    }
//    public static void readTest(File file) throws IOException {
//        Reader reader=new FileReader(file);
//        BufferedReader bf=new BufferedReader(reader);
//        String s=null;
//        String[] ss=null;
//        while ((s=bf.readLine())!=null){
//            ss=s.split(" ");
//            System.out.println(Double.valueOf(ss[0])+" "+Double.valueOf(ss[1])+" "+ss[2]);
//        }
//    }
}
