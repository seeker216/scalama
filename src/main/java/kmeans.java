import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.mllib.clustering.KMeans;
import org.apache.spark.mllib.clustering.KMeansModel;
import org.apache.spark.mllib.linalg.Vector;
import org.apache.spark.mllib.linalg.Vectors;

public class kmeans {
    public static void main(String[] args) {
        SparkConf conf=new SparkConf().setAppName("K-means test");
        JavaSparkContext sc=new JavaSparkContext(conf);
        JavaRDD<String> data=sc.textFile("D:\\codeWorkspace\\RTT\\testWrite.dat");
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
        int iter=20;
        int k=5;
        for (int i=1;i<k;i++) {
            KMeansModel clusters = KMeans.train(parsedData.rdd(), i, iter);
            double wssse = clusters.computeCost(parsedData.rdd());
            System.out.println("se= " + wssse +" with k= "+i);
            Vector[] vs = clusters.clusterCenters();
//        int clusidx=clus.predict(Vectors.dense())
            for (Vector v : vs)
                System.out.println("clus center=" + v);
        }
        sc.close();
    }

}
