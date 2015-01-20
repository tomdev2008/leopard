
cd /home/workspace/github/leopard
rm -f leopard-data/pom.xml
rm -f leopard-test/pom.xml

svn up;
cat leopard-data/pom2.xml > leopard-data/pom.xml
cat leopard-test/pom2.xml > leopard-test/pom.xml

