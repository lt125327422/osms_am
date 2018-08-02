package com.printMethod;


import com.amazon.client.AmazonReportClient;
import com.printMethod.annotations.LoggerNameDescription;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.codehaus.jackson.map.ObjectMapper;
import org.apache.log4j.Logger;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.reflect.Method;

/**
 * @Auther: liteng
 * @Date: 2018/7/20 17:21
 * @Description:  方法拦截器   打印访问中间服务的日志，只打印有注解的
 */
public class PrintLogger implements MethodInterceptor {

    private static final Logger LOGGER = Logger.getLogger(AmazonReportClient.class);

    private static final ObjectMapper jsonMapper = new ObjectMapper();

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
//        logger.info("Before: interceptor name: {}", invocation.getMethod().getName());
//        Method realMethod = joinPoint.getTarget().getClass().getDeclaredMethod(signature.getName(), method.getParameterTypes());

//        String className = invocation.getThis().getClass().getName();
//        String methodName = invocation.getMethod().getName();
//        invocation.getThis().getClass().getDeclaredMethod(methodName,)

        Object result = null;
        Method method = invocation.getMethod();

        LoggerNameDescription loggerNameDescription3 = AnnotationUtils.findAnnotation(invocation.getMethod(), LoggerNameDescription.class);
//        if(loggerNameDescription3!=null){
//            LoggerNameDescription loggerNameDescription = method.getAnnotation(LoggerNameDescription.class);
//            String methodNameDescription = loggerNameDescription.methodNameDescription();
            LOGGER.info("传来了: {}"+jsonMapper.writeValueAsString(invocation.getArguments()));
            result = invocation.proceed();
            LOGGER.info("返回了: result: {}"+ jsonMapper.writeValueAsString(result));
//        }


//        }else{
//            logger.info(className+ "." + methodName +"Arguments: {}", jsonMapper.writeValueAsString(invocation.getArguments()));
//            result = invocation.proceed();
//            logger.info(className+ "." + methodName+"After: result: {}", jsonMapper.writeValueAsString(result));
//        }


//        if(requiredInterceptor!=null){
//
//        }
            return result;
    }


}
