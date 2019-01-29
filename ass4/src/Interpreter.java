import java.util.*;
import Fun.Absyn.*;
import Fun.VisitSkel;

public class Interpreter {

    final Strategy strategy;
    private Closure funClosure;

    public Interpreter(Strategy strategy) {
        this.strategy = strategy;
    }

    public void interpret(Program p) {
        System.out.println(p.accept(new ProgramInterpreter(), new Object()));
    }

    private class ProgramInterpreter implements Program.Visitor<Integer, Object> {
        @Override
        public Integer visit(Prog p, Object arg) {
            funClosure = new Closure();
            for(Def d : p.listdef_) {
                d.accept(new FunctionLister(), arg);
            }
            return p.main_.accept(new MainInterpreter(), arg);
        }
    }

    private class MainInterpreter implements Main.Visitor<Integer, Object> {
        @Override
        public Integer visit(DMain p, Object arg) {
            Closure e = p.exp_.accept(new ExpInterpreter(), funClosure.env);
            return e.exp.accept(new EIntValue(), arg);
        }
    }

    /*
     * Extracts the value from an EInt, otherwise throws an error
     */
    private class EIntValue implements Exp.Visitor<Integer, Object> {
        @Override
        public Integer visit(EVar p, Object arg) {
            throw new RuntimeException("Can't use expression as an integer directly");
        }

        @Override
        public Integer visit(EInt p, Object arg) {
            return p.integer_;
        }

        @Override
        public Integer visit(EApp p, Object arg) {
            throw new RuntimeException("Can't use expression as an integer directly");
        }

        @Override
        public Integer visit(EAdd p, Object arg) {
            throw new RuntimeException("Can't use expression as an integer directly");
        }

        @Override
        public Integer visit(ESub p, Object arg) {
            throw new RuntimeException("Can't use expression as an integer directly");
        }

        @Override
        public Integer visit(ELt p, Object arg) {
            throw new RuntimeException("Can't use expression as an integer directly");
        }

        @Override
        public Integer visit(EIf p, Object arg) {
            throw new RuntimeException("Can't use expression as an integer directly");
        }

        @Override
        public Integer visit(EAbs p, Object arg) {
            throw new RuntimeException("Can't use expression as an integer directly");
        }
    }

    private class ExpInterpreter implements Exp.Visitor<Closure, HashMap<String, Closure>> {
        @Override
        public Closure visit(EVar p, HashMap<String, Closure> arg) {
            Closure s = lookup(p.ident_, arg);
            return s.exp.accept(this, s.env);
        }

        @Override
        public Closure visit(EInt p, HashMap<String, Closure> arg) {
            return new Closure(p);
        }

        @Override
        public Closure visit(EApp p, HashMap<String, Closure> arg) {
            Closure c = p.exp_1.accept(this, arg);
            if(!(c.exp instanceof EAbs))
                throw new RuntimeException("Can't use a value as a function");
            EAbs f = (EAbs) c.exp;
            Closure u;
            if(strategy == Strategy.CallByValue)
                u = p.exp_2.accept(this, arg);
            else if(strategy == Strategy.CallByName)
                u = new Closure(p.exp_2, arg);
            else
                throw new RuntimeException("No strategy chosen!");

            arg = update(f.ident_, u, c.env);
            return f.exp_.accept(this, arg);
        }

        @Override
        public Closure visit(EAdd p, HashMap<String, Closure> arg) {
            Integer c1 = p.exp_1.accept(this, arg).exp.accept(new EIntValue(), arg);
            Integer c2 = p.exp_2.accept(this, arg).exp.accept(new EIntValue(), arg);
            return new Closure(new EInt(c1 + c2));
        }

        @Override
        public Closure visit(ESub p, HashMap<String, Closure> arg) {
            Integer c1 = p.exp_1.accept(this, arg).exp.accept(new EIntValue(), arg);
            Integer c2 = p.exp_2.accept(this, arg).exp.accept(new EIntValue(), arg);
            return new Closure(new EInt(c1 - c2));
        }

        @Override
        public Closure visit(ELt p, HashMap<String, Closure> arg) {
            Integer c1 = p.exp_1.accept(this, arg).exp.accept(new EIntValue(), arg);
            Integer c2 = p.exp_2.accept(this, arg).exp.accept(new EIntValue(), arg);
            if(c1 < c2)
                return new Closure(new EInt(1));
            return new Closure(new EInt(0));
        }

        @Override
        public Closure visit(EIf p, HashMap<String, Closure> arg) {
            Integer c1 = p.exp_1.accept(this, arg).exp.accept(new EIntValue(), arg);
            if(c1 != 0)
                return p.exp_2.accept(this, arg);
            else
                return p.exp_3.accept(this, arg);
        }

        @Override
        public Closure visit(EAbs p, HashMap<String, Closure> arg) {
            return new Closure(p, arg);
        }
    }

    private class FunctionLister implements Def.Visitor<Object, Object> {
        @Override
        public Object visit(DDef p, Object arg) {
            funClosure.extendFunction(p.ident_, p.listident_, p.exp_);
            return null;
        }
    }

    public Closure lookup(String id, HashMap<String, Closure> clos) {
        if(clos.containsKey(id))
            return clos.get(id);
        if(funClosure.env.containsKey(id))
            return funClosure.env.get(id);
        throw new RuntimeException("Environment does not contain id " + id);
    }

    public HashMap<String, Closure> update(String id, Closure val, HashMap<String, Closure> old) {
        HashMap<String, Closure> newEnv = new HashMap<>(old);
        newEnv.put(id, new Closure(val.exp, val.env));
        return newEnv;
    }

    private class Closure {
        private Exp exp;
        private HashMap<String, Closure> env;

        public Closure() {
            this.env = new HashMap<>();
        }

        public Closure(Exp exp) {
            this.exp = exp;
            this.env = new HashMap<>();
        }

        public Closure(Exp exp, HashMap<String, Closure> env) {
            this.exp = exp;
            this.env = env;
        }

        public void extendFunction(String id, ListIdent vars, Exp exp) {
            Exp tmp = exp;
            for(int i=vars.size()-1; i>=0; i--) {
                tmp = new EAbs(vars.get(i), tmp);
            }
            //for(int i=0; i<vars.size(); i++) {
            //    tmp = new EAbs(vars.get(i), tmp);
            //}
            env.put(id, new Closure(tmp));
        }
    }
}
