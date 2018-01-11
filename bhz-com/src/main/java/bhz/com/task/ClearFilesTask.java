package bhz.com.task;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import bhz.com.service.SysFileComService;
import bhz.com.util.FastDFSClientUtils;

@Component("clearFilesTask")
public class ClearFilesTask {

	private SysFileComService sysFileComService;

	@Autowired
	public void setSysFileComService(SysFileComService sysFileComService) {
		this.sysFileComService = sysFileComService;
	}

	@Scheduled(initialDelay=5000, fixedDelay=1000*30)
	public void clearFiles(){
		System.out.println("定时清理数据库系统文件...");
		List<String> paths = this.sysFileComService.clearFileKeys();
		for (String p : paths) {
			String fileId = p.substring(this.sysFileComService.FASTDFS_BASEURL.length());
			String groupName = fileId.substring(0, fileId.indexOf("/"));
			System.out.println("fileId: " + fileId + ", groupName: " + groupName);
			int code = FastDFSClientUtils.delete(groupName, fileId);
			System.out.println("删除标识代码: " + code);
		}
	}
	
}
