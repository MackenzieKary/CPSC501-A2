import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Vector;

public class Inspector {
	/* TODO
	 * 
	 * setAccessible for private/protected/etc stuff
	 * 
	 */
	public void inspect(Object obj, boolean recursive){
		Vector objects = new Vector();
		Class reflectionClass = obj.getClass();
		
		// Inspect current class
		getClassName(reflectionClass);
		getSuperclassName(reflectionClass);
		getInterfaceNames(reflectionClass);
		getClassMethods(reflectionClass);
		getClassFields(reflectionClass);
		getClassFieldsValues(reflectionClass);
		
		// Inspect recursively (if recursive set to true)
		if (recursive){
			
		}
	}
	
	// Get the name of the class
	public void getClassName(Class reflectClass){
		String className = reflectClass.getName();
		System.out.println("Class Name: " + className);
	}
	
	// Get the name of the superclass
	public void getSuperclassName(Class reflectClass){
		Class reflectionClassSuper = reflectClass.getSuperclass();
		String superClassName = reflectionClassSuper.getName();
		System.out.println("Superclass Name: " + superClassName);
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
		Method[] classMethods = reflectClass.getDeclaredMethods();
		
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
	public String getClassConstructors(Class reflectClass){
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
		return null;
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
	public void getClassFieldsValues(Class reflectClass){
		// Print current value of each field

		// Get all fields (declared)
		Field[] classFields = reflectClass.getDeclaredFields();
		
		for (Field classField : classFields){
			classField.setAccessible(true);
			// Get Field Name
			String fieldName = classField.getName();
			System.out.println("Field Name: "+ fieldName);
			
			// Get Field Value
			Object fieldValue = null;
			try {
				fieldValue = classField.get(Class.forName(reflectClass.getName()).newInstance());
			} catch (IllegalArgumentException | IllegalAccessException | InstantiationException
					| ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("\tField Value: " + fieldValue);;
		}	
	}
	
}
