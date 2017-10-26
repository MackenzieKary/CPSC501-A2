import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Vector;
import java.lang.reflect.Array;

public class Inspector {
	public Inspector() { }
	/* TODO
	 * 
	 * setAccessible for private/protected/etc stuff
	 * 
	 */
	public static ArrayList<String> recursedClasses = new ArrayList<String>();
	public static ArrayList<String> recursedSuperClasses = new ArrayList<String>();
	
	
	public void inspect(Object obj, boolean recursive) throws ClassNotFoundException, InstantiationException, IllegalAccessException{
		
		// Check if object passed in is an object
		// NOTE: There is an array of type ClassA or whatever, but they are NOT instantiated, so they should be null, no?
//		if (obj.getClass().isArray()){
//			System.out.println("Is an array");
//			int length = Array.getLength(obj);
//			System.out.println("Array length:" + length);
//		    for (int i = 0; i < length; i ++) {
//		    	System.out.println("Going into first array element");
//		        Object arrayElement = Array.get(obj, i);
//		        System.out.println("Array element = " +arrayElement.toString());
//		        inspect(arrayElement, recursive);
//		    }
		if (obj.getClass().isArray()){
			System.out.println("Array = "+ obj.getClass());
			int length = Array.getLength(obj);
		    for (int i = 0; i < 1; i ++) {		// Change back to i < length later

		    	System.out.println("PRINTING i: " + i);
		    	Class componentType = obj.getClass().getComponentType();
		    	System.out.println("Component type: " + componentType);
		    	
		    	String test =  componentType.getName();
		    	String lastWord = test.substring(test.lastIndexOf(" ")+1);
		    	System.out.println("Last word = " + lastWord);
//		    	String finalWord = lastWord.substring(2, lastWord.length());
//		    	System.out.println("Final word = " + finalWord);
//		    	String finalWorddef = finalWord.substring(0, lastWord.length()-1);
//		    	System.out.println("FINAL word = " + finalWorddef);
		    	//Class clazz = Class.forName("[L" + lastWord + ";");
		    	System.out.println("[L"+lastWord);
		    	Class clazz = Class.forName(lastWord);
		    	//ClassLoader classLoader = componentType.getClassLoader();
		    	System.out.println("clazz = " + clazz.getClass());
		    	
		    	
		    	
				System.out.println("Object = " + clazz);
		    	System.out.println("Obj here= " + obj);
		    	getClassName(clazz);
				getSuperclassName(clazz, obj, recursive);
				getInterfaceNames(clazz, obj, recursive);
				getClassMethods(clazz);
				getClassFields(clazz);
				getClassFieldsValues(clazz, obj, recursive);

		    }
		}else{
			System.out.println("Obj = " + obj);
			Class reflectionClass = obj.getClass();
			System.out.println("Reflection class: " + reflectionClass);
			
			System.out.println("inside inspector: " + obj + " (recursive = "+recursive+")");
			// Inspect current class
			getClassName(reflectionClass);
			getSuperclassName(reflectionClass, obj, recursive);
			getInterfaceNames(reflectionClass, obj, recursive);
			getClassMethods(reflectionClass);
			getClassFields(reflectionClass);
			getClassFieldsValues(reflectionClass, obj, recursive);
		}
	}
	
	// Get the name of the class
	public void getClassName(Class reflectClass){
		String className = reflectClass.getName();
		System.out.println("Class Name: " + className);
	}
	
	// Get the name of the superclass
	public void getSuperclassName(Class reflectClass, Object obj, boolean recursive) throws ClassNotFoundException, InstantiationException, IllegalAccessException{
		Class reflectionClassSuper = reflectClass.getSuperclass();
		
		// Traverse through the hierarchy of superclasses 
		while (reflectionClassSuper != null) {
			System.out.println("Superclass: "+reflectionClassSuper.getName());
			System.out.println("----------- Traversing superclass: " + reflectionClassSuper.getName()+" ------------");
			getInterfaceNames(reflectionClassSuper, obj, recursive);
			getClassConstructors(reflectionClassSuper);
			getClassMethods(reflectionClassSuper);
			getClassFields(reflectionClassSuper);
			getClassFieldsValues(reflectionClassSuper, obj, recursive);
			System.out.println("----------- End of traversal of superclass: " + reflectionClassSuper.getName()+" ------------");
			reflectionClassSuper = reflectionClassSuper.getSuperclass();		  
		}
	}
	
	// Get the name of the interfaces
	public void getInterfaceNames(Class reflectClass, Object obj, boolean recursive) throws ClassNotFoundException, InstantiationException, IllegalAccessException{
		Class[] interfaces = reflectClass.getInterfaces();	
		
		// Traverse through the hierarchy of interfaces
		for(Class classInterface : interfaces){
			System.out.println("Interface Name: " + classInterface.getName());
			System.out.println("----------- Traversing Interface: " + classInterface.getName()+" ------------");
			getInterfaceNames(classInterface, obj, recursive);
			getClassConstructors(classInterface);
			getClassMethods(classInterface);
			getClassFields(classInterface);
			getClassFieldsValues(classInterface, obj, recursive);
			System.out.println("----------- End of traversal of superclass: " + classInterface.getName()+" ------------");
		}
	}
	
	// Get the methods in class and their info
	public void getClassMethods(Class reflectClass){
		// Return the following:
			// Name
			// Exceptions thrown
			// Parameter Type
			// Return Type
			// Modifiers
		
		Method[] classMethods = reflectClass.getDeclaredMethods();
		System.out.println("----Methods below for class : "+ reflectClass.getName() +", number of total methods= "+classMethods.length);
		for (Method classMethod : classMethods){
			classMethod.setAccessible(true);
			// Get method name 
			String methodName = classMethod.getName();
			System.out.println("\tMethod name: " + methodName);
			
			// Get Method Exception Types 
			Class[] exceptions = classMethod.getExceptionTypes();
			for (Class exception : exceptions){
				String exceptionType = exception.getName();
				System.out.println("\t\tException type: " + exceptionType);
			}
			
			// Get Parameter Types
			Class[] parameters = classMethod.getParameterTypes();
			for (Class parameter : parameters){
				String parameterType = parameter.getName();
				System.out.println("\t\tParameter type: " + parameterType);
			}
			
			// Get Return Type
			Class returnType = classMethod.getReturnType();
			System.out.println("\t\tReturn Type: " + returnType);
			
			// Get Modifiers
			int methodModifiers = classMethod.getModifiers();
			System.out.println("\t\tModifiers: " + Modifier.toString(methodModifiers));
		}
		System.out.println("----Done looking at methods for class : "+ reflectClass.getName());
	}
	
	// Get the constructors in class and their info
	public void getClassConstructors(Class reflectClass){
		// Print the following:
			// Parameter Types
			// Modifiers
		
		// Get all constructors 
		Constructor[] classConstructors = reflectClass.getDeclaredConstructors();
		
		for (Constructor classConstructor : classConstructors){
			// Get Constructor Name
			String constructorName = classConstructor.getName();
			System.out.println("Constructor Name: "+ constructorName);
			
			// Get Parameter Types
			Class[] parameters = classConstructor.getParameterTypes();
			for (Class parameter : parameters){
				String parameterType = parameter.getName();
				System.out.println("\tParameter type: " + parameterType);
			}
			
			// Get Modifiers
			int methodModifiers = classConstructor.getModifiers();
			System.out.println("\tModifiers: " + Modifier.toString(methodModifiers));
		}
	}
	
	// Get the fields in the class and their info
	public void getClassFields(Class reflectClass){
		// Print the following:
			// Type
			// Modifiers

		// Get all fields (declared)
		Field[] classFields = reflectClass.getDeclaredFields();
		
		for (Field classField : classFields){
			classField.setAccessible(true);
			// Get Field Name
			String fieldName = classField.getName();
			System.out.println("Field Name: "+ fieldName);
			
			// Get Field Type
			Class fieldType = classField.getType();
			System.out.println("\tField Type: " + fieldType);
			
			// Get Field Modifiers
			int fieldModifiers = classField.getModifiers();
			System.out.println("\tField Modifiers: "+ Modifier.toString(fieldModifiers));
		}	 
	}
	
	// Get the values of the fields within the class
	public void getClassFieldsValues(Class reflectClass, Object obj, boolean recursive) throws ClassNotFoundException, InstantiationException, IllegalAccessException{
		// Print current value of each field

		// Get all fields (declared)
		Field[] classFields = reflectClass.getDeclaredFields();
		
		for (Field classField : classFields){
			classField.setAccessible(true);
			// Get Field Name
			String fieldName = classField.getName();
			System.out.println("Field Name: "+ fieldName);
			
			// Check if field is an array
			Class fieldType = classField.getType();
			if (fieldType.isArray()){
				// if it is an array, we need to figure out the type of array
				Class arrType = fieldType.getComponentType();
				System.out.println("\tArrType = "+arrType);
				
				if(!arrType.isPrimitive()){
					// If array is not primitive, then it is objects. (Likely calling another class)
					Object arrValues = null;
					if (obj.getClass().isArray()){
						int length = Array.getLength(obj);
					    for (int i = 0; i < 1; i ++) {		// Change back to i < length later

					    	
					    	Class componentType = obj.getClass().getComponentType();
			
					    	
					    	String test =  componentType.getName();
					    	String lastWord = test.substring(test.lastIndexOf(" ")+1);
				

					    	Class clazz = Class.forName(lastWord);
					    	obj = clazz.newInstance();

					    }
					}
					try {
						System.out.println("####################### OBJECT = " + obj);
						arrValues = classField.get(obj);
						System.out.println("\tArray Reference value = " +arrValues);
						int length = Array.getLength(arrValues);
						System.out.println("\tLength: " + length +"\n");
						
						
						for (int i = 0; i < length; i++) {
							if (recursive){
								Object x = Array.get(arrValues, i);
								if (x == null){
									// Null when an array of objects has been created, but not yet instantiated 
									System.out.println("\tArray value for index " + i + " = " + x);
								}else{
									Class cls = Class.forName(arrType.getName());
					                Object object = cls.newInstance();
					                System.out.println("Object  = " + object);
					                inspect(object, recursive);
								}
							}else{
								// Print reference values & identity hashcode
								System.out.println("\tField Reference Value: "+ arrValues.getClass().getName());
								System.out.println("\tField Identity HashCode: "+ System.identityHashCode(arrValues));
							}
							System.out.println("        ________________ ");
						}			
					} catch (IllegalArgumentException | IllegalAccessException | ClassNotFoundException | InstantiationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 
				}

			}else{
				// Not an array
				// Get Field Value

				Object fieldValue = null;
				try {} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				recursedClasses.clear();
				//System.out.println("\tField Value: " + fieldValue);
			}
		}	
	}
	
}
