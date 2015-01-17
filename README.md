
Campus Planner App - Server Side

* To package the example run.

        mvn package

* To setup the database run.

        java -jar target/dropwizard-example-0.8.0-rc2-SNAPSHOT.jar db migrate uplan.yml

* To run the server run.

        java -jar target/dropwizard-example-0.8.0-rc2-SNAPSHOT.jar server uplan.yml

* To hit the Hello World example (hit refresh a few times).

	http://localhost:8080/hello-world
