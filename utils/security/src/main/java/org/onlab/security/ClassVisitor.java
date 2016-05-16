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

    /**
     * Implemented method of interface org.apache.bcel.classfile.Visitor.
     */
    public void visitJavaClass(JavaClass jc) {
        jc.getConstantPool().accept(this);
        Method[] methods = jc.getMethods();
        for (int i = 0; i < methods.length; i++) {
            methods[i].accept(this);
        }
    }
    /**
     * Implemented method of interface org.apache.bcel.classfile.Visitor.
     */
    public void visitConstantPool(ConstantPool constantPool) {
        for (int i = 0; i < constantPool.getLength(); i++) {
            Constant constant = constantPool.getConstant(i);
            if (constant == null) {
                continue;
            }
        }
    }
    /**
     * Implemented method of interface org.apache.bcel.classfile.Visitor.
     */
    public void visitMethod(Method method) {
        MethodGen mg = new MethodGen(method, javaClass.getClassName(), constants);
        MethodVisitor visitor = new MethodVisitor(mg, javaClass, appPermset);
        visitor.visit();
    }

    /**
     * Method to start class exploration.
     */
    public void start() {
        visitJavaClass(javaClass);
    }
}