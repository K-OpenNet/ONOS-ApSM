package org.onlab.security;

/**
 * Created by sdn on 16. 2. 13.
 */

import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.generic.ConstantPoolGen;
import org.apache.bcel.generic.ConstantPushInstruction;
import org.apache.bcel.generic.EmptyVisitor;
import org.apache.bcel.generic.INVOKEINTERFACE;
import org.apache.bcel.generic.INVOKESPECIAL;
import org.apache.bcel.generic.INVOKESTATIC;
import org.apache.bcel.generic.INVOKEVIRTUAL;
import org.apache.bcel.generic.Instruction;
import org.apache.bcel.generic.InstructionConstants;
import org.apache.bcel.generic.InstructionHandle;
import org.apache.bcel.generic.MethodGen;
import org.apache.bcel.generic.ReturnInstruction;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Simple method visitor to analyze what APIs are used from given ONOS application.
 * Class copied with modifications from java-callgraph: https://github.com/gousiosg/java-callgraph
 */
public class MethodVisitor extends EmptyVisitor {

    JavaClass visitedClass;
    private MethodGen methodGen;
    private ConstantPoolGen constantPoolGen;
    private PermissionComparer permissionComparer;
    public ArrayList<String> permSet;
    private String format; //

    public MethodVisitor(MethodGen m, JavaClass jc, ArrayList appPermSet) {
        permissionComparer = new PermissionComparer();
        visitedClass = jc;
        methodGen = m;
        constantPoolGen = methodGen.getConstantPool();
        permSet = appPermSet;
        format = "M:" + visitedClass.getClassName() + ":" + methodGen.getName()
                + " " + "(%s)%s:%s"; //
    }

    /**
     * Method to start exploration method of given application.
     */
    public void visit() {
        if (methodGen.isAbstract() || methodGen.isNative()) {
            return;
        }
        for (InstructionHandle ih = methodGen.getInstructionList().getStart();
             ih != null; ih = ih.getNext()) {
            Instruction i = ih.getInstruction();

            if (!visitInstruction(i)) {
                i.accept(this);
            }
        }
    }

    /**
     * Method to visit the instruction of given application.
     */
    private boolean visitInstruction(Instruction i) {
        short opcode = i.getOpcode();

        return ((InstructionConstants.INSTRUCTIONS[opcode] != null)
                && !(i instanceof ConstantPushInstruction)
                && !(i instanceof ReturnInstruction));
    }

    @Override
    public void visitINVOKEVIRTUAL(INVOKEVIRTUAL i) {
        try {
            File file = new File("/home/sdn/call.txt"); //
            FileWriter fw = new FileWriter(file, true); //
            fw.write(String.format(format,"M", i.getReferenceType(constantPoolGen), i.getMethodName(constantPoolGen))+"\n"); //
            fw.flush();//
            fw.close();//
        } catch (IOException e) {
            return;
        }

        permissionCheck(i.getReferenceType(constantPoolGen).toString(), i.getMethodName(constantPoolGen));
    }

    @Override
    public void visitINVOKEINTERFACE(INVOKEINTERFACE i) {
        try {
            File file = new File("/home/sdn/call.txt"); //
            FileWriter fw = new FileWriter(file, true); //
            fw.write(String.format(format,"M", i.getReferenceType(constantPoolGen), i.getMethodName(constantPoolGen))+"\n"); //
            fw.flush();//
            fw.close();//
        } catch (IOException e) {
            return;
        }
        permissionCheck(i.getReferenceType(constantPoolGen).toString(), i.getMethodName(constantPoolGen));
    }

    @Override
    public void visitINVOKESPECIAL(INVOKESPECIAL i) {
        try {
            File file = new File("/home/sdn/call.txt"); //
            FileWriter fw = new FileWriter(file, true); //
            fw.write(String.format(format,"M", i.getReferenceType(constantPoolGen), i.getMethodName(constantPoolGen))+"\n"); //
            fw.flush();//
            fw.close();//
        } catch (IOException e) {
            return;
        }
        permissionCheck(i.getReferenceType(constantPoolGen).toString(), i.getMethodName(constantPoolGen));
    }

    @Override
    public void visitINVOKESTATIC(INVOKESTATIC i) {
        try {
            File file = new File("/home/sdn/call.txt"); //
            FileWriter fw = new FileWriter(file, true); //
            fw.write(String.format(format,"M", i.getReferenceType(constantPoolGen), i.getMethodName(constantPoolGen))+"\n"); //
            fw.flush();//
            fw.close();//
        } catch (IOException e) {
            return;
        }
        permissionCheck(i.getReferenceType(constantPoolGen).toString(), i.getMethodName(constantPoolGen));
    }

    /**
     * Check the permission had been stored to the necessary permission list of given application already.
     * @param visitClass current invoked class name
     * @param visitMethod current invoked method name
     */
    public void permissionCheck(String visitClass, String visitMethod) {
        String key = permissionComparer.check(visitClass, visitMethod);
        if (!permSet.contains(key) && key != null) {
            permSet.add(key);
        }
    }
}
