package com.cm.controller;


import com.cm.entity.Maporg;
import com.cm.security.LoginManage;
import com.cm.service.MaporgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Scope("prototype")
@Controller
@RequestMapping("/maporg")
public class MaporgController {

	@Autowired
	private ResultObj result;

	@Autowired
	private LoginManage loginManage;

	@Autowired
	private MaporgService service;

	@RequestMapping(value = "/addup", method = RequestMethod.POST)
	@ResponseBody
	public Object addup(Maporg maporg, HttpServletRequest request) {
		if (!loginManage.isUserLogin(request)) {
			return result.setStatus(-1, "no login");
		}
		
		try {

			if (maporg.getUrl() != null && maporg.getUrl().isEmpty()
					&& maporg.getLatitude() != 0 && maporg.getLongitude() != 0) {
				if (maporg.getId() > 0) {
					service.update(maporg);
				}
				service.add(maporg);
			} else {
				return result.setStatus(-2, "对不起！地图数据有误。请重新操作");
			}

		} catch (Exception e) {
			e.printStackTrace();
			return result.setStatus(-4, "exception");
		}
		return result.setStatus(0, "ok");
	}

	@RequestMapping(value = "/get", method = RequestMethod.GET)
	@ResponseBody
	public Object get(HttpServletRequest request,HttpServletResponse response) {
		/*if (!loginManage.isUserLogin(request)) {
			return result.setStatus(-1, "no login");
		}*/
		try {
			List<Maporg> mg = service.get();
			result.put("data", mg);
		} catch (Exception e) {
			e.printStackTrace();
			return result.setStatus(-4, "exception");
		}
		return result.setStatus(0, "ok");
	}

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	@ResponseBody
	public Object upload(HttpServletRequest request,
			HttpServletResponse response) {
		if (!loginManage.isUserLogin(request)) {
			return result.setStatus(-1, "no login");
		}
		
		try {

			String latitude = request.getParameter("latitude");
			String longitude = request.getParameter("longitude");
			int type = Integer.parseInt(request.getParameter("type"));
			String path = getRootPath();
			createPath(path);
			String fileName = getfileName(request, "file");
			List<Maporg> list = service.getByType(type);
			if (list != null && list.size() > 0) {
				for (Maporg maporg : list) {
					service.del(maporg);
					deleteFile(maporg.getUrl(), maporg.getFilename());
				}
			}
			MultipartFile mfile=fileUpload(request, path, "file");
			InputStream fis = mfile.getInputStream();
			byte[] img = new byte[(int)mfile.getSize()];
			fis.read(img);
			Maporg maporg = new Maporg();
			maporg.setFilename(fileName);
			maporg.setUrl(path);
			maporg.setLatitude(Double.parseDouble(latitude));
			maporg.setLongitude(Double.parseDouble(longitude));
			maporg.setImg(img);
			maporg.setType(type);
			service.add(maporg);
			if (mfile != null && fileName != null
					&& fileName.length() > 0) {
				File file = new File(path + fileName);
				mfile.transferTo(file);
			}
			if (fis != null) {
				fis.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return result.setStatus(-2, "exception");
		}

		return result.setStatus(0, "ok");
	}

	private static void createPath(String path) {
		File fp = new File(path);
		// 创建目录
		if (!fp.exists()) {
			fp.mkdirs();// 目录不存在的情况下，创建目录。
		}
	}

	private MultipartFile fileUpload(HttpServletRequest request, String path, String name)
			throws IllegalStateException, IOException {
		if (request instanceof MultipartHttpServletRequest) {
			MultipartFile pic = ((MultipartHttpServletRequest) request)
					.getFile(name);

			return pic;
		}
		return null;
	}

	private String getfileName(HttpServletRequest request, String name) {
		MultipartFile pic = ((MultipartHttpServletRequest) request)
				.getFile(name);
		String originFileName = pic.getOriginalFilename();
		return originFileName;
	}

	private static void deleteFile(String path, String filename) {
		File file = new File(path);
		File Array[] = file.listFiles();
		if(Array!=null&&Array.length>0){
			for (File f : Array) {
				if (f.isFile()) {// 如果是文件
					if (f.getName().equals(filename)) {
						f.delete();
					}
				}
			}
		}
		
	}

	/**
	 * Gets the root path of server.
	 * 
	 * @return the root path
	 */
	public static String getRootPath() {
		String classPath = Thread.currentThread().getContextClassLoader()
				.getResource("").getPath();
		String rootPath = "";
		/** For Windows */
		if ("\\".equals(File.separator)) {
			String path = classPath.substring(1,
					classPath.indexOf("WEB-INF/classes/"));
			rootPath = path + "pic/";
			rootPath = rootPath.replace("/", "\\");
		}

		/** For Linux */
		if ("/".equals(File.separator)) {
			String path = classPath.substring(0,
					classPath.indexOf("WEB-INF/classes/"));
			rootPath = path + "pic/";
			rootPath = rootPath.replace("\\", "/");
		}
		return rootPath;
	}

}
