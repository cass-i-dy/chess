# ♕ BYU CS 240 Chess

This project demonstrates mastery of proper software design, client/server architecture, networking using HTTP and WebSocket, database persistence, unit testing, serialization, and security.

## 10k Architecture Overview

The application implements a multiplayer chess server and a command line chess client.

[![Sequence Diagram](10k-architecture.png)](https://sequencediagram.org/index.html#initialData=C4S2BsFMAIGEAtIGckCh0AcCGAnUBjEbAO2DnBElIEZVs8RCSzYKrgAmO3AorU6AGVIOAG4jUAEyzAsAIyxIYAERnzFkdKgrFIuaKlaUa0ALQA+ISPE4AXNABWAexDFoAcywBbTcLEizS1VZBSVbbVc9HGgnADNYiN19QzZSDkCrfztHFzdPH1Q-Gwzg9TDEqJj4iuSjdmoMopF7LywAaxgvJ3FC6wCLaFLQyHCdSriEseSm6NMBurT7AFcMaWAYOSdcSRTjTka+7NaO6C6emZK1YdHI-Qma6N6ss3nU4Gpl1ZkNrZwdhfeByy9hwyBA7mIT2KAyGGhuSWi9wuc0sAI49nyMG6ElQQA)

Link to Sequence Diagram for Phase 

https://sequencediagram.org/index.html?presentationMode=readOnly&shrinkToFit=true#initialData=IYYwLg9gTgBAwgGwJYFMB2YBQAHYUxIhK4YwDKKUAbpTngUSWOZVYSgM536HHCkARYGGABBECE5cAJsOAAjYBxQxp8zJnRgoATw64iaAOYwADADoAnNwZ9ScABb8jKALIoAjJiNQIAV2wYAGI0YCodGAAlFCMkDm1hJAg0YIB3ByQwFExEVFIAWgA+FmpKAC4YAG0ABQB5MgAVAF0YAHo-ZSgAHTQAbw7KUIBbFAAaGFwODlToaXGUIeAkBABfTApS2CKStkkOCujY+KhE5J32cagYuKyoAAoBqGGxiaVp2fnF5YBKddZ2DgwbZCETiPb7GAuMAAVU6D06z1+ILEEikQOKagqZAAogAZbFwBowR7PGAAM18Q2JnUwyLBaPy2w2u04FTQfgQCD+1AB6JgdNRUwqICuwhQsMo8MGwBG40m7yg0iRcnpUz5mJgAEkAHI4yJEkkyl7ymaKz5LBBa7UNWrU2jM3nAlWCiEilBi0R+MAOKVPI3K0Eu9XyCo6vUGhFG8bAL0OBoQADW6CtNpgMe9tOd4KBTP+ewq6bjifQ3JZgMZxWZ5TTsfjSbQpcoOeKuS0FQATKZTD1eoW6+g1uhpBofP5AkFoM4VLiILEUkF0plsq2CpXWNWavVmm1lFMkmge4bZa8pqbpGsq1Aig78zAZ3PzpJxgAxJBoaT3t++55yt5n343pwRQCuCFRQhK9xHigAYouCRQaji+KEnafojOSlIoZmgZwdeeasihP6qPIAD6JofDACwWqWALAVmUhlCADgoCACYQd+UZkWaRGkX+swAXhHC0dh9EwG6HqxuxIwwaqgmFBqYbYvqBFRjW3r9ikOqpoWWGwVIFaARCUHRrWxYNgZ+nrlAFRGapRb1o2WzbCuYAdl2h6RsefamWsmjviOvgBMEPgoMm97+MwC4ZFkOTIFofKXhUlQCHi2INNiLStLuHD7j0tnqQ5uE8ren4pIB4z3rUXpsV59b8UVQGFCB9HSCgCAoFknrencNXoNJLrwSGUTYq4tQAGrYnlpnoRAVLaU1UwWfV+zspy1F7ItNBWStXKXhWzllJ2pi+cO3gBeOVzSHeNwwAA4kagKRUuMV5MwuabIlN2pRlRhGj0PVmZZhVlhUd1oWVt3taDnDdSZtVrQ180Qo4zEJp1Pr-X1cFySGiEEkS-3TbNsbw7JiNgZD913JjenY1iKXIT9IyagIhOQkaOkyQNdNIUSjMoNqRqs3zHP9bT5D00SADqUXip0AtoRSM1syMItYwhEswAAQggoCsXLguK1SwuIxtAJlHzzPjHz8svNLS4QTb4za7rDvs+ZQPlBbAhW0ajswHbWSu8ezssUHKBNAVRT7TAh09l7PsjH7Aey5Qfsh3rqfs8d-ljsE2B+FA2BtfAopZLdguPdFznxZZiV1I032-aEsPoHHvtGhegOFAZZRQ4+LxwKXKBQ3c1sqRjJNCbpQrwExLFozDammdTC1i7jyEE4btmT41dFCmJWRQ8zo-t7KydhwAvNtTs66H+sjFfHIICvsnybqim86fLzx-7MthzfLt74qE0raYW5ld7CRnlCI+0gqaqxpurHm5cmYsy3sbPegkgZm2VigZmk8PZWS9pHQo+0iHZ1OrnIIOhWoIAgKkGAAApCAb5kEqCCPIW+CZnpxTeptOu0JtytD5n9FuB4+jOTgBAWhUAE64IEJ3TYWD8wACtmFoBHv9cYEipHQFkczOqZYp4yTKMjeeEkJ4mzXhrTeGFtLgLJpCCmIwT4oJflzden8UFC3ZibJR+Ex7Hh-ufIBAC76ZxVvYjBZRlBgBHgE7+RpLbwFihgSR0i3FiwUkpeJejvbJJemk6AKZQE+IwabW8218Hd1rjASpu0o4pJcjHLsGghw50CkEHwwAIiIHdLAYA2Ai6EFOPORcVdGk13elUZK+I0oZTUAVapS0QaC3BocG4TZwYlX7qJNqeBsSbR0N6N8Rg4GRMgfsEAeyoAjwyRqOA+JRCRBwTvBxVy+klTgZY+5jznmPFeVE3ZfT1nxElHcwaDzsRPO3r4pZwNalPyqQlBFq16kkMaa5I6bTMBAA

## IntelliJ Support

Open the project directory in IntelliJ in order to develop, run, and debug your code using an IDE.

## Maven Support

You can use the following commands to build, test, package, and run your code.

| Command                    | Description                                     |
| -------------------------- | ----------------------------------------------- |
| `mvn compile`              | Builds the code                                 |
| `mvn package`              | Run the tests and build an Uber jar file        |
| `mvn package -DskipTests`  | Build an Uber jar file                          |
| `mvn install`              | Installs the packages into the local repository |
| `mvn test`                 | Run all the tests                               |
| `mvn -pl shared test`     | Run all the shared tests                        |
| `mvn -pl client exec:java` | Build and run the client `Main`                 |
| `mvn -pl server exec:java` | Build and run the server `Main`                 |

These commands are configured by the `pom.xml` (Project Object Model) files. There is a POM file in the root of the project, and one in each of the modules. The root POM defines any global dependencies and references the module POM files.

### Running the program using Java

Once you have compiled your project into an uber jar, you can execute it with the following command.

```sh
java -jar client/target/client-jar-with-dependencies.jar

♕ 240 Chess Client: chess.ChessPiece@7852e922
```
