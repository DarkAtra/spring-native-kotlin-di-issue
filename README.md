Demonstrates that dependency injection with kotlin is not working as expected when using native image.

The `Service` class defines two constructor parameters, one of which should be autowired and one that has a default
value.

```kotlin
@Service
class Service(
    private val applicationContext: ApplicationContext,
    private val client: Client = Client()
)
```

When running on JVM the code behaves as expected: only the constructor parameter without default value is autowired.
When running the native image the application does not start and throws the following exception indicating that spring
attempts to autowire all constructor parameters (even the one with default values):

```
java.lang.NullPointerException: Parameter specified as non-null is null: method de.darkatra.kotlindiissue.Service.<init>, parameter client
```

It does not seem to matter how many constructor parameters there are as long as there is at least one to autowire and
one with a default value. Also, changing the visibility of the parameters has no effect.

Full stacktrace:

```
org.springframework.beans.factory.UnsatisfiedDependencyException: Error creating bean with name 'kotlinDiIssueApplication': Unsatisfied dependency expressed through constructor parameter 0: Error creating bean with name 'service': Instantiation of supplied bean failed
	at org.springframework.beans.factory.aot.BeanInstanceSupplier.resolveArgument(BeanInstanceSupplier.java:351) ~[na:na]
	at org.springframework.beans.factory.aot.BeanInstanceSupplier.resolveArguments(BeanInstanceSupplier.java:271) ~[na:na]
	at org.springframework.beans.factory.aot.BeanInstanceSupplier.get(BeanInstanceSupplier.java:206) ~[na:na]
	at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.obtainInstanceFromSupplier(AbstractAutowireCapableBeanFactory.java:1225) ~[de.darkatra.kotlindiissue.KotlinDiIssueApplicationKt:6.0.3]
	at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.obtainFromSupplier(AbstractAutowireCapableBeanFactory.java:1210) ~[de.darkatra.kotlindiissue.KotlinDiIssueApplicationKt:6.0.3]
	at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBeanInstance(AbstractAutowireCapableBeanFactory.java:1157) ~[de.darkatra.kotlindiissue.KotlinDiIssueApplicationKt:6.0.3]
	at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.doCreateBean(AbstractAutowireCapableBeanFactory.java:561) ~[de.darkatra.kotlindiissue.KotlinDiIssueApplicationKt:6.0.3]
	at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBean(AbstractAutowireCapableBeanFactory.java:521) ~[de.darkatra.kotlindiissue.KotlinDiIssueApplicationKt:6.0.3]
	at org.springframework.beans.factory.support.AbstractBeanFactory.lambda$doGetBean$0(AbstractBeanFactory.java:326) ~[de.darkatra.kotlindiissue.KotlinDiIssueApplicationKt:6.0.3]
	at org.springframework.beans.factory.support.DefaultSingletonBeanRegistry.getSingleton(DefaultSingletonBeanRegistry.java:234) ~[de.darkatra.kotlindiissue.KotlinDiIssueApplicationKt:6.0.3]
	at org.springframework.beans.factory.support.AbstractBeanFactory.doGetBean(AbstractBeanFactory.java:324) ~[de.darkatra.kotlindiissue.KotlinDiIssueApplicationKt:6.0.3]
	at org.springframework.beans.factory.support.AbstractBeanFactory.getBean(AbstractBeanFactory.java:200) ~[de.darkatra.kotlindiissue.KotlinDiIssueApplicationKt:6.0.3]
	at org.springframework.beans.factory.support.DefaultListableBeanFactory.preInstantiateSingletons(DefaultListableBeanFactory.java:961) ~[de.darkatra.kotlindiissue.KotlinDiIssueApplicationKt:6.0.3]
	at org.springframework.context.support.AbstractApplicationContext.finishBeanFactoryInitialization(AbstractApplicationContext.java:915) ~[de.darkatra.kotlindiissue.KotlinDiIssueApplicationKt:6.0.3]
	at org.springframework.context.support.AbstractApplicationContext.refresh(AbstractApplicationContext.java:584) ~[de.darkatra.kotlindiissue.KotlinDiIssueApplicationKt:6.0.3]
	at org.springframework.boot.SpringApplication.refresh(SpringApplication.java:730) ~[de.darkatra.kotlindiissue.KotlinDiIssueApplicationKt:3.0.1]
	at org.springframework.boot.SpringApplication.refreshContext(SpringApplication.java:432) ~[de.darkatra.kotlindiissue.KotlinDiIssueApplicationKt:3.0.1]
	at org.springframework.boot.SpringApplication.run(SpringApplication.java:308) ~[de.darkatra.kotlindiissue.KotlinDiIssueApplicationKt:3.0.1]
	at org.springframework.boot.SpringApplication.run(SpringApplication.java:1302) ~[de.darkatra.kotlindiissue.KotlinDiIssueApplicationKt:3.0.1]
	at org.springframework.boot.SpringApplication.run(SpringApplication.java:1291) ~[de.darkatra.kotlindiissue.KotlinDiIssueApplicationKt:3.0.1]
	at de.darkatra.kotlindiissue.KotlinDiIssueApplicationKt.main(KotlinDiIssueApplication.kt:30) ~[de.darkatra.kotlindiissue.KotlinDiIssueApplicationKt:na]
Caused by: org.springframework.beans.factory.BeanCreationException: Error creating bean with name 'service': Instantiation of supplied bean failed
	at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.obtainInstanceFromSupplier(AbstractAutowireCapableBeanFactory.java:1236) ~[de.darkatra.kotlindiissue.KotlinDiIssueApplicationKt:6.0.3]
	at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.obtainFromSupplier(AbstractAutowireCapableBeanFactory.java:1210) ~[de.darkatra.kotlindiissue.KotlinDiIssueApplicationKt:6.0.3]
	at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBeanInstance(AbstractAutowireCapableBeanFactory.java:1157) ~[de.darkatra.kotlindiissue.KotlinDiIssueApplicationKt:6.0.3]
	at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.doCreateBean(AbstractAutowireCapableBeanFactory.java:561) ~[de.darkatra.kotlindiissue.KotlinDiIssueApplicationKt:6.0.3]
	at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBean(AbstractAutowireCapableBeanFactory.java:521) ~[de.darkatra.kotlindiissue.KotlinDiIssueApplicationKt:6.0.3]
	at org.springframework.beans.factory.support.AbstractBeanFactory.lambda$doGetBean$0(AbstractBeanFactory.java:326) ~[de.darkatra.kotlindiissue.KotlinDiIssueApplicationKt:6.0.3]
	at org.springframework.beans.factory.support.DefaultSingletonBeanRegistry.getSingleton(DefaultSingletonBeanRegistry.java:234) ~[de.darkatra.kotlindiissue.KotlinDiIssueApplicationKt:6.0.3]
	at org.springframework.beans.factory.support.AbstractBeanFactory.doGetBean(AbstractBeanFactory.java:324) ~[de.darkatra.kotlindiissue.KotlinDiIssueApplicationKt:6.0.3]
	at org.springframework.beans.factory.support.AbstractBeanFactory.getBean(AbstractBeanFactory.java:200) ~[de.darkatra.kotlindiissue.KotlinDiIssueApplicationKt:6.0.3]
	at org.springframework.beans.factory.config.DependencyDescriptor.resolveCandidate(DependencyDescriptor.java:254) ~[de.darkatra.kotlindiissue.KotlinDiIssueApplicationKt:6.0.3]
	at org.springframework.beans.factory.support.DefaultListableBeanFactory.doResolveDependency(DefaultListableBeanFactory.java:1405) ~[de.darkatra.kotlindiissue.KotlinDiIssueApplicationKt:6.0.3]
	at org.springframework.beans.factory.support.DefaultListableBeanFactory.resolveDependency(DefaultListableBeanFactory.java:1325) ~[de.darkatra.kotlindiissue.KotlinDiIssueApplicationKt:6.0.3]
	at org.springframework.beans.factory.aot.BeanInstanceSupplier.resolveArgument(BeanInstanceSupplier.java:334) ~[na:na]
	... 20 common frames omitted
Caused by: java.lang.NullPointerException: Parameter specified as non-null is null: method de.darkatra.kotlindiissue.Service.<init>, parameter client
	at de.darkatra.kotlindiissue.Service.<init>(Service.kt) ~[de.darkatra.kotlindiissue.KotlinDiIssueApplicationKt:na]
	at de.darkatra.kotlindiissue.Service__BeanDefinitions.lambda$getServiceInstanceSupplier$0(Service__BeanDefinitions.java:18) ~[na:na]
	at org.springframework.util.function.ThrowingBiFunction.apply(ThrowingBiFunction.java:68) ~[de.darkatra.kotlindiissue.KotlinDiIssueApplicationKt:6.0.3]
	at org.springframework.util.function.ThrowingBiFunction.apply(ThrowingBiFunction.java:54) ~[de.darkatra.kotlindiissue.KotlinDiIssueApplicationKt:6.0.3]
	at org.springframework.beans.factory.aot.BeanInstanceSupplier.lambda$get$2(BeanInstanceSupplier.java:208) ~[na:na]
	at org.springframework.util.function.ThrowingSupplier.get(ThrowingSupplier.java:59) ~[de.darkatra.kotlindiissue.KotlinDiIssueApplicationKt:6.0.3]
	at org.springframework.util.function.ThrowingSupplier.get(ThrowingSupplier.java:47) ~[de.darkatra.kotlindiissue.KotlinDiIssueApplicationKt:6.0.3]
	at org.springframework.beans.factory.aot.BeanInstanceSupplier.invokeBeanSupplier(BeanInstanceSupplier.java:216) ~[na:na]
	at org.springframework.beans.factory.aot.BeanInstanceSupplier.get(BeanInstanceSupplier.java:208) ~[na:na]
	at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.obtainInstanceFromSupplier(AbstractAutowireCapableBeanFactory.java:1225) ~[de.darkatra.kotlindiissue.KotlinDiIssueApplicationKt:6.0.3]
	... 32 common frames omitted
```
