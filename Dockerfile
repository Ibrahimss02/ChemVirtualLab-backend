FROM openjdk:11
EXPOSE 5051:5051
RUN mkdir /app
COPY ./build/libs/fat.jar /app/chem-virtual-lab.jar
ENTRYPOINT ["java","-jar","/app/chem-virtual-lab.jar"]