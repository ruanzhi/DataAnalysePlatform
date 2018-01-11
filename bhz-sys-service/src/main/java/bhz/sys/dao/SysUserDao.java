package bhz.sys.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import bhz.com.dao.BaseJdbcDao;

import com.alibaba.fastjson.JSONObject;

@Repository("sysUserDao")
public class SysUserDao extends BaseJdbcDao {
	
	private static final String SQL_TABLE_NAME = "sys_user";
	private static final String SQL_SELECT_SYS_USER = "SELECT * FROM sys_user";
	private static final String SQL_SELECT_SYS_USER_BYID = "SELECT * FROM sys_user WHERE USER_ID = ?";
	
	public List<JSONObject> getList() throws Exception {
		return super.queryForJsonList(SQL_SELECT_SYS_USER);
	}
	
	public JSONObject getById(String id){
		return super.queryForJsonObject(SQL_SELECT_SYS_USER_BYID, id);
	}
	
	public int insert(JSONObject jsonObject) throws Exception {
		return super.insert(SQL_TABLE_NAME, jsonObject);
	}
	
}
