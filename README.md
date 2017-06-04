======================
RDF4J Application Demo
======================

Schelet de aplicatie backend + frontend.
Pentru extindere, se vor face urmatoarele:
- se vor adauga bean-uri in RDF4JConfig.java (vor fi disponibile la startul aplicatiei)
  - macar un bean va fi ptr un repository (recomandat sa persiste informatii pe disc)
- se vor adauga controllere in pachetul 'controller' (se urmeaza template-ul definit in RestController.java)
- se va adauga structura de frontend in locatia src/main/resources/static/
  - in momentul de fata, avem index.html, accesibil la localhost:8080 sau localhost:8080/index.html
  - este recomandat sa se creeze un Single Page Application, prin folosirea Angular
- se vor adauga proprietati specifice Spring in src/main/resources/application.properties (optional)

Pentru a invata API-ul de RDF4J, se vor urma instructiunile de la http://docs.rdf4j.org/programming/
Pentru introducere in metodologia RDF4J, se va citi http://docs.rdf4j.org/rdf-tutorial/