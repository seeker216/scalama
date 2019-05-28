import java.io.*;

public class preProcess {
    public static final double EPSILON = 1.192092896e-07;
    Region[] grids=new Region[16];
    double d=1000/4;

    /**
     * 从文件中读取数据
     * @param path
     * @throws IOException
     */
    public void readFromFile(String path) throws IOException {
        File file=new File(path);
        Point low=new Point(0,0);
        Point high=new Point(1000,1000);
        int gridNo=0;
        Reader reader=new FileReader(file);
        BufferedReader bf=new BufferedReader(reader);
        String line=null;
        String[] tuple=null;
        for (int y=0;y<4;y++){
            for (int x=0;x<4;x++){
                Point gLow=new Point(x*d,y*d);
                Point gHigh=new Point((x+1)*d,(y+1)*d);
                grids[gridNo++]=new Region(gLow,gHigh);
            }
        }
        while ((line=bf.readLine())!=null){
            tuple=line.split(" ");
            Point coord=new Point(Double.valueOf(tuple[0]),Double.valueOf(tuple[1]));
        }
    }

    /**
     * 把给的点映射到对应的格
     * @param p
     */
    public int pointToRegion(Point p){
        return (int)(p.pCoords[0]/d)+(int)(p.pCoords[1]/d*4);
    }
}
