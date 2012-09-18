package visitor;
import java.util.ArrayList;
import java.util.Hashtable;

public abstract class TableContents {
	public abstract TableContents find(String s);
}

class GoalTable extends TableContents{
	
	String mainClass;
	Hashtable<String, ClassTable> classes = new Hashtable<String, ClassTable>(50);
    public TableContents find(String s){
    	return classes.get(s);
    }
}

class ClassTable extends TableContents{
	
	boolean isMain = false;	//Is this a main class?
	boolean isExtends = false;
	String className; 			//Name of the class
	Hashtable<String, VariableTable> variables = new Hashtable<String, VariableTable>();
	Hashtable<String, FunctionTable> functions = new Hashtable<String,FunctionTable>();
	TableContents previousPointer;	//Pointer to the previous table (stack?)
	String extendsClassName;
	public TableContents find(String s){
		VariableTable _variable = variables.get(s);
		if(_variable != null) return _variable;
		else {
			FunctionTable _func = functions.get(s);
			if(_func != null) return _func;
			else {
				return previousPointer.find(s);
			}
		}
	}
}


class VariableTable extends TableContents{
	boolean isFormal = false;
	boolean isInstance = false;
	String type;
	int position;
	TableContents previousPointer;
	public TableContents find(String s){
		//return null;
		return previousPointer.find(s);
	}
	
}

class FunctionTable extends TableContents{

	Hashtable<String, VariableTable> parameters = new Hashtable<String, VariableTable>();
	ArrayList<VariableTable> orderedParameters = new ArrayList<VariableTable>();
	Hashtable<String, VariableTable> localVars = new Hashtable<String, VariableTable>();
	String returnType;					//Function's return type
	TableContents previousPointer;		//Pointer to the previous scope
	boolean isMain = false;				//Is the function inside main?
	public TableContents find(String s){
		TableContents t = localVars.get(s);
		if(t != null) return t;
		else {
			t = parameters.get(s);
			if(t != null) return t;
			else return previousPointer.find(s);
		}
	}
	
}