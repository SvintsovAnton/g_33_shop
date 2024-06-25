FROM maven:3.8.3-openjdk-17 as build

WORKDIR /workspace/app

COPY pom.xml .
COPY src src

RUN mvn -DskipTest=true clean package

RUN mkdir -p target/dependency &&(cd target/dependency; jar -xf ../*jar)

FROM eclipse-temurin:17-jre-alpine

ARG DEPEDENCY=/workspace/app/target/dependency

COPY --from=build ${DEPEDENCY}/BOOT-INF/lib app/lib
COPY --from=build ${DEPEDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPEDENCY}/BOOT-INF/classes /app

ENTRYPOINT ["java", "-cp","app:app/lib/*","de.ait_tr.g_33_shop.ShopApplication.java"]



