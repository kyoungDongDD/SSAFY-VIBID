# 이미지 빌드
./mvnw clean package
docker build -t back ./
docker-compose up --build -d