import CPP.Absyn.*;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.*;

public class Interpreter {
    public static Scanner sc = new Scanner(System.in);

    public void interpret(Program p) {
        Env env = new Env();
        p.accept(new InterpretProg(), env);
    }

    private class InterpretProg implements Program.Visitor<Env, Env> {
        @Override
        public Env visit(PDefs p, Env env) {
            //Store the syntax trees for the functions in the environment
            for(Def d: p.listdef_) {
                d.accept(new BuildTrees(), env);
            }
            env.addContext();
            DFun main = env.getFun("main");
            main.accept(new InterpretFun(), env);
            env.removeContext();

            return env;
        }
    }

    private class InterpretFun implements Def.Visitor<Val, Env> {
        @Override
        public Val visit(DFun p, Env env) {
            for(Stm s: p.liststm_) {
                //If we get a return, then we have the "return" statement
                Val ret = s.accept(new StmExecutor(), env);
                if(ret != null)
                    return ret;
            }
            return null;
        }
    }

    private class StmExecutor implements Stm.Visitor<Val, Env> {
        @Override
        public Val visit(SExp p, Env env) {
            p.exp_.accept(new ExpEvaluator(), env);
            return null;
        }

        @Override
        public Val visit(SDecls p, Env env) {
            for(String id : p.listid_) {
                env.initVar(id, null);
            }
            return null;
        }

        @Override
        public Val visit(SInit p, Env env) {
            Val expEv = p.exp_.accept(new ExpEvaluator(), env);
            if(expEv != null)
                expEv = expEv.copy();
            env.initVar(p.id_, expEv);
            return null;
        }

        @Override
        public Val visit(SReturn p, Env env) {
            Val ret = p.exp_.accept(new ExpEvaluator(), env);
            return ret;
        }

        @Override
        public Val visit(SWhile p, Env env) {
            while(p.exp_.accept(new ExpEvaluator(), env).valb) {
                env.addContext();
                Val ret = p.stm_.accept(this, env);
                env.removeContext();
                if(ret != null)
                    return ret;
            }
            return null;
        }

        @Override
        public Val visit(SBlock p, Env env) {
            env.addContext();
            for(Stm s: p.liststm_) {
                Val ret = s.accept(this, env);
                if(ret != null) { //This is a return statement
                    env.removeContext();
                    return ret;
                }
            }
            env.removeContext();
            return null;
        }

        @Override
        public Val visit(SIfElse p, Env env) {
            env.addContext();
            Val ret;
            if(p.exp_.accept(new ExpEvaluator(), env).valb) {
                ret = p.stm_1.accept(this, env);
            } else {
                ret = p.stm_2.accept(this, env);
            }
            env.removeContext();
            if(ret != null)
                return ret;
            return null;
        }
    }

    private class LookupArgId implements Arg.Visitor<String, Env> {
        @Override
        public String visit(ADecl p, Env arg) {
            return p.id_;
        }
    }

    private class ExpEvaluator implements Exp.Visitor<Val, Env> {
        @Override
        public Val visit(ETrue p, Env arg) {
            return new ValueBool(true);
        }

        @Override
        public Val visit(EFalse p, Env arg) {
            return new ValueBool(false);
        }

        @Override
        public Val visit(EInt p, Env arg) {
            return new ValueInt(p.integer_);
        }

        @Override
        public Val visit(EDouble p, Env arg) {
            return new ValueDouble(p.double_);
        }

        @Override
        public Val visit(EId p, Env arg) {
            return arg.lookupVar(p.id_);
        }

        @Override
        public Val visit(EApp p, Env env) {

            if(p.id_.equals("printInt")) {
                Val x = null;
                for(Exp e: p.listexp_) {
                    x = e.accept(this, env);
                }
                printInt(x.vali);
            } else if(p.id_.equals("printDouble")) {
                Val x = null;
                for(Exp e: p.listexp_) {
                    x = e.accept(this, env);
                }
                printDouble(x.vald);
            } else if(p.id_.equals("readInt")) {
                int result;
                result = readInt();
                return new ValueInt(result);
            } else if(p.id_.equals("readDouble")) {
                double result;
                result = readDouble();
                return new ValueDouble(result);
            } else {
                //Generic functions
                //Evaluate arguments
                LinkedList<Val> argVals = new LinkedList<>();
                for(Exp e: p.listexp_) {
                    argVals.addLast(e.accept(this, env));
                }
                //Add new context with id/value pairs
                env.addContext();
                ListArg argIds = env.getFun(p.id_).listarg_;
                for(int i=0; i<argIds.size(); i++) {
                    env.initVar(argIds.get(i).accept(new LookupArgId(), env), argVals.get(i).copy());
                }
                Val ret = env.getFun(p.id_).accept(new InterpretFun(), env);
                env.removeContext();
                return ret;
            }
            return null;
        }

        private double readDouble() {
            return sc.nextDouble();
        }

        private int readInt() {
            return sc.nextInt();
        }

        private void printInt(int x) {
            System.out.println(x);
        }

        private void printDouble(double x) {
            System.out.println(x);
        }

        @Override
        public Val visit(EPostIncr p, Env arg) {
            Val updat = arg.lookupVar(p.id_);
            Val ret = updat.copy();
            updat.incr();
            return ret;
        }

        @Override
        public Val visit(EPostDecr p, Env arg) {
            Val updat = arg.lookupVar(p.id_);
            Val ret = updat.copy();
            updat.decr();
            return ret;
        }

        @Override
        public Val visit(EPreIncr p, Env arg) {
            Val updat = arg.lookupVar(p.id_);
            updat.incr();
            return updat;
        }

        @Override
        public Val visit(EPreDecr p, Env arg) {
            Val updat = arg.lookupVar(p.id_);
            updat.decr();
            return updat;
        }

        @Override
        public Val visit(ETimes p, Env arg) {
            Val val1 = p.exp_1.accept(this, arg).copy();
            Val val2 = p.exp_2.accept(this, arg).copy();
            return val1.mult(val1, val2);
        }

        @Override
        public Val visit(EDiv p, Env arg) {
            Val val1 = p.exp_1.accept(this, arg).copy();
            Val val2 = p.exp_2.accept(this, arg).copy();
            return val1.div(val1, val2);
        }

        @Override
        public Val visit(EPlus p, Env arg) {
            Val val1 = p.exp_1.accept(this, arg).copy();
            Val val2 = p.exp_2.accept(this, arg).copy();
            return val1.add(val1, val2);
        }

        @Override
        public Val visit(EMinus p, Env arg) {
            Val val1 = p.exp_1.accept(this, arg).copy();
            Val val2 = p.exp_2.accept(this, arg).copy();
            return val1.minus(val1, val2);
        }

        @Override
        public Val visit(ELt p, Env arg) {
            Val val1 = p.exp_1.accept(this, arg).copy();
            Val val2 = p.exp_2.accept(this, arg).copy();
            return val1.eLt(val1, val2);
        }

        @Override
        public Val visit(EGt p, Env arg) {
            Val val1 = p.exp_1.accept(this, arg).copy();
            Val val2 = p.exp_2.accept(this, arg).copy();
            return val1.eGt(val1, val2);
        }

        @Override
        public Val visit(ELtEq p, Env arg) {
            Val val1 = p.exp_1.accept(this, arg).copy();
            Val val2 = p.exp_2.accept(this, arg).copy();
            return val1.eLtEq(val1, val2);
        }

        @Override
        public Val visit(EGtEq p, Env arg) {
            Val val1 = p.exp_1.accept(this, arg).copy();
            Val val2 = p.exp_2.accept(this, arg).copy();
            return val1.eGtEq(val1, val2);
        }

        @Override
        public Val visit(EEq p, Env arg) {
            Val val1 = p.exp_1.accept(this, arg).copy();
            Val val2 = p.exp_2.accept(this, arg).copy();
            return val1.eEq(val1, val2);
        }

        @Override
        public Val visit(ENEq p, Env arg) {
            Val val1 = p.exp_1.accept(this, arg).copy();
            Val val2 = p.exp_2.accept(this, arg).copy();
            return val1.eNEq(val1, val2);
        }

        @Override
        public Val visit(EAnd p, Env arg) {
            Val val1 = p.exp_1.accept(this, arg).copy();
            if(!val1.valb)
                return val1.copy();
            Val val2 = p.exp_2.accept(this, arg).copy();
            return val1.eAnd(val1, val2);
        }

        @Override
        public Val visit(EOr p, Env arg) {
            Val val1 = p.exp_1.accept(this, arg).copy();
            if(val1.valb)
                return val1.copy();
            Val val2 = p.exp_2.accept(this, arg).copy();
            return val1.eOr(val1, val2);
        }

        @Override
        public Val visit(EAss p, Env arg) {
            Val newVal = p.exp_.accept(this, arg).copy();
            arg.assignVar(p.id_, newVal);
            return newVal;
        }
    }

    private class BuildTrees implements Def.Visitor<Env, Env> {
        @Override
        public Env visit(DFun p, Env env) {
            env.addFun(p);
            return env;
        }
    }

    private class Env {
        public HashMap<String, DFun> functions = new HashMap<>();
        public LinkedList<HashMap<String, Val>> contexts = new LinkedList<>();

        private Val lookupVar(String id) {
            for(int i=0; i<contexts.size(); i++) {
                if(contexts.get(i).containsKey(id))
                    return contexts.get(i).get(id);
            }
            return null;
        }

        private void assignVar(String id, Val val) {
            for(int i=0; i<contexts.size(); i++) {
                if(contexts.get(i).containsKey(id)) {
                    contexts.get(i).put(id, val);
                    return;
                }
            }
        }

        private void initVar(String id, Val val) {
            contexts.getFirst().put(id, val);
        }

        private void addContext() {
            contexts.addFirst(new HashMap<>());
        }

        private void removeContext() {
            contexts.removeFirst();
        }

        private DFun getFun(String id) {
            return functions.get(id);
        }

        private void addFun(DFun d) {
            functions.put(d.id_, d);
        }
    }

    private abstract class Val {
        int vali;
        double vald;
        boolean valb;

        public abstract void incr();

        public abstract void decr();

        public abstract Val mult(Val val1, Val val2);

        public abstract Val div(Val val1, Val val2);

        public abstract Val add(Val val1, Val val2);

        public abstract Val minus(Val val1, Val val2);

        public abstract Val eLt(Val val1, Val val2);

        public abstract Val eGt(Val val1, Val val2);

        public abstract Val eLtEq(Val val1, Val val2);

        public abstract Val eGtEq(Val val1, Val val2);

        public abstract Val eEq(Val val1, Val val2);

        public abstract Val eNEq(Val val1, Val val2);

        public abstract Val eAnd(Val val1, Val val2);

        public abstract Val eOr(Val val1, Val val2);

        public abstract Val copy();

    }

    private class ValueInt extends Val {
        public ValueInt(int a) {
            vali = a;
        }

        @Override
        public void incr() {
            vali++;
        }

        @Override
        public void decr() {
            vali--;
        }

        @Override
        public Val mult(Val val1, Val val2) {
            return new ValueInt(val1.vali * val2.vali);
        }

        @Override
        public Val div(Val val1, Val val2) {
            return new ValueInt(val1.vali / val2.vali);
        }

        @Override
        public Val add(Val val1, Val val2) {
            return new ValueInt(val1.vali + val2.vali);
        }

        @Override
        public Val minus(Val val1, Val val2) {
            return new ValueInt(val1.vali - val2.vali);
        }

        @Override
        public Val eLt(Val val1, Val val2) {
            return new ValueBool(val1.vali < val2.vali);
        }

        @Override
        public Val eGt(Val val1, Val val2) {
            return new ValueBool(val1.vali > val2.vali);
        }

        @Override
        public Val eLtEq(Val val1, Val val2) {
            return new ValueBool(val1.vali <= val2.vali);
        }

        @Override
        public Val eGtEq(Val val1, Val val2) {
            return new ValueBool(val1.vali >= val2.vali);
        }

        @Override
        public Val eEq(Val val1, Val val2) {
            return new ValueBool(val1.vali == val2.vali);
        }

        @Override
        public Val eNEq(Val val1, Val val2) {
            return new ValueBool(val1.vali != val2.vali);
        }

        @Override
        public Val eAnd(Val val1, Val val2) {
            return null;
        }

        @Override
        public Val eOr(Val val1, Val val2) {
            return null;
        }

        @Override
        public Val copy() {
            return new ValueInt(vali);
        }
    }

    private class ValueDouble extends Val {
        public ValueDouble(double a) {
            vald = a;
        }


        @Override
        public void incr() {
            vald++;
        }

        @Override
        public void decr() {
            vald--;
        }

        @Override
        public Val mult(Val val1, Val val2) {
            return new ValueDouble(val1.vald * val2.vald);
        }

        @Override
        public Val div(Val val1, Val val2) {
            return new ValueDouble(val1.vald / val2.vald);
        }

        @Override
        public Val add(Val val1, Val val2) {
            return new ValueDouble(val1.vald + val2.vald);
        }

        @Override
        public Val minus(Val val1, Val val2) {
            return new ValueDouble(val1.vald - val2.vald);
        }

        @Override
        public Val eLt(Val val1, Val val2) {
            return new ValueBool(val1.vald < val2.vald);
        }

        @Override
        public Val eGt(Val val1, Val val2) {
            return new ValueBool(val1.vald > val2.vald);
        }

        @Override
        public Val eLtEq(Val val1, Val val2) {
            return new ValueBool(val1.vald <= val2.vald);
        }

        @Override
        public Val eGtEq(Val val1, Val val2) {
            return new ValueBool(val1.vald >= val2.vald);
        }

        @Override
        public Val eEq(Val val1, Val val2) {
            return new ValueBool(val1.vald == val2.vald);
        }

        @Override
        public Val eNEq(Val val1, Val val2) {
            return new ValueBool(val1.vald != val2.vald);
        }

        @Override
        public Val eAnd(Val val1, Val val2) {
            return null;
        }

        @Override
        public Val eOr(Val val1, Val val2) {
            return null;
        }

        @Override
        public Val copy() {
            return new ValueDouble(vald);
        }
    }

    private class ValueBool extends Val {
        public ValueBool(boolean a) {
            valb = a;
        }

        @Override
        public void incr() {

        }

        @Override
        public void decr() {

        }

        @Override
        public Val mult(Val val1, Val val2) {
            return null;
        }

        @Override
        public Val div(Val val1, Val val2) {
            return null;
        }

        @Override
        public Val add(Val val1, Val val2) {
            return null;
        }

        @Override
        public Val minus(Val val1, Val val2) {
            return null;
        }

        @Override
        public Val eLt(Val val1, Val val2) {
            return null;
        }

        @Override
        public Val eGt(Val val1, Val val2) {
            return null;
        }

        @Override
        public Val eLtEq(Val val1, Val val2) {
            return null;
        }

        @Override
        public Val eGtEq(Val val1, Val val2) {
            return null;
        }

        @Override
        public Val eEq(Val val1, Val val2) {
            return new ValueBool(val1.valb == val2.valb);
        }

        @Override
        public Val eNEq(Val val1, Val val2) {
            return new ValueBool(val1.valb != val2.valb);
        }

        @Override
        public Val eAnd(Val val1, Val val2) {
            return new ValueBool(val1.valb && val2.valb);
        }

        @Override
        public Val eOr(Val val1, Val val2) {
            return new ValueBool(val1.valb || val2.valb);
        }

        @Override
        public Val copy() {
            return new ValueBool(valb);
        }
    }

    private class ValueVoid extends Val {
        @Override
        public void incr() {

        }

        @Override
        public void decr() {

        }

        @Override
        public Val mult(Val val1, Val val2) {
            return null;
        }

        @Override
        public Val div(Val val1, Val val2) {
            return null;
        }

        @Override
        public Val add(Val val1, Val val2) {
            return null;
        }

        @Override
        public Val minus(Val val1, Val val2) {
            return null;
        }

        @Override
        public Val eLt(Val val1, Val val2) {
            return null;
        }

        @Override
        public Val eGt(Val val1, Val val2) {
            return null;
        }

        @Override
        public Val eLtEq(Val val1, Val val2) {
            return null;
        }

        @Override
        public Val eGtEq(Val val1, Val val2) {
            return null;
        }

        @Override
        public Val eEq(Val val1, Val val2) {
            return null;
        }

        @Override
        public Val eNEq(Val val1, Val val2) {
            return null;
        }

        @Override
        public Val eAnd(Val val1, Val val2) {
            return null;
        }

        @Override
        public Val eOr(Val val1, Val val2) {
            return null;
        }

        @Override
        public Val copy() {
            return null;
        }
    }
}
