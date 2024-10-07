#!/bin/bash

cp docker/Dockerfile .
docker build -t save-species .
rm ./Dockerfile
