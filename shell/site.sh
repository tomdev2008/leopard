#!/bin/bash
source /etc/profile

cd /data/src/leopard;svn up;mvn -f pom-site.xml site

