package Engine;

import RTREE.Point;
import RTREE.Region;

import java.util.*;

public class KwQuery {
    /**
     * 查询处理
     * @param preProcess
     * @param sampling
     * @param r
     * @param k
     * @return
     */
    public ArrayList queryKeyword(PreProcess preProcess,Sampling sampling,Region r,int k){
        ArrayList<Map.Entry<String,Double>> list=null;
        ArrayList<Summary> intactSummary=new ArrayList<>();//概要
        ArrayList<Point> partialSample=new ArrayList<>();//样本
        //找到被完全包含在查询区域的单元格
        for (int y = preProcess.pointToRegion(new Point(r.pLow))[1]+1; y<preProcess.pointToRegion(new Point(r.pHigh))[1]; y++){
            for (int x=preProcess.pointToRegion(new Point(r.pLow))[0]+1;x<preProcess.pointToRegion(new Point(r.pHigh))[0];x++){
                intactSummary.add(preProcess.summary[y*4+x]);
            }
        }
        Region intact=unionRegion(intactSummary);
        //对于被查询区域部分覆盖的单元格，选择样本
        for (Point p:sampling.samPoints){
            if (r.contains(p)&&(!intact.contains(p))){
                partialSample.add(p);
            }
        }
//        System.out.println("intact size:"+intactSummary.size());
//        System.out.println("partial size:"+partialSample.size());
        HashMap<String,Integer> intactTotal=new HashMap<>();
        for (Summary s:intactSummary){
            intactTotal= mapAdd(intactTotal,s.kwSummary);
        }
        int totalNum=0;
        HashMap<String,Double> keyWord=new HashMap<>();
        for (Map.Entry<String,Integer> entry:intactTotal.entrySet()){
            totalNum+=entry.getValue();
        }
        for (Map.Entry<String,Integer> entry:intactTotal.entrySet()){
            keyWord.put(entry.getKey(),(double) entry.getValue()/totalNum);
        }
        int partialNum=partialSample.size();
        HashMap<String, Integer> sampleMap=new HashMap<>();
        for (Point p:partialSample){
            String kword=new String(p.pData);
            sampleMap.put(kword,sampleMap.getOrDefault(kword,0)+1);
        }
        HashMap<String,Double> partWord=new HashMap<>();
        for (Point p:partialSample) {
            String kword=new String(p.pData);
            partWord.put(kword,(double)sampleMap.get(kword)/partialNum);
        }
        for (Map.Entry<String,Double> entry:partWord.entrySet()){
            if (keyWord.containsKey(entry.getKey()))
                keyWord.put(entry.getKey(),(entry.getValue()+keyWord.get(entry.getKey()))/2.0);
            else
                keyWord.put(entry.getKey(),keyWord.get(entry.getKey())/2.0);
        }
        list=sortMap(keyWord);
        ArrayList<Map.Entry<String,Double>> resList=new ArrayList<>();
        for (int i=0;i<k;i++)
            resList.add(list.get(i));
        return resList;
    }

    /**
     * 累加hashmap
     * @param map1
     * @param map2
     * @return
     */
    public HashMap mapAdd(HashMap<String,Integer> map1,HashMap<String,Integer> map2){
        HashMap<String,Integer> map3=new HashMap<>();
        map3.putAll(map1);
        for (HashMap.Entry entry:map2.entrySet()){
            if (map3.containsKey(entry.getKey()))
                map3.put((String) entry.getKey(),(Integer) entry.getValue()+map1.get(entry.getKey()));
            else
                map3.put((String)entry.getKey(),(Integer)entry.getValue());
        }
//        System.out.println("map3="+map3);
        return map3;
    }

    /**
     * 合并单元格
     * @param summarys
     * @return
     */
    public Region unionRegion(ArrayList<Summary> summarys){
        Region max=new Region();
        for (Summary s:summarys){
            max=max.combinedRegion(s.region);//here
        }
        return max;
    }

    /**
     * 合并单元格的摘要
     * @param summaries
     * @return
     */
    public ArrayList unionSummary(ArrayList<Summary> summaries){
        ArrayList<Map.Entry<String,Integer>> list=new ArrayList<>();
        HashMap<String,Integer> resMap=new HashMap();
        for (Summary s:summaries){
            resMap=mapAdd(resMap,s.kwSummary);
        }
        return list;
    }

    /**
     * hashmap排序
     * @param map
     * @return
     */
    public ArrayList sortMap(HashMap map){
        //降序排序
        ArrayList<Map.Entry<String,Double>> list=new ArrayList(map.entrySet());
        Collections.sort(list, (Comparator<Map.Entry<String, Double>>) (o1, o2) -> {
            //return o1.getValue().compareTo(o2.getValue());
            return o2.getValue().compareTo(o1.getValue());
        });
        return list;
    }
}
