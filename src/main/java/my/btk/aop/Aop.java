package my.btk.aop;

import my.btk.aop.advice.Advice;
import my.btk.aop.annotation.Aspect;
import my.btk.aop.annotation.Order;
import my.btk.core.BeanContainer;
import lombok.extern.slf4j.Slf4j;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class Aop {

    /**
     * Bean 容器
     */
    private BeanContainer beanContainer;

    public Aop() {
        beanContainer = BeanContainer.getInstance();
    }

    public void doAop() {
        //创建所有的代理通知列表
        List<ProxyAdvisor> proxyList = beanContainer.getClassesBySuper(Advice.class)
                .stream()
                .filter(clz -> clz.isAnnotationPresent(Aspect.class))
                .map(this::createProxyAdvisor)
                .collect(Collectors.toList());

        //创建代理类并注入到 Bean 容器中
        beanContainer.getClasses()
                .stream()
                .filter(clz -> !Advice.class.isAssignableFrom(clz))
                .filter(clz -> !clz.isAnnotationPresent(Aspect.class))
                .forEach(clz -> {
                    List<ProxyAdvisor> matchProxies = createMatchProxies(proxyList, clz);
                    if (matchProxies.size() > 0) {
                        Object proxyBean = ProxyCreator.createProxy(clz, matchProxies);
                        beanContainer.addBean(clz, proxyBean);
                    }
                });
    }
    /**
     * 通过 Aspect 切面类创建代理通知类
     */
    private ProxyAdvisor createProxyAdvisor(Class<?> aspectClass) {
        int order = 0;
        if (aspectClass.isAnnotationPresent(Order.class)) {
            order = aspectClass.getAnnotation(Order.class).value();
        }
        String expression = aspectClass.getAnnotation(Aspect.class).pointcut();
        ProxyPointcut proxyPointcut = new ProxyPointcut();
        proxyPointcut.setExpression(expression);
        Advice advice = (Advice) beanContainer.getBean(aspectClass);
        return new ProxyAdvisor(advice, proxyPointcut, order);
    }

    private List<ProxyAdvisor> createMatchProxies(List<ProxyAdvisor> proxyList, Class<?> targetClass) {
        Object targetBean = beanContainer.getBean(targetClass);
        return proxyList
                .stream()
                .filter(advisor -> advisor.getPointcut().matches(targetBean.getClass()))
                .sorted(Comparator.comparingInt(ProxyAdvisor::getOrder))
                .collect(Collectors.toList());
    }
}