cd api-gateway
./gradlew build
cd ..

cd savings-service
./gradlew build
cd ..

docker-compose up --build

