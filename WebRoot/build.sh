rm -rf *.class
rm -rf *.war
javac -cp ".:WEB-INF/lib/javax.servlet-api-3.0.1.jar:WEB-INF/lib/aws-java-sdk-bundle-1.11.228.jar" src/*.java -d WEB-INF/classes/
jar -cvf ROOT.war *
