# Produlytics persistence class

## How to use
1. installare nella repository locale di maven (dal progetto dove vuoi installarlo)
```
.\mvnw install:install-file -Dfile=..\persistence\target\persistence-1.0.0.jar -DpomFile=..\persistence\pom.xml
```
2. aggiungi la seguente riga nel `pom.xml`
```
<dependency>
    <groupId>it.deltax.produlytics</groupId>
    <artifactId>persistence</artifactId>
    <version>1.0.0</version>
</dependency>
```
3. Installare `.\mvnw dependency:resolve`