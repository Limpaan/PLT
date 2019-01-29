import CPP.Absyn.*;

import java.util.HashMap;
import java.util.LinkedList;

public class TypeChecker {

    //Declare some Type variables to use later
    Type TBool = new Type_bool();
    Type TDouble = new Type_double();
    Type TInt = new Type_int();
    Type TVoid = new Type_void();

    public void typecheck(Program p) {
        Env e = new Env();

        System.out.println(p);

        p.accept(new CheckProgram(), e);
    }


    public Type inferExp(Exp e, Env env) {
        return e.accept(new CheckExp(), env);
    }

    public ArgType inferArg(Arg a, Env env) {
        return a.accept(new CheckArg(), env);
    }

    public void inferStm(Stm s, Env env) {
        s.accept(new CheckStm(), env);
    }

    private TypeCode typeCode(Type ty) {
        if(ty instanceof Type_bool) {
            return TypeCode.CBool;
        }
        if(ty instanceof  Type_double) {
            return TypeCode.CDouble;
        }
        if(ty instanceof  Type_int) {
            return TypeCode.CInt;
        }
        if(ty instanceof  Type_void) {
            return TypeCode.CVoid;
        }
        throw new TypeException("typeCode: found an unknown type: " + ty);
    }

    private class CheckProgram implements Program.Visitor<Env, Env> {

        @Override
        public Env visit(PDefs p, Env env) {
            for(Def d: p.listdef_) {
                d.accept(new BuildSym(), env);
            }
            //Add the built-in functions
            addBuiltInFunctions(env);
            //Check for a 'int main()' function
            if(!(typeCode(env.lookupFun("main").val) == TypeCode.CInt))
                throw new TypeException("Function main must have 'int' as return type");
            if(!(env.lookupFun("main").args.size() == 0))
                throw new TypeException("Function main must have exactly 0 arguments");
            for(Def d: p.listdef_) {
                d.accept(new CheckDef(), env);
            }
            return env;
        }

        public void addBuiltInFunctions(Env env) {
            FunType printIntType = new FunType();
            FunType printDoubleType = new FunType();
            FunType readInt = new FunType();
            FunType readDouble = new FunType();
            LinkedList<Type> tmp = new LinkedList<Type>();

            printIntType.val = TVoid;
            tmp = new LinkedList<>();
            tmp.add(TInt);
            printIntType.args = tmp;
            env.addFunction("printInt", printIntType);

            printDoubleType.val = TVoid;
            tmp = new LinkedList<>();
            tmp.add(TDouble);
            printDoubleType.args = tmp;
            env.addFunction("printDouble", printDoubleType);

            readInt.val = TInt;
            tmp = new LinkedList<Type>();
            readInt.args = tmp;
            env.addFunction("readInt", readInt);

            readDouble.val = TDouble;
            tmp = new LinkedList<Type>();
            readDouble.args = tmp;
            env.addFunction("readDouble", readDouble);
        }
    }

    private class BuildSym implements Def.Visitor<Env, Env> {
        @Override
        public Env visit(DFun p, Env env) {
            FunType f = new FunType();
            f.args = new LinkedList<Type>();
            for(Arg a: p.listarg_)
                f.args.addLast(inferArg(a, env).ty);
            f.val = p.type_;
            env.addFunction(p.id_, f);

            return env;
        }
    }

    private class CheckDef implements Def.Visitor<Env, Env> {
        @Override
        public Env visit(DFun p, Env env) {
            env.addFunctionContext(p.id_);
            //Add arguments to context, also checks if they have the same id
            for(Arg a: p.listarg_) {
                ArgType at = a.accept(new CheckArg(), env);
                env.updateVar(at.id, at.ty);
            }
            for(Stm s: p.liststm_) {
                inferStm(s, env);
            }
            env.removeFunctionContext();
            return null;
        }
    }

    private class CheckArg implements Arg.Visitor<ArgType, Env> {

        @Override
        public ArgType visit(ADecl p, Env arg) {
            if(typeCode(p.type_) == TypeCode.CVoid)
                throw new TypeException("ADecl: argument type cannot be void");
            ArgType at = new ArgType();
            at.id = p.id_;
            at.ty = p.type_;

            return at;
        }
    }

    private class CheckStm implements Stm.Visitor<Env,Env> {

        @Override
        public Env visit(SBlock p, Env env) {
            env.addContext();
            for(Stm s: p.liststm_)
                s.accept(this, env);
            env.removeContext();
            return env;
        }

        @Override
        public Env visit(SDecls p, Env env) {
            for(String s:p.listid_) {
                if(typeCode(p.type_) == TypeCode.CVoid)
                    throw new TypeException("SDecls: Can't declare variable of type void");
                env.updateVar(s, p.type_);
            }
            return env;
        }

        @Override
        public Env visit(SExp p, Env env) {
            inferExp(p.exp_, env);
            return env;
        }

        @Override
        public Env visit(SIfElse p, Env env) {
            if(typeCode(inferExp(p.exp_, env)) != TypeCode.CBool)
                throw new TypeException("SIfElse: expression " + p.exp_ + " is not of form boolean");
            env.addContext();
            p.stm_1.accept(this, env);
            env.removeContext();
            env.addContext();
            p.stm_2.accept(this, env);
            env.removeContext();
            return env;
        }

        @Override
        public Env visit(SInit p, Env env) {
            if(typeCode(p.type_) == TypeCode.CVoid)
                throw new TypeException("SInit: Can't declare variable of type void");
            if(typeCode(inferExp(p.exp_, env)) != typeCode(p.type_))
                throw new TypeException("SInit: expression " + p.exp_ + " does not match type + " + p.type_);
            env.updateVar(p.id_, p.type_);
            return env;
        }

        @Override
        public Env visit(SReturn p, Env env) {
            if(typeCode(env.lookupFun(env.currentFun).val) != typeCode(inferExp(p.exp_, env)))
                throw new TypeException("SReturn: expression " + p.exp_ + " does not match return type " + env.lookupFun(env.currentFun).val);
            return env;
        }

        @Override
        public Env visit(SWhile p, Env env) {
            if(typeCode(inferExp(p.exp_, env)) != TypeCode.CBool)
                throw new TypeException("SWhile: expression " + p.exp_ + " is not of form boolean");
            env.addContext();
            p.stm_.accept(this, env);
            env.removeContext();
            return env;
        }
    }

    private class CheckExp implements Exp.Visitor<Type, Env> {
        @Override
        public Type visit(ETrue p, Env env) {
            return TBool;
        }

        @Override
        public Type visit(EFalse p, Env env) {
            return TBool;
        }

        @Override
        public Type visit(EInt p, Env env) {
            return TInt;
        }

        @Override
        public Type visit(EDouble p, Env env) {
            return TDouble;
        }

        @Override
        public Type visit(EId p, Env env) {
            return env.lookupVar(p.id_);
        }

        @Override
        public Type visit(EApp p, Env env) {
            FunType ft = env.lookupFun(p.id_);
            if(ft.args.size() != p.listexp_.size())
                throw new TypeException("EApp: expected " + ft.args.size() + " arguments but found " + p.listexp_.size());
            for(int i=0; i<ft.args.size(); i++) {
                Type argType = p.listexp_.get(i).accept(this, env);
                if(typeCode(argType) != typeCode(ft.args.get(i)))
                    throw new TypeException("EApp: expected type " + typeCode(argType) + " but found " + typeCode(ft.args.get(i)));
            }
            return ft.val;
        }

        @Override
        public Type visit(EPostIncr p, Env env) {
            Type t = env.lookupVar(p.id_);
            if(!(typeCode(t) == TypeCode.CInt || typeCode(t) == TypeCode.CDouble))
                throw new TypeException("EPostIncr: expected int or double but found " + typeCode(t));
            return t;
        }

        @Override
        public Type visit(EPostDecr p, Env env) {
            Type t = env.lookupVar(p.id_);
            if(!(typeCode(t) == TypeCode.CInt || typeCode(t) == TypeCode.CDouble))
                throw new TypeException("EPostDecr: expected int or double but found " + typeCode(t));
            return t;
        }

        @Override
        public Type visit(EPreIncr p, Env env) {
            Type t = env.lookupVar(p.id_);
            if(!(typeCode(t) == TypeCode.CInt || typeCode(t) == TypeCode.CDouble))
                throw new TypeException("EPreIncr: expected int or double but found " + typeCode(t));
            return t;
        }

        @Override
        public Type visit(EPreDecr p, Env env) {
            Type t = env.lookupVar(p.id_);
            if(!(typeCode(t) == TypeCode.CInt || typeCode(t) == TypeCode.CDouble))
                throw new TypeException("EPreDecr: expected int or double but found " + typeCode(t));
            return t;
        }

        @Override
        public Type visit(ETimes p, Env env) {
            Type t = p.exp_1.accept(this, env);
            if(!(typeCode(t) == TypeCode.CInt || typeCode(t) == TypeCode.CDouble))
                throw new TypeException("ETimes: expected int or double but found " + typeCode(t));
            Type t2 = p.exp_2.accept(this, env);
            if(!(typeCode(t) == typeCode(t2)))
                throw new TypeException("Etimes: type mismatch found " + typeCode(t) + " and " + typeCode(t2));
            return t;
        }

        @Override
        public Type visit(EDiv p, Env env) {
            Type t = p.exp_1.accept(this, env);
            if(!(typeCode(t) == TypeCode.CInt || typeCode(t) == TypeCode.CDouble))
                throw new TypeException("EDiv: expected int or double but found " + typeCode(t));
            Type t2 = p.exp_2.accept(this, env);
            if(!(typeCode(t) == typeCode(t2)))
                throw new TypeException("EDiv: type mismatch found " + typeCode(t) + " and " + typeCode(t2));
            return t;
        }

        @Override
        public Type visit(EPlus p, Env env) {
            Type t = p.exp_1.accept(this, env);
            if(!(typeCode(t) == TypeCode.CInt || typeCode(t) == TypeCode.CDouble))
                throw new TypeException("EPlus: expected int or double but found " + typeCode(t));
            Type t2 = p.exp_2.accept(this, env);
            if(!(typeCode(t) == typeCode(t2)))
                throw new TypeException("EPlus: type mismatch found " + typeCode(t) + " and " + typeCode(t2));
            return t;
        }

        @Override
        public Type visit(EMinus p, Env env) {
            Type t = p.exp_1.accept(this, env);
            if(!(typeCode(t) == TypeCode.CInt || typeCode(t) == TypeCode.CDouble))
                throw new TypeException("EMinus: expected int or double but found " + typeCode(t));
            Type t2 = p.exp_2.accept(this, env);
            if(!(typeCode(t) == typeCode(t2)))
                throw new TypeException("EMinus: type mismatch found " + typeCode(t) + " and " + typeCode(t2));
            return t;
        }

        @Override
        public Type visit(ELt p, Env env) {
            Type t = p.exp_1.accept(this, env);
            if(!(typeCode(t) == TypeCode.CInt || typeCode(t) == TypeCode.CDouble))
                throw new TypeException("ELt: expected int or double but found " + typeCode(t));
            Type t2 = p.exp_2.accept(this, env);
            if(!(typeCode(t) == typeCode(t2)))
                throw new TypeException("ELt: type mismatch found " + typeCode(t) + " and " + typeCode(t2));
            return TBool;
        }

        @Override
        public Type visit(EGt p, Env env) {
            Type t = p.exp_1.accept(this, env);
            if(!(typeCode(t) == TypeCode.CInt || typeCode(t) == TypeCode.CDouble))
                throw new TypeException("EGt: expected int or double but found " + typeCode(t));
            Type t2 = p.exp_2.accept(this, env);
            if(!(typeCode(t) == typeCode(t2)))
                throw new TypeException("EGt: type mismatch found " + typeCode(t) + " and " + typeCode(t2));
            return TBool;
        }

        @Override
        public Type visit(ELtEq p, Env env) {
            Type t = p.exp_1.accept(this, env);
            if(!(typeCode(t) == TypeCode.CInt || typeCode(t) == TypeCode.CDouble))
                throw new TypeException("ELtEq: expected int or double but found " + typeCode(t));
            Type t2 = p.exp_2.accept(this, env);
            if(!(typeCode(t) == typeCode(t2)))
                throw new TypeException("ELtEq: type mismatch found " + typeCode(t) + " and " + typeCode(t2));
            return TBool;
        }

        @Override
        public Type visit(EGtEq p, Env env) {
            Type t = p.exp_1.accept(this, env);
            if(!(typeCode(t) == TypeCode.CInt || typeCode(t) == TypeCode.CDouble))
                throw new TypeException("EGtEq: expected int or double but found " + typeCode(t));
            Type t2 = p.exp_2.accept(this, env);
            if(!(typeCode(t) == typeCode(t2)))
                throw new TypeException("EGtEq: type mismatch found " + typeCode(t) + " and " + typeCode(t2));
            return TBool;
        }

        @Override
        public Type visit(EEq p, Env env) {
            Type t = p.exp_1.accept(this, env);
            if(!(typeCode(t) == TypeCode.CInt || typeCode(t) == TypeCode.CDouble || typeCode(t) == TypeCode.CBool))
                throw new TypeException("EEq: expected int, double or bool but found " + typeCode(t));
            Type t2 = p.exp_2.accept(this, env);
            if(!(typeCode(t) == typeCode(t2)))
                throw new TypeException("EEq: type mismatch found " + typeCode(t) + " and " + typeCode(t2));
            return TBool;
        }

        @Override
        public Type visit(ENEq p, Env env) {
            Type t = p.exp_1.accept(this, env);
            if(!(typeCode(t) == TypeCode.CInt || typeCode(t) == TypeCode.CDouble || typeCode(t) == TypeCode.CBool))
                throw new TypeException("ENEq: expected int, double or bool but found " + typeCode(t));
            Type t2 = p.exp_2.accept(this, env);
            if(!(typeCode(t) == typeCode(t2)))
                throw new TypeException("ENEq: type mismatch found " + typeCode(t) + " and " + typeCode(t2));
            return TBool;
        }

        @Override
        public Type visit(EAnd p, Env env) {
            Type t = p.exp_1.accept(this, env);
            if(!(typeCode(t) == TypeCode.CBool))
                throw new TypeException("EAnd: expected int, double or bool but found " + typeCode(t));
            Type t2 = p.exp_2.accept(this, env);
            if(!(typeCode(t) == typeCode(t2)))
                throw new TypeException("EAnd: type mismatch found " + typeCode(t) + " and " + typeCode(t2));
            return TBool;
        }

        @Override
        public Type visit(EOr p, Env env) {
            Type t = p.exp_1.accept(this, env);
            if(!(typeCode(t) == TypeCode.CBool))
                throw new TypeException("EOr: expected int, double or bool but found " + typeCode(t));
            Type t2 = p.exp_2.accept(this, env);
            if(!(typeCode(t) == typeCode(t2)))
                throw new TypeException("EOr: type mismatch found " + typeCode(t) + " and " + typeCode(t2));
            return TBool;
        }

        @Override
        public Type visit(EAss p, Env env) {
            Type t = env.lookupVar(p.id_);
            Type t2 = p.exp_.accept(this, env);
            if(!(typeCode(t) == typeCode(t2)))
                throw new TypeException("Eass: type mismatch found " + typeCode(t) + " and " + typeCode(t2));
            return t;
        }
    }


	private class FunType {
        public LinkedList<Type> args;
		public Type val;

		@Override
        public boolean equals(Object o) {
		    if(o instanceof  FunType) {
                FunType ft = (FunType)o;
                if(val != ft.val)
                    return false;
                if(args.size() != ft.args.size())
                    return false;
                for(int i=0; i<args.size(); i++) {
                    if(!args.get(i).equals(ft.args.get(i)))
                        return false;
                }
                return true;
            }

            return false;
        }
	}

	private class ArgType {
        public String id;
        public Type ty;
    }

	private class Env {
		public HashMap<String,FunType> signature = new HashMap<>();
		public LinkedList<HashMap<String,Type>> contexts = new LinkedList<>();
		public String currentFun;

		public Type lookupVar(String id) {
            for(int i=0; i<contexts.size(); i++) {
                if(contexts.get(i).containsKey(id)) {
                    return contexts.get(i).get(id);
                }
            }
            throw new TypeException("Id " + id + " not found in environment");
		}

        public void addFunction(String id, FunType ty) {
		    if(signature.containsKey(id))
		        throw new TypeException("Function with name " + id + " already exists");
            signature.put(id, ty);
        }

        public FunType lookupFun(String id) {
		    if(signature.containsKey(id)) {
		        return signature.get(id);
            }
            throw new TypeException("Function " + id + " not found in environment");
        }

        public void updateVar(String id, Type ty) {
		    if(contexts.getFirst().containsKey(id)) {
		        throw new TypeException("Id " + id + " already exists in this context");
            }
            contexts.getFirst().put(id, ty);
        }

        public void addFunctionContext(String id) {
		    currentFun = id;
		    addContext();
        }

        public void removeFunctionContext() {
		    currentFun = null;
		    removeContext();
        }

		public void addContext() {
            contexts.addFirst(new HashMap<>());
        }

        public void removeContext() {
		    contexts.removeFirst();
        }
	}

	public static enum TypeCode {
        CInt,
        CDouble,
        CBool,
        CVoid
    };
}