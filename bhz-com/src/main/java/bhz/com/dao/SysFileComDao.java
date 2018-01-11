package bhz.com.dao;

import java.sql.Timestamp;
import java.util.*;

import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import bhz.com.entity.SysFile;
import bhz.com.util.FastJsonConvert;

import com.alibaba.fastjson.JSONObject;

@Repository
public class SysFileComDao extends BaseJdbcDao {

    /**
     * 系统文件行映射器
     */
    private static final SysFileRowMapper SYS_FILE_ROW_MAPPER = new SysFileRowMapper();

    /**
     * <B>方法名称：</B>插入系统文件<BR>
     * <B>概要说明：</B><BR>
     *
     * @param data 系统文件
     * @return int 插入数量
     */
    public int insert(SysFile data) {
        if (data == null) {
            return 0;
        }
        String sql = "INSERT INTO sys_file(ID, TYPE, NAME, EXT, BYTES, DATA_PATH, DATA_GROUP, EXPIRED, DESC_INFO, UPDATE_BY, UPDATE_TIME) VALUES (?, ?, ?, ?, ?, ?, ?, now(), ?, ?, now())";
        Object[] args = new Object[9];
        args[0] = data.getKey();
        args[1] = data.getType();
        args[2] = data.getName();
        args[3] = data.getExt();
        args[4] = data.getBytes();
        args[5] = data.getDataPath();
        args[6] = data.getDataGroup();
        // args[7] =new Timestamp(data.getExpired().getTime());
        args[7] = data.getDescInfo();
        args[8] = data.getUpdateBy();
        return super.getJdbcTemplate().update(sql.toString(), args);
    }

    /**
     * <B>方法名称：</B>清除系统文件过期限制<BR>
     * <B>概要说明：</B><BR>
     *
     * @param key 文件键值
     * @return int 更新数量
     */
    public int clearExpired(String key) {
        if (StringUtils.isBlank(key)) {
            return 0;
        }
        return super.getJdbcTemplate().update("UPDATE sys_file SET EXPIRED = NULL WHERE ID = ?", key);
    }

    /**
     * <B>方法名称：</B>更新系统文件过期时间<BR>
     * <B>概要说明：</B><BR>
     *
     * @param key     文件键值
     * @param expired 过期时间（null为当前时间）
     * @return int 更新数量
     */
    public int expire(String key, Date expired) {
        if (StringUtils.isBlank(key)) {
            return 0;
        }
        if (expired == null) {
            return super.getJdbcTemplate().update("UPDATE sys_file SET EXPIRED = now() WHERE ID = ?", key);
        }
        return super.getJdbcTemplate().update("UPDATE sys_file SET EXPIRED = ? WHERE ID = ?", expired, key);
    }

    /**
     * <B>方法名称：</B>删除系统文件<BR>
     * <B>概要说明：</B><BR>
     *
     * @param key 文件键值
     * @return int 删除数量
     */
    public int delete(String key) {
        if (StringUtils.isBlank(key)) {
            return 0;
        }
        return super.getJdbcTemplate().update("DELETE FROM sys_file WHERE ID = ?", key);
    }

    /**
     * <B>方法名称：</B>获取系统文件<BR>
     * <B>概要说明：</B><BR>
     *
     * @param key 文件键值
     * @return SysFile 系统文件
     */
    public SysFile get(String key) {
        if (StringUtils.isBlank(key)) {
            return null;
        }
        List<SysFile> dataList = getList(key.split(","));
        if (dataList == null || dataList.size() < 1) {
            return null;
        }
        return dataList.get(0);
    }

    /**
     * <B>方法名称：</B>获取系统文件列表<BR>
     * <B>概要说明：</B><BR>
     *
     * @param keys 文件键值集合
     * @return List<SysFile> 系统文件列表
     */
    public List<SysFile> getList(String[] keys) {
        List<Object> args = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT ID, TYPE, NAME, EXT, BYTES, DATA_PATH, DATA_GROUP, EXPIRED, DESC_INFO, UPDATE_BY, UPDATE_TIME ");
        sql.append(" FROM sys_file WHERE ID IN ");
        super.appendSqlIn(sql, args, keys);
        sql.append(" ORDER BY TYPE, NAME");
        return super.getJdbcTemplate().query(sql.toString(), SYS_FILE_ROW_MAPPER, args.toArray());
    }


    /**
     * <B>方法名称：</B>清空文件缓存方法<BR>
     * <B>概要说明：</B>清空文件缓存方法<BR>
     *
     * @return List<String> 主键KEY集合
     */
    public List<String> clearFileKeys() {

        //先查询数据
        List<JSONObject> result = super.queryForJsonList("select ID,DATA_PATH from sys_file where EXPIRED is not null");
        //在删除数据
        if (result != null && result.size() > 0) {
            StringBuffer sql = new StringBuffer("delete from sys_file where ID in(");
            StringBuffer delKeys = new StringBuffer();
            List<String> delPathList = new ArrayList<>();
            for (int i = 0; i < result.size(); i++) {
                JSONObject row = result.get(i);
                if (i < result.size() - 1) {
                    delKeys.append("'").append(row.get("ID")).append("'").append(",");
                } else if (i == result.size() - 1) {
                    delKeys.append("'").append(row.get("ID")).append("'");
                }
                delPathList.add(row.get("DATA_PATH").toString());
            }

            sql.append(delKeys.toString()).append(")");
            System.out.println("定时删除ids:"+sql.toString());
            int delResult = super.getJdbcTemplate().update(sql.toString());
            if (delResult > 0) {
                return delPathList;
            }
        }
        return Collections.emptyList();
    }

}
