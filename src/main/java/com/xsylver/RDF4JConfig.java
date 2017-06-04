package com.xsylver;

import org.eclipse.rdf4j.model.*;
import org.eclipse.rdf4j.model.impl.LinkedHashModel;
import org.eclipse.rdf4j.model.impl.SimpleValueFactory;
import org.eclipse.rdf4j.model.vocabulary.FOAF;
import org.eclipse.rdf4j.model.vocabulary.RDF;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RDF4JConfig {

    @Bean
    public Model bobModel() {
        Model model = new LinkedHashModel();
        ValueFactory factory = SimpleValueFactory.getInstance();
        IRI bob = factory.createIRI("http://example.org/bob");
        IRI name = factory.createIRI("http://example.org/name");
        Literal bobsName = factory.createLiteral("Bob");
        Statement nameStatement = factory.createStatement(bob, name, bobsName);
        Statement typeStatement = factory.createStatement(bob, RDF.TYPE, FOAF.PERSON);
        model.add(nameStatement);
        model.add(typeStatement);
        return model;
    }


}
