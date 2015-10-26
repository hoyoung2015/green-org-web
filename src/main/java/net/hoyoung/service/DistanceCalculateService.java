package net.hoyoung.service;

/**
 * Created by Administrator on 2015/10/26.
 */

import net.hoyoung.utils.DistanceUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

@Service
public class DistanceCalculateService {
    private Logger log = Logger.getLogger(getClass());
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private List<Map<String,Object>> companies;
    private List<Map<String,Object>> greenOrgs;
    @PostConstruct
    public void init(){
        log.info(getClass().getName()+" init");
        companies = jdbcTemplate.queryForList("SELECT stock_code,name,sname,pos_x,pos_y FROM company_info");
        greenOrgs = jdbcTemplate.queryForList("SELECT id as org_id,title,lng,lat FROM green_org");
    }

    public List<Map<String,Object>> calculate(){
        double dis = 100000;
        double lngDiff = DistanceUtil.distance2lngDiff(dis);
        double latDiff = DistanceUtil.distance2latDiff(dis);
        int max = 0;

        //清空
        int status = jdbcTemplate.update("DELETE FROM company_org_num WHERE 1=1");
//        if(status != 1) return;
        for (Map<String,Object> company : companies){
            Object t_comPos = company.get("pos_x");
            if(t_comPos instanceof String) {
                company.put("org_num",0);
                continue;
            }
            double comLng = (Double)t_comPos;//经度

            t_comPos = company.get("pos_y");
            if(t_comPos instanceof String){
                company.put("org_num",0);
                continue;
            }
            double comLat = (Double)t_comPos;//纬度

            int count = 0;

            for (Map<String,Object> map : greenOrgs){
                double orgLng = (Double)map.get("lng");
                double orgLat = (Double)map.get("lat");
                if(Math.abs(orgLng-comLng)>lngDiff) continue;//经度差距太大，淘汰
                if(Math.abs(orgLat-comLat)>latDiff) continue;//纬度差距太大，淘汰
                //计算距离
                if(DistanceUtil.GetLongDistance(comLng,comLat,orgLng,orgLat)>dis) continue;//距离过大，淘汰
                count++;
            }
            company.put("org_num",count);
            /*if(count > 0){
                status = jdbcTemplate.update("INSERT INTO company_org_num(stock_code,name,sname,org_num) VALUES (?,?,?,?)",
                        company.get("stock_code"),
                        company.get("name"),
                        company.get("sname"),
                        count);
            }*/
        }

        return companies;
    }

}
