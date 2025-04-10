package com.sky.aspect;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.sky.annotation.AutoFill;
import com.sky.constant.AutoFillConstant;
import com.sky.context.BaseContext;
import com.sky.enumeration.OperationType;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Aspect
@Component
@Slf4j
@JsonFormat
public class AutoFillAspect {
//切入点

        @Pointcut("execution(* com.sky.mapper.*.*(..)) && @annotation(com.sky.annotation.AutoFill)")
        public void autoFillPointcut(){}
        @Before("autoFillPointcut()")
        public void autoFill(JoinPoint joinPoint) {
            log.info("开始进行公共字段的自动填充");

            //获取拦截到的方法的类型
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            AutoFill autoFill = signature.getMethod().getAnnotation(AutoFill.class);
            OperationType operationType = autoFill.value();
            //获取到当前被拦截的方法的参数
            Object[] args = joinPoint.getArgs();
            if (args != null && args.length == 0) {
                return;
            }
            Object entity = args[0];

            //准备赋值的数据
            LocalDateTime now = LocalDateTime.now();
            long currentid = BaseContext.getCurrentId();

            //根据当前不同的操作类型,为对应的类型进行反射来赋值
            if (operationType == OperationType.INSERT) {
                //为四个公共字段赋值
                try {
                    Method setCreateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_TIME, LocalDateTime.class);
                    Method setCreateUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_USER, Long.class);
                    Method setUpdateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class);
                    Method setUpdateUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER, Long.class);

                    //通过反射为对象属性赋值

                    setCreateTime.invoke(entity, now);
                    setCreateUser.invoke(entity, currentid);
                    setUpdateTime.invoke(entity, now);
                    setUpdateUser.invoke(entity, currentid);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

            } else if (operationType == OperationType.UPDATE) {
                //为两个公共字段赋值
                try {
                    Method setUpdateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class);
                    Method setUpdateUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER, Long.class);

                    //通过反射为对象属性赋值


                    setUpdateTime.invoke(entity, now);
                    setUpdateUser.invoke(entity, currentid);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
}
