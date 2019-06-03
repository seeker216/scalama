package Engine;

import RTREE.Point;
import RTREE.Region;

import java.io.*;
import java.util.*;

public class PreProcess {
    public static final double EPSILON = 1.192092896e-07;
//    RTREE.Region[] grids=new RTREE.Region[16];
    Summary[] summary=new Summary[16];
    double d=1000/4;
//    List<Map.Entry<String,Integer>>[] summary=new List[16];
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
        HashMap<String,Integer>[] map=new HashMap[16];
        for (int y=0;y<4;y++){
            for (int x=0;x<4;x++){
                Point gLow=new Point(x*d,y*d);
                Point gHigh=new Point((x+1)*d,(y+1)*d);
                summary[gridNo]=new Summary();
                summary[gridNo++].region=new Region(gLow,gHigh);
            }
        }
        while ((line=bf.readLine())!=null){
            tuple=line.split(" ");
            Point coord=new Point(Double.valueOf(tuple[0]),Double.valueOf(tuple[1]));
            int regionNo=pointToRegion(coord)[2];
            if (map[regionNo]==null)
                map[regionNo]=new HashMap<>();
            map[regionNo].put(tuple[2],map[regionNo].getOrDefault(tuple[2],0)+1);
        }
        reader.close();
        adjustSummary(map);
    }

    /**
     * 把给的点映射到对应的格
     * @param p
     */
    public int[] pointToRegion(Point p){
        int[] res =new int[3];
        res[0]=(int)(p.pCoords[0]/d);
        res[1]=(int)(p.pCoords[1]/d);
        res[2]=res[0]+res[1]*4;
        return res;
    }

    /**
     * 生成概要
     * @param map
     */
    public void adjustSummary(Map<String,Integer>[] map){
        for (int i=0;i<map.length;i++){
//            summary[i]=new Summary();
            summary[i].kwSummary=(HashMap<String, Integer>) map[i];
            summary[i].summaryList = new ArrayList<Map.Entry<String, Integer>>(map[i].entrySet());
            Collections.sort(summary[i].summaryList, new Comparator<Map.Entry<String, Integer>>() {
                @Override
                public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                    return o2.getValue().compareTo(o1.getValue());
                }
            });
        }
    }


}
