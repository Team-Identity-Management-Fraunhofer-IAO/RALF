package com.neo4j.data;

import java.util.HashMap;

import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.transaction.Transaction;

import com.neo4j.data.binding.Neo4JSessionFactory;
import com.neo4j.data.objects.ObjectFactory;
import com.neo4j.data.objects.software.persistence.Component;
import com.neo4j.data.objects.software.persistence.SWStack;

@Deprecated
public class Neo4JConnector {

	public static void main(String[] args) {
		
		ObjectFactory factory = new ObjectFactory();
		Iterable<SWStack> stacks = factory.getAllSWStacks();
		factory.createNewComponent(208, 0, "hallo", "hallo", "hallo", "hallo", "hallo", "hallo", "hallo", "hallo", "hallo", "hallo", "hallo", "eq", true);
		for (SWStack stack : stacks){
			System.out.println(stack.getName()+" Id: "+stack.getId());
			Iterable<Component> compList = factory.getComponentsForSWStack(stack.getId());
			System.out.println(compList);
		}
		
		//String cypherQuery = "CREATE (n:test{test:'test'}) RETURN (n);";
        //Neo4JSessionFactory.getInstance().getNeo4jSession().query(cypherQuery, new HashMap<String, Object>());
		

	}

}
