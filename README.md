# CrudWithVaadin

Datum: 02.10.2019
Auszubildender : Surattana Bopp
Projekt Name : CrudWithVaadin als eines Teil des Praktikums und Übungen

:spring_version: current
:spring_boot_version: 2.1.6.RELEASE

Demo Crud (Create Read Update Delete) Projekt mit Spring Framework und Vaadin sowie als Datenbank MySQL
Dieses Handbuch führt Sie durch den Prozess der Erstellung einer  Spring-Anwendung, die mit ein em verbundenen MySQL-Datenbank und  die eine Vaadin auf einem Spring Data JPA  verwendet.
um auf die Datenbank zuzugreifen, Dies ist jedoch nur eine von vielen möglichen Optionen (z. B. können Sie die einfache Feder verwenden JDBC).

Was Sie bauen werden?
Sie erstellen eine Vaadin-Benutzeroberfläche für ein einfaches JPA-Repository.
Sie erhalten eine App mit vollständiger CRUD-Funktionalität (Erstellen, Lesen, Aktualisieren, Löschen) und einem Filterbeispiel, das eine benutzerdefinierte Repository Methode verwendet.

Sie erstellen eine MySQL-Datenbank, erstellen eine Spring-Anwendung und verbinden sie mit der neuen erstellte Datenbank.

Was Sie brauchen werden?
* https://dev.mysql.com/downloads/[MySQL] version 5.6 oder besser.
- Java_version : 1.8 
include:https://github.com/nuna-lamp/CrudWithVaadin/blob/master/README.md
https://nodejs.org/en/[NodeJS 10.x oder höher] wird auch von Vaadin benötigt, um das Front-End-Ressourcenpaket zu generieren. Sie können NodeJS mit dem folgenden Maven-Befehl lokal in Ihrem aktuellen Projekt installieren: `mvn com.github.eirslett: frontend-maven-plugin: 1.7.6: install-node-and-npm -DnodeVersion =" v10.16.3 "`

Beginnend mit Spring Initializr
Für alle Spring-Anwendungen sollten Sie mit https://start.spring.io [Spring beginnen Initializr]. Der Initializr bietet eine schnelle Möglichkeit, alle Abhängigkeiten zu erfassen, die Sie benötigen eine Anwendung und erledigt einen Großteil der Einrichtung für Sie. Dieses Beispiel benötigt das Spring Web Abhängigkeiten zwischen Starter, Spring Data JPA und MySQL-Treiber. 

Sie können Gradle verwenden. Es zeigt auch die Werte von "de.lamp" als die Gruppe bzw. Artefakt.
 
Erstellen Sie die Verzeichnisstruktur:
Erstellen Sie in einem Projektverzeichnis Ihrer Wahl die folgende Unterverzeichnisstruktur. Zum Beispiel mit mkdir -p src / main / java / hello auf * nix-Systemen:

Die folgende Auflistung zeigt die Datei `pom.xml`, die bei Auswahl von Maven erstellt wurde:

====
[src,xml]
----
include::CrudWithVaadin/pom.xml 
/pom.xml[]
----
====

Die folgende Auflistung zeigt die Datei "build.gradle", die bei Auswahl von "Gradle" erstellt wurde:
====
[src,groovy]
----
include::CrudWithVaadin/build.gradle[]
==

Erstellen Sie die Datenbank

Öffnen Sie ein Terminal (Eingabeaufforderung in Microsoft Windows) und öffnen Sie einen MySQL-Client als Benutzer
Wer kann neue Benutzer erstellen.
Verwenden Sie beispielsweise auf einem Linux-System den folgenden Befehl.

====
[source, sh]
----
$ sudo mysql -u <username> -p <password>
----
====

HINWEIS: Dies verbindet sich mit MySQL als "root" und ermöglicht dem Benutzer den Zugriff von allen Hosts. Diese ist * nicht die empfohlene Methode * für einen Produktionsserver.

Führen Sie zum Erstellen einer neuen Datenbank die folgenden Befehle an der Eingabeaufforderung `mysql` aus:

====
[source, mysql]
----
mysql> create database db_example; -- Erstellt die neue Datenbank
mysql> create user 'springuser'@'%' identified by 'ThePassword'; -- Erstellt den Benutzer
mysql> grant all on db_example.* to 'springuser'@'%'; -- Gewährt dem neuen Benutzer alle Berechtigungen für die neu erstellte Datenbank
----
====

Erstellen Sie die Datei "application.properties"

Spring Boot bietet Standardeinstellungen für alle Dinge. Beispielsweise ist die Standarddatenbank "H2". Wenn Sie also eine andere Datenbank verwenden möchten, müssen Sie die Verbindung definieren Attribute in der Datei "application.properties".
Erstellen Sie eine Ressourcendatei mit dem Namen "src / main / resources / application.properties.dist" als Die folgende Auflistung zeigt:

====
[source, java]
----
include::CrudWithVaadin/src/main/resources/application.properties.dist 
----
====
Hier kann "spring.jpa.hibernate.ddl-auto" "none", "update", "create" oder "create-drop" sein.
Weitere Informationen finden Sie in der Dokumentation zum Ruhezustand.

* `none`: Die Standardeinstellung für` MySQL`. An der Datenbankstruktur werden keine Änderungen vorgenommen.
* `update`: Hibernate ändert die Datenbank gemäß den angegebenen Entitätsstrukturen.
* `create`: Erstellt die Datenbank jedes Mal, lässt sie jedoch nicht beim Schließen fallen.
* `create-drop`: Erstellt die Datenbank und löscht sie, wenn` SessionFactory` geschlossen wird.

Die Standardeinstellung für "H2" und andere eingebettete Datenbanken ist "create-drop". Für andere Datenbanken wie `MySQL` ist die Standardeinstellung` none`.

Erstellen Sie das Modell `@ Entity`

Sie müssen das Entitätsmodell wie folgt erstellen (in `src / main / java / hello / Customer.java`) zeigt:
Dieses Beispiel ist eine Fortsetzung von link: / gs / accessing-data-jpa [Zugriff auf Daten mit JPA]. Der einzige Unterschied besteht darin, dass die Entitätsklasse über Getters und Setter verfügt und die benutzerdefinierte Suchmethode im Repository für Endbenutzer etwas eleganter ist. Sie müssen diesen Leitfaden nicht lesen, um durch diesen zu gehen, aber Sie können, wenn Sie möchten.

Wenn Sie mit einem neuen Projekt begonnen haben, fügen Sie die folgenden Entitäts- und Repository-Objekte hinzu, und Sie können loslegen. Falls Sie mit dem "initial" Schritt begonnen haben, sind diese bereits für Sie verfügbar.

`src/main/java/hello/Customer.java`
[source,java]
----
include::CrudWithVaadin/src/main/java/hello/Customer.java[]
----

`src/main/java/hello/CustomerRepository.java`
[source,java]
----
include::CrudWithVaadin/src/main/java/hello/CustomerRepository.java[]
----

Sie können die Spring Boot-basierte Anwendung intakt lassen, da sie unsere Datenbank mit einigen Beispieldaten füllt.

`src/main/java/hello/Application.java` 
[source,java]

Vaadin dependencies:
Wenn Sie das “ initial“ Projekt ausgecheckt haben, sind bereits alle erforderlichen Abhängigkeiten eingerichtet. Sehen Sie sich jedoch an, was Sie tun müssen, um einem neuen Spring-Projekt Vaadin-Unterstützung hinzuzufügen. Die Vaadin Spring-Integration enthält eine Abhängigkeitssammlung für Spring-Starter. Sie müssen also nur diesen Maven-Snippet oder eine ähnliche Gradle-Konfiguration hinzufügen:

[source,xml,indent=0]
----
include::CrudWithVaadin/pom.xml[tag=starter]
----

Gradle unterstützt standardmäßig keine "BOMs(Bill-of-Materials)", es gibt jedoch ein praktisches https://plugins.gradle.org/plugin/io.spring.dependency-management[plugin]. In der https://github.com/nuna-lamp/CrudWithVaadin/blob/master/build.gradle. gradle-Builddatei finden Sie ein Beispiel, wie Sie dasselbe erreichen können.

Definieren Sie die MainView-Klasse

Die MainView-Klasse ist der Einstiegspunkt für die UI von Vaadin. In Spring Boot-Anwendungen müssen Sie sie nur mit "@ Route" kommentieren. Sie wird von Spring automatisch abgerufen und im Stammverzeichnis Ihrer Web-App angezeigt. Sie können die URL anpassen, unter der die Ansicht angezeigt wird, indem Sie der Route Annotation einen Parameter zuweisen. 

[source,java]
----
incloud::CrudWithVaadin/src/main/java/hello/MainView.java
----
package hello;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.util.StringUtils;

@Route
public class MainView extends VerticalLayout {

	public MainView() {

		HorizontalLayout actions = new HorizontalLayout(filter, addNewBtn);
		add(actions, grid, editor);
		grid.setHeight("300px");
		grid.setColumns("id", "firstName", "lastName");
		grid.getColumnByKey("id").setWidth("50px").setFlexGrow(0);

		filter.setPlaceholder("Filter by last name");
		grid.asSingleSelect().addValueChangeListener(e -> {
			editor.editCustomer(e.getValue());
	}
}
----

Entitäten in einem Datenraster auflisten

Verwenden Sie für ein ansprechendes Layout die Komponente "Grid". Die Liste der Entitäten aus einem vom Konstruktor injizierten "CustomerRepository" kann einfach mit der setItems-Methode an das Grid übergeben werden. Der Körper Ihres `MainView` würde sich wie folgt erweitern:

[source,java]
----
@Route
public class MainView extends VerticalLayout {

	private final CustomerRepository repo;
	final Grid<Customer> grid;

	public MainView(CustomerRepository repo) {
		this.repo = repo;
		this.grid = new Grid<>(Customer.class);
		add(grid);
		listCustomers();
	}

	private void listCustomers() {
		grid.setItems(repo.findAll());
	}

}
----
ANMERKUNG: Wenn Sie große Tabellen und viele gleichzeitige Benutzer haben, möchten Sie höchstwahrscheinlich nicht das gesamte Dataset an Ihre UI-Komponenten binden.

Obwohl Vaadin Grid  Lazy die Daten vom Server  in den Browser lädt, behält diese Lösung oben die gesamte Liste der Daten im Serverspeicher. Um Speicherplatz zu sparen, können Sie nur die besten Ergebnisse anzeigen, Paging verwenden oder mit der Methode `setDataProvider (DataProvider)` einen verzögert ladenden Datenprovider bereitstellen.

Daten filtern

Bevor der große Datensatz zu einem Problem für Ihren Server wird, bereitet er Ihren Benutzern Kopfschmerzen, wenn sie versuchen, die relevante Zeile zu finden, die sie bearbeiten möchten. Verwenden Sie eine `TextField` -Komponente, um einen Filtereintrag zu erstellen. Ändern Sie zunächst die Methode listCustomer (), um die Filterung zu unterstützen:

[source,java,indent=0]
----
include::CrudWithVaadin/src/main/java/hello/MainView.java[tag=listCustomers]
----

HINWEIS: Hier sind die deklarativen Abfragen von Spring Data besonders praktisch. Das Schreiben von "findByLastNameStartsWithIgnoringCase" ist eine einzeilige Definition in "CustomerRepository".

Binden Sie einen Listener in die `TextField` -Komponente ein und fügen Sie dessen Wert in diese Filtermethode ein. Der `ValueChangeListener` wird während der Eingabe automatisch aufgerufen, während wir den` ValueChangeMode.EAGER` für das Filtertextfeld definieren.

[source,java]
----
final TextField filter;
TextField filter = new TextField();
filter.setPlaceholder("Filter by last name");
filter.setValueChangeMode(ValueChangeMode.EAGER);
filter.addValueChangeListener(e -> listCustomers(e.getValue()));

----
Definieren Sie die Editor-Komponente

Da Vaadin-UIs nur reiner Java-Code sind, gibt es keine Entschuldigung dafür, keinen wiederverwendbaren Code von Anfang an zu schreiben. Definieren Sie eine Editor-Komponente für Ihre Kunden-Entität. Sie machen es zu einem Spring-Managed-Bean, damit Sie das CustomerRepository direkt in den Editor einfügen und die C-, U- und D-Teile oder unsere CRUD-Funktionalität in Angriff nehmen können

`src/main/java/hello/CustomerEditor.java`
[source,java]
----
include::src/main/java/hello/CustomerEditor.java

----
In einer größeren Anwendung können Sie diese Editor-Komponente dann an mehreren Stellen verwenden. Beachten Sie auch, dass Sie in großen Anwendungen möglicherweise einige gängige Muster wie MVP anwenden möchten, um Ihren UI-Code zu strukturieren (was außerhalb des Geltungsbereichs dieses Handbuchs liegt).


Verdrahten Sie den Editor

In den vorherigen Schritten haben Sie bereits einige Grundlagen der komponentenbasierten Programmierung kennengelernt. Mit einem "Button" und einem Auswahllistener für "Grid" können Sie unseren Editor vollständig in die Hauptansicht integrieren. Die endgültige Version der Klasse "MainView" sieht folgendermaßen aus:

`src/main/java/hello/MainView.java`
[source,java]
----
incloud:: src/main/java/hello/MainView.java
----
Zusammenfassung
Eine CRUD-UI-Anwendung mit vollem Funktionsumfang geschrieben, die aus Gründen der Persistenz Spring Data JPA verwendet. die an MySQL gebunden ist Datenbank 

Siehe auch
Die folgenden Anleitungen können ebenfalls hilfreich sein:

* https://spring.io/guides/gs/spring-boot/[Building an Application with Spring Boot]
* https://spring.io/guides/gs/accessing-data-jpa/[Accessing Data with JPA]
* https://spring.io/guides/gs/accessing-data-mongodb/[Accessing Data with MongoDB]
* https://spring.io/guides/gs/accessing-data-gemfire/[Accessing Data with GemFire]
* https://spring.io/guides/gs/accessing-data-neo4j/[Accessing Data with Neo4j]
* https://spring.io/guides/gs/accessing-data-mysql/[Accessing data with MySQL]

include::https://github.com/nuna-lamp/CrudWithVaadin/blob/master/README.md