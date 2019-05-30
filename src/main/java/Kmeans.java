import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.mllib.clustering.KMeans;
import org.apache.spark.mllib.clustering.KMeansModel;
import org.apache.spark.mllib.linalg.Vector;
import org.apache.spark.mllib.linalg.Vectors;
import java.io.Serializable;

public class Kmeans implements Serializable {
    public void cluster(String path) {
        SparkConf conf=new SparkConf().setAppName("K-means test");
        JavaSparkContext sc=new JavaSparkContext(conf);
//        JavaRDD<String> data=sc.textFile("D:\\codeWorkspace\\RTT\\testWrite.dat");
        JavaRDD<String> data=sc.textFile(path);
        JavaRDD<Vector> parsedData= (JavaRDD<Vector>) data.map(new Function<String, Vector>() {
            public Vector call(String s){
                String[] array=s.split(" ");
                double[] values=new double[2];
                for (int i=0;i<1;i++)
                    values[i]=Double.parseDouble(array[i]);
                return Vectors.dense(values);
            }
        });
        parsedData.cache();
        //每次迭代20次
        int iter=20;
        //选k=1-10，取效果最好的k
        int candiK=10;
        int k=0;
        double minErr=Double.MAX_VALUE;
        for (int i=1;i<=candiK;i++) {
            KMeansModel clusters = KMeans.train(parsedData.rdd(), i, iter);
            double err = clusters.computeCost(parsedData.rdd());
            if (err<minErr){
                minErr=err;
                k=i;
            }
            System.out.println("se= " + err +" with k= "+i);
//            Vector[] vs = clusters.clusterCenters();
//        int clusidx=clus.Predict(Vectors.dense())
//            for (Vector v : vs)
//                System.out.println("clus center=" + v);
        }
        System.out.println("k="+k);
        KMeansModel clusters=KMeans.train(parsedData.rdd(),k,iter);
        Vector[] vs=clusters.clusterCenters();
        for (Vector v : vs)
            System.out.println("clus center=" + v);
        sc.close();
    }

}
