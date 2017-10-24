import java.lang.reflect.Method;

public class Inspector {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	// Get the name of the class
	public String getClassName(Class classInput){
		Class reflectionClass = classInput.getClass();
		String className = reflectionClass.getName();
		return className;
	}
	
	// Get the name of the superclass
	public String getSuperclassName(Class superclassInput){
		Class reflectionSuperClass = superclassInput.getSuperclass();
		String superClassName = reflectionSuperClass.getName();
		return superClassName;
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
		Method[] classMethods = reflectionClass.getMethods();
		
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
			System.out.println("\tModifiers: " + methodModifiers);
		}
		return null;
	}
	
	// Get the constructors in class and their info
	public String getClassConstructors(){
		// Return the following:
			// Parameter Types
			// Modifiers
		return null;
	}
	
	// Get the fields in the class and their info
	public String getClassFields(){
		// Return the following:
			// Type
			// Modifiers
		return null; 
	}
	
	// Get the values of the fields within the class
	public String getClassFieldsValues(){
		// Return current value of each field
		return null;
	}
	
}
