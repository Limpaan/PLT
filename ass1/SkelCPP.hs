module SkelCPP where

-- Haskell module generated by the BNF converter

import AbsCPP
import ErrM
type Result = Err String

failure :: Show a => a -> Result
failure x = Bad $ "Undefined case: " ++ show x

transId :: Id -> Result
transId x = case x of
  Id string -> failure x
transProgram :: Program -> Result
transProgram x = case x of
  PDefs defs -> failure x
transDef :: Def -> Result
transDef x = case x of
  DUsing ids -> failure x
  DTypeDef tdefstm -> failure x
  DFun type_ id arglist fbody -> failure x
  DArg arg -> failure x
transArglist :: Arglist -> Result
transArglist x = case x of
  AList args -> failure x
transFBody :: FBody -> Result
transFBody x = case x of
  SEmpty -> failure x
  SList stms -> failure x
transArg :: Arg -> Result
transArg x = case x of
  Argum type_ argdecl -> failure x
  ArgumCons type_ argdecl -> failure x
transArgDecl :: ArgDecl -> Result
transArgDecl x = case x of
  AEmpty -> failure x
  AIdent id -> failure x
  AInit id exp -> failure x
transStm :: Stm -> Result
transStm x = case x of
  SExp exp -> failure x
  SDecl type_ vars -> failure x
  SDeclCons type_ vars -> failure x
  SReturn exp -> failure x
  SWhile exp stm -> failure x
  SDoWhile stm exp -> failure x
  SFor arg exp1 exp2 stm -> failure x
  SBlock stms -> failure x
  SIf exp stm -> failure x
  SIfElse exp stm1 stm2 -> failure x
  STypeDef tdefstm -> failure x
transTDefStm :: TDefStm -> Result
transTDefStm x = case x of
  TypeDef type_ id -> failure x
transVar :: Var -> Result
transVar x = case x of
  VDecl id -> failure x
  VInit id exp -> failure x
transExp :: Exp -> Result
transExp x = case x of
  ELit lit -> failure x
  EQualC ids -> failure x
  EIndex exp1 exp2 -> failure x
  ECall exp exps -> failure x
  EProj1 exp1 exp2 -> failure x
  EProj2 exp1 exp2 -> failure x
  EPIncr exp -> failure x
  EPDecr exp -> failure x
  EIncr exp -> failure x
  EDecr exp -> failure x
  EDeref exp -> failure x
  ENeg exp -> failure x
  EMul exp1 exp2 -> failure x
  EDiv exp1 exp2 -> failure x
  ERem exp1 exp2 -> failure x
  EAdd exp1 exp2 -> failure x
  Esub exp1 exp2 -> failure x
  ERShift exp1 exp2 -> failure x
  ELShift exp1 exp2 -> failure x
  ELt exp1 exp2 -> failure x
  EGt exp1 exp2 -> failure x
  ELEq exp1 exp2 -> failure x
  EGEq exp1 exp2 -> failure x
  EEq exp1 exp2 -> failure x
  ENEq exp1 exp2 -> failure x
  EAnd exp1 exp2 -> failure x
  EOr exp1 exp2 -> failure x
  EAss exp1 exp2 -> failure x
  EPAss exp1 exp2 -> failure x
  EMAss exp1 exp2 -> failure x
  ECond exp1 exp2 exp3 -> failure x
  EExcep exp -> failure x
transType :: Type -> Result
transType x = case x of
  Tint -> failure x
  Tbool -> failure x
  Tchar -> failure x
  Tdouble -> failure x
  Tvoid -> failure x
  TQualC ids -> failure x
  TRef type_ -> failure x
transLit :: Lit -> Result
transLit x = case x of
  LInt integer -> failure x
  LDouble double -> failure x
  LChar char -> failure x
  LString strings -> failure x

