import java.util.*;

import CPP.Absyn.*;

public class Compiler {
    // The output of the compiler is a list of strings.
    LinkedList<String> output;
    String className;
    Env env;

    // Compile C-- AST to a .j source file (returned as String).
    // name should be just the class name without file extension.
    public String compile(String name, CPP.Absyn.Program p) {
        // Initialize output
        output = new LinkedList();
        className = name;
        env = new Env();

        // Output boilerplate
        output.add(".class public " + name + "\n");
        output.add(".super java/lang/Object\n");
        output.add("\n");
        output.add(".method public <init>()V\n");
        output.add("  .limit locals 1\n");
        output.add("\n");
        output.add("  aload_0\n");
        output.add("  invokespecial java/lang/Object/<init>()V\n");
        output.add("  return\n");
        output.add("\n");
        output.add(".end method\n");
        output.add("\n");
        output.add(".method public static main([Ljava/lang/String;)V\n");
        output.add("  .limit locals 1\n");
        output.add("  .limit stack  1\n");
        output.add("\n");
        output.add("  invokestatic " + name + "/main()I\n");
        output.add("  pop\n");
        output.add("  return\n");
        output.add("\n");
        output.add(".end method\n");
        output.add("\n");

        // TODO: compile AST, appending to output.
        p.accept(new ProgramCompiler(), new Object());

        // Concatenate strings in output to .j file content.
        StringBuilder jtext = new StringBuilder();
        for (String s : output) {
            jtext.append(s + "\n");
        }
        return jtext.toString();
    }

    private class Env {
        private HashMap<String, String> sigs;
        private HashMap<String, String> classNames;
        private LinkedList<HashMap<String, Integer>> vars;
        private int varAddr;
        private int jumpAddr;
        private Type funType;

        public Env() {
            sigs = new HashMap<>();
            classNames = new HashMap<>();
            vars = new LinkedList<>();
            vars.add(new HashMap<>());
            varAddr = 0;
            jumpAddr = 0;
        }

        public String newLabel() {
            String s = "L" + jumpAddr;
            jumpAddr = jumpAddr + 1;
            return s;
        }

        public void enterFun(Type t) {
            funType = t;
        }

        public void exitFun() {
            funType = null;
        }

        public Type getReturnType() {
            return funType;
        }

        public void newBlock() {
            vars.addFirst(new HashMap<>());
        }

        public void exitBlock() {
            HashMap<String, Integer> removed = vars.removeFirst();
            for(String s : removed.keySet()) {
                varAddr = varAddr - 1;
            }
        }

        public void extendVariable(String id, Type type) {
            vars.getFirst().put(id, varAddr);
            varAddr = varAddr + 1;
        }

        public Integer lookupVariable(String id) {
            for(int i=0; i<vars.size(); i++) {
                if(vars.get(i).containsKey(id)) {
                    return vars.get(i).get(id);
                }
            }
            throw new TypeException("lookupVariable: did not find a variable with id " + id);
        }

        public void addFuncSignature(String id, String cn, String signature) {
            sigs.put(id, signature);
            classNames.put(id, cn);
        }

        public String getFuncSignature(String id) {
            return sigs.get(id);
        }

        public String getFuncClass(String id) {
            return classNames.get(id);
        }
    }

    private class ProgramCompiler implements Program.Visitor<Object, Object> {
        @Override
        public Object visit(PDefs p, Object arg) {
            for (Def d : p.listdef_) {
                d.accept(new FuncEvaluator(), arg);
            }
            for (Def d : p.listdef_) {
                d.accept(new FuncCompiler(), arg);
            }
            return null;
        }
    }

    private class FuncCompiler implements  Def.Visitor<Object, Object> {
        @Override
        public Object visit(DFun p, Object arg) {
            env.newBlock();
            env.enterFun(p.type_);
            output.add(".method public static " + env.getFuncSignature(p.id_));
            output.add(".limit locals 1000");
            output.add(".limit stack 1000");
            for(Arg a : p.listarg_) {
                a.accept(new ArgExtender(), arg);
            }
            for(Stm s : p.liststm_) {
                s.accept(new StmCompiler(), arg);
            }
            if(!output.getLast().endsWith("return"))
                output.add("return");
            output.add(".end method");
            env.exitFun();
            env.exitBlock();
            return null;
        }
    }

    private class ExpCompiler implements  Exp.Visitor<Object, Object> {
        @Override
        public Object visit(ETrue p, Object arg) {
            output.add("iconst_1");
            return null;
        }

        @Override
        public Object visit(EFalse p, Object arg) {
            output.add("iconst_0");
            return null;
        }

        @Override
        public Object visit(EInt p, Object arg) {
            if(p.integer_ < 6) {
                output.add("iconst_" + p.integer_);
            } else {
                output.add("ldc " + p.integer_);
            }
            return null;
        }

        @Override
        public Object visit(EDouble p, Object arg) {
            throw new TypeException("EDouble: not using doubles for this assignment");
        }

        @Override
        public Object visit(EId p, Object arg) {
            output.add(makeShortStatement("iload", env.lookupVariable(p.id_)));
            return null;
        }

        @Override
        public Object visit(EApp p, Object arg) {
            for(Exp e : p.listexp_) {
                e.accept(this, arg);
            }
            output.add("invokestatic " + env.getFuncClass(p.id_) + "/" + env.getFuncSignature(p.id_));
            return null;
        }

        @Override
        public Object visit(EPostIncr p, Object arg) {
            output.add(makeShortStatement("iload",env.lookupVariable(p.id_)));
            output.add("dup");
            output.add("iconst_1");
            output.add("iadd");
            output.add(makeShortStatement("istore", env.lookupVariable(p.id_)));
            return null;
        }

        @Override
        public Object visit(EPostDecr p, Object arg) {
            output.add(makeShortStatement("iload",env.lookupVariable(p.id_)));
            output.add("dup");
            output.add("iconst_1");
            output.add("isub");
            output.add(makeShortStatement("istore", env.lookupVariable(p.id_)));
            return null;
        }

        @Override
        public Object visit(EPreIncr p, Object arg) {
            output.add(makeShortStatement("iload",env.lookupVariable(p.id_)));
            output.add("iconst_1");
            output.add("iadd");
            output.add("dup");
            output.add(makeShortStatement("istore", env.lookupVariable(p.id_)));
            return null;
        }

        @Override
        public Object visit(EPreDecr p, Object arg) {
            output.add(makeShortStatement("iload",env.lookupVariable(p.id_)));
            output.add("iconst_1");
            output.add("isub");
            output.add("dup");
            output.add(makeShortStatement("istore", env.lookupVariable(p.id_)));
            return null;
        }

        @Override
        public Object visit(ETimes p, Object arg) {
            p.exp_1.accept(this, arg);
            p.exp_2.accept(this, arg);
            output.add("imul");
            return null;
        }

        @Override
        public Object visit(EDiv p, Object arg) {
            p.exp_1.accept(this, arg);
            p.exp_2.accept(this, arg);
            output.add("idiv");
            return null;
        }

        @Override
        public Object visit(EPlus p, Object arg) {
            p.exp_1.accept(this, arg);
            p.exp_2.accept(this, arg);
            output.add("iadd");
            return null;
        }

        @Override
        public Object visit(EMinus p, Object arg) {
            p.exp_1.accept(this, arg);
            p.exp_2.accept(this, arg);
            output.add("isub");
            return null;
        }

        @Override
        public Object visit(ELt p, Object arg) {
            String tru = env.newLabel();
            output.add("iconst_1");
            p.exp_1.accept(this, arg);
            p.exp_2.accept(this, arg);
            output.add("if_icmplt " + tru);
            output.add("pop");
            output.add("iconst_0");
            output.add(tru + ":");
            return null;
        }

        @Override
        public Object visit(EGt p, Object arg) {
            String tru = env.newLabel();
            output.add("iconst_1");
            p.exp_1.accept(this, arg);
            p.exp_2.accept(this, arg);
            output.add("if_icmpgt " + tru);
            output.add("pop");
            output.add("iconst_0");
            output.add(tru + ":");
            return null;
        }

        @Override
        public Object visit(ELtEq p, Object arg) {
            String tru = env.newLabel();
            output.add("iconst_1");
            p.exp_1.accept(this, arg);
            p.exp_2.accept(this, arg);
            output.add("if_icmple " + tru);
            output.add("pop");
            output.add("iconst_0");
            output.add(tru + ":");
            return null;
        }

        @Override
        public Object visit(EGtEq p, Object arg) {
            String tru = env.newLabel();
            output.add("iconst_1");
            p.exp_1.accept(this, arg);
            p.exp_2.accept(this, arg);
            output.add("if_icmpge " + tru);
            output.add("pop");
            output.add("iconst_0");
            output.add(tru + ":");
            return null;
        }

        @Override
        public Object visit(EEq p, Object arg) {
            String tru = env.newLabel();
            output.add("iconst_1");
            p.exp_1.accept(this, arg);
            p.exp_2.accept(this, arg);
            output.add("if_icmpeq " + tru);
            output.add("pop");
            output.add("iconst_0");
            output.add(tru + ":");
            return null;
        }

        @Override
        public Object visit(ENEq p, Object arg) {
            String tru = env.newLabel();
            output.add("iconst_1");
            p.exp_1.accept(this, arg);
            p.exp_2.accept(this, arg);
            output.add("if_icmpne " + tru);
            output.add("pop");
            output.add("iconst_0");
            output.add(tru + ":");
            return null;
        }

        @Override
        public Object visit(EAnd p, Object arg) {
            String fals = env.newLabel();
            output.add("iconst_0");
            p.exp_1.accept(this, arg);
            output.add("ifeq " + fals);
            p.exp_2.accept(this, arg);
            output.add("ifeq " + fals);
            output.add("pop");
            output.add("iconst_1");
            output.add(fals + ":");
            return null;
        }

        @Override
        public Object visit(EOr p, Object arg) {
            String tru = env.newLabel();
            output.add("iconst_1");
            p.exp_1.accept(this, arg);
            output.add("ifne " + tru);
            p.exp_2.accept(this, arg);
            output.add("ifne " + tru);
            output.add("pop");
            output.add("iconst_0");
            output.add(tru + ":");
            return null;
        }

        @Override
        public Object visit(EAss p, Object arg) {
            p.exp_.accept(this, arg);
            output.add("dup");
            output.add(makeShortStatement("istore", env.lookupVariable(p.id_)));
            return null;
        }
    }

    private class StmCompiler implements Stm.Visitor<Object, Object> {
        @Override
        public Object visit(SExp p, Object arg) {
            p.exp_.accept(new ExpCompiler(), arg);
            //TODO make this prettier with an annotating typeChecker
            if(!output.getLast().endsWith("V"))
                output.add("pop");
            output.add("\n");
            return null;
        }

        @Override
        public Object visit(SDecls p, Object arg) {
            for(String id : p.listid_) {
                env.extendVariable(id, p.type_);
            }
            output.add("\n");
            return null;
        }

        @Override
        public Object visit(SInit p, Object arg) {
            env.extendVariable(p.id_, p.type_);
            p.exp_.accept(new ExpCompiler(), arg);
            output.add(makeShortStatement("istore", env.lookupVariable(p.id_)));
            output.add("\n");
            return null;
        }

        @Override
        public Object visit(SReturn p, Object arg) {
            p.exp_.accept(new ExpCompiler(), arg);
            if(TypeChecker.typeCode(env.getReturnType()) == TypeChecker.TypeCode.CVoid) {
                output.add("return");
            } else {
                output.add("ireturn");
            }
            output.add("\n");
            return null;
        }

        @Override
        public Object visit(SWhile p, Object arg) {
            String test = env.newLabel();
            String end = env.newLabel();
            output.add(test + ":");
            p.exp_.accept(new ExpCompiler(), arg);
            output.add("ifeq " + end);
            env.newBlock();
            p.stm_.accept(this, arg);
            env.exitBlock();
            output.add("goto " + test);
            output.add(end + ":");
            
            return null;
        }

        @Override
        public Object visit(SBlock p, Object arg) {
            env.newBlock();
            for(Stm s : p.liststm_) {
                s.accept(this, arg);
            }
            output.add("\n");
            env.exitBlock();
            return null;
        }

        @Override
        public Object visit(SIfElse p, Object arg) {
            String tru = env.newLabel();
            String fals = env.newLabel();
            p.exp_.accept(new ExpCompiler(), arg);
            output.add("ifeq " + fals);
            env.newBlock();
            p.stm_1.accept(this, arg);
            env.exitBlock();
            output.add("goto " + tru);
            output.add(fals + ":");
            env.newBlock();
            p.stm_2.accept(this, arg);
            env.exitBlock();
            output.add(tru + ":");
            output.add("\n");
            return null;
        }
    }

    private class ArgExtender implements  Arg.Visitor<Object, Object> {
        @Override
        public Object visit(ADecl p, Object arg) {
            env.extendVariable(p.id_, p.type_);
            return null;
        }
    }

    private class FuncEvaluator implements Def.Visitor<Object, Object> {
        @Override
        public Object visit(DFun p, Object arg) {
            String args = "";
            for(Arg a : p.listarg_) {
                args = args + "I";
            }
            env.addFuncSignature(p.id_, className, p.id_ + "(" + args + ")" + getOneCharType(p.type_));

            //Adds the inbuilt functions
            env.addFuncSignature("printInt", "Runtime", "printInt(I)V");
            env.addFuncSignature("printDouble", "Runtime", "printDouble(D)V");
            env.addFuncSignature("readInt", "Runtime", "readInt()I");
            env.addFuncSignature("readDouble", "Runtime", "readDouble()D");
            return null;
        }
    }

    private String makeShortStatement(String func, Integer addr) {
        if(addr >= 0 && addr <= 3) {
            return func + "_" + addr;
        }
        return func + " " + addr;
    }

    private Object getOneCharType(Type t) {
        TypeChecker.TypeCode tt = TypeChecker.typeCode(t);
        if(tt == TypeChecker.TypeCode.CInt)
            return "I";
        if(tt == TypeChecker.TypeCode.CVoid)
            return "V";
        if(tt == TypeChecker.TypeCode.CBool)
            return "I";
        throw new TypeException("getOneCharType: Unknown type (" + tt + ") detected, doubles are not allowed");
    }
}
