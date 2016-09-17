//package edu.utsa.tl15;

import java.util.*;

public class astNode {
//int NodeId;
//int parentId;
	//int intval;
	//boolean boolval;
	String type ;
	int nbrChild=0;
	astNode parent;
	List<astNode> child=new ArrayList<astNode>();
	String name;
	boolean valid;
	String ilocInstruction="";
	String register="not declared";
	//--------mips--------------------
	String mipsRegister="";
	String mipsInstruction="";
	//----------------------------------
	public astNode(String Name){
		valid=true;
		
		name=Name;
	}
	public boolean isNull(astNode node) {
		if(node==null)
			return true;
		else return false;
	}
	public void setChild(astNode node) {
		child.add(node);
		nbrChild++;
	}
	
	public void setName(String n) {
		name = n;
	}
	public int getNumberChild() {
		return nbrChild;
	}
	/*public void setIntVal(String variable){
		intval=Integer.parseInt(variable);
	}
	public void setBoolVal(String variable){
		boolval=Boolean.parseBoolean(variable);
	}*/
	public void setToIntType(){
		type="int";
	}
	public void setToBoolType(){
		type="bool";
	}
	public void setTName(String Name){
		name=Name;
	}
	public void setParent(astNode Parent){
		parent=Parent;
	}
	/*public astNode getParent(astNode Node){
		if (Node.parent!= null){System.out.println(Node.name+" does not have a parent");return null;}
		else return Node.parent;
		
	}*/
	/*public void setNodeId(int Id ){
		NodeId=Id;
	}
	public int getNodeId(astNode node ){
		return node.NodeId;
	}*/
	/*public int getParentId(astNode node ){
		return node.parentId;
	}
	public void setParentId(int Id ){
		parentId=Id;
	}*/
	public void setValid( boolean B, astNode node){
		node.valid=B;
		//if (node.parent!=null){node.parent.valid=B;}
		}
	public void setIlocInstruction(String instruction,String Operands,String arrow,String Register)
	{
		ilocInstruction="<tr><td align=\"left\">"+instruction+"</td><td align=\"left\">"+Operands+"</td><td align=\"left\">"+arrow+Register+"</td></tr>";
		}
}
