#!/bin/bash

aws dynamodb create-table --cli-input-json file://create-table-donations.json --endpoint-url http://localhost:8000
aws dynamodb create-table --cli-input-json file://create-table-species.json --endpoint-url http://localhost:8000

