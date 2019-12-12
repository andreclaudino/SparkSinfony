FROM gcr.io/master-sector-247616/ia-front/base-spark-multicloud:latest

# building gcr.io/master-sector-247616/ia-front/spark-sinfony-base

ADD *.jar /opt/sinfony-jar.jar

WORKDIR jobdir
ENV CONFIG_FILENAME="sinfony.yml"

ENTRYPOINT java -jar /opt/sinfony-jar.jar -s $CONFIG_FILENAME
