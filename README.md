# Spring boot axis 1.4 webService Server

Hello world demo of Axis 1.4 setup on Spring Boot 2.4.x.

## Usage

```
git clone https://github.com/alaeessaki/spring-boot-axis1.4-server.git
```

Then import the project in your IDE, and run it as a spring boot application with a specified port.
you can find axis server on `localost:<port>/axis` :

### to configure your webService :

1 - Create a Service annotated with `@Service` implementing a service interface with your own methods and signatures.
2 - Implements the service interface in `AxisWbeService`, then get the bean of your Service Impl, or just simply use `@Autowired`, and Override the methods.
This is the class that is registered in server-config.wsdd, and it's designed to separate the RPC aspects of the SOAP API from your spring based implementation.
3 - Modify the `server-config.wsdd` in `src/main/webapp/WEB-INF` (change the service name, namespaces and the operations)

This is an example :

```
<service name="Service" provider="java:RPC" style="rpc"
    use="encoded">
    <namespace>http://tempuri/</namespace>
    <parameter name="wsdlTargetNamespace"
        value="http://tempuri/" />
    <parameter name="wsdlServiceElement" value="Service" />
    <parameter name="schemaUnqualified"
        value="http://tempuri/Imports" />
    <parameter name="wsdlServicePort"
        value="BasicHttpBinding_ServiceTest" />
    <parameter name="className"
        value="com.exemple.service.AxisWebService" />
    <parameter name="wsdlPortType" value="ServiceTest" />
    <parameter name="typeMappingVersion" value="1.2" />
    <parameter name="allowedMethods" value="*" />

    <operation name="add" qname="operNS:add"
        xmlns:operNS="http://tempuri/" returnQName="addReturn"
        returnType="rtns:int" xmlns:rtns="http://www.w3.org/2001/XMLSchema"
        soapAction="http://tempuri/add">
        <parameter qname="a" type="tns:int"
            xmlns:tns="http://www.w3.org/2001/XMLSchema" />
        <parameter qname="b" type="tns:int"
            xmlns:tns="http://www.w3.org/2001/XMLSchema" />
    </operation>
    <operation name="show"
        qname="operNS:show" xmlns:operNS="http://tempuri/"
        returnQName="showReturn" returnType="rtns:string"
        xmlns:rtns="http://www.w3.org/2001/XMLSchema"
        soapAction="http://cpms-santeclair/Identification">
        <parameter qname="tmp" type="tns:string"
            xmlns:tns="http://www.w3.org/2001/XMLSchema" />
    </operation>
    <parameter name="allowedMethods" value="*" />
</service>
```

4 - Run your application :

The webService can be found in : `localhost:<port>/axis/<service-name>`.
the WSDL can be found in : `localhost:<port>/axis/<service-name>?wsdl`.


### Log configuration

Axis uses an old versio of log4j, while spring boot 2.x use log4j2.
To fix the conflict, I exluded the log4j2 from spring boot starter, and i changed the implementation to use the log4j with an older version configuration.

in `commons.logging.properties` :

```
org.apache.commons.logging.Log = org.apache.commons.logging.impl.Log4JLogger  
org.apache.commons.logging.LogFactory = org.apache.commons.logging.impl.LogFactoryImpl
```

in `log4j.properties` :

- Change the `log4j.appender.file.FILE` to your log file location.
- Change the logging level.

```
# Root logger option
log4j.rootLogger=DEBUG, stdout, file

# Redirect log messages to console
log4j.appender.stdout=org.apache.log4j.ConsoleAppender
log4j.appender.stdout.Target=System.out
log4j.appender.stdout.layout=org.apache.log4j.PatternLayout
log4j.appender.stdout.layout.ConversionPattern=%d{yyyy-MM-dd HH:mm:ss} %-5p %c{1}:%L - %m%n

# Redirect log messages to a log file, support file rolling.
log4j.appender.file=org.apache.log4j.RollingFileAppender
log4j.appender.file.File=c:/project/resources/t-output/log4j-application.log
log4j.appender.file.MaxFileSize=5MB
log4j.appender.file.MaxBackupIndex=10
log4j.appender.file.layout=org.apache.log4j.PatternLayout
log4j.appender.file.layout.ConversionPattern=%d{yyyy-MM-dd HH:mm:ss} %-5p %c{1}:%L - %m%n
```

## Deploiment 

This demo is tested on `tomcat 8.5.58` (archived version), and tested on `8.5.64` and `9`

locate to your projet location and run this command :

```
mvn clean install
```

after the build, you'll find the `.war` in the target folder.

Import the war file from your tomcat-manager. 

And Voilà !!!


