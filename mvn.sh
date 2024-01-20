docker run \
-it --rm \
-v "$PWD":/usr/src \
-v "$HOME/.m2":/root/.m2 \
-w /usr/src \
markhobson/maven-chrome:jdk-8 \
mvn $*
