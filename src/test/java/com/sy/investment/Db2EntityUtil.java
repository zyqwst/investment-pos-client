package com.sy.investment;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.Scanner;

import org.apache.commons.lang3.StringUtils;


/** 
* @ClassName: Db2Entity 
* @Description: 数据表生成实体，驼峰命名
* @author albert
* @date 2017年2月27日 下午4:19:14 
*  
*/
public class Db2EntityUtil {
	
	/**
	 * mysql com.mysql.jdbc.Driver
	 * oracle oracle.jdbc.driver.OracleDriver
	 * sqlserver com.microsoft.sqlserver.jdbc.SQLServerDriver
	 * jdbc:sqlserver://192.168.1.200:1433;DatabaseName=middle_db
	 * jdbc:oracle:thin:@61.174.50.10:1521:orcl
	 */
	private static String driverName = "com.mysql.jdbc.Driver";
	private static String url = "jdbc:mysql://122.112.197.187:23306/investment_behind?useUnicode=true&amp;characterEncoding=UTF-8";
	private static String user="root";
	private static String password="syadmin2589";
	/**包名*/
	private static String packageName = "com.sy.investment.domain.view";
	/**生成的java文件的路径*/
	private static String javaFilePath = "/Users/albert/Documents/workspace/investment-pos-client/src/main/java/com/sy/investment/domain/view";
	/**实体名称无修改命名,否则遇到 _ 则大写*/
	private static Boolean physicalNamingStrategyStandardImpl = true;
	/**PhysicalNamingStrategyStandardImpl=true生效，以下面这些单词结尾的首字母大写*/
	private static String[] regs ={"id","status","amount","company","date","address","count","type","price","qty"}; 
	/**
	 * 
	 * @param tableName 表名
	 * @param entityName 生成的实体名，若为空；则根据表名生成
	 * @throws Exception
	 */
	public static String genEntity(String tableName,String packageName,String entityName) throws Exception{
		Connection conn = getConnection();
		ResultSet tableRet = conn.createStatement().executeQuery("show full columns from "+tableName);
		
		StringBuffer javaTxt = new StringBuffer();
		javaTxt.append("package "+ packageName +";");
		StringBuffer getSetter = new StringBuffer();
		javaTxt.append("\nimport java.util.Date;"
				+ "\nimport java.math.BigDecimal;"
				+ "\nimport javax.persistence.Entity;"
				+ "\nimport javax.persistence.Table;"
				+ "\nimport javax.persistence.GeneratedValue;"
				+ "\nimport javax.persistence.GenerationType;"
				+ "\nimport javax.persistence.Id;"
				+ "\nimport com.sy.investment.domain.EntityBase;"
				+ "\nimport lombok.Data;");
		javaTxt.append("\n/**"+
					"\n * <p>Title: "+(entityName!=null?entityName:genJavaName(tableName))+"</p>"+
					"\n * <p>Description: auto generated </p>"+
					"\n * <p>Company: 湖州双翼信息技术有限公司</p>"+
					"\n * @author AlbertZhang"+
					"\n * @date	"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+
					"\n * @version 1.0"+
					"\n */");
		javaTxt.append("\n@Data");
		javaTxt.append("\n@Entity");
		javaTxt.append("\n@Table(name=\""+tableName+"\")");
		javaTxt.append("\npublic class "+(entityName!=null?entityName:genJavaName(tableName))+" implements EntityBase {");
		javaTxt.append("\n\tprivate static final long serialVersionUID = "+new Random(Long.MAX_VALUE).nextLong()+"L;");
		
		while(tableRet.next()) {		
			String columnName = tableRet.getString("Field");
			String attrName = camelCase(columnName);
			javaTxt.append("\n\t/**"+
							"\n\t * "+tableRet.getString("Comment")+
							"\n\t */");
			if(columnName.equals("id")) {
				javaTxt.append("\n\t@Id" + 
						"\n\t@GeneratedValue(strategy=GenerationType.AUTO)");
			}
			javaTxt.append("\n\t"+dbType2JavaType(tableRet.getString("Type"))+attrName+defaultValue(tableRet.getString("Default"))+";");
			
			
		}
		
		return javaTxt.toString()+getSetter.toString()+"\n}";
	}
	

	public static Connection getConnection() throws Exception{
		Class.forName(driverName).newInstance(); 
		Connection conn = DriverManager.getConnection(url,user,password);
		return conn;
	}
	/**
	 * 命名
	 * @param col
	 * @return
	 */
	public static String camelCase(String col) {
		if(physicalNamingStrategyStandardImpl) {
			String s = col.toLowerCase();
			for(String reg : regs){
				reg = reg.toLowerCase();
				if(s.endsWith(reg) && !reg.equals(s)){
					s = s.replace(reg, "") + toUpperFirstChar(reg);
				}
			}
			return s;
		}else {
			String[] arr = col.split("_");
			if(arr.length==1) return col;
			StringBuffer sb = new StringBuffer();
			int i =0;
			for(String s : arr) {
				if(i==0) {
					sb.append(s);
					
				}else {					
					sb.append(toUpperFirstChar(s));
				}
				i++;
			}
			return sb.toString();
		}
	}
	/**database column type转换成java 类型*/
	public static String dbType2JavaType(String type) {
		String str = null;
		type = type.toLowerCase();
		if(type.contains("char")){
			str = "private String ";
		}else if(type.equals("number") || type.contains("long") || type.contains("bigint")){
			str = "private Long ";
		}else if(type.contains("int")){
			str = "private Integer ";
		}else if(type.contains("date") || type.contains("time")){
			str = "private Date ";
		}else if(type.contains("number(") || type.contains("double") || type.contains("numeric")){
			str = "private Double ";
		}else if(type.contains("decimal")) {
			str = "private BigDecimal ";
		}
		else{
			str = "private String ";
		}
		return str;
	}
	/**
	 * @param string
	 * @return
	 */
	private static String defaultValue(String str) {
		if(str!=null && !str.equals("null")) {
			return " = "+str;
		}
		return "";
	}
	/**
	 * 由表名生成类名
	 * @param tableName
	 * @return
	 */
	public static String genJavaName(String tableName){
		String name = tableName.toLowerCase();
		if(name.endsWith("_t")){
			name = name.substring(0, name.length()-2);
		}
		if(name.contains("_")){
			int index = name.indexOf("_");
			name = name.replaceFirst("_", "");
			name = name.substring(0,index)+toUpperFirstChar(name.substring(index));
		}
		return toUpperFirstChar(name);
	}
	/**
	 * 首字母大写
	 * @param param
	 * @return
	 */
	public static String toUpperFirstChar(String param){
		return param.substring(0, 1).toUpperCase()+param.substring(1);
	}
	/**
	 * 生成getter setter
	 * @param className
	 * @param attr
	 * @return
	 */
	public static String genGetSetter(String className,String attr){
		StringBuffer sb = new StringBuffer("\n\tpublic void set"+toUpperFirstChar(attr)+"("+className+" "+attr+"){");
		sb.append("\n\t\tthis."+attr+" = "+attr+";");
		sb.append("\n\t}");
		
		sb.append("\n\tpublic "+className+" get"+toUpperFirstChar(attr)+"(){");
		sb.append("\n\t\treturn "+ attr +";");
		sb.append("\n\t}");
		return sb.toString();
	}
	
	public static void genJavaFile(String javaTxt,String className) throws Exception{
		File file = new File(javaFilePath);
		if(!file.exists() || !file.isDirectory()) throw new Exception(javaFilePath+"路径不存在");
		file = new File(javaFilePath+"/"+className+".java");
		BufferedWriter fw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true), "utf-8")); 
		fw.append(javaTxt);
		fw.flush();
		fw.close();
	}
	/**
	 * start method
	 * @throws Exception 
	 */
	public static void generate() throws Exception{
		String tname;
		String entityName;
		System.out.println("输入表名并回车：");
		Scanner sc = new Scanner(System.in);   
		tname = sc.nextLine();
		System.out.println("输入实体名并回车(可不输入，按照表名自动生成)：");
		Scanner sc2 = new Scanner(System.in);
		entityName = sc2.nextLine();
		System.out.println("正在生成，请稍后...");
		if(StringUtils.isEmpty(entityName)) entityName = null;
		genJavaFile(genEntity(tname,packageName,entityName), entityName!=null?entityName:genJavaName(tname));
		System.out.println("java对象成功生成");
	}
	public static void main(String[] args) {
		try {
			generate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
}
