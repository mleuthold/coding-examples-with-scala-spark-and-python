#!/usr/bin/env bash

sbt clean assembly copyJarToRootDir

./bin/spark-submit \
	--deploy-mode client \
	--master local \
	--class de.marleu88.examples.MySparkBatchExample1 \
	/home/mleuthold/IdeaProjects/github/spark-structured-streaming/my-jars/MySparkExamples-assembly-0.1.jar \
	--input-path /home/mleuthold/IdeaProjects/github/spark-structured-streaming/src/test/resources/MySparkBatchExample1Spec/input/ \
    --output-path /home/mleuthold/Downloads/output/