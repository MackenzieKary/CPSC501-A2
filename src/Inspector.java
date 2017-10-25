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
	
	
	public void inspect(Object obj, boolean recursive){
		Class reflectionClass = obj.getClass();
		System.out.println("Reflection class: " + reflectionClass);
		
		System.out.println("inside inspector: " + obj + " (recursive = "+recursive+")");
		// Inspect current class
		getClassName(reflectionClass);
		getSuperclassName(reflectionClass, obj, recursive);
		//getInterfaceNames(reflectionClass);
		//getClassMethods(reflectionClass);
		//getClassFields(reflectionClass);
		//getClassFieldsValues(reflectionClass, obj, recursive);
		
		// Inspect recursively (if recursive set to true)
		if (recursive){
			
		}
		//recursedClasses.clear();
	}
	
	// Get the name of the class
	public void getClassName(Class reflectClass){
		String className = reflectClass.getName();
		System.out.println("Class Name: " + className);
	}
	
	// Get the name of the superclass
	public void getSuperclassName(Class reflectClass, Object obj, boolean recursive){
//		Class reflectionClassSuper = reflectClass.getSuperclass();
//		String superClassName = reflectionClassSuper.getName();
//		System.out.println("Superclass Name: " + superClassName);
//		System.out.println("Subscript: " + superClassName.substring(0,5));
//		String subSuper = superClassName.substring(0,5);
//		if(!recursedSuperClasses.contains(superClassName) && subSuper.equals("Class") ){
//			if(!reflectionClassSuper.getName().equals("java.lang.Object")){
//				recursedSuperClasses.add(superClassName);
//				System.out.println("---------Traversing superclass----------");
//				//Object object = reflectionClassSuper.newInstance(obj);
//				inspect(reflectionClassSuper, recursive);
//				//System.out.println("New superclass: " + reflectionClassSuper.getSuperclass().getSuperclass().getName());
//			}
//		}else{
//			
//		}
		Class reflectionClassSuper = reflectClass.getSuperclass();
		
		// Traverse through the hierarchy of superclasses 
		while (reflectionClassSuper != null) {
			System.out.println("Superclass: "+reflectionClassSuper.getName());
			System.out.println("----------- Traversing superclass: " + reflectionClassSuper.getName()+" ------------");
			getInterfaceNames(reflectionClassSuper);
			getClassConstructors(reflectionClassSuper);
			getClassMethods(reflectionClassSuper);
			getClassFields(reflectionClassSuper);
			getClassFieldsValues(reflectionClassSuper, obj, recursive);
			System.out.println("----------- End of traversal of superclass: " + reflectionClassSuper.getName()+" ------------");
			reflectionClassSuper = reflectionClassSuper.getSuperclass();
		  
		}
	}
	
	// Get the name of the interfaces
	public void getInterfaceNames(Class reflectClass){
		Class[] interfaces = reflectClass.getInterfaces();
		
		for(Class classInterface : interfaces){
			System.out.println("Interface Name: " + classInterface.getName());
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
		System.out.println("Inside get methods");
		Method[] classMethods = reflectClass.getDeclaredMethods();
		System.out.println("classMethods length = " + classMethods.length);
		for (Method classMethod : classMethods){
			classMethod.setAccessible(true);
			// Get method name 
			String methodName = classMethod.getName();
			System.out.println("Method name: " + methodName);
			
			// Get Method Exception Types 
			Class[] exceptions = classMethod.getExceptionTypes();
			for (Class exception : exceptions){
				String exceptionType = exception.getName();
				System.out.println("\tException type: " + exceptionType);
			}
			
			// Get Parameter Types
			Class[] parameters = classMethod.getParameterTypes();
			for (Class parameter : parameters){
				String parameterType = parameter.getName();
				System.out.println("\tParameter type: " + parameterType);
			}
			
			// Get Return Type
			Class returnType = classMethod.getReturnType();
			System.out.println("\tReturn Type: " + returnType);
			
			// Get Modifiers
			int methodModifiers = classMethod.getModifiers();
			System.out.println("\tModifiers: " + Modifier.toString(methodModifiers));
		}
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
	public void getClassFieldsValues(Class reflectClass, Object obj, boolean recursive){
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
				
				Class arrType = fieldType.getComponentType();
				System.out.println("\tArrType = "+arrType);
				
				if(!arrType.isPrimitive()){
					Object arrValues = null;
					try {
						arrValues = classField.get(obj);
						System.out.println("\tArray Reference value = " +arrValues);
						int length = Array.getLength(arrValues);
						System.out.println("\tLength: " + length +"\n");
						
						
						for (int i = 0; i < length; i++) {
							if (recursive){
								Object x = Array.get(arrValues, i);
								System.out.println("\tArray value: " + i + " " + x);
//								System.out.println("Array element value: " +arrValues);
//								  Class cls = Class.forName(arrType.getName());
//					                Object object = cls.newInstance();
//					                System.out.println("Object  = " + object);
								//inspect(arr, recursive);
							}else{
								// Print reference values & identity hashcode
								System.out.println("\tField Reference Value: "+ arrValues.getClass().getName());
								System.out.println("\tField Identity HashCode: "+ System.identityHashCode(arrValues));
							}
							System.out.println("        ________________ ");
						}			
					} catch (IllegalArgumentException | IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 
				}

			}else{
				// Not an array
				// Get Field Value

				Object fieldValue = null;
				try {
					System.out.println("Class field = " +classField);
					fieldValue = classField.get(obj);
					if(fieldValue != null){
						System.out.println("\tField value: " + fieldType);
						System.out.println("\tField value is prim?: " + fieldType.isPrimitive());
						if(!fieldType.isPrimitive()){
							// If not primitive, then object. So use recursion
							System.out.println("Classes looked at BEFORE: " + recursedClasses.toString());
							if(!recursedClasses.contains(fieldValue.getClass().getName().toString())){
								recursedClasses.add(fieldValue.getClass().getName().toString());
								System.out.println("Classes looked at AFTER ADD: " + recursedClasses.toString());
								System.out.println("\t\t>>> Doing Recursion on : " + fieldValue);
								if (recursive){
									inspect(fieldValue, recursive);
								}else{
									// No recursion, so only print 
									System.out.println("\tField Reference Value: "+ fieldValue.getClass().getName());
									System.out.println("\tField Identity HashCode: "+ System.identityHashCode(fieldValue));
									
								}
								
								//System.out.println("Do we ever get here? <><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><>");
								//recursedClasses.remove(fieldValue.getClass());
							}
						}else{
							System.out.println("\tField Value: " + fieldValue);
						}
					}else{
						System.out.println("\tField Value: Null");
					}
				} catch (IllegalArgumentException | IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				recursedClasses.clear();
				//System.out.println("\tField Value: " + fieldValue);
			}
		}	
	}
	
}
