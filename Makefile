.DEFAULT_GOAL := jar

classes: 
	ant compile

jar: 
	ant jar

37: 
	java -jar build/jar/SD.jar 37

38: 
	java -jar build/jar/SD.jar 38

39: 
	java -jar build/jar/SD.jar 39

40: 
	java -jar build/jar/SD.jar 40

clean: 
	ant clean