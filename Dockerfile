# FROM hseeberger/scala-sbt:eclipse-temurin-11.0.14.1_1.6.2_3.1.1
# FROM buildo/scala-sbt-alpine:8_2.13.6_1.5.5
FROM anapsix/alpine-java:8u201b09_server-jre_nashorn

ENV GRPC_DB_URL=jdbc:postgresql://dpg-ch7d6n02qv26p1cbpjn0-a.frankfurt-postgres.render.com:5432/grpc_db
ENV GRPC_DB_USER=admin
ENV GRPC_DB_PASSWORD=iqJ3nafBtbMzFTu03j4yfzu7pg5KsrWS

CMD mkdir /home
ADD . /home
WORKDIR /home
CMD java -jar grpc.jar