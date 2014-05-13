# Compiling src
find src -name "*.java" -print0 | while IFS= read -r -d $'\0' line; do
	javac -d bin -sourcepath src:tests -cp /usr/share/java/junit4.jar "$line"
done

# Compiling tests
find tests -name "*.java" -print0 | while IFS= read -r -d $'\0' line; do
	javac -d bin -sourcepath src:tests -cp /usr/share/java/junit4.jar "$line"
done