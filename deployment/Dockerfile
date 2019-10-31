FROM andreclaudino/spark:2.4.3

ADD *.jar /startup.jar
ADD job_parameter_pi.yaml job_parameter_pi.yaml

ENTRYPOINT ["java", "-jar", "/startup.jar", "-s", "job_parameter_pi.yaml"]
