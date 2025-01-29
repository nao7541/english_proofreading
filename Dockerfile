# ステージ1: Mavenを使用してSpring Bootアプリケーションをビルド
FROM maven:3.9.9-amazoncorretto-21 AS build-app
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# ステージ2: 実行用の最小イメージ
FROM amazoncorretto:21
WORKDIR /app
COPY --from=build-app /app/target/*.jar app.jar

# gcloud sdkのインストール
RUN yum install -y curl tar gzip && \
    curl -O https://dl.google.com/dl/cloudsdk/channels/rapid/downloads/google-cloud-sdk-367.0.0-linux-x86_64.tar.gz && \
    tar -zxvf google-cloud-sdk-367.0.0-linux-x86_64.tar.gz && \
    ./google-cloud-sdk/install.sh

# 認証スクリプトのコピー
COPY gcloud-auth.sh /app/gcloud-auth.sh
RUN chmod +x /app/gcloud-auth.sh

EXPOSE 8080
ENTRYPOINT ["/app/gcloud-auth.sh"]