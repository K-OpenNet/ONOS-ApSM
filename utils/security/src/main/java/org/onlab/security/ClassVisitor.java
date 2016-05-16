package org.onlab.security;

import org.apache.bcel.classfile.EmptyVisitor;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.Method;
import org.apache.bcel.classfile.ConstantPool;
import org.apache.bcel.classfile.Constant;
import org.apache.bcel.generic.ConstantPoolGen;
import org.apache.bcel.generic.MethodGen;

import java.util.ArrayList;

/**
 * Simple class visitor to invoke the method visitor class.
 * Class copied with modifications from java-callgraph: https://github.com/gousiosg/java-callgraph
 */
public class ClassVisitor extends EmptyVisitor {

    private JavaClass javaClass;
    private ConstantPoolGen constants;
    private ArrayList<String> appPermset;

    public ClassVisitor(JavaClass jc, ArrayList<String> store) {
        javaClass = jc;
        constants = new ConstantPoolGen(javaClass.getConstantPool());
        appPermset = store;
    }


    public void visitJavaClass(JavaClass jc) {
        //TODO: TBD
    }
    /**
     * Implemented method of interface org.apache.bcel.classfile.Visitor.
     */
    public void visitConstantPool(ConstantPool constantPool) {
        //TODO: TBD
    }
    /**
     * Implemented method of interface org.apache.bcel.classfile.Visitor.
     */
    public void visitMethod(Method method) {
        //TODO: TBD
    }

    /**
     * Method to start class exploration.
     */
    public void start() {
        //TODO: TBD
    }
}