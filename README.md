# Rest
`rest` is a library which let you develop rest application.

#### How to create rest jar and use it:
* Run - **mvn clean compile install**.
* It will put **rest-<version>.jar** in maven repo.
* Put following maven dependency in your project .
```
	<dependency>
		<groupId>com.dsi</groupId>
		<artifactId>rest</artifactId>
		<version>1.0.0</version> <!-- put appropriate version here -->
	</dependency>
```

#### Note:
* Make sure **servlet 3** in available in project.
```
	<dependency>
	    <groupId>javax.servlet</groupId>
	    <artifactId>javax.servlet-api</artifactId>
	    <version>3.0.1</version>
	    <scope>provided</scope>
	</dependency>
```

#### How to run example:
* Download Apache Tomcat 8.x or above (See - http://tomcat.apache.org/download-80.cgi) and unzip it.
* Go to **rest-example** directory.
* Run - **mvn clean compile install** in terminal. It will results **rest.war** in **target** directory.
* Copy **rest.war** and paste to **webapp** directory inside unzipped tomcat directory.
* Start the tomcat and try - **localhost:8080/rest/plainTextGetRequest** in browser.
* You will see some response in browser. **voilà**!

#### Version
1.0.0

