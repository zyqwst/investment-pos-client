package com.sy.investment.utils;

import java.math.BigDecimal;

import org.springframework.util.Assert;

public class MathUtil{
	
	//默认除法运算精度
    private static final int DEF_DIV_SCALE = 2;
    //这个类不能实例化
    private MathUtil(){
    }

    /**
     * 提供精确的加法运算。
     * @param v1 被加数
     * @param v2 
     * @return 
     */
    public static Double add(Double v1,Double ...v2){
    		Assert.notNull(v1, "参数不可为空");
    		Assert.notEmpty(v2, "参数不可为空");
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        for(Double v : v2) {
        		Assert.notNull(v, "参数不可为空");
        		b1 = b1.add(new BigDecimal(Double.toString(v)));
        }
        return b1.doubleValue();
    }
    
    public static BigDecimal add(BigDecimal v1,BigDecimal ...v2){
		Assert.notNull(v1, "参数不可为空");
		Assert.notEmpty(v2, "参数不可为空");
		BigDecimal b1= BigDecimal.ZERO;
		b1 = b1.add(v1);
	    for(BigDecimal v : v2) {
	    		Assert.notNull(v, "参数不可为空");
	    		b1 = b1.add(v);
	    }
	    return b1;
	}
    /**
     * 提供精确的减法运算。
     * @param v1 被减数
     * @param v2 
     * @return 
     */
    public static Double sub(Double v1,Double v2){
	    	Assert.notNull(v1, "参数不可为空");
	    	Assert.notNull(v2, "参数不可为空");
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.subtract(b2).doubleValue();
    }
    public static BigDecimal sub(BigDecimal v1,BigDecimal v2){
	    	Assert.notNull(v1, "参数不可为空");
	    	Assert.notNull(v2, "参数不可为空");
	    return v1.subtract(v2);
	}
    /**
     * 提供精确的乘法运算。
     * @param v1 被乘数
     * @param v2 乘数
     * @return 
     */
    public static Double mul(Double v1,Double ...v2){
    		Assert.notNull(v1, "参数不可为空");
    		Assert.notEmpty(v2, "参数不可为空");
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        for(Double v : v2) {
	    		Assert.notNull(v, "参数不可为空");
	    		b1 = b1.multiply(new BigDecimal(Double.toString(v)));
	    }
        return b1.doubleValue();
    }
    public static BigDecimal mul(BigDecimal v1,BigDecimal ...v2){
		Assert.notNull(v1, "参数不可为空");
		Assert.notEmpty(v2, "参数不可为空");
	    BigDecimal b1 = BigDecimal.ONE;
	    b1 = b1.multiply(v1);
	    for(BigDecimal v : v2) {
	    		Assert.notNull(v, "参数不可为空");
	    		b1 = b1.multiply(v);
	    }
	    return b1;
	}
    /**
     * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到
     * 小数点以后{@link DEF_DIV_SCALE}位，以后的数字四舍五入。
     * @param v1 被除数
     * @param v2 除数
     * @return 两个参数的商
     */
    public static Double div(Double v1,Double v2){
        return div(v1,v2,DEF_DIV_SCALE);
    }
    public static BigDecimal div(BigDecimal v1,BigDecimal v2){
        return div(v1,v2,DEF_DIV_SCALE);
    }
    /**
     * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指
     * 定精度，以后的数字四舍五入。
     * @param v1 被除数
     * @param v2 除数
     * @param scale 表示表示需要精确到小数点以后几位。
     * @return 两个参数的商
     */
    public static Double div(Double v1,Double v2,int scale){
        if(scale<0){
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.divide(b2,scale,BigDecimal.ROUND_HALF_UP).doubleValue();
    }
    public static BigDecimal div(BigDecimal v1,BigDecimal v2,int scale){
        if(scale<0){
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        return v1.divide(v2,scale,BigDecimal.ROUND_HALF_UP);
    }
    /**
     * 提供精确的小数位四舍五入处理。
     * @param v 需要四舍五入的数字
     * @param scale 小数点后保留几位
     * @return 四舍五入后的结果
     */
    public static Double round(Double v,int scale){

        if(scale<0){
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        BigDecimal b = new BigDecimal(Double.toString(v));
        BigDecimal one = new BigDecimal("1");
        return b.divide(one,scale,BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public static void main(String [] args){
        BigDecimal a = new BigDecimal(0.04);
        BigDecimal b = new BigDecimal(0.01);
        BigDecimal c = new BigDecimal(0.01);
        System.out.println(MathUtil.add(a, b,c).doubleValue());
        System.out.println(MathUtil.sub(a, b));
        System.out.println(MathUtil.mul(a, b,c));
        System.out.println(MathUtil.div(a, b));
        
        System.out.println(b.subtract(c).compareTo(BigDecimal.ZERO));
    }
}