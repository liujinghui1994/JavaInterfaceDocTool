package com.util.doctool;

public class ExcelDataVO {

    private String sourcePackageName;
    private String sourceFileName;
    private String sourceFunctionName;
    private String param;
    private String returnType;

    public String getSourcePackageName() {
        return sourcePackageName;
    }

    public void setSourcePackageName(String sourcePackageName) {
        this.sourcePackageName = sourcePackageName;
    }

    public String getSourceFileName() {
        return sourceFileName;
    }

    public void setSourceFileName(String sourceFileName) {
        this.sourceFileName = sourceFileName;
    }

    public String getSourceFunctionName() {
        return sourceFunctionName;
    }

    public void setSourceFunctionName(String sourceFunctionName) {
        this.sourceFunctionName = sourceFunctionName;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public String getReturnType() {
        return returnType;
    }

    public void setReturnType(String returnType) {
        this.returnType = returnType;
    }

    public ExcelDataVO(String sourcePackageName, String sourceFileName, String sourceFunctionName, String param, String returnType) {
        this.sourcePackageName = sourcePackageName;
        this.sourceFileName = sourceFileName;
        this.sourceFunctionName = sourceFunctionName;
        this.param = param;
        this.returnType = returnType;
    }

    public ExcelDataVO() {
    }
}
