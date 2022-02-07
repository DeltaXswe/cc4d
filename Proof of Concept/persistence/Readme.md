# Produlytics persistence class

## Build and deploy
1. Costruire il package
```
mvn package
```
2. installare nella repository locale di maven (da questo progetto)
```
mvn install:install-file -Dfile=.\target\persistence-${versione}.jar -DpomFile=pom.xml
```
## Nel progetto dove mi vuoi installare
3. Vai nel progetto dove lo vuoi installare
```
cd ../root_progetto
```
4. aggiungi la seguente riga nel `pom.xml`
```
<dependency>
    <groupId>it.deltax.produlytics</groupId>
    <artifactId>persistence</artifactId>
    <version>${versione}</version>
</dependency>
```
5. Installare `.\mvnw dependency:resolve`