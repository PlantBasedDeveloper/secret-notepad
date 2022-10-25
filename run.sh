# build project
mvn clean package
# generate docs
mvn clean install javadoc:javadoc
# build image
docker build -t secret-notepad:1.0 .
# run image
docker run -p 1234:1234 secret-notepad:1.0