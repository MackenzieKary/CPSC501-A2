import static org.junit.Assert.*;

import java.lang.reflect.Method;

import org.junit.Before;
import org.junit.Test;

public class InspectorTest {
	Method inspectionMethod = null;
	Method getClassNameMethod = null;
	Method getFieldValues = null;
	Object ObjInspector = null;
	Class objInspectClass = null;
	
	
	@Before
	public void setUp() throws Exception {
		try{
			objInspectClass = Class.forName("Inspector");
			ObjInspector = objInspectClass.newInstance();
	    }catch(Exception e) {
	    	throw new Exception("Unable create instance of your object inspector");
	    }
		try{
			Class[] param = { Object.class, boolean.class };
			inspectionMethod = objInspectClass.getDeclaredMethod("inspect",param);
			getClassNameMethod = objInspectClass.getDeclaredMethod("getClassName",null);
			getFieldValues = objInspectClass.getDeclaredMethod("getFieldValues",null);
	    }catch(Exception e) {
	    	throw new Exception("Unable to find required method: public void inspect(Object obj,boolean recursive)");
	    }
	}

	
	@Test
	public void testGetClassNameClassA() throws Exception{
		Object[] param = { new ClassA(), new Boolean(true) };
		inspectionMethod.invoke(ObjInspector, param);
		
		String className = (String) getClassNameMethod.invoke(ObjInspector);
		
		assertEquals("ClassA", className);
	}
	@Test
	public void testGetClassNameClassTest() throws Exception{
		Object[] param = { new ClassTest(), new Boolean(true) };
		inspectionMethod.invoke(ObjInspector, param);
		
		String className = (String) getClassNameMethod.invoke(ObjInspector);
		
		assertEquals("ClassTest", className);
	}
	@Test
	public void testGetFieldValuesClassA() throws Exception{
		Object[] param = { new ClassA(), new Boolean(true) };
		inspectionMethod.invoke(ObjInspector, param);
		
		String fieldValues = (String) getFieldValues.invoke(ObjInspector);
		
		assertEquals(" 3 0.2 true", fieldValues);
	}
	@Test
	public void testGetFieldValuesClassTest() throws Exception{
		Object[] param = { new ClassTest(), new Boolean(true) };
		inspectionMethod.invoke(ObjInspector, param);
		
		String fieldValues = (String) getFieldValues.invoke(ObjInspector);
		
		assertEquals(" 42 false", fieldValues);
	}

}
