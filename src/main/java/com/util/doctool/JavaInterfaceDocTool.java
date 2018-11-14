package com.util.doctool;

import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.io.*;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

public class JavaInterfaceDocTool {

    private static final List<ExcelDataVO> result = new ArrayList<ExcelDataVO>();

    public static void getAllFile(String filePath){
        File dir = new File(filePath);
        File[] files = dir.listFiles();
        if(files != null){
            for(int i=0;i<files.length;i++){
                String fileName = files[i].getName();
                if(files[i].isDirectory()){
                    getAllFile(files[i].getAbsolutePath());
                }else if(fileName.endsWith(".class")){
                    try{
                        String keyWord = "\\com\\";
                        String absPth = files[i].getCanonicalPath();
                        absPth = absPth.substring(absPth.indexOf(keyWord)+1,absPth.length()-6);
                        absPth = absPth.replace("\\",".");
                        Class c = Class.forName(absPth);
                        if(c.isInterface()){
                            Method[] methods = c.getDeclaredMethods();
                            for(int j=0;j<methods.length;j++){
                                ExcelDataVO e = new ExcelDataVO();
                                e.setSourcePackageName(c.getPackage().getName().toString());
                                e.setSourceFileName(c.getSimpleName().toString());
                                e.setSourceFunctionName(methods[j].getName().toString());
                                e.setParam(getParams(methods[j].getParameters()));
                                e.setReturnType(methods[j].getGenericReturnType().getTypeName().toString());
                                result.add(e);
                            }
                        }
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public static String getParams(Parameter[] params){
        StringBuilder typeString = new StringBuilder("[");
        for(int i=0;i<params.length;i++){
            typeString.append(params[i].getName().toString());
            if(i != params.length-1){
                typeString.append(",");
            }
        }
        typeString.append("]");
        return typeString.toString();
    }


    public static void excelTool(List<ExcelDataVO> result) throws IOException {
        OutputStream out = new FileOutputStream("D:\\导出接口Doc.xlsx");
        SXSSFWorkbook sxssfWorkbook = new SXSSFWorkbook();
        SXSSFSheet sxssfSheet = sxssfWorkbook.createSheet("InterfaceDoc");
        SXSSFRow sxssfRowTitle = sxssfSheet.createRow(0);
        sxssfRowTitle.createCell(0).setCellValue("包名");
        sxssfRowTitle.createCell(1).setCellValue("接口名");
        sxssfRowTitle.createCell(2).setCellValue("方法名");
        sxssfRowTitle.createCell(3).setCellValue("参数名");
        sxssfRowTitle.createCell(4).setCellValue("返回值类型");

        int index = 1;
        for(ExcelDataVO excelDataVO : result){
            SXSSFRow sxssfRowDetail = sxssfSheet.createRow(index);
            sxssfRowDetail.createCell(0).setCellValue(excelDataVO.getSourcePackageName()==null?"":excelDataVO.getSourcePackageName());
            sxssfRowDetail.createCell(1).setCellValue(excelDataVO.getSourceFileName()==null?"":excelDataVO.getSourceFileName());
            sxssfRowDetail.createCell(2).setCellValue(excelDataVO.getSourceFunctionName()==null?"":excelDataVO.getSourceFunctionName());
            sxssfRowDetail.createCell(3).setCellValue(excelDataVO.getParam()==null?"":excelDataVO.getParam());
            sxssfRowDetail.createCell(4).setCellValue(excelDataVO.getReturnType()==null?"":excelDataVO.getReturnType());
            index++;
        }
        sxssfWorkbook.write(out);
    }

    public static void main(String[] args) {
        String filePath = "D:\\IDEAWorkSpace\\JavaInterfaceDocTool";
        try{
            getAllFile(filePath);
            excelTool(result);
        }catch(Exception e){
            e.printStackTrace();
        }
    }



}
