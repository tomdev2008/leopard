svn up;mvn clean archetype:create-from-project
cd target/generated-sources/archetype;mvn deploy -U



root@db163:/home/tanmr/workspace/lexample# svn up;mvn clean archetype:create-from-project

root@db163:/home/tanmr/workspace/lexample/target/generated-sources/archetype# cd .;mvn deploy -U



mvn archetype:generate -DarchetypeRepository=http://leopard.io/nexus/content/repositories/snapshots/ -DarchetypeGroupId=io.leopard.archetype -DarchetypeArtifactId=lexample-archetype -DarchetypeVersion=0.0.1-SNAPSHOT -DgroupId=com.baidu.example -DartifactId=example -Dversion=0.0.1-SNAPSHOT

mvn archetype:generate -DarchetypeRepository=http://leopard.io/nexus/content/repositories/snapshots/ -DarchetypeGroupId=io.leopard.archetype -DarchetypeArtifactId=sexample-archetype -DarchetypeVersion=0.0.1-SNAPSHOT -DgroupId=com.baidu.example -DartifactId=example -Dversion=0.0.1-SNAPSHOT


#################################
svn up;mvn clean archetype:create-from-project
cd target/generated-sources/archetype;mvn deploy -U


mvn leopard:generate -DgroupId=com.baidu.example -DartifactId=example -Dversion=0.1-SNAPSHOT

mvn io.leopard.archetype:leopard:0.1:sexample -DgroupId=com.baidu.example

mvn io.leopard.maven.plugins:leopard-maven-plugin:0.1-SNAPSHOT:sexample -DgroupId=com.baidu.example

