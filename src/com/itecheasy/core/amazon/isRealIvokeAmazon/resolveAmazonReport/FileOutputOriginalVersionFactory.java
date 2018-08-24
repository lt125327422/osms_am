package com.itecheasy.core.amazon.isRealIvokeAmazon.resolveAmazonReport;

import org.springframework.beans.factory.FactoryBean;

import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: liteng
 * @Date: 2018/8/9 11:29
 * @Description: spring工厂
 * 通过实例工厂方法配置Bean
 *
 * 实例工厂方法: 将对象的创建过程封装到另外一个对象实例的方法里. 当客户端需要请求对象时,
 * 只需要简单的调用该实例方法而不需要关心对象的创建细节.
 *
 * 要声明通过实例工厂方法创建的 Bean
 * ①在 bean 的 factory-bean 属性里指定拥有该工厂方法的 Bean
 * ②在 factory-method 属性里指定该工厂方法的名称
 * ③使用 construtor-arg 元素为工厂方法传递方法参数
 * 代码示例
 */
public class FileOutputOriginalVersionFactory {

    /*
     * 实例工厂方法：实例工厂的方法，即先需要创建工厂本身，再调用工厂的实例方法来返回bean的实例
     */
    private Map<String ,ResolutionReportFile> resolutionReportFileMap = resolutionReportFileMap=new HashMap<String, ResolutionReportFile>();

    public FileOutputOriginalVersionFactory() {

        resolutionReportFileMap.put("amazonStock",new AmazonStockItemReportFileToVoJsonImpl());
        resolutionReportFileMap.put("inventoryAged",new ResolutionInventoryAgedItemResolutionReportImpl());
    }

    public ResolutionReportFile getAmazonReportType(String beanName) {
        return resolutionReportFileMap.get(beanName);
    }

}
