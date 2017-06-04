package com.xsylver;

import org.eclipse.rdf4j.model.*;
import org.eclipse.rdf4j.model.impl.LinkedHashModel;
import org.eclipse.rdf4j.model.impl.SimpleValueFactory;
import org.eclipse.rdf4j.model.vocabulary.FOAF;
import org.eclipse.rdf4j.model.vocabulary.RDF;
import org.eclipse.rdf4j.model.vocabulary.RDFS;
import org.eclipse.rdf4j.query.QueryResults;
import org.eclipse.rdf4j.repository.Repository;
import org.eclipse.rdf4j.repository.RepositoryConnection;
import org.eclipse.rdf4j.repository.RepositoryResult;
import org.eclipse.rdf4j.repository.sail.SailRepository;
import org.eclipse.rdf4j.rio.RDFFormat;
import org.eclipse.rdf4j.rio.Rio;
import org.eclipse.rdf4j.sail.memory.MemoryStore;

/**
 * Created by vlasceanusilviu on 6/3/17.
 */
public class ValueFactoryTinker {

    public static void gaminAroundWithModels() {
        ValueFactory factory = SimpleValueFactory.getInstance();
        IRI bob = factory.createIRI("http://example.org/bob");
        IRI name = factory.createIRI("http://example.org/name");
        Literal bobsName = factory.createLiteral("Bob");
        Statement nameStatement = factory.createStatement(bob, name, bobsName);
        Statement typeStatement = factory.createStatement(bob, RDF.TYPE, FOAF.PERSON);

        // create a new Model to put statements in
        Model model = new LinkedHashModel();
        // add an RDF statement
        model.add(typeStatement);
        model.add(nameStatement);
        // add another RDF statement by simply providing subject, predicate, and object.
        model.add(bob, name, bobsName);

        model.forEach(System.out::println);
    }

    public static void repoExample() {
        Repository rep = new SailRepository(new MemoryStore());
        rep.initialize();
        String namespace = "http://example.org/";
        ValueFactory f = rep.getValueFactory();
        IRI john = f.createIRI(namespace, "john");
        RepositoryConnection conn = rep.getConnection();
        conn.add(john, RDF.TYPE, FOAF.PERSON);
        conn.add(john, RDFS.LABEL, f.createLiteral("John"));
        RepositoryResult<Statement> statements = conn.getStatements(null, null, null);
        Model model = QueryResults.asModel(statements);

        model.setNamespace(RDF.NS);
        model.setNamespace(RDFS.NS);
        model.setNamespace("foaf", FOAF.NAMESPACE);
        model.setNamespace("ex", namespace);

        Rio.write(model, System.out, RDFFormat.TURTLE);
    }

}
