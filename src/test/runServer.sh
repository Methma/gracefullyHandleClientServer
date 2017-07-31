#!/usr/bin/env bash

SERVICE_PORT=$9898
MAX_SIZE=$3;
MIN_SIZE=$2;
QUEUE_SIZE=$2;

java -jar ./out/artifacts/IdeaProjects_jar/IdeaProjects.jar ${SERVICE_PORT} ${MAX_SIZE} ${MIN_SIZE} ${QUEUE_SIZE}
