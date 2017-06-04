package com.xsylver;

import org.eclipse.rdf4j.model.*;
import org.eclipse.rdf4j.model.impl.LinkedHashModel;
import org.eclipse.rdf4j.model.impl.SimpleValueFactory;
import org.eclipse.rdf4j.model.util.ModelBuilder;
import org.eclipse.rdf4j.model.util.Models;
import org.eclipse.rdf4j.model.util.RDFCollections;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by vlasceanusilviu on 6/3/17.
 */
public class ValueFactoryExample {

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

    public static Model modelBuilderExample() {
        ModelBuilder builder = new ModelBuilder();

        // set some namespaces
        builder.setNamespace("ex", "http://example.org/").setNamespace(FOAF.NS);

        builder.namedGraph("ex:graph1")      // add a new named graph to the model
                .subject("ex:john")        // add  several statements about resource ex:john
                .add(FOAF.NAME, "John")  // add the triple (ex:john, foaf:name "John") to the named graph
                .add(FOAF.AGE, 42)
                .add(FOAF.MBOX, "john@example.org");

        // add a triple to the default graph
        builder.defaultGraph().add("ex:graph1", RDF.TYPE, "ex:Graph");

        // return the Model object
        Model m = builder.build();
        return m;
    }

    public static Model toRDFCollectionsFromList() {
        String ns = "http://example.org/";
        ValueFactory vf = SimpleValueFactory.getInstance();
        // IRI for ex:favoriteLetters
        IRI favoriteLetters = vf.createIRI(ns, "favoriteLetters");
        // IRI for ex:John
        IRI john = vf.createIRI(ns, "John");
        // create a list of letters
        List<Literal> letters = Arrays.asList(vf.createLiteral("A"), vf.createLiteral("B"), vf.createLiteral("C"));
        // create a head resource for our list
        Resource head = vf.createBNode();
        // convert our list and add it to a newly-created Model
        Model aboutJohn = RDFCollections.asRDF(letters, head, new LinkedHashModel());
        // set the ex:favoriteLetters property to link to the head of the list
        aboutJohn.add(john, favoriteLetters, head);

        return aboutJohn;
    }

    public static List toListFromRDFCollections() {
        String ns = "http://example.org/";
        ValueFactory vf = SimpleValueFactory.getInstance();
        IRI favoriteLetters = vf.createIRI(ns, "favoriteLetters");
        // IRI for ex:John
        IRI john = vf.createIRI(ns, "John");

        Model aboutJohn = toRDFCollectionsFromList();
        // get the value of the ex:favoriteLetters property
        Resource node = Models.objectResource(aboutJohn.filter(john, favoriteLetters, null)).orElse(null);
        // Convert its collection back to an ArrayList of values
        if(node != null) {
            return RDFCollections.asValues(aboutJohn, node, new ArrayList<>());
        }
        return null;
    }

}
