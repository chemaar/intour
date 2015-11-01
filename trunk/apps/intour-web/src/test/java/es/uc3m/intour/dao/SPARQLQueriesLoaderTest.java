package es.uc3m.intour.dao;

import static org.junit.Assert.*;
import junit.framework.Assert;

import org.junit.Test;

public class SPARQLQueriesLoaderTest {

	@Test
	public void test() {
		Assert.assertEquals(1, SPARQLQueriesLoader.getSPARQLQueries().size());
		
	}

}
