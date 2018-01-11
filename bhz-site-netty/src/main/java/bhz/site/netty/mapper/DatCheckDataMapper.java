package bhz.site.netty.mapper;

import bhz.site.netty.entity.DatCheckData;

import java.util.List;


public interface DatCheckDataMapper {
    int deleteByPrimaryKey(String checkNo);

    int insert(DatCheckData record);

    int insertSelective(DatCheckData record);

    DatCheckData selectByPrimaryKey(String checkNo);

    int updateByPrimaryKeySelective(DatCheckData record);

    int updateByPrimaryKey(DatCheckData record);
    
    //String generateKeyPreFix();
    
    List<DatCheckData> findNeedSync();
    
    void updateSync(String checkNo);
}