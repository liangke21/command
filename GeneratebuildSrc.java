
import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class GeneratebuildSrc {
	//当前java文件所在的路径，即这个工具所在的根目录。
	public final static String ROOT_PATH = GeneratebuildSrc.class.getResource("").getPath();
	//模板文件所在的路径。
	public final static String FTL_PATH = ROOT_PATH+"/marker/buildsrc";
	
	//项目根目录。
	public static String PROJECT_ROOT = "";
	
	//包名
	public static String PACKGE_NAME = "";
	
	//所有依赖库拼接而成的字符串，以#分割。
	public static String DEPENDENCIES_STR_IN = "";
	
	//项目根目录下build.gradle所需要的配置(通常以classpath开头)
	public static String DEPENDENCIES_STR_OUT = "";
	
	//项目根目录下build.gradle所需要的仓库配置。（如 jcenter()、mavenCentral()）
	public static String DEPENDENCIES_REPOSITORIES = "";
	
	public static void main(String[] args) throws Exception {
		//传递进来三个参数，第一个是项目名，第二个是包名，第三个是所有的依赖库拼接而成的字符串。
		PROJECT_ROOT = args[0];
		PACKGE_NAME = args[1];
		


		createAppBuildGradle(PROJECT_ROOT+"/buildSrc");
		createAppsettigsGradle(PROJECT_ROOT+"/buildSrc");

	/*	createKotlinConstants();*/

	}
	
	//生成module的build.gradle文件
	private static void createAppBuildGradle(String appPath) throws Exception {
		Template template = getTemplate("build.gradle.kts.ftl");
		File file = new File(appPath+"/build.gradle.kts");
		if(!file.exists()) {
			file.createNewFile();
		}
		FileWriter fileWriter = new FileWriter(file);
		Map<Object, Object> map = new HashMap<>();

		map.put("packageName", PACKGE_NAME);
		template.process(map, fileWriter);
		fileWriter.close();
	}



	private static void createAppsettigsGradle(String appPath) throws Exception {
		Template template = getTemplate("settings.gradle.kts.ftl");
		File file = new File(appPath+"/settings.gradle.kts");
		if(!file.exists()) {
			file.createNewFile();
		}
		FileWriter fileWriter = new FileWriter(file);
		Map<Object, Object> map = new HashMap<>();

		map.put("packageName", PACKGE_NAME);
		template.process(map, fileWriter);
		fileWriter.close();
	}




/*
	public static void createKotlinConstants() throws Exception{
		//获取文件
		Template template = getTemplate("KotlinConstants.kt.ftl");
*/
/*	//	String [] packages = PACKGE_NAME.split("\\.");*//*

		String mainActivityPath = PROJECT_ROOT+"/buildSrc/src/main/kotlin";
		*/
/*for(int i=0;i<packages.length;i++) {
			mainActivityPath = mainActivityPath+"/"+packages[i];
		}*//*


		System.out.println(mainActivityPath);
//新建的文件名
		File mainActivityFile = new File(mainActivityPath+"/KotlinConstants.kt");
		//这个文件是否纯在
		if(!mainActivityFile.exists()) {
			mainActivityFile.createNewFile(); ///不纯在就创建
		}
		FileWriter fileWriter = new FileWriter(mainActivityFile);
		Map<Object, Object> map = new HashMap<>();
		map.put("packageName", PACKGE_NAME);

		System.out.println("包裹的名字"+ PACKGE_NAME);

		template.process(map, fileWriter);
		fileWriter.close();
	}

*/







	public static Template getTemplate(String templateName) throws Exception{
		Configuration configuration = new Configuration();
		configuration.setDirectoryForTemplateLoading(new File(FTL_PATH));
		Template template = configuration.getTemplate(templateName);
		return template;
	}

}
