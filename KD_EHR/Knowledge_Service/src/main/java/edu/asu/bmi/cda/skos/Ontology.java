package edu.asu.bmi.cda.skos;

import java.io.FileOutputStream;
import java.util.List;
import java.util.Set;

import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.apibinding.OWLManager;

/**
 * Code initially was done without implementations of methods. Methods to steamline tasks were added later on. 
 * @author amolbhalla
 *
 */
public class Ontology {

	OWLOntologyManager manager;
	OWLDataFactory factory;
	OWLOntology ontology;

	static String fileName = "/bmiVocabulary.owl";
	static String BMI591 = "http://edu.asu.bmi/cad591/bhalla/skos/ext#";
	static String name = "bmiVocabulary";
	static String prefix = "skos:";

	public static void main(String[] args) throws OWLOntologyCreationException,
			OWLException {

		IRI bmiIRI = IRI.create(BMI591 + name);
		String code = null;
		List<String> labels = null;
		List<IRI> parents = null;
		OWLOntology ontology = null;

		OWLOntologyManager manager = OWLManager.createOWLOntologyManager();

		ontology = manager.loadOntologyFromOntologyDocument(
		// the relative path to your file, where "/" corresponds to
		// src/main/resources
				manager.getClass().getResourceAsStream(fileName));

		OWLDataFactory factory = manager.getOWLDataFactory();
		manager.saveOntology(ontology, System.err);

		// instantiates OR looks up an individual with the given IRI
		OWLNamedIndividual ind = factory.getOWLNamedIndividual(bmiIRI);

		// instantiates a relationship between two individuals
		// Adding Pharmaceuticals is a subTypeOf broaderTransitive
		OWLObjectPropertyAssertionAxiom opa = factory
				.getOWLObjectPropertyAssertionAxiom(
						factory.getOWLObjectProperty(IRI.create(prefix
								+ "subTypeOf")), // the property
						factory.getOWLNamedIndividual(IRI.create(prefix
								+ "broaderTransative")), // the subject
						factory.getOWLNamedIndividual(IRI.create(prefix
								+ "Pharmaceuticals")) // the object
				);

		// Adding Acetic Acid is a subTypeOf Pharmaceuticals
		OWLObjectPropertyAssertionAxiom opa1 = factory
				.getOWLObjectPropertyAssertionAxiom(
						factory.getOWLObjectProperty(IRI.create(prefix
								+ "subTypeOf")), // the property
						factory.getOWLNamedIndividual(IRI.create(prefix
								+ "Pharmaceuticals")), // the subject
						factory.getOWLNamedIndividual(IRI.create(prefix
								+ "Acetic Acid")) // the object
				);

		// instantiates a data property (“attribute”) of an individual
		OWLLiteral value = factory.getOWLLiteral("Acetic Acid + Honey");
		OWLDataPropertyAssertionAxiom oda = factory
				.getOWLDataPropertyAssertionAxiom(
						factory.getOWLDataProperty(IRI.create(prefix
								+ "subTypeOf")),
						factory.getOWLNamedIndividual(IRI.create("Acetic Acid")),
						value);

		// Adding Alcohol Products is a subTypeOf Pharmaceuticals
		OWLObjectPropertyAssertionAxiom opa2 = factory
				.getOWLObjectPropertyAssertionAxiom(
						factory.getOWLObjectProperty(IRI.create(prefix
								+ "subTypeOf")), // the property
						factory.getOWLNamedIndividual(IRI.create(prefix
								+ "Pharmaceuticals")), // the subject
						factory.getOWLNamedIndividual(IRI.create(prefix
								+ "Alcohol Products")) // the object
				);

		// Adding Alkalizing Agent is a subTypeOf Pharmaceuticals
		OWLObjectPropertyAssertionAxiom opa3 = factory
				.getOWLObjectPropertyAssertionAxiom(
						factory.getOWLObjectProperty(IRI.create(prefix
								+ "subTypeOf")), // the property
						factory.getOWLNamedIndividual(IRI.create(prefix
								+ "Pharmaceuticals")), // the subject
						factory.getOWLNamedIndividual(IRI.create(prefix
								+ "Alkalizing Agent")) // the object
				);

		// Adding Alopecia Preperation is a subTypeOf Pharmaceuticals
		OWLObjectPropertyAssertionAxiom opa4 = factory
				.getOWLObjectPropertyAssertionAxiom(
						factory.getOWLObjectProperty(IRI.create(prefix
								+ "subTypeOf")), // the property
						factory.getOWLNamedIndividual(IRI.create(prefix
								+ "Pharmaceuticals")), // the subject
						factory.getOWLNamedIndividual(IRI.create(prefix
								+ "Alopecia Preperation")) // the object
				);

		// Adding Analgesic is a subTypeOf Pharmaceuticals
		OWLObjectPropertyAssertionAxiom opa5 = factory
				.getOWLObjectPropertyAssertionAxiom(
						factory.getOWLObjectProperty(IRI.create(prefix
								+ "subTypeOf")), // the property
						factory.getOWLNamedIndividual(IRI.create(prefix
								+ "Pharmaceuticals")), // the subject
						factory.getOWLNamedIndividual(IRI.create(prefix
								+ "Analgesic")) // the object
				);

		manager.addAxiom(ontology, factory.getOWLDeclarationAxiom(ind));
		manager.addAxiom(ontology, opa);

		manager.addAxiom(ontology, opa1);
		manager.addAxiom(ontology, oda);

		manager.addAxiom(ontology, opa2);
		manager.addAxiom(ontology, opa3);
		manager.addAxiom(ontology, opa4);
		manager.addAxiom(ontology, opa5);

		manager.saveOntology(ontology, System.err);

		ReasonerHelper helper = new ReasonerHelper();

		/*
		 * axiomTester.checkRelationship( descendant, “subTypeOf”, ancestor,
		 * false ); new ReasonerHelper().makeInferences( ontology );
		 * axiomTester.checkRelationship( descendant, “subTypeOf”, ancestor,
		 * true );
		 */
		OWLAxiom axiomTester;
		String predicate = "subTypeOf";
		//axiomTester.checkRelationship( opa, predicate, opa1, false ); 
		//axiomTester.checkRelationship( oda, predicate, opa1, true );
		helper.makeInferences(ontology);
		// helper.defaultAxiomGenerators();
	}

	public void printOntology(String outOwlFileName) throws Exception {
		manager.saveOntology(ontology, System.out);
		manager.saveOntology(ontology, new FileOutputStream(outOwlFileName));
	}
	
	//Task 1
	
	public OWLOntology getPopulatedOntology(String owlFileName)
			throws Exception {
		manager = OWLManager.createOWLOntologyManager();
		factory = manager.getOWLDataFactory();
		ontology = manager.loadOntologyFromOntologyDocument(this.getClass()
				.getResourceAsStream(owlFileName));
		return (ontology);
	}
	
	//Task 3
	
	public boolean checkRelationship(String subject, String predicate,
			String object) {
		OWLNamedIndividual sub = factory.getOWLNamedIndividual(IRI
				.create(subject));
		OWLNamedIndividual obj = factory.getOWLNamedIndividual(IRI
				.create(object));
		OWLObjectProperty prp = factory.getOWLObjectProperty(IRI
				.create(predicate));

		Set<OWLObjectPropertyAssertionAxiom> info = ontology
				.getObjectPropertyAssertionAxioms(sub);
		OWLObjectPropertyAssertionAxiom testX = factory
				.getOWLObjectPropertyAssertionAxiom(prp, sub, obj);

		return (info.contains(testX));
	}


	public void addCodedConcept(String id, String dproperty, String code)
			throws OWLOntologyStorageException, OWLOntologyCreationException {
		manager.addAxiom(ontology, factory.getOWLDeclarationAxiom(factory
				.getOWLNamedIndividual(IRI.create(id))));
		manager.addAxiom(ontology, factory.getOWLDataPropertyAssertionAxiom(
				factory.getOWLDataProperty(IRI.create(dproperty)),
				factory.getOWLNamedIndividual(IRI.create(id)), code));
	}

	public void addRelationship(String subject, String property, String object) {
		manager.addAxiom(ontology, factory.getOWLObjectPropertyAssertionAxiom(
				factory.getOWLObjectProperty(IRI.create(property)),
				factory.getOWLNamedIndividual(IRI.create(subject)),
				factory.getOWLNamedIndividual(IRI.create(object))));
	}
}
