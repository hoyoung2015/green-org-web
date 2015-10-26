package net.hoyoung.service;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/10/26.
 */
public class TestImportData {
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
    public void test() throws IOException {
        List<String> sqlLines = FileUtils.readLines(new File("company_info_to_sqllite.csv"), "UTF-8");
        String valueFields = sqlLines.get(0).replace("\"","'");
        System.out.println(valueFields);
        for (int i = 1; i < sqlLines.size(); i++) {
            String sql = sqlLines.get(i);//.replace("\"","'");
            System.out.println(sql);
            int status = jdbcTemplate.update("INSERT INTO company_info("+valueFields+") VALUES ("+sql+")");
            System.out.println("status="+status);
        }
    }
    @Test
    public void test2() {
        List<Map<String,Object>> list = jdbcTemplate.queryForList("SELECT * FROM company_info");
        for (Map<String,Object> map : list){
            System.out.println(map.get("name"));
        }
    }
    @Test
    public void test3() {
        long start = System.currentTimeMillis();
        DistanceCalculateService service = ctx.getBean(DistanceCalculateService.class);
        service.calculate();
        long end = System.currentTimeMillis();
        System.out.println("cost "+(end-start)/1000+" seconds");
    }
    @Test
    public void test4() throws IOException {
        List<String> sqlLines = FileUtils.readLines(new File("data/green_org.csv"), "UTF-8");
        String valueFields = sqlLines.get(0).replace("\"","'");
        System.out.println(valueFields);
        for (int i = 1; i < sqlLines.size(); i++) {
            String sql = sqlLines.get(i);//.replace("\"","'");
            System.out.println(sql);
            int status = jdbcTemplate.update("INSERT INTO green_org("+valueFields+") VALUES ("+sql+")");
            System.out.println("status="+status);
        }
    }
}
