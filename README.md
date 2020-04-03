# neodymium-plugin-applitools
##To include this plugin in your maven project you will need

* `~/.m2/settings.xml` file, which should contain this text:
```xml
          <settings xmlns="http://maven.apache.org/SETTINGS/1.0.0"
           xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
           xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.0.0
                      http://maven.apache.org/xsd/settings-1.0.0.xsd">
	          <servers>
	            <server>
	              <id>github</id>
	              <username>YOUR_GITHUB_LOGIN</username>
	              <password>YOUR_GITHUB_TOKEN</password>
	            </server>
	          </servers>
          </settings>
```
where YOUR\_GITHUB\_LOGIN should be replaced with your github login (username or email) 
and YOUR\_GITHUB\_TOKEN with your personal access token, which should have read:packages permission
to generate one folow [this tutorial](https://help.github.com/en/github/authenticating-to-github/creating-a-personal-access-token-for-the-command-line/)
*  as you have your settings ready add folowing to your project's `pom.xml`
```xml
        <repositories>
	          <repository>
		            <id>github</id>
		            <name>GitHub Neodymium Applitools Plugin</name>
		            <url>https://maven.pkg.github.com/Xceptance/neodymium-plugin-applitools</url>
		            <releases>
			              <enabled>true</enabled>
		            </releases>
		            <snapshots>
			              <enabled>true</enabled>
		            </snapshots>
	          </repository>
        </repositories>
```
* and the dependency
```xml
        <dependency>
           <groupId>com.xceptance</groupId>
           <artifactId>neodymium-plugin-applitools</artifactId>
           <version>0.0.1-SNAPSHOT</version>
        </dependency>
```
* if dependency is still unresolved, please run ` mvn clean install` and then update your maven project
