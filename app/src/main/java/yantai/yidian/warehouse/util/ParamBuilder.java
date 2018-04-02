package yantai.yidian.warehouse.util;

public class ParamBuilder {
    private StringBuffer stringBuffer = new StringBuffer();

    public void add(String name,String value){


        stringBuffer.append(name);
        stringBuffer.append("=");
        stringBuffer.append(value);

    }

    public String build(){
        return stringBuffer.toString();
    }

}
