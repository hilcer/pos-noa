#!/bin/sh

docker build -f micro-services/app-web/Dockerfile micro-services/app-web/ -t com.noa/app-web-posnoa:1.0.0 --platform linux/amd64

echo 'Se finalizo la construccion de imagenes'