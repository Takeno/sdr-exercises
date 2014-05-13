find tests -name "*.java" -print0 | while IFS= read -r -d $'\0' line; do
	class=`echo $line | awk '{print substr($0, 7)}' | awk 'gsub("/", ".")' | awk 'gsub(".java", "")'`
	java -cp ./bin/:/usr/share/java/junit4.jar org.junit.runner.JUnitCore "$class"
done

#java -cp ./bin/:/usr/share/java/junit4.jar org.junit.runner.JUnitCore it.uniroma3.domain.ComplexTest