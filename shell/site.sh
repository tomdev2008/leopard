#!/bin/bash
source /etc/profile

cd /data/src/leopard;svn up;mvn site -Psite

