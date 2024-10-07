#!/bin/bash

cp docker/Dockerfile .
docker build -t save-species .
rm /Users/miloszj/workspace/save-species/Dockerfile
