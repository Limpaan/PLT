.class public scopes_while_leakage

.super java/lang/Object



.method public <init>()V

  .limit locals 1



  aload_0

  invokespecial java/lang/Object/<init>()V

  return



.end method



.method public static main([Ljava/lang/String;)V

  .limit locals 1

  .limit stack  1



  invokestatic scopes_while_leakage/main()I

  pop

  return



.end method



.method public static main()I
.limit locals 1000
.limit stack 1000
iconst_1
istore_0


iconst_0
istore_1


L0:
iconst_1
iload_1
dup
iconst_1
iadd
istore_1
iconst_1
if_icmplt L2
pop
iconst_0
L2:
ifeq L1
iconst_1
istore_2


goto L0
L1:
iconst_1
iload_0
iconst_1
if_icmpeq L3
pop
iconst_0
L3:
pop


iload_0
istore_2


iconst_0
ireturn


return
.end method
