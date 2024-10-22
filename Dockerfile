FROM adoptopenjdk:11-jre-hotspot-bionic
EXPOSE 9096
COPY target/timesheet.jar timesheet.jar
ENTRYPOINT ["java","-jar","/timesheet.jar"]