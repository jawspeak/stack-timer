#!/bin/bash

set -e

mvn eclipse:eclipse -DdownloadSources=true -DdownloadJavadocs=true

