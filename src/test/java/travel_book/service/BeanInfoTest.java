package travel_book.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.mail.javamail.JavaMailSender;
import travel_book.service.config.MemberConfig;
import travel_book.service.web.api.perplexity.PerplexityApiController;

@SpringBootTest(classes = {ServiceApplication.class})
public class BeanInfoTest {

    @Autowired
    private ApplicationContext applicationContext/* = new AnnotationConfigApplicationContext(WebConfig.class)*/;    // 모든 빈 출력할 때
    @MockBean
    private JavaMailSender javaMailSender; // Mail 관련 의존성 Mock 처리

    @Test
    @DisplayName("모든 빈 출력하기")
    public void printAllBeans() {
        String[] beanNames = applicationContext.getBeanDefinitionNames();
        System.out.println("등록된 빈의 총 개수: " + beanNames.length);
        for (String beanName : beanNames) {
            Object bean = applicationContext.getBean(beanName);
            System.out.println("빈 이름: " + beanName + ", 빈 타입: " + bean.getClass().getName());
        }
    }

    @Test
    @DisplayName("애플리케이션 빈 출력하기")
    public void printApplicationBeans() {
        String[] beanNames = applicationContext.getBeanDefinitionNames();
        ConfigurableListableBeanFactory beanFactory = (ConfigurableListableBeanFactory) applicationContext.getAutowireCapableBeanFactory();
        System.out.println("애플리케이션 빈의 개수: " + beanNames.length);
        for (String beanName : beanNames) {
            BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanName);

            // ROLE_APPLICATION에 해당하는 빈만 출력     ==> travel_book 패키지를 통해서 등록된 빈들만 보려고 했는데 실패...  memberRepository, loginService 등 다른게 안보임
//            if (beanDefinition.getRole() == BeanDefinition.ROLE_APPLICATION) {
            if (beanDefinition.getRole() == BeanDefinition.ROLE_APPLICATION && beanDefinition.getBeanClassName() != null
                    && beanDefinition.getBeanClassName().contains("travel_book")) {
                //System.out.println("beanDefinition = " + beanDefinition);
                Object bean = applicationContext.getBean(beanName);
                System.out.println("빈 이름: " + beanName + ", 빈 타입: " + bean.getClass().getName() + ", 등록된 빈클래스 이름: " + beanDefinition.getBeanClassName() + ", 등록된 클래스 이름: " + beanDefinition.getClass().getName());
            }
        }
    }

    @Test
    public void basicScan() {
        PerplexityApiController bean = applicationContext.getBean(PerplexityApiController.class);
        Assertions.assertThat(bean).isInstanceOf(PerplexityApiController.class);
        System.out.println("bean = " + bean);
    }


}