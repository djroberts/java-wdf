#!/bin/bash

# Check if a filename argument is provided
if [ -z "$1" ]; then
  echo "Please provide a Java file to compile and run."
  exit 1
fi

# Normalize the file path (remove leading ./ if present)
FILE_PATH="${1#./}"

# Check if the file exists
if [ ! -f "$FILE_PATH" ]; then
  echo "File not found: $FILE_PATH"
  exit 1
fi

# Ensure path starts with src/
if [[ "$FILE_PATH" != src/* ]]; then
  echo "Error: Java file must be inside the src/ directory."
  exit 1
fi

# Run Maven clean package first
echo "Running Maven clean package..."
mvn clean package
if [ $? -ne 0 ]; then
  echo "Maven build failed!"
  exit 1
fi

# Remove 'src/' and replace '/' with '.' to get the full class name
CLASS_PATH="${FILE_PATH#src/}"  # Strip 'src/' prefix
CLASS_PATH="${CLASS_PATH%.java}"  # Remove '.java' extension
FULL_CLASS_NAME="${CLASS_PATH//\//.}"  # Replace slashes with dots

# Debug output
echo "Compiling: $FILE_PATH"
echo "Full class name: $FULL_CLASS_NAME"

# Compile the Java file
javac -cp target/infernokitty-wdf.jar -d target/classes "$FILE_PATH"
if [ $? -ne 0 ]; then
  echo "Compilation failed!"
  exit 1
fi

# Run the Java class
java -cp target/infernokitty-wdf.jar:target/classes "$FULL_CLASS_NAME"
