import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class Inspector {
	/* TODO
	 * 
	 * setAccessible for private/protected/etc stuff
	 * 
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	// Get the name of the class
	public String getClassName(Class classInput){
		Class reflectionClass = classInput.getClass();
		String className = reflectionClass.getName();
		System.out.println("Class Name: " + className);
		return className;
	}
	
	// Get the name of the superclass
	public String getSuperclassName(Class classInput){
		Class reflectionClass = classInput.getSuperclass();
		String superClassName = reflectionClass.getName();
		System.out.println("Superclass Name: " + superClassName);
		return null;
	}
	
	// Get the name of the interfaces
	public String getInterfaceNames(Class classInput){
		Class reflectionClass = classInput.getClass();
		Class[] interfaces = reflectionClass.getInterfaces();
		
		for(Class classInterface : interfaces){
			System.out.println("Interface Name: " + classInterface.getName());
		}
		return null;
	}
	
	// Get the methods in class and their info
	public String getClassMethods(Class classInput){
		// Return the following:
			// Name
			// Exceptions thrown
			// Parameter Type
			// Return Type
			// Modifiers
		Class reflectionClass = classInput.getClass();
		Method[] classMethods = reflectionClass.getDeclaredMethods();
		
		for (Method classMethod : classMethods){
			
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
		return null;
	}
	
	// Get the constructors in class and their info
	public String getClassConstructors(Class classInput){
		// Return the following:
			// Parameter Types
			// Modifiers
		
		Class reflectionClass = classInput.getClass();
		// Get all constructors 
		Constructor[] classConstructors = reflectionClass.getDeclaredConstructors();
		
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
	public String getClassFields(Class classInput){
		// Return the following:
			// Type
			// Modifiers
		Class reflectionClass = classInput.getClass();
		// Get all fields (declared)
		Field[] classFields = reflectionClass.getDeclaredFields();
		
		for (Field classField : classFields){
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
		return null; 
	}
	
	// Get the values of the fields within the class
	public String getClassFieldsValues(Class classInput){
		// Return current value of each field
		Class reflectionClass = classInput.getClass();
		// Get all fields (declared)
		Field[] classFields = reflectionClass.getDeclaredFields();
		
		for (Field classField : classFields){
			// Get Field Name
			String fieldName = classField.getName();
			System.out.println("Field Name: "+ fieldName);
			
			// Get Field Value
			classField.setAccessible(true);
			Object fieldValue = null;
			try {
				fieldValue = classField.get(Class.forName(classInput.getName()).newInstance());
			} catch (IllegalArgumentException | IllegalAccessException | InstantiationException
					| ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("\tField Value: " + fieldValue);;
		}	
		return null;
	}
	
}
