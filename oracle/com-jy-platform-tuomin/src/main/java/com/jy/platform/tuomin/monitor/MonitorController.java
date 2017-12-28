package com.jy.platform.tuomin.monitor;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.jy.platform.tuomin.Main;

import freemarker.template.Template;

//@RestController--适用于返回json的rest服务
//@RestController
//@Controller--适用于普通的mvc
@Controller
@Scope("singleton")
@EnableAutoConfiguration
public class MonitorController {
	
	/**
	 * 脱敏处理的线程是否已经启动。
	 */
	//private boolean started = false;
	
	private static Logger logger = LoggerFactory.getLogger(MonitorController.class);
	
	/**
	 * TODO 打包成jar的场合，jsp处理有问题，这个方法已废弃
	 * @return
	 */
	public ModelAndView main2() {
		List<MonitorBean> list = MonitorService.getThreadStatus();
		ModelAndView mv = new ModelAndView();
		mv.addObject("list", list);
		mv.setViewName("index");
		return mv;
	}

	/**
	 * 显示监控页面
	 * @param resp HttpServletResponse
	 */
	@RequestMapping("/")
	public void home(HttpServletResponse resp) {
		try {
			@SuppressWarnings("deprecation")
			freemarker.template.Configuration cfg = new freemarker.template.Configuration();
			//cfg.setDirectoryForTemplateLoading(new File(MonitorController.class.getResource("/").getPath()));
			cfg.setClassLoaderForTemplateLoading(MonitorController.class.getClassLoader(), "/templates");
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("beans", MonitorService.getThreadStatus());
			map.put("currentTime", new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()));
			map.put("started", Main.isStarted());
			Template t = cfg.getTemplate("home.jsp"); 
			resp.setCharacterEncoding("UTF-8");
			t.process(map, resp.getWriter());
		} catch (Exception e) {
			logger.error("显示监控页面异常", e);
			resp.setCharacterEncoding("UTF-8");
			try {
				e.printStackTrace(resp.getWriter());
			} catch (IOException e1) {
				logger.error("向页面输出错误信息异常", e1);
			}
		}
	}
	
	/**
	 * 启动脱敏处理线程
	 * @param resp HttpServletResponse
	 */
	@RequestMapping("/start")
	public void start(HttpServletResponse resp) {
		logger.info("请求启动脱敏处理线程...");
		String msg;
		if (!Main.isStarted()) {
			Main.setStarted(true);
			Main.main(new String[]{});
			msg = "started success";//"启动成功";
		} else {
			msg = "the program has already started";//"脱敏进程已经在执行中了";
		}
		resp.setCharacterEncoding("UTF-8");
		try {
			resp.getWriter().println(msg);
		} catch (IOException e1) {
			logger.error("向页面输出信息异常", e1);
		}
	}
}
