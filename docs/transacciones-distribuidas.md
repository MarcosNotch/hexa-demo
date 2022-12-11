[< Inicio](../README.md)
# Transacciones distribuidas con JPA y Atomikos

Cuando es necesario que en una misma transacción 
se inserte y/o modifique información en diferentes bases de datos,
sería pertinente implementar un sistema de transacciones distribuidas.
Parece ser que este tipo de soluciones no son recomendables y por lo general
se asocian a malas decisiones de diseño o a sistemas "monoliticos".

La siguiente implementación sirve a modo de ejemplo y sería bueno revisar 
a fondo tanto el código como los conceptos y el contexto en donde 
se quiere utilizar antes de aplicarla.
(requiere ser muy cuidadoso al momento de configurar JPA)

## Implementacion:
#### 1. Agregar las dependencias en el archivo [pom.xml](../pom.xml)
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-jta-atomikos</artifactId>
</dependency>

<dependency>
    <groupId>com.atomikos</groupId>
    <artifactId>transactions-jta</artifactId>
    <version>4.0.6</version>
</dependency>
```
---
#### 2. Crear una clase que extienda de AbstractJtaPlatform
Este paso no seria necesario si se usa la implementacion de Spring `SpringJtaPlatform`
```java
import org.hibernate.engine.transaction.jta.platform.internal.AbstractJtaPlatform;

import javax.transaction.TransactionManager;
import javax.transaction.UserTransaction;

public class DistributedJtaPlatform extends AbstractJtaPlatform {

    static TransactionManager transactionManager;
    static UserTransaction userTransaction;

    @Override
    protected TransactionManager locateTransactionManager() {
        return transactionManager;
    }

    @Override
    protected UserTransaction locateUserTransaction() {
        return userTransaction;
    }
}
```
---
#### 3. Configurar un `TransactionManager` para las transacciones distribuidas
El valor de `userTransactionImp.setTransactionTimeout(10000);` es solo a modo de ejemplo
```java
import com.atomikos.icatch.jta.UserTransactionImp;
import com.atomikos.icatch.jta.UserTransactionManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.jta.JtaTransactionManager;

import javax.transaction.TransactionManager;
import javax.transaction.UserTransaction;

@Configuration
@EnableTransactionManagement
public class DistributedTransactionManagerConfig {

    @Bean
    public UserTransaction atomikosUserTransaction() throws Throwable {
        UserTransactionImp userTransactionImp = new UserTransactionImp();
        userTransactionImp.setTransactionTimeout(10000);
        return userTransactionImp;
    }

    @Bean(destroyMethod = "close", initMethod = "init")
    public TransactionManager atomikosTransactionManager() {
        UserTransactionManager userTransactionManager = new UserTransactionManager();
        userTransactionManager.setForceShutdown(false);

        DistributedJtaPlatform.transactionManager = userTransactionManager;

        return userTransactionManager;
    }

    @Bean
    @Primary
    public PlatformTransactionManager distributedTransactionManager(
            @Qualifier("atomikosUserTransaction") UserTransaction userTransaction,
            @Qualifier("atomikosTransactionManager") TransactionManager transactionManager
    ) throws Throwable {

        DistributedJtaPlatform.userTransaction = userTransaction;

        return new JtaTransactionManager(userTransaction, transactionManager);
    }
}
```
---
#### 4. Configurar las conexiones a las bases de datos correctamente
Configuracion de MySQL a modo de ejemplo. 

Notese la etiqueta `@EnableJpaRepositories` 
donde se le indica en la propiedad `transactionManagerRef` 
el nombre del Bean (PlatformTransactionManager) que creamos 
en el paso anterior `distributedTransactionManager`.

No olvidar de colocar las variables indicadas en el archivo de propiedades.

Cada base de datos puede que requiera de configuraciones diferentes y/o externas
para funcionar.
```java
import com.mysql.cj.jdbc.MysqlXADataSource;
import lombok.RequiredArgsConstructor;
import org.hibernate.engine.transaction.jta.platform.internal.AtomikosJtaPlatform;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jta.atomikos.AtomikosDataSourceBean;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import javax.sql.DataSource;
import java.util.HashMap;

@RequiredArgsConstructor
@Configuration
@EnableJpaRepositories(
        basePackages = {"com.example.demo.*"},
        entityManagerFactoryRef = "mysqlEntityManagerFactory",
        transactionManagerRef = "distributedTransactionManager"
)
public class MySQLConfig {

    private final Environment env;

    @Bean
    public DataSource mysqlDataSource() throws Exception {
        MysqlXADataSource mysqlXADataSource = new MysqlXADataSource();
        mysqlXADataSource.setPinGlobalTxToPhysicalConnection(true);
        mysqlXADataSource.setUrl(env.getProperty("spring.datasource.mysql.url"));
        mysqlXADataSource.setUser(env.getProperty("spring.datasource.mysql.user"));
        mysqlXADataSource.setPassword(env.getProperty("spring.datasource.mysql.password"));
        
        AtomikosDataSourceBean atomikosDataSourceBean = new AtomikosDataSourceBean();
        atomikosDataSourceBean.setUniqueResourceName(env.getProperty("spring.datasource.mysql.unique-resource-name"));
        atomikosDataSourceBean.setXaDataSource(mysqlXADataSource);
        return atomikosDataSourceBean;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean mysqlEntityManagerFactory(
            @Qualifier("mysqlDataSource") DataSource mysqlDataSource,
            EntityManagerFactoryBuilder builder
    ) {
        HashMap<String, Object> properties = new HashMap<String, Object>();
        properties.put("hibernate.transaction.jta.platform", AtomikosJtaPlatform.class.getName());
        properties.put("javax.persistence.transactionType", "JTA");

        return builder
                .dataSource(mysqlDataSource)
                .properties(properties)
                .packages("com.example.demo.*")
                .build();
    }
}
```
