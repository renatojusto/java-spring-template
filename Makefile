build:
	./mvnw -f pom.xml -Pprod verify jib:dockerBuild -Pintegration-test
