FROM hseeberger/scala-sbt:eclipse-temurin-11.0.14.1_1.6.2_3.1.1

ENV GRPC_DB_URL=jdbc:postgresql://dpg-ch7d6n02qv26p1cbpjn0-a.frankfurt-postgres.render.com:5432/grpc_db
ENV GRPC_DB_USER=admin
ENV GRPC_DB_PASSWORD=iqJ3nafBtbMzFTu03j4yfzu7pg5KsrWS

CMD mkdir /home
ADD . /home
WORKDIR /home
CMD sbt "runMain server.Server"
# CMD wget https://github.com/sbt/sbt/releases/download/v1.8.2/sbt-1.8.2.tgz && tar -xzf sbt-1.8.2.tgz && sbt/bin/sbt "runMain server.Server"