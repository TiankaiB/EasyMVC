package my.btk.ioc;

import my.btk.core.BeanContainer;
import my.btk.ioc.annotation.Autowired;
import my.btk.util.ClassUtil;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.Optional;

@Slf4j
public class Ioc {

    /**
     * Bean container
     */
    private BeanContainer beanContainer;

    public Ioc() {
        beanContainer = BeanContainer.getInstance();
    }

    /**
     * do Ioc
     */
    public void doIoc() {
        for (Class<?> clz : beanContainer.getClasses()) { //遍历 Bean 容器中所有的 Bean
            final Object targetBean = beanContainer.getBean(clz);
            Field[] fields = clz.getDeclaredFields();
            for (Field field : fields) { //遍历 Bean 中的所有属性
                if (field.isAnnotationPresent(Autowired.class)) {// 如果该属性被 Autowired 注解，则对其注入
                    final Class<?> fieldClass = field.getType();
                    Object fieldValue = getClassInstance(fieldClass);
                    if (null != fieldValue) {
                        ClassUtil.setField(field, targetBean, fieldValue);
                    } else {
                        throw new RuntimeException("can't inject properties: " + fieldClass.getName());
                    }
                }
            }
        }
    }

    /**
     * get object by class
     */
    private Object getClassInstance(final Class<?> clz) {
        return Optional
                .ofNullable(beanContainer.getBean(clz))
                .orElseGet(() -> {
                    Class<?> implementClass = getImplementClass(clz);
                    if (null != implementClass) {
                        return beanContainer.getBean(implementClass);
                    }
                    return null;
                });
    }

    /**
     * get interface class
     */
    private Class<?> getImplementClass(final Class<?> interfaceClass) {
        return beanContainer.getClassesBySuper(interfaceClass)
                .stream()
                .findFirst()
                .orElse(null);
    }

}