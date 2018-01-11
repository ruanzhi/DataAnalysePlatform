package bhz.mst.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import bhz.com.dao.MstCodeComDao;
import bhz.com.util.FastJsonConvert;

import com.alibaba.fastjson.JSONObject;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;

@Service
public class MstCodeComService {

    /** 缓存标识前缀 */
    private static final String CACHE_ID = "MST_CODE";
    
    @Autowired
	private MstCodeComDao mstCodeComDao;

	@Resource
	private JedisPool jedisPool;
	
	public List<JSONObject> getTypeList(String type){
		Jedis resource = jedisPool.getResource();
		String ret = resource.hget(CACHE_ID, type);
		if(ret == null){
			System.out.println("----查询数据库----");
			List<JSONObject> list = this.mstCodeComDao.getTypeList(type);
			resource.hset(CACHE_ID, type, list.toString());
			ret = list.toString();
		}
		return FastJsonConvert.convertJSONToArray(ret, JSONObject.class);
	}
	
	//清空指定的hash key 自己实现...
	public void clearTypeList(String type){
		
	}
	
	
	
	
}
