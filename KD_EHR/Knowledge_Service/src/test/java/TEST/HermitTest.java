package TEST;

import org.junit.Test;
import org.semanticweb.HermiT.Reasoner;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;

public class HermitTest {
	
	@Test
	public void hermitTest() throws Exception {
    	// First, we create an OWLOntologyManager object. The manager will load and save ontologies.
        OWLOntologyManager m=OWLManager.createOWLOntologyManager();
        // We use the OWL API to load the Pizza ontology.
        OWLOntology o=m.loadOntologyFromOntologyDocument(IRI.create("http://purl.obolibrary.org/obo/dron.owl"));
        // Now, we instantiate HermiT by creating an instance of the Reasoner class in the package org.semanticweb.HermiT.
        Reasoner hermit=new Reasoner(o);
        // Finally, we output whether the ontology is consistent.
        System.out.println(hermit.isConsistent());
	}

}
