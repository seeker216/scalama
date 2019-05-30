import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class KwQuery {
    public List queryKeyword(List<HashMap> summary,Region r,int k){
        List list=null;
        ArrayList<HashMap<String,Integer>> intactGrids=new ArrayList<>();
        PreProcess preProcess=new PreProcess();
        for (int y=preProcess.pointToRegion(new Point(r.pLow))[1]+1;y<preProcess.pointToRegion(new Point(r.pHigh))[1];y++){
            for (int x=preProcess.pointToRegion(new Point(r.pLow))[0]+1;x<preProcess.pointToRegion(new Point(r.pHigh))[0];x++){
                intactGrids.add(summary.get(y*4+x));
            }
        }

        return list;
    }

    public static void main(String[] args) {
        Point low=new Point(200,200);
        Point high=new Point(600,600);
        Region r=new Region(low,high);

    }

    public HashMap mapAdd(HashMap<String,Integer> map1,HashMap<String,Integer> map2){
//        HashMap<String,Integer> map1=new HashMap<>();
//        map1.put("a",5);
//        map1.put("b",3);
//        map1.put("c",1);
//        System.out.println("map1="+map1);
//        HashMap<String,Integer> map2=new HashMap<>();
//        map2.put("a",2);
//        map2.put("d",3);
//        System.out.println("map2="+map2);
        HashMap<String,Integer> map3=new HashMap<>();
        map3.putAll(map1);
        for (HashMap.Entry entry:map2.entrySet()){
            if (map3.containsKey(entry.getKey()))
                map3.put((String) entry.getKey(),(Integer) entry.getValue()+map1.get(entry.getKey()));
        }
        System.out.println("map3="+map3);
        return map3;
    }
}
