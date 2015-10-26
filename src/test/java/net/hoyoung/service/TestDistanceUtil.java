package net.hoyoung.service;

import net.hoyoung.utils.DistanceUtil;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/10/26.
 */
public class TestDistanceUtil {
    ApplicationContext ctx = null;
    JdbcTemplate jdbcTemplate = null;
    @Before
    public void before(){
        String config = "src/main/resources/application-context.xml";
        ctx = new FileSystemXmlApplicationContext(config);
        jdbcTemplate = ctx.getBean(JdbcTemplate.class);
        System.out.println("init complete");
    }
    @Test
     public void test(){
        double dis = 100000.0d;
        double d = DistanceUtil.distance2lngDiff(dis);
        System.out.println(d);

        dis = DistanceUtil.GetLongDistance(109,18, 119,18);
        System.out.println(dis);

    }
    @Test
    public void test2(){
        long start = System.currentTimeMillis();
        DistanceCalculateService service = ctx.getBean(DistanceCalculateService.class);
        List<Map<String,Object>> companies = service.calculate();
//        for (int i=0;i<10;i++){
//            System.out.println(companies.get(i));
//        }
        Collections.sort(companies, new Comparator<Map<String, Object>>() {
            @Override
            public int compare(Map<String, Object> o1, Map<String, Object> o2) {

                Object oa = o1.get("org_num");
                Object ob = o2.get("org_num");

//                return  (int)Math.round(Math.random()*10-5);
                return (Integer)ob-(Integer)oa;//升序排列
            }
        });
        for (int i=0;i<10;i++){
            System.out.println(companies.get(i));
        }
        long end = System.currentTimeMillis();
        System.out.println("cost "+(end-start)/1000+" seconds");

    }
}
