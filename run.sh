#!/bin/bash
# ============================================================
# Java Refresher Course — FutureFrame Services
# One-command compile & run script
# ============================================================
echo ""
echo "⚡ Java Refresher Course — FutureFrame Services"
echo "================================================"
echo ""

# Check Java
if ! command -v java &> /dev/null; then
  echo "❌ Java not found. Install JDK 17+ from https://adoptium.net/"
  exit 1
fi

JAVA_VER=$(java -version 2>&1 | awk -F '"' '/version/ {print $2}' | cut -d'.' -f1)
echo "✅ Java $JAVA_VER found"

# Option A — Maven (preferred)
if command -v mvn &> /dev/null; then
  echo "✅ Maven found — running with Maven"
  echo ""
  mvn -q compile exec:java
  exit $?
fi

# Option B — Plain javac (fallback)
if command -v javac &> /dev/null; then
  echo "✅ javac found — compiling manually"
  mkdir -p out
  find src -name "*.java" > sources.txt
  javac -d out @sources.txt
  echo ""
  java -cp out com.futureframeservices.javaRefresherCourse.Main
  exit $?
fi

echo "❌ Neither mvn nor javac found."
echo "   Install Maven: https://maven.apache.org/install.html"
echo "   Install JDK:   https://adoptium.net/"
exit 1
