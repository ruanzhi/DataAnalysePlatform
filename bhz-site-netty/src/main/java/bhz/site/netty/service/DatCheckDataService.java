package bhz.site.netty.service;

import java.util.List;

import bhz.site.netty.entity.DatCheckData;
import bhz.site.netty.mapper.DatCheckDataMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class DatCheckDataService  {

	@Autowired
	private DatCheckDataMapper mapper;
	
//	public String generateKey(){
//		String preFix = this.mapper.generateKeyPreFix();
//		String uid = UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
//		return preFix + uid.substring(12) ;
//	}
	
	public int insert(DatCheckData record){
		return this.mapper.insert(record);
	}
	
	public List<DatCheckData> findNeedSync(){
		return this.mapper.findNeedSync();
	}
	
	public void updateSync(String checkNo){
		this.mapper.updateSync(checkNo);
	}
	
}
